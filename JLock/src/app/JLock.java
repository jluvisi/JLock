package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class JLock {

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	public static File selectedFile = null;

	// Github first commit test
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JLock window = new JLock();
					window.frame.setVisible(true);
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					SwingUtilities.updateComponentTreeUI(window.frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JLock() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblTitle = new JLabel("JLock Password Manager");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		
		
		JSeparator separator = new JSeparator();
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblUsername = new JLabel("Username");
		
		JLabel lblKey = new JLabel("File Key");
		
		usernameField = new JTextField();
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		
		JSeparator separator_1 = new JSeparator();
		
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				final String username = usernameField.getText();
				if(username.equals("jluvisi")) {
					
					if(selectedFile == null) {
						JOptionPane.showMessageDialog(frame, "Please select a file to open!");
					}else {
						// Open the password view
						DocumentModifier.main(null);
					}
					
				}else {
					JOptionPane.showMessageDialog(frame, "Incorrect Login!");
				}
				
			}
		});
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JButton btnNewDoc = new JButton("Create New Manager");
		btnNewDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Open the doc modifier
				//DocumentModifier.main(null);
			}
		});
		
		JLabel lblFilePath = new JLabel("no file selected");
		
		JButton btnSelect = new JButton("Select File");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				 fileChooser.setAcceptAllFileFilterUsed(false);
			     FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT Files", "txt");
			     fileChooser.addChoosableFileFilter(filter);
			     fileChooser.showOpenDialog(null);
			     
			     System.out.println("User selected for deletion: " + fileChooser.getSelectedFile().getAbsolutePath());
			     selectedFile = fileChooser.getSelectedFile();
			     lblFilePath.setText(selectedFile.toString());
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTitle)
						.addComponent(separator, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(lblLogin)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUsername)
								.addComponent(lblKey))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(passwordField)
								.addComponent(usernameField, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED, 240, Short.MAX_VALUE))
						.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnLogin)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExit))
						.addComponent(btnNewDoc)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnSelect)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblFilePath)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLogin)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsername)
						.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKey)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnLogin)
						.addComponent(btnExit))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewDoc)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSelect)
						.addComponent(lblFilePath))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	
	
	public static String parseID(String s) {
		return s.split("0x0005")[0];
	}
	public static String parseApplication(String s) {
		int index = s.indexOf("0x0005");
		String temp = s.split("0xE503")[0];
		return temp.substring(index+6, temp.length());
	}
	public static String parseUsername(String s) {
		int index = s.indexOf("0xE503");
		String temp = s.split("0xF4R4")[0];
		return temp.substring(index+6, temp.length());
		//return s.split("0xF4R4")[0];
	}
	public static String parsePassword(String s) {
		int index = s.indexOf("0xF4R4");
		String temp = s.split("0xOP22")[0];
		return temp.substring(index+6, temp.length());
		//return s.split("0xOP22")[0];
	}
	
	/**
	 * ADDRESS KEYS:
	 * 0x0005 -> ID
	 * 0xE503 -> APPLICATION
	 * 0xF4R4 -> USERNAME
	 * 0xOP22 -> PASSWORD
	 */
	public static ArrayList<String> readData(File f) {
		
		ArrayList<String> data = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String str;
			while((str = br.readLine()) != null) {
			data.add(str);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
