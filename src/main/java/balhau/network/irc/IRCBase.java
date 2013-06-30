package balhau.network.irc;

import balhau.network.base.AbstractClient;
	
public class IRCBase extends AbstractClient{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1587371229524501813L;

	public IRCBase(String host,int port,boolean debug){
		super(host,port,debug);
	}
}
