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

/**
 * This a dialog box for logging a user in
 * 
 * @author Cesar Ramirez
 * 
 */
public class LoginDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private LoginDialog loginDialog;
	private CNPClient client;
	private JFormattedTextField formattedUsername;
	private JFormattedTextField formattedPassword;
	private JButton btnRegister;
	private JButton btnLogIn;
	private JButton btnCancel;

	/**
	 * Create the dialog.
	 * 
	 * @param client
	 *            that will provide the logic and network interface.
	 */
	public LoginDialog(final CNPClient client) {
		loginDialog = this;
		this.client = client;
		this.client.setLogDialog(this);
		setTitle("CoNetPad Client");
		setBounds(100, 100, 486, 151);
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		JLabel lblUsername = new JLabel("Username:");
		JLabel lblPassword = new JLabel("Password:");

		formattedUsername = new JFormattedTextField();

		formattedPassword = new JFormattedTextField();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 454,
								Short.MAX_VALUE)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblPassword)
														.addComponent(
																lblUsername))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																formattedUsername,
																GroupLayout.DEFAULT_SIZE,
																335,
																Short.MAX_VALUE)
														.addComponent(
																formattedPassword,
																GroupLayout.DEFAULT_SIZE,
																335,
																Short.MAX_VALUE))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblUsername)
														.addComponent(
																formattedUsername,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(6)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblPassword)
														.addComponent(
																formattedPassword,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED,
												167, Short.MAX_VALUE)
										.addComponent(panel,
												GroupLayout.PREFERRED_SIZE, 45,
												GroupLayout.PREFERRED_SIZE)));
		{
			btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					client.closeConnection();
					dispose();
				}
			});

			btnRegister = new JButton("Register");
			btnRegister.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					RegisterDialog dialog = new RegisterDialog(client);
					dialog.setLoginDialog(loginDialog);
					dialog.setModalityType(ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});

			btnLogIn = new JButton("Log In");
			btnLogIn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!formattedUsername.getText().isEmpty()
							&& !formattedPassword.getText().isEmpty()) {
						client.loginToAccount(formattedUsername.getText(),
								formattedPassword.getText());
						formattedPassword.setEnabled(false);
						formattedUsername.setEnabled(false);
						btnRegister.setEnabled(false);
						btnLogIn.setEnabled(false);
					}
				}
			});

			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
					Alignment.LEADING).addGroup(
					gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnRegister)
							.addPreferredGap(ComponentPlacement.RELATED, 105,
									Short.MAX_VALUE).addComponent(btnLogIn)
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
											.addComponent(btnLogIn)
											.addComponent(btnRegister))
							.addContainerGap()));
			panel.setLayout(gl_panel);
			getContentPane().setLayout(groupLayout);
		}
	}

	/**
	 * This method will call the next step in the login process. The current
	 * dialog will be disposed and an instance of SessionDialog will be created.
	 */
	public void openSessionDialog() {
		SessionDialog dialog = new SessionDialog(client);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		loginDialog.setVisible(false);
		loginDialog.dispose();
	}

	/**
	 * Re-enable all the UI components. Usually this method gets called after
	 * the client reports an error in the submission. The components get reset
	 * so the user can fix and resubmit.
	 */
	public void resetDialog() {
		formattedPassword.setEnabled(true);
		formattedUsername.setEnabled(true);
		btnRegister.setEnabled(true);
		btnLogIn.setEnabled(true);
	}

	/**
	 * @param text
	 *            to be placed in the username field.
	 */
	public void setUsername(String text) {
		formattedUsername.setText(text);
	}
}
