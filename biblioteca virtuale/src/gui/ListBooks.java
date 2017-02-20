package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import model.Author_model;
import model.Book_model;
import model.User_model;
import utility.GetImage;
import view.AdminView;
import view.BasicView;
import view.EditorPageView;
import view.EditorTextView;
import view.ExpertView;

public class ListBooks {
	private JPanel list = new JPanel();
	private JPanel grid;
	private ListSearch listSearch;
	private AdminView admin;
	private ExpertView expert;
	private String user;
	private List<Book_model> listBooks;
	private EditorPageView editorPage;
	private EditorTextView editorText;
	private BasicView basic_user;
	private GetImage getImage=new GetImage();

	// /LISTA LIBRI RICERCA
	public JPanel getBooks(String title, User_model userModel, JPanel content) {
		try {
			this.user = userModel.getGroup();
		} catch (java.lang.NullPointerException e) {
			this.user = "basic_user";
		}
		this.listBooks = new ArrayList<>();

		int size = 0;
		switch (this.user) {
		case "admin_user":
			this.admin = new AdminView();
			this.listBooks = this.admin.getBookSearch(title);
			break;
		case "expert_user":
			this.expert = new ExpertView();
			this.listBooks = this.expert.getBookSearch(title);
			break;
		case "editor_page":
			this.editorPage = new EditorPageView();
			this.listBooks = this.editorPage.getBookSearch(title);
			break;
		case "editor_text":
			this.editorText = new EditorTextView();
			this.listBooks = this.editorText.getBookSearch(title);
			break;
		case "basic_user":
			this.basic_user = new BasicView();

			this.listBooks = this.basic_user.getBookSearch(title);
			break;

		}

		size = this.listBooks.size();

		this.grid = new JPanel(new GridLayout(size, 2, 10, 10));

		for (Book_model n : this.listBooks) {
			this.grid = populateList(n, content);
		}

		this.list.add(this.grid);
		return this.list;
	}

	// BOTTONE LISTA LIBRI
	public JPanel getBooks(User_model userModel, JPanel content) {
		this.user = userModel.getGroup();
		this.listBooks = new ArrayList<>();
		int size = 0;
		switch (this.user) {

		case "expert_user":
			this.expert = new ExpertView();
			this.listBooks = expert.getBookList();
			break;

		}
		size = this.listBooks.size();

		this.grid = new JPanel(new GridLayout(size, 2, 10, 10));

		for (Book_model n : this.listBooks) {
			this.grid = populateList(n, content);
		}

		this.list.add(grid);
		return this.list;
	}

	// LISTA LIBRI AUTORE SELEZIONATO
	public JPanel getBooks(String user, JPanel content, Author_model author) {
		this.user = user;
		ImageIcon image=new ImageIcon();
		this.listBooks = new ArrayList<>();
		int idAuthor = author.getId_author();
		int size = 0;
		switch (this.user) {

		case "expert_user":
			this.expert = new ExpertView();
			this.listBooks = expert.getAuthorBookList(idAuthor);
			
			break;
		case "admin_user":
			this.admin = new AdminView();

			this.listBooks = admin.getAuthorBookList(idAuthor);
			break;
		case "editor_page":
			this.editorPage = new EditorPageView();

			this.listBooks = editorPage.getAuthorBookList(idAuthor);
			break;
		case "editor_text":
			this.editorText = new EditorTextView();

			this.listBooks = editorText.getAuthorBookList(idAuthor);
			break;

		}
		size = this.listBooks.size();
		image=getImage.getImageAuthor(author.getImage());
		this.grid = new JPanel(new GridLayout(size+1, 1, 10, 10));
		this.grid.add(new JLabel(author.getName().toUpperCase(),SwingConstants.CENTER));
		this.grid.add(new JLabel (image));
		for (Book_model n : this.listBooks) {
			this.grid = populateList(n, null);
		}

		this.list.add(grid);
		return this.list;
	}

	// /LISTA LIBRI DOPO AVER PREMUTO BOTTONE INDIETRO
	public JPanel getBooksBackButton(String userGroup, JPanel content,
			List<Book_model> listBooksParam) {
		this.user = userGroup;
		this.listBooks = listBooksParam;
		int size = 0;
		size = listBooks.size();

		this.grid = new JPanel(new GridLayout(size, 2, 10, 10));

		for (Book_model n : listBooks) {
			this.grid = populateList(n, content);
		}

		this.list.add(grid);
		return this.list;
	}

	private JPanel populateList(final Book_model n, final JPanel content) {
		this.listSearch = new ListSearch();
		JLabel label = new JLabel();
		JPanel descr = new JPanel(new GridLayout(3, 1, 0, 0));
		JPanel author = new JPanel();
		JButton buttonAuth = new JButton();

		JButton open = new JButton("APRI");
		ImageIcon img = new ImageIcon();
		JPanel openbutpan = new JPanel();

		String book = n.getName();
		int page = n.getNum_total_page();

		switch (this.user) {
		case "admin_user":
			this.admin = new AdminView();
			if (content != null) {
				author.add(new JLabel("autore: " + n.getAuthor().getName(),
						SwingConstants.CENTER));

				author.add(buttonAuth = new JButton("altri libri"));
			}
			break;
		case "expert_user":
			this.expert = new ExpertView();
			if (content != null) {
				author.add(new JLabel("autore: " + n.getAuthor().getName(),
						SwingConstants.CENTER));

				author.add(buttonAuth = new JButton("altri libri"));
			}

			break;
		case "editor_page":
			this.editorPage=new EditorPageView();
			if (content != null) {
				author.add(new JLabel("autore: " + n.getAuthor().getName(),
						SwingConstants.CENTER));

				author.add(buttonAuth = new JButton("altri libri"));
			}
			break;
		case "editor_text":
			this.editorText=new EditorTextView();
			if (content != null) {
				author.add(new JLabel("autore: " + n.getAuthor().getName(),
						SwingConstants.CENTER));

				author.add(buttonAuth = new JButton("altri libri"));
			}
			break;
		case "basic_user":
			openbutpan.setVisible(false);
			break;

		}
		img = getImage.getImageList(n.getImage());
		label.setIcon(img);

		grid.add(label);
		descr.add(new JLabel(book + " (" + String.valueOf(page) + ")",
				SwingConstants.CENTER));

		descr.add(author);
		openbutpan.add(open);
		if (content != null) {
			descr.add(openbutpan);
		}
		open.setPreferredSize(new Dimension(100, 20));
		grid.add(descr);

		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				list.removeAll();
				JScrollPane page = new JScrollPane();
				JPanel pagePanel = new JPanel();
				pagePanel = listSearch.getPages(n, user, content, listBooks);
				page.setViewportView(pagePanel);
				content.add(page, "list_pages");
				CardLayout layout = (CardLayout) content.getLayout();

				layout.show(content, "list_pages");

			}
		});
		buttonAuth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				list.removeAll();
				JScrollPane page = new JScrollPane();
				JPanel pagePanel = new JPanel();
				pagePanel = getBooks(user, null, n.getAuthor());
				page.setViewportView(pagePanel);
				content.add(page, "list_pages");
				CardLayout layout = (CardLayout) content.getLayout();

				layout.show(content, "list_pages");

			}
		});
		return grid;
	}

}
