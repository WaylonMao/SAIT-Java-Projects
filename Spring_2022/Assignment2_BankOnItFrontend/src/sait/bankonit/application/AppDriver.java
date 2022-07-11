package sait.bankonit.application;

//import ca.bankonit.manager.*;
//import ca.bankonit.models.*;
import sait.bankonit.gui.*;

/**
 * Application driver
 * @author Waylon Mao (weilong.mao@edu.sait.ca)
 * @version June 30, 2022
 */
public class AppDriver {

	/**
	 * Entry point for program
	 * @param args
	 */
	public static void main(String[] args) {
		/* Uncomment for versions 1.0 - 3.0 */
		// long cardNumber = 4444111122223333L;
		// short pin = 4444;
		//
		// Account account = BankManagerBroker.getInstance().login(cardNumber, pin);
 		// AccountWindow accountWindow = new AccountWindow(account);
		// accountWindow.setVisible(true);
		//
		
		/* Uncomment for version 4.0 */
 		LoginWindow login = new LoginWindow();
		login.setVisible(true);
		//
	}

}
