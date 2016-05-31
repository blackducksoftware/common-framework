/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *  with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 *  under the License.
 *
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
