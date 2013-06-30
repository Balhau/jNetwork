package balhau.network.upnp;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import balhau.utils.Log;

/**
 * Classe utilizada para armazenar a informação proveniente de uma resposta em xml segundo o protocolo SSDP.
 * Todas as propriedades desta classe são públicas.<br><br>
 * <b>Referências</b><br>
 * <a href="http://www.upnp.org/specs/arch/UPnP-arch-DeviceArchitecture-v1.0-20081015.pdf">UPnP Device Arquitecture v1.0 2008-10-15</a>
 * @author balhau
 *
 */
public class GatewayDevice {
	
	public final static String DEVICE_TYPE="devicetype";
	public final static String FRIENDLY_NAME="friendlyname";
	public final static String MANUFACTURER="manufacturer";
	public final static String MANUFACTURER_URL="manufacturerurl";
	public final static String MODEL_DESCRIPTION="modeldescription";
	public final static String MODEL_NUMBER="modelnumber";
	public final static String MODEL_NAME="modelname";
	public final static String MODEL_URL="modelurl";
	public final static String SERIAL_NUMBER="serialnumber";
	public final static String UDN="udn";
	public final static String PRESENTATION_URL="presentationurl"; 
	public final static String SERVICE_LIST="servicelist";
	public final static String DEVICE_LIST="devicelist";
	
	public String DeviceType;
	public String FriendlyName;
	public String Manufacturer;
	public String ManufacturerURL;
	public String ModelDescription;
	public String ModelName;
	public String ModelNumber;
	public String ModelURL;
	public String SerialNumber;
	public String Udn;
	public String PresentationURL;
	public ArrayList<GDService> lServices;
	public ArrayList<GatewayDevice> lDevices;
	
	/**
	 * Construtor defeito
	 */
	public GatewayDevice(){}
	
	/**
	 * Construtor do Objecto com passagem dos parâmetros
	 * @param DeviceType {@link String}
	 * @param FriendlyName {@link String}
	 * @param Manufacturer {@link String}
	 * @param ManufacturerURL {@link String}
	 * @param ModelDescription {@link String}
	 * @param ModelName {@link String}
	 * @param ModelNumber {@link String}
	 * @param ModelURL {@link String}
	 * @param SerialNumber {@link String}
	 * @param UDN {@link String}
	 * @param PresentationURL {@link String}
	 */
	public GatewayDevice(
			String DeviceType,String FriendlyName,String Manufacturer,
			String ManufacturerURL,String ModelDescription,String ModelName,
			String ModelNumber,String ModelURL,String SerialNumber,
			String UDN,String PresentationURL
		){
		this.DeviceType=DeviceType;this.FriendlyName=FriendlyName;this.Manufacturer=Manufacturer;
		this.ManufacturerURL=ManufacturerURL;this.ModelDescription=ModelDescription;this.ModelName=ModelName;
		this.ModelNumber=ModelNumber;this.ModelURL=ModelURL;this.SerialNumber=SerialNumber;this.Udn=UDN;
		this.PresentationURL=PresentationURL;
		lServices=new ArrayList<GDService>();
		lDevices=new ArrayList<GatewayDevice>();
	}
	/**
	 * Método que atribui a uma das propriedades do objecto o valor presente no {@link Node} passado como parâmetro da função
	 * @param node {@link Node} 
	 */
	public void setVal(Node node){
		String nval=node.getFirstChild().getNodeValue();
		if(nval==null)
			return;
		String val=node.getNodeName().toLowerCase().trim();
		if(val.equals(DEVICE_TYPE))
			this.DeviceType=nval;
		if(val.equals(FRIENDLY_NAME))
			this.FriendlyName=nval;
		if(val.equals(MANUFACTURER))
			this.Manufacturer=nval;
		if(val.equals(MANUFACTURER_URL))
			this.ManufacturerURL=nval;
		if(val.equals(MODEL_DESCRIPTION))
			this.ModelDescription=nval;
		if(val.equals(MODEL_NAME))
			this.ModelName=nval;
		if(val.equals(MODEL_NUMBER))
			this.ModelNumber=nval;
		if(val.equals(MODEL_URL))
			this.ModelURL=nval;
		if(val.equals(SERIAL_NUMBER))
			this.SerialNumber=nval;
		if(val.equals(UDN))
			this.Udn=nval;
		if(val.equals(PRESENTATION_URL))
			this.PresentationURL=nval;
		if(val.equals(SERVICE_LIST))
			this.lServices=parseServices(node);
		if(val.equals(DEVICE_LIST))
			this.lDevices=parseNodes(node);
	}
	
	private static ArrayList<GDService> parseServices(Node node){
		ArrayList<GDService> ls=new ArrayList<GDService>();
		ArrayList<Node> lnode=prepareNodeList(node.getChildNodes());
		int len=lnode.size();
		Node aux;
		GDService gds;
		for(int i=0;i<len;i++){
			aux=lnode.get(i);
			gds=GDService.fromXMLNode(aux);
			if(gds!=null)
				Log.log(gds.toString());
			ls.add(gds);
		}
		return ls;
	}
	
	public static ArrayList<Node> prepareNodeList(NodeList nList){
		int len=nList.getLength();
		ArrayList<Node> lst=new ArrayList<Node>();
		Node aux;
		for(int i=0;i<len;i++){
			aux=nList.item(i);
			if(aux.getNodeType()==Node.TEXT_NODE)
				if(aux.getNodeValue().trim().length()==0)
					continue;
			lst.add(aux);
		}
		return lst;
	}
	/**
	 * Método utilitário que devolve as chaves e propriedades do objecto para facilitar iterações
	 * @return {@link Object[][]}
	 */
	private Object[][] KeyVals(){
		return new Object[][]{
				{DEVICE_TYPE,DeviceType},
				{FRIENDLY_NAME,FriendlyName},
				{MANUFACTURER,Manufacturer},
				{MANUFACTURER_URL,ManufacturerURL},
				{MODEL_DESCRIPTION,ModelDescription},
				{MODEL_NAME,ModelName},
				{MODEL_NUMBER,ModelNumber},
				{MODEL_URL,ModelURL},
				{SERIAL_NUMBER,SerialNumber},
				{UDN,Udn},
				{PRESENTATION_URL,PresentationURL}
		};
	}
	
	public String toString(){
		String s=":\t";
		String n="\n";
		StringBuilder sb=new StringBuilder();
		Object[][] kvals=KeyVals();
		Object[] aux;
		int len=kvals.length;
		sb.append("-------------------------------------------------------------------------"+n);
		for(int i=0;i<len;i++){
			aux=kvals[i];
			sb.append((String)aux[0]+s+(String)aux[1]+n);
		}
		sb.append("-------------------------------------------------------------------------"+n);
		return sb.toString();
	}
	
	private static ArrayList<GatewayDevice> parseNodes(Node devNode){
		ArrayList<Node> nL=prepareNodeList(devNode.getChildNodes());
		ArrayList<GatewayDevice> devs=new ArrayList<GatewayDevice>();
		int l=nL.size();
		for(int i=0;i<l;i++){
			devs.add(parseNode(nL.get(i)));
		}
		return devs;
	}
	
	private static GatewayDevice parseNode(Node devNode){
		NodeList nl=devNode.getChildNodes();
		ArrayList<Node> nList=prepareNodeList(nl);
		GatewayDevice gd=new GatewayDevice();
		int len=nList.size();
		for(int i=0;i<len;i++){
			gd.setVal(nList.get(i));
		}
		Log.log(gd.toString());
		return gd;
	}
	
	public static GatewayDevice deviceFromURL(Node root) throws ParserConfigurationException, SAXException, IOException{
		return parseNode(root); 
	}
}
