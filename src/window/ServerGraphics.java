package window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;

import common.ChatIF;
import component.DisplayChatArea;
import component.ServerToolBar;
import server.EchoServer;

public class ServerGraphics extends JFrame implements ChatIF, ActionListener {

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
	
	public ServerGraphics(int port) {
		es = new EchoServer(port, this);
		try {
			es.getObs().listen();
		} catch (IOException e) {
		    System.out.println("ERROR - Could not listen for clients!");
		}
	}
	
	public void listen(){
		try {
			es.getObs().listen();
		} catch (IOException e) {
		    System.out.println("ERROR - Could not listen for clients!");
		}
	}

	@Override
	public void display(String message) {
		// TODO Auto-generated method stub
		JLabel msg = new JLabel("<html><p>"+ message+"</p></html");
		msg.setMaximumSize(new Dimension(windowWidth, 15));
		msg.setBorder(BorderFactory.createLineBorder(Color.black));
		this.displayChatArea.addMessage(msg);
	}

	@Override
	public void handleCode(int code) {
		// TODO Auto-generated method stub

	}
	
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
	}

	private void initComponents() {
		// TODO Auto-generated method stub
		this.toolBar = new ServerToolBar(this);
		this.displayChatArea = new DisplayChatArea(this);
		this.add(toolBar, BorderLayout.NORTH);
		this.add(displayChatArea, BorderLayout.CENTER);
		
	}

	private void initWindow() {
		// TODO Auto-generated method stub
		Toolkit tk = Toolkit.getDefaultToolkit(); 
		Dimension d = tk.getScreenSize(); 
		int hauteurEcran = d.height; 
		int largeurEcran = d.width; 
		windowHeight = hauteurEcran/2;
		windowWidth = largeurEcran/2;
		setSize(windowWidth, windowHeight); 
		this.setTitle("SimpleChat Server (Online)");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String commande = arg0.getActionCommand();
		System.out.println(commande);
		switch(commande) {
			case ServerToolBar.START:
				es.handleMessageFromAdmin("#start");
				this.display("Server starting");
				break;
			case ServerToolBar.STOP:
				es.handleMessageFromAdmin("#stop");
				this.display("Server stoping");
				break;
		}
		
	}

}
