package balhau.network.irc;

import balhau.metastruct.Lista;
import balhau.network.Message;

/**
 * Class que representa a informação de um canal do IRC
 * @author balhau
 *
 */
public class IRCCanal {
	private String _name;
	private Lista<IRCUser> _users;
	
	public IRCCanal(String nome){
		_users=new Lista<IRCUser>();
		_name=nome;
	}
	
	public void addUser(IRCUser user){
		_users.addValue(user);
	}
	
	public IRCUser[] getUsers(){
		IRCUser[] usrs=new IRCUser[_users.getNumElementos()];
		usrs=_users.toArray();
		return usrs;
	}
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("Nome do canal: "+_name);
		_users.beginCursor();
		for(int i=0;i<_users.getNumElementos();i++){
			sb.append(_users.getValor().toString()+Message.EOL);
			_users.proximo();
		}
		return sb.toString();
	}
}
