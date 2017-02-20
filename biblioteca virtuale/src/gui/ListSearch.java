package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.CellEditorListener;

import model.Book_model;
import model.PageBook_model;
import model.User_model;
import utility.GetImage;
import view.AdminView;
import view.BasicView;
import view.EditorPageView;
import view.EditorTextView;
import view.ExpertView;

public class ListSearch {
	private SinglePage singlePage;
	private AdminView admin;
	private ExpertView expert;
	private JButton open;
	private EditorPageView editorPage;
	private JButton backButton = new JButton("INDIETRO");
	private JPanel list = new JPanel(new FlowLayout());
	private EditorTextView editorText;
	private BasicView basic;
	private GetImage getImage=new GetImage();

	// ////////////////////////////////ULTIME PAGINE RIFIUTATE DAGLI
	// EDITORS/////////////7777
	public JPanel getPages(AdminView viewParam, JPanel content, User_model user)
			throws IOException {
		List<PageBook_model> listPages = new ArrayList<>();

		listPages = viewParam.lastPages();
		JPanel grid = new JPanel(new GridLayout(listPages.size(), 2, 10, 0));

		for (PageBook_model n : listPages) {
			grid = populateList(grid, n, viewParam, false, content, user, null,
					null);

		}

		this.list.add(grid);
		return this.list;
	}

	// /////////LISTA DI PAGINE IN ATTESA DI UNA TRASCRIZIONE///////////

	public JPanel getPages(ExpertView viewParam, JPanel content, User_model user)
			throws IOException {
		List<PageBook_model> listPages = new ArrayList<>();

		listPages = viewParam.listPagesTran();
		JPanel grid = new JPanel(new GridLayout(listPages.size(), 2, 10, 0));

		for (PageBook_model n : listPages) {
			grid = populateList(grid, n, viewParam, false, content, user, null,
					null);

		}

		this.list.add(grid);
		return this.list;
	}

	// ////LISTA TRASCRIZIONI DA REVISIONARE
	public JPanel getPages(EditorTextView viewParam, JPanel content) {
		List<PageBook_model> listPages = new ArrayList<>();

		listPages = viewParam.listPages();
		JPanel grid = new JPanel(new GridLayout(listPages.size(), 2, 10, 0));

		for (PageBook_model n : listPages) {
			grid = populateList(grid, n, viewParam, false, content, null, null,
					null);

		}
		this.list.add(grid);
		return this.list;
	}

	// LISTE PAGINE DA REVISIONARE
	public JPanel getPages(EditorPageView viewParam, JPanel content)
			throws IOException {
		List<PageBook_model> listPages = new ArrayList<>();

		listPages = viewParam.listPages();
		JPanel grid = new JPanel(new GridLayout(listPages.size(), 2, 10, 0));

		for (PageBook_model n : listPages) {
			grid = populateList(grid, n, viewParam, false, content, null, null,
					null);

		}

		this.list.add(grid);
		return this.list;
	}

	// // LISTA PAGINE DEL LIBRO SELEZIONATO/////////
	public JPanel getPages(Book_model book, final String group,
			final JPanel content, final List<Book_model> listBooks) {
		JPanel backButtonPan = new JPanel(new BorderLayout());
		JPanel grid;
		List<PageBook_model> listPages = new ArrayList<>();
		List<PageBook_model> listPagesTemp = new ArrayList<>();
		listPagesTemp = book.getPagebook();
		for (PageBook_model element : listPagesTemp) {
			
			if (element.getIs_confirmed().equals("yes")) {
				System.out.println("TRANNNNNN:"+element.getTranscript().getTEI());
				listPages.add(element);
			}
		}

		this.list = new JPanel(new FlowLayout());
		this.list.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.list.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		this.list.add(backButton);
		this.list.add(backButtonPan);
		try {
			
			grid = new JPanel(new GridLayout(0, 6));
		} catch (java.lang.ArithmeticException e) {
			grid = new JPanel(new GridLayout(2, 1, 0, -50));
		}
		for (PageBook_model n : listPages) {

			switch (group) {
			case "admin_user":
				this.admin = new AdminView();

				grid = populateList(grid, n, this.admin, true, content, null,
						listBooks, listPages);
				break;
			case "expert_user":
				this.expert = new ExpertView();

				grid = populateList(grid, n, this.expert, true, content, null,
						listBooks, listPages);
				break;
			case "editor_page":
				this.editorPage = new EditorPageView();
				grid = populateList(grid, n, this.editorPage, true, content,
						null, listBooks, listPages);
				break;
			case "editor_text":
				this.editorText = new EditorTextView();
				grid = populateList(grid, n, this.editorText, true, content,
						null, listBooks, listPages);
				break;

			}

		}

		this.list.add(grid, BorderLayout.WEST);

		this.backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ListBooks backPage = new ListBooks();
				JScrollPane previousPage = new JScrollPane();
				JPanel previousPagePanel = new JPanel();
				previousPagePanel = backPage.getBooksBackButton(group, content,
						listBooks);
				previousPage.setViewportView(previousPagePanel);
				content.add(previousPage, "previousPage");
				CardLayout layout = (CardLayout) content.getLayout();
				layout.show(content, "previousPage");

			}
		});

		return this.list;
	}

	// /METODO UTILIZZATO PER OTTNERE LA LISTA DELLE PAGINE DEL LIBRO A
	// DIFFERENZA DEL GET PAGE NON VIENE FATTA LA QUERY PER OTTENERE
	// LA LISTA DELLE PAGINE DEL LIBRO POICHè LA LISTA è TRA I PARAMETRI
	public JPanel getPagesBackButton(final String userGroup,
			final JPanel content, final List<Book_model> listBooksParam,
			List<PageBook_model> listPagesParam) {
		JPanel backButtonPan = new JPanel(new BorderLayout());
		JPanel grid;
		List<PageBook_model> listPages = new ArrayList<>();

		listPages = listPagesParam;

		this.list = new JPanel(new FlowLayout());
		this.list.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.list.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		this.list.add(backButton);
		this.list.add(backButtonPan);
		try {
			grid = new JPanel(new GridLayout(0, 6));
		} catch (java.lang.ArithmeticException e) {
			grid = new JPanel(new GridLayout(2, 1, 0, -50));
		}
		for (PageBook_model n : listPages) {
			switch (userGroup) {
			case "admin_user":
				this.admin = new AdminView();

				grid = populateList(grid, n, this.admin, true, content, null,
						listBooksParam, listPages);
				break;
			case "expert_user":
				this.expert = new ExpertView();

				grid = populateList(grid, n, this.expert, true, content, null,
						listBooksParam, listPages);
				break;
			case "editor_page":
				this.editorPage = new EditorPageView();
				grid = populateList(grid, n, this.editorPage, true, content,
						null, listBooksParam, listPages);
				break;
			case "editor_text":
				this.editorText = new EditorTextView();
				grid = populateList(grid, n, this.editorText, true, content,
						null, listBooksParam, listPages);
				break;

			}

		}

		this.list.add(grid, BorderLayout.WEST);

		this.backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ListBooks backPage = new ListBooks();
				JScrollPane previousPage = new JScrollPane();
				JPanel previousPagePanel = new JPanel();
				previousPagePanel = backPage.getBooksBackButton(userGroup,
						content, listBooksParam);
				previousPage.setViewportView(previousPagePanel);
				content.add(previousPage, "previousPage");
				CardLayout layout = (CardLayout) content.getLayout();
				layout.show(content, "previousPage");

			}
		});

		return this.list;
	}

	private <T> JPanel populateList(JPanel grid, final PageBook_model n,
			final T viewParam, final boolean isSearch, final JPanel content,
			final User_model user, final List<Book_model> listBooks,
			final List<PageBook_model> listPages) {
		this.singlePage = new SinglePage();
		JPanel openbutpan = new JPanel();
		ImageIcon img = new ImageIcon();
		JLabel label = new JLabel();
		JLabel book = new JLabel();
		JPanel descr = new JPanel(new GridLayout(2, 1, 0, -50));
		this.open = new JButton("APRI");
		int page = n.getNum_pag();

		if (isSearch == false) {
			String bookName = n.getBook().getName();
			if (viewParam instanceof AdminView) {
				book = new JLabel(bookName + " (" + String.valueOf(page) + ")|user:"+n.getUser().getUsername(),
						SwingConstants.CENTER);

			}
			else{
			book = new JLabel(bookName + " (" + String.valueOf(page) + ")",
					SwingConstants.CENTER);
			}
		} else {
			book = new JLabel("(" + String.valueOf(page) + ")",
					SwingConstants.CENTER);
		}
		if (viewParam instanceof AdminView) {
			this.admin = new AdminView();

		} else if (viewParam instanceof ExpertView) {
			this.expert = new ExpertView();

		} else if (viewParam instanceof EditorPageView) {
			this.editorPage = new EditorPageView();

		} else if (viewParam instanceof EditorTextView) {
			this.editorText = new EditorTextView();

		}
		img = getImage.getImageList(n.getImage());
		label.setIcon(img);
		descr.add(book);

		openbutpan.add(this.open);
		descr.add(openbutpan);
		this.open.setPreferredSize(new Dimension(100, 30));

		grid.add(label);
		grid.add(descr);

		this.open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JScrollPane page = new JScrollPane();
				JPanel pagePanel = new JPanel();
				try {
					if (isSearch == true) {
						pagePanel = singlePage.GetPage(n, viewParam, listPages,
								listBooks, content);
					} else {
						if (viewParam instanceof ExpertView) {
							pagePanel = singlePage.AddTranscriptPanel(n,
									expert, user);
						} else if (viewParam instanceof AdminView) {
							pagePanel = singlePage.GetPage(n, viewParam,
									listPages, listBooks, content);
						} else if (viewParam instanceof EditorPageView) {
							pagePanel = singlePage.EditorPagePanel(n,
									editorPage, user);
						} else if (viewParam instanceof EditorTextView) {
							pagePanel = singlePage.EditorTextPanel(n,
									editorText, user);
						}

					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				page.setViewportView(pagePanel);
				content.add(page, "single_page");
				CardLayout layout = (CardLayout) content.getLayout();
				layout.show(content, "single_page");

			}
		});

		return grid;
	}

	// LISTA ULTIME PAGINE E TRASCRIZIONI CONFERMATE PER BASIC_USER
	public JPanel getPages(BasicView view, JPanel content) {
		this.basic = new BasicView();
		List<PageBook_model> listPages = new ArrayList<>();
		List<PageBook_model> listTranscripts = new ArrayList<>();
		JPanel grid = new JPanel(new GridLayout(2, 1, 20, 0));
		JPanel listPagesPan = new JPanel(new BorderLayout());
		JPanel listTranPan = new JPanel(new BorderLayout());
		JPanel FlowPages = new JPanel(new FlowLayout());
		JPanel FlowTranscripts = new JPanel(new FlowLayout());
		FlowPages.setLayout(new FlowLayout(FlowLayout.LEFT));
		FlowPages.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		FlowTranscripts.setLayout(new FlowLayout(FlowLayout.LEFT));
		FlowTranscripts
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		try {
			listPages = this.basic.listPages();
			listTranscripts = this.basic.listTrascripts();
			for (PageBook_model element : listPages) {
				JLabel JLabel;
				JPanel gridElement = new JPanel(new GridLayout(2, 1, 0, -70));
				ImageIcon img = getImage.getImageList(element.getImage());
				gridElement.add(new JLabel(img));
				gridElement.add(JLabel = new JLabel(element.getBook().getName()
						+ " (" + element.getNum_pag() + ")"));
				JLabel.setHorizontalAlignment(SwingConstants.CENTER);
				FlowPages.add(gridElement);
			}
			for (PageBook_model element : listTranscripts) {
				JLabel JLabel;
				JPanel gridElement = new JPanel(new GridLayout(2, 1, 0, -70));
				ImageIcon img = getImage.getImageList(element.getImage());
				gridElement.add(new JLabel(img));
				gridElement.add(JLabel = new JLabel(element.getBook().getName()
						+ " (" + element.getNum_pag() + ")"));
				JLabel.setHorizontalAlignment(SwingConstants.CENTER);
				FlowTranscripts.add(gridElement);
			}
			listPagesPan.add(new JLabel("LISTA PAGINE"), BorderLayout.NORTH);
			listPagesPan.add(FlowPages, BorderLayout.CENTER);
			grid.add(listPagesPan);
			listTranPan.add(new JLabel("LISTA TRASCRIZIONI"),
					BorderLayout.NORTH);
			listTranPan.add(FlowTranscripts, BorderLayout.CENTER);
			grid.add(listTranPan);
			this.list.add(grid);
		} catch (java.lang.NullPointerException e) {
			this.list.add(new JLabel("NESSUNA PAGINA PRESENTE"));
		}

		return this.list;
	}

}
