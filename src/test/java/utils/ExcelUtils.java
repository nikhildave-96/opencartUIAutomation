package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	private static Logger logger = LogManager.getLogger(ExcelUtils.class);
	static FileInputStream excelFile;
	static String excelFilePath = System.getProperty("user.dir") + "/src/test/java/resources/TestDataExcel.xlsx";
	static XSSFWorkbook workbook;
	static XSSFSheet worksheet;
	static XSSFRow row;
	static XSSFCell cell;

	static {
		try {
			excelFile = new FileInputStream(excelFilePath);
			logger.info("loading excel file '" + excelFilePath + "' ...");
		} catch (FileNotFoundException e) {
			logger.error("excel data file not found ...");
		}
	}

	/**
	 * Fetch row and column data as an 2D array object from the given worksheet
	 * name.
	 * 
	 * @param sheetName Name of the worksheet from which data is to be fetched
	 * @return 2D array containing worksheet data in form of rows and columns
	 * @throws IOException
	 */
	public static Object[][] getData(String sheetName) throws IOException {
		workbook = new XSSFWorkbook(excelFile);
		worksheet = workbook.getSheet(sheetName);
		if (worksheet != null) {
			int rowsCount = worksheet.getLastRowNum() - worksheet.getFirstRowNum();
			int colsCount = worksheet.getRow(1).getLastCellNum();
			Object[][] data = new Object[rowsCount][colsCount];
//			 skipping header row purposefully
//			rows, cols if required to be removed should also be deleted mandatorily from excel
			for (int row = 1; row <= rowsCount; row++) {
				for (int col = 0; col < colsCount; col++) {
					switch (worksheet.getRow(row).getCell(col).getCellType()) {
					case STRING:
//						 storing at zeroth row index of resultant array
						data[row - 1][col] = worksheet.getRow(row).getCell(col).getStringCellValue().trim();
						break;
					case BOOLEAN:
						data[row - 1][col] = worksheet.getRow(row).getCell(col).getBooleanCellValue();
						break;
					case NUMERIC:
						data[row - 1][col] = worksheet.getRow(row).getCell(col).getNumericCellValue();
						break;
					default:
						logger.error("incorrect cell type ...");
						break;
					}
				}
			}
			return data;
		} else {
			logger.error("'" + sheetName + "' not found in the excel file ...");
			return null;
		}
	}

//	public static void main(String[] args) throws IOException {
//		Object[][] sheetData = getData("Register");
//
//		for (int i = 0; i < 1; i++) {
//			for (int j = 0; j < 7; j++) {
//				System.out.println("data is: " + sheetData[i][j]);
//			}
//			System.out.println();
//		}
//	}
}
