/**
 * Pacote com rotinas necessárias para implementação do protocolo MSN
 */
package balhau.network.msn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import balhau.network.Message;
import balhau.utils.Debug;

/**
 * Classe responsável por efectuar a ligação a um servidor de messenger<br/><br/>
 * <b>Referências:</b><br/>
 * <a href="http://www.hypothetic.org/docs/msn/">MSN Messenger Protocol</a>
 * @author balhau
 *
 */
public class MsnClient {
	private static int maxmsg=1000; 
	private String uname;
	private String pass;
	private Socket conn;
	private String server;
	private PrintWriter pw;
	private BufferedReader br;
	private int porta;
	private int conid;
	private static String TICKET_SERVER="https://nexus.passport.com/rdr/pprdr.asp";
	private static String USER_AGENT="Balhau Client";
	
	/**
	 * Construtor da classe com especificação dos parâmetros de autenticação na rede
	 * @param username {@link String} Identificação da conta
	 * @param password {@link String} Password associada a conta de utilizador
	 * @param server {@link String} Nome do servidor
	 * @param porta {@link int} Porta de acesso ao servidor
	 */
	public MsnClient(String username,String password,String server,int porta){
		this.uname=username;
		this.pass=password;
		this.server=server;
		this.porta=porta;
		this.conid=1;
	}
	
	/**
	 * Construtor da classe com especificação dos parâmetros de conta de utilizador 
	 * @param username {@link String} Identificação da conta
	 * @param password {@link String} Password associada à conta de utilizador
	 */
	public MsnClient(String username,String password){
		this.uname=username;
		this.pass=password;
		this.server="messenger.hotmail.com";
		this.porta=1863;
		this.conid=1;
	}
	
	/**
	 * Método que efectua a conecção ao servidor de MSN
	 * @throws IOException Excepção em caso de erro na comunicação
	 */
	public void connect() throws IOException{
		Debug.DEBUG=true;
		String out;
		String aux1;
		String ticket;
		this.conn(this.server, this.porta);
		this.sendMsg("VER "+this.conid+" MSNP8 CVR0");
		this.getMsg();
		out="CVR "+conid+" 0x0409 "+System.getProperty("os.name")+" "+System.getProperty("os.version")+" "+System.getProperty("os.arch");
		out+=" MSNMSGR 8.0.0812 msmsgs "+this.uname;
		this.sendMsg(out);
		this.pw.flush();
		this.getMsg();
		this.sendMsg("USR "+conid+" TWN I "+this.uname);
		out=this.conn.getInetAddress().toString().split("/")[1];
		this.sendMsg("XFR "+conid+" NS "+out+":"+porta+" 0 "+out+":"+porta);
		this.pw.flush();
		out=this.getMsg().split(" ")[3];
		aux1=out.split(":")[0];
		this.closeConn();
		this.conn(aux1, this.porta);
		this.sendMsg("VER "+this.conid+" MSNP8 CVR0");
		this.getMsg();
		out="CVR "+conid+" 0x0409 "+System.getProperty("os.name")+" "+System.getProperty("os.version")+" "+System.getProperty("os.arch");
		out+=" MSNMSGR 8.0.0812 msmsgs "+this.uname;
		this.sendMsg(out);
		this.getMsg();
		this.sendMsg("USR "+this.conid+" TWN I "+this.uname);
		//Debug.log("Getting PASSPORT");
		ticket=this.getPassport(this.getMsg());
		this.sendMsg("USR "+this.conid+" TWN S "+ticket);
		this.processaMsg();
		this.sendMsg("SYN "+this.conid+" 2000");
		this.processaMsg();
	}
	
	private void conn(String host,int porta) throws UnknownHostException, IOException{
		Debug.log("Connecting Server: "+host);
		this.conid=1;
		this.conn=new Socket(host, porta);
		this.pw=new PrintWriter(this.conn.getOutputStream());
		this.br=new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
	}
	
	private void processaMsg() throws IOException{
		String ms=" ";
		while(ms!=null && !ms.equals("")){
			ms=this.getMsg();
		}
	}
	
	private void sendMsg(String s){
		this.pw.write(s+Message.CRLF);
		this.pw.flush();
		if(this.conid<maxmsg)
			this.conid++;
		else
			this.conid=1;
		Debug.log("Send: "+s);
	}
	
	private String getMsg() throws IOException{
		String sout=this.br.readLine();
		Debug.log("Received: "+sout);
		return sout;
	}
	
	private void closeConn() throws IOException{
		this.conn.close();
		Debug.log("Connection closed!");
	}
	
	private String[] passVarsToStringArray(String strpasspor){
		String[] arrAux;
		arrAux=strpasspor.split(",");
		arrAux[0]=arrAux[0].split(" ")[4];
		return arrAux;
	}
	/**
	 * Método que devolve o ticket de acesso do serviço do messenger
	 * @param passp {@link String} Representa o passaport
	 * @return {@link String} Chave de autenticação no serviço de messenger
	 * @throws IOException
	 */
	private String getPassport(String passp) throws IOException{
		String[] strvars=this.passVarsToStringArray(passp);
//		URL url=new URL("http://webfact.homeip.net");
		String passport;
		@SuppressWarnings("unused")
		String passt;
		String urlpass;
		String authstr;
		URL url=new URL(TICKET_SERVER);
		URLConnection urlcon=url.openConnection();
		passport=urlcon.getHeaderField("PassportURLs");
		urlpass=passport.split(",")[1].split("=")[1];
		passt=passport.split(",")[0].split("=")[1];
		Debug.log("URLPASS: "+urlpass);
		url=new URL("https://"+urlpass);
		urlcon=url.openConnection();
		authstr="Passport1.4 OrgURL=http%3A%2F%2Fmessenger%2Emsn%2Ecom,sign-in="+this.uname+", OrgVerb=GET,";
		authstr+="pwd="+this.pass+",";
		for(int i=0;i<strvars.length;i++){
			if(strvars[i]!=""){
				if(i!=0)
					authstr+=",";
				authstr+=strvars[i];
			}
		}
		urlcon.setRequestProperty("Authorization", authstr);
		urlcon.setRequestProperty("Accept", "text/html");
		urlcon.setRequestProperty("Connection", "close");
		urlcon.setRequestProperty("User-Agent", USER_AGENT);
		String auth=urlcon.getHeaderFields().get("Authentication-Info").get(0);
		return auth.split("'")[1];
	}
}

