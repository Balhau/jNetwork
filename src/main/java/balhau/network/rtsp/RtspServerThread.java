package balhau.network.rtsp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RtspServerThread implements Runnable{
	private RtspCommand command_type;
	private RtspBase rtsp_base_object;
	private RtspParser parser;
	private Socket client;
	private ServerSocket server;
	private PrintWriter pwr;
	private BufferedReader br;
	
	public RtspServerThread(ServerSocket server,Socket client) throws IOException {
		this.server=server;
		this.client=client;
		pwr=new PrintWriter(client.getOutputStream());
		br=new BufferedReader(new InputStreamReader(client.getInputStream()));
		parser=new RtspParser();
	}
	public void run() {
	}
	
	private String readMessage(){
		return "";
	}
}
