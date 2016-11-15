import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;

public class UI extends JFrame{
	/*定义字典类*/
	private Dictionary MyWord = new Dictionary();
	/*定义图片文件*/
	private ImageIcon icon = new ImageIcon("./src/background.jpg");
	private ImageIcon searchIcon = new ImageIcon("./src/search.png");
	private ImageIcon wordIcon = new ImageIcon("./src/word.png");
	/*定义输入面板p1*/
	private JPanel p1 = new JPanel();//最北方部分的面板
	private JButton jbtSearch = new JButton("Search");//搜索按钮
	private JLabel jlb = new JLabel(/*" 单词 "*/);
	private JTextArea jtfInput = new JTextArea(1,22);//输入栏	
	/*定义显示框*/
	private JTextArea display = new JTextArea();
	/*定义联想区面板listScroller*/
	private JList<String> list = new JList<String>();//联想
	private JScrollPane listScroller = new JScrollPane(list);
	
	private JPanel p0 = new JPanel();//frame上第一层面板
	private JPanel p3 = new JPanel();//定义综合面板
	
	/*UI构造方法*/
	public UI(){
		Border lineBorder = new LineBorder(Color.GRAY, 1);//全局边界线
		
		/*设置frame布局*/
		setLayout(new BorderLayout());//frame layout
		
		/*设置背景图片，将frame转换成panel，加入图片后，再在frame上覆盖面板以达成背景图片设置的目的*/
		JLabel backImage = new JLabel(icon);
		backImage.setBounds(0, 0, icon.getIconWidth(),icon.getIconHeight());//设置图片大小
		((JPanel)this.getContentPane()).setOpaque(false);//获取frame的面板，将其转换成JPanel
		this.getLayeredPane().setLayout(null);//设置无布局方式
		this.getLayeredPane().add(backImage, new Integer(Integer.MIN_VALUE));//加入图片
		
		/*在背景frame上添加第一层面板p0*/
		Container c = getContentPane(); //获取JFrame面板
		c.add(p0,BorderLayout.CENTER);//
		p0.setOpaque(false);//设置p0为透明，
		p0.add(p3);
		
		/*设置顶部输入框面板p1*/
		//属性设置
		p1.setLayout(new FlowLayout(FlowLayout.LEFT,25,20));
		jtfInput.setFont(new Font("Serif", 0, 25));
		jlb.setFont(new Font("Serif", 0, 18));
		jbtSearch.setPreferredSize(new Dimension(100,32));
		jbtSearch.setToolTipText("press it to search");
		p1.setOpaque(false);//透明化
		
		jbtSearch.addActionListener(new SearchListener());//为search button添加监听 
		changeListWithInput();//文本框添加监听器，有改动时自动更新联想框
		
		jbtSearch.setIcon(searchIcon);
		jlb.setIcon(wordIcon);
		
		p1.add(jlb);
		p1.add(jtfInput);
		p1.add(jbtSearch);
		
		/*设置左侧联想框滑动条面板listScroller，*/	
		//添加list组件表示联想框
		list = new JList<String>(MyWord.getWordList());//init list
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
		list.setFont(new Font("Arial", Font.PLAIN, 19));
		
		//为list组件添加监听器，在鼠标点击联想框时会执行相应函数
		list.addListSelectionListener(new listSelectionListener());
		
		//定义带有滑动条的面板listScroller，将list组件添加上去
		listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(180,630));
		
		//设置联想框透明
		listScroller.setOpaque(false);
		listScroller.getViewport().setOpaque(false);
		list.setOpaque(false);
		((JLabel)list.getCellRenderer()).setOpaque(false);
		
		/*设置中间显示框的属性*/
		display.setBorder(lineBorder);
		display.setFont(new Font("Serif", 0, 30));
		display.setLineWrap(true);//自动换行
		display.setOpaque(false);//透明化
		display.setEditable(false);//不可编辑
		
		/*设置面板综合p3*/
		p3.setLayout(new BorderLayout());
		p3.setOpaque(false);
		p3.add(p1,BorderLayout.NORTH);//
		p3.add(listScroller, BorderLayout.WEST);//
		p3.add(display,BorderLayout.CENTER);//
				
		//list组件的鼠标响应监听方法
		list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if(arg0.getClickCount() == 2){
                	String s1 = MyWord.getMeaning((Integer) (MyWord.currentList.get(list.locationToIndex(arg0.getPoint()))));
                	String s2 = MyWord.getWord((Integer)MyWord.currentList.get(list.locationToIndex(arg0.getPoint())));
                	display.setText(s2+"\r\n"+s1);
                }
            }
        });
	}
	
	/*刷新整个联想区*/
	public void refreshList(String[] tempWordList){
		list.setListData(tempWordList);
	}
	
	/*搜索按钮监听方法实现*/
	class SearchListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自动生成的方法存根
			if(jtfInput.getText()!=""){
			    int index = MyWord.binarySearchWord(jtfInput.getText());
			    if(index != -1)
			        display.setText(jtfInput.getText()+"\r\n"+MyWord.getMeaning(index));
			    else
			    	display.setText("could not find this word, please input again.");
			}
		}
	}
	
	/*list联想区鼠标选择方法实现*/
	class listSelectionListener implements ListSelectionListener{

		public void valueChanged(ListSelectionEvent arg0) {
			// TODO 自动生成的方法存根
			//JList<String> tempList = (JList<String>)arg0.getSource();
			int index = list.getSelectedIndex();
			if(list.getValueIsAdjusting()==false&&index!=-1){
			    String s1 = MyWord.getMeaning((Integer) (MyWord.currentList.get(index)));
			    String s2 = MyWord.getWord((Integer)MyWord.currentList.get(index));
		        display.setText(s2+"\r\n"+s1);
			}
		}
	}
	
	/*输入框文本改动响应实现*/
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

















