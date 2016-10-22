import java.util.*;
import java.io.*;

public class Dictionary {
	private ArrayList<String> wordList = new ArrayList<String>();
	private ArrayList<String> wordMeaning = new ArrayList<String>();
	private int len;
	
	public ArrayList currentList = new ArrayList();//保存当前找到的联想的单词
	
	public Dictionary(){
		Scanner input = null;
		try{
		    input = new Scanner(new File("./src/dictionary.txt"));}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		while(input.hasNext()){
			String buffer = input.nextLine();
			String[] token = buffer.split("\t",0);  
			wordList.add(token[0]);
			wordMeaning.add(token[1]);
		}
		len = wordList.size();
		for(int i=0;i<len;i++){
			currentList.add(i);
		}
	}
	
	public int getSize(){
		return len;
	}
	
	public String getWord(int index){
		return wordList.get(index);
	}
	
	public String getMeaning(int index){
		return wordMeaning.get(index);
	}
	
	public String[] getWordList(){
		String[] stockArr = new String[wordList.size()];
		stockArr = wordList.toArray(stockArr);
		return stockArr;
	}
	
	public int[] getCurrentList(){
		return currentList;
	}
	//从数组中获得当前显示于jlist中的String[]
	public String[] getAnListOfCurrentArray(int[] a){
		String[] current = null;
		for(int i=0;i<a.length;i++)
			current[i]=wordList.get(i);
		return current;
	}
	
	public int binarySearchWord(String searchWord){
		int low=0,high=len-1;
		int mid;
		while (low<=high){
			mid = (low+high)/2;
	         if(searchWord.compareTo(wordList.get(mid))==0)
		             return mid;
		         if(searchWord.compareTo(wordList.get(mid))<0)
		             high=mid-1;
		         if(searchWord.compareTo(wordList.get(mid))>0)
		             low=mid+1;
		     }
		     return -1;
	}
	
	public String[] hasSamePrefix(String prefix){
		currentList.clear();
		ArrayList<String> search = new ArrayList<String>();
		int prefixLen = prefix.length();
		for(int i = 0;i < len;i++){
			if(prefixLen <= wordList.get(i).length()){
			    String subStr = wordList.get(i).substring(0, prefixLen);
			    if(subStr.equals(prefix)){
			    	search.add(wordList.get(i));
			    	currentList.add(i);
			    }
			}
		}
				
		String[] stockArr = new String[search.size()];
		stockArr = search.toArray(stockArr);
		return stockArr;
	}
}
