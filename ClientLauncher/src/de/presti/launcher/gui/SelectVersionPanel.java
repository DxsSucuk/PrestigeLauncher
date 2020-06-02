package de.presti.launcher.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.presti.launcher.launch.Client;

public class SelectVersionPanel extends JPanel {
	private JComboBox<Client> jcb;

	public SelectVersionPanel(ArrayList<Client> clients) {
		setLayout(new BorderLayout(0, 2));
		JButton button = new JButton();
		button.setText("Update");
		button.addActionListener(new ButtonShit());
		JPanel version = new JPanel();
		version.setLayout(new GridLayout(1, 2, 4, 0));
		version.add(new JLabel("Client to Launch:"));
		version.add(this.jcb = new JComboBox<>(new Vector<>(clients)));
		add(version, "North");
		add(button, "South");
	}

	public Client selectedClient() {
		return (Client) this.jcb.getSelectedItem();
	}
}
