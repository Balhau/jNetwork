package balhau.network.rtsp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe base para um servidor de RTSP
 * @author balhau
 *
 */
public abstract class RtspServerBase {
	protected int porta;
	protected boolean on;
	protected ServerSocket sock;
	
	public RtspServerBase(int porta) {
		this.porta=porta;
	}
	
	public void startServer() throws IOException{
		sock=new ServerSocket(this.porta);
		on=true;
		Socket client;
		RtspServerThread st;
		while(on){
			client=sock.accept();
			st=new RtspServerThread(sock, client);
			st.run();
		}
	}
	
	public void stopServer(){
		on=false;
	}
}
