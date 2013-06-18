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
package com.sangnd.gwt.faceme.client.view;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sangnd.gwt.faceme.client.core.model.Constant;
import com.sangnd.gwt.faceme.client.model.Status;

/**
 * @author heroandtn3
 *
 */
public class StatusWidget implements HasStatus, IsWidget {

	private Status status;
	private Image img;

	/**
	 * 
	 */
	public StatusWidget() {
		status = Status.OFFLINE;
		img = new Image();
		img.setUrl(Constant.STATUS_DIR + "offline.png");
	}
	

	@Override
	public Widget asWidget() {
		return img;
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public void setStatus(Status status) {
		switch (status) {
			case AVAI:
				img.setUrl(Constant.STATUS_DIR + "avai.png");
				break;
			case BUSY:
				img.setUrl(Constant.STATUS_DIR + "busy.png");
				break;
			case OFFLINE:
				img.setUrl(Constant.STATUS_DIR + "offline.png");
				break;
			default:
				img.setUrl(Constant.STATUS_DIR + "offline.png");
		}
		
	}

}
