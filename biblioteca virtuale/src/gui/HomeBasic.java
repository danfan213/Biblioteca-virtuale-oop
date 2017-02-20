package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import view.BasicView;

public class HomeBasic {

	private JFrame window = new JFrame("HOME");
	private JPanel panel = new JPanel(new BorderLayout());
	private JPanel listBooksPanel = new JPanel();
	private JPanel listPagesPanel = new JPanel();
	private JScrollPane listPages = new JScrollPane();
	private ListBooks listbook = new ListBooks();
	private ListSearch pages = new ListSearch();
	private JPanel content = new JPanel();
	private JScrollPane listBooks = new JScrollPane();
	private JPanel menu = new JPanel(new FlowLayout());
	private JPanel searchPanel = new JPanel();
	private JTextField searchField = new JTextField();
	private JButton searchBut = new JButton("SEARCH");
	private JButton listPageBut = new JButton("LISTA PAGINE");
	private JButton logout = new JButton("LOGOUT");
	private BasicView view = new BasicView();

	public void getHome() throws IOException {
		// this.permessi = this.list.getList();
		// this.userList = this.Ulist.getUserList(user);

		JPanel test = new JPanel(new BorderLayout());

		// JLabel testl = new JLabel("CIAO");
		// test.add(testl);
		final CardLayout layout = new CardLayout();
		this.content.setLayout(layout);
		this.content.add(test, "test");
		this.menu.add(listPageBut);
		// this.menu.add(visual);
		this.menu.add(logout);
		this.searchPanel.add(searchField);
		this.searchField.setPreferredSize(new Dimension(250, 30));
		this.searchPanel.add(searchBut);
		this.menu.add(searchPanel, BorderLayout.NORTH);
		this.panel.add(menu, BorderLayout.NORTH);
		this.panel.add(content, BorderLayout.CENTER);
		this.window.add(panel);
		this.window.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.window.pack();
		this.window.setVisible(true);

		this.logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				view.logout(window);

			}
		});

		this.listPageBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listBooksPanel.removeAll();
				listPagesPanel.removeAll();
				listPagesPanel = pages.getPages(view, content);
				listPages.setViewportView(listPagesPanel);
				content.add(listPages, "list_pages");
				layout.show(content, "list_pages");

			}
		});

		searchBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listBooksPanel.removeAll();
				listPagesPanel.removeAll();
				listBooksPanel = listbook.getBooks(searchField.getText(), null,
						content);
				listBooks.setViewportView(listBooksPanel);
				content.add(listBooks, "list_book");
				layout.show(content, "list_book");

			}
		});

	}

}
