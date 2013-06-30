package balhau.network.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import balhau.network.Message;
import balhau.utils.JSONSerialize;
import balhau.utils.Log;

/**
 * Classe abstracta para servir de base para a implementação de clientes nos variados protocolos de rede
 * @author balhau
 *
 */
public abstract class AbstractClient extends AbstractNode{
	
	protected Socket _sock;				//Socket TCP
	
	
	protected AbstractClient(){
		this("",0);
	}
	
	/**
	 * Método que efectua a connecção ao servidor e inicializa os objectos de escrita e leitura
	 * @return {@link boolean} Valor boleano identificando o sucesso ou insucesso da ligação
	 */
	protected boolean connect(){
		try{
			_sock=new Socket(_host,_porta);
			_pw=new PrintWriter(_sock.getOutputStream());
			_br=new BufferedReader(new InputStreamReader(_sock.getInputStream()));
			if(_debug){
				Log.log("Connecção Efectuada com sucesso:");
				Log.log("Servidor: "+_host);
				Log.log("Porta: "+_porta);
			}
			
		}catch (Exception e) {
			Log.log(e.getMessage());
			return false;
		}
		return true;
	}
	/**
	 * Método que efectua o fecho do socket e objectos de leitura e escrita.
	 * @return {@link boolean} Parâmetro que indica o sucesso/insucesso da operação.
	 */
	protected boolean disconnect(){
		try{
			closeDescriptors();
			if(_sock!=null){
				_sock.close();
				_sock=null;
			}
			if(_debug){
				Log.log("Limpeza do socket e fecho dos objectos de escrita e leitura de dados");
			}
			return true;
		}catch(Exception e){
			if(_debug) Log.log(e.getMessage());
			return false;
		}
	}
	
	protected AbstractClient(String host,int porta){
		this(host,porta,false);
	}
	protected AbstractClient(String host,int porta,boolean debugging){
		_host=host;
		_porta=porta;
		_debug=debugging;
	}
}
