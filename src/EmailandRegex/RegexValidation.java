package EmailandRegex;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexValidation {

	/*  
	    The following regular expression may not get 100% of valid email addresses but it will be very close. 
	    Derivation found here: http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
		^                          -- start of the line
		[_a-zA-Z0-9-\\+]+          -- must start with string in the bracket [ ], must contains one or more (+)
		(			               -- start of group #1
		\\.[_A-Za-z0-9-]+	       -- follow by a dot "." and string in the bracket [ ], must contains one or more (+)
		)*			               -- end of group #1, this group is optional (*)
		@			               -- must contains a "@" symbol
		[A-Za-z0-9-]+              -- follow by string in the bracket [ ], must contains one or more (+)
		(			               -- start of group #2 - first level TLD checking
		\\.[A-Za-z0-9]+            -- follow by a dot "." and string in the bracket [ ], must contains one or more (+)
		)*		                   -- end of group #2, this group is optional (*)
		(			               -- start of group #3 - second level TLD checking
		\\.[A-Za-z]{2,}            -- follow by a dot "." and string in the bracket [ ], with minimum length of 2
		)			               -- end of group #3
		$			               -- end of the line	 
	 */
	private static String emailregex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static Pattern emailpattern = Pattern.compile(emailregex);
	
	/*
		^                 # start-of-string
		(?=.*[0-9])       # a digit must occur at least once
		(?=.*[a-z])       # a lower case letter must occur at least once
		(?=.*[A-Z])       # an upper case letter must occur at least once
		(?=.*[@#$%^&+=])  # a special character must occur at least once
		(?=\\S+$)         # no whitespace allowed in the entire string
		.{8,}             # anything, at least eight places though
		$                 # end-of-string	
	 */
	//private static String passwordregex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	private static String passwordregex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	private static Pattern passwordpattern = Pattern.compile(passwordregex);
	

	/*
	^                 # start-of-string
	(?=.*[0-9])       # a digit must occur at least once
	(?=.*[a-zA-Z])    # an upper or lower case letter must occur at least once
	.{8,}             # anything, at least eight places though
	$                 # end-of-string	
	 */
	private static String simplepasswordregex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
	private static Pattern simplepasswordpattern = Pattern.compile(simplepasswordregex);
	
    public static boolean validSimplePassword(String password)
    {
        Matcher matcher = simplepasswordpattern.matcher(password);
        return matcher.find();
    }
	
    public static boolean validPassword(String password)
    {
        Matcher matcher = passwordpattern.matcher(password);
        return matcher.find();
    }

    public static boolean validEmailAddress (String emailaddress)
    {
        Matcher matcher = emailpattern.matcher(emailaddress);
        return matcher.find();
    }
    
    // -- state machine matcher for the complex password pattern
   public static boolean stateMachine (String emailaddress) 
    {
    	boolean result = true;
    	int state = 0; // -- start state
    	int cursor = 0;
    	
    	while (state != -99) { // -- terminal state
    		char ch = emailaddress.charAt(cursor);
    		switch (state) {
    		case 0: // -- look for the first character
    			if ((ch == '_') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || (ch == '-') || (ch == '+')) {
    				state = 1;
    			}
    			else {
    				result = false;
    				state = -99;
    			}
    			break;
    		case 1: // -- look for more characters (stay in this state), . (back to previous state), @ (next state)
    			if ((ch == '_') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || (ch == '-') || (ch == '+')) {
    				state = 1;
    			}
    			else if (ch == '.') {
    				state = 0;
    			}
    			else if (ch == '@') {
    				state = 2;
    			}
    			else {
    				result = false;
    				state = -99;
    			}
    			break;
    		case 2: // look for first character after @
    			if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || (ch == '-')) {
    				state = 3;
    			}
    			else {
    				result = false;
    				state = -99;
    			}
    			break;
    		case 3: // -- look for second character after @ (next state), . (back to previous state)
    			if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || (ch == '-')) {
    				state = 3;
    			}
    			else if (ch == '.') {
    				state = 4;
    			}
    			else {
    				result = false;
    				state = -99;
    			}
    			break;
    		case 4: // -- look for subsequent characters after @ (stay in this state), . (back 2 states)
    			if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || (ch == '-')) {
    				state = 5;
    			}
    			else {
    				result = false;
    				state = -99;
    			}
    			break;
    		case 5: // -- look for subsequent characters after @ (stay in this state), . (back 2 states)
    			if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || (ch == '-')) {
    				state = 6;
    			}
    			else if (ch == '.') {
    				state = 4;
    			}
    			else {
    				result = false;
    				state = -99;
    			}
    			break;
    		case 6: // -- look for subsequent characters after @ (stay in this state), . (back 2 states)
    			if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || (ch == '-')) {
    				state = 6;
    			}
    			else if (ch == '.') {
    				state = 4;
    			}
    			else {
    				result = false;
    				state = -99;
    			}
    			break;
     		}
    		
    		++cursor;
    		// -- if we hit the end of string and not seen an illegal character then success and done
    		if (cursor == emailaddress.length()) {
    			if (state == 6) {
    				result = true;
    			}
    			else {
    				result = false;
    			}
    			state = -99;
    		}
    	}
    	
    	return result;
    }
   

}
