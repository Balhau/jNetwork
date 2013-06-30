package balhau.network.TLS;

/**
 * Enumerado que representa o metodo de compressão
 * Para mais informação consultar o RFC5246
 * @author balhau
 */
public enum CompressionMethod {
	Null(0),
	other(255);
	private final int val;
	CompressionMethod(int v) {
		this.val=v;
	}
	public int val(){return val;}
}
