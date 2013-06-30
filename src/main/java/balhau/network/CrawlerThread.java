package balhau.network;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import balhau.metastruct.Lista;
/**
 * Esta classe extende a classe Thread. É utilizada na implementação de webcrawlers multithreaded.
 * @author balhau
 *
 */
public class CrawlerThread extends Thread{
	private String padrao;
	private Pattern regxL;
	private Pattern regxP;
	private static String HTTPREG="http://(www|[^w]{3})[a-z.]+\\.[a-z]{0,3}\\.*";
	public static String RES_FILE="res.txt";
	public static String LOG_FILE="log.txt";
	private HttpConn cn;
	private String source;
	private String txtfind;
	private Lista<String> _lnks;
	private Lista<String> _res;
	public static long ntotal;
	private static int tthread;
	private static int fact=10;
	private int nthread;
	private int _dimbuff;
	private OutputStreamWriter osh;
	private OutputStreamWriter oshl;
	private OutputStreamWriter osp;
	
	/**
	 * Construtor do objecto
	 * @param regex {@link String} representando a expressão regular de procura 
	 * @param source {@link String} representando o endereço inicial de procura
	 * @throws FileNotFoundException 
	 * @throws SecurityException 
	 */
	public CrawlerThread(String regex,String source) throws SecurityException, FileNotFoundException
	{
		osh=new OutputStreamWriter(new FileOutputStream(RES_FILE));
		oshl=new OutputStreamWriter(new FileOutputStream(LOG_FILE));
		this.setPriority(MIN_PRIORITY);
		this.padrao=regex;
		this.source=source;
		this.regxP=Pattern.compile(regex);
		this.regxL=Pattern.compile(HTTPREG);
		this.cn=new HttpConn(80);
		this._lnks=new Lista<String>();
		this._lnks.addValue(source);
		this._dimbuff=100;
		nthread=tthread;
		tthread++;
	}
	/**
	 * Construtor do objecto
	 * @param source {@link String} representando o endereço inicial de procura
	 * @param patt {@link String} representando o padrão de procura
	 * @param Buff {@link int} representando a dimensão do buffer
	 * @throws InterruptedException
	 * @throws FileNotFoundException 
	 */
	public CrawlerThread(String source,String patt,int Buff) throws InterruptedException, FileNotFoundException{
		osh=new OutputStreamWriter(new FileOutputStream(RES_FILE));
		oshl=new OutputStreamWriter(new FileOutputStream(LOG_FILE));
		this.setPriority(MIN_PRIORITY);
		this.padrao=HTTPREG;
		this.cn=new HttpConn(80);
		this.regxL=Pattern.compile(HTTPREG);
		this.regxP=Pattern.compile(patt);
		this._lnks=new Lista<String>();
		this._res=new Lista<String>();
		this._lnks.addValue(source);
		this._dimbuff=Buff;
		nthread=tthread;
		tthread++;
	}
	
	private void sortBuffer(){
		Lista<String> nova=new Lista<String>();
		int pos;
		for(int i=0;i<(int)_dimbuff/fact;i++){
			pos=(int)Math.floor(Math.random()*_lnks.getNumElementos());
			nova.addValue(_lnks.remValor(pos));
		}
		_lnks=nova;
	}
	
	private synchronized void logValue(OutputStreamWriter o,String val) throws IOException{
		o.write(val);
		o.flush();
	}
	
	public synchronized void run(){
		int nel=_lnks.getNumElementos();
		while(true){
			try{
				if(nel==0)
					this._lnks.addValue(source);
				if(nel>_dimbuff){
					sortBuffer();
				}
				int ind=(int)Math.floor(Math.random()*_lnks.getNumElementos());
				
				String end=_lnks.remValor(ind);
				logValue(oshl, "->"+end+"\n");
				txtfind=this.cn.getRequest(end);
				Matcher mt=regxL.matcher(txtfind);
				Matcher mtP=regxP.matcher(txtfind);
				while(mt.find()){
					_lnks.addValue(mt.group());
				}
				while(mtP.find()){
					String grupo=mtP.group();
					logValue(osh, end+"\t"+grupo+"\n");
					ntotal++;
				}
				nel=_lnks.getNumElementos();
				//System.out.println("Thread "+nthread+", LNKS FOUND: "+_lnks.getNumElementos());
				//System.out.println("Pattern Found: "+ntotal);
			}
			catch (Exception e) {
				System.out.println("Erro na thread: "+nthread+": "+e.getLocalizedMessage());
			}
			try{sleep(200);}catch(Exception e){};
		}
	}
}
