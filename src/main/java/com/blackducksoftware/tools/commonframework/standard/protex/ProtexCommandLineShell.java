/*******************************************************************************
 * Copyright (C) 2015 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *******************************************************************************/
package com.blackducksoftware.tools.commonframework.standard.protex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.blackducksoftware.tools.commonframework.standard.common.CommandLineShell;

/**
 * The Class ProtexCommandLineShell.
 */
public class ProtexCommandLineShell implements CommandLineShell {

    /**
     * Instantiates a new protex command line shell.
     */
    public ProtexCommandLineShell() {

    }

    @Override
    public ProtexProjectPojo runProjectSelector(
	    List<ProtexProjectPojo> projectList) throws Exception {
	ProtexProjectPojo projSelection = null;
	String projectID = null;

	System.out
		.println("Please provide the number of the project (press 0 to exit):");
	for (int i = 0; i < projectList.size(); i++) {
	    System.out.println((i + 1) + ". Project: "
		    + projectList.get(i).getProjectName());
	}

	int inputSelectionNumber = -1;
	try {
	    BufferedReader br = new BufferedReader(new InputStreamReader(
		    System.in));
	    String line = br.readLine();
	    line = line.trim();
	    inputSelectionNumber = Integer.parseInt(line);

	    br.close();

	    if (inputSelectionNumber == 0) {
		System.out.println("Bye!");
		System.exit(1);
	    }

	} catch (IOException e1) {
	    throw new Exception("Trouble parsing selection: " + e1.getMessage());
	}

	{
	    if (inputSelectionNumber > projectList.size()
		    || inputSelectionNumber < 1) {
		System.err.println("Please provide a valid selection number!");
		System.exit(1);
	    }

	    // Reduce the number by one, because the selection screen was
	    // incremented by one.
	    inputSelectionNumber = inputSelectionNumber - 1;

	    projSelection = projectList.get(inputSelectionNumber);
	    projectID = projSelection.getProjectKey();
	}

	if (projectID == null) {
	    System.err.println("Unable to determine correct project ID.");
	    System.exit(1);
	}

	return projSelection;
    }

}
