package basic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import plotter.Graphic;
import jserver.Board;
import jserver.XSendAdapter;


public class Spiel implements KeyListener {
	
	private Board board = new Board();
	private XSendAdapter xsend = new XSendAdapter(board);
	private int size = 4;
	private Graphic graphic;
	
	

	public static void main(String[] args) {
		(new Spiel()).start();
	
	}

	private void start() {
		setUpBoard();
	}

	private void setUpBoard() {
		board.getPlotter().addKeyListener(this);
		board.getPlotter().setFocusable(true);
		board.getPlotter().requestFocusInWindow();
		
		xsend.flaeche(XSendAdapter.WHITE);
		xsend.groesse(size, size);
		xsend.formen("s");
		
		setUpBoardGui();
	}
	

	public void setUpBoardGui() {
		graphic = board.getGraphic();
		xsend = new XSendAdapter(board);
		JButton mordsButton = new JButton("Kennst du Mort?");
		graphic.addSouthComponent(mordsButton);
		mordsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				xsend.statusText("Meinen mords Cock?");	
				board.getPlotter().requestFocusInWindow();
			}
		});
	}


	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}


