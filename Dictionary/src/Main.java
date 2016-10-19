import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
	public static void main(String[] args){
		JFrame frame = new UI();
		frame.setTitle("My Dictionary");
		frame.setSize(850, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
