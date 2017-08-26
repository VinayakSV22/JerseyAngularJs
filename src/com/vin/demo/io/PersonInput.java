package com.vin.demo.io;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersonInput {

	public String name;
	public String emailId;
	public List<String> hobbies;
	public String comment;
	
	public PersonInput() {
	}
}
