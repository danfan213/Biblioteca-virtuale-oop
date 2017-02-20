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
import view.AdminView;

public class HomeAdmin {
	private JFrame window = new JFrame("HOME");
	private JPanel panel = new JPanel(new BorderLayout());
	private JPanel permessiPanel = new JPanel();
	private JPanel userListPanel = new JPanel();
	private JPanel listBooksPanel = new JPanel();
	private JPanel listAllBooksPanel = new JPanel();
	private PermessiList list = new PermessiList();
	private UserList Ulist = new UserList();
	private BookManager bookManager=new BookManager();
	private ListBooks listBook=new ListBooks();
	private ListSearch lsearch = new ListSearch();
	private JPanel content = new JPanel();
	private JPanel lastPagesPanel = new JPanel();
	private JScrollPane lastPages = new JScrollPane();
	private JScrollPane userList = new JScrollPane();
	private JScrollPane permessi = new JScrollPane();
	private JScrollPane listBooks = new JScrollPane();
	private JScrollPane listAllBooks = new JScrollPane();
	private JPanel menu = new JPanel(new FlowLayout());
	private JPanel searchPanel = new JPanel();
	private JTextField searchField = new JTextField();
	private JButton back=new JButton();
	private JButton searchBut = new JButton("SEARCH");
	private JButton permessiBut = new JButton("PERMESSI");
	private JButton visual = new JButton("ULTIME PAGINE");
	private JButton delete_user = new JButton("ELIMINA UTENTI");
	private JButton logout = new JButton("LOGOUT");
	private JButton listaLibri = new JButton("LISTA LIBRI");
	private AdminView view=new AdminView();
	
	
	public void getHome(final User_model user) throws IOException {
		//this.permessi = this.list.getList();
		//this.userList = this.Ulist.getUserList(user);
		
		
		
		
		JPanel first = new JPanel();

		//test.add(testl);
		final CardLayout layout = new CardLayout();
		this.content.setLayout(layout);
		this.content.add(first);
		this.menu.add(permessiBut);
		this.menu.add(listaLibri);
		this.menu.add(visual);
		this.menu.add(delete_user);
		this.menu.add(logout);
		this.searchPanel.add(searchField);
		this.searchField.setPreferredSize(new Dimension(250, 30));
		this.searchPanel.add(searchBut);
		this.menu.add(searchPanel, BorderLayout.NORTH);
		this.panel.add(menu, BorderLayout.NORTH);
		this.panel.add(content);
		this.window.add(panel);
		 this.window.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.window.pack();
		this.content.setVisible(true);
		this.window.setVisible(true);

		this.permessiBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listBooksPanel.removeAll();
				listAllBooksPanel.removeAll();
				userListPanel.removeAll();
				permessiPanel.removeAll();
				lastPagesPanel.removeAll();
				permessiPanel = list.getList();
				permessi.setViewportView(permessiPanel);
				content.add(permessi, "permessi");

				// content.add(permessi,"permessi");
				layout.show(content, "permessi");

			}
		});
		this.logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.logout(window);

			}
		});
		this.delete_user.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listBooksPanel.removeAll();
				listAllBooksPanel.removeAll();
				userListPanel.removeAll();
				permessiPanel.removeAll();
				lastPagesPanel.removeAll();

				userListPanel = Ulist.getUserList(user);
				// content.add(permessi,"permessi");
				userList.setViewportView(userListPanel);

				content.add(userList, "delete_user");
				layout.show(content, "delete_user");

			}
		});
		this.visual.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				lastPagesPanel.removeAll();
				listBooksPanel.removeAll();
				listAllBooksPanel.removeAll();
				userListPanel.removeAll();
				permessiPanel.removeAll();
				

				try {
					lastPagesPanel = lsearch.getPages(view,content,user);
					lastPages.setViewportView(lastPagesPanel);
					content.add(lastPages, "lastPages");

					layout.show(content, "lastPages");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				

			}
		});
		
		searchBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listBooksPanel.removeAll();
				listAllBooksPanel.removeAll();
				userListPanel.removeAll();
				permessiPanel.removeAll();
				lastPagesPanel.removeAll();
				listBooksPanel = listBook.getBooks(searchField.getText(),user,content);
	
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
				userListPanel.removeAll();
				permessiPanel.removeAll();
				lastPagesPanel.removeAll();
				listAllBooksPanel = bookManager.getBooks(user);

				listAllBooks.setViewportView(listAllBooksPanel);

				content.add(listAllBooks, "list_Allbook");
				layout.show(content, "list_Allbook");	
				
			}
		});
		
	}
}	

