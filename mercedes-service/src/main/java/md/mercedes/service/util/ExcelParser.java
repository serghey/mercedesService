package md.mercedes.service.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelParser {

	public static final String XLS_FILE_DIR = "sample.xls";
	private static final String ENCODING = "UTF-8";

	public List<String> getProductCodesFromXls(String filePath)
			throws Exception, IOException {
		List<String> productCodeList = new ArrayList<String>();

		Workbook workbook = Workbook.getWorkbook(new File(filePath));
		Sheet sheet = workbook.getSheet(0);

		for (int i = 9; i < sheet.getRows(); i++) {
			Cell[] cells = sheet.getRow(i);
			String code = null;
			if (cells.length > 0) {
				if (cells[2].getContents().contains("/")) {
					code = cells[2].getContents().replace("/", "_slash_");
				} else {
					code = cells[2].getContents();
				}
				productCodeList.add(new String(code.getBytes(), ENCODING));
			}
		}

		workbook.close();

		return productCodeList;

	}

	public List<String> getProductCodesFromClassPath() throws Exception,
			IOException {

		List<String> productCodeList = new LinkedList<String>();
		Workbook workbook = Workbook.getWorkbook(new File(getClass()
				.getClassLoader().getResource(XLS_FILE_DIR).toURI()));
		Sheet sheet = workbook.getSheet(0);

		for (int i = 8; i < sheet.getRows(); i++) {
			Cell[] cells = sheet.getRow(i);
			if (cells.length > 0) {
				String code = null;
				if (cells[2].getContents().contains("/")) {
					code = cells[2].getContents().replace("/", "_slash_");
				} else {
					code = cells[2].getContents();
				}
				if (code.length() > 1) {
					productCodeList.add(new String(code.getBytes(), ENCODING));
				}
			}
		}

		workbook.close();
		return productCodeList.subList(0, 2000);

	}

}
