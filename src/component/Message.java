package component;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class Message extends JLabel implements MouseListener {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public Message(String message) {
			super(message);
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			this.addMouseListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			this.setForeground(Color.RED);			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

			this.setForeground(Color.BLACK);
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
}
