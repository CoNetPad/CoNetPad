package org.ndacm.acmgroup.cnp.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import org.ndacm.acmgroup.cnp.CNPClient;

public class ServerConnectionDialog extends JDialog {

	private CNPClient client;
	private JDialog serverConnectionDialog = this;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			ServerConnectionDialog dialog = new ServerConnectionDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ServerConnectionDialog() {
		client = new CNPClient();
		setTitle("CoNetPad Client");
		setBounds(100, 100, 382, 117);
		JLabel lblServer = new JLabel("Server:");

		JCheckBox chckbxRemember = new JCheckBox("Remember");

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		final JFormattedTextField formattedServer = new JFormattedTextField();
		formattedServer.setText("localhost");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 384,
								Short.MAX_VALUE)
						.addGroup(
								Alignment.LEADING,
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblServer)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																chckbxRemember)
														.addComponent(
																formattedServer,
																GroupLayout.DEFAULT_SIZE,
																297,
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
																formattedServer,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblServer))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(chckbxRemember)
										.addPreferredGap(
												ComponentPlacement.RELATED, 47,
												Short.MAX_VALUE)
										.addComponent(panel,
												GroupLayout.PREFERRED_SIZE, 49,
												GroupLayout.PREFERRED_SIZE)));

		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (client.connectToServer(formattedServer.getText())) {
					LoginDialog dialog = new LoginDialog(client);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					serverConnectionDialog.setVisible(false);
					serverConnectionDialog.dispose();
				} else {
					JOptionPane.showMessageDialog(serverConnectionDialog,
							"Error connecting to server.");
				}
			}
		});

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.TRAILING).addGroup(
				gl_panel.createSequentialGroup()
						.addContainerGap(199, Short.MAX_VALUE)
						.addComponent(btnConnect).addGap(18)
						.addComponent(btnExit).addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.TRAILING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(btnExit)
												.addComponent(btnConnect))
								.addContainerGap()));
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
	}
}
