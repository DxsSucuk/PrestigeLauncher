package de.presti.launcher.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class RankCode {

	private String host;
	private int port;
	private String id;
	private String code;

	public RankCode(String host, int port, String userid, String code) {
		this.host = host;
		this.port = port;
		this.id = userid;
		this.code = code;
	}

	public void run() throws UnknownHostException, IOException {
		Socket client = new Socket(host, port);

		PrintStream output = new PrintStream(client.getOutputStream());

		output.println(id + ":" + code);

		output.close();
		client.close();

	}
}