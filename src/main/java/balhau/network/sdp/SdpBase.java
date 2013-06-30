package balhau.network.sdp;


import java.util.Date;

import balhau.network.types.IpProtocol;
import balhau.metastruct.Lista;
import balhau.network.Message;

/**
 * Classe que implementa o protocolo Session Description Protocol (SDP, RFC 4566)
 * @author balhau
 *
 */
public abstract class SdpBase {
	protected static final int VERSION=0;
	
	protected String sessionName;
	protected String sessionIdent;
	protected String originName;
	protected long origin_session_id;
	protected long origin_session_version;
	protected String ipValue;
	protected IpProtocol protocol;
	protected Lista<String> sessionInfo;
	protected Lista<String> uriDesc;
	protected Lista<String> emailAddress;
	protected Lista<String> phoneNumber;
	protected Lista<String> conectionInfo;
	protected Lista<String> atributeString;
	protected Lista<String> timeActive;
	protected Lista<String> repeatTime;
	protected Lista<String> mediaTrans;
	protected Lista<String> timeAdjust;
	protected Lista<String> bandwidthInfo;
	protected Lista<String> encKey;
	
	public SdpBase(){
		sessionName="";
		protocol=IpProtocol.IpV4;
		Date dt=new Date();
		origin_session_id=dt.getTime();
		origin_session_version=(long)Math.random();
		origin_session_version=origin_session_version>=0?origin_session_id:-origin_session_id;
		sessionInfo=new Lista<String>();
		uriDesc=new Lista<String>();
		emailAddress=new Lista<String>();
		phoneNumber=new Lista<String>();
		conectionInfo=new Lista<String>();
		atributeString=new Lista<String>();
		timeActive=new Lista<String>();
		repeatTime=new Lista<String>();
		mediaTrans=new Lista<String>();
		timeAdjust=new Lista<String>();
		bandwidthInfo=new Lista<String>();
		encKey=new Lista<String>();
		ipValue="127.0.0.1";
	}
	/**
	 * Configura o parametro "o="
	 * @return {@link String} String com o parâmetro "o="
	 */
	protected void setupOrigin(){
		
		sessionIdent=originName+" "+origin_session_id+" "+origin_session_version+" IN "+protocol.getDesc()+" "+ipValue;
	}
	
	/**
	 * Método que privado responsavel por devolver partes da mensagem sdp para os atributos com mais de um elemento 
	 * @param label String Label associado ao atributo
	 * @param lista {@link Lista} de elementos
	 * @return {@link String}
	 */
	private String getListVals(String label,Lista<String > lista){
		if(lista==null)
			return "";
		if(lista.getNumElementos()==0)
			return "";
		StringBuilder sb=new StringBuilder();
		int len=lista.getNumElementos();
		for(int i=0;i<len;i++){
			if(i>0)
				sb.append(Message.CRLF);
			sb.append(label+"="+lista.getValor(i));
		}
		return sb.toString();
	}
	/**
	 * Método que devolve a mensagem SDP
	 * @return {@link String} Mensagem do protocolo SDP
	 */
	public String getSdpMessage(){
		StringBuilder sb=new StringBuilder();
		sb.append("v="+VERSION+Message.CRLF);
		if(!sessionIdent.equals("")) sb.append("o="+sessionIdent+Message.CRLF);
		if(!sessionName.equals("")) sb.append("s="+sessionName+Message.CRLF);
		sb.append(getListVals("i", sessionInfo));
		sb.append(getListVals("u", uriDesc));
		sb.append(getListVals("e", emailAddress));
		sb.append(getListVals("z", timeAdjust));
		sb.append(getListVals("p", phoneNumber));
		sb.append(getListVals("c", conectionInfo));
		sb.append(getListVals("b", bandwidthInfo));
		sb.append(getListVals("k", encKey));
		sb.append(getListVals("r", repeatTime));
		sb.append(getListVals("m",mediaTrans));
		sb.append(getListVals("a", atributeString));
		return sb.toString();
	}
}
