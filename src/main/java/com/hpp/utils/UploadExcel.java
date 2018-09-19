package com.hpp.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

public class UploadExcel {
	
	//判断excel版本
	public static Workbook openWorkbook(InputStream in,String filename)throws IOException
	{
		Workbook wb = null;
//		 if(filename.toLowerCase().endsWith(".xlsx")){
//			wb = new XSSFWorkbook(in);//Excel 2007
//		} else {
//			wb = new HSSFWorkbook(in);//Excel 2003
//		} 
		if (!in.markSupported()) {
		    in = new PushbackInputStream(in, 8);
		}
		if (POIFSFileSystem.hasPOIFSHeader(in)) {
			wb = new HSSFWorkbook(in);//Excel 2003
		}else if (POIXMLDocument.hasOOXMLHeader(in)) {//后缀.xlsx
			wb = new XSSFWorkbook(in);
		}
		return wb;
	}
	
	public static String fileType(String filename) 
	{
		 if(filename.toLowerCase().endsWith(".xlsx")){
			 return "xlsx";//Excel 2007
		} else {
			return "xls";//Excel 2003
		} 
	}
	public static String getValue(Cell cell)
	{
		/*String cellValue = "";
		if (null != cell) {
			
			 * if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){ //时间格式
			 * if(HSSFDateUtil.isCellDateFormatted(cell)){ Date dd =
			 * cell.getDateCellValue(); DateFormat df = new
			 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); cellValue =
			 * df.format(dd); }else{ //
			 * cell.setCellType(Cell.CELL_TYPE_STRING);//设置列的类型 cellValue =
			 * cell.getNumericCellValue() + ""; } }else{
			 * cell.setCellType(Cell.CELL_TYPE_STRING);//设置列的类型 cellValue =
			 * cell.getStringCellValue().trim(); }
			 
			int cellType = cell.getCellType();
			switch (cellType) {
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue().trim();
				cellValue = StringUtils.isEmpty(cellValue) ? "" : cellValue;
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				cellValue = String.valueOf(cell.getCellFormula().trim());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					cellValue = DateUtils.formateDate(cell.getDateCellValue(),
							DateUtils.YMDHMS);
				} else {
					cellValue = new DecimalFormat("#.####").format(cell
							.getNumericCellValue());
				}
				break;
			default:
				cellValue = cell.toString().trim();
				break;
			}

		}
		return cellValue;*/
		String cellValue = "";
		if(null != cell){
			if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
				//时间格式
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					Date dd = cell.getDateCellValue();
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					cellValue = df.format(dd);
				}else{
//					cell.setCellType(Cell.CELL_TYPE_STRING);//设置列的类型
					cellValue = cell.getNumericCellValue() + "";
				}
			}else{
				cell.setCellType(Cell.CELL_TYPE_STRING);//设置列的类型
				cellValue = cell.getStringCellValue().trim();	
			}

		}
		return cellValue;
	}
	
	public void getExcelData(String fileName) throws Exception 
	{
		InputStream in = new FileInputStream(fileName);
		Workbook wb = openWorkbook(in, fileName);
		Sheet sheet = (Sheet)wb.getSheetAt(0);
		Row row = null;
		Cell cell = null;
		
		int totalRows = sheet.getPhysicalNumberOfRows();
		int totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		
		for(int r=0; r<totalRows; r++) 
		{
			row = sheet.getRow(r);
			System.out.print("第" + r + "行");
			for(int c = 0; c < totalCells; c++)
			{
				cell = row.getCell(c);
				String cellValue = "";
				if(null != cell){
					// 以下是判断数据的类型
					switch (cell.getCellType()) 
					{
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						cellValue = cell.getNumericCellValue() + "";
						//时间格式
						if(HSSFDateUtil.isCellDateFormatted(cell)){
							Date dd = cell.getDateCellValue();
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							cellValue = df.format(dd);
						}
						break;

					case HSSFCell.CELL_TYPE_STRING: // 字符串
						cellValue = cell.getStringCellValue();
						break;

					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						cellValue = cell.getBooleanCellValue() + "";
						break;

					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						cellValue = cell.getCellFormula() + "";
						break;

					case HSSFCell.CELL_TYPE_BLANK: // 空值
						cellValue = "";
						break;

					case HSSFCell.CELL_TYPE_ERROR: // 故障
						cellValue = "非法字符";
						break;

					default:
						cellValue = "未知类型";
						break;
					}
					
					System.out.print("   "+cellValue+"\t");
				}
			}
			System.out.println();
		}
		
	}
	
	public static void main(String[] args) throws Exception
	{
		String fileName = "C:/test.xls";
		//String fileName = "C:/test.xlsx";
//		Upload upload = new Upload();
//		upload.getExcelData(fileName);
	}

}
