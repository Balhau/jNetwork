package balhau.network;

/**
 * Enumerado que representa a estrutura de dados<br/><br/> 
 * <b>Referências:</b><br/>
 * <a href="http://www.ietf.org/rfc/rfc959.txt">FILE TRANSFER PROTOCOL (FTP)</a>
 * @author balhau
 */
public enum FTPDStruct {
	/**
	 * Estrutura de ficheiro
	 */
	F,
	/**
	 * Estrutura de record
	 */
	R,
	/**
	 * Estrutura de página
	 */
	P
}
