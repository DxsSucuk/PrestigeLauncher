package de.presti.launcher.launch;

import java.io.File;
import java.util.ArrayList;

public class Client {
	private String entryName;

	private String name;

	private String version;

	private String mcv;

	private String jarName;

	private ArrayList<File> libs;

	public Client(String entryName, String name, String version, String mcv, String jarName,
			ArrayList<File> libraries) {
		this.entryName = entryName;
		this.name = name;
		this.version = version;
		this.mcv = mcv;
		this.jarName = jarName;
		this.libs = libraries;
	}

	public ArrayList<File> getLibs() {
		return this.libs;
	}

	public void setLibs(ArrayList<File> libs) {
		this.libs = libs;
	}

	public String getJarName() {
		return this.jarName;
	}

	public void setJarName(String jarName) {
		this.jarName = jarName;
	}

	public String getEntryName() {
		return this.entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMcv() {
		return this.mcv;
	}

	public void setMcv(String mcv) {
		this.mcv = mcv;
	}

	public String toString() {
		return String.valueOf(this.name) + " " + this.version;
	}

	public String getMain() {
		return "net.minecraft.client.main.Main";
	}
}
