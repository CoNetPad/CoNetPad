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

public class RegisterDialog extends JDialog {
	private CNPClient client;
	private LoginDialog logDialog;

	/**
	 * Create the dialog.
	 */
	public RegisterDialog(final CNPClient client) {
		this.client = client;
		setBounds(100, 100, 375, 200);
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		final JLabel lblUsername = new JLabel("Username:");

		final JLabel lblPassword = new JLabel("Password:");

		final JLabel lblPasswordAgain = new JLabel("Password again:");

		final JLabel lblEmail = new JLabel("Email:");

		JFormattedTextField formattedTextField = new JFormattedTextField();

		JFormattedTextField formattedTextField_1 = new JFormattedTextField();

		JFormattedTextField formattedTextField_2 = new JFormattedTextField();

		JFormattedTextField formattedTextField_3 = new JFormattedTextField();
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
																formattedTextField_3,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																242,
																Short.MAX_VALUE)
														.addComponent(
																formattedTextField_2,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																271,
																Short.MAX_VALUE)
														.addComponent(
																formattedTextField_1,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																269,
																Short.MAX_VALUE)
														.addComponent(
																formattedTextField,
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
																formattedTextField,
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
																formattedTextField_1,
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
																formattedTextField_2,
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
																formattedTextField_3,
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
				logDialog.setVisible(true);
				dispose();
			}
		});
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lblPassword.getText().contentEquals(
						lblPasswordAgain.getText())) {
					client.createAccount(lblUsername.getText(),
							lblPassword.getText(), lblEmail.getText());
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_panel.createSequentialGroup()
						.addContainerGap(211, Short.MAX_VALUE)
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
}
