package soleng.framework.standard.workbook;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;

public interface WorkbookWriter {
	public void write(Workbook wb) throws IOException;
}
