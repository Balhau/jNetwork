package balhau.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import balhau.metastruct.Dicionario;
import balhau.metastruct.Par;
import balhau.utils.Log;
import balhau.utils.Sys;



/**
 * Classe responsável por lidar com pedidos a páginas web através do protocolo HTTP 
 * @author balhau
 *
 */
public class HttpConn {
	private int _porta;
	private String _url;
	private Socket _sock;
	private String _host;
	public static String SERVER_RESPONSE="SERVER_RESPONSE";
	private PrintWriter _ptw;
	private BufferedReader _br;
	private Dicionario<String, String> _sheader;
	private Dicionario<String, String> _rheader;
	private String _body;
	/**
	 * Construtor da classe com a especificação da porta
	 * @param porta {@link int} Porta de conecção
	 */
	public HttpConn(int porta){
		this._porta=porta;
		this._sheader=new Dicionario<String, String>();
		this._rheader=new Dicionario<String, String>();
	}
	
	/**
	 * Construtor da classe sem especificação de parâmetros
	 */
	public HttpConn(){
		this._porta=80;
		this._sheader=new Dicionario<String,String>();
		this._rheader=new Dicionario<String, String>();
	}
	
	private void connect(String url,int port){
		try{
			this._sock=new Socket(url, port);			
			this._ptw=new PrintWriter(this._sock.getOutputStream());
			this._br=new BufferedReader(new InputStreamReader(this._sock.getInputStream()));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendHeader(){
		String[] urlp=parseURL(this._url);
		this._host=urlp[0];
		String page=urlp[1];
		String msg="GET "+page+" HTTP/1.1"+Message.CRLF;
		buildDefaultHeaders();
		msg+=getHeaders();
		System.out.println(msg);
		_ptw.write(msg);
		_ptw.flush();
	}
	/**
	 * Especificação do URL
	 * @param url {@link String} com o url da ligação
	 */
	public void setURL(String url){
		this._url=url;
	}
	/**
	 * Getter para a propriedade URL
	 * @return {@link String} representando o URL da ligação 
	 */
	public String getUrl(){
		return this._url;
	}
	
	private void buildDefaultHeaders(){
		if(this._porta==80)	 this.setHeaderItem("Host", this._host);
		else this.setHeaderItem("Host",this._host+":"+this._porta);
		this.setHeaderItem("User-Agent", "Balhau Crawler");
		//this.setHeaderItem("Accept", "application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
		this.setHeaderItem("Pragma","no-cache");
	}
	
	public static String[] parseURL(String url){
		if(url.indexOf("://")<0)
			url="http://"+url;
		if(url.charAt(url.length()-1)=='/')
			url+=" ";
		Pattern pt=Pattern.compile("[^/]+");
		Matcher mt=pt.matcher(url);
		String[] out=new String[3];
		String aux;
		out[1]="";
		if(!mt.find())
			return null; 
		if(!mt.find())
			return null;
		String hs;
		hs=mt.group();
		if(hs.split(":").length==2)
			out[0]=hs.split(":")[0];
		else
			out[0]=hs;
		while(mt.find()){
			aux=mt.group();
			out[1]+="/";
			out[1]+=aux;
		}
		if(out[1]=="")
			out[1]="/";
		String auxs[]=hs.split(":");
		if(auxs.length==2){
			out[2]=auxs[1];
		}else{
			out[2]="80";
		}
		return out;
	}
	
	public static String formatURL(String url){
		String aux=url;
		if(url.indexOf("://")<0)
			aux="http://"+url;
		return aux;
	}
	public String getHeadersSent(String url){
		String[] out=parseURL(url);
		this._host=out[0];
		this._porta=Integer.parseInt(out[2]);
		this.buildDefaultHeaders();
		return "GET "+out[1]+" HTTP/1.1"+Message.CRLF+this.getHeaders()+Message.CRLF;
	}
	
	/**
	 * Efectua um pedido HTTP para o endereço URL fornecido
	 * @param url {@link String} endereço da ligação 
	 * @return {@link String} com o resultado do pedido.
	 * @throws IOException 
	 */
	public String getRequest(String url){
		String[] out=parseURL(url);
		this._host=out[0];
		this._porta=Integer.parseInt(out[2]);
		buildDefaultHeaders();
		return getHttpRequest(url, getHeaders());
	}
	
	public String getFullRequest(String url,String headers){
		StringBuilder sb=new StringBuilder();
		String staux;
		StringBuilder msg=new StringBuilder();
		String[] urlp;
		this._host="";
		String page="";
		urlp=parseURL(url);
		this._host=urlp[0];
		page=urlp[1];
		try {
			this.connect(this._host, this._porta);
			msg.append(headers);
			_ptw.write(msg.toString());
			_ptw.flush();
			while((staux=_br.readLine())!=null){
					sb.append(staux+Sys.EOL);
			}
			this._sock.close();
		} catch (Exception e) {
			//System.out.println(e.toString());
			try {
				this._sock.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			return "";
		}
		return sb.toString();
	}
	
	public String getHttpRequest(String url,String headers){
		StringBuilder sb=new StringBuilder();
		String staux;
		StringBuilder msg=new StringBuilder();
		String[] urlp;
		this._host="";
		String page="";
		urlp=parseURL(url);
		this._porta=Integer.parseInt(urlp[2]);
		this._host=urlp[0];
		page=urlp[1];
		try {
			this.connect(this._host, this._porta);
			msg.append("GET "+page+" HTTP/1.1"+Message.CRLF);
			msg.append(headers);
			_ptw.write(msg.toString());
			_ptw.flush();
			while((staux=_br.readLine())!=null){
					sb.append(staux+Sys.EOL);
			}
			this._sock.close();
		} catch (Exception e) {
			//System.out.println(e.toString());
			try {
				this._sock.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			return "";
		}
		return sb.toString();
	}
	/**
	 * Devolve os cabeçalhos para um determinado endereço
	 * @return String com os headers enviados pelo servidor
	 */
	public void getResponseHeaders(){
		String msg="...";
		String[] par;
		try{
			this._rheader=new Dicionario<String, String>();
			this.connect(this._host, this._porta);
			this.sendHeader();
			while((msg=_br.readLine()).length()!=0){
				par=msg.split(":");
				
				if(par.length==2){
					this._rheader.addItem(par[0], par[1]);
				}
			}
			this._sock.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public Dicionario<String, String> getRHeaders(){
		return this._rheader;
	}
	
	public String getResponseHTML(String url){
		String out=this.getHttpRequest(url, this.getHeaders());
		String[] linhas=out.split(Sys.EOL);
//		System.out.println("Count: "+linhas.length);
		String aux="";
		int k=1;
		boolean tru=true;
		for(int i=1;i<linhas.length && tru;i++){
			if(linhas[i].split(":").length<2)
				tru=false;
//			System.out.println("SOM: "+linhas[i].split(":").length);
//			System.out.println("Ravina: "+linhas.length);
//			System.out.println("LINHA: "+linhas[i]+", "+k);
			k++;
		}
		for(int j=k;j<linhas.length;j++){
			aux+=linhas[j]+Sys.EOL;
		}
		return aux;
	}
	
	
	
	/**
	 * Método que especifica um determinado parâmetro Header para a conecção
	 * @param name {@link String} nome do parâmetro
	 * @param value {@link String} valor do parâmetro
	 */
	public void setHeaderItem(String name,String value){
		if(this._sheader.existeChave(name)!=-1)
		{
			this._sheader.getItem(name).valor=value;
		}
		else
		{
			this._sheader.addItem(name, value);
		}
		
	}
	/**
	 * Método que devolve os headers para a ligação HTTP
	 * @return {@link String} contendo os headers da ligação
	 */
	public String getHeaders(){
		String sb="";
		Par<String,String> par;
		for(int i=0;i<this._sheader.getNumElementos();i++){
			par=this._sheader.getItem(i);
			sb+=par.chave+": "+par.valor;
			sb+=Message.CRLF;
		}
		sb+=Message.CRLF;
		return sb;
	}
	/**
	 * Método que devolve os headers da ligação
	 * @param host {@link String} host da ligação
	 * @param page {@link String} Página
	 * @return String resposta do servidor
	 */
	public String getRequest(String host,String page)
	{
		StringBuilder sb=new StringBuilder();
		String staux;
		String msg;
		try {
			this._sock = new Socket(host,this._porta);
			this._host=host;
			PrintWriter ptw=new PrintWriter(this._sock.getOutputStream());
			BufferedReader br=new BufferedReader(new InputStreamReader(this._sock.getInputStream()));
			msg="GET "+page+" HTTP/1.0"+Message.CRLF;
			buildDefaultHeaders();
			msg+=getHeaders();
			System.out.println(msg);
			ptw.write(msg);
			ptw.flush();
			getHeadersFromServer(br);
			while((staux=br.readLine())!=null){
					sb.append(staux+Sys.EOL);
			}
			this._sock.close();
		} catch (Exception e) {
			return "";
		}		
		return sb.toString();
	}
	
	public Dicionario<String, String> getServerHeaders(){
		return this._rheader;
	}
	
	public void getHeadersFromServer(BufferedReader br){
		this._rheader=new Dicionario<String, String>();
		try{
			String linha=br.readLine();
			String[] vals;
			this._rheader.addItem(SERVER_RESPONSE, linha);
			linha=br.readLine();
			while(linha!=null && linha!=Message.CRLF){//se não for fim de ficheiro e houver mais headers
				vals=linha.split(":",2);
				this._rheader.addItem(vals[0], vals[1]);
				linha=br.readLine();
			}
		}
		catch (Exception e) {
			System.out.println("Erro: "+e.toString());
		}
	}
	
	/**
	 * Objecto Buffered Reader
	 * @param br B
	 */
	public void getBody(BufferedReader br){
		StringBuilder body=new StringBuilder();
		String staux;
		try{
			while((staux=br.readLine())!=null){
				body.append(staux+Sys.EOL);
			}
		}
		catch (Exception e) {
			System.out.println("ERRO: "+e.toString());
		}
		this._body=body.toString();
	}
	
	public String getHost(){
		return this._host;
	}
}
