

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class program extends JPanel implements ActionListener, KeyListener {

	Timer tm = new Timer(5,this);
	int x=0, velX=0, y=300;
	boolean readyToFire, shot=false;
	int bulletx, bullety;
	
	Rectangle bullet;
	
	public program() {
		tm.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//coloring and filling the component
		g.fillRect(x, y, 50, 30);
		Image img = Toolkit.getDefaultToolkit().getImage("src/images/pngwing.com_1_50x30.png");
		g.drawImage(img, x, y, this);
		//drawing bullet when shot
		if (shot) {
			g.setColor(Color.BLUE);
			g.fillRect(bullet.x,bullet.y, bullet.width, bullet.height);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		//if moved to the border on the left, do not let the shooter move past the border
		if (x < 0) {
			velX = 0;
			x = 0;
		}
		
		//if moved to the border on the right, do not let the shooter move past the border
		if (x > 530) {
			velX = 0;
			x = 530;
		}
		
		//repaint animation so shooter looks like its moving
		x = x + velX;
		repaint();
	}
	
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
		
		//move to the left if left arrow key is pressed
		if (c == KeyEvent.VK_LEFT) {
			velX = -1;
		}

		//move to the right if right arrow key is pressed
		if (c == KeyEvent.VK_RIGHT) {
			velX = 1;
		}
		
		//shoots bullet if space bar is pressed
		if (c == KeyEvent.VK_SPACE) {
			//when space bar is clicked for the first time, bullet is initially set to null
			if (bullet == null) {
				readyToFire = true;
			}
			//ready to fire is true once space bar is clicked
			if (readyToFire == true) {
				//bulletx is fired from the x location of the shooter
				bulletx = x+23;
				//bullety is fired from the y location of the shooter
				bullety = y-7;
				//creating the bullet
				bullet = new Rectangle(bulletx, bullety, 3, 5);
				shot=true; //controls the shot method
				shoot();
			}
		}
	}
	
	public void shoot() {
		//occurs when shot is true
		if (shot) {
			bullet.y++;
		}
	}
	
	public void keyTyped(KeyEvent e) {}
	//to allow object to stop moving if no key is clicked
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == e.VK_LEFT || e.getKeyCode() == e.VK_RIGHT) {
			velX = 0;
		}
		else if (e.getKeyCode() == e.VK_SPACE){
			readyToFire = false;
			if (bullet.y <= -5) {
				bullet = new Rectangle(0,0,0,0);
				shot = false;
				readyToFire = true;
			}
		}
	}
	
	public static void main(String[] args) {
		program t = new program();
		JFrame jf = new JFrame();
		jf.setTitle("Program");
		jf.setSize(600,400);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(t);
	}

}
