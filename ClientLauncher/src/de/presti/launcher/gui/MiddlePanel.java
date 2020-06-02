package de.presti.launcher.gui;

import java.awt.BorderLayout;
import java.io.OutputStream;
import java.io.PrintStream;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import de.presti.launcher.util.CustomOutputStream;

public class MiddlePanel extends JPanel {
	private JTabbedPane tabs = new JTabbedPane();

	public MiddlePanel() {
		JFXPanel jfx = new JFXPanel();
		JFXPanel jfx2 = new JFXPanel();
		JFXPanel jfx3 = new JFXPanel();
		JTextArea jta = new JTextArea();
		jta.setEditable(false);
		PrintStream log = new PrintStream((OutputStream) new CustomOutputStream(jta));
		System.setOut(log);
		System.setErr(log);
		this.tabs.addTab("Notes", jfx);
		this.tabs.addTab("Client Log", new JScrollPane(jta));
		this.tabs.addTab("Advertisement", jfx2);
		setLayout(new BorderLayout());
		add(this.tabs);
		Platform.runLater(() -> {
			WebView webView = new WebView();
			jfx.setScene(new Scene(webView));
			webView.getEngine().load("http://ichbinlost.tk/notes.htm");
			WebView webView2 = new WebView();
			jfx2.setScene(new Scene(webView2));
			webView2.getEngine().load("https://www.youtube.com/channel/UCg9sQ_VRxgLKCSpuAeqaIBQ");
			
		});
	}
}
