package balhau.network.rtsp;

/**
 * Enumerado que representa os comandos presentes no protocolo RTSP (RFC 2326)
 * @author balhau
 *
 */
public enum RtspCommand {
	DESCRIBE("DESCRIBE"),
	ANNOUNCE("ANNOUNCE"),
	GET_PARAMETER("GET_PARAMETER"),
	OPTIONS("OPTIONS"),
	
	PAUSE("PAUSE"),
	PLAY("PLAY"),
	RECORD("RECORD"),
	REDIRECT("REDIRECT"),
	SETUP("SETUP"),
	SET_PARAMETER("SET_PARAMETER"),
	TEARDOWN("TEARDOWN")
	;
	
	private String commandName;
	private RtspCommand(String commandName) {
		this.commandName=commandName;
	}
	/**
	 * Método que devolve o nome do comando.
	 * @return {@link String} Nome do comando
	 */
	public String getCommand(){
		return this.commandName;
	}
	
	public static RtspBase commandObject(String commandName){
		if(commandName.equals(RtspCommand.DESCRIBE.getCommand()))
			return new Describe();
		if(commandName.equals(RtspCommand.SETUP.getCommand()))
			return new Setup();
		if(commandName.equals(RtspCommand.PLAY.getCommand()))
			return new Play();
		if(commandName.equals(RtspCommand.PAUSE.getCommand()))
			return new Pause();
		if(commandName.equals(RtspCommand.ANNOUNCE.getCommand()))
			return new Announce();
		return new Teardown(); 
	}
}
