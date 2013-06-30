package balhau.network.TLS;
/**
 * Classe que implementa a estrutura ClientHello do protocolo TLS
 * Para mais informação consultar o RFC5246
 * @author balhau
 *
 */
public class ClientHello {
	public ContentType type;
	public ProtocolVersion version;
	public int length;
	public ClientHello(ContentType t,ProtocolVersion v,int l){
		this.length=l;
		this.type=t;
		this.version=v;
	}
}
