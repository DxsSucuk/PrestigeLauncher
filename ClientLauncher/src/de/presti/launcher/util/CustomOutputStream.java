package de.presti.launcher.util;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

public class CustomOutputStream extends OutputStream {
	private JTextArea textArea;

	public CustomOutputStream(JTextArea textArea) {
		this.textArea = textArea;
	}

	public void write(int b) throws IOException {
		this.textArea.append(String.valueOf((char) b));
		this.textArea.setCaretPosition(this.textArea.getDocument().getLength());
	}
}
