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
package com.blackducksoftware.tools.commonframework.standard.trackable;

import com.blackducksoftware.tools.commonframework.standard.trackable.Trackable;
import com.blackducksoftware.tools.commonframework.standard.trackable.progressrecorder.ProgressRecorder;

// TODO: Auto-generated Javadoc
/**
 * The Class SuicidalWorkerMock.
 */
public class SuicidalWorkerMock implements Trackable {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.standard.trackable.Trackable
     * #setProgressRecorder(com.blackducksoftware
     * .tools.commonframework.standard
     * .trackable.progressrecorder.ProgressRecorder)
     */
    public void setProgressRecorder(ProgressRecorder progressRecorder) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.blackducksoftware.tools.commonframework.standard.trackable.Trackable
     * #runAndTrack()
     */
    public void runAndTrack() {
	throw new NullPointerException("SuicidalWorkerMock committing suicide.");
    }
}
