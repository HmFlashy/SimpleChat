package window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import common.ChatIF;
import component.ActionWritingArea;
import component.DisplayChatArea;
import component.Message;
import component.ServerToolBar;
import server.EchoServer;

public class ServerGraphics extends JFrame implements ChatIF, ActionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 /**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 6000;
  
	EchoServer es;

	private int windowHeight;

	private int windowWidth;
	
	private ServerToolBar toolBar;

	private DisplayChatArea displayChatArea;

	private ActionWritingArea actionArea;
	
	public ServerGraphics(int port) {
		es = new EchoServer(port, this);
	}

	@Override
	public void display(String message) {
		// TODO Auto-generated method stub
		Message msg = new Message("<html><p>"+ message+"</p></html");
		this.displayChatArea.addMessage(msg);
	}

	@Override
	public void handleCode(int code) {
		switch(code) {
			case 1:
				this.setTitle("SimpleChat (Running)");
				this.toolBar.started();
				break;
			case 2:
				this.setTitle("SimpleChat (Offline)");
				this.toolBar.stopped();
				break;
			case 3:
				this.setTitle("SimpleChat (Stop)");
				this.toolBar.closed();
				break;
		}
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		int port = 0; //Port to listen on

	    try
	    {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555
	    }
		
	    ServerGraphics sc = new ServerGraphics(port);
	    sc.initWindow();
	    sc.initComponents();
	    sc.setVisible(true);
	    sc.display("Bienvenue sur SimpleChat4 ! \nPour lancer le serveur, appuyer sur Start.");
	}

	/**
	 * 
	 */
	private void initComponents() {
		// TODO Auto-generated method stub
		this.toolBar = new ServerToolBar(this);
		this.displayChatArea = new DisplayChatArea(this);
		this.actionArea = new ActionWritingArea(this, this);
		this.add(toolBar, BorderLayout.NORTH);
		this.add(displayChatArea, BorderLayout.CENTER);
		this.add(actionArea, BorderLayout.SOUTH);
		
	}

	/**
	 * 
	 */
	private void initWindow() {
		// TODO Auto-generated method stub
		Toolkit tk = Toolkit.getDefaultToolkit(); 
		Dimension d = tk.getScreenSize(); 
		int hauteurEcran = d.height; 
		int largeurEcran = d.width; 
		windowHeight = hauteurEcran/2;
		windowWidth = largeurEcran/2;
		this.setSize(windowWidth, windowHeight); 
		this.setTitle("SimpleChat Server (Online)");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String commande = arg0.getActionCommand();
		switch(commande) {
			case ServerToolBar.START:
				es.handleMessageFromAdmin("#start");
				this.display("Server starting");
				break;
			case ServerToolBar.STOP:
				es.handleMessageFromAdmin("#stop");
				this.display("Server stoping");
				break;
			case ServerToolBar.SETPORT:
				String s = (String)JOptionPane.showInputDialog(
	                    this,
	                    "Port :",
	                    "Changer de port",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    String.valueOf(es.getPort())
						);
				if(s != null) {
					es.handleMessageFromAdmin("#setport " + s);
				}
				break;
			case ServerToolBar.GETPORT:
				es.handleMessageFromAdmin("#getport");
				break;
			case ServerToolBar.CLOSE:
				es.handleMessageFromAdmin("#close");
				break;
		}
		
	}
	
	/**
	 * Handle message from writing area
	 */
	public void handleMessageFromWritingArea() {
		String message = this.actionArea.getText();
		if(message.endsWith("\n")) {
			message = message.substring(0, message.length()-1);
		}
		if(!message.equals("")) {
			es.handleMessageFromAdmin(message);
			this.actionArea.setText("");
			this.actionArea.setColor(Color.white);
		} else {
			this.actionArea.setColor(Color.red);
		}
		this.actionArea.setText("");
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
