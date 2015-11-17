package vkicl.report.bean;

import java.io.InputStream;

@SuppressWarnings("serial")
public class FileBean extends BaseReportBean {

	private int fileLength = 0;
	private String fileName = null;
	private InputStream inputStream = null;

	public int getFileLength() {
		return fileLength;
	}

	public void setFileLength(int fileLength) {
		this.fileLength = fileLength;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public String toString() {
		return "FileBean [fileLength=" + fileLength + ", fileName=" + fileName
				+ ", inputStream=" + inputStream + "]";
	}

}
