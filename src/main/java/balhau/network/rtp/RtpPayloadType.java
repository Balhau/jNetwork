package balhau.network.rtp;

import balhau.network.Message;

/**
 * Enumerado que representa o tipo de dados transmitidos no protocolo RTP
 * Enumerado construido com base no RFC3551
 * @author balhau
 *
 */
public enum RtpPayloadType {
	//Audio Media
	PCMU("PCMU","A",8000,1,0),
	GSM("GSM","A",8000,1,3),
	G723("G723","A",8000,1,4),
	DVI4_8("DVI4","A",8000,1,5),
	GVI4_16("GVI4","A",16000,1,6),
	LPC("LPC","A",8000,1,7),
	PCMA("PCMA","A",8000,1,8),
	G722("G722","A",8000,1,9),
	L16_2("L16","A",44100,2,10),
	L16_1("L16","A",44100,1,11),
	QCELP("QCELP","A",8000,1,12),
	CN("CN","A",8000,1,13),
	MPA("MPA","A",90000,1,14),
	G728("G728","A",8000,1,15),
	DVI4_11("DVI4","A",11025,1,16),
	DVI4_22("DVI4","A",22050,1,17),
	G729("G729","A",8000,1,18),
	//Video media
	CELB("CelB","V",90000,0,25),
	JPEG("JPEG","V",90000,0,26),
	NV("nv","V",90000,0,28),
	H261("H261","V",90000,0,31),
	MPV("MPV","V",90000,0,32),
	MP2T("MP2T","V",90000,0,33),
	M263("M263","V",90000,0,34),
	//Formato dinamico
	DYNAMIC("DYNAMIC","?",-1,-1,-1)
	;
	
	private int clockRate;
	private int channels;
	private int payloadType;
	private String encname;
	private String mediaType;
	
	RtpPayloadType(String encodingName,String mediaType,int clockRate,int channels,int payloadType) {
		this.encname=encodingName;
		this.mediaType=mediaType;
		this.clockRate=clockRate;
		this.channels=channels;
		this.payloadType=payloadType;
	}
	/**
	 * Devolve a informação relativa ao número de canais de som do codec
	 * @return {@link int} Numero de canais de som
	 */
	public int getChannels(){
		return this.channels;
	}
	
	/**
	 * Devolve a frequência para o processamento do codec
	 * @return {@link int}
	 */
	public int getclockRate(){
		return this.clockRate;
	}
	/**
	 * Devolve o tipo de conteúdo multimédia
	 * @return {@link String}
	 */
	public String getMediaType(){
		return this.mediaType;
	}
	/**
	 * Devolve o nome da codificação
	 * @return {@link String}
	 */
	public String getEncodingName(){
		return this.encname;
	}
	
	/**
	 * Devolve o tipo dos dados 
	 * @return {@link int} Constante numérica
	 */
	public int getPayloadType(){
		return this.payloadType;
	}
	/**
	 * Devolve a informação sob o protocolo SDP
	 * @param porta {@link int} Numero da porta 
	 * @return {@link String} 
	 */
	public String getSDPPayload(int porta){
		if(this==DYNAMIC)
			return "";
		String out="";
		String type;
		if(this.mediaType=="A")
			type="audio";
		else
			type="video";
		out+=type+" "+porta+" RTP/AVP "+this.payloadType+Message.CRLF;
		out+="rtpmap:"+this.payloadType+" "+this.encname+"/"+this.clockRate+Message.CRLF;
		return out;
	}
	/**
	 * Overload do método toString para apresentar a informação, amigavelmente, da informação presente no objecto
	 */
	public String toString(){
		String out;
		out="Encoding Name: "+this.encname+Message.EOL;
		out+="Media Type: "+this.mediaType+Message.EOL;
		out+="Clock Rate: "+this.clockRate+Message.EOL;
		out+="Channels: "+this.channels+Message.EOL;
		out+="Payload Type: "+this.payloadType+Message.EOL;
		return out;
	}
}
