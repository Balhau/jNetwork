package balhau.network.rtsp;


/**
 * Relative Timestamp. Classe que representa a informação temporal do atributo range no protoolo
 * RTSP.
 * @author balhau
 *
 */
public class SMTPE {
	public int Hora;
	public int Minuto;
	public int Segundo;
	public int Frame;
	public int Subframe;
	
	public SMTPE(int hora,int minuto,int segundo,int frame,int subframe){
		Hora=hora;
		Minuto=minuto;
		Segundo=segundo;
		Frame=frame;
		Subframe=subframe;
	}
	/**
	 * Devolve uma string com a informação de tempo presente no objecto
	 * @return {@link String} Tempo no formato SMTPE 
	 */
	public String getSMTPEString(){
		if(Hora==-1)
			return "";
		else if(Minuto==-1)
			return Hora+"";
		else if(Segundo==-1)
			return Hora+":"+Minuto;
		else if(Frame==-1){
			return Hora+":"+Minuto+":"+Segundo;
		}
		else if(Subframe==-1)
			return Hora+":"+Minuto+":"+Segundo+":"+Frame;
		return Hora+":"+Minuto+":"+Segundo+":"+Frame+"."+Subframe;
	}
	
	/**
	 * Converte um SMTPE representado sob a forma de string para o objecto {@link SMTPE}
	 * @param time {@link String}
	 * @return {@link SMTPE} Objecto que representa o tempo
	 */
	public static SMTPE fromString(String time){
		String ttime=time.trim();
		if(ttime.equals(""))
			return new SMTPE(-1, -1, -1, -1, -1);
		String spl[]=ttime.split(":");
		String tmp[];
		int hora=-1;
		int minuto=-1;
		int segundo=-1;
		int frame=-1;
		int subframe=-1;
		
		hora=Integer.parseInt(spl[0]);
		if(spl.length>1)
			minuto=Integer.parseInt(spl[1]);
		if(spl.length>2)
			segundo=Integer.parseInt(spl[2]);
		if(spl.length>3){//caso em que estão definidos os quatro campos
			tmp=spl[3].split(".");
			frame=Integer.parseInt(tmp[0]);
			if(tmp.length==2)//caso em que foi definido o subframe
				subframe=Integer.parseInt(tmp[1]);
		}
		return new SMTPE(hora, minuto, segundo, frame, subframe);
	}
}
