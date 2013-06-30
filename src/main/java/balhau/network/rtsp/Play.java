package balhau.network.rtsp;

import balhau.network.types.NetworkAgent;
/**
 * Classe que extende {@link RtspBase} implementando o comando PLAY do protocolo RTSP
 * @author balhau
 *
 */
public class Play extends RtspBase{
	public Play() {
		super();
		command=RtspCommand.PLAY;
	}
	
	public String getMessage(){
		StringBuilder message=new StringBuilder();
		if(netpoint==NetworkAgent.CLIENT){//Mensagem de cliente
			message.append(getHeaderClientMessage());
			message.append(getCSeqMessage());
			message.append(getSessionMessage());
			message.append(getRangeMessage());
		}else{//mensagem de servidor
			message.append(getHeaderServerMessage());
			message.append(getCSeqMessage());
			message.append(getGMTDate());
			message.append(getRangeMessage());
		}
		return message.toString();
	}
}
