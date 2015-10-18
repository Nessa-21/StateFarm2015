package com.statefarm.codingcomp.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        		String[] tokens = i.split(",");
        		
        		String dateTmp = tokens[2].substring(0, tokens[2].length()-1);
        		String[] dateTokens = dateTmp.split("T");
        		
        		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        		
        		Email email = new Email(tokens[0], tokens[1], sdf.parse(dateTokens[0]+" "+dateTokens[1]));
        		
        		emails.add(email);
        	}
    		nodeCount++;
    	}
    	return emails;
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
