package balhau.network.websocket.base;
/**
 * Enumerado que representa as várias constantes necessárias ao protocolo
 * <b>Referências:</b><br>
 * <a href="http://tools.ietf.org/html/rfc6455">WebSocket Protocol</a>
 * @author balhau
 *
 */
public enum WebSocketCode {
	STATUS_OK("101"),
	WS_VERSION("13"),
	WS_GUID("258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
	;
	private String val;
	private WebSocketCode(String value){
		this.val=value;
	}
	
	public String value(){
		return this.val;
	}
}
