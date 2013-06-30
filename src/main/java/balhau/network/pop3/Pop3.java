package balhau.network.pop3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Classe que implementa o protocolo de POP3 (cliente)<br/><br/>
 * <b>Referências:</b><br/>
 * <a href="http://www.ietf.org/rfc/rfc1939.txt">Post Office Protocol - Version 3</a>
 * @author balhau
 */
public class Pop3 {
	private String user;
	private String pass;
	private String host;
	private Socket sock;
	private PrintWriter pwr;
	private BufferedReader br;
	private int porta;
	/**
	 * Construtor do cliente Pop3
	 * @param user {@link String} Representa o username da conta
	 * @param pass {@link String} Representa a password associada à conta
	 * @param host {@link String} Representa o DNS do host a connectar
	 * @param porta {@link int} Representa a porta onde está a correr o serviço de Pop3 no
	 * servidor
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public Pop3(String user,String pass,String host,int porta) throws UnknownHostException, IOException {
		this.porta=porta;
		this.user=user;
		this.pass=pass;
		this.host=host;
		this.sock=new Socket(this.host, this.porta);
		this.pwr=new PrintWriter(new OutputStreamWriter(this.sock.getOutputStream()));
		this.br=new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
	}
	/**
	 * Overload do construtor. Este não precisa do parâmetro porta, assume-se a porta 110,
	 * defeito na grande maioria dos servidores
	 * @param user {@link String} Representa o username da conta
	 * @param pass {@link String} Representa a password associada à conta
	 * @param host {@link String} Dns do host a connectar
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public Pop3(String user,String pass,String host) throws UnknownHostException, IOException{
		this.porta=110;
		this.host=host;
		this.user=user;
		this.pass=pass;
		this.sock=new Socket(this.host, this.porta);
		this.pwr=new PrintWriter(new OutputStreamWriter(this.sock.getOutputStream()));
		this.br=new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
	}
	
	private void login(){
		
	}
	
	private void quit(){
		
	}
}
