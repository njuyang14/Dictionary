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
	
	private JPanel p1 = new JPanel();//输入框
	private JButton jbtSearch = new JButton("Search");
	private JLabel jlb = new JLabel(" 单词 ");
	private JTextField jtfInput = new JTextField(50);
	
	private JLabel display = new JLabel();
	
	
	private JPanel p2 = new JPanel();//联想panel
	private JList<String> list = new JList<String>();//联想
	//private ListModel<String> listModel;
	//private JScrollPane listScroller = new JScrollPane();//滚动条
	//private JScrollPane listScroller1 = new JScrollPane(list);
	
	
	public UI(){
		//some value
		Border lineBorder = new LineBorder(Color.GRAY, 1);
		setLayout(new BorderLayout());//frame layout
		
		//顶部输入框
		p1.setLayout(new FlowLayout(FlowLayout.LEFT,25,20));
		jlb.setFont(new Font("Serif", 0, 18));
		jlb.setBorder(lineBorder);
		jbtSearch.addActionListener(new SearchListener());//search button 
		changeListWithInput();
		p1.add(jlb);
		p1.add(jtfInput);
		p1.add(jbtSearch);
		add(p1,BorderLayout.NORTH);
		
		
		//左侧联想框
		p2.setLayout(new BorderLayout());
		list = new JList<String>(MyWord.getWordList());//init list
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
		list.addListSelectionListener(new listSelectionListener());
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(180,200));
		//p2.add(list,BorderLayout.CENTER);
		p2.add(listScroller,BorderLayout.CENTER);
		add(p2, BorderLayout.WEST);
		
		//右侧显示框
		display.setBorder(lineBorder);
		display.setFont(new Font("Serif", 0, 30));
		display.setVerticalAlignment(SwingConstants.TOP);
		add(display,BorderLayout.CENTER);
		
		//jlist mouse click event
		list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if(arg0.getClickCount() == 2){
                	String s1 = MyWord.getMeaning((Integer) (MyWord.currentList.get(list.locationToIndex(arg0.getPoint()))));
                	String s2 = MyWord.getWord((Integer)MyWord.currentList.get(list.locationToIndex(arg0.getPoint())));
                    display.setText(s2+" "+s1);
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
			int index = MyWord.binarySearchWord(jtfInput.getText());
			display.setText(jtfInput.getText()+" "+MyWord.getMeaning(index));
		}

	}
	
	class listSelectionListener implements ListSelectionListener{

		public void valueChanged(ListSelectionEvent arg0) {
			// TODO 自动生成的方法存根
			JList<String> tempList = (JList<String>)arg0.getSource();
			String s1 = MyWord.getMeaning((Integer) (MyWord.currentList.get(tempList.getSelectedIndex())));
			String s2 = MyWord.getWord((Integer)MyWord.currentList.get(tempList.getSelectedIndex()));
		    display.setText(s2+" "+s1);
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

















