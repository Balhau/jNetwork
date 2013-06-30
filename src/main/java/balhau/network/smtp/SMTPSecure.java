package balhau.network.smtp;
/**
 * Enumerados representando o tipo de comunicação com o servidor SMTP.
 * @author root
 *
 */
public enum SMTPSecure {
	/**
	 * Ausência de protocolo de segurança
	 */
	None,
	/**
	 * Comunicação baseada no protocolo Secure Socket Layer
	 */
	SSL,
	/**
	 * Comunicação baseada no protocolo Transmission Layer Security
	 */
	TLS
}
