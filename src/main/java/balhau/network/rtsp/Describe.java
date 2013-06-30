package balhau.network.rtsp;

import balhau.network.Message;
import balhau.network.types.NetworkAgent;
/**
 * Implementa a mensagem do protocolo RTSP para o comando DESCRIBE
 * @author balhau
 *
 */
public class Describe extends RtspBase{
	
	public Describe(){
		super();
		command=RtspCommand.DESCRIBE;
	}
	
	public String getMessage(){
		StringBuilder message=new StringBuilder();
		if(netpoint==NetworkAgent.CLIENT){//invocação pelo cliente do método Describe
			message.append(getHeaderClientMessage());
			message.append(getCSeqMessage());
			message.append(getAccept());
		}
		else{//resposta pelo servidor ao método describe
			message.append(getHeaderServerMessage());
			message.append(getCSeqMessage());
			message.append(getGMTDate());
			message.append(getContentType());
			message.append(getContentLength());
			message.append(Message.CRLF);
			message.append(sdpmessage.getSdpMessage());
		}
		return message.toString();
	}
}
