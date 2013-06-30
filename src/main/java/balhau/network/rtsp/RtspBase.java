package balhau.network.rtsp;

import java.util.Date;


import balhau.network.Message;
import balhau.network.sdp.MediaSDP;
import balhau.network.types.NetworkAgent;

/**
 * Classe base para o protocolo de RTSP (RFC 2236)
 * @author balhau
 *
 */
public abstract class RtspBase {
	protected RtspStatusCode responseCode;
	public static String version="RTSP/1.0";
	protected MediaSDP sdpmessage;
	protected RtspCommand command;
	protected String command_param;
	protected RtspCommand avaiable_commands[];
	protected SMTPE start_time;
	protected SMTPE end_time;
	protected int cseq;
	protected int client_rtp;
	protected int client_rtcp;
	protected int server_rtp;
	protected int server_rtcp;
	protected int content_length;
	protected Date data;
	protected int session;
	protected NetworkAgent netpoint;
	protected String contentType;//de momento o protocolo fica a funcionar com a apresentação de media no formato SDP
	
	public RtspBase() {
		avaiable_commands=new RtspCommand[]{RtspCommand.DESCRIBE,RtspCommand.SETUP,
				RtspCommand.PLAY,RtspCommand.TEARDOWN};
		data=new Date();
		start_time=new SMTPE(-1, -1, -1, -1, -1);
		end_time=new SMTPE(-1, -1, -1, -1, -1);
		contentType="application/sdp";
	}
	
	public void setNetPoint(NetworkAgent netsource){
		this.netpoint=netsource;
	}
	
	public void setStatusCode(RtspStatusCode code){
		responseCode=code;
	}
	
	public void setDate(Date data){
		this.data=data;
	}
	
	public void setCSeq(int cseq){
		this.cseq=cseq;
	}
	
	protected String getCSeqMessage(){
		return "CSeq: "+cseq+Message.CRLF;
	}
	
	protected String getHeaderClientMessage(){
		return command.getCommand()+" "+command_param+" "+version+Message.CRLF;
	}
	
	protected String getContentLength(){
		return "Content-Length: "+sdpmessage.getSdpMessage().length()+Message.CRLF;
	}
	
	protected String getAccept(){
		return "Accept: "+contentType;
	}
	
	protected String getHeaderServerMessage(){
		return version+" "+responseCode.getStatusCode()+" "+responseCode.getCodeMessage()+Message.CRLF;
	}
	
	@SuppressWarnings("deprecation")
	protected String getGMTDate(){
		return "Date: "+data.toGMTString()+Message.CRLF;
	}
	
	protected String getTransportMessage(NetworkAgent netpoint){
		String transport="Transport: RTP/AVP;unicast;client_port="+client_rtp+"-"+client_rtcp;
		if(netpoint==NetworkAgent.CLIENT){
			transport+=Message.CRLF;
		}else{
			transport+=";server_port="+server_rtp+"-"+server_rtcp+Message.CRLF;
		}
		return transport;
	}
	
	protected String getContentType(){
		return "Content-Type: "+contentType+Message.CRLF;
	}
	
	protected String getSessionMessage(){
		return "Session: "+session+Message.CRLF;
	}
	
	protected String getRangeMessage(){
		String message="Range: ";
		if(start_time.getSMTPEString().equals(""))
			return "";
		message+=start_time.getSMTPEString()+"-"+end_time.getSMTPEString()+Message.CRLF;
		return message;
	}
	
	public void setSessionId(int session){
		this.session=session;
	}
	
	public void setAvaiableCommands(RtspCommand[] commandosDisponiveis){
		this.avaiable_commands=commandosDisponiveis;
	}
	
	public void setCommandParam(String param){
		this.command_param=param;
	}
	
	public void setClientPorts(int rtp,int rtcp){
		this.client_rtp=rtp;
		this.client_rtcp=rtcp;
	}
	
	public void setServerPorts(int rtp,int rtcp){
		this.server_rtp=rtp;
		this.server_rtcp=rtcp;
	}
	
	public void setRelativeTimeStamp(SMTPE start,SMTPE end){
		start_time=start;
		end_time=end;
	}
	
	public void setSDPMessage(MediaSDP sdpmessage){
		this.sdpmessage=sdpmessage;
	}
	
	public void parseMessage(String rtspmessage){
		
	}
	
	public String getMessage(){
		return "";
	}
}
