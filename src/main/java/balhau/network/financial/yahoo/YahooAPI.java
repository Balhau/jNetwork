package balhau.network.financial.yahoo;

import balhau.metastruct.Lista;
import balhau.network.HttpConn;
import balhau.network.Message;
import balhau.network.financial.IFinancial;
/**
 * Classe que utiliza a api da Yahoo para obter dados do mercado financeiro<br><br>
 * <b>Referências:</b><br>
 * <a href="http://stackoverflow.com/questions/5246843/how-to-get-a-complete-list-of-ticker-symbols-from-yahoo-finance">Lista de símbolos financeiros</a><br>
 * <a href=""></a><br>
 * @author balhau
 *
 */
public class YahooAPI implements IFinancial{
	public static String API_HOST="download.finance.yahoo.com";
	public static String API_URL="http://"+API_HOST+"/d/quotes.csv";
	private Lista<YahooAPITag> commands;
	private Lista<String> stockSymbols;
	private HttpConn httpcon;
	
	/**
	 * Construtor do objecto
	 */
	public YahooAPI(){
		commands=new Lista<YahooAPITag>();
		stockSymbols=new Lista<String>();
		httpcon=new HttpConn();
	}
	/**
	 * Adição de um novo símbolo financeiro
	 */
	public void addStockSymbol(String stocksymbol){
		stockSymbols.addValue(stocksymbol);
	}
	/**
	 * Adição de uma tag que representa uma coluna de dados a ser devolvida
	 **/
	public void addTag(YahooAPITag tag){
		commands.addValue(tag);
	}
	/**
	 * Adição de um array de tags à lista de tags
	 **/
	public void addTags(YahooAPITag[] tags){
		commands.addValues(tags);
	}
	private String getStockSymbolsURL(){
		StringBuilder sb=new StringBuilder();
		String[] ss=stockSymbols.toArray();
		for(int i=0;i<ss.length;i++){
			if(i!=0)
				sb.append("+");
			sb.append(ss[i]);
		}
		return sb.toString();
	}
	
	private String getTagsURL(){
		StringBuilder sb=new StringBuilder();
		YahooAPITag[] tags=commands.toArray();
		for(int i=0;i<tags.length;i++){
			sb.append(tags[i].getTag());
		}
		return sb.toString();
	}
	
	private String parseData(String htmlval){
		String[] linhas=htmlval.split(Message.LF);
		StringBuilder sb=new StringBuilder();
		for(int i=1;i<linhas.length-2;i++){
			sb.append(linhas[i]+Message.LF);
		}
		return sb.toString();
	}
	/**
	 * Devolve dados do mercado financeiro
	 * @return {@link String} Dados do mercado financeiro
	 */
	public String getData() {
		String url=API_URL+"?s="+getStockSymbolsURL()+"&f="+getTagsURL();
		httpcon.setHeaderItem("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.19 (KHTML, like Gecko) Ubuntu/11.10 Chromium/18.0.1025.151 Chrome/18.0.1025.151 Safari/535.19");
		httpcon.setHeaderItem("Accept", "application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
		httpcon.setHeaderItem("Connection", "close");
		httpcon.setHeaderItem("Pragma", "no-cache");
		httpcon.setHeaderItem("Host","finance.yahoo.com");
		return parseData(httpcon.getResponseHTML(url));
	}
	/**
	 * Método que devolve todos os campos possíveis
	 * @return {@link String} Dados do mercado financeiro
	 */
	public String getAll(){
		commands=new Lista<YahooAPITag>();
		commands.addValues(YahooAPITag.values());
		return getData();
	}
}
