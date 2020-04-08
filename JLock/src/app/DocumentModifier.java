package app;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DocumentModifier extends JFrame {

	private JPanel contentPane;
	private JTable table;
	//int r = 0;
	String[][] data;
	int lines;
	//int idNum = 1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DocumentModifier frame = new DocumentModifier();
					//UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DocumentModifier() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		
		JSeparator separator = new JSeparator();
		
		
		JButton btnAddEntry = new JButton("Add Entry");
		btnAddEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String application = JOptionPane.showInputDialog("Input an application name!");
				if(application.isEmpty()) {
					return;
				}
				String username = JOptionPane.showInputDialog("Input a username!");
				if(username.isEmpty()) {
					return;
				}
				String password = JOptionPane.showInputDialog("Input a password!");
				if(password.isEmpty()) {
					return;
				}
		
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[] {model.getRowCount()+1, application, username, password});
				
				PrintWriter writer = null;
				try {
					writer = new PrintWriter(new FileWriter(JLock.selectedFile, true));
				} catch (FileNotFoundException | UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				writer.println(model.getRowCount() + "0x0005" + application + "0xE503" + username + "0xF4R4" + password + "0xOP22");
				
				updateLines();
				updateData();
				writer.close();
				
			}
			
		});
		
		
		
		JButton btnRemoveEntry = new JButton("Remove Entry");
		btnRemoveEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
					int row = table.getSelectedRow();
					int col = table.getSelectedColumn();
					
					if(row == -1 && col == -1) {
						JOptionPane.showMessageDialog(contentPane, "Please select a cell to delete.");
						return;
					}
					System.out.println("Selected: " + row + ", " + col);
					int response = JOptionPane.showConfirmDialog(contentPane, "Remove ID: " + (row+1) + " from the manager?");
					if(response == JOptionPane.YES_OPTION) {
						System.out.println("Ordered deletion of " + row + ", " + col);
						
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.removeRow(row);
						
						ArrayList<String> arr = JLock.readData(JLock.selectedFile);
						arr.remove(row);
						File fl = JLock.selectedFile;
						System.out.println("Copied " + fl.getAbsolutePath());
						JLock.selectedFile.delete();
						
						PrintWriter writer = null;
						try {
							writer = new PrintWriter(new FileWriter(fl, true));
						} catch (FileNotFoundException | UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						for(int i = 0; i < arr.size(); i++) {
							writer.println(arr.get(i));
							System.out.println("Wrote line: " + i + " to " + fl.getAbsolutePath());
						}
						
						writer.close();
						System.out.println("Task Completed.");
					}
					return;
				/*
				int index = -1;
				try {
				index = Integer.parseInt(JOptionPane.showInputDialog("Please enter the ID # of the column you would like to delete..."));
				}catch(Exception e) {
					JOptionPane.showMessageDialog(contentPane, "You must input a number!");
					return;
				}
				
				int response = JOptionPane.showConfirmDialog(contentPane, "Remove " + index + " from the manager?");
				if(response == JOptionPane.YES_OPTION) {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					
					
					// Werite everytthing in the file except skip the index they want to delete.
					
					ArrayList<String> old_file = JLock.readData(JLock.selectedFile);
					old_file.remove(index); //REMOVE THE OLD INDEX
					
					PrintWriter writer = null;
					try {
						writer = new PrintWriter(new FileWriter(JLock.selectedFile, true));
					} catch (FileNotFoundException | UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for(String s: old_file) {
						writer.println(s);
					}
					
					
					model.removeRow(index);
					updateData();
					updateLines();
					
				}
				return;
				*/
			}
			
		});
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fileChooser = new JFileChooser();
				
				 fileChooser.setDialogTitle("Choose a location to save the export.");

				 fileChooser.setAcceptAllFileFilterUsed(false);
			     FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT Files", "txt");
			     fileChooser.setFileFilter(filter);
			    
				 if(fileChooser.showOpenDialog(contentPane) == JFileChooser.APPROVE_OPTION) {
					 File f = fileChooser.getSelectedFile();
					 
					
					 PrintWriter writer = null;
					 try {
						writer = new PrintWriter(new FileWriter(f, true));
					} catch (IOException e) {
						JOptionPane.showMessageDialog(contentPane, "This program does not have permission to access this location.\nTry running the program as administrator.");
					}
					 	String[][] temp = new String[lines][4];
					 	System.out.println();
					 	System.out.println("Copying data to file.");
					 	int mgr = 0;
						for(int i = 0; i < lines; i++) {
							temp[i][0] = JLock.parseID(JLock.readData(JLock.selectedFile).get(i));
							mgr++;
						}
						for(int i = 0; i < lines; i++) {
							temp[i][1] = JLock.parseApplication(JLock.readData(JLock.selectedFile).get(i));
							mgr++;
						}
						for(int i = 0; i < lines; i++) {
							temp[i][2] = JLock.parseUsername(JLock.readData(JLock.selectedFile).get(i));
							mgr++;
						}
						for(int i = 0; i < lines; i++) {
							temp[i][3] = JLock.parsePassword(JLock.readData(JLock.selectedFile).get(i));
							mgr++;
						}
						
						
						
						
						
					 for(int i = 0; i < temp.length; i++) {
						 for(int j = 0; j < temp.length; j++) {
							 mgr++;
							 if(j == 0) {
								 writer.print("ID: " + temp[i][j] + " ");
							 }else if(j == 1) {
								 writer.print("Application: " + temp[i][j] + " ");
							 }else if(j == 2) {
								 writer.print("Username: " + temp[i][j] + " ");
							 }else if(j == 3) {
								 writer.print("Password: " + temp[i][j] + " ");
							 }
						 }
						 writer.println();
					 }
					 writer.close();
					 System.out.println("Ran " + mgr + " loop runs.");
					 System.out.println("Finished task.");
				 }
				 
			}
		});
		
		JButton btnDeleteAll = new JButton("Delete All");
		btnDeleteAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int selection = JOptionPane.showConfirmDialog(contentPane, "Remove everything? (This will wipe the file)");
				if(selection != JOptionPane.YES_OPTION) {
					return;
				}
				
				String dir = JLock.selectedFile.getAbsolutePath();
				JLock.selectedFile.delete();
				try {
					new File(dir).createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(contentPane, "Please press the reload button.");
				System.out.println("Created new file.");
				
			}
		});
		
		JButton btnSaveAs = new JButton("Save As");
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				JLock.main(null);
				
			}
		});
		
		String[] columnNames = {
				"ID #", "APPLICATION", "USERNAME", "PASSWORD"
		};
		
		updateData();
		
		table = new JTable(new DefaultTableModel(data, columnNames));
		table.setToolTipText("Password Manager");
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(true);
		JScrollPane sp = new JScrollPane(table);
		
		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	updateData();
				
				dispose();
				DocumentModifier.main(null);
				
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnAddEntry, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnRemoveEntry, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnExport, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnDeleteAll, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
					.addGap(43)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnExit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnSaveAs, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
					.addGap(35)
					.addComponent(btnReload)
					.addGap(86))
				.addComponent(sp, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(4)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAddEntry)
						.addComponent(btnExport)
						.addComponent(btnSaveAs)
						.addComponent(btnReload))
					.addGap(7)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRemoveEntry)
						.addComponent(btnDeleteAll)
						.addComponent(btnExit))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addComponent(sp, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private int findLines() throws FileNotFoundException {
		return data.length;
	}
	
	private void updateData() {
		updateLines();
		data = new String[lines][4];
		for(int i = 0; i < lines; i++) {
			data[i][0] = JLock.parseID(JLock.readData(JLock.selectedFile).get(i));
		}
		for(int i = 0; i < lines; i++) {
			data[i][1] = JLock.parseApplication(JLock.readData(JLock.selectedFile).get(i));
		}
		for(int i = 0; i < lines; i++) {
			data[i][2] = JLock.parseUsername(JLock.readData(JLock.selectedFile).get(i));
		}
		for(int i = 0; i < lines; i++) {
			data[i][3] = JLock.parsePassword(JLock.readData(JLock.selectedFile).get(i));
		}
		
		
	}
	
	private void updateLines() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(JLock.selectedFile));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		lines = 0;
		try {
			while (reader.readLine() != null) lines++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
