package de.presti.launcher.launch;

import java.io.PrintStream;

public class RevertBootstrapLog extends Thread {
	public void run() {
		PrintStream err = System.err;
		PrintStream out = System.out;
		try {
			while (err == System.err || out == System.out)
				Thread.sleep(100L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.setErr(err);
		System.setOut(out);
		System.out.println("Set back PrintStreams!");
	}
}
