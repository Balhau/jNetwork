package balhau.network.bluetooth;
/**
 * Classe que contém as constantes que identificam as várias classes de dispositivos de bluetooth
 * @author balhau<br>
 * <b>Referências:</b><br>
 * <a href="https://www.bluetooth.org/Technical/AssignedNumbers/baseband.htm">Documentação oficial do Bluetooth Special Interest Group</a><br>
 * <a href="http://bluetooth-pentest.narod.ru/software/bluetooth_class_of_device-service_generator.html">
 * Bluetooth Class of Device/Service (CoD) Generator
 * </a><br>
 */
public class DeviceClassCode {
	/**
	 * Código que identifica um desktop, laptop, PDA etc
	 */
	public static final char Computer=0x100;
	/**
	 * Código que identifica um telefone, telemóvel, modem
	 */
	public static final char Phone=0x200;
	/**
	 * Código que identifica um ponto de acesso à rede (NAP)
	 */
	public static final char LAN_NAP=0x300;
	/**
	 * Código que identifica headphones, microfones, dispositivos de reprodução de vídeo etc
	 */
	public static final char AUDIO_VIDEO=0x400;
	/**
	 * Código que identifica dispositivos periféricos. Rato, joystick, teclado, camara etc 
	 */
	public static final char Peripheral=0x500;
	/**
	 * 
	 */
	public static final char Wearable=0x700;
	/**
	 * 
	 */
	public static final char Toy=0x800;
	public static final char Uncategorized=0x1f00;
	
}
