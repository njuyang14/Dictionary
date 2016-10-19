import java.util.*;
import java.io.*;

public class Dictionary {
	private ArrayList<String> wordList = new ArrayList<String>();
	private ArrayList<String> wordMeaning = new ArrayList<String>();
	private int len;
	
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
	}
	
	public int getSize(){
		return len;
	}
	
	public String getMeaning(int index){
		return wordMeaning.get(index);
	}
	
	public String[] getWordList(){
		String[] stockArr = new String[wordList.size()];
		stockArr = wordList.toArray(stockArr);
		return stockArr;
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
}