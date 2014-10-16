package me.utils;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Represents file uploaded from extjs form
 * 
 * @author Loiane Groner
 * http://loiane.com
 * http://loianegroner.com
 */
public class FileUploadBean {

	private CommonsMultipartFile file;

	private String zbxMc;
	
	
	public String getZbxMc() {
		return zbxMc;
	}

	public void setZbxMc(String zbxMc) {
		this.zbxMc = zbxMc;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
}
