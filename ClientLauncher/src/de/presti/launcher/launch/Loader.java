package de.presti.launcher.launch;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.IOUtils;

public class Loader extends ClassLoader {
	private HashMap<String, byte[]> data = (HashMap) new HashMap<>();

	private HashMap<String, Class<?>> defined = new HashMap<>();

	private Client client;

	private String path;

	private static final boolean DEBUG = false;

	public Loader(Client c, ArrayList<File> libs, byte[] client) {
		super(ClassLoader.getSystemClassLoader());
		try {
			this.client = c;
			this.path = System.getProperty("user.dir");
			this.data.putAll((Map) loadEntries(client));
			System.out.println("Loaded client data: " + this.data.size() + " files found");
			loadLibs(libs);
			System.out.println("Altogether " + this.data.size() + " files found");
			Class<?> loader = ClassLoader.class;
			Field f2 = loader.getDeclaredField("scl");
			f2.setAccessible(true);
			f2.set(null, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if (isRT(name))
			try {
				return super.loadClass(name);
			} catch (Exception exception) {
			}
		return getClazz(name);
	}

	public URL getResource(String name) {
		try {
			return new URL("file:" + this.path + File.separator + "clients" + File.separator + this.client.getJarName()
					+ "!" + File.separator + name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public InputStream getResourceAsStream(String name) {
		if (this.data.containsKey(name))
			return new ByteArrayInputStream(this.data.get(name));
		return super.getResourceAsStream(name);
	}

	public boolean isRT(String name) {
		return !(!name.startsWith("java.") && !name.startsWith("com.oracle") && !name.startsWith("com.sun")
				&& !name.startsWith("javax.") && !name.startsWith("jdk") && !name.startsWith("sun."));
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException {
		if (isRT(name))
			try {
				return super.findClass(name);
			} catch (Exception exception) {
			}
		return getClazz(name);
	}

	private Class<?> getClazz(String name) {
		if (this.defined.containsKey(name))
			return this.defined.get(name);
		String fixed = String.valueOf(name.replace('.', '/')) + ".class";
		if (this.data.containsKey(fixed))
			return get(name, this.data.get(fixed));
		return null;
	}

	public Class<?> get(String name, byte[] bytes) {
		Class<?> c = defineClass(name, bytes, 0, bytes.length);
		resolveClass(c);
		this.defined.put(name, c);
		return c;
	}

	private void loadLibs(ArrayList<File> libs) throws IOException {
		for (File f : libs) {
			Map<String, byte[]> entries = loadEntries(f);
			this.data.putAll((Map) entries);
		}
	}

	public static Map<String, byte[]> loadEntries(File jarFile) throws IOException {
		Map<String, byte[]> entries = (Map) new HashMap<>();
		ZipInputStream jis = new ZipInputStream(new FileInputStream(jarFile));
		ZipEntry entry;
		while ((entry = jis.getNextEntry()) != null) {
			try {
				String name = entry.getName();
				if (!entry.isDirectory()) {
					byte[] bytes = IOUtils.toByteArray(jis);
					entries.put(name, bytes);
				}
			} catch (Exception e) {
				e.printStackTrace();
				jis.closeEntry();
			} finally {
				jis.closeEntry();
			}
		}
		jis.close();
		return entries;
	}

	public static Map<String, byte[]> loadEntries(byte[] jarFile) throws IOException {
		Map<String, byte[]> entries = (Map) new HashMap<>();
		ZipInputStream jis = new ZipInputStream(new ByteArrayInputStream(jarFile));
		ZipEntry entry;
		while ((entry = jis.getNextEntry()) != null) {
			try {
				String name = entry.getName();
				if (!entry.isDirectory()) {
					byte[] bytes = IOUtils.toByteArray(jis);
					entries.put(name, bytes);
				}
			} catch (Exception e) {
				e.printStackTrace();
				jis.closeEntry();
			} finally {
				jis.closeEntry();
			}
		}
		jis.close();
		return entries;
	}
}
