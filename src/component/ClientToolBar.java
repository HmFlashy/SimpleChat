package component;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import window.ClientGraphics;

public class ClientToolBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextArea credentialTextArea, hostTextArea, portTextArea;
	private JLabel pseudo;
	private JButton disconnection, connection, getPort, getHost;
	private ClientGraphics frame;
	
	public static final String CONNECTION = "connection";
	public static final String DISCONNECTION = "disconnection";
	public static final String GETPORT = "getport";
	public static final String GETHOST = "gethost";
	public static final String SETPORT = "setport";
	public static final String SETHOST = "sethost";
	
	public ClientToolBar(ClientGraphics frame) {
		super(new GridLayout(1, 0));
		this.frame = frame;
		this.pseudo = new JLabel("Pseudo :");
		this.credentialTextArea = new JTextArea();
		this.credentialTextArea.getDocument().putProperty("filterNewlines", Boolean.TRUE);
		
		this.connection = new JButton("Connexion");
		this.connection.setActionCommand(ClientToolBar.CONNECTION);
		this.connection.addActionListener(this.frame);
		
		this.disconnection = new JButton("X");
		this.disconnection.setActionCommand(ClientToolBar.DISCONNECTION);
		this.disconnection.addActionListener(this.frame);
		
		this.getPort = new JButton("Port");
		this.getPort.setActionCommand(ClientToolBar.GETPORT);
		this.getPort.addActionListener(this.frame);
		
		this.getHost = new JButton("Host");
		this.getHost.setActionCommand(ClientToolBar.GETHOST);
		this.getHost.addActionListener(this.frame);
		
		this.add(this.pseudo);
		this.add(this.credentialTextArea);
		this.add(this.connection);
		this.add(this.getPort);
		this.add(this.getHost);
	}
	
	public JTextArea getPseudoArea() {
		return this.credentialTextArea;
	}

	public void connected() {
		this.remove(this.pseudo);
		this.remove(this.credentialTextArea);
		this.remove(this.connection);
		this.add(this.disconnection, 0);
		this.repaint();
		this.revalidate();
	}
	
	public void disconnected() {
		this.remove(this.disconnection);
		this.add(this.pseudo, 0);
		this.add(this.credentialTextArea, 1);
		this.add(this.connection, 2);
		this.repaint();
		this.revalidate();
	}

}
