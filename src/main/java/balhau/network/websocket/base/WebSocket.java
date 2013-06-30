
package balhau.network.websocket.base;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import balhau.metastruct.Dicionario;
import balhau.metastruct.Lista;
import balhau.network.websocket.server.WebSocketServer;
import balhau.utils.JSONSerialize;
import balhau.utils.Log;
import balhau.utils.StringUtils;

/**
 * Classe principal para gestão dos clientes
 * <b>Referências:</b><br>
 * <a href="http://tools.ietf.org/html/rfc6455">WebSocket Protocol</a> 
 * @author balhau
 *
 */
public class WebSocket implements IWebSocket{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int ON_MESSAGE=0x1;
	public static final int ON_STOP=0x2;
	public static final int ON_START=0x3;
	public static final int MASK_SET=0x80;
	protected WebSocketServer server;
	protected String path;
	protected InputStream is;
	protected OutputStream os;
	protected Socket socket;
	protected Dicionario<String, String> headers;
	protected Lista<IWebSocketEvent> msgList;
	protected Lista<IWebSocketEvent> msgListRem;
	protected MsgHandler msghandler;
	protected boolean running;
	public String Name;
	/**
	 * Construtor
	 * @param server {@link WebSocketServer} Objecto que representa o servidor WebSocket
	 * @param is {@link InputStream} Stream para os dados de entrada
	 * @param os {@link OutputStream} Stream para os dados de saída
	 * @param client {@link Socket} Socket de ligação para o cliente
	 */
	public WebSocket(WebSocketServer server,InputStream is,OutputStream os, Socket client,String path){
		this.server=server;this.socket=client;this.is=is;this.os=os;
		this.headers=new Dicionario<String, String>();
		this.running=false;
		this.path=path;
		this.msgList=new Lista<IWebSocketEvent>();
		this.msgListRem=new Lista<IWebSocketEvent>();
		msghandler=new MsgHandler(this);
	}
	
	public Dicionario<String,String> getHeaders(){
		return headers;
	}

	public String getHeader(String key) {
		return headers.getValueItem(key);
	}
	
	public void putHeader(String key,String value){
		headers.addItem(key, value);
	}
	
	public void start(){
		running=true;
		msghandler.start();
	}
	
	public void stop(){
		running=false;
	}
	/**
	 * Método que efectua o envio de dados. Este método poderá ser futuramente alterado para permitir o uso de
	 * máscaras no envio de dados. Os primeiros dois bytes correspondem à informação de frame associada à mensagem.
	 * O primeiro byte contém as propriedades de frame e o segundo byte contém o tamanho do frame.
	 */
	public void sendMsg(String msg) {
		try{
			byte[] data=msg.getBytes();
			os.write((byte)0x81);
			if(data.length<0x7d)
				os.write((byte)data.length);
			else if(data.length<0x10000){
				os.write(0x7e);
				os.write((byte)(data.length>>>8)&0xFF);
				os.write((byte)(data.length&0xFF));
			}else{
				os.write(0x7f);
				os.write((byte)(data.length>>>54)&0xFF);
				os.write((byte)(data.length>>>48)&0xFF);
				os.write((byte)(data.length>>>40)&0xFF);
				os.write((byte)(data.length>>>32)&0xFF);
				os.write((byte)(data.length>>>24)&0xFF);
				os.write((byte)(data.length>>>16)&0xFF);
				os.write((byte)(data.length>>>8)&0xFF);
				os.write((byte)(data.length)&0xFF);
			}
			os.write(data);
			os.flush();
		}catch (Exception e) {
			Log.log("Erro durante o envio da mensagem: "+e.getMessage());
		}
	}

	public void addMsgListener(IWebSocketEvent msgList) {
		this.msgList.addValue(msgList);
	}

	public void remMsgListener(IWebSocketEvent msgList) {
		msgListRem.addValue(msgList);
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Thread interna para gestão das mensagens enviadas pelo servidor
	 * @author balhau
	 */
	private class MsgHandler extends Thread{
		private IWebSocket ws;
		int[] frame=new int[2];
		public MsgHandler(IWebSocket obj){
			ws=obj;
		}
		
		private int[] getMaskBytes(InputStream is) throws IOException{
			return new int[]{is.read(),is.read(),is.read(),is.read()};
		}
		
		private boolean dataInvalid(int[] data){
			for(int i=0;i<data.length;i++)
				if(data[i]==-1)
					return true;
			return false;
		}
		
		private boolean end(int val){
			return ((val&(~0x80))==0);
		}
		
		public void run(){
			long frameLen;
			int l1;
			int[] mask=new int[4];
			int[] l;
			try{
			while(running){
				try{
					StringBuilder sb=new StringBuilder();
					int c=is.read();
					if(c==-1){
						Log.log("Cliente efectuou o fecho do socket");
						break;
					}
					frame=new int[]{c,is.read()};
					if(dataInvalid(frame) || end(frame[1])){
						Log.log("Cliente efectuou o fecho do socket. [Invalid Data 1 case]");
						break;
					}
					frameLen=(long)(frame[1]&~(MASK_SET));
					l1=(int)frameLen;
					if(l1==0x7e){
						l=new int[]{is.read(),is.read()};
						if(dataInvalid(l)){Log.log("Fecho do socket por parte do cliente[invalid data 2 case]");break;}
						frameLen=l[0]<<8|l[1];
					}
					if(l1==0x7f){
						l=new int[]{	is.read(),is.read(),is.read(),is.read(),
											is.read(),is.read(),is.read(),is.read()};
						if(dataInvalid(l)){Log.log("Fecho do socket por parte do cliente [invalid data, 3 case]");break;}
						frameLen=	(l[0]<<56)|(l[1]<<48)|(l[2]<<40)|
									(l[3]<<32)|(l[4]<<24)|(l[5]<<16)|
									(l[6]<<8)|l[7];
					}
					if((frame[1]&MASK_SET)!=0)
						mask=getMaskBytes(is);
					byte[] data=new byte[(int)frameLen];
					for(long i=0;i<frameLen;i++){
						c=is.read();
						if(c==-1){
							Log.log("Socket fechado pelo cliente [normal case]");
							break;
						}
						if((frame[1]&MASK_SET)!=0){
							data[(int)i]=(byte)(c^mask[(int)i%4]);
						}else{
							data[(int)i]=((byte)c);
						}
					}
					notifyListeners(new String(data));
				}catch (SocketException e) {
					Log.log("Socket Closed: "+e.getMessage());
					break;
				}catch(IOException ex){
					Log.log("Erro na leitura de dados: "+ex.getMessage());
					break;
				}
			}
			}finally{
				if(!socket.isClosed()){
					try{
						Log.log("Socket Fechado");
						socket.close();
					}catch (Exception e) {
						Log.log("Erro durante o encerramento do socket de cliente");
					}
				}
				server.notifyListeners(ws, WebSocketServer.CLIENT_CLOSED);
			}
		}
		/**
		 * Método que envia uma mensagem de notificação a todos os gestores de eventos associados ao presente objecto {@link WebSocket}
		 * @param msg {@link Object} Objecto com os dados a enviar para o evento respectivo
		 */
		public void notifyListeners(String message){
			msgList=msgList.removeAll(msgListRem);
			msgListRem.Limpa();
			IWebSocketEvent[] listeners=msgList.toArray();
			if(listeners!=null){
				for(int i=0;i<listeners.length;i++){
					listeners[i].onMessage(message);
				}
			}
		}
	}
	public String getPath() {
		return path;
	}

}
