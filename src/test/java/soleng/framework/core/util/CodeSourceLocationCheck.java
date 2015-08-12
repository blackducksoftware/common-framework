/**
 * Test class to verify the code source location
 *  
 * @author garoushanian
 * @date May 4, 2015
 *
 * Copyright (C) 2014-2015 Black Duck Software Inc.
 *
 * http://www.blackducksoftware.com/
 * All rights reserved. 
 */
package soleng.framework.core.util;

import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class CodeSourceLocationCheck {

    private static Logger log = LoggerFactory.getLogger(CodeSourceLocationCheck.class);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }


    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    
    @Test
    public void codeSourceLocationCheck() {
	findCodeSourceLocation(Base64.class);
    }   
    
 
    public void findCodeSourceLocation(Class codeSourceClass) {
	 log.info("CODESOURCE LOCATION: " + codeSourceClass.getProtectionDomain().getCodeSource().getLocation());
    }
    
}
