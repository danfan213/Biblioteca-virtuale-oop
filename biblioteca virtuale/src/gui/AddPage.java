package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

import model.User_model;
import view.ExpertView;

///RITORNA IL JPANEL PER INSERIRE UNA NUOVA PAGINA
public class AddPage {
	public void getPanel(User_model user) {
		ExpertView view = new ExpertView();
		String nameSelIm;
		JPanel myPanel = new JPanel(new GridLayout(5, 2));
		myPanel.add(new JLabel("image"));

		JButton imageBut = new JButton("Select File");
		final JFileChooser fileChooser = new JFileChooser();
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
		JComboBox<String> ComboBoxAuthor = new JComboBox<>();
		HashMap<String, Integer> listBook = new HashMap<>();
		listBook = view.listBook();
		@SuppressWarnings("rawtypes")
		Iterator it = listBook.entrySet().iterator();

		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) it.next();
			ComboBoxAuthor.addItem((String) entry.getKey());

		}
		myPanel.add(new JLabel("libro"));
		myPanel.add(ComboBoxAuthor);

		myPanel.add(new JLabel("num pagine"));
		NumberFormatter numPag = new NumberFormatter();
		numPag.setMinimum(new Integer(0));
		numPag.setMaximum(new Integer(10000));
		final JFormattedTextField field = new JFormattedTextField(numPag);
		myPanel.add(field);

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"nouva pagina", JOptionPane.OK_CANCEL_OPTION);
		try {
			nameSelIm = fileChooser.getSelectedFile().getName();

		} catch (java.lang.NullPointerException e1) {
			nameSelIm = null;

		}
		if (result == JOptionPane.OK_OPTION) {

			if (field.getText().equals("") || nameSelIm == null) {
				JOptionPane.showMessageDialog(myPanel,
						"Riempire tutti i campi", "Error",
						JOptionPane.ERROR_MESSAGE);

			} else if (view.checkNumPage(
					listBook.get(ComboBoxAuthor.getSelectedItem()),
					Integer.parseInt(field.getText())) == false) {
				JOptionPane.showMessageDialog(myPanel,
						"numero pagina non valido", "Error",
						JOptionPane.ERROR_MESSAGE);

			}

			else {

				// boolean
				try {
					boolean ris = view.insertNewPage(
							listBook.get(ComboBoxAuthor.getSelectedItem()),
							fileChooser.getSelectedFile(),
							Integer.parseInt(field.getText()), user);
					if (ris == false) {
						JOptionPane.showMessageDialog(myPanel,
								"Inserimento fallito ", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(myPanel,
								"Inserimento completato ", "information",
								JOptionPane.INFORMATION_MESSAGE);

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
