/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *******************************************************************************/
package com.blackducksoftware.tools.commonframework.standard.email;

public class EmailTemplate {
    private String from;

    private String to;

    private String subject;

    private String style;

    private String body;

    public void setFrom(String from) {
	this.from = from;

    }

    public void setTo(String to) {
	this.to = to;

    }

    public void setSubject(String subject) {
	this.subject = subject;

    }

    public void setStyle(String style) {
	this.style = style;

    }

    public void setBody(String body) {
	this.body = body;

    }

    public String getTo() {
	return to;
    }

    public String getFrom() {
	return from;
    }

    public String getSubject() {
	return subject;
    }

    public String getStyle() {
	return style;
    }

    public String getBody() {
	return body;
    }

}
