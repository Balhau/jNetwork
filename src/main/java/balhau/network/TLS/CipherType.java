package balhau.network.TLS;

/**
 * Enumerado que representa o tipo de cifra.
 * Para mais informação consultar o RFC5246
 * @author balhau
 *
 */
public enum CipherType {
	stream,
	block,
	aead
}
