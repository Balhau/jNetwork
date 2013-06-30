package balhau.network.TLS;

/**
 * Enumerado para o tipo de Message Authentication Code (MAC)Algorithm 
 * Para mais informação consultar o RFC5246
 * @author balhau
 */
public enum MACalgorithm {
	Null,
	hmac_md5,
	hmac_sha1,
	hmac_sha256,
	hmac_sha384,
	hmac_sha512
}
