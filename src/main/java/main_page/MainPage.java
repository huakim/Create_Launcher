package main_page;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.BorderLayout;
import java.awt.Window.Type;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.awt.Dialog.ModalExclusionType;

import javax.swing.*;
import java.awt.event.ActionListener;
import general.Utilities;
import java.awt.event.ActionEvent;

public class MainPage {

	private JFrame frmMainWindow;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage window = new MainPage();
					window.frmMainWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws FontFormatException 
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	
	public void setDefaultFont()  {

		Font fontCoffe, font;
		try {
			fontCoffe = Font.createFont(Font.TRUETYPE_FONT, Utilities.class.getResourceAsStream("HYCoffee_Bold.ttf"));
			font = fontCoffe.deriveFont(14.1f);//RESIZE
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
	
					UIManager.put("Label.font"			, font);
	}

	public MainPage() {
		setDefaultFont();
		initialize();
		utils = new Utilities();
		frmMainWindow.setSize(435, 526);

		frmMainWindow.setIconImage(java.awt.Toolkit.getDefaultToolkit().
				getImage(Utilities.class.getResource("icon.png")));
		
		checkBoxIterable = new  Iterable<String>(){
				JCheckBox [] boxes = {Multimedia, Development,
						Network, Game, Utility, 
						Settings, Graphic, Education, 
						Office};
				int len = boxes.length;
				@Override
				public Iterator<String> iterator() {
					// TODO Auto-generated method stub
					return new Iterator<String>() {
						int index = 0;
						boolean exists = false;
						
						@Override
						public boolean hasNext() {
							if (exists) return true;
							if (index < len) {
								while (!boxes[index].isSelected()) {
									index ++;
									if (index == len) return false;
								}
								exists = true;
								return true;
							} else {
								return false;
							}
						}

						@Override
						public String next() {
							if (exists) {
								exists = false;
								return boxes[index++].getText();
							} else {
								if (index < len) {
									JCheckBox b;
									while (!(b=boxes[index]).isSelected()) {
										index ++;
										if (index == len) throw new NoSuchElementException();
									}
									index ++;
									return b.getText();
								} else {
									throw new NoSuchElementException();
								}
							}
						}
					};
				}
			};
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	
	private general.Utilities utils;
	
	private JTextField 
		Name, 
		Comment,
		Version,
		Path,
		Categories,
		Executable,
		Icon,
		Directory;
	
	private JCheckBox
	    Multimedia, 
	    Application,
	    Development,
	    Network,
	    Game, 
	    Utility,
	    Settings,
	    Graphic,
	    Education,
	    Office,
	    Terminal;
	
	private Iterable<String> checkBoxIterable;
	
	private void generate() {
		utils.generate(
				Name.getText(), 
				Comment.getText(), 
				Version.getText(), 
				Path.getText(),
				Categories.getText(), 
				Executable.getText(), 
				Icon.getText(), 
				Directory.getText(), 
				Application.isSelected(), 
				Terminal.isSelected(), 
				checkBoxIterable);
	}
	
	private JButton 
		choosePath, 
		chooseExe, 
		chooseIcon,
		chooseDir,
		generate;
	
	private void initialize() {
		frmMainWindow = new JFrame();
		frmMainWindow.setTitle("create launcher");
		frmMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainWindow.setAutoRequestFocus(false);
		frmMainWindow.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("comment:");
		lblNewLabel.setFont(UIManager.getFont("Label.font"));
		lblNewLabel.setBounds(12, 44, 92, 20);
		frmMainWindow.getContentPane().add(lblNewLabel);
		
		Name = new JTextField();
		Name.setFont(UIManager.getFont("Label.font"));
		Name.setBounds(122, 12, 294, 25);
		frmMainWindow.getContentPane().add(Name);
		
		JLabel lblNewLabel_1 = new JLabel("name:");
		lblNewLabel_1.setFont(UIManager.getFont("Label.font"));
		lblNewLabel_1.setBounds(12, 12, 92, 20);
		frmMainWindow.getContentPane().add(lblNewLabel_1);
		
		Comment = new JTextField();         
		Comment.setFont(UIManager.getFont("Label.font"));
		Comment.setBounds(122, 44, 294, 25);
		frmMainWindow.getContentPane().add(Comment);
		
		Version = new JTextField();             
		Version.setFont(UIManager.getFont("Label.font"));
		Version.setBounds(122, 76, 294, 25);
		frmMainWindow.getContentPane().add(Version);
		
		JLabel lblVersion = new JLabel("version:");
		lblVersion.setFont(UIManager.getFont("Label.font"));
		lblVersion.setBounds(12, 76, 92, 20);
		frmMainWindow.getContentPane().add(lblVersion);
		
		JLabel lblPath = new JLabel("launcher path:");
		lblPath.setFont(UIManager.getFont("Label.font"));
		lblPath.setBounds(12, 108, 120, 20);
		frmMainWindow.getContentPane().add(lblPath);
		
		Path = new JTextField();
		Path.setFont(UIManager.getFont("Label.font"));
		Path.setBounds(12, 129, 293, 25);
		frmMainWindow.getContentPane().add(Path);
		
		Terminal = new JCheckBox("using in terminal");
		Terminal.setFont(UIManager.getFont("Label.font"));
		Terminal.setBounds(182, 173, 218, 23);
		frmMainWindow.getContentPane().add(Terminal);
		
		Application = new JCheckBox("is application");
		Application.setSelected(true);
		Application.setFont(UIManager.getFont("Label.font"));
		Application.setBounds(8, 173, 218, 23);
		frmMainWindow.getContentPane().add(Application);
		
		JLabel lblCategories = new JLabel("categories: ");
		lblCategories.setFont(UIManager.getFont("Label.font"));
		lblCategories.setBounds(12, 204, 92, 20);
		frmMainWindow.getContentPane().add(lblCategories);
		
		Graphic = new JCheckBox("Graphics");
		Graphic.setFont(UIManager.getFont("Label.font"));
		Graphic.setBounds(132, 252, 120, 23);
		frmMainWindow.getContentPane().add(Graphic);
		
		Game = new JCheckBox("Game");
		Game.setFont(UIManager.getFont("Label.font"));
		Game.setBounds(8, 252, 120, 23);
		frmMainWindow.getContentPane().add(Game);
		
		Office = new JCheckBox("Office");
		Office.setFont(UIManager.getFont("Label.font"));
		Office.setBounds(8, 273, 120, 23);
		frmMainWindow.getContentPane().add(Office);
		
		Multimedia = new JCheckBox("AudioVideo");
		Multimedia.setFont(UIManager.getFont("Label.font"));
		Multimedia.setBounds(8, 232, 120, 23);
		frmMainWindow.getContentPane().add(Multimedia);
		
		Education = new JCheckBox("Utility");
		Education.setFont(UIManager.getFont("Label.font"));
		Education.setBounds(280, 232, 120, 23);
		frmMainWindow.getContentPane().add(Education);
		
		Development = new JCheckBox("Development");
		Development.setFont(UIManager.getFont("Label.font"));
		Development.setBounds(132, 232, 120, 23);
		frmMainWindow.getContentPane().add(Development);
		
		Settings = new JCheckBox("Settings");
		Settings.setFont(UIManager.getFont("Label.font"));
		Settings.setBounds(132, 273, 120, 23);
		frmMainWindow.getContentPane().add(Settings);
		
		Network = new JCheckBox("Network");
		Network.setFont(UIManager.getFont("Label.font"));
		Network.setBounds(280, 252, 120, 23);
		frmMainWindow.getContentPane().add(Network);
		
		Utility = new JCheckBox("System");
		Utility.setFont(UIManager.getFont("Label.font"));
		Utility.setBounds(280, 273, 120, 23);
		frmMainWindow.getContentPane().add(Utility);
		
		Categories = new JTextField();
		Categories.setFont(UIManager.getFont("Label.font"));
		Categories.setBounds(122, 203, 294, 25);
		frmMainWindow.getContentPane().add(Categories);
		
		JLabel lblRunnableFile = new JLabel("runnable file: ");
		lblRunnableFile.setFont(UIManager.getFont("Label.font"));
		lblRunnableFile.setBounds(12, 304, 214, 20);
		frmMainWindow.getContentPane().add(lblRunnableFile);
		
		Executable = new JTextField();
		Executable.setFont(UIManager.getFont("Label.font"));
		Executable.setBounds(12, 326, 293, 25);
		frmMainWindow.getContentPane().add(Executable);
		
		JLabel lblImage = new JLabel("image: ");
		lblImage.setFont(UIManager.getFont("Label.font"));
		lblImage.setBounds(12, 349, 214, 20);
		frmMainWindow.getContentPane().add(lblImage);
		
		Icon = new JTextField();
		Icon.setFont(UIManager.getFont("Label.font"));
		Icon.setBounds(12, 369, 293, 25);
		frmMainWindow.getContentPane().add(Icon);
		
		JLabel lblWorkingDirectory = new JLabel("working directory: ");
		lblWorkingDirectory.setFont(UIManager.getFont("Label.font"));
		lblWorkingDirectory.setBounds(12, 393, 214, 20);
		frmMainWindow.getContentPane().add(lblWorkingDirectory);
		
		Directory = new JTextField();
		Directory.setFont(UIManager.getFont("Label.font"));
		Directory.setBounds(12, 412, 293, 25);
		frmMainWindow.getContentPane().add(Directory);
		
		chooseExe = new JButton("choose");
		chooseExe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				utils.chooseFile(Executable, "Choose runnable file");
			}
		});
		chooseExe.setFont(UIManager.getFont("Label.font"));
		chooseExe.setBounds(317, 326, 99, 25);
		frmMainWindow.getContentPane().add(chooseExe);
		
		chooseIcon = new JButton("choose");
		chooseIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				utils.chooseFile(Icon, "Choose image");
			}
		});
		chooseIcon.setFont(UIManager.getFont("Label.font"));
		chooseIcon.setBounds(317, 369, 99, 25);
		frmMainWindow.getContentPane().add(chooseIcon);
		
		chooseDir = new JButton("choose");
		chooseDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				utils.chooseDir(Directory, "Choose working directory");
			}
		});
		chooseDir.setFont(UIManager.getFont("Label.font"));
		chooseDir.setBounds(317, 412, 99, 25);
		frmMainWindow.getContentPane().add(chooseDir);
		
		generate = new JButton("generate");
		generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainPage.this.generate();
			}
		});
		generate.setFont(UIManager.getFont("Label.font"));
		generate.setBounds(12, 449, 404, 31);
		frmMainWindow.getContentPane().add(generate);
		
		choosePath = new JButton("choose");
		choosePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				utils.chooseLauncher(Path, "Choose launcher path");
			}
		});
		choosePath .setFont(UIManager.getFont("Label.font"));
		choosePath .setBounds(314, 129, 99, 25);
		frmMainWindow.getContentPane().add(choosePath);
	}
}
