package balhau.network.websocket.base;

/**
 * Interface para eventos de mensagens do protocolo WebSocket
 * <b>Referências:</b><br>
 * <a href="http://tools.ietf.org/html/rfc6455">WebSocket Protocol</a>
 * @author balhau
 *
 */
public interface IWebSocketEvent {
	void onMessage(String message);
}
