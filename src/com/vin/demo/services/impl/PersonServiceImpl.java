package com.vin.demo.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vin.demo.bean.Person;
import com.vin.demo.helper.DBHelper;
import com.vin.demo.io.PersonInput;
import com.vin.demo.io.PersonOutPut;
import com.vin.demo.services.PersonService;

public class PersonServiceImpl implements PersonService{

	private Connection connection = DBHelper.getI().createConnection();
	
	@Override
	public boolean addPerson(PersonInput input) {
		
		Person person = new Person(input.name, input.emailId, input.hobbies, input.comment);
		String query = "insert into person(name, emailId, comment) values(?,?,?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, person.getName());
			preparedStatement.setString(2, person.getEmailId());
			preparedStatement.setString(3, person.getComment());
			
			
			preparedStatement.executeUpdate();
			
			PreparedStatement preStmtCount = connection.prepareStatement("Select max(id) as id from person");
			ResultSet rs = preStmtCount.executeQuery();
			int autoId = 0;
			if(rs.next()){
				autoId = rs.getInt("id");
			}
			
			//connection.commit();
			
			String hobyQuery = "insert into hobbies(hoby,pid) values(?,?)";
			for(String hoby: person.getHobbies()){
				PreparedStatement preStmt = connection.prepareStatement(hobyQuery);
				preStmt.setString(1, hoby);
				preStmt.setInt(2, autoId);
				
				preStmt.execute();
			}
			
			connection.commit();
			return true;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}

	@Override
	public List<PersonOutPut> getAllPesrsonData() {
		
		List<PersonOutPut> personList = new ArrayList<PersonOutPut>();
		String getPersonQuery = "Select * from person";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(getPersonQuery);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String emailId = resultSet.getString("emailId");
				String comment = resultSet.getString("comment");
				int count = 0;
				
			if(comment != null){	
				for(int i=0; i<comment.length(); i++){
					if(count>50){
						comment = comment.substring(0, 50);
						break;
					}
					else if(comment.charAt(i) != '$'){
						count++;
					}
				}
			}
				
				List<String> hobbies = getHobbies(id);
				
				PersonOutPut person = new PersonOutPut(id,name, emailId, hobbies, comment);
				personList.add(person);
			}
			
			return personList;
		}catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				if(resultSet != null)
					resultSet.close();
				
				if(preparedStatement != null)
					preparedStatement.close();
				
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	public List<String> getHobbies(int id){
		
		PreparedStatement pstmt = null;
		List<String> hobbies = new ArrayList<String>();
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement("Select * from hobbies where pid = ?");
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				String hoby = rs.getString("hoby");
				hobbies.add(hoby);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs != null)
					rs.close();
				
				if(pstmt != null)
					pstmt.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return hobbies;
	}
	
	public boolean deleteById(int id){
		PreparedStatement pstmtForPerson = null;
		boolean isDeleted = false;
		try {
			if(deleteHobbies(id) != 0){
				pstmtForPerson = connection.prepareStatement("delete from person where id = ?");
				pstmtForPerson.setInt(1, id);
				
				int rows = pstmtForPerson.executeUpdate();
				if(rows != 0){
					connection.commit();
					isDeleted = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(pstmtForPerson != null)
					pstmtForPerson.close();
				
				if(connection != null)
					connection.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return isDeleted;
	}
	
	public int deleteHobbies(int pId){
		PreparedStatement pstmt = null;
		int isDeletedHoby = 0;
		try {
			pstmt = connection.prepareStatement("delete from hobbies where pid = ?");
			pstmt.setInt(1, pId);
			
			isDeletedHoby = pstmt.executeUpdate();
			
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isDeletedHoby;
	}
	
	public boolean deleteAll(){
		PreparedStatement pstmt;
		boolean isDeleted = false;
		try {
			if(deleteHobbies() != 0){
				pstmt = connection.prepareStatement("delete from person");
				
				int rows = pstmt.executeUpdate();
				if(rows != 0){
					connection.commit();
					isDeleted = true;
				}
			}
		} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			e.printStackTrace();
		}finally{
				try {
					if(connection != null)
						connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return isDeleted;
	}
	
	public int deleteHobbies(){
		PreparedStatement pstmt;
		int rowsDeleted = 0;
		try {
			pstmt = connection.prepareStatement("delete from hobbies");
			
			 rowsDeleted = pstmt.executeUpdate();
			 if(rowsDeleted != 0)
				 connection.commit();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsDeleted;
	}
}
