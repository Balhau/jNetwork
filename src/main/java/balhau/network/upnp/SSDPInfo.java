package balhau.network.upnp;

/**
 * Classe que representa a informação enviada segundo o protocolo SSDP de forma estruturada. 
 * @author balhau
 *
 */
public class SSDPInfo {
	public String Location;
	public String Server;
	public String ST;
	public String Usn;
	
	public SSDPInfo(String location,String server,String st,String usn){
		this.Location=location;this.Server=server;
		this.ST=st;this.Usn=usn;
	}
	
	public String toString(){
		String out="----------------------------------\n";
		out+="Location: "+Location+"\n";
		out+="Server: "+Server+"\n";
		out+="St: "+ST+"\n";
		out+="Usn: "+Usn+"\n";
		out+="----------------------------------\n";
		return out;
	}
	
	public SSDPInfo(){}
}
