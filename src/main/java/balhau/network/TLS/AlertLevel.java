package balhau.network.TLS;
/**
 * Enumerado que indica o nível da mensagem de alerta
 * @author balhau
 *
 */
public enum AlertLevel {
	warning(1),
	fatal(2),
	other(255);
	
	private final int val;
	AlertLevel(int v){
		this.val=v;
	}
	public int val(){return val;}
}
