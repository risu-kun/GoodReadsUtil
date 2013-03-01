package gr;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Fetcher 
{
	String key;
	int limit = 10;
	String url;
	SAXBuilder builder;
	Document doc;
	
	public Fetcher()
	{
		this.key = "U12KOBpkFJCtlENbwdQA";
		this.limit = 10;
		builder = new SAXBuilder();
	}
	
	public String[] fetchTitles(String attr) throws JDOMException, IOException
	{
		url = "http://www.goodreads.com/search.xml?key=" + key + "&q=" + URLEncoder.encode(attr, "UTF-8");
		doc = builder.build(url);
		Element response = doc.getRootElement();
		String[] list = null;
					
		if (!response.getChild("search").getChildText("total-results").equals("0"))
		{				
			List<Element> allBooks =  response.getChild("search").getChild("results").getChildren("work");
			int count = 0;
			for (Element book : allBooks)
				count++;
			list = new String[count];
			
			int i = 0;
			
			for (Element book : allBooks)
			{
				list[i] = book.getChild("best_book").getChildText("title");		
				i++;
			}
		}
		
		else
		{
			list  = new String[1];
			list[0] = "No results found.";
		}
		return list;			
	}
	
	public String fetchInfo(String title) throws JDOMException, IOException
	{
		String info = "No description found.";
		if (!title.equals("No results found."))
		{
			url = "http://www.goodreads.com/book/title?format=xml&key=" + key + "&title=" + URLEncoder.encode(title, "UTF-8");
			doc = builder.build(url);
			if (isValid(doc.getRootElement()))
			{
				Element book = doc.getRootElement().getChild("book");
							
				info =  "<em>" + book.getChildText("title") + "</em> by " + book.getChild("authors").getChild("author").getChildText("name") +
						"<br>ISBN: " + book.getChildText("isbn13") +
						"<br>Avg. Rating: " + book.getChildText("average_rating") + " (" + book.getChildText("ratings_count") + " ratings)" +
						"<br>Description: " + book.getChildText("description");
			}
		}
		
		return info;		
	}
	
	private boolean isValid(Element element)
	{
		if (!element.getName().equals("error"))
			return true;
		else
			return false;
	}

}

