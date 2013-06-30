package balhau.network.upnp;

import balhau.metastruct.Dicionario;
import balhau.metastruct.Par;
/**
 * Class com um conjunto de funções utilitárias para a implementação de funcionalidades segundo o protocolo UpNp<br><br>
 * <b>Referências:</b><br>
 * <a href="http://www.w3.org/TR/2007/REC-soap12-part0-20070427/">W3C SOAP specification</a><br>
 * <a href="http://www.w3schools.com/soap/soap_example.asp">Exemplo de mensagem SOAP no W3Schools</a><br>
 * @author balhau
 *
 */
public class UpnpUtils {
	
	public static String postSOAPCommand(String url,String servico,String commando,Dicionario<String,String> argumentos){
		Dicionario<String, String> stDic=new Dicionario<String, String>();
		StringBuilder sb=new StringBuilder();
		/**
		 * <SOAP-ENV:Envelope
		  xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
		  SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
		   <SOAP-ENV:Body>
		       <m:GetLastTradePrice xmlns:m="Some-URI">
		           <symbol>DIS</symbol>
		       </m:GetLastTradePrice>
		   </SOAP-ENV:Body>
		</SOAP-ENV:Envelope>
		 */
		
		sb.append(
				"<?xml version=\"1.0\"?>\r\n"+
				"<SOAP-ENV:Envelope\r\n"+
				"xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\r\n"+
				"SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\r\n"+
				"<SOAP-ENV:Body>\r\n"
				);
		sb.append("<m:"+commando+" xmlns:m=\""+servico+"\">\r\n");
		int argLen=argumentos.getNumElementos();
		Par<String,String> item;
		for(int i=0;i<argLen;i++){
			item=argumentos.getItem(i);
			sb.append("<"+item.chave+">"+item.valor+"</"+item.chave+">");
		}
		sb.append("</m:"+commando+">\r\n");
		sb.append("</SOAP-ENV:Body>\r\n");
		sb.append("</SOAP-ENV:Envelop>\r\n");
		return sb.toString();
	}
}
