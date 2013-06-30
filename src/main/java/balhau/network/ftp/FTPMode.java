package balhau.network.ftp;
/**
 * Enumerado que representa o tipo de transmissão de dados no protocolo FTP
 * @author balhau
 *
 */
public enum FTPMode {
	/**
	 * Modo por fluxo
	 */
	S,
	/**
	 * Modo por blocos.
	 */
	B,
	/**
	 * Modo comprimido
	 */
	C
}
