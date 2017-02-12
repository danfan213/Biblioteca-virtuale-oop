package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.User_model;
import view.AdminView;

public class UserList {
	private JPanel list = new JPanel();

	public JPanel getUserList(User_model user) {
		list.removeAll();
		List<String> usernameList = new ArrayList<>();
		AdminView view = new AdminView(user);
		usernameList = view.usernameList();
		JPanel row = new JPanel(new GridLayout(usernameList.size(), 2));

		for (String username : usernameList) {
			pupulate_list(username, row, user);

		}

		return this.list;
	}

	public void pupulate_list(String name, JPanel panel, final User_model user) {
		JButton delete = new JButton("delete");
		final JLabel username = new JLabel(name);
		panel.add(username);
		panel.add(delete);
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean resp;
				AdminView view = new AdminView();

				resp = view.deleteUser(username.getText());
				if (resp == false) {
					JOptionPane.showMessageDialog(list, "Operazione fallita ",
							"Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(list,
							"Operazione completata ", "information",
							JOptionPane.INFORMATION_MESSAGE);
					list=getUserList(user);
					list.revalidate();

				}
			}
		});
		this.list.add(panel);

	}

}
