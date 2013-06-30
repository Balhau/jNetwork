package balhau.network;

/**
 * Classe utilizada para armazenamento das entradas presentes no ficheiro de servidores whois
 * @author balhau
 *
 */
public class WhoisEntry{
	/**
	 * String que representa o top level domain name (ex: com, pt, com.br, uk,...)
	 */
	public String topLevelDomainName;
	/**
	 * String que representa o nome do servidor whois para o respectivo top level domain name
	 */
	public String whoisHost;
	
	public WhoisEntry(String topLevelDomainName,String whoisHost){
		this.topLevelDomainName=topLevelDomainName;
		this.whoisHost=whoisHost;
	}
}