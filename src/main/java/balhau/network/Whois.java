package balhau.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import balhau.metastruct.Lista;
import balhau.network.base.AbstractClient;


/**
 * Classe que efectua uma consulta de informação sobre um determinado domínio ou ip
 * Esta classe utiliza o protocol WHOIS especificado no rfc3912
 * @author balhau
 *
 */
public class Whois extends AbstractClient{
	/**
	 * Construtor do objecto
	 * @param host {@link String} Nome do host a conectar
	 * @param porta {@link int} Número da porta a conectar
	 */
	private static final long serialVersionUID = 124212412512553623L;
	public static String wservfile="/home/balhau/workspace/jproj/bin/app/whois-servers.txt";
	private WhoisEntry wservlist[];
	public Whois() throws IOException {
		super();
		getWhoisServerList(wservfile);
	}
	/**
	 * Método que devolve um array com os vários servidores para os vários top level domain name
	 * @param filePath Caminho para o ficheiro com a informação dos servidores whois
	 * @return {@link WhoisEntry[]} Array de entradas com informação sobre os servidores whois
	 * @throws IOException 
	 */
	private void getWhoisServerList(String filePath) throws IOException{
		Lista<WhoisEntry> listWhois=new Lista<WhoisEntry>();
		BufferedReader br=new BufferedReader(new FileReader(new File(filePath)));
		String line;
		String spl[];
		while((line=br.readLine())!=null){
			spl=line.split(" ");
			listWhois.addValue(new WhoisEntry(spl[0],spl[1]));
		}
		wservlist=listWhois.toArray();
	}
	
	public String whoisServerFromDomainName(String domainName){
		String tldname;
		String spl[]=domainName.split("\\.");
		tldname=spl[spl.length-1];
		for(int i=0;i<wservlist.length;i++){
			if(tldname.toLowerCase().equals(wservlist[i].topLevelDomainName)){
				return wservlist[i].whoisHost;
			}
		}
		return "";
	}
	
	public void printWhoisServerInfo() throws IOException{
		if(wservlist==null)
			getWhoisServerList(wservfile);
		System.out.println("_________________________________________________________________________________");
		System.out.println("Server List Info:");
		for(int i=0;i<wservlist.length;i++){
			System.out.println("TLDN: "+wservlist[i].topLevelDomainName+"\t"+"Whois Server: "+wservlist[i].whoisHost);
		}
		System.out.println("_________________________________________________________________________________");
	}
	/**
	 * Método que devolve informação whois sobre um determinado domínio
	 * @param domain {@link String} Nome do domínio
	 * @return {@link String} Informação whois sobre o domínio. 
	 * @throws IOException 
	 */
	public String getWhois(String domain) throws IOException{
		StringBuilder sb=new StringBuilder();
		_host=whoisServerFromDomainName(domain);
		_porta=43;
		connect();
		msg2h(domain+Message.CRLF);
		sb.append(getAll());
		disconnect();
		return sb.toString();
	}
}
