import java.util.*;
import java.io.*;

public class Dictionary {
	private ArrayList<String> wordList = new ArrayList<String>();
	private ArrayList<String> wordMeaning = new ArrayList<String>();
	private int len;
	
	public ArrayList<Integer> currentList = new ArrayList<Integer>();//保存当前找到的联想的单词
	
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
		wordList.add(new String(""));
		wordMeaning.add(new String(""));
		
		len = wordList.size();
		for(int i=0;i<len;i++){
			currentList.add(i);
		}
	}
	
	public int getSize(){
		return len;
	}
	
	public String getWord(int index){
		if(index != -1){
		    return wordList.get(index);
		}
		else{
			return " ";
		}
	}
	
	public String getMeaning(int index){
		if(index != -1)
		    return wordMeaning.get(index);
		else
			return " ";
	}
	
	public String[] getWordList(){
		String[] stockArr = new String[wordList.size()];
		stockArr = wordList.toArray(stockArr);
		return stockArr;
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
	
	//找出前缀相同的单词以及相似度较大的单词（同时纠错）
	public String[] hasSamePrefix(String prefix){
		currentList.clear();
		ArrayList<String> search = new ArrayList<String>();
		int prefixLen = prefix.length();
		for(int i = 0;i < len;i++){
			//判断前缀是否相同
			if(prefixLen <= wordList.get(i).length()){
			    String subStr = wordList.get(i).substring(0, prefixLen);
			    if(subStr.equals(prefix)){
			    	search.add(wordList.get(i));
			    	currentList.add(i);
			    }
			}
		}
		
		if(currentList.size()==0){
			//判断相似度，如果，则加入联想框
			for(int i=0;i<len;i++){
				if(Math.abs(prefix.length()-wordList.get(i).length())<2){
				    int editdistance = editDistance(prefix, wordList.get(i));
				    //System.out.println(editdistance);
			        if(editdistance<=1){
				        search.add(wordList.get(i));
		    	        currentList.add(i);
			        }
				}
			}
		}
				
		String[] stockArr = new String[search.size()];
		stockArr = search.toArray(stockArr);
		return stockArr;
	}
	
	//用编辑距离算法纠错
	public int minOfThree(int a, int b, int c){
		int temp1 = a > b ? b : a;
		int temp2 = temp1 > c ? c : temp1;
		return temp2;
	}

	public int editDistance(String str1, String str2){
		int len1 = str1.length();
		int len2 = str2.length();
		int[][] dynamic;
		dynamic = new int[100][100];//dynamic[i][j]表示前i个str1子串转换成前j个str2子串所需的编辑距离
		//when i==0||j==0,editDistance=i||j
		for (int i = 0; i <= len1; i++)
			dynamic[i][0] = i;
		for (int j = 0; j <= len2; j++)
			dynamic[0][j] = j;

		for (int i = 1; i <= len1; i++)
			for (int j = 1; j <= len2; j++){
				if (str1.charAt(i-1) == str2.charAt(j-1)){//实际长度为1时下标为0
					dynamic[i][j] = dynamic[i-1][j - 1];
				}
				else{
					dynamic[i][j] = minOfThree(dynamic[i][j-1]+1,dynamic[i-1][j]+1,dynamic[i-1][j-1]+1);
				}
			}
		return dynamic[len1][len2];
	}
	
	
}
