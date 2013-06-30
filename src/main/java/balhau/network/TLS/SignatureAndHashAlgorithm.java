package balhau.network.TLS;
/**
 * Classe que representa a estrutura de assinatura
 * @author balhau
 *
 */
public class SignatureAndHashAlgorithm {
	public HashAlgorithm hash;
	public SignatureAlgorithm signature;
	
	public SignatureAndHashAlgorithm(HashAlgorithm h,SignatureAlgorithm s){
		this.hash=h;
		this.signature=s;
	}
}
