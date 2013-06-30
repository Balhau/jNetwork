package balhau.network.TLS;

public class Alert {
	public AlertLevel level;
	public AlertDescription description;
	public Alert(AlertLevel l,AlertDescription d){
		this.level=l;
		this.description=d;
	}
}
