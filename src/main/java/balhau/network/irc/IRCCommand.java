package balhau.network.irc;

/**
 * Enumerado que representa os comandos existentes no protocolo IRC
 * @author balhau
 *
 */
public enum IRCCommand {
		NOTICE("NOTICE"),
		PING("PING"),
		JOIN("JOIN"),
		MODE("MODE"),
		PRIVMSG("PRIVMSG"),
		NICK("NICK"),
		USER("USER"),
		WHO("WHO"),
		ADMIN("ADMIN"),
		AWAY("AWAY"),
		CONNECT("CONNECT"),
		DIE("DIE"),
		ERROR("ERROR"),
		INFO("INFO"),
		INVITE("INVITE"),
		ISON("ISON"),
		KICK("KICK"),
		KILL("KILL"),
		LINKS("LINKS"),
		LIST("LIST"),
		LUSERS("LUSERS"),
		MOTD("MOTD"),
		NAMES("NAMES"),
		OPER("OPER"),
		PART("PART"),
		PASS("PASS"),
		QUIT("QUIT"),
		REHASH("REHASH"),
		RESTART("RESTART"),
		SERVICE("SERVICE"),
		SERVLIST("SERVLIST"),
		SERVER("SERVER"),
		SQUERY("SQUERY"),
		SQUIT("SQUIT"),
		STATS("STATS"),
		SUMMON("SUMMON"),
		TIME("TIME"),
		TOPIC("TOPIC"),
		TRACE("TRACE"),
		USERHOST("USERHOST"),
		USERS("USERS"),
		VERSION("VERSION"),
		WALLOPS("WALLOPS"),
		WHOIS("WHOIS"),
		WHOWAS("WHOWAS"),
		NOT_FOUND("NOT_FOUND"),
		PONG("PONG")
;
	private String _command;
	
	private IRCCommand(String command){
		this._command=command;
	}
	
	public String command(){
		return _command;
	}
	/**
	 * Devolve a estrutura enum a partir da string representante do comando
	 * @param command {@link String} Representação do comando
	 * @return {@link IRCCommand} Enumerado respectivo
	 */
	public static IRCCommand fromString(String command){
		IRCCommand[] val=values();
		for(int i=0;i<val.length;i++){
			if(command.trim()==val[i]._command)
				return val[i];
		}
		return null;
	}
}
