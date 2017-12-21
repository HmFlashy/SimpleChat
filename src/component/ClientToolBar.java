package component;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
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
	
	private JTextArea credentialTextArea, hostTextArea;
	private JLabel pseudo;
	private JButton disconnection, connection, getPort, getHost, setPort, setHost;
	private ActionListener listener;
	
	public static final String CONNECTION = "connection";
	public static final String DISCONNECTION = "disconnection";
	public static final String GETPORT = "getport";
	public static final String GETHOST = "gethost";
	public static final String SETPORT = "setport";
	public static final String SETHOST = "sethost";
	
	public ClientToolBar(ActionListener listener) {
		super(new GridLayout(1, 0));
		this.listener = listener;
		this.pseudo = new JLabel("Pseudo :");
		this.credentialTextArea = new JTextArea();
		this.credentialTextArea.getDocument().putProperty("filterNewlines", Boolean.TRUE);
		
		this.connection = new JButton("Connexion");
		this.connection.setActionCommand(ClientToolBar.CONNECTION);
		this.connection.addActionListener(this.listener);
		
		this.disconnection = new JButton("X");
		this.disconnection.setActionCommand(ClientToolBar.DISCONNECTION);
		this.disconnection.addActionListener(this.listener);
		
		this.getPort = new JButton("Port");
		this.getPort.setActionCommand(ClientToolBar.GETPORT);
		this.getPort.addActionListener(this.listener);
		
		this.getHost = new JButton("Hôte");
		this.getHost.setActionCommand(ClientToolBar.GETHOST);
		this.getHost.addActionListener(this.listener);
		
		JPanel port = new JPanel(new GridLayout(1, 0));
		JPanel hote = new JPanel(new GridLayout(1, 0));
		
		ImageIcon modifyIcon = new ImageIcon(new ImageIcon("../assets/modify.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		
		this.setPort = new JButton(modifyIcon);
		this.setPort.setActionCommand(ClientToolBar.SETPORT);
		this.setPort.addActionListener(this.listener);
		
		this.setHost = new JButton(modifyIcon);
		this.setHost.setActionCommand(ClientToolBar.SETHOST);
		this.setHost.addActionListener(this.listener);
		port.add(this.getPort);
		port.add(this.setPort);
		hote.add(this.getHost);
		hote.add(this.setHost);
		
		this.add(this.pseudo);
		this.add(this.credentialTextArea);
		this.add(this.connection);
		this.add(port);
		this.add(hote);
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
