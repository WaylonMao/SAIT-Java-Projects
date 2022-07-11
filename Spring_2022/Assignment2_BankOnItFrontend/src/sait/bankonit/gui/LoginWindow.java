package sait.bankonit.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import ca.bankonit.manager.*;
import ca.bankonit.models.*;

/**
 * Renders the login window
 * 
 * @author Waylon Mao (weilong.mao@edu.sait.ca)
 * @version Jun 30, 2022
 */
public class LoginWindow extends JFrame implements ActionListener{
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is the Font for title part.
	 * 
	 * @Fields account_font
	 */
	private Font title_font = new Font("Verdana", Font.PLAIN, 26);
    
	/**
	 * This is the Font for body part.
	 * 
	 * @Fields body_font
	 */
    private Font body_font = new Font("Verdana", Font.PLAIN, 14);

	/**
	 * This is the JTextField to input card number.
	 * 
	 * @Fields cardNumber
	 */
    private JTextField cardNumber;
    
	/**
	 * This is the JPasswordField to input pin number.
	 * 
	 * @Fields pin
	 */
    private JPasswordField pin;
    
	/**
	 * This is the JButton to login.
	 * 
	 * @Fields loginButton
	 */
    private JButton loginButton;
    
	/**
	 * This is for holding the Account object.
	 * 
	 * @Fields account
	 */
    private Account account;

    /**
     * Initializes the login window.
     */
    public LoginWindow() {
        super("Bank On It Login");

        // Set window size to 500x150
        this.setSize(500, 150);

        // Cause process to exit when X is clicked.
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Center login window in screen
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.add(topPanel(), BorderLayout.NORTH);
        this.add(midPanel(), BorderLayout.CENTER);
        this.add(bottomPanel(), BorderLayout.SOUTH);
        
        // Show this window in the center of the screen.
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) screensize.getWidth() / 2 - this.getWidth() / 2;
		int y = (int) screensize.getHeight() / 2 - this.getHeight() / 2;
		this.setLocation(x, y);

    }

    /**
     * This is the top part of the login window.
     * 
     * @return The top part of the login window.
     */
    private JPanel topPanel() {
        JPanel panel = new JPanel();
        JLabel jLabel = new JLabel("Bank On It Login");
        jLabel.setFont(title_font);
        panel.add(jLabel);
        return panel;
    }

    /**
     * This is the middle part of the login window.
     * 
     * @return JPanel The middle part of the login window.
     */
    private JPanel midPanel() {
        JPanel panel = new JPanel();
        JLabel jLabel1 = new JLabel("Card Number: ");
        jLabel1.setFont(body_font);
        JLabel jLabel2 = new JLabel("PIN: ");
        jLabel2.setFont(body_font);
        cardNumber = new JTextField("", 15);
        cardNumber.setFont(body_font);
        pin = new JPasswordField("", 4);
        pin.setFont(body_font);
        panel.add(jLabel1);
        panel.add(cardNumber);
        panel.add(jLabel2);
        panel.add(pin);
        return panel;
    }

    /**
     * This is the bottom part of the login window.
     * 
     * @return JPanel the bottom part of the login window.
     */
    private JPanel bottomPanel() {
        JPanel panel = new JPanel();
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setFont(body_font);
        panel.add(loginButton);
        return panel;
    }

	/**
	 * 
	 * This is the ActionListener for LoginWindow.
	 *
	 */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String inputACC = cardNumber.getText();
            String inputPIN = new String(pin.getPassword());
            if ((!inputACC.isEmpty() && inputACC.matches("^[0-9]*$")) && (!inputPIN.isEmpty() && inputPIN.matches("^[0-9]*$"))) {
                long input = Long.parseLong(cardNumber.getText());
                short password = Short.parseShort(new String(this.pin.getPassword()));
                account = BankManagerBroker.getInstance().login(input, password);
                if (account == null) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Incorrect account number or/and password.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
                } else {
                    this.showAccount(account);

                }
            } else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Please enter account number and PIN.\nPIN contains only numbers.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    /**
     * This method will show the Account Window for the Account object.
     * 
     * @param account
     */
    private void showAccount(Account account){
        AccountWindow accountWindow = new AccountWindow(account);
        accountWindow.setVisible(true);
        this.dispose();
    }

}