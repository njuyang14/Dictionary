import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;

public class UI extends JFrame{
	private Dictionary MyWord = new Dictionary();
	private ImageIcon icon = new ImageIcon("./src/background3.jpg");
	private JPanel p1 = new JPanel();//输入框
	private JButton jbtSearch = new JButton("Search");
	private JLabel jlb = new JLabel(" 单词 ");
	private JTextField jtfInput = new JTextField(30);	
	private JLabel display = new JLabel();	
	private JPanel p2 = new JPanel();//联想panel
	private JList<String> list = new JList<String>();//联想
	private JPanel p0 = new JPanel();//底层图片
	private JPanel p3 = new JPanel();
	
	public UI(){
		//some value
		Border lineBorder = new LineBorder(Color.GRAY, 1);
		setLayout(new BorderLayout());//frame layout
		
		//设置背景图片
		JLabel backImage = new JLabel(icon);
		backImage.setBounds(0, 0, icon.getIconWidth(),icon.getIconHeight());
		((JPanel)this.getContentPane()).setOpaque(false);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(backImage, new Integer(Integer.MIN_VALUE));
		Container c = getContentPane(); //获取JFrame面板
		p0.setOpaque(false);
		p3.setLayout(new BorderLayout());
		p3.setOpaque(false);
		p1.setOpaque(false);
		p2.setOpaque(false);
		c.add(p0,BorderLayout.CENTER);
		p0.add(p3);
		
		//设置顶部输入框
		p1.setLayout(new FlowLayout(FlowLayout.LEFT,25,20));
		jlb.setFont(new Font("Serif", 0, 18));
		jlb.setBorder(lineBorder);
		jbtSearch.addActionListener(new SearchListener());//search button 
		changeListWithInput();
		p1.add(jlb);
		p1.add(jtfInput);
		p1.add(jbtSearch);
		p3.add(p1,BorderLayout.NORTH);//
		
		//设置左侧联想框
		p2.setLayout(new BorderLayout());
		list = new JList<String>(MyWord.getWordList());//init list
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
		list.addListSelectionListener(new listSelectionListener());
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(180,430));
		list.setFont(new Font("Arial", Font.PLAIN, 19));
		//设置联想框透明
		listScroller.setOpaque(false);
		listScroller.getViewport().setOpaque(false);
		list.setOpaque(false);
		((JLabel)list.getCellRenderer()).setOpaque(false);
		p3.add(listScroller, BorderLayout.WEST);//
		
		//中间显示框
		display.setBorder(lineBorder);
		display.setFont(new Font("Serif", 0, 30));
		display.setVerticalAlignment(SwingConstants.TOP);
		p3.add(display,BorderLayout.CENTER);//
				
		//jlist mouse click event
		list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if(arg0.getClickCount() == 2){
                	String s1 = MyWord.getMeaning((Integer) (MyWord.currentList.get(list.locationToIndex(arg0.getPoint()))));
                	String s2 = MyWord.getWord((Integer)MyWord.currentList.get(list.locationToIndex(arg0.getPoint())));
                	display.setText("<html><body><p>"+s2+"</p><br><p>"+s1+"</p><body></html>");
                }
            }
        });
	}
	
	public void refreshList(String[] tempWordList){
		list.setListData(tempWordList);
	}
	
	class SearchListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自动生成的方法存根
			if(jtfInput.getText()!=""){
			    int index = MyWord.binarySearchWord(jtfInput.getText());
			    display.setText("<html><body><p>"+jtfInput.getText()+"</p><br><p>"+MyWord.getMeaning(index)+"</p><body></html>");
			}
		}
	}
	
	class listSelectionListener implements ListSelectionListener{

		public void valueChanged(ListSelectionEvent arg0) {
			// TODO 自动生成的方法存根
			//JList<String> tempList = (JList<String>)arg0.getSource();
			int index = list.getSelectedIndex();
			if(list.getValueIsAdjusting()==false&&index!=-1){
			    String s1 = MyWord.getMeaning((Integer) (MyWord.currentList.get(index)));
			    String s2 = MyWord.getWord((Integer)MyWord.currentList.get(index));
		        display.setText("<html><body><p>"+s2+"</p><br><p>"+s1+"</p><body></html>");
			}
		}
	}
	
    public void changeListWithInput(){
    	jtfInput.getDocument().addDocumentListener(new DocumentListener(){

			public void changedUpdate(DocumentEvent arg0) {
				// TODO 自动生成的方法存根
				refreshList(MyWord.hasSamePrefix(jtfInput.getText()));
			}

			public void insertUpdate(DocumentEvent arg0) {
				// TODO 自动生成的方法存根
				refreshList(MyWord.hasSamePrefix(jtfInput.getText()));
			}

			public void removeUpdate(DocumentEvent arg0) {
				// TODO 自动生成的方法存根
				refreshList(MyWord.hasSamePrefix(jtfInput.getText()));
			}
			});
    } 
}

















