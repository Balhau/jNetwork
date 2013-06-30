package balhau.network.bluetooth;

import balhau.metastruct.Lista;
import balhau.utils.Mask;

/**
 * Enumerado que representa os vários tipos de dispositivos bluetooth. Este objecto utiliza a informação do major e minor code
 * para detectar o tipo de dispositivo.<br><br>
 * <b>Referências:</b><br>
 * <a href="https://www.bluetooth.org/Technical/AssignedNumbers/baseband.htm">Documentação oficial do Bluetooth Special Interest Group</a><br>
 * <a href="http://bluetooth-pentest.narod.ru/software/bluetooth_class_of_device-service_generator.html">
 * Bluetooth Class of Device/Service (CoD) Generator
 * </a><br>
 * @author balhau
 *
 */
public enum BluetoothDevice{
	//Computer Major Devices
	COMPUTER_UNCATEGORIZED(0x100,0,"Computer Uncategorized"),
	COMPUTER_DESKTOP(0x100,0x4,"Computer Desktop"),
	COMPUTER_SERVER(0x100,0x8,"Server class computer"),
	COMPUTER_LAPTOP(0x100,0xC,"Computer Laptop"),
	COMPUTER_HANDHELD(0x100,0x10,"Computer handheld"),
	COMPUTER_PALM_SIZED(0x100,0x14,"Computer palm sized"),
	COMPUTER_WEARABLE(0x100,0x18,"Wearable computer"),
	
	//Phone Major devices
	PHONE_UNCATEGORIZED(0x200,0,"Phone Uncategorized"),
	PHONE_CELLULAR(0x200,0x4,"Cellular phone"),
	PHONE_CORDLESS(0x200,0x8,"Cordless phone"),
	PHONE_SMARTPHONE(0x200,0xC,"Smartphone"),
	PHONE_WMORVG(0x200,0x10,"Wired Modem or Voice Gatway"),
	PHONE_CIA(0x200,0x14,"Common ISDN Access"),
	
	//Lan/Network Access Point devices
	LNAPD_FULLY_AVAILABLE(0x300,0,"Fully available"),
	LNAPD_1_17(0x300,0x20,"1%-17% utilized"),
	LNAPD_17_33(0x300,0x40,"17%-33% utilized"),
	LNAPD_33_50(0x300,0x60,"33%-50% utilized"),
	LNAPD_50_67(0x300,0x80,"50%-67% utilized"),
	LNAPD_67_83(0x300,0xA0,"67%-83% utilized"),
	LNAPD_83_99(0x300,0xc0,"83%-99% utilized"),
	LNAPD_NO_SERVICE_AVAILABLE(0x300,0xe0,"No service available"),
	
	//Audio/video devices
	AV_UNCATEGORIZED(0x400,0x0,"Uncategorized, code for device not assigned"),
	AV_WEARABLE_HEADSET(0x400,0x4,"Wearable Handset device"),
	AV_HANDS_FREE(0x400,0x8,"Hands free device"),
	AV_MICROPHONE(0x400,0x10,"Microphone device"),
	AV_LOUDSPEAKER(0x400,0x14,"Loudspeaker device"),
	AV_HEADPHONES(0x400,0x18,"Headphones device"),
	AV_PORTABLE_AUDIO(0x400,0x1c,"Portable audio device"),
	AV_CAR_AUDIO(0x400,0x20,"Car audio device"),
	AV_SET_TOP_BOX(0x400,0x24,"Set top box device"),
	AV_HIFI_AUDIO(0x400,0x28,"HiFi Audio device "),
	AV_VCR(0x400,0x2c,"VCR device"),
	AV_VIDEO_CAMERA(0x400,0x30,"Video camera device"),
	AV_CAMCORDER(0x400,0x34,"Camcorder device"),
	AV_VIDEOMONITOR(0x400,0x38,"Video monitor device"),
	AV_VIDEODISPLAY_LOUDSPEAKER(0x400,0x3c,"Video display and loudspeaker device"),
	AV_VIDOCONFERENCING(0x400,0x40,"Video Conferencing device"),
	AV_GAMING_TOY(0x400,0x48,"Gaming/toy device"),
	
	//Peripheral devices
	PERIPHERAL_UNCATEGORIZED(0x500,0x0,"Uncategorized device"),
	PERIPHERAL_JOYSTICK(0x500,0x4,"Joystick device"),
	PERIPHERAL_GAMEPAD(0x500,0x8,"Gamepad device"),
	PERIPHERAL_REMOTECONTROL(0x500,0xc,"Remote control device"),
	PERIPHERAL_SENSING(0x500,0x10,"Sensing device"),
	PERIPHERAL_DIGITIZER_TABLET(0x500,0x14,"Digitizer tablet device"),
	PERIPHERAL_CARDREADER(0x500,0x18,"Card Reader device"),
	PERIPHERAL_KEYBOARD(0x500,0x40,"Keyboard device"),
	PERIPHERAL_POINTING(0x500,0x80,"Pointing device"),
	PERIPHERAL_COMBO(0x500,0xc0,"Combo keyboard/pointing device"),
	
	//Imaging devices
	IMAGING_DISPLAY(0x600,0x10,"Display"),
	IMAGING_CAMERA(0x600,0x20,"Camera"),
	IMAGING_SCANNER(0x600,0x40,"Scanner"),
	IMAGING_PRINTER(0x600,0x80,"Printer"),
	
	//Wearable devices
	WEARABLE_WRISTWATCH(0x700,0x4,"Wrist watch"),
	WEARABLE_PAGER(0x700,0x8,"Pager"),
	WEARABLE_JACKET(0x700,0xc,"Jacket"),
	WEARABLE_HELMET(0x700,0x10,"Helmet"),
	WEARABLE_GLASSES(0x700,0x14,"Glasses"),
	
	//Toy devices
	TOY_ROBOT(0x800,0x4,"Robot"),
	TOY_VEHICLE(0x800,0x8,"Vehicle"),
	TOY_DOLL(0x800,0xc,"Doll/Action Figure"),
	TOY_CONTROLLER(0x800,0x10,"Controller"),
	TOY_GAME(0x800,0x14,"Game"),
	
	MISCELLANEOUS(0x0,0x0,"Miscellaneous device"),
	UNCATEGORIZED(0x1f00,0x0,"Uncategorized,specific device code not specified")
	;
	private int major;
	private int minor;
	private String desc;
	
	private BluetoothDevice(int major,int minor,String desc){
		this.major=major;
		this.minor=minor;
		this.desc=desc;
	}
	public int getMajor(){
		return major;
	}
	
	public int getMinor(){
		return minor;
	}
	/**
	 * Método que devolve um array de enumerados {@link BluetoothDevice} com informação acerca do dispositivo de bluetooth
	 * @param code {@link int} Valor inteiro com o código identificador de tipo de dispositvo
	 * @return {@link BluetoothDevice[]} Array de valores enumerados que representam as propriedades presentes no dispositivo
	 */
	public static BluetoothDevice[] devicesFromCode(int code){
		int minor=code & Mask.MASK8;
		int major= code ^ minor;
		BluetoothDevice[] devices=BluetoothDevice.values();
		Lista<BluetoothDevice> lista=new Lista<BluetoothDevice>();
		for(int i=0;i<devices.length;i++){
			if(devices[i].major==major && ((devices[i].minor&minor)!=0))
				lista.addValue(devices[i]);
		}
		return lista.toArray();
	}
	
	public String toString(){
		return desc;
	}
}
