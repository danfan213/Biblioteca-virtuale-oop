package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import view.RegistrationView;

public class Registration {

	private JFrame regJFrame;
	private JTextField txtuser;
	private JPasswordField txtpass;
	private JTextField txtemail;
	private JPasswordField txtconfpass;
	private RegistrationView regView = new RegistrationView();

	public JFrame initialize() {
		this.regJFrame = new JFrame("REGISTRAZIONE");
		JButton register = new JButton("registrati");
		JPanel loginPanel = new JPanel(new BorderLayout());
		JPanel buttonContainer = new JPanel(new FlowLayout());
		JPanel labelContainer = new JPanel(new GridLayout(4, 2));
		JLabel username = new JLabel("username");
		this.txtuser = new JTextField();
		this.txtuser.setPreferredSize(new Dimension(150, 30));
		labelContainer.add(username);
		labelContainer.add(this.txtuser);
		JLabel password = new JLabel("password");
		this.txtpass = new JPasswordField();
		this.txtpass.setPreferredSize(new Dimension(150, 30));
		labelContainer.add(password);
		labelContainer.add(this.txtpass);
		JLabel conf_password = new JLabel("conferma password");
		this.txtconfpass = new JPasswordField();
		this.txtconfpass.setPreferredSize(new Dimension(150, 30));
		labelContainer.add(conf_password);
		labelContainer.add(this.txtconfpass);
		JLabel email = new JLabel("email");
		this.txtemail = new JTextField();
		this.txtemail.setPreferredSize(new Dimension(150, 30));
		labelContainer.add(email);
		labelContainer.add(this.txtemail);
		buttonContainer.add(register);
		loginPanel.add(buttonContainer, BorderLayout.SOUTH);
		loginPanel.add(labelContainer, BorderLayout.CENTER);
		this.regJFrame.add(loginPanel);
		this.regJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.regJFrame.setPreferredSize(new Dimension(300, 200));
		this.regJFrame.pack();
		this.regJFrame.setLocationRelativeTo(null);
		this.regJFrame.setResizable(false);
		this.regJFrame.setVisible(true);
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = txtuser.getText();
				String password = String.valueOf(txtpass.getPassword());
				String conf_password = String.valueOf(txtconfpass.getPassword());
				String email = txtemail.getText();
				if (username.equals("") || password.equals("")
						|| conf_password.equals("") || email.equals("")) {
					JOptionPane.showMessageDialog(regJFrame,
							"riempire tutti i campi", "ERRORE",
							JOptionPane.PLAIN_MESSAGE);
				} else if (!password.equals(conf_password)) {
					JOptionPane
							.showMessageDialog(
									regJFrame,
									"password e conferma password devono essere uguali",
									"ERRORE", JOptionPane.PLAIN_MESSAGE);

				} else {
					boolean response = regView.registrazione(username,
							password, email);
					if (response == true) {
						regJFrame.setVisible(false);
						regView.register();

					} else {
						JOptionPane.showMessageDialog(regJFrame,
								"esiste già un utente con questo username",
								"ERRORE", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});
		return this.regJFrame;
	}

}
