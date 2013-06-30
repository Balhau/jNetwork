package balhau.network.TLS;
/**
 * Enumerado que representa o tipo de algoritmo de assinatura utilizado
 * @author balhau
 *
 */
public enum SignatureAlgorithm {
	anonymous(2),
	rsa(1),
	dsa(2),
	ecdsa(3),
	other(255);
	
	private final int val;
	SignatureAlgorithm(int v){
		this.val=v;
	}
	public int val(){return this.val;}
}
