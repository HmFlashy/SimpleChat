package component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ActionWritingArea extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ActionListener listener;
	private KeyListener keyListener;
	private JTextArea writingarea;
	private JButton sendButton;
	
	public ActionWritingArea(ActionListener listener, KeyListener keyListener) {
		super(new BorderLayout());
		this.listener = listener;
		this.keyListener = keyListener;
		writingarea = new JTextArea(1, 34);
		writingarea.setBorder(BorderFactory.createLineBorder(Color.black));
		sendButton = new JButton("Envoyer");
		sendButton.addActionListener(this.listener);
		writingarea.addKeyListener(this.keyListener);
		this.add(writingarea, BorderLayout.CENTER);
		this.add(sendButton, BorderLayout.EAST);
	}

	public String getText() {
		return writingarea.getText();
	}
	

	public void setText(String text) {
		writingarea.setText(text);
	}

	public void setColor(Color color) {
		// TODO Auto-generated method stub
		this.writingarea.setBackground(color);
	}

}
