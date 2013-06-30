package balhau.network.rtsp;

import balhau.network.Message;
import balhau.network.types.NetworkAgent;

public class Announce extends RtspBase{
	public Announce() {
		super();
		command=RtspCommand.ANNOUNCE;
	}
	
	public String getMessage(){
		StringBuilder message=new StringBuilder();
		if(netpoint==NetworkAgent.CLIENT){//mensagem de cliente
			message.append(getHeaderClientMessage());
			message.append(getCSeqMessage());
			message.append(getGMTDate());
			message.append(getSessionMessage());
			message.append(getContentType());
			message.append(getContentLength());
			message.append(Message.CRLF);
			message.append(sdpmessage.getSdpMessage());
		}
		else{//mensagem de servidor
			message.append(getHeaderServerMessage());
			message.append(getCSeqMessage());
		}
		return message.toString();
	}
}
