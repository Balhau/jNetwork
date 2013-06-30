package balhau.network.TLS;

/**
 * Esta classe contem todas as propriedades públicas porque é suposto representar uma structure
 * Para mais informação consultar o RFC5246
 * @author balhau
 */
public class SecurityParameters {
	public ConnectionEnd entity;
	public PRFAlgorithm prf_algorithm;
	public BulkCipherAlgorithm bulk_cipher_algorithm;
	public CipherType cipher_type;
	public short enc_key_length;
	public short block_length;
	public short fixed_iv_length;
	public short record_iv_length;
	public MACalgorithm mac_algorithm;
	public short mac_length;
	public short mac_key_length;
	public CompressionMethod compression_algorithm;
	public byte[] master_secret=new byte[48];
	public byte[] client_random=new byte[32];
	public byte[] server_random=new byte[32];
	public SecurityParameters(){};
}
