package de.presti.launcher.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import de.presti.launcher.launch.Client;
import de.presti.launcher.launch.Loader;
import de.presti.launcher.launch.RevertBootstrapLog;
import de.presti.launcher.login.Login;

public class Launcher extends JFrame {
	private static final long serialVersionUID = 1L;

	private BorderLayout layout;

	private ArrayList<Client> clients;

	private LaunchBar lb;
	private static InputStream input;
	public static String[] args2;

	public Launcher() throws Exception {
		this.clients = new ArrayList<>();
		readClients();
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setSize(new Dimension(900, 580));
		setTitle("Launcher");
		setLocationRelativeTo(null);
		try {
			InputStream launcher = Launcher.class.getResourceAsStream("/res/favicon.png");
			if (launcher != null)
				setIconImage(ImageIO.read(launcher));
		} catch (IOException iOException) {
		}
		this.layout = new BorderLayout();
		setLayout(this.layout);
		add(new MiddlePanel(), "Center");
		add(this.lb = new LaunchBar(this, this.clients), "South");
		System.out.println("Initialized GUI!");
	}

	private void readClients() throws IOException {
		JSONObject o = new JSONObject(new String(IOUtils.toByteArray(input)));
		JSONArray ids = o.getJSONArray("ids");
		for (int i = 0; i < ids.length(); i++) {
			String s = ids.getString(i);
			JSONObject client = o.getJSONObject(s);
			ArrayList<File> libraries = new ArrayList<>();
			JSONArray libs = client.getJSONArray("libs");
		/*	for (int j = 0; j < libs.length(); j++) {
				String lib = libs.getString(j);
				libraries.add(new File("libs/" + lib + ".jar"));
			} */
			this.clients.add(new Client(s, client.getString("name"), client.getString("version"),
					client.getString("mcv"), client.getString("path"), libraries));
		}
	}

	public static void main(String[] args) throws Exception {
		args2 = args;
		System.setProperty("java.library.path", (new File("natives")).getAbsolutePath());
		System.setProperty("org.lwjgl.librarypath", (new File("natives")).getAbsolutePath());
		input = new File("clients/clients.json").toURI().toURL().openStream();
		//(new Launcher()).setVisible(true);
		new Login();
	}

	public void launch() {
		(new Thread(() -> {
			Client c = this.lb.svp.selectedClient();
			try {
				byte[] client = Files.readAllBytes((new File("clients/" + c.getJarName())).toPath());
				if (client == null) {
					JOptionPane.showMessageDialog(null, "Client jar not found!");
					return;
				}
				
				(new RevertBootstrapLog()).start();
				
				Loader sl = new Loader(c, c.getLibs(), client);
				Class<?> api = sl.loadClass("de.prestigelauncher.LauncherAPI");
				Object apiob = api.newInstance();
				Object apiparam = new String[] { Login.getID() , Login.loggedusername };
				api.getMethod("login", String[].class).invoke(apiob, apiparam);
				
				
				Object startob = api.newInstance();
				Object startparam = new String[] { "--version", "mcp", "--accessToken", "0", "--assetsDir", "assets", "--assetIndex", "1.8", "--userProperties", "{}" };
				api.getMethod("start", String[].class).invoke(startob, startparam);
				
				System.out.println("Exit");
			} catch (Exception e) {
				e.printStackTrace();
			}
		})).start();
	}

	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
}
