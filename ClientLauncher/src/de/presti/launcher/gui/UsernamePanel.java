package de.presti.launcher.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import de.presti.launcher.login.Login;

public class UsernamePanel extends JPanel {
	public UsernamePanel() {
		JButton code = new JButton("Code");
		code.setLayout(new BorderLayout(0, 1));
		code.addActionListener(new ButtonShit2());
		add(code, "East");
	//	code.setHorizontalAlignment(4);
		JLabel username = new JLabel("Username: " + Login.loggedusername);
		username.setLayout(new GridLayout(2, 2, 2, 4));
		username.setHorizontalAlignment(4);
		add(username);
	}
}
