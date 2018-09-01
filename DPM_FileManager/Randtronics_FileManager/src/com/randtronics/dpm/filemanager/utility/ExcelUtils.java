package com.randtronics.dpm.filemanager.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.randtronics.dpm.filemanager.config.Constants;

public class ExcelUtils 
{
	public static FileInputStream ExcelFile;
	public static HSSFWorkbook ExcelWBook;
	public static HSSFSheet ExcelWSheet;
	public static HSSFRow Row;
	public static HSSFCell Cell;
	public static HSSFCell Cell1;
	public static HSSFCell Cell2;
    public static HSSFCellStyle my_style;
    public static File f1,f2;
    
	public static void setExcelFile(String path) throws Exception
	{
		f1 = new File(path);
		boolean check;
		String getCurrentTime= "" + System.currentTimeMillis();
		String destFile= Constants.Path_TestData + Constants.File_TestData1 + "_" + getCurrentTime + ".xls";
		
		f2=new File(destFile);
		check=f2.createNewFile();
		FileUtils.copyFile(f1, f2);

		ExcelFile=new FileInputStream(f1);
		ExcelWBook=new HSSFWorkbook(ExcelFile);
	}
	
	public static int getRowCount(String SheetName)
	{
		int number=0;
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
	    number=ExcelWSheet.getLastRowNum()+1;
		return number;
	}
	
	public static String getCellData(int rowNum,int ColNum) throws Exception
	{
		Cell=ExcelWSheet.getRow(rowNum).getCell(ColNum);
		String cellData=CellToString(Cell);
		return cellData;
	}
	
	public static String CellToString(HSSFCell cell)
    {
          String result = "";
          try
          {
              if(cell==null)
              {
                  result="";
              }
              else if(cell.getCellType()==org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING)
              {
            	  result=cell.getStringCellValue();
              } 
              else if(cell.getCellType()==org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC)
              {
                  result  = String.valueOf(cell.getNumericCellValue());
                  return result;
              }
              else if(cell.getCellType()==org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK)
              {
            	  result="";
              }
              else if(cell.getCellType()==org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN)
              {
            	  result=String.valueOf(cell.getBooleanCellValue());
              }
              else
              {
            	  System.out.println("Out of world.");
            	  throw new RuntimeException("Out of world.");
              }   
           }
         
	      catch (Exception e) 
	      {
              System.out.println("Exception:"+e);
	      }
		return result;
      }


	public static void setCellData(String Status, String Result,int RowNum, int StatusColNum,int ResultColNum) throws Exception	
	{ 
		try
		{
			my_style = ExcelWBook.createCellStyle();
		  	Row  = ExcelWSheet.getRow(RowNum);
		  	Cell1 = Row.getCell(StatusColNum,Row.RETURN_BLANK_AS_NULL);
		  	Cell2 = Row.getCell(ResultColNum,Row.RETURN_BLANK_AS_NULL);
			if (Cell1 == null) 
			{
				Cell1 = Row.createCell(StatusColNum);
				Cell1.setCellValue(Status);
			} 
			else 
			{
				Cell1.setCellValue(Status);
			}
			
			if (Cell2 == null) 
			{
				Cell2 = Row.createCell(ResultColNum);
				Cell2.setCellValue(Result);
			} 
			else 
			{
				Cell2.setCellValue(Result);
			}
			
			if(Status=="Pass")
			{
			    my_style.setFillPattern(HSSFCellStyle.FINE_DOTS );
			    my_style.setFillForegroundColor(new HSSFColor.GREEN().getIndex());
			    my_style.setFillBackgroundColor(new HSSFColor.GREEN().getIndex());
			    Cell1.setCellStyle(my_style);
			}
			else if(Status=="Skipped")
			{
				  my_style.setFillPattern(HSSFCellStyle.FINE_DOTS );
				  my_style.setFillForegroundColor(new HSSFColor.BLUE().getIndex());
				  my_style.setFillBackgroundColor(new HSSFColor.BLUE().getIndex());
				  Cell1.setCellStyle(my_style);
			}
			
			else if(Status=="Error")
			{
				  my_style.setFillPattern(HSSFCellStyle.FINE_DOTS );
				  my_style.setFillForegroundColor(new HSSFColor.RED().getIndex());
				  my_style.setFillBackgroundColor(new HSSFColor.RED().getIndex());
				  Cell1.setCellStyle(my_style);
			}
			else
			{
			    my_style.setFillPattern(HSSFCellStyle.FINE_DOTS );
			    my_style.setFillForegroundColor(new HSSFColor.RED().getIndex());
			    my_style.setFillBackgroundColor(new HSSFColor.RED().getIndex());
			    Cell1.setCellStyle(my_style);
			}
			FileOutputStream fileOut = new FileOutputStream(f2);
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		}
		catch(Exception e)
		{
			throw (e);
		}
	}
}

	
	/*public static int getRowCount(String SheetName)
    {
          ExcelWSheet = ExcelWBook.getSheet(SheetName);
          //int number = ExcelWSheet.getPhysicalNumberOfRows();
          int number=1000;
          //System.out.println("Physical no.of rows:"+number);
          int count = 0;
          boolean notEmpty = true;
          int emptyCount = 0;
          while (count < number && emptyCount <= 5 ) 
          {
        	  
               if (ExcelWSheet.getRow(count) != null) 
               {
                    ++count;
                    //System.out.println("Count value is:"+count);
                   emptyCount = 0;
               }
               else {
            	   	++count;
                    ++emptyCount;
                    //System.out.println("Count value with empty"+count);
                   //System.out.println("empty count value:"+emptyCount);
               }
          }
          
          if(emptyCount>5)
          {
        	  count=count-emptyCount;
          }
          
          return count;
    }*/

	
