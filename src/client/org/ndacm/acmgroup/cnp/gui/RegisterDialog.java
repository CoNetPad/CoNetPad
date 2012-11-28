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
import javax.swing.SwingUtilities;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import org.ndacm.acmgroup.cnp.CNPClient;

public class RegisterDialog extends JDialog {

	private CNPClient client;
	private LoginDialog logDialog;
	private RegisterDialog regDialog;

	/**
	 * Create the dialog.
	 */
	public RegisterDialog(final CNPClient client) {
		this.client = client;
		this.regDialog = this;
		this.client.setRegDialog(regDialog);
		setBounds(100, 100, 375, 200);
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		final JLabel lblUsername = new JLabel("Username:");

		final JLabel lblPassword = new JLabel("Password:");

		final JLabel lblPasswordAgain = new JLabel("Password again:");

		final JLabel lblEmail = new JLabel("Email:");

		final JFormattedTextField formattedUsername = new JFormattedTextField();

		final JFormattedTextField formattedEmail = new JFormattedTextField();

		final JFormattedTextField formattedPassword = new JFormattedTextField();

		final JFormattedTextField formattedPasswordAgain = new JFormattedTextField();
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
																lblPassword)
														.addComponent(
																lblUsername)
														.addComponent(lblEmail))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																formattedPasswordAgain,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																242,
																Short.MAX_VALUE)
														.addComponent(
																formattedPassword,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																271,
																Short.MAX_VALUE)
														.addComponent(
																formattedEmail,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																269,
																Short.MAX_VALUE)
														.addComponent(
																formattedUsername,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																269,
																Short.MAX_VALUE))
										.addContainerGap())
						.addComponent(panel, Alignment.TRAILING,
								GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE));
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
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(lblEmail)
														.addComponent(
																formattedEmail,
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
																lblPassword)
														.addComponent(
																formattedPassword,
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
																formattedPasswordAgain,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED,
												GroupLayout.DEFAULT_SIZE,
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
		final JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formattedPassword.getText().contentEquals(
						formattedPasswordAgain.getText())) {
					client.createAccount(formattedUsername.getText(),
							formattedEmail.getText(),
							formattedPassword.getText());
					formattedUsername.setEnabled(false);
					formattedEmail.setEnabled(false);
					formattedPassword.setEnabled(false);
					formattedPasswordAgain.setEnabled(false);
					btnCreate.setEnabled(false);
					Runnable doWorkRunnable = new Runnable() {
						public void run() {
							logDialog.setUsername(formattedUsername.getText());
						}
					};
					SwingUtilities.invokeLater(doWorkRunnable);
				} else {
					JOptionPane.showMessageDialog(regDialog,
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

	public void setLoginDialog(LoginDialog dialog) {
		this.logDialog = dialog;
	}
}
