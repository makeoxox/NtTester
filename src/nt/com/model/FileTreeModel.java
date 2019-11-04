package nt.com.model;

import java.io.File;

//文件模型
public class FileTreeModel {
	private String fileName;
	private String filePathName;
	private File file;
	
	public FileTreeModel(String fileName,String filePathName,File file){
		this.file=file;
		this.fileName=fileName;
		this.filePathName=filePathName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePathName() {
		return filePathName;
	}

	public void setFilePathName(String filePathName) {
		this.filePathName = filePathName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public String toString() {
		return fileName;
	}
}
