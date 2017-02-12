package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Book_model;
import model.PageBook_model;
import model.Transcript_model;
import model.User_model;
import utility.ReadXMLFile;
import view.AdminView;
import view.EditorPageView;
import view.EditorTextView;
import view.ExpertView;

public class SinglePage {

	private AdminView admin;
	private ExpertView expert;
	private JPanel list=new JPanel();
	JTextArea text=new JTextArea(25,58);
	JScrollPane textPan=new JScrollPane();
	private EditorPageView editorPage;
	private EditorTextView editorText;
	
	
	
	public JPanel EditorTextPanel(final PageBook_model n, final EditorTextView editorText,
			User_model user) throws IOException {
JPanel panel = new JPanel(new BorderLayout());
		
		JPanel contBut=new JPanel(new FlowLayout());
		JButton conf=new JButton("CONFERMA");
		JButton ref=new JButton("RIFIUTA");

		this.text.setEditable(false);
		//JButton nega=new JButton("RIFIUTA TRASCRIZIONE");
		contBut.add(conf);
		contBut.add(ref);
		//contBut.add(nega);
		panel=getPanel(n, editorText, contBut,"view_page",null,null,null);
		
		conf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				acceptTranscription(n.getTranscript(),editorText);
				
			}

			
		});
ref.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				refuseTranscription(n.getTranscript(),editorText);
				
			}
		});
		
		
		this.list=panel;
		return this.list;
	}
	
	////////
	public JPanel EditorPagePanel(final PageBook_model n, final EditorPageView editorPage,
			User_model user) throws IOException {
JPanel panel = new JPanel(new BorderLayout());
		
		JPanel contBut=new JPanel(new FlowLayout());
		JButton conf=new JButton("CONFERMA");
		JButton ref=new JButton("RIFIUTA");

		this.text.setEditable(false);
		contBut.add(conf);
		contBut.add(ref);
		panel=getPanel(n, editorPage, contBut,"add_transcription",null,null,null);
		
		conf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				acceptPage(n,editorPage);
				
			}
		});
ref.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				refusePage(n,editorPage);
				
			}
		});
		
		
		this.list=panel;
		return this.list;		
	}
	
	/// PAGINA PER AGGIUNGERE TRASCRIZIONE
	public JPanel AddTranscriptPanel(final PageBook_model page, final ExpertView userView,final User_model user) throws IOException{
		
		
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel contBut=new JPanel(new FlowLayout());
		JButton conf=new JButton("AGGIUNGI TRASCRIZIONE");
		this.text.setEditable(true);
		contBut.add(conf);
		panel=getPanel(page, userView, contBut,"add_transcription",null,null,null);
		
		conf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addTranscript(page,user);
				
			}
		});
		
		
		this.list=panel;
		return this.list;
		
		
		
	}
	
public <T> JPanel GetPage( PageBook_model page, T viewParam, List<PageBook_model> listPages,List<Book_model> listbooks,JPanel content) throws IOException{
		
		
		this.text.setEditable(false);
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel contBut=new JPanel(new FlowLayout());
		panel=getPanel(page, viewParam, contBut,"view_page",listPages,listbooks,content);
		
		
		
		
		this.list=panel;
		return this.list;
		
		
		
	}
	
	private <T> JPanel getPanel (PageBook_model page, final T user,JPanel contBut,String action, final List<PageBook_model> listPages,final List<Book_model> listBooks,final JPanel content) throws IOException{
		JPanel panel = new JPanel(new BorderLayout());
		JPanel image=new JPanel(new BorderLayout());
		JButton back=new JButton("INDIETRO");
		ImageZoom imagePanel;
		try {
			if(user instanceof AdminView){
				this.admin=new AdminView();
				imagePanel = new ImageZoom(this.admin.getImageSingle(page.getImage()));
				image.add(imagePanel.initialize(),BorderLayout.WEST);
			}
			else if(user instanceof ExpertView){
				this.expert=new ExpertView();
				imagePanel = new ImageZoom(this.expert.getImageSingle(page.getImage()));
				image.add(imagePanel.initialize(),BorderLayout.WEST);	
			}
			else if(user instanceof EditorPageView){
				this.editorPage=new EditorPageView();
				imagePanel = new ImageZoom(this.editorPage.getImageSingle(page.getImage()));
				image.add(imagePanel.initialize(),BorderLayout.WEST);	
			}
			else if(user instanceof EditorTextView){
				this.editorText=new EditorTextView();
				imagePanel = new ImageZoom(this.editorText.getImageSingle(page.getImage()));
				image.add(imagePanel.initialize(),BorderLayout.WEST);	
			}

		} catch (IOException e) {
			
			image.add(new JPanel());
			e.printStackTrace();
		}
		
		JPanel eastpan=new JPanel(new BorderLayout());
		if(action.equals("view_page")){
			ReadXMLFile readXml=new ReadXMLFile(page.getTranscript().getTEI());
			String xml=readXml.parserTei();
			this.text.setText(xml);
			this.text.setLineWrap(true);

		}
		this.textPan.setViewportView(this.text);
		if(listPages!=null){
			panel.add(back,BorderLayout.NORTH);
			panel.add(new JPanel());
		}
		eastpan.add(this.textPan,BorderLayout.CENTER);
		eastpan.add(contBut,BorderLayout.SOUTH);
		panel.add(eastpan,BorderLayout.EAST);
		panel.add(image,BorderLayout.WEST);
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String userGroup="";
				ListSearch backPage=new ListSearch();
				JScrollPane previousPage=new JScrollPane();
				JPanel previousPagePanel=new JPanel();
				if(user instanceof AdminView){
					userGroup="admin_user";
				}
				else if(user instanceof ExpertView){
					userGroup="expert_user";
				}
				else if(user instanceof EditorPageView){
					userGroup="editor_page";
				}
				else if(user instanceof EditorTextView){
					userGroup="editor_text";
				}
				
					previousPagePanel=backPage.getPagesBackButton(userGroup, content, listBooks, listPages);
				
				previousPage.setViewportView(previousPagePanel);
				content.add(previousPage, "previousPage");
				CardLayout layout=(CardLayout) content.getLayout();
				layout.show(content, "previousPage");
				
			}
		});
		return panel;
	}
	
	
	////
	private void addTranscript(PageBook_model page,User_model user){
		this.expert=new ExpertView(user);
		boolean ris=this.expert.InsertNewTrascript(page,text.getText());
		if (ris==false){
			JOptionPane.showMessageDialog(this.list,
					"Inserimento fallito ", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(this.list,
					"Inserimento completato ", "information",
					JOptionPane.INFORMATION_MESSAGE);
			
		}
	}

	private void acceptPage(PageBook_model page,EditorPageView editorPage){
	boolean ris;
	ris=editorPage.acceptPage(page);
	if (ris==false){
		JOptionPane.showMessageDialog(this.list,
				"operazione fallita ", "Error",
				JOptionPane.ERROR_MESSAGE);
	}
	else{
		JOptionPane.showMessageDialog(this.list,
				"Pagina confermata ", "information",
				JOptionPane.INFORMATION_MESSAGE);
			
	}
	}
	
	private void refusePage(PageBook_model page,EditorPageView editorPage){
	boolean ris;
	ris=editorPage.refusePage(page);
	if (ris==false){
		JOptionPane.showMessageDialog(this.list,
				"operazione fallita ", "Error",
				JOptionPane.ERROR_MESSAGE);
	}
	else{
		JOptionPane.showMessageDialog(this.list,
				"Pagina rifiutata ", "information",
				JOptionPane.INFORMATION_MESSAGE);
		
	}
	}

	private void acceptTranscription(Transcript_model transcript_model,
			EditorTextView editorText) {
		boolean ris;
		ris=editorText.acceptPage(transcript_model);
		if (ris==false){
			JOptionPane.showMessageDialog(this.list,
					"operazione fallita ", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(this.list,
					"Trascrizione confermata ", "information",
					JOptionPane.INFORMATION_MESSAGE);
				
		}		
	}
	private void refuseTranscription(Transcript_model transcript_model,
			EditorTextView editorText) {
		boolean ris;
		ris=editorText.refusePage(transcript_model);
		if (ris==false){
			JOptionPane.showMessageDialog(this.list,
					"operazione fallita ", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(this.list,
					"Trascrizione rifiutata ", "information",
					JOptionPane.INFORMATION_MESSAGE);
				
		}		
	}
	
}
