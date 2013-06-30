package balhau.network.Paint;

/**
 * Esta classe implementa um servidor UDP. Este servidor será utilizado para a implementação de um paint
 * em rede. O objectivo da aplicação consiste num ambiente de desenho com comunicações de rede.
 * @author balhau
 *
 */
public class PaintUDPServer {
	@SuppressWarnings("unused")
	private int _porta;
	/**
	 * Construtor da classe. Especificação da porta onde irá correr o servidor
	 * @param porta {@link Integer} representa a porta onde irá correr o servidor
	 */
	public PaintUDPServer(int porta){
		this._porta=porta;
	}
}
