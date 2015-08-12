package soleng.framework.standard.workbook;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;

public class ExcelWriter implements WorkbookWriter {
	private String filePath;
	public ExcelWriter(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public void write(Workbook wb) throws IOException {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(filePath);
			wb.write(outputStream);
		    outputStream.close();	
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) { //ignore
			}
		}

	}
}
