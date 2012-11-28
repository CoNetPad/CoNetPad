package org.ndacm.acmgroup.cnp.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import org.ndacm.acmgroup.cnp.CNPClient;

/**
 * This is the GUI Dialog box for creating new sessions
 * 
 * @author Cesar Ramirez
 * 
 */
public class CreateSessionDialog extends JDialog {

	private CNPClient client;
	private SessionDialog sessionDialog;
	private CreateSessionDialog createDialog;
	private JButton btnCreate;
	private JTextField textFieldPassword;
	private JTextField textFieldPasswordAgain;

	/**
	 * Create the dialog.
	 * 
	 * @param client
	 *            that will provide the logic and network interface.
	 */
	public CreateSessionDialog(final CNPClient client) {
		this.client = client;
		this.createDialog = this;
		this.client.setCreateSessionDialog(createDialog);
		setBounds(100, 100, 389, 255);
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		final JLabel lblPasswordAgain = new JLabel("Password again:");

		JLabel lblInstructions = new JLabel(
				"<html>Sessions have names created randomly, you can share this name with other prople to give them access to your session. The name of your session will appear after you click the Create button.\r\n<p><p>\r\nIf you want you can also place a password to protect your session. This is completely optional.");

		JLabel lblPassword = new JLabel("Password:");

		textFieldPassword = new JTextField();
		textFieldPassword.setColumns(10);

		textFieldPasswordAgain = new JTextField();
		textFieldPasswordAgain.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblPasswordAgain)
														.addComponent(
																lblPassword))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																textFieldPassword,
																GroupLayout.DEFAULT_SIZE,
																270,
																Short.MAX_VALUE)
														.addComponent(
																textFieldPasswordAgain,
																GroupLayout.DEFAULT_SIZE,
																270,
																Short.MAX_VALUE))
										.addContainerGap())
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblInstructions)
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
						.addComponent(panel, Alignment.TRAILING,
								GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblInstructions)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblPassword)
														.addComponent(
																textFieldPassword,
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
																lblPasswordAgain)
														.addComponent(
																textFieldPasswordAgain,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED, 23,
												Short.MAX_VALUE)
										.addComponent(panel,
												GroupLayout.PREFERRED_SIZE, 46,
												GroupLayout.PREFERRED_SIZE)));
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pass1 = textFieldPassword.getText();
				String pass2 = textFieldPasswordAgain.getText();
				if (pass1.compareTo(pass2) == 0) {
					client.createSession(pass1);
					textFieldPassword.setEnabled(false);
					textFieldPasswordAgain.setEnabled(false);
					btnCreate.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(createDialog,
							"Passwords do not match");
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_panel.createSequentialGroup()
						.addContainerGap(186, Short.MAX_VALUE)
						.addComponent(btnCreate)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnCancel).addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.TRAILING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(btnCancel)
												.addComponent(btnCreate))
								.addContainerGap()));
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
	}

	/**
	 * Re-enable all the UI components. Usually this method gets called after
	 * the client reports an error in the submission. The components get reset
	 * so the user can fix and resubmit.
	 */
	public void resetDialog() {
		textFieldPassword.setEnabled(true);
		textFieldPasswordAgain.setEnabled(true);
		btnCreate.setEnabled(true);
	}
}