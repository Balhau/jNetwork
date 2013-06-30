package balhau.network.bluetooth;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.RemoteDevice;

/**
 * Interface para as funções de callback do serviço de bluetooth
 * @author balhau
 *
 */
public interface BluemarkEvent {
	/**
	 * Evento que dispara quando se encontra um serviço para um determinado dispositivo de bluetooth
	 * @param urlconn {@link String} String de conecção ao dispositvo e respectivo serviço
	 * @param rd {@link RemoteDevice} Objecto que representa o disposito remoto de bluetooth
	 */
	public void handleService(String urlconn,RemoteDevice rd);
	/**
	 * Evento que dispara quando um novo dispositivo de bluetooth é encontrado na rede
	 * @param rd {@link RemoteDevice} Objecto que representa o dispositivo remoto de bluetooth
	 * @param dc {@link DeviceClass} Objecto que contem informações acerca do tipo de dispositivo de bluetooth
	 * @return {@link boolean} Valor booleano que representa o sucesso ou insucesso do evento 
	 */
	public boolean onDevice(RemoteDevice rd,DeviceClass dc);
	/**
	 * Evento que dispara quando a pesquisa de dispositivos termina de forma normal
	 * @param bmarkdiscover {@link BlueMarkDiscover} Objecto que invocou o pedido de pesquisa de dispositivos
	 */
	public void onCompleteDeviceSearch(BlueMarkDiscover bmarkdiscover);
	/**
	 * Evento que dispara quando existe um erro durante a pesquisa de dispostivos
	 * @param bmarkdiscover {@link BlueMarkDiscover} Objecto que invocou o pedido de pesquisa de dispositivos
	 */
	public void onDeviceSearchError(BlueMarkDiscover bmarkdiscover);
	/**
	 * Evento que dispara quando a pesquisa de dispositvos de bluetooth é cancelada
	 * @param bmarkdiscover {@link BlueMarkDiscover} Objecto que invocou o pedido de pesquisa de dispositos
	 */
	public void onCanceledDeviceSearch(BlueMarkDiscover bmarkdiscover);
	
	/**
	 * Evento que dispara quando a inicialização do dispositivo local de bluetooth falha
	 * @param bmarkdiscover {@link BlueMarkDiscover} Objecto que invocou o pedido
	 */
	public void onDiscoveryAgentInitFailure(BlueMarkDiscover bmarkdiscover);
}
