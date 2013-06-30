package balhau.network.irc;
/**
 * Interface que identifica os vários eventos associados ao cliente de irc
 * @author balhau
 *
 */
public interface IEventClient {
	public void onServerMessage(IRCMessage msgserver,IRCCCient cl);
	public void onQuit(IRCCCient cl);
}
