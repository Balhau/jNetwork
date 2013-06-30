package balhau.network.smtp;
/**
 * Enumerado que representa a prioridade associada ao envio do email 
 * @author balhau
 *
 */
public enum SMTPPrioridade {
	/**
	 * Email com alta prioridade, valor 1
	 */
	Alta,
	/**
	 * Email com prioridade normal, valor 3
	 */
	Normal,
	/**
	 * Email com prioridade baixa, valor 5
	 */
	Baixa
}
