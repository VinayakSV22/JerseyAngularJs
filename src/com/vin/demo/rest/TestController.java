package com.vin.demo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.vin.demo.common.CommonNotify;
import com.vin.demo.io.PersonInput;
import com.vin.demo.io.PersonOutPut;
import com.vin.demo.models.PersonModel;
import com.vin.demo.models.PersonResult;
import com.vin.demo.services.PersonService;
import com.vin.demo.services.impl.PersonServiceImpl;

@Path("/")
public class TestController {


	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PersonResult addPerson(PersonInput input){
		PersonService personService = new PersonServiceImpl();
		boolean added =  personService.addPerson(input);
		if(added){
			String notification = "Succefull!";
			return new PersonResult(notification);
		}
		return null;
	}
	
	@GET
	@Path("allpersons")
	@Produces(MediaType.APPLICATION_JSON)
	public PersonModel getPersonData(){
		PersonService personService = new PersonServiceImpl();
		List<PersonOutPut> personList = personService.getAllPesrsonData();
		
		return new PersonModel(personList);
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CommonNotify deleteById(@PathParam("id") Integer id){
		
		PersonService personService = new PersonServiceImpl();
		Integer error = 0;
		if(id != -1){
			if(personService.deleteById(id))
				error = 999;
		}else{
			if(personService.deleteAll())
				error = 777;
		}
		
		return new CommonNotify(error);
	}
	
/*	@GET
	@Path("{all}")
	@Produces(MediaType.APPLICATION_JSON)
	public CommonNotify deleteAll(@PathParam("all") String all){

		Integer error = 0;
		if(all.equals("all")){
			PersonService personService = new PersonServiceImpl();
			if(personService.deleteAll()){
				error = 777;
			}
		}
		
		return new CommonNotify(error);
	}*/
}
