/**
 * Pacote com rotinas necessárias para a implementação do protocolo IRC
 */
package balhau.network.irc;

import java.io.IOException;

import balhau.network.Message;

/**
 * CLasse que implementa as funcionalidades do protocolo IRC para o cliente<br/><br/>
 * <b>Referências:</b><br/>
 * <a href="http://www.irchelp.org/irchelp/rfc/rfc.html">Internet Relay Chat Protocol</a>
 * @author balhau
 */
public class IRCCCient extends IRCBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _username;
	private String _nickname;
	@SuppressWarnings("unused")
	private String _pass;
	private String _hostname;
	private String _servername;
	private String _realname;
	private IEventClient _event;
	/**
	 * Construtor do objecto com especificação do endereço do servidor e porta associada
	 * @param host {@link String} Endereço do servidor
	 * @param porta {@link int} Numero da porta onde o servidor actua
	 * @param eventHandler {@link IEventClient} Objecto que representa as funções de gestão de eventos
	 */
	public IRCCCient(String hostname,String servername,int porta,IEventClient eventHandler){
		super(hostname,porta,true);
		_hostname=hostname;
		_servername=servername;
		_realname="";
		_username="";
		_nickname="";
		_event=eventHandler;
		_pass="";
		connect();
	}
	/**
	 * Setter para os parâmetros de autenticação no servidor de IRC
	 * @param login {@link String} Nome do utilizador
	 * @param pass {@link String} Password do utilizador
	 */
	public void setUserInfo(String nickname,String username,String realname,String pass){
		_username=username;
		_nickname=nickname;
		_realname=realname;
		_pass=pass;
	}
	
	public void joinCanal(String canal) throws IOException{
		msg2h("JOIN #"+canal+Message.CRLF);
		msg2h("MODE #"+canal+Message.CRLF);
		msg2h("WHO #"+canal+Message.CRLF);
	}
	
	public void sendToCanal(String canal, String message) throws IOException{
		msg2h("PRIVMSG #"+canal+" :"+message+Message.CRLF);
	}
	
	public void sendToUser(String user,String message) throws IOException{
		msg2h("PRIVMSG "+user+" :"+message+Message.CRLF);
	}
	
	public void hiServer() throws IOException{
		msg2h("CAP LS"+Message.CRLF);
	}
	
	public void sendRawMessage(String msg){
		msg2h(msg);
	}
	
	public String getHelloServer() throws IOException{
		return getAll();
	}
	
	/**
	 * Método que envia para o servidor IRC informação sobre utilizador
	 * @param username {@link String} Nome de utilizador
	 * @param hostname {@link String} Nome do host
	 * @param servername {@link String} Nome do servidor
	 * @param realname {@link String} Nome real do utilizador
	 * @return {@link boolean} Não tem grande significado.
	 * @throws IOException
	 */
	public boolean sendUser(String username,String hostname,String servername,String realname) throws IOException{
		msg2h("USER "+username+" "+hostname+" "+servername+" :"+realname+Message.CRLF);
		return true;
	}
	/**
	 * Overload da função com o mesmo nome mas com especificação dos parâmetros como sendo as respectivas
	 * propriedades do objecto
	 * @return {@link boolean} Sem grande significado
	 * @throws IOException 
	 */
	public boolean sendUser() throws IOException{
		return sendUser(_username,_hostname,_servername,_realname);
	}
	
	public IRCCanal getCanalInfo(String canal) throws IOException{
		msg2h("WHO #"+canal+Message.CRLF);
		IRCMessage imsg;
		IRCCanal cinfo=new IRCCanal(canal);
		String msg;
		waitForServer();
		while(_br.ready() && (msg=_br.readLine())!=null){
			imsg=IRCMessage.parseMessage(msg);
			if(imsg.getCommand().equals("352")){
				cinfo.addUser(IRCUser.fromIRCMessage(imsg));
			}
		}
		return cinfo;
	}
	
	public boolean sendNick(String nick) throws IOException{
		msg2h(IRCCommand.NICK.command()+" "+nick+Message.CRLF);
		return true;
	}
	
	public boolean sendNick() throws IOException{
		return sendNick(_nickname);
	}
	
	public void getAllServerMessages() throws IOException{
		String aux;
		IRCMessage imsg;
		while(_br.ready()){
			if((aux=_br.readLine())==null)
				return;
			imsg=IRCMessage.parseMessage(aux);
			_event.onServerMessage(imsg,this);
		}
	}
	
	public void getMessage() throws IOException{
		IRCMessage imsg;
		String aux;
		if((aux=_br.readLine())==null)
			return;
		imsg=IRCMessage.parseMessage(aux);
		_event.onServerMessage(imsg, this);
	}
	
	public IRCMessage getServerMessage() throws IOException{
		IRCMessage imsg=IRCMessage.parseMessage(getLine());
		_event.onServerMessage(imsg,this);
		return imsg;
	}
	
	public void quit() throws IOException{
		msg2h("quit");
		_event.onQuit(this);
		_sock.close();
		
	}
}
