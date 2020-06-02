package de.presti.launcher.login;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mysql.cj.MysqlConnection;
import com.mysql.cj.jdbc.MysqlDataSource;

import de.presti.launcher.gui.Launcher;

public class Login extends JFrame implements ActionListener {

	public static String loggedusername = "";
	public static String id = "";

	static Connection con;
	static PreparedStatement ps;
	static Statement st;
	static ResultSet rs;

	private static final long serialVersionUID = 1L;
	public static JButton button2;
	public static JTextField LoginField;
	public static JPasswordField password;
	public static JTextArea loginmessage;
	public static JButton button3;
	public static boolean loggedin = false;

	public Login() {
		connect();
		new LoginGUI().setVisible(true);
	}

	private void gui() {
		setLayout(null);
		this.LoginField = new JTextField(0);
		this.LoginField.setBounds(50, 80, 140, 30);
		this.LoginField.setLocation(120, 120);

		this.password = new JPasswordField(0);
		this.password.setBounds(50, 80, 140, 30);
		this.password.setLocation(120, 150);

		this.loginmessage = new JTextArea("Gebe deinen Namen und dein Passwort ein.");
		this.loginmessage.setBounds(40, 80, 245, 17);
		this.loginmessage.setLocation(70, 100);

		this.button3 = new JButton("Login");
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
		setTitle("PrestigeLauncher Login || Made by Presti");

		add(this.password);
		add(this.button2);
		add(this.LoginField);
		add(this.loginmessage);
		add(this.button3);
		setVisible(true);
	}

	public void connect() {
		String driver = "com.mysql.cj.jdbc.Driver";
		try {
			String dbName = "gaydb";
			String dbUserName = "gayuser";
			String dbPassword = "gaypasswort;C";
			String connectionString = "jdbc:mysql://gayserverip:3306/" + dbName + "?user=" + dbUserName + "&password="
					+ dbPassword
					+ "&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			con = DriverManager.getConnection(connectionString);
			st = con.createStatement();
		} catch (Exception e) {
			System.out.println(e.getCause().getMessage());
		}
	}

	public static boolean exist(String code) {
		boolean checkUser = false;

		String sql = "SELECT * FROM `launcher_codes` WHERE `codes` =?";

		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, code);

			rs = ps.executeQuery();

			if (rs.next()) {
				checkUser = true;
			}

		} catch (Exception e) {
			checkUser = false;
		}
		return checkUser;
	}

	public static Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public PreparedStatement getPs() {
		return ps;
	}

	public void setPs(PreparedStatement ps) {
		this.ps = ps;
	}

	public Statement getSt() {
		return st;
	}

	public void setSt(Statement st) {
		this.st = st;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public void login(String user, String pw) {
		try {
			String sql = "SELECT * FROM `launcher_users` WHERE `name` =? AND `pw` =?";
			ps = con.prepareStatement(sql);

			ps.setString(1, user);
			ps.setString(2, pw);

			rs = ps.executeQuery();

			if (rs.next()) {
				loggedusername = user;
				id = rs.getString("id");
				this.setVisible(false);
				(new Launcher()).setVisible(true);
				System.out.println("Loged in " + user);
				System.out.println("ID -> " + getID());
			} else {
				if (exist(user)) {
					JOptionPane.showMessageDialog(null, "Wrong Password!");
				} else {
					JOptionPane.showMessageDialog(null, "User doesnt exist!");
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error at User checking! Pls check your Connection");
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == this.button2) {
			System.exit(0);
		} else if (arg0.getSource() == this.button3) {
			login(this.LoginField.getText().trim(), this.password.getText().trim());
		}
	}

	public static String getID() {
		return id;
	}

}
