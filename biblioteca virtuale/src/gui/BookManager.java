package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import model.Book_model;
import model.User_model;
import view.AdminView;

public class BookManager {
	private JPanel list = new JPanel();
	private AdminView view;

	// STRUTTURA DEL JPANEL
	public JPanel getBooks(final User_model user) {
		this.list.removeAll();
		HashMap<String, Book_model> BookList = new HashMap<>();
		this.view = new AdminView(user);
		JPanel column;
		JPanel insertPanel = new JPanel();
		JPanel row = new JPanel(new GridLayout(BookList.size(), 3));
		JButton insertNewBook = new JButton("INSERiSCI NUOVO LIBRO");
		JButton insertNewAuthor = new JButton("INSERISCI NUOVO AUTORE");

		try {
			BookList = this.view.getBookManagerList();
			column = new JPanel(new GridLayout(BookList.size() + 1, 1, 10, 20));

		} catch (java.lang.IndexOutOfBoundsException e) {
			BookList = null;
			column = new JPanel(new GridLayout(2, 1));
		}

		insertNewBook.setPreferredSize(new Dimension(200, 50));
		insertPanel.add(insertNewBook);

		insertNewAuthor.setPreferredSize(new Dimension(200, 50));
		insertPanel.add(insertNewAuthor);
		column.add(insertPanel);
		try {
			Iterator it = BookList.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				pupulate_list(entry, row, user, column);

			}
		} catch (java.lang.NullPointerException e) {
			column.add(new JLabel("NESSUN LIBRO PRESENTE"));
		}
		this.list.add(column);

		insertNewBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ///
				insertBookButton(user);
			}
		});
		insertNewAuthor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				insertAuthorButton(user);

			}
		});
		this.list.revalidate();
		return this.list;
	}

	// ///////LISTA DEI LIBRI DA GESTIRE////////////////
	private void pupulate_list(final Map.Entry entry, JPanel panel,
			final User_model user, JPanel column) {

		JButton delete = new JButton("delete");
		JButton update = new JButton("update");
		final JLabel book = new JLabel((String) entry.getKey());

		this.view = new AdminView();

		panel.add(book);
		panel.add(delete);
		panel.add(update);
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean resp;
				view = new AdminView();

				resp = view.deleteBook(book.getText());
				if (resp == false) {
					JOptionPane.showMessageDialog(list, "operazione fallita ",
							"Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(list,
							"operazione completata ", "information",
							JOptionPane.INFORMATION_MESSAGE);
					list = getBooks(user);
					list.revalidate();

				}
			}
		});
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateBook(entry, user);

			}
		});

		column.add(panel);

	}

	// INSERISCI NUOVO LIBRO
	private void insertBookButton(User_model user) {
		this.view = new AdminView();
		JTextField name = new JTextField();
		String nameSelIm;
		JPanel myPanel = new JPanel(new GridLayout(5, 2));
		JFormattedTextField anno = null;
		JFormattedTextField numPag = null;
		JButton imageBut = new JButton("Select File");
		final JFileChooser fileChooser = new JFileChooser();
		JComboBox<String> ComboBoxAuthor = new JComboBox<>();
		HashMap<String, Integer> listAuthor = new HashMap<>();
		myPanel.add(new JLabel("nome"));
		myPanel.add(name);
		myPanel.add(new JLabel("image"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files",
				"jpg", "png", "tif"));
		imageBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {

				}
			}
		});
		myPanel.add(imageBut);

		listAuthor = this.view.listAuthor();
		Iterator it = listAuthor.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			ComboBoxAuthor.addItem((String) entry.getKey());

		}
		myPanel.add(new JLabel("autore"));
		myPanel.add(ComboBoxAuthor);
		myPanel.add(new JLabel("anno"));
		try {

			myPanel.add(anno = new JFormattedTextField(
					new MaskFormatter("####")));
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		myPanel.add(new JLabel("num pagine"));
		try {
			myPanel.add(numPag = new JFormattedTextField(new MaskFormatter(
					"###")));
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"nouvo libro", JOptionPane.OK_CANCEL_OPTION);
		try {
			nameSelIm = fileChooser.getSelectedFile().getName();

		} catch (java.lang.NullPointerException e1) {
			nameSelIm = null;

		}
		if (result == JOptionPane.OK_OPTION) {
			if (name.getText().equals("")) {
				JOptionPane.showMessageDialog(myPanel,
						"Inserire nome, anno e numero pagine del libro",
						"Error", JOptionPane.ERROR_MESSAGE);

			} else {
				if (nameSelIm == null) {
					try {
						boolean ris = this.view
								.insertBook(name.getText(), listAuthor
										.get(ComboBoxAuthor.getSelectedItem()),
										Integer.parseInt(anno.getText()),
										Integer.parseInt(numPag.getText()));
						if (ris == false) {
							JOptionPane.showMessageDialog(list,
									"Inserimento fallito ", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(list,
									"Inserimento completato ", "information",
									JOptionPane.INFORMATION_MESSAGE);
							this.list.removeAll();
							this.list = getBooks(user);
							this.list.revalidate();
						}
					} catch (java.lang.NumberFormatException
							| FileNotFoundException e2) {
						JOptionPane
								.showMessageDialog(
										myPanel,
										"Inserimento fallito:numero pagina o anno non valido",
										"Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					try {
						boolean ris = this.view
								.insertBook(name.getText(), listAuthor
										.get(ComboBoxAuthor.getSelectedItem()),
										fileChooser.getSelectedFile(), Integer
												.parseInt(anno.getText()),
										Integer.parseInt(numPag.getText()));
						if (ris == false) {
							JOptionPane.showMessageDialog(list,
									"Inserimento fallito ", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(list,
									"Inserimento completato ", "information",
									JOptionPane.INFORMATION_MESSAGE);
							this.list.removeAll();
							this.list = getBooks(user);
							this.list.revalidate();
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(myPanel,
								"Inserimento fallito ", "Error",
								JOptionPane.ERROR_MESSAGE);
					} catch (java.lang.NumberFormatException e2) {
						e2.printStackTrace();
						JOptionPane
								.showMessageDialog(
										myPanel,
										"Inserimento fallito:numero pagina o anno non valido",
										"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}

	}

	// MODIFICA LIBRO ESISTENTE///
	public void updateBook(Map.Entry entry, User_model user) {
		JTextField name = new JTextField();
		String nameSelIm;
		JPanel myPanel = new JPanel(new GridLayout(5, 2));
		JFormattedTextField anno = null;
		JFormattedTextField numPag = null;
		JButton imageBut = new JButton("Select File");
		JComboBox<String> ComboBoxAuthor = new JComboBox<>();
		HashMap<String, Integer> listAuthor = new HashMap<>();
		final JFileChooser fileChooser = new JFileChooser();
		this.view = new AdminView();
		Book_model model = (Book_model) entry.getValue();

		name.setText(model.getName());

		myPanel.add(new JLabel("nome"));
		myPanel.add(name);
		myPanel.add(new JLabel("image"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files",
				"jpg", "png", "tif"));
		imageBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {

				}
			}
		});
		myPanel.add(imageBut);

		listAuthor = this.view.listAuthor();
		Iterator it = listAuthor.entrySet().iterator();
		System.out.println(model.getAuthor().getId_author() + "id");
		while (it.hasNext()) {
			Map.Entry entry1 = (Map.Entry) it.next();
			ComboBoxAuthor.addItem((String) entry1.getKey());
			if ((int) entry1.getValue() == model.getAuthor().getId_author()) {
				ComboBoxAuthor.setSelectedItem(entry1.getKey());
			}

		}
		myPanel.add(new JLabel("autore"));
		myPanel.add(ComboBoxAuthor);
		myPanel.add(new JLabel("anno"));
		try {

			myPanel.add(anno = new JFormattedTextField(
					new MaskFormatter("####")));
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		myPanel.add(new JLabel("num pagine"));
		try {
			myPanel.add(numPag = new JFormattedTextField(new MaskFormatter(
					"###")));
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"nouvo libro", JOptionPane.OK_CANCEL_OPTION);
		try {
			nameSelIm = fileChooser.getSelectedFile().getName();

		} catch (java.lang.NullPointerException e1) {
			nameSelIm = null;

		}
		if (result == JOptionPane.OK_OPTION) {
			if (name.getText().equals("")) {
				JOptionPane.showMessageDialog(myPanel,
						"Inserire nome, anno e numero pagine del libro",
						"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				if (nameSelIm == null) {
					try {

						boolean ris = this.view
								.updateBook(name.getText(), listAuthor
										.get(ComboBoxAuthor.getSelectedItem()),
										null, Integer.parseInt(anno.getText()),
										Integer.parseInt(numPag.getText()),
										model.getId_book());
						if (ris == false) {
							JOptionPane.showMessageDialog(myPanel,
									"update fallito ris ", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(myPanel,
									"update completato ", "information",
									JOptionPane.INFORMATION_MESSAGE);
							this.list = getBooks(user);
							this.list.revalidate();

						}
					} catch (java.lang.NumberFormatException e) {
						JOptionPane
								.showMessageDialog(
										myPanel,
										"Inserimento fallito:numero pagina o anno non valido",
										"Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					try {

						System.out.println(model.getId_book());
						boolean ris = this.view
								.updateBook(name.getText(), listAuthor
										.get(ComboBoxAuthor.getSelectedItem()),
										fileChooser.getSelectedFile(), Integer
												.parseInt(anno.getText()),
										Integer.parseInt(numPag.getText()),
										model.getId_book());
						if (ris == false) {
							JOptionPane.showMessageDialog(myPanel,
									"update fallito ris ", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(myPanel,
									"update completato ", "information",
									JOptionPane.INFORMATION_MESSAGE);

						}
					}

					catch (java.lang.NumberFormatException e2) {
						e2.printStackTrace();
						JOptionPane
								.showMessageDialog(
										myPanel,
										"Inserimento fallito:numero pagina o anno non valido",
										"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

	// ///INSERISCI NUOVO AUTORE
	private void insertAuthorButton(User_model user) {
		this.view = new AdminView();
		JTextField name = new JTextField();
		String nameSelIm;
		JPanel myPanel = new JPanel(new GridLayout(5, 2));
		JButton imageBut = new JButton("Select File");
		final JFileChooser fileChooser = new JFileChooser();
		myPanel.add(new JLabel("nome"));
		myPanel.add(name);
		myPanel.add(new JLabel("image"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files",
				"jpg", "png", "tif"));
		imageBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {

				}
			}
		});
		myPanel.add(imageBut);

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"nouvo libro", JOptionPane.OK_CANCEL_OPTION);
		try {
			nameSelIm = fileChooser.getSelectedFile().getName();

		} catch (java.lang.NullPointerException e1) {
			nameSelIm = null;

		}
		if (result == JOptionPane.OK_OPTION) {
			if (name.getText().equals("")||nameSelIm==null) {
				JOptionPane.showMessageDialog(myPanel, "riempire tutti icampi",
						"Error", JOptionPane.ERROR_MESSAGE);

			} else {
				// boolean
				try {
					boolean ris = this.view.insertAuthor(name.getText(),
							fileChooser.getSelectedFile());
					if (ris == false) {
						JOptionPane.showMessageDialog(myPanel,
								"Inserimento fallito ", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(myPanel,
								"Inserimento completato ", "information",
								JOptionPane.INFORMATION_MESSAGE);
						this.list.removeAll();
						this.list = getBooks(user);
					}
				} catch (java.lang.NumberFormatException e2) {
					e2.printStackTrace();
					JOptionPane
							.showMessageDialog(
									myPanel,
									"Inserimento fallito:numero pagina o anno non valido",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		}

	}

}
