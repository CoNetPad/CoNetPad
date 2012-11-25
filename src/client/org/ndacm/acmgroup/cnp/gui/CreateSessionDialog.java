package org.ndacm.acmgroup.cnp.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import org.ndacm.acmgroup.cnp.CNPClient;
import javax.swing.JPasswordField;

public class CreateSessionDialog extends JDialog {

	private CNPClient client;
	private SessionDialog sessionDialog;
	private CreateSessionDialog createDialog;
	private JPasswordField passwordField;
	private JPasswordField passwordAgainField;
	private JButton btnCreate;

	/**
	 * Create the dialog.
	 */
	public CreateSessionDialog(final CNPClient client) {
		this.client = client;
		this.createDialog = this;
		this.client.setCreateSessionDialog(createDialog);
		setBounds(100, 100, 376, 227);
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		final JLabel lblPasswordAgain = new JLabel("Password again:");

		JLabel lblInstructions = new JLabel(
				"<html>Sessions have names created randomly, you can share this name with other prople to give them access to your session. The name of your session will appear after you click the Create button.\r\n<p>\r\nIf you want you can also place a password to protect your session. This is completely optional.");

		JLabel lblPassword = new JLabel("Password:");

		passwordField = new JPasswordField();

		passwordAgainField = new JPasswordField();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, Alignment.TRAILING,
						GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblInstructions)
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblPassword)
								.addGap(33)
								.addComponent(passwordField,
										GroupLayout.DEFAULT_SIZE, 257,
										Short.MAX_VALUE).addContainerGap())
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblPasswordAgain)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(passwordAgainField,
										GroupLayout.DEFAULT_SIZE, 257,
										Short.MAX_VALUE).addContainerGap()));
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
																passwordField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblPassword))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblPasswordAgain)
														.addComponent(
																passwordAgainField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED, 9,
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

				if (passwordField.getPassword().toString()
						.compareTo(passwordAgainField.getPassword().toString()) == 0) {

					client.createSession(passwordField.getPassword().toString());
					passwordField.setEnabled(false);
					passwordAgainField.setEnabled(false);
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

	public void resetDialog() {
		passwordField.setEnabled(true);
		passwordAgainField.setEnabled(true);
		btnCreate.setEnabled(true);
	}
}