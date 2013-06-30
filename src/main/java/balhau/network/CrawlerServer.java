package balhau.network;

/**
 * Classe que irá representar um servidor crawler. Será responsável por gerir o número de threads
 * em funcionamento e deverá fornecer uma API para que seja possível fazer-se a gestão do servidor
 * API:
 * SET <prop> <value> --> especifica uma propriedade do servidor
 * KILL SERVER --> para o servidor
 * @author balhau
 *
 */
public class CrawlerServer {
	private int porta;
	private int nthreads;
	private CrawlerThread[] threads;
	/**
	 * Construtor do servidor Crawler
	 * @param porta {@link int} representa a porta onde ficará a correr o servidor
 	 * @param nthreads {@link int} representa o número de threads máximas em funcionamento
	 */
	public CrawlerServer(int porta,int nthreads){
		this.porta=porta;
		this.nthreads=nthreads;
		threads=new CrawlerThread[nthreads];
	}
	
	private void StartThreads(){
		for(int i=0;i<nthreads;i++){
			threads[i].start();
		}
	}
}
