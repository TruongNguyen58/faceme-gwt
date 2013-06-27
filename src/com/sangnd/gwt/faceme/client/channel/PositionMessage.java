/*
 * Copyright 2013 heroandtn3 (heroandtn3 [at] gmail.com - www.sangnd.com
 * )
 * This file is part of mFaceme.
 * mFaceme is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * mFaceme is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with mFaceme.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * 
 */
package com.sangnd.gwt.faceme.client.channel;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

/**
 * @author heroandtn3
 * 
 */
public class PositionMessage extends JavaScriptObject {

	/**
	 * 
	 */
	protected PositionMessage() {
	}

	public final native int getRow() /*-{
		return this.row;
	}-*/;

	public final native void setRow(int row) /*-{
		this.row = row;
	}-*/;

	public final native int getCol() /*-{
		return this.col;
	}-*/;

	public final native void setCol(int col) /*-{
		this.col = col;
	}-*/;

	public final native boolean isKillable() /*-{
		return this.killable;
	}-*/;

	public final native void setKillable(boolean killable) /*-{
		this.killable = killable;
	}-*/;

	public static final native PositionMessage fromJson(String json) /*-{
		return JSON.parse(json, function(key, value) {
			return value;
		});
	}-*/;

	public final String toJson() {
		return new JSONObject(this).toString();
	}

}
