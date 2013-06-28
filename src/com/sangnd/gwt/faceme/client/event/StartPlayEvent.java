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
package com.sangnd.gwt.faceme.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author heroandtn3
 *
 */
public class StartPlayEvent extends GwtEvent<StartPlayHandler>{

	public final static Type<StartPlayHandler> TYPE = new Type<StartPlayHandler>();
	@Override
	public Type<StartPlayHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(StartPlayHandler handler) {
		handler.onStart(this);
	}

}
