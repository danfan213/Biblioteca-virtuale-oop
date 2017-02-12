package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.User_model;
import view.LoginView;

public class Login {
	private JFrame logJFrame;
	private JTextField txtuser;
	private JPasswordField txtpass;
	private JButton basicUser;
	private JButton register;
	private JButton login;
	private boolean logbut = false;
	LoginView logview = new LoginView();

	public JFrame Initialize() {
		this.logJFrame = new JFrame("LOGIN");
		this.basicUser = new JButton("login come ospite");
		this.register = new JButton("registrati");
		this.login = new JButton("login");
		JPanel loginPanel = new JPanel(new BorderLayout());
		JPanel buttonContainer = new JPanel(new FlowLayout());
		JPanel labelContainer = new JPanel(new GridLayout(2, 2));
		JPanel textuserPan=new JPanel(new BorderLayout());
		JPanel textpassPan=new JPanel(new BorderLayout());
		JLabel username = new JLabel("username");
		
		this.txtuser = new JTextField();
		this.txtuser.setPreferredSize(new Dimension(150, 20));
		labelContainer.add(username);
		textuserPan.add(this.txtuser,BorderLayout.CENTER);
		labelContainer.add(textuserPan);
		JLabel password = new JLabel("password");
		this.txtpass = new JPasswordField();
		this.txtpass.setPreferredSize(new Dimension(150, 20));
		labelContainer.add(password);
		textpassPan.add(this.txtpass,BorderLayout.CENTER);
		labelContainer.add(textpassPan);
		buttonContainer.add(this.login);
		buttonContainer.add(this.register);
		buttonContainer.add(this.basicUser);
		loginPanel.add(buttonContainer, BorderLayout.SOUTH);
		loginPanel.add(labelContainer, BorderLayout.CENTER);
		this.logJFrame.add(loginPanel);
		this.logJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.logJFrame.setPreferredSize(new Dimension(400, 300));
		this.logJFrame.pack();
		this.logJFrame.setLocationRelativeTo(null);
		this.logJFrame.setResizable(false);
		this.logJFrame.setVisible(true);
		this.login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = txtuser.getText();
				char[] password = txtpass.getPassword();

				if (username.equals("") || password.equals("")) {
					JOptionPane.showMessageDialog(logJFrame,
							"riempire tutti i campi", "ERRORE",
							JOptionPane.PLAIN_MESSAGE);
					logbut = false;
				}
				List<User_model> list = new ArrayList<>();
				list = logview.loginCheck(username, password);
				if (list.isEmpty()) {

					logbut = false;
				} else {

					User_model u = list.get(0);

					logJFrame.setVisible(false);
					logview.login(u);
					logbut = true;
				}
				if (logbut == false) {
					JOptionPane.showMessageDialog(logJFrame,
							"utente non esistente", "ERRORE",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		});

		this.register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logJFrame.setVisible(false);
				logview.registrazione();

			}
		});
		this.basicUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logJFrame.setVisible(false);

				logview.logAsBasic();

			}
		});
		return this.logJFrame;
	}

}
