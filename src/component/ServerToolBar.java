package component;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ServerToolBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String START = "start";
	public static final String STOP = "stop";
	
	private ActionListener windowServer;
	private JButton stopServer, startServer, getPort;
	
	public ServerToolBar(ActionListener windowServer) {
		super(new GridLayout(1,0));
		this.windowServer = windowServer;
		stopServer = new JButton("Stop");
		stopServer.addActionListener(this.windowServer);
		stopServer.setActionCommand(STOP);
		startServer = new JButton("Start");
		startServer.addActionListener(this.windowServer);
		startServer.setActionCommand(START);
		this.add(startServer);
		this.add(stopServer);
	}

}
