package window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import client.ChatClient;
import common.ChatIF;
import component.ClientToolBar;
import component.DisplayChatArea;

public class ClientGraphics extends JFrame implements ChatIF, ActionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	   * The default port to connect on.
	   */
	  final public static int DEFAULT_PORT = 5555;
	  
	  //Instance variables **********************************************
	  
	  /**
	   * The instance of the client that created this ConsoleChat.
	   */
	  ChatClient client;
	  int windowHeight, windowWidth;
	  
	  private JTextArea writingarea;
	  private DisplayChatArea displayChatArea;
	  private JPanel displayArea, actionArea;
	  private JButton sendButton;
	  private ClientToolBar toolsBar;
	  
	
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
		Toolkit tk = Toolkit.getDefaultToolkit(); 
		Dimension d = tk.getScreenSize(); 
		int hauteurEcran = d.height; 
		int largeurEcran = d.width; 
		windowHeight = hauteurEcran/2;
		windowWidth = largeurEcran/2;
		setSize(windowWidth, windowHeight); 
		this.setTitle("SimpleChat");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initComponents() {
		toolsBar = new ClientToolBar(this);
		actionArea = new JPanel(new BorderLayout());
		displayChatArea = new DisplayChatArea(this);
		writingarea = new JTextArea(1, 34);
		writingarea.setBorder(BorderFactory.createLineBorder(Color.black));
		sendButton = new JButton("Envoyer");
		sendButton.addActionListener(this);
		writingarea.addKeyListener(this);
		actionArea.add(writingarea, BorderLayout.CENTER);
		actionArea.add(sendButton, BorderLayout.EAST);
		this.add(toolsBar, BorderLayout.NORTH);
		this.add(displayChatArea, BorderLayout.CENTER);
		this.add(actionArea, BorderLayout.SOUTH);
		writingarea.requestFocusInWindow();
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
			JLabel msg = new JLabel("<html><p>"+ message+"</p></html");
			msg.setMaximumSize(new Dimension(windowWidth, 15));
			msg.setBorder(BorderFactory.createLineBorder(Color.black));
			displayChatArea.addMessage(msg);
	}
	
	public void handleMessageFromWritingArea() {
		String message = this.writingarea.getText();
		if(message.endsWith("\n")) {
			message = message.substring(0, message.length()-1);
		}
		if(!message.equals("")) {
			client.handleMessageFromClientUI(message);
			this.writingarea.setText("");
			writingarea.setBackground(Color.white);
		} else {
			writingarea.setBackground(Color.red);
		}
		this.writingarea.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String commande = arg0.getActionCommand();
		switch(commande) {
			case ClientToolBar.CONNECTION:
				JTextArea aPseudo = toolsBar.getPseudoArea();
				String pseudo = aPseudo.getText();
				client.handleMessageFromClientUI("#login " + pseudo);
				aPseudo.setText("");
				break;
			case ClientToolBar.DISCONNECTION:
				client.handleMessageFromClientUI("#logoff");
				break;
			case ClientToolBar.GETPORT:
				client.handleMessageFromClientUI("#getport");
				break;
			case ClientToolBar.SETPORT:
				String s = (String)JOptionPane.showInputDialog(
	                    this,
	                    "Port :",
	                    "Changer de port",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    String.valueOf(client.getPort())
						);
				if(s != null) {
					client.handleMessageFromClientUI("#setport " + s);
				}
				break;
			case ClientToolBar.GETHOST:
				client.handleMessageFromClientUI("#gethost");
				break;
			default:
				this.handleMessageFromWritingArea();
				break;
		}
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

	@Override
	public void handleCode(int code) {
		// TODO Auto-generated method stub
		switch(code) {
			case 1:
				this.setTitle("SimpleChat (Connecté)");
				this.toolsBar.connected();
				break;
			case 2:
				this.setTitle("SimpleChat (Déconnecté)");
				this.toolsBar.disconnected();
				break;
		}
	}


}
