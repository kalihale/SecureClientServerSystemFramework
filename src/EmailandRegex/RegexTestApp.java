package EmailandRegex;

import java.util.Scanner;

public class RegexTestApp {

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		try {
			String emailaddr;
	
			do {
				System.out.print("Enter an email address: ");
				emailaddr = kb.next().toUpperCase();
			} while (!RegexValidation.validEmailAddress(emailaddr));
	
	
			String password;
			do {
				System.out.print("Enter a password: ");
				password = kb.next();
			} while (!RegexValidation.validSimplePassword(password));
		}
		catch (Exception e) {
			System.out.println("Invalid input");
		}
		
		kb.close();
		System.out.println("finish");
	}

}
