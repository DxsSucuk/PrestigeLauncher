package de.presti.launcher.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ButtonShit implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(new JPanel(), "Updating Files!\nThis can take up to 5 Minutes!", "Updater", 0);
		downloadClientsandJson();
	}
	
	public void downloadClientsandJson() {
		download("http://clients.ichbinlost.tk/PreLife.jar", "clients\\PreLife.jar");
		download("http://clients.ichbinlost.tk/FlameLife.jar", "clients\\FlameLife.jar");
		download("http://clients.ichbinlost.tk/YeetClient.jar", "clients\\YeetClient.jar");
		download("http://clients.ichbinlost.tk/clients.json", "clients\\clients.json");
	}
	
	public void download(String url, String FileName) {
		String realname = url.replace("http://clients.ichbinlost.tk/", "");
		System.out.println("Downloading " + realname);
		try {
			URL link = new URL(url);
			URLConnection con = link.openConnection();
			con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			InputStream in = con.getInputStream();
			ReadableByteChannel readableByteChannel = Channels.newChannel(in);
			FileOutputStream fileOutputStream = new FileOutputStream(FileName);
			FileChannel fileChannel = fileOutputStream.getChannel();
			fileOutputStream.getChannel()
			  .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
			System.out.println("The file " + realname + " was downloaded successfully");
		} catch (IOException e) {
			System.out.println("Error > Couldnt Download " + realname + "\nError Message > " + e.getMessage() + "\nError Message > " + e.getCause());
		}
	}

}
