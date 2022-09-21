package sait.bankonit.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import ca.bankonit.exceptions.*;
import ca.bankonit.manager.*;
import ca.bankonit.models.*;

/**
 * 
 * Class description: Renders the account window.
 *
 * @author Waylon Mao (weilong.mao@edu.sait.ca)
 * @version Jun 30, 2022
 */
public class AccountWindow extends JFrame implements ActionListener {

	/**
	 * @Fields serialVersionUID : Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Stores the Account object in the current window.
	 * 
	 * @Fields account
	 */
	private Account account;

	/**
	 * Stores the Account transactions for JList.
	 * 
	 * @Fields transactionsList
	 */
	private DefaultListModel<String> transactionsList = new DefaultListModel<>();

	/**
	 * Stores the balance of the account.
	 * 
	 * @Fields balanceString
	 */
	private String balanceString;
	/**
	 * Account number JLable at the top of this window.
	 * 
	 * @Fields accountLabel
	 */
	private JLabel accountLabel;

	/**
	 * This JLabel shows account's balance at the top of this window.
	 * 
	 * @Fields balanceLabel
	 */
	private JLabel balanceLabel;

	/**
	 * Click this button to show or hide the account number.
	 * 
	 * @Fields showhideButton
	 */
	private JButton showhideButton;

	/**
	 * Selecting this radio indicates that the deposit operation will be performed.
	 * 
	 * @Fields depositRadio
	 */
	private JRadioButton depositRadio;

	/**
	 * Selecting this radio indicates that the withdraw operation will be performed.
	 * 
	 * @Fields withdrawRadio
	 */
	private JRadioButton withdrawRadio;

	/**
	 * Click this button to confirm the operation.
	 * 
	 * @Fields submitButton
	 */
	private JButton submitButton;

	/**
	 * Click this button to sign out and close this window.
	 * 
	 * @Fields signoutButton
	 */
	private JButton signoutButton;

	/**
	 * This JTextField is provided for the user to enter deposit or withdraw amount.
	 * 
	 * @Fields amountInput
	 */
	private JTextField amountInput;

	/**
	 * This is the Font for body part.
	 * 
	 * @Fields body_font
	 */
	private Font body_font = new Font("Verdana", Font.PLAIN, 14);

	/**
	 * This is the Font for account number.
	 * 
	 * @Fields account_font
	 */
	private Font account_font = new Font("Verdana", Font.PLAIN, 26);

	/**
	 * This type means that the user have not select an transaction type.
	 * 
	 * @Fields nullType
	 */
	private final char nullType = 'N';

	/**
	 * This type means that the user want to deposit.
	 * 
	 * @Fields depositType
	 */
	private final char depositType = 'D';

	/**
	 * This type means that the user want to withdraw.
	 * 
	 * @Fields withdrawType
	 */
	private final char withdrawType = 'W';

	/**
	 * This char indicates the type of transaction. Default value is equal to
	 * nullType which is 'N', which meanings type have not been selected.
	 * 
	 * 
	 * @Fields operateType
	 */
	private char operateType = nullType;
	/**
	 * This boolean indicates whether the account number is showed or hidden.
	 * 
	 * @Fields isShowed
	 */
	private boolean isShowed = false;

	/**
	 * Initializes the account window
	 *
	 * @param account Account to manage
	 */
	public AccountWindow(Account account) {

		super("Bank On It Account");

		// Set size to 600x500
		final short windowWidth = 600;
		final short windowHeight = 500;

		// Store account as field.
		this.account = account;

		// Set size
		this.setSize(windowWidth, windowHeight);

		// This is the main panel.
		JPanel panel = new JPanel();

		// Set with BorderLayout.
		panel.setLayout(new BorderLayout());

		// Add three panels to the main panel.
		panel.add(topPanel(), BorderLayout.NORTH);
		panel.add(midPanel(), BorderLayout.CENTER);
		panel.add(bottomPanel(), BorderLayout.SOUTH);

		// Add main panel to this AccountWindow JFrame.
		this.add(panel);

		// Show this window in the center of the screen.
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) screensize.getWidth() / 2 - this.getWidth() / 2;
		int y = (int) screensize.getHeight() / 2 - this.getHeight() / 2;
		this.setLocation(x, y);

		// Add a WindowAdapter to detect the closing of this AccountWindow, for calling
		// LoginWindow.
		this.addWindowListener(new MyWinAdapter());
	}

	/**
	 * Create a panel in the NORTH. Include accountLabel, balanceLabel,
	 * showhidenButton.
	 * 
	 * @return JPanel The top part of the account window.
	 */
	private JPanel topPanel() {

		// Create 2 panels for adjusting of the layout.
		JPanel panel = new JPanel();
		JPanel panel1 = new JPanel();

		balanceLabel = new JLabel("", JLabel.CENTER);
		balanceLabel.setFont(body_font);

		// Load the transactions and count balance.
		populateTransactions();

		accountLabel = new JLabel("Card#" + encrypt(account.getCardNumber()));
		showhideButton = new JButton("Show");
		showhideButton.addActionListener(this);

		accountLabel.setFont(account_font);

		panel.setLayout(new BorderLayout());
		panel1.add(accountLabel);
		panel1.add(showhideButton);
		panel.add(panel1, BorderLayout.NORTH);
		panel.add(balanceLabel, BorderLayout.SOUTH);

		return panel;
	}

	/**
	 * Create a panel in the CENTER.
	 * 
	 * @return JPanel The middle part of the account window.
	 */
	private JPanel midPanel() {
		// Set the size of the scrollPane.
		final short scrollPaneWidth = 550;
		final short scrollPaneHeight = 320;

		JPanel panel = new JPanel();

		// Use JList to show transactions details.
		JList<String> listDetails = new JList<String>(transactionsList);

		listDetails.setFont(body_font);
		JScrollPane scrollPane = new JScrollPane(listDetails);

		// Set size of list by width 550, height 320.
		scrollPane.setPreferredSize(new Dimension(scrollPaneWidth, scrollPaneHeight));
		panel.add(scrollPane);
		return panel;
	}

	/**
	 * Create a panel in the SOUTH.
	 * 
	 * @return JPanel The bottom part of the account window.
	 */
	private JPanel bottomPanel() {
		final short inputLength = 7;
		final short inputWidth = 70;
		final short inputHeight = 20;

		// Create 3 panels for adjusting of the layout.
		JPanel panel = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();

		JLabel jLabel = new JLabel("Type: ");
		jLabel.setFont(body_font);

		// Create 2 Radios to ButtonGroup.
		ButtonGroup typeRadio = new ButtonGroup();
		depositRadio = new JRadioButton("Deposit");
		depositRadio.setFont(body_font);
		depositRadio.addActionListener(this);
		withdrawRadio = new JRadioButton("Withdraw");
		withdrawRadio.setFont(body_font);
		withdrawRadio.addActionListener(this);

		// Create JTextField for inputing transaction amount.
		amountInput = new JTextField("", inputLength);
		amountInput.setSize(inputWidth, inputHeight);
		amountInput.setFont(body_font);

		// Create a button for submit operation.
		submitButton = new JButton("Submit");
		submitButton.setFont(body_font);
		submitButton.addActionListener(this);

		// Create a button for sign out operation.
		signoutButton = new JButton("Signout");
		signoutButton.setFont(body_font);
		signoutButton.addActionListener(this);

		typeRadio.add(depositRadio);
		typeRadio.add(withdrawRadio);
		panel1.add(jLabel);
		panel1.add(depositRadio);
		panel1.add(withdrawRadio);
		panel1.add(amountInput);
		panel1.add(submitButton);
		panel2.add(signoutButton);
		panel.setLayout(new BorderLayout());
		panel.add(panel1, BorderLayout.NORTH);
		panel.add(panel2, BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * Clears and re-populates transactions as well as updates balance.
	 * 
	 */
	private void populateTransactions() {

		// Clear JList before load transactions.
		this.transactionsList.clear();
		double amount = 0;
		ArrayList<Transaction> transArraylist;
		try {
			transArraylist = BankManagerBroker.getInstance().getTransactionsForAccount(this.account);
		} catch (InvalidAccountException e) {
			throw new RuntimeException(e);
		}

		// Use for-each loop to add each transaction to JList, and count balance.
		for (Transaction i : transArraylist) {
			this.transactionsList.addElement(i.toString());
			if (i.getTransactionType() == depositType) {
				amount += i.getAmount();
			} else {
				amount -= i.getAmount();
			}
		}

		// Update the balanceLabel.
		balanceString = String.format("%.2f", amount);
		if (isShowed) {
			balanceLabel.setText("Balance: $" + balanceString);
		} else {
			balanceLabel.setText("Balance: $ ***");
		}

		// Save to files.
		BankManagerBroker.getInstance().persist();
	}

	/**
	 * This method can add a space to every 4 digits of the account number. And
	 * transfer long to String. For example: Input: 4444555566667777L Output: "4444
	 * 5555 6666 7777"
	 * 
	 * @param number The account number
	 * @return String
	 */
	private String addSpace(long number) {
		String original = String.valueOf(number);
		String result = "";
		for (int i = 0; i < original.length(); i += 4) {
			result += original.substring(i, i + 4) + " ";
		}
		return result.substring(0, result.length() - 1);
	}

	/**
	 * This method can encrypt the 5th-12th digits of the 16-digit account number
	 * into * for hidden. For example: Input: 4444555566667777L Output: "4444 ****
	 * **** 7777"
	 * 
	 * @param number The 16-digit account number
	 * @return String
	 */
	private String encrypt(long number) {
		String original = String.valueOf(number);
		String result = original.substring(0, 4) + " **** **** " + original.substring(12, 16);
		return result;
	}

	/**
	 * This method can transfer String to Double, and make sure that the input
	 * String is positive, with a maximum of two decimal places if there are
	 * decimals.
	 * 
	 * @param string Any input String.
	 * @return double
	 */
	public double toDigital(String string) {
		// If the input string does not match the regular expression, the return will be
		// default 0.
		double digital = 0;

		// Non-null, non-negative, numeric, up to two decimal places.
		if (!string.isEmpty() && string.matches("^[0-9]+(.[0-9]{1,2})?$")) {
			digital = Double.parseDouble(string);
		} else {
			// An error dialog pops up when the input are not met the conditions.
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Please enter a positive number with up to two decimal places.",
					"ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
		}
		return digital;
	}

	/**
	 * 
	 * Class description: This is the ActionListener for AccountWindow.
	 *
	 * @author Waylon Mao (weilong.mao@edu.sait.ca)
	 *
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// When showhideButton is clicked, according to field isShowed to modify
		// accountLabel's text.
		if (e.getSource() == showhideButton) {
			if (isShowed) {
				accountLabel.setText("Card#" + encrypt(account.getCardNumber()));
				balanceLabel.setText("Balance: $ ***");
				showhideButton.setText("Show");
				isShowed = false;
			} else {
				accountLabel.setText("Card#" + addSpace(account.getCardNumber()));
				balanceLabel.setText("Balance: $" + balanceString);
				showhideButton.setText("Hide");
				isShowed = true;
			}
		}

		// When depositRadio is selected, change field operateType to depositType which
		// should be 'D'.
		if (e.getSource() == depositRadio) {
			operateType = depositType;
		}

		// When withdrawRadio is selected, change field operateType to withdrawType
		// which should be 'W'.
		if (e.getSource() == withdrawRadio) {
			operateType = withdrawType;
		}

		// When submitButton is clicked, do transaction by operateType and amount.
		if (e.getSource() == submitButton) {
			if (operateType == nullType) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Please select Deposit of Withdraw.", "ERROR_MESSAGE",
						JOptionPane.ERROR_MESSAGE);
			} else {
				String inputString = amountInput.getText();

				// toDigital method can check the input and transfer to double value. If the
				// input is not match conditions, the amount will be 0.
				double amount = toDigital(inputString);

				// Only do transaction when amount is not 0.
				if (amount != 0) {
					if (operateType == depositType) {
						try {
							BankManagerBroker.getInstance().deposit(account, amount);
						} catch (InvalidAccountException ex) {
							throw new RuntimeException(ex);
						}
					} else {
						try {
							BankManagerBroker.getInstance().withdraw(account, amount);
						} catch (InvalidAccountException ex) {
							throw new RuntimeException(ex);
						}
					}
					// Reload transactions, modify related objects in this window.
					this.populateTransactions();
				}
			}

		}

		// When signouotButton is clicked, show LoginWindow and close this
		// AccountWindow.
		if (e.getSource() == signoutButton) {
			LoginWindow login = new LoginWindow();
			login.setVisible(true);
			this.dispose();
		}
	}

	/**
	 * 
	 * Class description: This is the WindowAdapter for AccountWindow.
	 *
	 * @author Waylon Mao (weilong.mao@edu.sait.ca)
	 *
	 */
	private class MyWinAdapter extends WindowAdapter {
		@Override
		/**
		 * When closing this AccountWindow, it will call LoginWindow and close this
		 * AccountWindow.
		 * 
		 */
		public void windowClosing(WindowEvent e) {
			LoginWindow login = new LoginWindow();
			login.setVisible(true);
			dispose();
		}
	}
}
