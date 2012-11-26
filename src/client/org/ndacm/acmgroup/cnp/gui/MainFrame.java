package org.ndacm.acmgroup.cnp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;

import org.ndacm.acmgroup.cnp.CNPClient;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private Map<Integer, JTextField> tabs; // fileID to tab component
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

	/**
	 * Create the frame.
	 */
	public MainFrame(CNPClient client, List<String> sessionFiles) {
		this.cnpClient = client;
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
		listUsers.setLayoutOrientation(JList.HORIZONTAL_WRAP);
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
					if (textAreaChatInput.getText().length() > 0) {
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
														GroupLayout.PREFERRED_SIZE,
														67,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblChat,
														Alignment.LEADING))
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
								249, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE,
								42, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		panel.setLayout(gl_panel);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		JLabel lblWorkspace = new JLabel("Workspace");

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO
			}
		});

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cnpClient.closeConnection();
				dispose();
			}
		});

		for (int i = 0; i < sessionFiles.size(); i++) {
			modelFiles.addElement(sessionFiles.get(i));
		}

		listFiles = new JList(modelFiles);
		listFiles.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listFiles
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listFiles.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listFiles.setVisibleRowCount(-1);

		JButton btnNewFile = new JButton("New File");
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
																GroupLayout.DEFAULT_SIZE,
																200,
																Short.MAX_VALUE)
														.addGroup(
																Alignment.TRAILING,
																gl_panel_1
																		.createSequentialGroup()
																		.addComponent(
																				btnExit)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				92,
																				Short.MAX_VALUE)
																		.addComponent(
																				btnSave))
														.addComponent(
																lblWorkspace)
														.addComponent(
																btnNewFile))
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
										GroupLayout.PREFERRED_SIZE, 481,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnNewFile)
								.addPreferredGap(ComponentPlacement.RELATED,
										51, Short.MAX_VALUE)
								.addGroup(
										gl_panel_1
												.createParallelGroup(
														Alignment.BASELINE)
												.addComponent(btnSave)
												.addComponent(btnExit))
								.addContainerGap()));
		panel_1.setLayout(gl_panel_1);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		// add to
		tabbedPane.addTab("New tab", null, panel_2, null);

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_3, null);
	}

	public void addTab(int fileID, String filename) {
		JTextField fileTextField = new JTextField(columnWidth);
		tabbedPane.addTab("filename", null, fileTextField, filename);
		tabs.put(fileID, fileTextField);
	}

	public void updateSourceTab(int fileID, int keyPressed, int editIndex)
			throws BadLocationException {
		JTextField text = tabs.get(fileID);
		text.getDocument().insertString(editIndex,
				Character.toString((char) keyPressed), null);
	}

	public void updateChat(String username, String message) {
		textAreaChat.append(username + ": " + message + "\n");
	}

	public void addTab(int fileID, String filename, String fileContent) {
		JTextField fileTextField = new JTextField(fileContent, columnWidth);
		tabbedPane.addTab(filename, null, fileTextField, filename);
		tabs.put(fileID, fileTextField);
	}

	public void removeTab(int tabIndex) {
		tabbedPane.removeTabAt(tabIndex);
	}

	public void addToFileList(List<String> fileList) {
		for (int i = 0; i < fileList.size(); i++) {
			modelFiles.addElement(fileList.get(i));
		}
	}

	public void addToFileList(String filename) {
		modelFiles.addElement(filename);
	}

	/**
	 * Add the given user to the list of connected users.
	 * 
	 * @param username
	 *            the user to add
	 */
	public void addUser(String username) {
		modelUsers.addElement(username);
	}

	/**
	 * Remove the given user from the list of connected users.
	 * 
	 * @param username
	 *            the user to remove
	 */
	public void removeUser(String username) {
		// TODO implement
	}

	/**
	 * Leave the session the client is currently connected to.
	 */
	public void leaveSession() {
		// leave the current session
	}
}
