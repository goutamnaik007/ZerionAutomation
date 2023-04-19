package com.zerion.utility;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DataProviders {

	DataFormatter formatter = new DataFormatter();
	String dataPath = "src\\test\\resources\\TestData\\";

	// ----------- Start of Data Providers for Integrations page -----------
	@DataProvider(name = "ingrationsTileContents")
	public Object[][] ingrationsTileContents() throws IOException {
		FileInputStream fis = new FileInputStream(dataPath + "IntegrationsTestData.xlsx");
		Object[][] data = getAllData(fis, "ingrationsTileContents");
		return data;
	}
	
	@DataProvider(name = "verifyLinkRedirections")
	public Object[][] verifyLinkRedirections() throws IOException {
		FileInputStream fis = new FileInputStream(dataPath + "IntegrationsTestData.xlsx");
		Object[][] data = getAllData(fis, "verifyLinkRedirections");
		return data;
	}
	
	@DataProvider(name = "validations")
	public Object[][] validations() throws IOException {
		FileInputStream fis = new FileInputStream(dataPath + "IntegrationsTestData.xlsx");
		Object[][] data = getAllData(fis, "validations");
		return data;
	}

	public Object[][] getAllData(FileInputStream fis, String sheetName) throws IOException {
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		// XSSFSheet sheet = wb.getSheetAt(0);
		XSSFSheet sheet = wb.getSheet(sheetName);
		int rowCount = sheet.getPhysicalNumberOfRows();
		XSSFRow row = sheet.getRow(0);
		int colCount = row.getLastCellNum();
		Object data[][] = new Object[rowCount - 1][colCount];
		for (int i = 0; i < rowCount - 1; i++) {
			row = sheet.getRow(i + 1);
			for (int j = 0; j < colCount; j++) {
				XSSFCell cell = row.getCell(j);
				data[i][j] = formatter.formatCellValue(cell);
			}
		}
		return data;
	}
}
