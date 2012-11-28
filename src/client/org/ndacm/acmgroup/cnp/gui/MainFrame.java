package org.ndacm.acmgroup.cnp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import org.ndacm.acmgroup.cnp.CNPClient;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private Map<Integer, JTextArea> tabs; // fileID to tab component
	private int columnWidth;
	private static final int DEFAULT_WIDTH = 891;
	private static final int DEFAULT_HEIGHT = 664;
	private CNPClient cnpClient;

	private JTextArea textAreaChatInput;
	private JTextArea textAreaChat;
	private JList listUsers;
	private JList listFiles;

	private DefaultListModel modelFiles;
	private DefaultListModel modelUsers;
	
	DocumentFilter editorFilter;

	/**
	 * Create the frame.
	 */
	public MainFrame(CNPClient client) {
		this.cnpClient = client;
		editorFilter = new DoNothingFilter();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		// column width should be updated whenever window resized (some
		// percentage of the parent window)
		columnWidth = (int) (DEFAULT_WIDTH * 0.75); //
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);

		JLabel lblCurrentUsers = new JLabel("Current Users");

		modelFiles = new DefaultListModel();
		modelUsers = new DefaultListModel();

		listUsers = new JList(modelUsers);
		listUsers.setBorder(new LineBorder(new Color(0, 0, 0)));
		listUsers
		.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listUsers.setVisibleRowCount(-1);

		JLabel lblChat = new JLabel("Chat");

		textAreaChat = new JTextArea();
		textAreaChat.setEditable(false);
		textAreaChat.setLineWrap(true);
		textAreaChat.setBorder(new LineBorder(new Color(0, 0, 0)));
		JScrollPane scrollPane = new JScrollPane(textAreaChat);
		setPreferredSize(new Dimension(textAreaChat.getWidth(),
				textAreaChat.getHeight()));

		textAreaChatInput = new JTextArea();
		textAreaChatInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if (arg0.getKeyChar() == '\n') {
					if (textAreaChatInput.getText().trim().length() > 0) {
						String message = textAreaChatInput.getText().trim();
						cnpClient.sendChatMessage(message);
						textAreaChatInput.setText("");
					}
				}
			}
		});
		textAreaChatInput.setLineWrap(true);
		textAreaChatInput.setBorder(new LineBorder(new Color(0, 0, 0)));

		JScrollPane scrollPane_1 = new JScrollPane(textAreaChatInput);
		setPreferredSize(new Dimension(textAreaChatInput.getWidth(),
				textAreaChatInput.getHeight()));

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						gl_panel.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panel.createParallelGroup(
										Alignment.TRAILING)
										.addComponent(
												listUsers,
												GroupLayout.DEFAULT_SIZE,
												191, Short.MAX_VALUE)
												.addComponent(scrollPane,
														Alignment.LEADING)
														.addComponent(
																scrollPane_1,
																Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
																.addComponent(
																		lblCurrentUsers,
																		Alignment.LEADING,
																		GroupLayout.DEFAULT_SIZE,
																		191, Short.MAX_VALUE)
																		.addComponent(
																				lblChat,
																				Alignment.LEADING,
																				GroupLayout.DEFAULT_SIZE,
																				191, Short.MAX_VALUE))
																				.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
						gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblCurrentUsers)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(listUsers, GroupLayout.PREFERRED_SIZE,
								121, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(lblChat)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
										377, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE,
												42, GroupLayout.PREFERRED_SIZE)
												.addContainerGap()));
		panel.setLayout(gl_panel);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		JLabel lblWorkspace = new JLabel("Workspace");

		listFiles = new JList(modelFiles);
		listFiles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					int index = listFiles.locationToIndex(arg0.getPoint());
					cnpClient.openSourceFile((String) (listFiles.getModel().getElementAt(
							index)));
				}
			}
		});
		listFiles.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listFiles
		.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listFiles.setVisibleRowCount(-1);

		JButton btnNewFile = new JButton("New File");
		btnNewFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewFileDialog dialog = new NewFileDialog(cnpClient);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JButton btnDeleteFile = new JButton("Delete File");
		btnDeleteFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
		.setHorizontalGroup(gl_panel_1
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panel_1
								.createParallelGroup(
										Alignment.LEADING)
										.addComponent(
												listFiles,
												Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE,
												208,
												Short.MAX_VALUE)
												.addComponent(
														lblWorkspace,
														GroupLayout.PREFERRED_SIZE,
														200,
														GroupLayout.PREFERRED_SIZE)
														.addComponent(
																panel_2,
																GroupLayout.DEFAULT_SIZE,
																208,
																Short.MAX_VALUE)
																.addGroup(
																		gl_panel_1
																		.createSequentialGroup()
																		.addComponent(
																				btnDeleteFile)
																				.addPreferredGap(
																						ComponentPlacement.RELATED,
																						31,
																						Short.MAX_VALUE)
																						.addComponent(
																								btnNewFile,
																								GroupLayout.PREFERRED_SIZE,
																								94,
																								GroupLayout.PREFERRED_SIZE)))
																								.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING)
				.addGroup(
						gl_panel_1
						.createSequentialGroup()
						.addGap(10)
						.addComponent(lblWorkspace)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(listFiles,
								GroupLayout.DEFAULT_SIZE, 507,
								Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										gl_panel_1
										.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(btnNewFile)
												.addComponent(btnDeleteFile))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(panel_2,
														GroupLayout.PREFERRED_SIZE, 43,
														GroupLayout.PREFERRED_SIZE)
														.addContainerGap()));

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cnpClient.closeConnection();
				dispose();
			}
		});

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO
			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(
				Alignment.LEADING).addGroup(
						gl_panel_2
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(btnExit)
						.addPreferredGap(ComponentPlacement.RELATED, 52,
								Short.MAX_VALUE)
								.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 81,
										GroupLayout.PREFERRED_SIZE).addContainerGap()));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(
				Alignment.LEADING)
				.addGroup(
						Alignment.TRAILING,
						gl_panel_2
						.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
								.addGroup(
										gl_panel_2
										.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(btnExit)
												.addComponent(btnSave))
												.addContainerGap()));
		panel_2.setLayout(gl_panel_2);
		panel_1.setLayout(gl_panel_1);


		tabs = new HashMap<Integer, JTextArea>();
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				cnpClient.closeConnection();
				dispose();
				System.exit(0); // calling the method is a must
			}
		});
	}

	public void addTab(final int fileID, String filename) {
		final JTextArea fileTextArea = new JTextArea();
		fileTextArea.setEditable(true);
		fileTextArea.setLineWrap(true);
		fileTextArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		fileTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				cnpClient.editFile(arg0.getKeyChar(),
						fileTextArea.getCaretPosition(), fileID);
			}
		});

		JScrollPane scrollPane = new JScrollPane(fileTextArea);
		setPreferredSize(new Dimension(fileTextArea.getWidth(),
				fileTextArea.getHeight()));
		((AbstractDocument)fileTextArea.getDocument()).setDocumentFilter(editorFilter);


		tabbedPane.addTab("filename", null, scrollPane, filename);
		tabs.put(fileID, fileTextArea);
	}

	public void updateSourceTab(int fileID, int keyPressed, int editIndex)
			throws BadLocationException {

		JTextArea text = tabs.get(fileID);

		if (keyPressed == KeyEvent.VK_BACK_SPACE) {
			// delete character
			text.getDocument().remove(editIndex, 1);
		} else {
			// insert character
			text.getDocument().insertString(editIndex,
					Character.toString((char) keyPressed), null);
		}
	}

	public void updateChat(String username, String message) {
		textAreaChat.append(username + ": " + message + "\n");
	}

	public void addTab(final int fileID, String filename, String fileContent) {
		final JTextArea fileTextArea = new JTextArea(fileContent);
		fileTextArea.setEditable(true);
		fileTextArea.setLineWrap(true);
		fileTextArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		fileTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				cnpClient.editFile(arg0.getKeyChar(),
						fileTextArea.getCaretPosition(), fileID);
			}
		});

		JScrollPane scrollPane = new JScrollPane(fileTextArea);
		setPreferredSize(new Dimension(fileTextArea.getWidth(),
				fileTextArea.getHeight()));
		((AbstractDocument)fileTextArea.getDocument()).setDocumentFilter(editorFilter);

		tabbedPane.addTab(filename, null, scrollPane, filename);
		tabs.put(fileID, fileTextArea);
	}

	public void removeTab(int tabIndex) {
		tabbedPane.removeTabAt(tabIndex);
	}

	public void addToFileList(List<String> fileList) {
		for (int i = 0; i < fileList.size(); i++) {
			modelFiles.addElement(fileList.get(i));
		}
	}

	public void addToFileList(String file) {
		modelFiles.addElement(file);
	}

	/**
	 * Add the given user to the list of connected users.
	 * 
	 * @param username
	 *            the user to add
	 */
	public void addToUserList(String username) {
		modelUsers.addElement(username);
	}

	public void addToUserList(List<String> userList) {
		for (int i = 0; i < userList.size(); i++) {
			modelUsers.addElement(userList.get(i));
		}
	}

	/**
	 * Remove the given user from the list of connected users.
	 * 
	 * @param username
	 *            the user to remove
	 */
	public void removeUser(String username) {
		for (int i = 0; i < modelUsers.size(); i++) {
			if(((String)(modelUsers.elementAt(i))).compareTo(username) == 0){
				modelUsers.remove(i);
				break;
			}

		}
	}

	/**
	 * Leave the session the client is currently connected to.
	 */
	public void leaveSession() {
		// leave the current session
	}

	// http://examples.oreilly.com/jswing2/code/ch22/UpcaseFilter.java

	private class DoNothingFilter extends DocumentFilter {

	  public void insertString(DocumentFilter.FilterBypass fb, int offset,
	                String text, AttributeSet attr) throws BadLocationException
	  {
	    // do not do anything
	  }

	  public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
	                  String text, AttributeSet attr) throws BadLocationException
	  {
	    // do not do anything
	  }

	}
	
}
