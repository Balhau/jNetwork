package balhau.network.rtsp;

import balhau.network.types.NetworkAgent;
/**
 * Classe que implementa o comando TEARDOWN do protoco RTSP
 * @author balhau
 *
 */
public class Teardown extends RtspBase{
	public Teardown() {
		super();
		command=RtspCommand.TEARDOWN;
	}
	
	public String getMessage(){
		StringBuilder message=new StringBuilder();
		if(netpoint==NetworkAgent.CLIENT){
			message.append(getHeaderClientMessage());
			message.append(getCSeqMessage());
			message.append(getSessionMessage());
		}else{
			message.append(getHeaderServerMessage());
			message.append(getCSeqMessage());
		}
		return message.toString();
	}
}
