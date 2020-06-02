package de.presti.launcher.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import de.presti.launcher.launch.Client;

public class LaunchBar extends JPanel {
	public SelectVersionPanel svp;

	public LaunchBar(Launcher sl, ArrayList<Client> clients) {
		setLayout(new GridLayout(1, 3, 4, 16));
		setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		JButton launch = new JButton("Launch");
		add(wrapSidePanel(this.svp = new SelectVersionPanel(clients), 17));
		add(launch);
		add(new UsernamePanel());
		launch.addActionListener(a -> sl.launch());
	}

	protected JPanel wrapSidePanel(JPanel target, int side) {
		JPanel wrapper = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = side;
		constraints.weightx = 1.0D;
		constraints.weighty = 1.0D;
		wrapper.add(target, constraints);
		return wrapper;
	}
}
