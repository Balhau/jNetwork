package balhau.network.types;
/**
 * Enumerado que representa o tipo de protocolo de IP, versão 4 ou 6
 * @author balhau
 *
 */
public enum IpProtocol {
	IpV4("IPV4"),
	IpV6("IPV6")
	;
	
	private String desc;
	IpProtocol(String desc){
		this.desc=desc;
	}
	
	public String getDesc(){return desc;};
}
