package balhau.network.sdp;

import balhau.network.rtp.RtpPayloadType;
import balhau.network.types.IpProtocol;
import balhau.metastruct.Lista;
import balhau.metastruct.Par;
import balhau.network.Message;

/**
 * Protocolo SDP aplicado à descrição multimédia
 * Utilizado, por exemplo, para a implementação do protocolo RSTP
 * RFC 4566 (SDP), RFC 3551(RTP A/V Profile)
 * @author balhau
 *
 */
public class MediaSDP extends SdpBase{
	/**
	 * Lista com a informação multimedia a transmitir
	 */
	private Lista<MediaData> mediaData;
	
	public MediaSDP(){
		super();//Inicializar o construtor da classe base
		mediaData=new Lista<MediaData>();
	}
	
	public void addMedia(String mediaInfo){
		mediaTrans.addValue(mediaInfo);
	}
	
	public void setIPAddress(String ip){
		ipValue=ip;
	}
	
	public void setSessionIdentifier(String identifier){
		sessionIdent=identifier;
	}
	
	public void addConnectionAddress(String address,IpProtocol protocol){
		conectionInfo.addValue("IN "+protocol.getDesc()+" "+address);
	}
	
	public void setSessionIdentifier(String identifier,IpProtocol protocol){
		sessionIdent=identifier;
		this.protocol=protocol;
	}
	
	public void setSessionName(String name){
		sessionName=name;
	}
	
	public void addMail(String mail){
		emailAddress.addValue(mail);
	}
	
	public void addURI(String uri){
		uriDesc.addValue(uri);
	}
	
	public void addMedia(RtpPayloadType media, int porta){
		mediaData.addValue(new MediaData(media, porta));
	}
	
	private void buildAttribFromMediaTypes(){
		MediaData aux;
		String[] split;
		String str_aux;
		int len=mediaData.getNumElementos();
		for(int i=0;i<len;i++){
			aux=mediaData.getValor(i);
			str_aux=aux.MediaType.getSDPPayload(aux.Porta);
			split=str_aux.split(Message.CRLF);
			mediaTrans.addValue(split[0]);
			atributeString.addValue(split[1]);
		}
	}
	/**
	 * Este método é semelhante ao {@link setSessionIdentifier}, no entanto efectua a construção automática do 
	 * parâmetro "o=" segundo a especificação do protocolo enquanto o {@link setSessionIdentifier} especifica
	 * directamente a propriedade
	 * @param username Nome do utilizador origem
	 */
	public void setupSessionIdentifier(String username){
		originName=username;
		setupOrigin();
		buildAttribFromMediaTypes();
	}
}
