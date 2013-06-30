package balhau.network.financial.yahoo;
/**
 * Enumerado que contém os comandos que se podem passar para a API da Yahoo<br><br>
 * <b>Referencias:<b><br>
 * <a href="http://ilmusaham.wordpress.com/tag/stock-yahoo-data/">Descrição não oficial da API</a><br>
 * @author balhau
 *
 */
public enum YahooAPITag {
	ASK("a","Ask",false),
	BID("b","Bid",false),
	BOOK_VALUE("b4","Book Value",false),
	CHANGE("c1","Change",false),
	AHC("c8","After Hours Change",true),
	TD("d2","Trade Date",false),
	EPS("e7","EPS Estimate Current Year",false),
	FS("f6","Float Shares",false),
	WL52("j","52 Week Low",false),
	AG("g3","Annualized Gain",false),
	HGR("g6","Holdings Gain",true),
	MC("j1","Market Capitalization",false),
	CF52L("j5","Change from 52 week low",false),
	CP("k2","Change Percent",true),
	PCF52H("k5","Percent change from 52 week high",false),
	HL("12","High Limit",false),
	DRR("m2","Day's Range",true),
	CF200MA("m5","Change from 200 day moving average",false),
	PCF50DMA("m8","Percent change from 50 day moving average",false),
	OPEN("o","Open",false),
	CIP("p2","Change in percent",false),
	EDD("q","Ex Dividend Date",false),
	PERR("r2","P/E Ratio",true),
	PEPSENY("r7","Price/EPS estimate next year",false),
	SR("s7","Short ratio",false),
	TT("t7","Ticker trend",false),
	HV("v1","Holdings value",false),
	DVC("w1","Day's value change",false),
	DY("y","Dividend yeld",false),
	ADV("a2","Average daily volume",false),
	ASKR("b2","Ask",true),
	BS("b6","Bid size",false),
	COMMISSION("c3","Commisison",false),
	DS("d","Dividend/share",false),
	ES("e","Earnings/share",false),
	EPSENY("e8","EPS estimate next year",false),
	DL("g","Day's low",false),
	WH52("k","52 week high",false),
	HG("g4","Holdings gain",false),
	MI("i","More Info",false),
	MCR("j3","Market Capital",true),
	PCF52WL("j6","Percent change from 52 week low",false),
	LTS("k3","Last trade size",false),
	LL("l3","Low limit",false),
	MA50D("m3","50 day Moving Average",false),
	PCF200DMA("m6","Percent change from 200 day moving average",false),
	NAME("n","Name",false),
	PC("p","Previous Close",false),
	PS("p5","Price Sales",false),
	PER("r","P/E ratio",false),
	PEGR("r5","PEG ratio",false),
	SYMBOL("s","Symbol",false),
	LTT("t1","Last trade time",false),
	OYTP("t8","One year target price",false),
	HVR("v7","Holdings Value",true),
	DVCR("w4","Day's value change",true),
	ASKS("a5","Ask Size",false),
	BIDR("b3","Bid",true),
	CPC("c","Change and Percent Change",false),
	CHANGER("c6","Change",true),
	LTD("d1","Last Trade Date",false),
	EI("e1","Error Indication",false),
	EPSENQ("e9","EPS Estimate Next Quarter",false),
	DH("h","Day's High",false),
	HGP("g1","Holdings Gain Percent",false),
	HGPR("g5","Holdings Gain Percent",true),
	OB("i5","Order book",true),
	EBITDA("j4","EBITDA",false),
	LTWT("k1","Last trade with time",true),
	CF52WH("k4","Change from 52 week high",false),
	LTPO("l1","Last Trade (Price Only)",false),
	DR("m","Day's Range",false),
	MA200D("m4","Moving Average 200 Day",false),
	CF50DMA("m7","Change from 50 day moving average",false),
	NOTES("n4","Notes",false),
	PP("p1","Price paid",false),
	PB("p6","Price/Book",false),
	DPD("r1","Dividend Pay Date",false),
	PEPSECY("r6","Price EPS Estimate Current Year",false),
	SO("s1","Shares Owned",false),
	TL("t6","Trade links",false),
	VOLUME("v","Volume",false),
	R52W("w","52 week range",false),
	SE("x","Stock Exchange",false)
	;
	private String command;
	private String desc;
	private boolean realTime;
	
	private YahooAPITag(String command,String desc,boolean realTime){
		this.command=command;
		this.desc=desc;
		this.realTime=realTime;
	}
	
	public boolean isRealTime(){
		return this.realTime;
	}
	
	public String getTag(){
		return command;
	}
	
	public String toString(){
		String rtime=realTime?" (Real Time)":"";
		return "["+this.command+","+this.desc+rtime+"]";
	}
}
