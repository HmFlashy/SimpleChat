import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;

import client.ChatClient;
import common.ChatIF;

@SuppressWarnings("serial")
public class ClientGraphics extends JFrame implements ChatIF {

	/**
	   * The default port to connect on.
	   */
	  final public static int DEFAULT_PORT = 5555;
	  
	  //Instance variables **********************************************
	  
	  /**
	   * The instance of the client that created this ConsoleChat.
	   */
	  ChatClient client;
	  
	  /**
	   * Graphics component
	   */
	  FlowLayout layout;
	  JTextArea writingarea;
	  JTextPane textarea;
	  
	
	public ClientGraphics(String host, int port, String id) {
		try 
	    {
	      client= new ChatClient(host, port, this, id);
	    } 
	    catch(IOException exception) 
	    {
	      System.out.println("Error: Can't setup connection!"
	                + " Terminating client.");
	      System.exit(1);
	    }
		this.configWindow();
		this.initComponents();
		this.setVisible(true);
	}
	
	private void configWindow() {
		this.setSize(500, 500);
		this.setTitle("SimpleChat");
	}
	
	private void initComponents() {
		layout = new FlowLayout();
		writingarea = new JTextArea(1, 20);
		textarea = new JTextPane();
		writingarea.setBorder(BorderFactory.createLineBorder(Color.black));
		writingarea.setFocusable(true);
		this.setLayout(layout);
		this.add(textarea);
		this.add(writingarea);
	}
	  
	public static void main(String[] args) {
		String host = "";
	    int port = 0;  //The port number

	    try
	    {
	      host = args[1];
	      port =  Integer.parseInt(args[2]);
	    }
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	      host = "localhost";
	      port = DEFAULT_PORT;
	    }
	    ClientGraphics chat= new ClientGraphics(host, port, args[0]);
	}

	@Override
	public void display(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
	}


}
