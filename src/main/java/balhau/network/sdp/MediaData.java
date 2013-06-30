package balhau.network.sdp;

import balhau.network.rtp.RtpPayloadType;
/**
 * Classe auxiliar para a construção do {@link MediaSDP}
 * @author balhau
 *
 */
public class MediaData {
	public RtpPayloadType MediaType;
	public int Porta;
	
	public MediaData(RtpPayloadType mediaType,int porta){
		this.MediaType=mediaType;
		this.Porta=porta;
	}
}
