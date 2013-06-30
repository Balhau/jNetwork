package balhau.network.upnp.xml.service;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import balhau.utils.Sys;

@XmlRootElement
public class Scpd {
	
	@XmlAttribute
	public String xmlns;
	public SpecVersion specVersion;
	public ActionList actionList;
	public ServiceStateTable serviceStateTable;
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		return sb.toString();
	}
}
