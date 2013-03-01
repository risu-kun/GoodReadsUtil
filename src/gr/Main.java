package gr;

import java.io.IOException;
import javax.swing.JFrame;
import org.jdom2.JDOMException;




public class Main 
{
	public static void main(String args[]) throws JDOMException, IOException 
	{
		 JFrame frame = new JFrame("GoodReads Search");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	       GRPanel panel = new GRPanel();
	       frame.getContentPane().add(panel);

	       frame.pack();
	       frame.setVisible(true);
	}	

}
