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
import com.sangnd.gwt.faceme.client.core.model.ChessPosition;

/**
 * @author heroandtn3
 *
 */
public class ChessSelectEvent extends GwtEvent<ChessSelectHandler> {
	
	public static final Type<ChessSelectHandler> TYPE = new Type<ChessSelectHandler>();
	private ChessPosition pos;
	
	public ChessSelectEvent(ChessPosition pos) {
		this.pos = pos;
		
	}
	
	public ChessPosition getPos() {
		return pos;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChessSelectHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChessSelectHandler handler) {
		handler.onSelect(this);
		
	}

}
