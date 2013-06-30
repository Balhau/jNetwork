package balhau.network.rtp;

import balhau.utils.ByteBuffer;

/**
 * Classe que representa a estrutura presente num pacote de dados segundo o protocolo Real Time Protocol (RTP)<br><br>
 * <b>Referências:</b><br>
 * <a href="http://tools.ietf.org/html/rfc3550">RFC 3550. A transport protocol for Real Time Applications</a><br>
 * <a href="http://tools.ietf.org/html/rfc3551">RFC 3551. RTP Profile for Audio and Video Conferences with Minimal Control</a><br>
 * <a href=""></a><br>
 * @author balhau
 *
 */
public class RtpPacket{
	private short _version;								//2 bits
	private short _padding;								//1 Bit
	private short _extension;							//1 bit
	private short _CSRCCount;							//4 bit
	private short _marker;								//1 bit
	private short _payloadType;							//7 bits
	private short _seqNumber;							//16 bit
	private int _timeStamp;								//32 bit
	private int _SSRC;									//32 bit Synchronization source identifier
	private byte _payload[];							//Array de bytes representando os dados brutos presentes no pacote
	
	/**
	 * Método que devolve um array de bytes representando os dados presentes no RTPPacket
	 * @return {@link byte[]} Array de bytes presentes no pacote (Header + payload)
	 */
	public byte[] getData(){
		ByteBuffer bf=new ByteBuffer();
		int h1;
		h1=((_version&3)<<15);
		h1=(h1|(_padding&1)<<13);
		h1=(h1|(_extension&1)<<12);
		h1=(h1|(_CSRCCount&15)<<11);
		h1=(h1|(_marker&1)<<7);
		h1=(h1|(_payloadType&127));
		bf.writeShort((short)h1);
		bf.writeShort(_seqNumber);
		bf.writeInt(_timeStamp);
		bf.writeInt(_SSRC);
		bf.writeBytes(_payload);
		return bf.toByteArray();
	}
}
