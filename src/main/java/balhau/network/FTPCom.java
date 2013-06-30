package balhau.network;
/**
 * Classe que contem os comandos especificados pelo protocolo FTP<br/><br/>
 * <b>Referências:</b><br/>
 * <a href="http://www.ietf.org/rfc/rfc959.txt">File Transfer Protocol (FTP)</a>
 * @author balhau
 *
 */
public class FTPCom {
	/**
	 * Commando que aborta o processo de conecção
	 */
	public static String CM_ABOR="ABOR";
	/**
	 * Comando que define a conta para sistemas com privilégios
	 */
	public static String CM_ACCOUNT="ACCT";
	/**
	 * Comando que aloca um número de bytes para armazenamento no servidor
	 */
	public static String CM_ALLO="ALLO"; 
	/**
	 * Comando que muda para a directoria pai no servidor
	 */
	public static String CM_CDUP="CDUP";
	/**
	 * Comando que muda a directoria no servidor
	 */
	public static String CM_CWD="CWD";
	/**
	 * Comando que apaga um determinado ficheiro no servidor
	 */
	public static String CM_DELE="DELE";
	/**
	 * Comando que retorna informação sobre o comando especificado
	 */
	public static String CM_HELP="HELP";
	/**
	 * Comando que devolve a informação sobre o ficheiro ou os ficheiros caso o argumento
	 * seja um directório
	 */
	public static String CM_LIST="LIST";
	/**
	 * Comando que especifica o modo de transmissão de dados
	 */
	public static String CM_MODE="MODE";
	/**
	 * Comando que cria um directório no servidor
	 */
	public static String CM_MKDIR="MKD";
	/**
	 * Comando que lista os conteúdos contidos num directório
	 */
	public static String CM_NLST="NLST";
	/**
	 * Informa o reconhecimento por parte do servidor
	 */
	public static String CM_NOOP="NOOP";
	/**
	 * Comando que especifica a password de acesso ao servidor
	 */
	public static String CM_PASS="PASS";
	/**
	 * Comando que requisita tempo ao servidor para a conecção
	 */
	public static String CM_PASV="PASV";
	/**
	 * Comando que especifica o ip e a porta xxx.xxx.xxx.xxx:yy e a porta para acesso ao servidor
	 */
	public static String CM_PORT="PORT";
	/**
	 * Comando que mostra o conteúdo do actual directório
	 */
	public static String CM_PWD="PWD";
	/**
	 * Comando que efectua invoca a saida do servidor
	 */
	public static String CM_QUIT="QUIT";
	/**
	 * Comando que reinicializa a conecção
	 */
	public static String CM_REIN="REIN";
	/**
	 * Comando que reinicializa a transmissão dos dados a partir de um determinado offset
	 */
	public static String CM_REST="REST";
	/**
	 * Comando que recupera um ficheiro do servidor
	 */
	public static String CM_RETR="RETR";
	/**
	 * Comando que remove um determinado directório do servidor 
	 */
	public static String CM_RMD="RMD";
	/**
	 * Comando que introduz o caminho antigo para renomeação
	 */
	public static String CM_RNFR="RNFR";
	/**
	 * Comando que introduz o novo caminho para a renomeação
	 */
	public static String CM_RNTO="RNTO";
	/**
	 * Comando que especifica parametros disponibilizados pelo servidor
	 */
	public static String CM_SITE="SITE";
	/**
	 * Comando que monta a estrutura de ficheiros especificada
	 */
	public static String CM_SMNT="SMNT";
	/**
	 * Comando que devolve informação do actual processo ou directório
	 */
	public static String CM_STATE="STATE";
	/**
	 * Comando que guarda uma cópia do ficheiro no servidor
	 */
	public static String CM_STOR="STOR";
	/**
	 * Comando que guarda um ficheiro no servidor
	 */
	public static String CM_STOU="STOU";
	/**
	 * Comando que especifica o tipo de estruturas de dados
	 */
	public static String CM_STRU="STRU";
	/**
	 * Comando que devolve informação sobre o sistema utilizado pelo sistema operativo
	 */
	public static String CM_SYST="SYST";
	/**
	 * Comando que especifica o tipo de dados
	 */
	public static String CM_TYPE="TYPE";
	/**
	 * Comando que especifica o nome de utilizador para autenticação
	 */
	public static String CM_USER="USER";
}
