/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package me.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import me.entity.Zbx;
import me.utils.BusinessException;
import me.utils.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.utils.Reflections;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 导入Excel文件（支持“XLS”和“XLSX”格式）
 * @author wj
 * @version 2014-10-15
 */
public class ImportExcelme {
	
	private static Logger log = LoggerFactory.getLogger(ImportExcelme.class);
			
	/**
	 * 工作薄对象
	 */
	private Workbook wb;
	
	/**
	 * 工作表对象
	 */
	private Sheet sheet;
	

	/**
	 * 标题行号
	 */
	private int headerNum;
	
	/**
	 * 构造函数
	 * @param path 导入文件，读取第一个工作表
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @throws InvalidFormatException 
	 * @throws IOException 
	 */
	public ImportExcelme(String fileName, int headerNum) 
			throws InvalidFormatException, IOException {
		this(new File(fileName), headerNum);
	}
	
	/**
	 * 构造函数
	 * @param path 导入文件对象，读取第一个工作表
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @throws InvalidFormatException 
	 * @throws IOException 
	 */
	public ImportExcelme(File file, int headerNum) 
			throws InvalidFormatException, IOException {
		this(file, headerNum, 0);
	}

	/**
	 * 构造函数
	 * @param path 导入文件
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException 
	 * @throws IOException 
	 */
	public ImportExcelme(String fileName, int headerNum, int sheetIndex) 
			throws InvalidFormatException, IOException {
		this(new File(fileName), headerNum, sheetIndex);
	}
	
	/**
	 * 构造函数
	 * @param path 导入文件对象
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException 
	 * @throws IOException 
	 */
	public ImportExcelme(File file, int headerNum, int sheetIndex) 
			throws InvalidFormatException, IOException {
		this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
	}
	
	/**
	 * 构造函数
	 * @param file 导入文件对象
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException 
	 * @throws IOException 
	 */
	public ImportExcelme(MultipartFile multipartFile, int headerNum, int sheetIndex) 
			throws InvalidFormatException, IOException {
		this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerNum, sheetIndex);
	}

	/**
	 * 构造函数
	 * @param path 导入文件对象
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException 
	 * @throws IOException 
	 */
	public ImportExcelme(String fileName, InputStream is, int headerNum, int sheetIndex) 
			throws InvalidFormatException, IOException {
		if (StringUtils.isBlank(fileName)){
			throw new RuntimeException("导入文档为空!");
		}else if(fileName.toLowerCase().endsWith("xls")){    
			this.wb = new HSSFWorkbook(is);    
        }else if(fileName.toLowerCase().endsWith("xlsx")){  
        	this.wb = new XSSFWorkbook(is);
        }else{  
        	throw new RuntimeException("文档格式不正确!");
        }  
		if (this.wb.getNumberOfSheets()<sheetIndex){
			throw new RuntimeException("文档中没有工作表!");
		}
		this.sheet = this.wb.getSheetAt(sheetIndex);
		this.headerNum = headerNum;
		log.debug("Initialize success.");
	}
	
	/**
	 * 获取行对象
	 * @param rownum
	 * @return
	 */
	public Row getRow(int rownum){
		return this.sheet.getRow(rownum);
	}

	/**
	 * 获取数据行号
	 * @return
	 */
	public int getDataRowNum(){
		return headerNum+1;
	}
	
	/**
	 * 获取最后一个数据行号
	 * @return
	 */
	public int getLastDataRowNum(){
		return this.sheet.getLastRowNum();
//		return this.sheet.getLastRowNum()+headerNum;
	}
	
	/**
	 * 获取最后一个列号
	 * @return
	 */
	public int getLastCellNum(){
		return this.getRow(headerNum).getLastCellNum();
	}
	
	/**
	 * 获取单元格值
	 * @param row 获取的行
	 * @param column 获取单元格列号
	 * @return 单元格值
	 */
	public Object getCellValue(Row row, int column){
		Object val = "";
		try{
			Cell cell = row.getCell(column);
			if (cell != null){
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
					val = cell.getNumericCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_STRING){
					val = cell.getStringCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA){
					val = cell.getCellFormula();
				}else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
					val = cell.getBooleanCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_ERROR){
					val = cell.getErrorCellValue();
				}
			}
		}catch (Exception e) {
			return val;
		}
		return val;
	}
	
	/**
	 * 获取导入数据列表
	 * @param cls 导入对象类型
	 */
	public <E> List<E> getDataList(Class<E> cls,List<Zbx> zbxList) throws Exception{
		String title = (String)getMergedRegionValue(sheet, 0, 1);//标题从第二列合并
		String tbDw = StringUtils.substringAfter((String)getMergedRegionValue(sheet, 1, 0), "：");//
		Date tbDate = DateUtils.parseDate((StringUtils.substringAfter((String)getMergedRegionValue(sheet, 1, 4), ":"))) ;//从第5列开始合并
//		Date tbDate = DateUtil.getJavaDate(Double.valueOf(StringUtils.substringAfter((String)getMergedRegionValue(sheet, 1, 4), ":"))) ;//从第5列开始合并
		
		Map<String,String> map = Maps.newHashMap();
		for(Zbx zbx : zbxList){
			map.put(zbx.getPropertyText(), zbx.getPropertyName());
		}
		List<String> propertyNames = Lists.newArrayList();
		for(int i = 0; i < this.getLastCellNum(); i++){//只有header 是 指标项的子集
			Row row = this.getRow(this.getHeaderRowNum());
			Object val = this.getCellValue(row, i);
			String propertyName = map.get(val);
			if(StringUtils.isEmpty(propertyName)){
				throw new BusinessException("行标题："+val+"未进行指标项配置");
			}
			propertyNames.add(propertyName);
		}
		//log.debug("Import column count:"+annotationList.size());
		// Get excel data
		List<E> dataList = Lists.newArrayList();
		for (int i = this.getDataRowNum(); i <= this.getLastDataRowNum(); i++) {
			E e = (E)cls.newInstance();
			
			
			int column = 0;
			Row row = this.getRow(i);
			StringBuilder sb = new StringBuilder();
			for (String propertyName : propertyNames){
				Object val = this.getCellValue(row, column++);//都是string 类型
				if (val != null){
					// Get param type and type cast
					Class<?> valType = Reflections.getAccessibleField(e, propertyName).getType();
					
					check(e, propertyName, val, i, column);
					//log.debug("Import value type: ["+i+","+column+"] " + valType);
					try {
						if (valType == String.class){
							String s = String.valueOf(val.toString());
							if(StringUtils.endsWith(s, ".0")){
								val = StringUtils.substringBefore(s, ".0");
							}else{
								val = String.valueOf(val.toString());
							}
						}else if (valType == Integer.class){
							val = Double.valueOf(val.toString()).intValue();
						}else if (valType == Long.class){
							val = Double.valueOf(val.toString()).longValue();
						}else if (valType == Double.class){
							val = Double.valueOf(val.toString());
						}else if (valType == Float.class){
							val = Float.valueOf(val.toString());
						}else if (valType == Date.class){
//							val = DateUtil.getJavaDate((Double)val);
							val = DateUtils.parseDate(val.toString());
						}else{//暂时用不到  wj，用于many-to-one etc.
						}
					} catch (Exception ex) {
						log.info("Get cell value ["+i+","+column+"] error: " + ex.toString());
						val = null;
					}
					// set entity value
					Reflections.invokeSetter(e, propertyName, val);
				}
				sb.append(val+", ");
			}
			Reflections.invokeSetter(e, "title", title);
			Reflections.invokeSetter(e, "tbDw", tbDw);
			Reflections.invokeSetter(e, "tbDate", tbDate);
			dataList.add(e);
			log.debug("Read success: ["+i+"] "+sb.toString());
		}
		return dataList;
	}
	/**
	 * 暂时只检查列是否为空
	 * @wj
	 * @param e
	 * @param propertyName
	 * @param val
	 * @param i
	 * @param column
	 * @throws BusinessException
	 */
	private <E> void check(E e,String propertyName,Object val,int i, int column) throws BusinessException {
//		NotNull notNullAnno = Reflections.getAccessibleField(e, propertyName).getAnnotation(NotNull.class);
//		NotBlank notBlankAnno = Reflections.getAccessibleField(e, propertyName).getAnnotation(NotBlank.class);
		String getMethodName = "get" + StringUtils.capitalize(propertyName);
		NotNull notNullAnno = Reflections.getAccessibleMethodByName(e, getMethodName).getAnnotation(NotNull.class);
		NotBlank notBlankAnno = Reflections.getAccessibleMethodByName(e, getMethodName).getAnnotation(NotBlank.class);
		
		if(notNullAnno!=null){
			if(StringUtils.isBlank(String.valueOf(val.toString()))){
				throw new BusinessException("表格 ["+i+","+column+"] 数据为空");
			}
		}
		if(notBlankAnno!=null){
			if(StringUtils.isBlank(String.valueOf(val.toString()))){
				throw new BusinessException("表格 ["+i+","+column+"] 数据为空");
			}
		}
	}

	private int getHeaderRowNum() {
		return this.headerNum;
	}

	/**   
	* 获取合并单元格的值   
	* @param sheet   
	* @param row   
	* @param column   
	* @return   
	*/    
	public Object getMergedRegionValue(Sheet sheet ,int row , int column){    
	    int sheetMergeCount = sheet.getNumMergedRegions();    
	        
	    for(int i = 0 ; i < sheetMergeCount ; i++){    
	        CellRangeAddress ca = sheet.getMergedRegion(i);    
	        int firstColumn = ca.getFirstColumn();    
	        int lastColumn = ca.getLastColumn();    
	        int firstRow = ca.getFirstRow();    
	        int lastRow = ca.getLastRow();    
	            
	        if(row >= firstRow && row <= lastRow){    
	                
	            if(column >= firstColumn && column <= lastColumn){    
	            	Row row_ = this.getRow(row);
	                return this.getCellValue(row_, column);  
	            }    
	        }    
	    }    
	        
	    return null ;    
	}    
	  
	/**  
	* 判断合并了行  
	* @param sheet  
	* @param row  
	* @param column  
	* @return  
	*/  
	private boolean isMergedRow(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row == firstRow && row == lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}
//	/**
//	 * 导入测试
//	 */
//	public static void main(String[] args) throws Throwable {
//		
//		ImportExcel ei = new ImportExcel("target/export.xlsx", 1);
//		
//		for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum(); i++) {
//			Row row = ei.getRow(i);
//			for (int j = 0; j < ei.getLastCellNum(); j++) {
//				Object val = ei.getCellValue(row, j);
//				System.out.print(val+", ");
//			}
//			System.out.print("\n");
//		}
//		
//	}

}
