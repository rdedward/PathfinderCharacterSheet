import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JMenuBar;
import java.io.*;
import java.util.*;

public class PathFinderV1 extends JFrame //implements Accessible 
{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 125;
	
	private JLabel nameL, classL, playerL, fileL;
	private JTextField nameTF,classTF,playerTF,fileTF;
	private JButton saveB, exitB;
	
	//Button handlers:
	private SaveButtonHandler cbHandler;
	private ExitButtonHandler ebHandler;
	private LoadCharacterHandler lcHandler;
	private ManualLoadCharacterHandler mlcHandler;
	private NewCharacterHandler ncHandler;
	private ForceModeHandler fmHandler;
	
	public PathFinderV1()
	{
                JFrame frame = new JFrame("TestIMenuBar1");

		nameL = new JLabel("Character Name: ", SwingConstants.RIGHT);
		classL = new JLabel("Class: ", SwingConstants.RIGHT);
		playerL = new JLabel("Player: ", SwingConstants.RIGHT);
		fileL = new JLabel("File: ", SwingConstants.RIGHT);
		
		nameTF = new JTextField(10);
		classTF = new JTextField(10);
		playerTF = new JTextField(10);
		fileTF = new JTextField(10);
                fileTF.setEditable(false);
		
		//SPecify handlers for each button and add (register) ActionListeners to each button.
		saveB = new JButton("Save");
		cbHandler = new SaveButtonHandler();
		saveB.addActionListener(cbHandler);
		exitB = new JButton("Exit");
		ebHandler = new ExitButtonHandler();
		exitB.addActionListener(ebHandler);






                //Begin Menu
		//Where the GUI is created:
		JMenuBar menuBar;
		JMenu file, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		JCheckBoxMenuItem cbMenuItem;

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_A);
		file.getAccessibleContext().setAccessibleDescription(
 		       "The only menu in this program that has menu items");
		menuBar.add(file);

		//a group of JMenuItems
		menuItem = new JMenuItem("Load Character",
		                         KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
 		       KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
 		       "This doesn't really do anything");
		mlcHandler = new ManualLoadCharacterHandler();
            	menuItem.addActionListener(mlcHandler);
		file.add(menuItem);
		
		menuItem = new JMenuItem("New Character");
		menuItem.setMnemonic(KeyEvent.VK_B);
		ncHandler = new NewCharacterHandler();
            	menuItem.addActionListener(ncHandler);
		file.add(menuItem);

		menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_D);
		file.add(menuItem);


		//a group of check box menu items
		file.addSeparator();
		cbMenuItem = new JCheckBoxMenuItem("Backup");
		cbMenuItem.setMnemonic(KeyEvent.VK_C);
		file.add(cbMenuItem);

		cbMenuItem = new JCheckBoxMenuItem("Force Mode");
		cbMenuItem.setMnemonic(KeyEvent.VK_H);
                fmHandler = new ForceModeHandler();
		cbMenuItem.addActionListener(fmHandler);
		file.add(cbMenuItem);

		//a submenu
		file.addSeparator();
		submenu = new JMenu("Saved Accounts");
		submenu.setMnemonic(KeyEvent.VK_S);
                int count = 1;
                String filename = "Accounts\\character"+count+".dat";
                File f = new File(filename);
		String title = "";
                while(f.exists()){
			try {
        	                Scanner character = new Scanner(new File("Accounts\\character"+count+".dat"));
				String field = "";
				String value = "";
        	                while(character.hasNextLine()) {
	                                field = character.next();
        	                        value = character.nextLine();
                	        	if (field.equals("name")){
						title = "character"+count+".dat  " + "("+value+")";
					}
                	        }
                        } catch (FileNotFoundException b) {
                	        System.out.println("File not found. " + "Please try again.");
                        }
                        menuItem = new JMenuItem(title);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
                        
	           	lcHandler = new LoadCharacterHandler();
              		menuItem.addActionListener(lcHandler);
                        
			submenu.add(menuItem);

                        count += 1;
                        f = new File("Accounts\\character"+count+".dat");

                }

		file.add(submenu);

                //End Menu
		setJMenuBar(menuBar);
		

		setTitle("PathFinderV1");
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(3, 1));

		
		//Add things to the pane in the order you want them to appear (left to right, top to bottom)
		pane.add(nameL);
		pane.add(nameTF);
		pane.add(classL);
		pane.add(classTF);
		pane.add(playerL);
		pane.add(playerTF);
		pane.add(fileL);
		pane.add(fileTF);
		pane.add(saveB);
		pane.add(exitB);
		
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private class SaveButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String nameS,classS,playerS;
			
			nameS = nameTF.getText(); 
			classS = classTF.getText();
			playerS = playerTF.getText();
			String content = "name " + nameS + "\nclass " + classS + "\nplayer " + playerS;
			try {
                                File file = new File("Accounts\\character"+1+".dat");
				
                                if (fileTF.getText().length() <= 2){
	 				int count2 = 1;
					file = new File("Accounts\\character"+count2+".dat");
					while(file.exists()){
						count2 += 1;
						file = new File("Accounts\\character"+count2+".dat");
        	                        }
					file.createNewFile();
                                        fileTF.setText("character"+count2+".dat");
				}
				else{
                                        System.out.println("Using fileTF");
					file = new File("Accounts\\"+fileTF.getText());
                                        if (!file.exists()){
						file.createNewFile();
                                        }
				}
				
 	 
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(content);
				bw.close();
 
				System.out.println("Done");
 
			} catch (IOException c) {
				c.printStackTrace();
			}


                        System.out.println("Saved");
			
		}
	}
	
	public class ExitButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}
	
	public class LoadCharacterHandler implements ActionListener
	{


		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source instanceof JMenuItem) {
				JMenuItem item = (JMenuItem) source;
                                Scanner filename = new Scanner(item.getText());
                                String filenameS = filename.next();
                                System.out.println(filenameS);
				
			

				fileTF.setText(filenameS);
	                        String name = " ";
	                        String classS = " ";
	                        String player = " ";
	                        String field = " ";
	                        String value = " ";
	                        try {
		                        Scanner character = new Scanner(new File("Accounts\\"+filenameS));
		                        while(character.hasNextLine()) {
		                                field = character.next();
		                                value = character.nextLine();
		                        	if (field.equals("name")){
							nameTF.setText(value);
						}
		                        	if (field.equals("class")){
							classTF.setText(value);
						}
		                        	if (field.equals("player")){
							playerTF.setText(value);
						}
		                        }
	                        } catch (FileNotFoundException b) {
        	                System.out.println("File not found. " + "Please try again.");
                        }
                        
			System.out.println("Loaded "+filenameS);
                        }
		}
	}

	public class ManualLoadCharacterHandler implements ActionListener 
	{


		public void actionPerformed(ActionEvent e) {
                        /*
                        JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Dat Files", "dat");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(parent);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("You chose to open this file: " +
				chooser.getSelectedFile().getName());
			}
                        */

			Object source = e.getSource();
			if (source instanceof JMenuItem) {
				JMenuItem item = (JMenuItem) source;
                                Scanner filename = new Scanner(item.getText());
                                String filenameS = filename.next();
                                System.out.println(filenameS);
				
			

				fileTF.setText(filenameS);
	                        String name = " ";
	                        String classS = " ";
	                        String player = " ";
	                        String field = " ";
	                        String value = " ";
	                        try {
		                        Scanner character = new Scanner(new File("Accounts\\"+filenameS));
		                        while(character.hasNextLine()) {
		                                field = character.next();
		                                value = character.nextLine();
		                        	if (field.equals("name")){
							nameTF.setText(value);
						}
		                        	if (field.equals("class")){
							classTF.setText(value);
						}
		                        	if (field.equals("player")){
							playerTF.setText(value);
						}
		                        }
	                        } catch (FileNotFoundException b) {
        	                System.out.println("File not found. " + "Please try again.");
                        }
                        
			System.out.println("Loaded "+filenameS);
                        }
		}
	}


	public class NewCharacterHandler implements ActionListener
	{


		public void actionPerformed(ActionEvent e)
		{
			nameTF.setText("");
			classTF.setText("");
			playerTF.setText("");
			fileTF.setText("");
		}
	}



	public class ForceModeHandler implements ActionListener
	{


		public void actionPerformed(ActionEvent e)
		{
			
			AbstractButton aButton = (AbstractButton) e.getSource();
     			boolean selected = aButton.getModel().isSelected();
		        if (selected) {
		        	System.out.println("Force Mode Activated");
               			fileTF.setEditable(true);

		        } else {
		        	System.out.println("Force Mode Unactivated");
               			fileTF.setEditable(false);
		        }
		}
	}
	
	public static void main(String[] args)
	{
		PathFinderV1 rectObj = new PathFinderV1();
	}
	
}


