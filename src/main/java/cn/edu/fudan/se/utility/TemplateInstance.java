package cn.edu.fudan.se.utility;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TemplateInstance implements Serializable   {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement
	public String submitterName;
	@XmlElement
	public String goalModelName;
	@XmlElement
	public String templateName;
	@XmlElement
	public String templateInstance;
}

