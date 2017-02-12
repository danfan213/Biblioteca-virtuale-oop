package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import view.AdminView;

public class PermessiList {
	private JPanel list = new JPanel();

	public JPanel getList() {
		AdminView view = new AdminView();
		HashMap<String, String> users = new HashMap<>();
		users = view.usersGroup();
		Iterator it = users.entrySet().iterator();

		JPanel row = new JPanel(new GridLayout(users.size(), 5));

		while (it.hasNext()) {

			Map.Entry entry = (Map.Entry) it.next();

			populateList((String) entry.getKey(), (String) entry.getValue(),
					row);

		}
		return this.list;
	}

	private void populateList(final String username, final String group,
			JPanel row) {
		JButton conf = new JButton("conferma");
		JRadioButton rb1 = new JRadioButton("expert_user");
		rb1.setActionCommand(rb1.getText());
		JRadioButton rb2 = new JRadioButton("admin_user");
		rb2.setActionCommand(rb2.getText());
		JRadioButton rb3 = new JRadioButton("editor_page");
		rb3.setActionCommand(rb3.getText());
		JRadioButton rb4 = new JRadioButton("editor_text");
		rb4.setActionCommand(rb4.getText());

		if (group.equals("expert_user")) {
			rb1.setSelected(true);
		} else if (group.equals("admin_user")) {
			rb2.setSelected(true);
		} else if (group.equals("editor_page")) {
			rb3.setSelected(true);
		} else if (group.equals("editor_text")) {
			rb4.setSelected(true);
		}
		final ButtonGroup bg = new ButtonGroup();
		bg.add(rb1);
		bg.add(rb2);
		bg.add(rb3);
		bg.add(rb4);
		row.add(new JLabel(username));
		row.add(rb1);
		row.add(rb2);
		row.add(rb3);
		row.add(rb4);
		row.add(conf);

		conf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean resp;
				AdminView view = new AdminView();

				resp = view.updateGroup(username, bg.getSelection()
						.getActionCommand());
				if (resp == false) {
					JOptionPane.showMessageDialog(list, "Operazione fallita ",
							"Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(list,
							"Operazione completata ", "information",
							JOptionPane.INFORMATION_MESSAGE);
					list.removeAll();
					list=getList();
					list.revalidate();
				}
			}
		});

		this.list.add(row);

	}

}
