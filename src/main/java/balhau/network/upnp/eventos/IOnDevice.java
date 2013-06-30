package balhau.network.upnp.eventos;

import balhau.network.upnp.SSDPInfo;
/**
 * Interface para implementação de eventos
 * @author balhau
 */
public interface IOnDevice {
	/**
	 * Evento invocado quando recebida uma mensagem SSDP
	 * @param sddpinfo {@link SSDPInfo} Mensagem SSDP
	 */
	public void onSsdpReceived(SSDPInfo sddpinfo);
}
