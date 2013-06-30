package balhau.network.websocket.base;
/**
 * Interface para eventos de abertura e encerramento de ligações entre os clientes do protocolo WebSocket
 * <b>Referências:</b><br>
 * <a href="http://tools.ietf.org/html/rfc6455">WebSocket Protocol</a>
 * @author balhau
 *
 */
public interface IWebSocketHandler {
	public void connected(IWebSocket ws);
	public void disconnected(IWebSocket ws);
}
