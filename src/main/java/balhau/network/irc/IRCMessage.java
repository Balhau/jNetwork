package balhau.network.irc;

import balhau.utils.StringUtils;


/**
 * Classe que representa a informação da mensagem de IRC numa forma estruturada
 * @author balhau
 *
 */
public class IRCMessage {
	private String Command;
	private String Origem;
	private String Destino;
	
	public String getCommand() {
		return Command;
	}

	public String getOrigem() {
		return Origem;
	}

	public String getDestino() {
		return Destino;
	}

	public IRCMessage(String origem,String command,String destino){
		Command=command;
		Origem=origem;
		Destino=destino;
	}
	
	public static IRCMessage parseMessage(String msg){
		String[] args=StringUtils.splitUntil(msg, ' ', 3);
		if(msg.charAt(0)!=':'){
			return new IRCMessage(null, args[0].trim(), args[1].trim());
		}else{
			return new IRCMessage(args[0].trim(), args[1].trim(), args[2].trim());
		}
	}
	
	public String toString(){
		return "Origem: "+Origem+",Commando: "+Command+",Destino: "+Destino;
	}
	
	@SuppressWarnings("unused")
	private static IRCCommand commandFromMsg(String msg){
		
		return IRCCommand.NOT_FOUND;
	}
}