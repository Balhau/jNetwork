package balhau.network.websocket.base;
/**
 * Interface para implementações do protocolo WebSocket. Esta interface foi construída para antecipar possíveis
 * alterações no protocolo que sejam significativamente diferentes. Deste modo cada uma das versões do protocolo
 * deverá implementar esta interface. Por enquanto somente o objecto {@link WebSocket} está construído e pretenderá 
 * seguir a especificação presente no rfc mensionado em seguida
 * <b>Referências:</b><br>
 * <a href="http://tools.ietf.org/html/rfc6455">WebSocket Protocol</a>
 * @author balhau
 *
 */
public interface IWebSocket {
	/**
	 * Método que permite recuperar uma entrada nos cabeçalhos HTTP enviados pelo cliente
	 * @param key {@link String} String que identifica a chave do cabeçalho HTTP
	 * @return {@link String} Valor do cabeçalho http identificado pela chave
	 */
	public String getHeader(String key);
	/**
	 * Evento para envio de mensagens ao cliente
	 * @param msg {@link String} Mensagem que se pretende enviar para o cliente
	 */
	public void sendMsg(String msg);
	/**
	 * Método que efectua a adição de um listener para gestão as mensagens recebidas
	 * @param msgList {@link IWebSocketMsg} Interface que gere a recepção de mensagens
	 */
	public void addMsgListener(IWebSocketEvent msgList);
	/**
	 * Método que efectua a remoção de um listener para gestão das mensagens recebidas
	 * @param msgList {@link IWebSocketMsg} Interface que gere a recepção de mensagens
	 */
	public void remMsgListener(IWebSocketEvent msgList);
	/**
	 * Método que efectua o fecho de ligação com o cliente
	 */
	void close();
	/**
	 * Método que devolve o caminho de contexto do websocket
	 * @return
	 */
	public String getPath();
}
