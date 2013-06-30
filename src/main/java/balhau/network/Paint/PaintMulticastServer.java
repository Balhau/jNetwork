package balhau.network.Paint;

import java.net.MulticastSocket;

/**
 * Esta classe implementa um servidor UDP. Este servidor será utilizado para a implementação de um paint
 * em rede. O objectivo da aplicação consiste num ambiente de desenho com comunicações de rede.
 * @author balhau
 *
 */
public class PaintMulticastServer {
	private MulticastSocket _msock;
	/**
	 * Construtor da classe. Especificação da porta onde irá correr o servidor
	 * @param porta {@link Integer} representa a porta onde irá correr o servidor
	 */
	public PaintMulticastServer(int porta){
		try{
			this._msock=new MulticastSocket(porta);
			this._msock.setTimeToLive(4);
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}
