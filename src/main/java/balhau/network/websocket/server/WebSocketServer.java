package balhau.network.websocket.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Date;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import balhau.crypto.hash.MD5;
import balhau.metastruct.Dicionario;
import balhau.metastruct.Lista;
import balhau.network.Message;
import balhau.network.websocket.base.IWebSocket;
import balhau.network.websocket.base.IWebSocketHandler;
import balhau.network.websocket.base.WebSocket;
import balhau.network.websocket.base.WebSocketUtil;
import balhau.utils.ArrayUtils;
import balhau.utils.ByteBuffer;
import balhau.utils.DateUtil;
import balhau.utils.Endianess;
import balhau.utils.Log;

/**
 * Classe que tem como propósito a implementação de um servidor para o protocolo websocket definido no RFC6455
 * <br><br>
 * <b>Referências:</b><br>
 * <a href="http://tools.ietf.org/html/rfc6455">WebSocket Protocol</a>
 * <a href="http://tools.ietf.org/html/draft-hixie-thewebsocketprotocol-76#page-47">WebSocket Protocol Draft (Usado no opera)</a>
 * @author balhau
 *
 */
public class WebSocketServer{
	public static final int CLIENT_CONNECTED=0x1;
	public static final int CLIENT_CLOSED=0x2;
	private ServerSocket serverSocket;
	private ServerSocket serverSocketSSL;
	private Dicionario<String,Lista<IWebSocketHandler> > connList;
	private int porta;
	private int portaSSL;
	private String sslCertificatePath;
	private String sslCertificatePassword;
	/**
	 * Construtor da classe servidor
	 * @param porta {@link int} Porta onde deverá correr o servidor 
	 * @param subProtocol {@link ISubProtocol} Interface para o subprotocolo que irá correr no servidor
	 * @throws IOException 
	 */
	public WebSocketServer(int porta,int portaSSL) throws IOException{
		this.porta=porta;
		connList=new Dicionario<String, Lista<IWebSocketHandler>>();
		this.portaSSL=portaSSL;
	}
	/**
	 * Método que especifica o certificado a usar na construção do {@link SSLServerSocket}
	 * @param certPath String Caminho para o certificado do servidor
	 * @param password String Password do certificado
	 */
	public void setSSLCertificate(String certPath,String password){
		this.sslCertificatePath=certPath;
		this.sslCertificatePassword=password;
	}
	/**
	 * Adiciona um gestor de eventos para os objectos {@link IWebSocket} associados a um determinado caminho
	 * @param path {@link String} Caminho de contexto
	 * @param handler {@link IWebSocketHandler} Objecto com os eventos
	 */
	public void addWebSocketHandler(String path,IWebSocketHandler handler){
		Lista<IWebSocketHandler> hList=connList.getValueItem(path);
		if(hList==null){
			hList=new Lista<IWebSocketHandler>();
			connList.addItem(path, hList);
		}
		hList.addValue(handler);
	}
	
	public void stop(){
		if(serverSocket==null || serverSocket.isClosed()){
			serverSocket=null;return;
		}
		try{
			serverSocket.close();
			serverSocket=null;
		}catch (Exception e) {
			Log.log("Erro ao encerrar o servidor: "+e.getMessage());
		}
	}
	
	private String getPath(String line){
		return line.split("GET")[1].split("HTTP/1.1")[0].trim();
	}
	
	public void notifyListeners(IWebSocket ws,int typeOfEvent){
		Lista<IWebSocketHandler> lHandlers=connList.getValueItem(ws.getPath());
		if(lHandlers==null)return;
		IWebSocketHandler[] handlers=lHandlers.toArray();
		for(int i=0;i<handlers.length;i++){
			switch (typeOfEvent) {
			case CLIENT_CONNECTED:
				handlers[i].connected(ws);
				break;
			case CLIENT_CLOSED:
				handlers[i].disconnected(ws);
				break;
			default:
				break;
			}
		}
	}
	
	private static byte[] getDraftKey(String key1,String key2,byte[] key3){
		long kval=WebSocketUtil.getDraftKeyNum(key1);
		int spaces=WebSocketUtil.spacesInKey(key1);
		long kval2=WebSocketUtil.getDraftKeyNum(key2);
		int spaces2=WebSocketUtil.spacesInKey(key2);
		int part1=(int)((0xFFFFFFFF)&(kval/spaces));
		int part2=(int)((0xFFFFFFFF)&(kval2/spaces2));
		ByteBuffer bf=new ByteBuffer(Endianess.BigEndian);
		bf.writeInt(part1);
		bf.writeInt(part2);
		bf.writeBytes(key3);
		MD5 md5=new MD5();
		MessageDigest md;
		Log.log(md5.encode(bf.toByteArray()));
		return md5.digest();
	}
	
	/**
	 * Método que efectua o handshake com o cliente segundo o protocolo
	 * WebSocket
	 * @throws IOException
	 */
	public WebSocket processHandshake(Socket sock) throws IOException{
		InputStream is=sock.getInputStream();
		OutputStream os=sock.getOutputStream();
		String uri=WebSocketUtil.getLine(is);
		String p=uri.split(" ")[1].trim();
		Log.log("URI: "+p);
		WebSocket ws=new WebSocket(this, is, os, sock,getPath(uri));
		WebSocketUtil.getHTTPHeaders(is,ws);
		String[] vals=ws.getHeaders().toValueArray();
		String[] keys=ws.getHeaders().toKeyArray();
		for(int i=0;i<vals.length;i++){
			Log.log(keys[i]+"--"+vals[i]);
		}
		String key=ws.getHeader("Sec-WebSocket-Key");
		String key1=ws.getHeader("Sec-WebSocket-Key1");
		String key2=ws.getHeader("Sec-WebSocket-Key2");
		String origin=ws.getHeader("Origin");
		String skey="";
		byte[] dkey=null;
		if(key1!=null && key2!=null){
			byte[] bkey3=new byte[8];
			is.read(bkey3);
			dkey=getDraftKey(key1.trim(), key2.trim(), bkey3);
			Log.log(ArrayUtils.ArrayByteHexDesc(bkey3));
			Log.log(ArrayUtils.ArrayByteHexDesc(dkey));
		}
		else{
			skey=WebSocketUtil.webSocketServerKey(key.trim());
		}
		StringBuilder sb=new StringBuilder();
		String el=Message.CRLF;
		if(key!=null)
			sb.append("HTTP/1.1 101 Socket Protocol Handshake"+el);
		else
			sb.append("HTTP/1.1 101 Web Socket Protocol Handshake"+el);
		sb.append("Upgrade: WebSocket"+el);
		sb.append("Connection: Upgrade"+el);
		if(key!=null)
			sb.append("Sec-WebSocket-Accept: "+skey+el);
//		sb.append("Accept: text/plain"+el);
//		sb.append("Accept-Encoding: text/plain"+el);
		if(key!=null){
			sb.append("Access-Control-Allow-Origin: "+origin.trim()+el);
		}else{
			sb.append("Sec-WebSocket-Origin: "+origin.trim()+el);
			sb.append("Sec-WebSocket-Location: ws://"+ws.getHeader("Host").trim()+p+el);
		}
		sb.append("Server: Balhau WebSockets"+el);
		sb.append("Date: "+DateUtil.rfc1123Date(new Date())+el);
		sb.append("Access-Control-Allow-Credentials: true"+el);
		sb.append("Access-Control-Allow-Headers: content-type"+el);
		sb.append("Access-Control-Allow-Headers: authorization"+el);
		sb.append("Access-Control-Allow-Headers: x-websocket-extensions"+el);
		sb.append("Access-Control-Allow-Headers: x-websocket-version"+el);
		sb.append("Access-Control-Allow-Headers: x-websocket-protocol"+el);
		sb.append(el);
		os.write(sb.toString().getBytes());
		if(dkey!=null)
			os.write(dkey);
		os.flush();
		ws.start();
		return ws;
	}
	/**
	 * Método que encapsula a criação do socket de servidor.
	 * Este método assume que o certificado foi criado utilizando o formato SunX509. Para tal necessita de
	 * utilizar a ferramenta keytool para gerir os certificados.
	 * @param porta {@link int} Porta onde irá correr o servidor
	 * @return {@link ServerSocket} Socket de servidor.
	 * @throws IOException 
	 * @throws KeyStoreException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnrecoverableKeyException 
	 * @throws KeyManagementException 
	 */
	private ServerSocket createServerSocket(int porta,boolean SSL) throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, KeyManagementException{
		if(SSL){
				KeyStore ks=KeyStore.getInstance("JKS");
				ks.load(new FileInputStream(sslCertificatePath),sslCertificatePassword.toCharArray());
				KeyManagerFactory kmf=KeyManagerFactory.getInstance("SunX509");
				kmf.init(ks,sslCertificatePassword.toCharArray());
				SSLContext ctx=SSLContext.getInstance("TLS");
				ctx.init(kmf.getKeyManagers(),null,null);
				SSLServerSocketFactory ssf = ctx.getServerSocketFactory();
		        return ssf.createServerSocket(porta);
		}else{
			return new ServerSocket(porta);
		}
	}
	
	public void start() {
		try{
			serverSocket=createServerSocket(porta,false);
			serverSocketSSL=createServerSocket(portaSSL,true);
			new ReceiverThread(false).start();
			new ReceiverThread(true).start();
		}catch (Exception e) {
			Log.log("Erro ao inicializar o servidor: "+e.getMessage());
		}
		
	}
	
	class ClientThread extends Thread{
		private Socket socket;
		private ServerSocket server_socket;
		public ClientThread(Socket sock,ServerSocket serversocket){
			socket=sock;
			server_socket=serversocket;
		}
		
		public void run(){
			try{
				WebSocket ws=processHandshake(socket);
				if(ws!=null){
					notifyListeners(ws, CLIENT_CONNECTED);
				}
			}catch (SocketException e) {
				Log.log("Socket de cliente encerrado pelo cliente");
			}catch (IOException e) {
				Log.log("Erro de leitura ou escrita com o cliente");
			}
		}
	}
	
	class ReceiverThread extends Thread{
		private boolean ssl;
		private ServerSocket serversocket;
		public ReceiverThread(boolean isSSL){
			this.ssl=isSSL;
			this.serversocket=ssl?serverSocketSSL:serverSocket;
		}
		public void run(){
			while(true){
				try{
					Socket sock=serversocket.accept();
					new ClientThread(sock,serversocket).start();
				}catch (SocketException ex) {
					Log.log("Servidor encerrou normalmente");
					break;
				}catch (IOException e) {
					Log.log("Servidor encerrou de maneira inesperada"+e.getMessage());
					if(!serversocket.isClosed()){
						try{
							serversocket.close();
						}catch (Exception ee) {
							Log.log("Erro ao tentar encerrar o socket de servidor servidor"+ee.getMessage());
							serversocket=null;
						}
					}
				}
			}
		}
	}
}
