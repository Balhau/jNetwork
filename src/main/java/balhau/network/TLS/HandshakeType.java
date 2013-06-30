package balhau.network.TLS;
/**
 * Enumerado que representa o HandshakeType no protocolo TLS
 * Para mais informação consultar o RFC5246
 * @author balhau
 *
 */
public enum HandshakeType {
	hello_client(0),
	client_hello(1),
	server_hello(2),
	certificate(11),
	server_key_exchange(12),
	certificate_request(13),
	server_hello_done(14),
	certificate_verify(15),
	client_key_exchange(16),
	finished(20),
	other(255);
	
	private final int val;
	HandshakeType(int v) {
		this.val=v;
	}
	public int val(){return val;}
}
