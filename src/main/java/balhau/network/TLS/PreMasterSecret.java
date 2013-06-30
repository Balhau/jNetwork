package balhau.network.TLS;
/**
 * Objecto que representa a informação sobre a mensagem pre master
 * @author balhau
 *
 */
public class PreMasterSecret{
	public ProtocolVersion client_version;
	public byte[] random=new byte[46];
	public PreMasterSecret(ProtocolVersion v){
		this.client_version=v;
	}
}
