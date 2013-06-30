package balhau.network.TLS;
/**
 * Enumerado que representa os vários mecanismos de hashing disponíveis
 * @author balhau
 *
 */
public enum HashAlgorithm {
	none(0),
	md5(1),
	sha1(2),
	sha224(3),
	sha256(4),
	sha384(5),
	sha512(6),
	other(255);
	
	private final int val;
	HashAlgorithm(int v) {
		this.val=v;
	}
	public int val(){return this.val;};
}
