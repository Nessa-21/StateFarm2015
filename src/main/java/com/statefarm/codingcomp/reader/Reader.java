package com.statefarm.codingcomp.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import com.statefarm.codingcomp.model.Email;
import com.statefarm.codingcomp.model.User;

public class Reader {

    /**
     * This method will read in a given file on a given node and return the
     * contents of the file as a String array. Each entry in the array
     * represents a single line in the file which is a comma separated
     * representation of either an Email object or a User object.
     * 
     * @param nodeNumber
     * @param filename
     * @return
     * @throws Exception
     */

    public String[] read( int nodeNumber, String filename ) throws Exception {
        URL url = this.getClass().getResource( "/node" + nodeNumber + "/" + filename );
        URI uri = new URI( url.toString() );
        File file = new File( uri.getPath() );
        FileInputStream fis = new FileInputStream( file );
        byte[] data = new byte[(int) file.length()];
        try {
            fis.read( data );
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( fis != null ) {
                fis.close();
            }
        }
        return new String( data, "UTF-8" ).split( "\n" );
    }
    
    
    
    public ArrayList<Email> getAllEmails() throws Exception{
    	ArrayList<Email> emails = new ArrayList<>();
    	String[] currentFile = null;
    	
    	int nodeCount = 1; 
    	while ( nodeCount < 5){
       		
    		currentFile = read(nodeCount,"mail.txt");
    		for(String i : currentFile){
        		saveEmail(emails,i);
        	}
    		nodeCount++;
    	}
    	return emails;
    }
    
    public void saveEmail(ArrayList<Email> emails, String currentLine){
    	
    	if(currentLine == null || currentLine.length() <= 0 ){
    		return;
    	}
    	
    	ArrayList<String> data = new ArrayList<String>();
    	String currentData = "";
    	
    	// Save to-email, from-email, date
    	for (char i : currentLine.toCharArray() ){
    		
    		if ( i == ','){
    			data.add(currentData);
    			currentData = "";
    			continue;
    		}
    		currentData += String.valueOf(i);
    	}
        data.add(currentData);
    	
    	// Check an id email and email exist
    	if ( data.size() == 3){
    		
    		String dateString = data.get(2);
    		Calendar calendar = getCalendar(dateString);
    		
    		if (calendar != null){
    		  emails.add(new Email(data.get(0),data.get(1),calendar.getTime()));
    		}
    	}
    	
    }
    
	public Calendar getCalendar(String dateString){
    	
		
		dateString = dateString.replaceAll("Z", "");
		String[] dataArray = dateString.split("T");
		
		if ( dataArray.length != 2){
			return null;
		}
		
		String[] dateArray = dataArray[0].split("-");
		String[] timeArray = dataArray[1].split(":");
    	ArrayList<Integer> dateData = new ArrayList<>();
    	
    	for (String i : dateArray){
    		dateData.add(Integer.valueOf(i));
    	}
    	
    	for (String i : timeArray){
    		dateData.add(Integer.valueOf(i));
    	}
    	
    	if (dateData.size() == 6 ){
    		
    	    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    	    calendar.set(
    	    		dateData.get(0),dateData.get(1),
    	    		dateData.get(2),dateData.get(3),
    	    		dateData.get(4),dateData.get(5)
    	    		);
    		return calendar;
    	}
    	
    	return null;
    }
    
    public HashMap<String,User> getAllUsers() throws Exception{
    	
    	HashMap<String,User> users = new HashMap<String,User>();
    	String[] currentFile = null;
    	
    	int nodeCount = 1; 
    	while ( nodeCount < 5){
       		
    		currentFile = read(nodeCount,"users.txt");
    		for(String i : currentFile){
        		saveUserRecord(users,i);
        	}
    		nodeCount++;
    	}
    	
		return users;
    }
    
    public void saveUserRecord(HashMap<String,User> users,String currentLine){
    	
    	if(currentLine == null || currentLine.length() <= 0 ){
    		return;
    	}
    	
    	ArrayList<String> data = new ArrayList<String>();
    	String currentData = "";
    	
    	// Save Id, Name, and e-mail
    	for (char i : currentLine.toCharArray() ){
    		
    		if ( i == ','){
    			data.add(currentData);
    			currentData = "";
    			continue;
    		}
    		currentData += String.valueOf(i);
    	}
        data.add(currentData);
    	
    	// Check an id email and email exist
    	if ( data.size() == 3){
    		
    		// Check the current user is not already stored
    		if ( users.get(data.get(0)) == null){
    			
    			users.put(data.get(0), new User(data.get(1), data.get(2)));
    		}
    	}
    }
    
}
