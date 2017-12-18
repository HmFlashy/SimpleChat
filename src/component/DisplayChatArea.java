package component;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class DisplayChatArea extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame window;
	private JPanel displayArea;
	
	public DisplayChatArea(JFrame window) {
		this.window = window;
		displayArea = new JPanel(new GridLayout(0,1, 0, 2));
		this.setViewportView(displayArea);
		this.setMaximumSize(new Dimension(500, 450));
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}

	public void addMessage(JLabel msg) {
		this.displayArea.add(msg);
		this.validate();
		JScrollBar vertical = this.getVerticalScrollBar();
		vertical.setValue( vertical.getMaximum() );
	}
}
