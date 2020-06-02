package de.presti.launcher.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.presti.launcher.login.Login;
import de.presti.launcher.util.RankCode;

public class CodeGUI extends JFrame implements ActionListener {
	
	  public static JButton button2;
	  public static JTextField LoginField;
	  public static JTextArea loginmessage;
	  public static JButton button3;
	
	public CodeGUI() {
	    setLayout(null);
	    this.LoginField = new JTextField(0);
	    this.LoginField.setBounds(50, 80, 140, 30);
	    this.LoginField.setLocation(120, 120);
	    
	    this.loginmessage = new JTextArea("Gebe bitte den Code hier ein!");
	    this.loginmessage.setBounds(40, 80, 245, 17);
	    this.loginmessage.setLocation(70, 100);
	    
	    this.button3 = new JButton("Read");
	    this.button3.setBounds(50, 50, 100, 20);
	    this.button3.setLocation(120, 190);
	    
	    this.button2 = new JButton("Exit");
	    
	    this.button2.setBounds(50, 50, 100, 30);
	    
	    this.button2.setLocation(120, 220);
	    
	    this.button3.addActionListener(this);
	    this.button2.addActionListener(this);
	    
	    setDefaultCloseOperation(3);
	    setSize(400, 400);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    setTitle("PrestigeLauncher CodeSystem || Made by Presti");
	    
	    add(this.button2);
	    add(this.LoginField);
	    add(this.loginmessage);
	    add(this.button3);
	    setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(button2)) {
			this.setVisible(false);
		} else if(e.getSource().equals(button3)) {
			if (Login.exist(LoginField.getText().trim())) {
			try {
				JOptionPane.showMessageDialog(new JPanel(), "Code used!", "Code Checker", 0);
				new RankCode("server.ichbinlost.tk", 1067, Login.getID(), LoginField.getText().trim()).run();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			} else {
				JOptionPane.showMessageDialog(new JPanel(), "Codes doesnt Exist!", "Code Checker", 0);
			}
		}
	}

}
