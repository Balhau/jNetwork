package balhau.network.TLS;
/**
 * Enumerado que especifica o tipo de conteúdo da mensagem
 * @author balhau
 *
 */
public enum ContentType {
	change_cipher_spec(20),
	alert(21),
	handshake(22),
	application_data(23),
	other(255);
	private final int v;
	ContentType(int val){
		this.v=val;
	}
	public int val(){return v;}
}
