import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.ChatClient;
import common.ChatIF;

@SuppressWarnings("serial")
public class ClientGraphics extends JFrame implements ChatIF, ActionListener, KeyListener {

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
	  JPanel displayArea, actionArea, messageArea;
	  JTextField textarea;
	  JButton sendButton;
	  
	
	public ClientGraphics(String host, int port) {
		try 
	    {
	      client= new ChatClient(host, port, this);
	    } 
	    catch(IOException exception) 
	    {
	      System.out.println("Error: Can't setup connection!"
	                + " Terminating client.");
	      System.exit(1);
	    }
	}
	
	private void configWindow() {
		this.setSize(500, 500);
		this.setTitle("SimpleChat");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initComponents() {
		displayArea = new JPanel();
		messageArea = new JPanel(new GridLayout(0, 1));
		actionArea = new JPanel(new BorderLayout());
		JScrollPane scroll = new JScrollPane(messageArea);
		displayArea.setMaximumSize(new Dimension(500, 400));
		JScrollBar scrollbar = scroll.getVerticalScrollBar();
		displayArea.add(messageArea, BorderLayout.WEST);
		displayArea.add(scrollbar, BorderLayout.EAST);
		this.add(scroll);
		writingarea = new JTextArea(1, 34);
		writingarea.setBorder(BorderFactory.createLineBorder(Color.black));
		sendButton = new JButton("Envoyer");
		sendButton.addActionListener(this);
		writingarea.addKeyListener(this);
		this.add(displayArea, BorderLayout.NORTH);
		actionArea.add(writingarea, BorderLayout.WEST);
		actionArea.add(sendButton, BorderLayout.EAST);
		this.add(actionArea, BorderLayout.SOUTH);
	}
	  
	public static void main(String[] args) {
		String host = "";
	    int port = 0;  //The port number

	    try
	    {
	      host = args[0];
	      port =  Integer.parseInt(args[1]);
	    }
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	      host = "localhost";
	      port = DEFAULT_PORT;
	    }
	    ClientGraphics chat= new ClientGraphics(host, port);
		chat.configWindow();
		chat.initComponents();
		chat.setVisible(true);
	}

	@Override
	public void display(String message) {
		// TODO Auto-generated method stub
		JLabel msg = new JLabel(message);
		msg.setBorder(BorderFactory.createLineBorder(Color.black));
		messageArea.add(msg, BorderLayout.SOUTH);
		System.out.println(displayArea.getHeight());
		this.validate();
	}
	
	public void handleMessageFromWritingArea() {
		String message = this.writingarea.getText();
		client.handleMessageFromClientUI(message);
		this.writingarea.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		this.handleMessageFromWritingArea();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyChar() == KeyEvent.VK_ENTER) {
			this.handleMessageFromWritingArea();
		}
	}


}
