import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;

public class UI extends JFrame{
	//private String[] temp = {"a","b","c","d","e","f","g","a","b","c","d","e","f","g","a","b","c","d","e","f","g"};
	private Dictionary MyWord = new Dictionary();
	
	private JPanel p1 = new JPanel();//输入框
	private JButton jbtSearch = new JButton("Search");
	private JLabel jlb = new JLabel(" 单词 ");
	private JTextField jtfInput = new JTextField(50);
	
	private JLabel display = new JLabel();
	
	
	private JPanel p2 = new JPanel();//联想panel
	private JList list = new JList(MyWord.getWordList());//联想
	private JScrollPane listScroller = new JScrollPane(list);//滚动条
	//private JScrollPane listScroller1 = new JScrollPane(list);
	
	
	public UI(){
		//some value
		Border lineBorder = new LineBorder(Color.GRAY, 1);
		
		//顶部输入框
		p1.setLayout(new FlowLayout(FlowLayout.LEFT,25,20));
		jlb.setFont(new Font("Serif", 0, 18));
		jlb.setBorder(lineBorder);
		p1.add(jlb);
		p1.add(jtfInput);
		p1.add(jbtSearch);
		
		//左侧联想框
		p2.setLayout(new BorderLayout());
		list = new JList();
		list.setCellRenderer(null);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);		
		listScroller.setPreferredSize(new Dimension(250, 80));
		p2.add(list,BorderLayout.CENTER);
		p2.add(listScroller,BorderLayout.EAST);
		
		//右侧显示框
		setLayout(new BorderLayout());
		add(p1,BorderLayout.NORTH);
		add(p2, BorderLayout.WEST);
		display.setBorder(lineBorder);
		display.setFont(new Font("Serif", 0, 30));
		display.setVerticalAlignment(SwingConstants.TOP);
		add(display,BorderLayout.CENTER);
		
		jbtSearch.addActionListener(new SearchListener());
		
		//click
		/*MouseListener mouseListener = new MouseAdapter() {
		     public void mouseClicked(MouseEvent e) {
		         if (e.getClickCount() == 2) {
		             int index = list.locationToIndex(e.getPoint());
		             System.out.println("Double clicked on Item " + index);
		          }
		     }
		 };
		list.addMouseListener(new MouseListener());*/
	}
	
	
	
	class SearchListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自动生成的方法存根
			int index = MyWord.binarySearchWord(jtfInput.getText());
			display.setText(MyWord.getMeaning(index));
		}

	}
	
	 
}

















