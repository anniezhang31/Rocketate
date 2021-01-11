import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Final extends JFrame{
	private JLayeredPane layeredPane=new JLayeredPane();

    public Final() {
		super("Rocketate");
		setSize(800,600);

		ImageIcon backPic = new ImageIcon("Title Screen/titleScreen.png");
		JLabel back = new JLabel(backPic);		
		back.setBounds(0, 0,backPic.getIconWidth(),backPic.getIconHeight());
		layeredPane.add(back,1);
		
		ImageIcon startPic = new ImageIcon("Title Screen/start.png");
		JButton startBtn = new JButton(startPic);	
		startBtn.addActionListener(new ClickStart());
		startBtn.setBounds(290,475,startPic.getIconWidth(),startPic.getIconHeight());
		layeredPane.add(startBtn,2);
			
		setContentPane(layeredPane);        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
    }
    
    public static void main(String[] arguments) {
		Final frame = new Final();
    }
    
    class ClickStart implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent evt){
    		Rocketate game = new Rocketate();
    		setVisible(false);
    	}
    }
}
