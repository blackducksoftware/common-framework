/**
Copyright (C)2014 Black Duck Software Inc.
http://www.blackducksoftware.com/
All rights reserved. **/

package soleng.framework.standard.protex.identification;

public class IdentificationMade {
	private String path;
	private int startLine;
	private int numberOfLines;
	private String componentId;
	private String componentVersionId;
	private String componentVersionString;
	private int score;
	
	public IdentificationMade(String path, int startLine, int numberOfLines,
			String componentId, String componenetVersionId,
			String componentVersionString, int score) {
		super();
		this.path = path;
		this.startLine = startLine;
		this.numberOfLines = numberOfLines;
		this.componentId = componentId;
		this.componentVersionId = componentVersionId;
		this.componentVersionString = componentVersionString;
		this.score = score;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((componentId == null) ? 0 : componentId.hashCode());
		result = prime
				* result
				+ ((componentVersionId == null) ? 0 : componentVersionId
						.hashCode());
		result = prime * result + numberOfLines;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + score;
		result = prime * result + startLine;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IdentificationMade)) {
			return false;
		}
		IdentificationMade other = (IdentificationMade) obj;
		if (componentId == null) {
			if (other.componentId != null) {
				return false;
			}
		} else if (!componentId.equals(other.componentId)) {
			return false;
		}
		if (componentVersionId == null) {
			if (other.componentVersionId != null) {
				return false;
			}
		} else if (!componentVersionId.equals(other.componentVersionId)) {
			return false;
		}
		if (numberOfLines != other.numberOfLines) {
			return false;
		}
		if (path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!path.equals(other.path)) {
			return false;
		}
		if (score != other.score) {
			return false;
		}
		if (startLine != other.startLine) {
			return false;
		}
		return true;
	}



	@Override
	public String toString() {
		return "IdentificationMade [path=" + path + ", startLine=" + startLine
				+ ", numberOfLines=" + numberOfLines + ", componentId="
				+ componentId + ", componentVersion=" + componentVersionId
				+ ", componentVersionString=" + componentVersionString
				+ ", score=" + score + "]";
	}

	
	
	
	
}
