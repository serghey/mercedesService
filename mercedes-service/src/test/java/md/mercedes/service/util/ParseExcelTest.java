package md.mercedes.service.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class ParseExcelTest {

	ExcelParser parseExcel;
	private String tempURL = "C:/Users/sdediu001c/Downloads/18.10.2013-040005_ru.xls";
	
	public ParseExcelTest(){
		parseExcel = new ExcelParser();
	}
	
	@Test
	public void test() throws IOException, Exception {

		List<String> productCodes = parseExcel.getProductCodesFromClassPath();
		
		assertNotNull(productCodes);
		assertTrue(productCodes.size()>0);
		System.out.println(productCodes);
		
	}

}
