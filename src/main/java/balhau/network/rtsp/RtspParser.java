package balhau.network.rtsp;

import java.text.ParseException;
import java.util.Date;

import balhau.network.Message;
/**
 * Parser para as mensagens do protocolo RTSP
 * @author balhau
 */
public class RtspParser {
	private static String[] gmt_month={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	public static RtspBase parseMessage(String message){
		String[] spl=message.split(Message.CRLF+Message.CRLF);
		String header=spl[0];
		String headerLines[]=header.split(Message.CRLF);
		RtspBase msgObject=RtspCommand.commandObject(headerLines[0]);
		msgObject.setCommandParam(headerLines[1]);
		return msgObject;
	}
	
	@SuppressWarnings("unused")
	private static void parseLineHeader(String line,RtspBase obj) throws ParseException{
		String prop=line.substring(0, line.indexOf(":"));
		String param=line.substring(line.indexOf(":")+1, line.length());
		if(prop.equals("CSeq")){
			obj.cseq=Integer.parseInt(param);
		}
		if(prop.equals("Session")){
			obj.session=Integer.parseInt(param);
		}
		if(prop.equals("Range")){
			parseSMPTETime(param, obj);
		}
		if(prop.equals("Accept")){
			obj.data=parseGMTDate(param);
		}
	}
	
	private static void parseSMPTETime(String time,RtspBase obj){
		String[] spl=time.split("=");
		String[] tmp=spl[1].split(";");
		tmp=tmp[0].split("-");
		obj.start_time=SMTPE.fromString(tmp[0]);
		obj.end_time=SMTPE.fromString(tmp[1]);
	}
	
	@SuppressWarnings("deprecation")
	private static Date parseGMTDate(String date){	
		String[] spl=date.split(" ");
		String[] tim=spl[3].split(":");
		int dia=Integer.parseInt(spl[0]);
		int mes=monthLabelToInt(spl[1]);
		int ano=Integer.parseInt(spl[2]);
		int hora=Integer.parseInt(tim[0]);
		int min=Integer.parseInt(tim[1]);
		int seg=Integer.parseInt(tim[2]);
		return new Date(ano, mes, dia, hora, min, seg);
	}
	
	private static int monthLabelToInt(String label){
		for(int i=0;i<gmt_month.length;i++){
			if(gmt_month[i].equals(label))
				return i;
		}
		return -1;
	}
	
}