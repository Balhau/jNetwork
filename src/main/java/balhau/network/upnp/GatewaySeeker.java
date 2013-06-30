package balhau.network.upnp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import balhau.metastruct.Dicionario;
import balhau.metastruct.Lista;
import balhau.network.upnp.eventos.IOnDevice;

/**
 * Classe responsável pela procura de dispositivos de gateway. Utiliza-se o protocolo Simple Service Descovery Protocol
 * (SSDP)<br><br>
 * <b>Referências:</b><br>
 * <a href="https://tools.ietf.org/html/draft-cai-ssdp-v1-03">SSDP Draft specification</a><br>
 * <a href="http://www.iana.org/assignments/multicast-addresses/multicast-addresses.xml">Tabelas de endereçamento multicast
 * disponibilizadas pelo IANA</a><br>
 * </a><br>
 * @author balhau
 *
 */
public class GatewaySeeker {
	public static final int SSDP_PORT=1900;
	public static final String SSDP_MULTICAST_IP="239.255.255.250";
	public static final int SOCKET_TIMEOUT=2500;
	
	public static final String SSDP_SERVICES[]={
		"urn:schemas-upnp-org:device:InternetGatewayDevice:1"
        , "urn:schemas-upnp-org:service:WANIPConnection:1"
        , "urn:schemas-upnp-org:service:WANPPPConnection:1"
	};
	
	private Lista<SSDPInfo> devicesFound;
	private IOnDevice onDevEvent;
	
	public GatewaySeeker(IOnDevice onDevEvent) throws SocketException{
		devicesFound=new Lista<SSDPInfo>();
		this.onDevEvent=onDevEvent;
	}
	
	public void seek(){
		SeekerThread[] threads=new SeekerThread[SSDP_SERVICES.length];
		for(int i=0;i<SSDP_SERVICES.length;i++){
			threads[i]=new SeekerThread(SSDP_SERVICES[i],onDevEvent);
			threads[i].start();
		}
		for(int i=0;i<SSDP_SERVICES.length;i++){
			try {
				threads[i].join();
			} catch (InterruptedException e) {}
		}
	}
	
	private SSDPInfo parseSSDP(String response){
		Dicionario<String, String> dic=new Dicionario<String, String>();
		String[] lines=response.split("\r\n");
		int posI;
		for(int i=0;i<lines.length;i++){
			posI=lines[i].indexOf(":");
			if(posI==-1)
				continue;
			dic.addItem(lines[i].substring(0, posI).toLowerCase(), lines[i].substring(posI+1, lines[i].length()));
		}
		return new SSDPInfo(
				dic.getValueItem("location"),dic.getValueItem("server"),dic.getValueItem("st"),dic.getValueItem("usn")
				);
	}
	
	private class SeekerThread extends Thread{
		private String message;
		private IOnDevice events;
		public SeekerThread(String message,IOnDevice events){
			this.message=message;
			this.events=events;
		}
		
		public void run(){
			DatagramPacket receive_packet=new DatagramPacket(new byte[2048], 2048);
			devicesFound.Limpa();
			try {
				DatagramSocket dgsock=new DatagramSocket();
				InetAddress addr=InetAddress.getByName(SSDP_MULTICAST_IP);
				byte[] s_buff=seekMessage(message).getBytes();
				DatagramPacket send_packet=new DatagramPacket(s_buff, s_buff.length,addr,SSDP_PORT);
				dgsock.setSoTimeout(SOCKET_TIMEOUT);
				dgsock.send(send_packet);
				boolean seeking=true;
				SSDPInfo ssdpResponse;
				while(seeking){
					try{
						dgsock.receive(receive_packet);
						synchronized (devicesFound) {
							ssdpResponse=parseSSDP(new String(receive_packet.getData()));
							devicesFound.addValue(ssdpResponse);
							events.onSsdpReceived(ssdpResponse);
						}	
					}catch (SocketTimeoutException e) {
						seeking=false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private String seekMessage(String serviceType){
		String out="M-SEARCH * HTTP/1.1\r\n";
		out+="HOST: "+SSDP_MULTICAST_IP+"\r\n";
		out+="MAN: \"ssdp:discover\"\r\n";
		out+="MX: 3\r\n";
		out+="ST: "+serviceType+"\r\n";
		out+="\r\n";
		return out;
	}
}
