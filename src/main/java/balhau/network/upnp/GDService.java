package balhau.network.upnp;

import org.w3c.dom.Node;

/**
 * Objecto que representa a informação contida no GatewayDeviceService
 * @author balhau
 *
 */
public class GDService {
	
	public static final String SERVICE_TYPE="servicetype";
	public static final String SERVICE_ID="serviceid";
	public static final String CONTROL_URL="controlurl";
	public static final String EVENT_SUB_URL="eventsuburl";
	public static final String SCPD_URL="scpdurl";
	
	
	public String ServiceType;
	public String ServiceId;
	public String ControlURL;
	public String EventSubURL;
	public String SCPDUrl;
	
	public GDService(){}
	
	private Object[][] keyVals(){
		return new Object[][]{
				{SERVICE_TYPE,ServiceType},
				{SERVICE_ID,ServiceId},
				{CONTROL_URL,ControlURL},
				{EVENT_SUB_URL,EventSubURL},
				{SCPD_URL,SCPDUrl}
		};
	}
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		Object[][] kv=keyVals();
		int len=kv.length;
		String s=":\t";
		String n="\n";
		sb.append("--------------------------------------------------------------------------");
		Object[] a;
		for(int i=0;i<len;i++){
			a=kv[i];
			sb.append((String)a[0]+s+(String)a[1]+n);
		}
		sb.append("--------------------------------------------------------------------------");
		return sb.toString();
	}
	
	public static GDService fromXMLNode(Node node){
		return null;
	}
	
	public GDService(
				String ServiceType,String ServiceId,
				String ControlURL,String EventSubURL,
				String SCPDUrl
			){
		this.ServiceType=ServiceType;this.ServiceId=ServiceId;
		this.ControlURL=ControlURL;this.EventSubURL=EventSubURL;
		this.SCPDUrl=SCPDUrl;
	}
}
