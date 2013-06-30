package balhau.network.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import balhau.network.Message;
import balhau.utils.JSONSerialize;
import balhau.utils.Log;

public class AbstractNode extends JSONSerialize{
	protected String _host;			//String com o nome do host
	protected PrintWriter _pw;			//Objecto de escrita no stream de servidor
	protected BufferedReader _br; 		//Objecto de leitura do stream de servidor
	protected int _porta;				//Numero da porta de ligação
	protected boolean _debug;
	public static int THREAD_SLEEP=200;
	
	public void setHost(String host){
		_host=host;
	}
	
	public void setPorta(int porta){
		_porta=porta;
	}
	
	protected void closeDescriptors() throws IOException{
		if(_pw!=null){
			_pw.close();
			_pw=null;
		}
		if(_br!=null){
			_br.close();
			_br=null;
		}
	}
	
	public String getLine(){
		try{
			if(_br.ready()){
				return _br.readLine();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Métdodo que espera pela resposta do servidor.
	 * @throws IOException
	 */
	public void waitForServer() throws IOException{
		while(!_br.ready()){
			try{
				Thread.sleep(THREAD_SLEEP);
			}catch (Exception e) {
				Log.log(e.getMessage());
			}
		}
	}
	public String getAll() throws IOException{
		StringBuffer sb=new StringBuffer();
		waitForServer();
		String aux;
		while(_br.ready() && (aux=_br.readLine())!=null){
			sb.append(aux+Message.EOL);
		}
		return sb.toString();
	}

	
	protected void msg2h(String msg){
		_pw.write(msg);
		_pw.flush();
		if(_debug)
			Log.log("C->H: "+msg);

	}
}
