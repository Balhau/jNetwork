package balhau.network.irc;

import balhau.network.Message;

/**
 * Classe que representa a informação de um utilizador no protocolo IRC
 * @author balhau
 *
 */
public class IRCUser {
	private String Name;
	private String Nick;
	private String Host;
	private String Server;
	private String Rname;
	private String Opcount;
	
	
	public String getName() {
		return Name;
	}


	public String getNick() {
		return Nick;
	}


	public String getHost() {
		return Host;
	}


	public String getServer() {
		return Server;
	}


	public String getRname() {
		return Rname;
	}


	public String getOpcount() {
		return Opcount;
	}
	/**
	 * Construtor do objecto
	 * @param name {@link String} Nome do utilizador
	 * @param nick {@link String} Nick do utilizador
	 * @param host {@link String} Hostname info
	 * @param server {@link String} Server info
	 * @param rname {@link String} Nome real do utilizador
	 * @param opcount {@link String} Informações de operador
	 */
	public IRCUser(String name,String nick,String host,String server,String rname,String opcount){
		this.Name=name;
		this.Nick=nick;
		this.Host=host;
		this.Server=server;
		this.Rname=rname;
		this.Opcount=opcount;
	}
	
	public static IRCUser fromIRCMessage(IRCMessage imsg){
		IRCUser iusr;
		String msg=imsg.getDestino();
		String props[]=msg.split(" ");
		iusr=new IRCUser(props[0], props[5], props[4], props[3], props[9], props[6]+" "+props[7]+" "+props[8]);
		return iusr;
	}

	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("User Info:"+Message.EOL);
		sb.append("Name: "+Name+Message.EOL);
		sb.append("Nick: "+Nick+Message.EOL);
		sb.append("Real Name: "+Rname+Message.EOL);
		sb.append("Host: "+Host+Message.EOL);
		sb.append("Server: "+Server+Message.EOL);
		sb.append("Op Info: "+Opcount+Message.EOL);
		sb.append("User info end"+Message.EOL);
		return sb.toString();
	}
}
