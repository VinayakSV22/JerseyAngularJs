package com.vin.demo.models;

import java.util.List;

import com.vin.demo.io.PersonOutPut;

public class PersonModel {

	public List<PersonOutPut> personList;

	public PersonModel(List<PersonOutPut> personList) {
		this.personList = personList;
	}
	
	public PersonModel() {
		// TODO Auto-generated constructor stub
	}
}
