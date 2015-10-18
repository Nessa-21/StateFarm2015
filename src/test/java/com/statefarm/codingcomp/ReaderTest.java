package com.statefarm.codingcomp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

import com.statefarm.codingcomp.model.Email;
import com.statefarm.codingcomp.model.User;
import com.statefarm.codingcomp.reader.Reader;

public class ReaderTest {
	
	private HashMap<String,User> userList;
	private Reader reader = new Reader();
	
	@Before
	public void test() {
		userList = new HashMap<>();
	}
	
	@Test 
	public void getAllUsersTest() throws Exception{
		reader.getAllUsers();
	}
	
	@Test 
	public void saveUserRecordTest(){
	
		reader.saveUserRecord(userList, "e4ae6b95-af45-4938-b090-ad2b80769946,Clarence Graham,cgraham1p@imgur.com");
		assertEquals(1,userList.size());
		
		String userKey = "e4ae6b95-af45-4938-b090-ad2b80769946";
		User expectedUser = new User("Clarence Graham", "cgraham1p@imgur.com");
		User user = userList.get(userKey);
		
		assertEquals(true, user.equals(expectedUser));
	}
	
	
	@Test
	public void userDuplicatesTest(){
		reader.saveUserRecord(userList, "e4ae6b95-af45-4938-b090-ad2b80769946,Clarence Graham,cgraham1p@imgur.com");
		reader.saveUserRecord(userList, "e4ae6b95-af45-4938-b090-ad2b80769946,any-name,any-email");
		
		assertEquals(1,userList.size());
	}
	
	@Test
	public void getAllEmails() throws Exception{
		ArrayList<Email> emails = reader.getAllEmails();
		assertNotNull(emails);
		assertTrue( emails.size() > 0);
		
	}
	
	@Test 
	public void noEmailHasNullDateTest() throws Exception{
		ArrayList<Email> emails = reader.getAllEmails();
		
		for (Email i : emails){
			
			assertNotNull(i.getSent());
			assertNotNull(i.getSent().getTime());
		}
		
	}
	
	
	
	
	

}
