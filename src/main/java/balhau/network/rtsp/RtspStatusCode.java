package balhau.network.rtsp;
/**
 * Mensagens de estado (Status Codes) para o protocolo RTSP (RFC 2326).
 * @author balhau
 *
 */
public enum RtspStatusCode {
	CONTINUE("Continue",100),
	
	OK("Ok",200),
	CREATED("Created",201),
	LOW_ON_STORAGE_SPACE("Low on storage space",250),
	
	MULTIPLE_CHOICES("Multiple Choices",300),
	MOVED_PERMANENTLY("Moved Permanently",301),
	MOVED_TEMPORARILY("Moved Temporarily",302),
	SEE_OTHER("See Other",303),
	USE_PROXY("Use Proxy",305),
	
	BAD_REQUEST("Bad Request",400),
	UNAUTHORIZED("Unauthorized",401),
	PAYMENT_REQUIRED("Payment Required",402),
	FORBIDDEN("Forbidden",403),
	NOT_FOUND("Not Found",404),
	METHOD_NOT_ALLOWED("Method not Allowed",405),
	NOT_ACCEPTABLE("Not Acceptable",406),
	PROXY_AUTHENTICATION_REQUIRED("Proxy Authentication Required",407),
	REQUEST_TIMEOUT("Request Timeout",408),
	GONE("Gone",410),
	LENGTH_REQUIRED("Length Required",411),
	PRECONDITION_FAILED("Precondition Failed",412),
	REQUEST_ENTITY_TOO_LARGE("Request entity too large",413),
	REQUEST_URI_TOO_LONG("Request-uri too long",414),
	UNSUPPORTED_MEDIA_TYPE("Unsupported media type",415),
	INVALID_PARAMETER("Invalid parameter",451),
	ILEGAL_CONFERENCE_IDENTIFIER("Ilegal conference identifier",452),
	NOT_ENOUGH_BANDWIDTH("Not enough bandwidth",453),
	SESSION_NOT_FOUND("Session not found",454),
	METHOD_NOT_VALID_IN_THIS_STATE("Method not valid in this state",455),
	HEADER_FIELD_NOT_VALID("Header field not valid",456),
	INVALID_RANGE("Invalid Range",457),
	PARAMETER_IS_READ_ONLY("Parameter is read only",458),
	AGGREGATE_OPERATION_NOT_ALLOWED("Aggregate operation not allowed",459),
	ONLY_AGGREGATE_OPERATION_ALLOWED("Only aggregate operation allowed",460),
	UNSUPPORTED_TRANSPORT("Unsupported transport",461),
	DESTINATION_UNREACHABLE("Destination Unreachable",462),
	
	INTERNAL_SERVER_ERROR("Internal server error",500),
	NOT_IMPLEMENTED("Not implemented",501),
	BAD_GATEWAY("Bad gateway",502),
	SERVICE_UNAVAIABLE("Service Unavaiable",503),
	GATEWAY_TIMEOUT("Gateway timeout",504),
	RTSP_VERSION_NOT_SUPPORTED("RTSP version not supported",505),
	OPTION_NOT_SUPPORTED("Option not supported",506)
	;
	
	private int codigo;
	private String descricao;
	
	RtspStatusCode(String descricao,int codigo){
		this.codigo=codigo;
		this.descricao=descricao;
	}
	/**
	 * Método que devolve a mensagem do respectivo Status Code
	 * @return {@link String} Mensagem do Status Code
	 */
	public String getCodeMessage(){
		return this.descricao;
	}
	
	/**
	 * Método que devolve o código do respectivo Status Code 
	 * @return {@link int} Representação numérica do status code
	 */
	public int getStatusCode(){
		return this.codigo;
	}
}
