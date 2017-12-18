package component;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ServerToolBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String START = "start";
	public static final String STOP = "stop";
	public static final String GETPORT = "getport";
	public static final String SETPORT = "setport";
	public static final String CLOSE = "close";
	
	private ActionListener windowServer;
	private JButton stopServer, startServer, getPort, setPort, close;
	private JPanel portPanel;
	
	public ServerToolBar(ActionListener windowServer) {
		super(new GridLayout(1,0));
		this.windowServer = windowServer;
		stopServer = new JButton("Stop");
		stopServer.addActionListener(this.windowServer);
		stopServer.setActionCommand(STOP);
		startServer = new JButton("Start");
		startServer.addActionListener(this.windowServer);
		startServer.setActionCommand(START);
		close = new JButton("Fermer");
		close.addActionListener(this.windowServer);
		close.setActionCommand(CLOSE);
		
		
		portPanel = new JPanel(new GridLayout(1, 0));
		ImageIcon modifyIcon = new ImageIcon(new ImageIcon("../assets/modify.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		
		this.getPort = new JButton("Port");
		this.getPort.setActionCommand(ServerToolBar.GETPORT);
		this.getPort.addActionListener(this.windowServer);
		this.setPort = new JButton(modifyIcon);
		this.setPort.setActionCommand(ServerToolBar.SETPORT);
		this.setPort.addActionListener(this.windowServer);
		
		portPanel.add(this.getPort);
		portPanel.add(this.setPort);
		
		this.add(startServer);
		this.add(stopServer);
		this.add(close);
		this.add(portPanel);
	}

}
