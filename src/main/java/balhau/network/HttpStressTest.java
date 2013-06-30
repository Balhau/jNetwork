package balhau.network;

import balhau.utils.Log;

/**
 * Classe que efectua um teste de stress sobre uma página web
 * @author balhau
 *
 */
public class HttpStressTest extends Thread{
	private HttpConn cn;
	private String url;
	private String txt;
	private int iter;
	private int id;
	private boolean stoped;
	
	public HttpStressTest(String url,int niter,int id){
		String[] spl=url.split(":");
		if(spl.length==2){
			this.cn=new HttpConn(Integer.parseInt(spl[1]));
			this.url=spl[0];
		}else{
			this.cn=new HttpConn(80);
			this.url=url;
		}
		this.iter=niter;
		this.id=id;
		this.stoped=true;
	}
	
	public synchronized void run(){
		this.stoped=false;
		for(int i=0;i<this.iter;i++){
			this.txt=this.cn.getRequest(this.url);
			Log.log("Iteracção: "+i+", da thread "+this.id);
		}
		this.stoped=true;
	}
	
	public boolean getStoped(){
		return this.stoped;
	}
}
