/**
 * Pacote com funcionalidades para comunicação via Bluetooth.
 */
package balhau.network.bluetooth;

import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

import balhau.metastruct.Lista;
import balhau.utils.Log;
import balhau.utils.StringUtils;
/**
 * Classe que implementa a interface DiscoveryAgent com o propósito de efectuar a pesquisa
 * de dispositivos bluetooth e respectivo envio de mensagens
 * @author balhau
 *
 */
public class BlueMarkDiscover implements DiscoveryListener{
	private static final int OBEX_PUSH=0x1105;
	private Lista<DeviceInfo> lDevice;
	private int op;
	public boolean ThreadEnd;
	private BluemarkEvent event;
	private DiscoveryAgent dAgent;
	private MessengerThread senderThread;
	private int searchCounter;
	
	public int getSearchCounter(){
		return searchCounter;
	}
	
	public synchronized void decSearchCounter(){
		searchCounter--;
	}
	
	public synchronized void incSearchCounter(){
		searchCounter++;
	}
	
	/**
	 * Construtor defeito do objecto
	 * @throws InterruptedException 
	 */
	public BlueMarkDiscover() throws InterruptedException{
		event=new BluemarkEvent() {
			public boolean onDevice(RemoteDevice rd, DeviceClass dc) {
				return true;
			}
			
			public void handleService(String urlconn, RemoteDevice rd) {			
			}

			public void onCompleteDeviceSearch(BlueMarkDiscover bmarkdiscover) {
			}

			public void onDeviceSearchError(BlueMarkDiscover bmarkdiscover) {
			}

			public void onCanceledDeviceSearch(BlueMarkDiscover bmarkdiscover) {	
			}

			public void onDiscoveryAgentInitFailure(
					BlueMarkDiscover bmarkdiscover) {
			}
		};
		initVals();
		op=OBEX_PUSH;
	}
	
	private void initVals(){
		searchCounter=0;
		lDevice=new Lista<DeviceInfo>();
		ThreadEnd=false;
	}
	
	public boolean getDiscoveryAgent(){
		try {
			dAgent=LocalDevice.getLocalDevice().getDiscoveryAgent();
			return true;
		}
		catch(BluetoothStateException e){
				Log.log(BMessages.LOCAL_DEV_NOT_FOUND);
				event.onDiscoveryAgentInitFailure(this);
		}
		return false;
	}
	
	public DeviceInfo[] getRemoteDevicesFound(){
		return lDevice.toArray();
	}
	/**
	 * Construtor com especificação do UUID de serviço
	 * @param op_uuid {@link Integer} UUID
	 */
	public BlueMarkDiscover(int op_uuid){
		event=new BluemarkEvent() {
			public boolean onDevice(RemoteDevice rd, DeviceClass dc) {
				return true;
			}
			
			public void handleService(String urlconn, RemoteDevice rd) {
			}

			public void onCompleteDeviceSearch(BlueMarkDiscover bmarkdiscover) {
				
			}

			public void onDeviceSearchError(BlueMarkDiscover bmarkdiscover) {
			}

			public void onCanceledDeviceSearch(BlueMarkDiscover bmarkdiscover) {
				
			}

			public void onDiscoveryAgentInitFailure(
					BlueMarkDiscover bmarkdiscover) {
				
			}
		};
		initVals();
	}
	/***
	 * Construtor com espeficiação da interface para função callback
	 * @param call {@link BluemarkEvent} Objecto com os eventos necessários 
	 */
	public BlueMarkDiscover(BluemarkEvent event){
		this.event=event;
		op=OBEX_PUSH;
		initVals();
	}

	/***
	 * Especifica a função callback
	 * @param  call {@link BluetoothServiceCallback}  
	 */
	public void setEvent(BluemarkEvent event){
		this.event=event;
	}
	
	/**
	 * Inicia a pesquisa da dispositivos
	 */
	public boolean startDiscover(){
		try{
			initVals();
			return dAgent.startInquiry(DiscoveryAgent.GIAC, this);
		} catch (BluetoothStateException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public DeviceInfo[] getListOfRemoteDevicesFound(){
		return lDevice.toArray();
	}
	/**
	 * Cancela a pesquisa de dispositivos
	 */
	public void stopDiscover(){
		dAgent.cancelInquiry(this);
	}
	
	public void deviceDiscovered(RemoteDevice rm, DeviceClass dc) {
		//caso seja um pc ou um telemóvel adicionar o dispositivo
		//à lista dos dispositivos
		if(event.onDevice(rm, dc)){
			lDevice.addValue(new DeviceInfo(rm, dc));
			incSearchCounter();
		}
		try {
			Log.log(BMessages.DEV_DISC_LOG+":"+rm.getFriendlyName(false)+":"+rm.getBluetoothAddress());
			Log.log("Major Device: "+StringUtils.hex32(dc.getMajorDeviceClass()));
			Log.log("Minor Device: "+StringUtils.hex32(dc.getMinorDeviceClass()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void inquiryCompleted(int keyCode) {
		switch (keyCode) {
		case DiscoveryListener.INQUIRY_COMPLETED:
			event.onCompleteDeviceSearch(this);
			processaEndDevicesCompleted();
			searchServices();
			Log.log(BMessages.DEV_PROC_COMP);
			break;
		case DiscoveryListener.INQUIRY_ERROR:
			event.onDeviceSearchError(this);
			Log.log(BMessages.DEV_PROC_ERR);
			break;
		case DiscoveryListener.INQUIRY_TERMINATED:
			event.onCanceledDeviceSearch(this);
			Log.log(BMessages.DEV_PROC_TERM);
		default:
			break;
		}
	}
	
	private void searchServices(){
		try{//pesquisar, dentro dos dispositivos encontrados aqueles que possuem o serviço ObexPush
			for(int i=0,len=lDevice.getNumElementos();i<len;i++){
				dAgent.searchServices(null, new UUID[]{new UUID(op)}, lDevice.getValor(i).device, this);
				Log.log("Iteracção: "+(i+1)+" de "+len);
			}
		}
		catch (Exception e) {
		}
	}

	public void serviceSearchCompleted(int trID, int keyCode) {
		Log.log("Metodo serviceSearchCompleted: "+searchCounter);
		switch (keyCode) {
		case DiscoveryListener.SERVICE_SEARCH_COMPLETED:
			Log.log(BMessages.PROCURA_COMPLETA);
			break;
		case DiscoveryListener.SERVICE_SEARCH_ERROR:
			Log.log(BMessages.PROCURA_ERRO);
			processaEndServicesFail(trID);
			break;
		case DiscoveryListener.SERVICE_SEARCH_TERMINATED:
			Log.log(BMessages.PROCURA_FIM);
			break;
		case DiscoveryListener.SERVICE_SEARCH_NO_RECORDS:
			Log.log(BMessages.PROCURA_VAZIA);
			break;
		case DiscoveryListener.SERVICE_SEARCH_DEVICE_NOT_REACHABLE:
			processaEndServicesFail(trID);
			Log.log(BMessages.SERVICO_NDISP);
			break;
		default:
			break;
		}
	}
	
	private void processaEndDevicesCompleted(){
		Log.log("Search Counter: "+searchCounter);
		try{
			if(searchCounter==0){
				synchronized (this) {
					ThreadEnd=true;
					notifyAll();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void processaEndServicesFail(int transferId){
		Log.log("PROCESSA END SERVICES CALLED");
		try{
			decSearchCounter();
			if(searchCounter==0){
				synchronized (this) {
					ThreadEnd=true;
					notifyAll();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void servicesDiscovered(int idTrans, ServiceRecord[] srec) {
		Log.log("Metodo ServicesDiscovered: "+searchCounter);
		senderThread=new MessengerThread(srec.clone(),event,this);
		senderThread.run();
	}
}
/**
 * Classe criada para representar as threads de envio. Este objecto implementa a class Runnable e estamos portanto na presença de um mecanismo
 * de threads. Esta classe foi criada para permitir o envio concorrente de mensagens
 * @author balhau
 *
 */
class MessengerThread implements Runnable{
	public ServiceRecord[] serviceRecords;
	public BluemarkEvent event;
	public BlueMarkDiscover pai;
	
	public MessengerThread(ServiceRecord[] serviceRecords,BluemarkEvent event, BlueMarkDiscover pai){
		this.serviceRecords=serviceRecords;
		this.event=event;
		this.pai=pai;
	}
	
	public void run() {
		for(int i=0;i<serviceRecords.length;i++){
			String urlconn=serviceRecords[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
			RemoteDevice rd=serviceRecords[i].getHostDevice();
			Log.log(urlconn);
			event.handleService(urlconn, rd);
		}
		try{
			pai.decSearchCounter();
			if(pai.getSearchCounter()<=0){
				synchronized (pai) {
					pai.ThreadEnd=true;
					pai.notifyAll();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}