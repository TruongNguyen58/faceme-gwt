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
public class ChannelMessage extends JavaScriptObject {

	/**
	 * 
	 */
	protected ChannelMessage() {
	}

	public final native String getSenderId() /*-{
		return this.senderId;
	}-*/;
	
	public final native void setSenderId(String senderId) /*-{
		this.senderId = senderId;
	}-*/;

	public final native String getContent() /*-{
		return this.content;
	}-*/;
	
	public final native void setContent(String content) /*-{
		this.content = content;
	}-*/;
	
	public static final native ChannelMessage fromJson(String json) /*-{
		return JSON.parse(json, function(key, value) {
			return value;
		});
	}-*/;

	public final String toJson() {
		return new JSONObject(this).toString();
	}
	
	public static final ChannelMessage create(String senderId, String content) {
		//return fromJson("({\"senderId\":\""+senderId+"\",\"content\":\""+content+"\"})");
		ChannelMessage message = (ChannelMessage) JavaScriptObject.createObject().cast();
		message.setSenderId(senderId);
		message.setContent(content);
		return message;
	}

}
