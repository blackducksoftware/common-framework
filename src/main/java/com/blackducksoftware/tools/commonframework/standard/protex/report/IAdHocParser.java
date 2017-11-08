/**
 * CommonFramework
 *
 * Copyright (C) 2017 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.blackducksoftware.tools.commonframework.standard.protex.report;

import java.util.ArrayList;

import org.jsoup.nodes.Document;

public interface IAdHocParser<T extends HocElement> {

    /**
     * Parses the headers from doc.
     * 
     * @param doc
     *            the doc
     * @return the hoc element
     * @throws Exception
     *             the exception
     */
    HocElement parseHeadersFromDoc(Document doc,
	    int targetSectionIndex) throws Exception;

    /**
     * Parse report section table rows from the Protex report, returning the
     * data in a list of HocElements. nonConforming = report sections of the
     * form: Label: value. Normal (conforming) = report sections that contain
     * columns of data, each with a header (label) at the top (first row).
     * 
     * @param doc
     *            the doc
     * @param adHocHeader
     *            the ad hoc header
     * @param returnRawHtml
     *            the return raw html
     * @param hocElementClass
     *            the hoc element class
     * @return the array list
     * @throws Exception
     */
    ArrayList<T> parseRows(Document doc,
	    HocElement adHocHeader, boolean returnRawHtml,
	    Class<T> hocElementClass, int targetSectionIndex) throws Exception;

}
