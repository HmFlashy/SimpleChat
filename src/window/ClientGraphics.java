package window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import client.ChatClient;
import common.ChatIF;
import component.ActionWritingArea;
import component.ClientToolBar;
import component.DisplayChatArea;
import component.Message;

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
	  
	  private DisplayChatArea displayChatArea;
	  private ActionWritingArea actionArea;
	  private ClientToolBar toolsBar;
	  
	/**
	 * 
	 * Constructor of the ClientGraphics class
	 * 
	 * @param host
	 * @param port
	 */
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
	
	/**
	 * Set the default parameters for the window
	 */
	private void configWindow() {
		Toolkit tk = Toolkit.getDefaultToolkit(); 
		Dimension d = tk.getScreenSize(); 
		int hauteurEcran = d.height; 
		int largeurEcran = d.width; 
		windowHeight = hauteurEcran/2;
		windowWidth = largeurEcran/2;
		setSize(windowWidth, windowHeight); 
		this.setTitle("SimpleChat");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initComponents() {
		toolsBar = new ClientToolBar(this);
		actionArea = new ActionWritingArea(this, this);
		displayChatArea = new DisplayChatArea(this);
		
		this.add(toolsBar, BorderLayout.NORTH);
		this.add(displayChatArea, BorderLayout.CENTER);
		this.add(actionArea, BorderLayout.SOUTH);
	}
	
	/**
	 * 
	 * @param args
	 */
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
	    chat.display("Bienvenue sur SimpleChat4 ! \nPour vous connecter rentrez votre pseudo et c'est parti !");
	}
	
	@Override
	public void display(String message) {
		// TODO Auto-generated method stub
			Message msg = new Message("<html><p>"+ message+"</p></html");
			displayChatArea.addMessage(msg);
	}
	
	/**
	 * Handle the message from the writing area
	 */
	public void handleMessageFromWritingArea() {
		String message = this.actionArea.getText();
		if(message.endsWith("\n")) {
			message = message.substring(0, message.length()-1);
		}
		if(!message.equals("")) {
			client.handleMessageFromClientUI(message);
			this.actionArea.setText("");
			this.actionArea.setColor(Color.white);
		} else {
			this.actionArea.setColor(Color.red);
		}
		this.actionArea.setText("");
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
			case ClientToolBar.SETHOST:
				String hote = (String)JOptionPane.showInputDialog(
	                    this,
	                    "Hôte :",
	                    "Changer d'hôte",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    client.getHote()
						);
				if(hote != null) {
					client.handleMessageFromClientUI("#sethost " + hote);
				}
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
