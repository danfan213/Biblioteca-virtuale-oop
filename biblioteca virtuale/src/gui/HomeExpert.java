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

import model.User_model;
import view.ExpertView;

public class HomeExpert {
	private JFrame window = new JFrame("HOME");
	private JPanel panel = new JPanel(new BorderLayout());
	private JPanel addTrascriptPanel = new JPanel();
	private JScrollPane ListTrascript = new JScrollPane();
	private JPanel listBooksPanel = new JPanel();
	private JPanel listAllBooksPanel = new JPanel();
	private AddPage addPage = new AddPage();
	private ListBooks listbook = new ListBooks();
	private ListSearch listpages = new ListSearch();
	private JPanel content = new JPanel();
	private JScrollPane listBooks = new JScrollPane();
	private JScrollPane listAllBooks = new JScrollPane();
	private JPanel menu = new JPanel(new FlowLayout());
	private JPanel searchPanel = new JPanel();
	private JTextField searchField = new JTextField();
	private JButton searchBut = new JButton("SEARCH");
	private JButton addPageBut = new JButton("AGGIUNGI PAGINA");
	private JButton addTranscript = new JButton("AGGIUNGI TRASCRIZIONE");
	private JButton logout = new JButton("LOGOUT");
	private JButton listaLibri = new JButton("LISTA LIBRI");
	private ExpertView view = new ExpertView();

	public void getHome(final User_model user) throws IOException {
		JPanel first = new JPanel();
		final CardLayout layout = new CardLayout();
		this.content.setLayout(layout);
		this.content.add(first, "test");
		this.menu.add(addPageBut);
		this.menu.add(listaLibri);
		this.menu.add(addTranscript);
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

		this.addPageBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listBooksPanel.removeAll();
				listAllBooksPanel.removeAll();
				addPage.getPanel(user);

			}
		});

		searchBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listBooksPanel.removeAll();
				listAllBooksPanel.removeAll();
				listBooksPanel = listbook.getBooks(searchField.getText(), user,
						content);
				listBooks.setViewportView(listBooksPanel);
				content.add(listBooks, "list_book");
				layout.show(content, "list_book");

			}
		});

		listaLibri.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listAllBooksPanel.removeAll();
				listBooksPanel.removeAll();

				listAllBooksPanel = listbook.getBooks(user, content);

				listAllBooks.setViewportView(listAllBooksPanel);
				//
				content.add(listAllBooks, "list_Allbook");
				layout.show(content, "list_Allbook");

			}
		});

		this.addTranscript.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listAllBooksPanel.removeAll();
				listBooksPanel.removeAll();
				addTrascriptPanel.removeAll();

				try {
					addTrascriptPanel = listpages.getPages(view, content, user);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				ListTrascript.setViewportView(addTrascriptPanel);
				content.add(ListTrascript, "addTrascriptPanel");
				layout.show(content, "addTrascriptPanel");

			}
		});

	}
}
