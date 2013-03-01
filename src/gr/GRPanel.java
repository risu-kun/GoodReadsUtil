package gr;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.jdom2.JDOMException;

public class GRPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	JLabel inputLabel;
	JTextField inputText;
	JList<String> titleList;
	JEditorPane titleInfo;
	JButton search, showInfo, goToPage;
	JScrollPane scrollPaneTitles, scrollPaneInfo;
	Fetcher fetcher;
	
	public GRPanel()
	{
		fetcher = new Fetcher();
		inputLabel = new JLabel("Type title, author, ISBN, or keyword: ");
		search = new JButton("Search");
		inputText = new JTextField(20);
		titleList = new JList<String>();
		scrollPaneTitles = new JScrollPane(titleList);
		showInfo = new JButton("Show Info");
		titleInfo = new JEditorPane();
		scrollPaneInfo = new JScrollPane(titleInfo);
		goToPage = new JButton("Go to GoodReads Page");
		
		setPreferredSize(new Dimension(360, 600));
		titleInfo.setContentType("text/html");
		titleInfo.setEditable(false);
		titleInfo.setPreferredSize(new Dimension(300,300));
		
		
		add(inputLabel);
		add(inputText);
		add(search);
		add(scrollPaneTitles);
		add(showInfo);
		add(scrollPaneInfo);			
		add(goToPage);
		
		
		
		inputText.addActionListener(new SearchListener());
		search.addActionListener(new SearchListener());
		goToPage.addActionListener(new GoToPageListener());
		showInfo.addActionListener(new ShowInfoListener());
		
		
	}
	
	private class SearchListener implements ActionListener
	{

		public void actionPerformed(ActionEvent event) 
		{
			try {
				titleList = new JList<String>(fetcher.fetchTitles(inputText.getText()));
				scrollPaneTitles.getViewport().setView(titleList);
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}		
	}
	
	
	private class ShowInfoListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent event) 
		{
			try {
				titleInfo.setText("<html>" + fetcher.fetchInfo(titleList.getSelectedValue()) + "</html>");
				scrollPaneInfo.getViewport().setView(titleInfo);
			} catch (JDOMException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private class GoToPageListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent event) 
		{
			Desktop desktop = Desktop.getDesktop();
			
			if(desktop.isSupported( java.awt.Desktop.Action.BROWSE ) ) 
			{
				String title = "null";
				try {
					title = URLEncoder.encode(titleList.getSelectedValue(), "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				
				String url = "http://www.goodreads.com/book/title/" + title;
				try {
					desktop.browse(new URI(url));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
	            
	        }
		}
		
	}
	
}
