package org.ndacm.acmgroup.cnp.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import org.ndacm.acmgroup.cnp.CNPClient;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class SessionDialog extends JDialog {

	private SessionDialog loginDialog;
	private CNPClient client;
	private JPasswordField passwordField;

	/**
	 * Create the dialog.
	 */
	public SessionDialog(final CNPClient client) {
		loginDialog = this;
		this.client = client;
		this.client.setSessionDialog(this);
		setTitle("CoNetPad Client");
		setBounds(100, 100, 466, 218);
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JLabel lblSession = new JLabel("Session name:");

		final JFormattedTextField formattedSession = new JFormattedTextField();

		JLabel lblInfo = new JLabel(
				"Enter the name of a session to join or create:");

		JLabel lblUsePassword = new JLabel(
				"<html>-If you are creating a session, you may use a password to protect it.");

		JLabel lblifYouAre = new JLabel(
				"<html>-If you are accessing a password-protected session, enter the password here, leave it empty otherwise.");

		JLabel lblPassword_1 = new JLabel("Password (Optional):");

		passwordField = new JPasswordField();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 462,
								Short.MAX_VALUE)
						.addGroup(
								groupLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblInfo)
										.addContainerGap(128, Short.MAX_VALUE))
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																lblifYouAre,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																438,
																Short.MAX_VALUE)
														.addComponent(
																lblUsePassword,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																438,
																Short.MAX_VALUE))
										.addContainerGap())
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblPassword_1)
														.addComponent(
																lblSession))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																passwordField,
																GroupLayout.DEFAULT_SIZE,
																276,
																Short.MAX_VALUE)
														.addComponent(
																formattedSession,
																GroupLayout.DEFAULT_SIZE,
																276,
																Short.MAX_VALUE))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblInfo)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblSession)
														.addComponent(
																formattedSession,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblPassword_1)
														.addComponent(
																passwordField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(10)
										.addComponent(lblUsePassword)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(lblifYouAre)
										.addPreferredGap(
												ComponentPlacement.RELATED, 16,
												Short.MAX_VALUE)
										.addComponent(panel,
												GroupLayout.PREFERRED_SIZE, 45,
												GroupLayout.PREFERRED_SIZE)));
		{
			JButton btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					client.closeConnection();
					dispose();
				}
			});
			JButton btnAccess = new JButton("Access");
			btnAccess.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!formattedSession.getText().isEmpty()
							&& !passwordField.getText().isEmpty()) {
						
					}
				}
			});

			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
					Alignment.LEADING).addGroup(
					Alignment.TRAILING,
					gl_panel.createSequentialGroup()
							.addContainerGap(282, Short.MAX_VALUE)
							.addComponent(btnAccess)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCancel).addContainerGap()));
			gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
					Alignment.TRAILING).addGroup(
					gl_panel.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE,
									Short.MAX_VALUE)
							.addGroup(
									gl_panel.createParallelGroup(
											Alignment.BASELINE)
											.addComponent(btnCancel)
											.addComponent(btnAccess))
							.addContainerGap()));
			panel.setLayout(gl_panel);
			getContentPane().setLayout(groupLayout);
		}
	}

	public void openMainFrame() {
		MainFrame frame = new MainFrame(client);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		loginDialog.setVisible(false);
		loginDialog.dispose();

	}
}
