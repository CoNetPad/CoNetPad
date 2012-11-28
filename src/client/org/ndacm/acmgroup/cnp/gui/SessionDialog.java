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
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import org.ndacm.acmgroup.cnp.CNPClient;

/**
 * This is the class for joining an existing session
 * 
 * @author Cesar Ramirez
 * 
 */
public class SessionDialog extends JDialog {

	private SessionDialog loginDialog;
	private CNPClient client;
	private JPasswordField passwordField;

	private JButton btnAccess;
	private JFormattedTextField formattedSession;
	private JButton btnCreate;

	/**
	 * Create the dialog.
	 * 
	 * @param client
	 *            that will provide the logic and network interface.
	 */
	public SessionDialog(final CNPClient client) {
		loginDialog = this;
		this.client = client;
		this.client.setSessionDialog(this);
		setTitle("CoNetPad Client");
		setBounds(100, 100, 394, 218);
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JLabel lblSession = new JLabel("Session name:");

		formattedSession = new JFormattedTextField();

		JLabel lblInfo = new JLabel("Enter the name of a session to join:");

		JLabel lblifYouAre = new JLabel(
				"<html>-If you are accessing a password-protected session, enter the password here, leave it empty otherwise.");

		JLabel lblPassword_1 = new JLabel("Password (Optional):");

		passwordField = new JPasswordField();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 378,
								Short.MAX_VALUE)
						.addGroup(
								groupLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblInfo)
										.addContainerGap(150, Short.MAX_VALUE))
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
																253,
																Short.MAX_VALUE)
														.addComponent(
																formattedSession,
																GroupLayout.DEFAULT_SIZE,
																253,
																Short.MAX_VALUE))
										.addContainerGap())
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblifYouAre,
												GroupLayout.DEFAULT_SIZE, 358,
												Short.MAX_VALUE)
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
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(lblifYouAre)
										.addPreferredGap(
												ComponentPlacement.RELATED, 44,
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
			btnAccess = new JButton("Access");
			btnAccess.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!formattedSession.getText().isEmpty()) {
						client.joinSession(formattedSession.getText(),
								new String(passwordField.getPassword()));
						formattedSession.setEnabled(false);
						passwordField.setEnabled(false);
						btnAccess.setEnabled(false);
						btnCreate.setEnabled(false);
					}
				}
			});

			btnCreate = new JButton("Create Session");
			btnCreate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CreateSessionDialog dialog = new CreateSessionDialog(client);
					dialog.setModalityType(ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});

			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
					Alignment.TRAILING).addGroup(
					gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnCreate)
							.addPreferredGap(ComponentPlacement.RELATED, 129,
									Short.MAX_VALUE).addComponent(btnAccess)
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
											.addComponent(btnAccess)
											.addComponent(btnCreate))
							.addContainerGap()));
			panel.setLayout(gl_panel);
			getContentPane().setLayout(groupLayout);
		}
	}

	/**
	 * This method will create a MainFrame(main GUI class).This method should be
	 * called when the the CNPClient recieved a successful
	 * JoinSessionTaskResponse.
	 * 
	 * @return reference to the created MainFrame
	 */
	public MainFrame openMainFrame() {
		MainFrame frame = new MainFrame(client);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		loginDialog.setVisible(false);
		loginDialog.dispose();
		return frame;
	}

	/**
	 * Re-enable all the UI components. Usually this method gets called after
	 * the client reports an error in the submission. The components get reset
	 * so the user can fix and resubmit.
	 */
	public void resetDialog() {
		formattedSession.setEnabled(true);
		passwordField.setEnabled(true);
		btnAccess.setEnabled(true);
		btnCreate.setEnabled(true);
	}

	/**
	 * @param sessionName name of the session
	 */
	public void setSessionName(String sessionName) {
		formattedSession.setText(sessionName);
	}
}
