import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class Rocketate extends JFrame implements ActionListener{
    Timer myTimer;
    RocketateGame game;

    public Rocketate() {
        super("Rocketate");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,630);
        myTimer = new Timer(10, this);
        game = new RocketateGame (this);
        add(game);
        setResizable(false);
        setVisible(true);
    }

    public void start(){
        myTimer.start();
    }

    public void actionPerformed(ActionEvent evt){
    	if(game.checkGameOver()==false){
    		game.rotate();
    	    game.move();
        	game.repaint();
    	}
    	else{
    		game.gameOver();
    	}
    }

    public static void main(String[] arguments) {
        Rocketate frame = new Rocketate();
    }
}


class RocketateGame extends JPanel implements KeyListener{
	private JLayeredPane layeredPane=new JLayeredPane();
    private int rocketx,rockety;
    private Rocket rocket;
    private Background background;
    private Blocks block1, block2, block3;
    private boolean []keys;
    private boolean mouseDown;
    public static final int LEFT = -1, RIGHT = 1, DOWN = 3, UP = -2;
    private int dir;
    private Image back;
    private Rocketate mainFrame;

    public RocketateGame (Rocketate m){
        rocketx = 250;
        rockety = 175;
        rocket = new Rocket(rocketx, rockety);
        background = new Background();
        block1 = new Blocks(325, 275, new ImageIcon("Blocks/block1.png").getImage(), new ImageIcon("Blocks/block1.png").getImage(), 75, 75);
        block2 = new Blocks(509, 220, new ImageIcon("Blocks/block2.png").getImage(), new ImageIcon("Blocks/block3.png").getImage(), 70, 20);
        block3 = new Blocks(219, 135, new ImageIcon("Blocks/block2.png").getImage(), new ImageIcon("Blocks/block3.png").getImage(), 108, 20);
        keys = new boolean[KeyEvent.KEY_LAST+1];
        mouseDown = false;
        mainFrame = m;
        setSize(800,630);
        addKeyListener(this);
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

    public void rotate() {	//each object must change positions when screen rotates
        if(keys[KeyEvent.VK_RIGHT] ){
        	background.rotate(RIGHT);
        	block1.rotate(RIGHT, background.getFrame());
        	block2.rotate(RIGHT, background.getFrame());
        	block3.rotate(RIGHT, background.getFrame());
        	rocket.rotate(RIGHT, background.getFrame());
        }
        if(keys[KeyEvent.VK_LEFT] ){
        	background.rotate(LEFT);
        	block1.rotate(LEFT, background.getFrame());
        	block2.rotate(LEFT, background.getFrame());
        	block3.rotate(LEFT, background.getFrame());
        	rocket.rotate(LEFT, background.getFrame());
        }
    }

    public void move(){
    	Point mouse = MouseInfo.getPointerInfo().getLocation();
		Point offset = getLocationOnScreen();
	//	System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
	
        if(keys[KeyEvent.VK_UP] ){
        	if(background.collide(rocket.getX()+1, rocket.getY()+1, 37, 37, UP) == false){
        		if(rocket.collideBlock(block1.getX(), block1.getY(), block1.getWidth(), block1.getHeight(), UP)== false && rocket.collideBlock(block2.getX(), block2.getY(), block2.getWidth(), block2.getHeight(), UP)== false && rocket.collideBlock(block3.getX(), block3.getY(), block3.getWidth(), block3.getHeight(), UP)== false){
       				rocket.checkFlying(true);
        			rocket.move(UP);
        		}
        		else if(rocket.collideBlock(block1.getX(), block1.getY(), block1.getWidth(), block1.getHeight(), UP)== true){
        			if(block1.collideBlock(block2.getX(), block2.getY(), block2.getWidth(), block2.getHeight(), UP)==false && block1.collideBlock(block3.getX(), block3.getY(), block3.getWidth(), block3.getHeight(), UP)==false && background.collide(block1.getX(), block1.getY(), block1.getWidth(), block1.getHeight(), UP) == false){
        				block1.move(UP);
        				rocket.checkFlying(true);
        				rocket.move(UP);
        			}
        		}
        		else if(rocket.collideBlock(block2.getX(), block2.getY(), block2.getWidth(), block2.getHeight(), UP)==true){
        			if(block2.collideBlock(block1.getX(), block1.getY(), block1.getWidth(), block1.getHeight(), UP)==false && block2.collideBlock(block3.getX(), block3.getY(), block3.getWidth(), block3.getHeight(), UP)==false && background.collide(block2.getX(), block2.getY(), block2.getWidth(), block2.getHeight(), UP) == false){
        				block2.move(UP);
        				rocket.checkFlying(true);
        				rocket.move(UP);
        			}
        		}
        		else if(rocket.collideBlock(block3.getX(), block3.getY(), block3.getWidth(), block3.getHeight(), UP)==true){
        			if(block3.collideBlock(block1.getX(), block1.getY(), block1.getWidth(), block1.getHeight(), UP)==false && block3.collideBlock(block2.getX(), block2.getY(), block2.getWidth(), block2.getHeight(), UP)==false && background.collide(block3.getX(), block3.getY(), block3.getWidth(), block3.getHeight(), UP) == false){
        				block3.move(UP);
        				rocket.checkFlying(true);
        				rocket.move(UP);
        			}
        		}
           	}
        }
        else{
        	if(background.collide(rocket.getX()+1, rocket.getY()+1, 37, 37, DOWN) == false){
        		if(rocket.collideBlock(block1.getX(), block1.getY(), block1.getWidth(), block1.getHeight(), DOWN)==false && rocket.collideBlock(block2.getX(), block2.getY(), block2.getWidth(), block2.getHeight(), DOWN)==false && rocket.collideBlock(block3.getX(), block3.getY(), block3.getWidth(), block3.getHeight(), DOWN)==false){
            		rocket.checkFlying(false);
            		rocket.move(DOWN);
        		}
        	}
        }
        if(background.collide(block1.getX(), block1.getY(), block1.getWidth(), block1.getHeight(), DOWN) == false){
        	if(block1.collideBlock(block2.getX(), block2.getY(), block2.getWidth(), block2.getHeight(), DOWN)==false && block1.collideBlock(block3.getX(), block3.getY(), block3.getWidth(), block3.getHeight(), DOWN)==false && rocket.collideBlock(block1.getX(), block1.getY(), block1.getWidth(), block1.getHeight(), UP)==false){
        		block1.move(DOWN);
        	}
        }
        if(background.collide(block2.getX(), block2.getY(), block2.getWidth(), block2.getHeight(), DOWN) == false){
     		if(block2.collideBlock(block1.getX(), block1.getY(), block1.getWidth(), block1.getHeight(), DOWN)==false && block2.collideBlock(block3.getX(), block3.getY(), block3.getWidth(), block3.getHeight(), DOWN)==false && rocket.collideBlock(block2.getX(), block2.getY(), block2.getWidth(), block2.getHeight(), UP)==false){
        		block2.move(DOWN);
     		}
        }
        if(background.collide(block3.getX(), block3.getY(), block3.getWidth(), block3.getHeight(), DOWN) == false){
        	if(block3.collideBlock(block2.getX(), block2.getY(), block2.getWidth(), block2.getHeight(), DOWN)==false && block3.collideBlock(block1.getX(), block1.getY(), block1.getWidth(), block1.getHeight(), DOWN)==false && rocket.collideBlock(block3.getX(), block3.getY(), block3.getWidth(), block3.getHeight(), UP)==false){
        		block3.move(DOWN);
        	}
        }
    }

	public boolean checkGameOver(){
		if(background.collideDoor(rocket.getX(), rocket.getY(), 37, 37)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void gameOver(){
		
	}

    public void paint(Graphics g){
       g.setColor(new Color(216,191,216));  
       g.fillRect(0,0,getWidth(),getHeight());
       background.draw(g);
       block1.draw(g);
       block2.draw(g);
       block3.draw(g);
       rocket.draw(g);
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
    public void mousePressed(MouseEvent e) {
	    if (e.getButton() == MouseEvent.BUTTON1) {
    	    mouseDown = true;
    	}
	}

	public void mouseReleased(MouseEvent e) {
    	if (e.getButton() == MouseEvent.BUTTON1) {
        	mouseDown = false;
    	}
	}
}

class Rocket{
    private int x, y, frame, delay, originalX, originalY;
    private int newx, newy;
    private Image character;
    private Image[]characterFly;
    private boolean fly=false;
    public static final int WAIT = 10, LEFT = -1, RIGHT = 1, DOWN = 3, UP = -2;

    public Rocket(int x, int y) {
        this.x = x;
        this.y = y;
        originalX = x;
        originalY = y;
        newx=x;
        newy=y;
        frame = 0;
        delay = 0;
        character = (new ImageIcon("Rocket Sprites/rocket.png").getImage()).getScaledInstance(37, 37, Image.SCALE_SMOOTH);
        characterFly = new Image[6];
        for (int i = 0; i < 6; i++) {
            characterFly[i] = (new ImageIcon("Rocket Sprites" + "/" + "rocket" + (i + 1) + ".png").getImage()).getScaledInstance(37,37, Image.SCALE_SMOOTH);
        }
    }
    
    public int getX(){
    	return x;
    }
    
    public int getY(){
    	return y;
    }

    public void move(int dy){
    	y+=dy;
   	    delay += 1;
    	if(delay % WAIT == 0){
        	frame = (frame + 1) % characterFly.length;
        }
    }
    
    public void rotate(int dir, int backgroundFrame){
    	if (dir==RIGHT){
    		if (backgroundFrame == 0){
    			newx = 400 - (y-100) + 200 - 37;
    			newy = x - 100;
    		}
    		else if (backgroundFrame == 1){
    			newx = (600-y) + 100 -37;
    			newy = (x-200) + 100;
    		}
    		else if (backgroundFrame == 2){
    			newx = 400 - (y-100) + 200 -37;
    			newy = (x - 100);
    		}
    		else{
    			newx = (600-y) + 100 - 37;
    			newy = (x-200) + 100;
    		}
    	}
    	else{
    		if (backgroundFrame == 0){
    			newx = y - 100 + 200;
    			newy = 600 - (x-100) - 37;
    		}
    		else if (backgroundFrame == 1){
    			newx = y + 100;
    			newy = 400 - (x-200) + 100 - 37;
    		}
    		else if (backgroundFrame == 2){
    			newx = (y-100) + 200;
    			newy = 600 - (x-100) - 37;
    		}
    		else{
    			newx = y + 100;
    			newy = 400 - (x-200) + 100 - 37;
    		}
    	}
    	x = newx;
    	y = newy;
    }

    public void checkFlying(boolean flying){
        fly = flying;
    }
    
    public boolean collideBlock(int bx, int by, int bw, int bh, int dy){
    	Rectangle blockRect = new Rectangle(bx, by, bw, bh);
    	Rectangle newPos = new Rectangle(x,y+dy,37,37);
    	if(newPos.intersects(blockRect)){
    		return true;
    	}
    	return false;
    }

    public void draw(Graphics g){
        if (fly){
            g.drawImage(characterFly[frame], x, y, null);
        }
        else{
            g.drawImage(character, x, y, null);
        }
    }
}

class Background{
	public static final int LEFT = -1, RIGHT = 1, UP=-2, DOWN=3;
	private int frame;
	private Image[]backgrounds = new Image[4];
	private Rectangle doorRect;
	
	private BufferedImage[]walls = new BufferedImage[4];	
		
	public Background(){
		frame = 0;
        for (int i = 0; i < 4; i++) {
        	if(i%2==1){
        		backgrounds[i] = (new ImageIcon("Background" + "/" + "background" + (i + 1) + ".png").getImage()).getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        	}
        	else{
            	backgrounds[i] = (new ImageIcon("Background" + "/" + "background" + (i + 1) + ".png").getImage()).getScaledInstance(400, 600, Image.SCALE_SMOOTH);
        	}
        }
        try {
        	for (int i = 0; i < 4; i++) {
        		
        		walls[i] = (ImageIO.read(new File("Background/wall"+(i+1)+".png")));
        		
        	}
		} 
		catch (IOException e) {
			System.out.println(e);
		} 
	}
	
	public void rotate(int dir){
		frame += dir;
		try {
 		    Thread.sleep(200);
		} 
		catch (InterruptedException ie) {
    		Thread.currentThread().interrupt();
		}
		if (frame<0){
			frame =3;
		}
		if(frame>3){
			frame=0;
		}
	}
	
	public int getFrame(){
		return frame;
	}
	
	public Rectangle getDoor(){
		return doorRect;
	}
	
	public boolean collide (int oldX, int oldY, int width, int height, int dy){
		int c;  
		Color colour;
		for(int i=0; i<width-2; i++){
			if (dy==UP && walls[frame].getRGB(oldX+i-100, dy+oldY)==-16777216){
				return true;
			}
			else if (dy==DOWN && walls[frame].getRGB(oldX+i-100, dy+oldY+height)==-16777216){
				return true;
			}
		}
		return false;
	}
	
	public boolean collideDoor(int oldX, int oldY, int width, int height){
		for(int i=0; i<width; i++){
			if (walls[frame].getRGB(oldX+i-100, oldY+height)==-16735512){
				return true;
			}
			else if(walls[frame].getRGB(oldX+i-100, oldY)==-16735512){
				return true;
			}
		}
		return false;
	}
	
	public void draw(Graphics g){
		if(frame%2==1){
			g.drawImage(backgrounds[frame], 100, 100, null);
		}
		else{
			g.drawImage(backgrounds[frame], 200, 0, null);
		}
	}
}

class Blocks{
	private int x, y, width, height, newx, newy;
    private Image blockV, blockH;
    private int currentFrame;
    public static final int HORIZONTAL = 1, VERTICAL=2;
    public static final int LEFT = -1, RIGHT = 1, UP=-2, DOWN=3;
    
    public Blocks(int x, int y, Image picV, Image picH, int width, int height){
    	this.x = x;
        this.y = y;
        newx = x;
        newy = y;
        this.width = width;
        this.height = height;
    	blockV = picV.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    	blockH = picH.getScaledInstance(height, width, Image.SCALE_SMOOTH);
    	currentFrame= VERTICAL;
    }
    
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public int getWidth(){
    	return width;
    }
    public int getHeight(){
    	return height;
    }
    
    public void move(int dy){
    	y += dy;
    }
    
    public boolean collideBlock(int oldX, int oldY, int oldWidth, int oldHeight, int dy){
    	Rectangle newPos = new Rectangle(oldX, oldY, oldWidth, oldHeight);
    	Rectangle block = new Rectangle(x,y+dy,width,height);
    	if(block.intersects(newPos)){
    		return true;
    	}
    	return false;
    }
    
    public void rotate(int dir, int backgroundFrame){
    	int newWidth, newHeight;
    	if (dir==RIGHT){
    		if (backgroundFrame == 0){
    			newx = 400 - (y-100) + 200 - height;
    			newy = x - 100;
    		}
    		else if (backgroundFrame == 1){
    			newx = (600-y) + 100 - height;
    			newy = (x-200) + 100;
    		}
    		else if (backgroundFrame == 2){
    			newx = 400 - (y-100) + 200 -height;
    			newy = (x - 100);
    		}
    		else{
    			newx = (600-y) + 100 - height;
    			newy = (x-200) + 100;
    		}
    	}
    	else{
    		if (backgroundFrame == 0){
    			newx = y - 100 + 200;
    			newy = 600 - (x-100) - width;
    		}
    		else if (backgroundFrame == 1){
    			newx = y + 100;
    			newy = 400 - (x-200) + 100 - width;
    		}
    		else if (backgroundFrame == 2){
    			newx = (y-100) + 200;
    			newy = 600 - (x-100) - width;
    		}
    		else{
    			newx = y + 100;
    			newy = 400 - (x-200) + 100 - width;
    		}
    	}
    	newWidth=height; newHeight=width;
    	width=newWidth; height=newHeight;
    	x = newx;
    	y = newy;
    	if (currentFrame==VERTICAL){
    		currentFrame=HORIZONTAL;
    	}
    	else{
    		currentFrame=VERTICAL;
    	}
    }
    
    public void draw(Graphics g){
    	if(currentFrame == VERTICAL){
    		g.drawImage(blockV,x,y, null);
    	}
    	else if(currentFrame == HORIZONTAL){
    		g.drawImage(blockH,x,y, null);
    	}
    }
}
