package com.statefarm.codingcomp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import com.statefarm.codingcomp.model.Email;
import com.statefarm.codingcomp.model.User;
import com.statefarm.codingcomp.reader.Reader;

public class QueryServiceImpl implements QueryService {
	HashMap<String, User> userList;
	ArrayList<Email> emailList;
	
    Reader reader = new Reader();
    
    public QueryServiceImpl() {
    	try {
			userList = reader.getAllUsers();
			emailList = reader.getAllEmails();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public List<User> usersByDomain( String domain ) throws Exception {
        List<User> usersDomain = new ArrayList<User>();
        
        for(String key : userList.keySet()){
        	String userEmail = userList.get(key).getEmail();
        	if(userEmail.contains("@")){
        		String[] tokens = userEmail.split("@");
            	if(tokens[1].equalsIgnoreCase(domain) ){
            		usersDomain.add(userList.get(key));
            	}
        	}
        }
        return usersDomain;
    }

    @Override
    public List<Email> emailsInDateRange( Date start, Date end ) throws Exception {
        List<Email> emailsInRange = new ArrayList<Email>();
        
        for(Email email : emailList){
        	Date date = email.getSent();
        	if( ( date.after(start) || date.equals(start) ) && (date.before(end) || date.equals(end) ) ){
        		long time = date.getTime();
        		if(time >= start.getTime() && time <= end.getTime() ){
        			emailsInRange.add(email);
        		}
        	}
        }
        return emailsInRange;
    }

    @Override
    public Map<String, List<Email>> emailsByDay() throws Exception {
        Map<String, List<Email>> emailsListByDay = new HashMap<String, List<Email>>();
        
        emailsListByDay.put("Monday", new ArrayList<Email>());
        emailsListByDay.put("Tusday", new ArrayList<Email>());
        emailsListByDay.put("Wednesday", new ArrayList<Email>());
        emailsListByDay.put("Thursday", new ArrayList<Email>());
        emailsListByDay.put("Friday", new ArrayList<Email>());
        emailsListByDay.put("Saturday", new ArrayList<Email>());
        emailsListByDay.put("Sunday", new ArrayList<Email>());
        
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        for(Email email : emailList){
        	Date date = email.getSent();
        	cal.setTime(date);
        	int day = cal.get(Calendar.DAY_OF_WEEK);
        	
        	if(day == Calendar.MONDAY){
        		emailsListByDay.get("Monday").add(email);
        	}else if(day == Calendar.TUESDAY){
        		emailsListByDay.get("Tuesday").add(email);
        	}else if(day == Calendar.WEDNESDAY){
        		emailsListByDay.get("Wednesday").add(email);
        	}else if(day == Calendar.THURSDAY){
        		emailsListByDay.get("Thursday").add(email);
        	}else if(day == Calendar.FRIDAY){
        		emailsListByDay.get("Friday").add(email);
        	}else if(day == Calendar.SATURDAY){
        		emailsListByDay.get("Saturday").add(email);
        	}else if(day == Calendar.SUNDAY){
        		emailsListByDay.get("Sunday").add(email);
        	}
        	
        }
        return emailsListByDay;
    }

    @Override
    public int emailsOnDay( Date date ) throws Exception {
        int numOfEmails = 0;
        
        Calendar calCompare = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calCompare.setTime(date);
        calCompare.set(Calendar.HOUR_OF_DAY, 0);
        calCompare.set(Calendar.MINUTE, 0);
        calCompare.set(Calendar.SECOND, 0);
        calCompare.set(Calendar.MILLISECOND, 0);
        
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        for(Email email : emailList){
        	Date d = email.getSent();
        	cal.setTime(d);
        	cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            
        	if(cal.compareTo(calCompare) == 0){
        		numOfEmails++;
        	}
        }
        return numOfEmails;
    }

    @Override
    public Map<User, List<Email>> emailsFromOurUsers() throws Exception {
        Map<User, List<Email>> emailsFromUsers = new HashMap<User, List<Email>>();
        
        for( String userKey : userList.keySet()){
        	emailsFromUsers.put(userList.get(userKey), new ArrayList<Email>());
        }
        
//        for(Email email : emailList){
//        	String from = email.getFrom();
//        	if(emailsFromUsers.containsValue(User))
//        }
        return null;
    }

    @Override
    public Map<User, List<Email>> emailsToOurUsers() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Email> emailsToUserFromUser() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> emailAddressesByDegrees( String email, int degrees ) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int degreesBetween( String emailAddressOne, String emailAddressTwo ) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String mostConnected() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String mostActiveSender() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String mostActiveReceiver() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String mostSingularSender() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String mostSelfEmails() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int mostPopularHour() throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int mostPopularHour( String email ) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int leastPopularHour() throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int leastPopularHour( String email ) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String mostPopularDate() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String mostPopularDate( String email ) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String leastPopularDate() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String leastPopularDate( String email ) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
