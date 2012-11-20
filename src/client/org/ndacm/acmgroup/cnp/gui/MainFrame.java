package org.ndacm.acmgroup.cnp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private Map<Integer, JTextField> tabs; // fileID to tab component
	private int columnWidth;
	private static final int DEFAULT_WIDTH = 891;
	private static final int DEFAULT_HEIGHT = 664;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		// column width should be updated whenever window resized (some percentage of the parent window)
		columnWidth = (int) (DEFAULT_WIDTH * 0.75); // 
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);

		JLabel lblCurrentUsers = new JLabel("Current Users");

		JList list = new JList();
		list.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblChat = new JLabel("Chat");

		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		JScrollPane scrollPane = new JScrollPane(textArea);
		setPreferredSize(new Dimension(textArea.getWidth(),
				textArea.getHeight()));

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setLineWrap(true);
		textArea_1.setBorder(new LineBorder(new Color(0, 0, 0)));

		JScrollPane scrollPane_1 = new JScrollPane(textArea_1);
		setPreferredSize(new Dimension(textArea_1.getWidth(),
				textArea_1.getHeight()));

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(list, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING)
						.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblCurrentUsers, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblChat, Alignment.LEADING))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCurrentUsers)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblChat)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);

		JTree tree = new JTree();
		tree.setBorder(new LineBorder(new Color(0, 0, 0)));
		JLabel lblWorkspace = new JLabel("Workspace");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panel_1
										.createParallelGroup(Alignment.LEADING)
										.addComponent(tree,
												GroupLayout.DEFAULT_SIZE, 200,
												Short.MAX_VALUE)
										.addComponent(lblWorkspace))
						.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblWorkspace)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(tree, GroupLayout.DEFAULT_SIZE, 569,
								Short.MAX_VALUE).addContainerGap()));
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
	
	public void updateSourceTab(int fileID, int keyPressed, int editIndex) throws BadLocationException {
		JTextField text = tabs.get(fileID);
		text.getDocument().insertString(editIndex, Character.toString((char) keyPressed), null);
	}
}
