package com.vin.demo.services;

import java.util.List;

import com.vin.demo.io.PersonInput;
import com.vin.demo.io.PersonOutPut;


public interface PersonService {

	public boolean addPerson(PersonInput personInput);
	
	public List<PersonOutPut> getAllPesrsonData();
	
	public boolean deleteById(int id);
	
	public boolean deleteAll();
}
