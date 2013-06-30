package balhau.network.bluetooth;

import java.io.OutputStream;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.obex.ClientSession;
import javax.obex.HeaderSet;
import javax.obex.Operation;
import javax.obex.ResponseCodes;

import balhau.utils.Log;

public class ObexPushCallback implements BluemarkEvent{
	private String fname;
	private byte[] data;
	private String dataType;
	
	public ObexPushCallback(String fname,byte[] data,String dtype){
		this.fname=fname;
		this.data=data;
		this.dataType=dtype;
	}
	public void handleService(String urlconn,RemoteDevice rd) {
		try{
			Log.log(BMessages.CONNECTING_DEVICE);
			ClientSession csession=(ClientSession)Connector.open(urlconn);
			HeaderSet hs=csession.connect(null);
			if (hs.getResponseCode() != ResponseCodes.OBEX_HTTP_OK){
				Log.log(BMessages.CONNECTING_ERROR);
				return;
			}
			HeaderSet hso=csession.createHeaderSet();
			hso.setHeader(HeaderSet.NAME, fname);
			hso.setHeader(HeaderSet.TYPE, dataType);
			hso.setHeader(HeaderSet.LENGTH, (long)data.length);
			Log.log("Length:"+data.length);
			Operation op=csession.put(hso);
			OutputStream os=op.openOutputStream();
			os.write(data);
			os.flush();
			os.close();
			op.close();
			csession.close();
			Log.log("Mensagem enviada");
			}catch (Exception e) {
				Log.log(e.getMessage());
			}
	}
	public boolean onDevice(RemoteDevice rd, DeviceClass dc) {
		return true;
	}
	public void onCompleteDeviceSearch(BlueMarkDiscover bmarkdiscover) {
		DeviceInfo[] listOfLocalDevicesFound=bmarkdiscover.getListOfRemoteDevicesFound();
		if(listOfLocalDevicesFound !=null && listOfLocalDevicesFound.length==0){
			synchronized (bmarkdiscover) {
				bmarkdiscover.ThreadEnd=true;
				bmarkdiscover.stopDiscover();
				notifyAll();
			}
		}
	}
	public void onDeviceSearchError(BlueMarkDiscover bmarkdiscover) {
		
	}
	public void onCanceledDeviceSearch(BlueMarkDiscover bmarkdiscover) {
		
	}
	public void onDiscoveryAgentInitFailure(BlueMarkDiscover bmarkdiscover) {
		
	}
}
