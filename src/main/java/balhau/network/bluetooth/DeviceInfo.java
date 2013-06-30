package balhau.network.bluetooth;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.RemoteDevice;

public class DeviceInfo {
	public RemoteDevice device;
	public DeviceClass info;
	
	public DeviceInfo(RemoteDevice device,DeviceClass info){
		this.device=device;
		this.info=info;
	}
}
