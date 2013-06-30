package balhau.network.websocket.base;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import balhau.crypto.coding.base64;
import balhau.crypto.hash.SHA1;

/**
 * Classe abstracta que serve de contentor de funções utilitárias. Aqui devem ser colocadas 
 * as funções comuns, ou pelo menos potencialmente necessárias às diversas classes.
 * <br><br>
 * <b>Referências:</b><br>
 * <a href="http://tools.ietf.org/html/rfc6455">WebSocket Protocol</a>
 * @author balhau
 *
 */
public class WebSocketUtil{
	/**
	 * Método que devolve uma nova chave para o campo Sec-WebSocket-Key.
	 * São gerados 16 bytes aleatoriamente que são codificados no formato base64 e é este valor que é
	 * devolvido. 
	 * @return {@link String} Key no formato base64. 
	 */
	public static String webSocketClientKey(){
		byte[] data=new byte[16];
		for(int i=0;i<16;i++){
			data[i]=(byte)((int)Math.floor(Math.random()*255)&0xFF);
		}
		base64 b64=new base64();
		return b64.encode(data);
	}
	/**
	 * Método que efectua a construção da chave de resposta para efectuar o handshake com o cliente
	 * @param clientKey {@link String} Chave proveniente do cliente
	 * @return {@link String} Chave resposta para o handshake
	 */
	public static String webSocketServerKey(String clientKey){
		String encVal=clientKey+WebSocketCode.WS_GUID.value();
		SHA1 sha1=new SHA1();
		base64 b64=new base64();
		sha1.encode(encVal);
		return b64.encode(sha1.digest());
	}
	/**
	 * Método que efectua o parsing dos headers enviados pelo cliente 
	 * @param reader
	 * @return
	 * @throws IOException 
	 */
	public static void getHTTPHeaders(InputStream is,WebSocket ws) throws IOException{
		String aux;
		String[] split;
		
		while(!(aux=getLine(is)).equals("")){
			if(aux.indexOf(":")>0){
				split=getHTTPHeaderLine(aux);
				ws.putHeader(split[0], split[1]);
			}
			else break;
		}
	}
	/**
	 * Método utilitário para construção de um subarray de bytes
	 * @param array {@link byte[]} Array de bytes
	 * @param ini {@link int} Posição inicial
	 * @param end {@link int} Posição final
	 * @return {@link byte} Subarray de bytes
	 */
	private static byte[] subarray(byte[] array,int ini, int end){
		byte[] out=new byte[end-ini];
		if(end>array.length || ini < 0)
			return null;
		for(int i=ini;i<end;i++){
			out[i-ini]=array[i];
		}
		return out;
	}
	/**
	 * Método utilizado para efectuar a leitura de uma linha a partir do servidor
	 * @param is {@link InputStream} Stream de leitura
	 * @return {@link String} String a representar a linha de texto lida 
	 * @throws IOException Excepção de leitura de file descriptors
	 */
	public static String getLine(InputStream is) throws IOException{
		ByteBuffer bf=ByteBuffer.allocate(512);
		bf.clear();
		byte[] sub;
		int aux;
		int lf='\n';
		int cr='\r';
		boolean iscr=false;
		while(true){
			aux=is.read();
			if(aux==-1)
				return null;
			if(aux==cr){
				iscr=true;
				continue;
			}
			if(iscr && aux==lf){
				break;
			}
			bf.put((byte)aux);
		}
		bf.flip();
		sub=subarray(bf.array(), 0, bf.limit());
		return new String(sub);
	}
	/**
	 * Método que extrai o valor numérico da chave fornecida na chave presente no protocolo draft
	 * @param key {@link String} Chave 1 ou chave 2 do protocolo draft
	 * @return {@link long} Inteiro de 64bit com o valor numérico extraído a partir da chave
	 */
	public static long getDraftKeyNum(String key){
		String aux="";
		char chcd=0;
		for(int i=0;i<key.length();i++){
			chcd=key.charAt(i);
			if(chcd>='0' && chcd<='9'){
				aux+=chcd;
			}
		}
		return Long.parseLong(aux, 10);
	}
	public static int spacesInKey(String key){
		int spaces=0;
		for(int i=0;i<key.length();i++)
			if(key.charAt(i)==' ')
				spaces++;
		return spaces;
	}
	/**
	 * Parsing de uma linha de um cabeçalho HTTP
	 * @param line
	 * @return
	 */
	private static String[] getHTTPHeaderLine(String line){
		String[] entry=new String[2];
		int index=line.indexOf(":");
		entry[0]=line.substring(0, index);
		entry[1]=line.substring(index+1);
		return entry;
	}
}
