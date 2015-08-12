/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.standard.datatable.reader;

import soleng.framework.standard.datatable.DataTable;

public interface DataTableReader {
	public void read(DataTable dataSet) throws Exception;
}
