package com.vin.demo.bean;

import java.util.List;

public class Person {

	public String name;
	public String emailId;
	public List<String> hobbies;
	public String comment;
	
	public Person() {
	}
	public Person(String name, String emailId, List<String> hobbies, String comment) {
		this.name = name;
		this.emailId = emailId;
		this.hobbies = hobbies;
		this.comment = comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public List<String> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
