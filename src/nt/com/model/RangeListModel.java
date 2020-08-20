package nt.com.model;

import java.util.List;


/**
 * 关键词坐标模型
 * 
 * @author kege
 *
 */
public class RangeListModel {
	
	private String fileName;

	private String keyWord; 
	
	private int currentIndex;
	
	private List<Integer> rangeList;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public int getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	public List<Integer> getRangeList() {
		return rangeList;
	}
	public void setRangeList(List<Integer> rangeList) {
		this.rangeList = rangeList;
	}
	
	
}
