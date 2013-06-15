/**
 * Copyright 2013 heroandtn3 (@sangnd.info)
 */
/*
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sangnd.gwt.faceme.client.core.control;

import com.google.gwt.event.shared.HandlerRegistration;
import com.sangnd.gwt.faceme.client.core.model.ChessPosition;
import com.sangnd.gwt.faceme.client.core.model.Level;
import com.sangnd.gwt.faceme.client.core.model.Side;
import com.sangnd.gwt.faceme.client.event.ChessSelectHandler;

/**
 * @author heroandtn3
 * @date Jan 7, 2013
 */
public interface Computer {
	/**
	 * 
	 * @param level do kho
	 * @return mang 2 phan tu ChessPosition:
	 * [0]: oldPos
	 * [1]: newPos
	 */
	public ChessPosition[] getBestMove(Level level);
	
	public Side getSide();
	
	public void move();
	
	public HandlerRegistration addChessSelectHandler(ChessSelectHandler handler);
}
