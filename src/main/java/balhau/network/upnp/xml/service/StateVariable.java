package balhau.network.upnp.xml.service;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

public class StateVariable {
	@XmlAttribute
	public String sendEvents;
	public String name;
	public String dataType;
	public String defaultValue;
	public AllowedValueList allowedValueList;
}
