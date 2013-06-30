package balhau.network.ftp;
/**
 * Classe que contem os códigos de resposta de servidor especificados no protocolo FTP
 * @author balhau
 *
 */
public class FTPRCode {
	public short RestartMarker=110;
	public short ServiceReadyInMin=120;
	public short TransferStarting=125;
	public short OpenConnection=150;
	public short OK=200;
	public short CommandNI=202;
	public short SysStatReply=211;
	public short DirStatReply=212;
	public short FileStatReply=213;
	public short HelpMessageReply=214;
	public short SysTypeReply=215;
	public short ServiceReady=220;
	public short LogOffNetw=221;
	public short DataConOpen=225;
	public short CloseDataCon=226;
	public short EnterPassMode=227;
	public short LogOnNetw=230;
	public short FileActionCom=250;
	public short PathNameCreated=257;
	public short PassReq=331;
	public short AccountNameReq=332;
	public short FileActionPending=350;
	public short ServiceShuttingDown=421;
	public short CannotOpenDataCon=425;
	public short ConnectionClosed=426;
	public short FileUnavaiable=450;
	public short LocalErrorFound=451;
	public short InsufDiskSpace=452;
	public short InvalidCommand=500;
	public short BadParameter=501;
	public short CommandNI2=502;
	public short BadCommSeq=503;
	public short ParInvForComm=504;
	public short NotLoggedIntoNetw=530;
	public short NeedAccStoreFiles=532;
	public short FileUnavaiable2=550;
	public short PageTypeUnknown=551;
	public short StorageAllocExceed=552;
	public short FileNameNotAllowed=553; 	
}
