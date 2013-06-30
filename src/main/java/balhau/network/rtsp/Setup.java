package balhau.network.rtsp;

import balhau.network.types.NetworkAgent;
/**
 * Implementa a mensagem do protocolo RTSP para o comando SETUP
 * @author balhau
 *
 */
public class Setup extends RtspBase{
	public Setup() {
	 	super();
		command=RtspCommand.SETUP;
	}
	
	public String getMessage(){
		StringBuilder message=new StringBuilder();
		if(netpoint==NetworkAgent.CLIENT){//mensagem de cliente
			message.append(getHeaderClientMessage());
			message.append(getCSeqMessage());
			message.append(getTransportMessage(netpoint));
		}
		else{//Mensagem servidor
			message.append(getHeaderServerMessage());
			message.append(getCSeqMessage());
			message.append(getGMTDate());
			message.append(getSessionMessage());
			message.append(getTransportMessage(netpoint));
		}
		return message.toString();
	}
}
