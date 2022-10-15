package general;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.StringJoiner;

import com.sun.security.auth.module.UnixSystem;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class Utilities {

	// RUN THE COMMAND
	public static String runCmd(String cmdText) throws IOException {
		Runtime cmd = Runtime.getRuntime();
		Process prc = cmd.exec(cmdText);

		InputStream in = prc.getInputStream();
		byte[] bytes = new byte[in.available()];
		in.read(bytes);
		StringBuilder result = new StringBuilder("");
		for (byte b : bytes) {
			result.append((char) b);
		}

		return result.toString();
	}

	private static void Log(String text) {

	}

	public static boolean cmpExt(File f, String ext) {
		String str = f.getName();
		return cmpExt(str, ext);
	}

	public static boolean cmpExt(String str, String ext) {
		int i = str.lastIndexOf('.');
		if (i > 0) {
			str = str.substring(i);
			if (str.equals(".desktop")) {
				return true;
			}
		}
		return false;
	}

	private static FileFilter launcherFilter = new FileFilter() {

		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}

			return cmpExt(f, ".desktop");
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return "*.desktop";
		}

	};

	// private String SHARE_DESKTOP_FOLDER = null;

	private String startDirectory = "/", launcherStartDirectory = null, SHARE_DESKTOP_FOLDER = null;

	private static final String SHARE_DESKTOP_FOLDER_ROOT = "/usr/share/applications/",
			SHARE_DESKTOP_FOLDER_USER = "/.local/share/applications/";

	{
		if (new UnixSystem().getUid() == 0) {
			launcherStartDirectory = SHARE_DESKTOP_FOLDER = SHARE_DESKTOP_FOLDER_ROOT;
		} else {
			launcherStartDirectory = SHARE_DESKTOP_FOLDER = System.getProperty("user.home") + SHARE_DESKTOP_FOLDER_USER;
		}
	}

	private static File getPath(String title, String path, int mods) {
		return getPath(title, path, mods, null);
	}

	private static File getPath(String title, String path, int mods, FileFilter filter) {
		File startedPoint = new File(path);
		JFileChooser chooser = new JFileChooser(startedPoint);
		if (filter != null) {
			chooser.setFileFilter(filter);
		}
		chooser.setFileSelectionMode(mods);
		chooser.setFileHidingEnabled(false);
		int response = chooser.showDialog(null, title);
		if (response == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		} else {
			return new File("");
		}
	}

	private static boolean setText(JTextField txt, File file) {
		String str = file.getAbsolutePath();
		if (file.exists()) {
			Log("");
			txt.setText(str);
			return true;
		} else {
			Log("Path not exists: " + str);
			return false;
		}
	}

	public void chooseFile(JTextField txt, String title) {
		File file;
		if (setFile(txt, file = getPath(title, startDirectory, JFileChooser.FILES_ONLY))) {
			startDirectory = file.getParentFile().getAbsolutePath();
		}
		;
	}

	public void chooseLauncher(JTextField txt, String title) {
		File file;
		FileFilter filter = launcherFilter;
		file = getPath(title, startDirectory, JFileChooser.FILES_ONLY, filter);
		// check if file is equalited to file
		if (filter.accept(file)) {
			if (setFile(txt, file)) {
				launcherStartDirectory = file.getParentFile().getAbsolutePath();
			}
		} else {
			Log("Do not match to glob: " + filter.getDescription());
		}
	}

	public void chooseDir(JTextField txt, String title) {
		File file;
		if (setDir(txt, file = getPath(title, startDirectory, JFileChooser.DIRECTORIES_ONLY))) {
			startDirectory = file.getAbsolutePath();
		}
		;
	}

	private static boolean setDir(JTextField txt, File file) {
		if (file.isFile()) {
			Log("Path is not directory: " + file);
			return false;
		} else {
			return setText(txt, file);
		}
	}

	private static boolean setFile(JTextField txt, File file) {
		if (file.isDirectory()) {
			Log("Path is not file: " + file);
			return false;
		} else {
			return setText(txt, file);
		}
	}

	public void generate(String name, String comment, String version, String path, String categories, String runnable,
			String image, String directory, boolean application, boolean terminal, Iterable categorySet) {		
		StringBuilder error = new StringBuilder("");

		// WRITE THE CONTENT OF DESKTOP FILE
		StringBuilder content = new StringBuilder("[Desktop Entry]\n");
		StringJoiner categoryset = new StringJoiner(";");
		if (!name.isBlank())
			content.append("Name=" + name + "\n");
		else
			error.append("Name field is empty\n");

		if (!runnable.isBlank())
			content.append("Exec=" + runnable + "\n");
		else
			error.append("Runnable file field is empty\n");

		if (!error.isEmpty()) {
			JOptionPane.showMessageDialog(null, error);
			return;
		}

		if (!comment.isBlank())
			content.append("Comment=" + comment + "\n");
		if (!version.isBlank())
			content.append("Version=" + version + "\n");
		if (!image.isBlank())
			content.append("Icon=" + image + "\n");
		if (!directory.isBlank())
			content.append("Path=" + directory + "\n");
		content.append("Terminal=" + terminal + "\n");
		if (application)
			content.append("Type=Application\n");
		else
			content.append("Type=non-Application\n");

		if (!categories.isBlank())
			categoryset.add(categories);
		for (Object chb : categorySet) {
			categoryset.add(chb.toString());
		}
		categories = categoryset.toString();
		if (!categories.isEmpty())
			content.append("Categories=" + categories + ";\n");

		File destinationFile;
		if (path.isBlank()) {
			path = name.toLowerCase().strip();
			path = path.replaceAll("/", "_");
			path = path.replaceAll("\\\\", "_");
			path = path.replaceAll("[ ]+", "_");
		}
		if (cmpExt(path, ".desktop")) {
			destinationFile = new File(path);
		} else {
			destinationFile = new File(path + ".desktop");
		}
		
		if (!destinationFile.isAbsolute()) {
			destinationFile = new File(SHARE_DESKTOP_FOLDER, destinationFile.getPath());
		}

		try {
			destinationFile.getParentFile().mkdirs();
			FileOutputStream fos = new FileOutputStream(destinationFile);
			fos.write(content.toString().getBytes());
			fos.close();
			JOptionPane.showMessageDialog(null, "generated at " + destinationFile);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Fail!", JOptionPane.NO_OPTION);
		}
		
	}

}
