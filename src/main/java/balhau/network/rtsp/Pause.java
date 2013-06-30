package balhau.network.rtsp;

import balhau.network.types.NetworkAgent;

/**
 * Classe que implementa o comando PAUSE do protoco RTSP
 * @author balhau
 *
 */
public class Pause extends RtspBase{
	public Pause() {
		super();
		command=RtspCommand.PAUSE;
	}
	public String getMessage(){
		StringBuilder message=new StringBuilder();
		if(netpoint==NetworkAgent.CLIENT){
			message.append(getHeaderClientMessage());
			message.append(getCSeqMessage());
			message.append(getSessionMessage());
		}
		else{
			message.append(getHeaderServerMessage());
			message.append(getCSeqMessage());
			message.append(getGMTDate());
		}
		return message.toString();
	}
}
