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

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.sangnd.gwt.faceme.client.core.model.Board;
import com.sangnd.gwt.faceme.client.core.model.ChessPosition;
import com.sangnd.gwt.faceme.client.core.model.GameState;
import com.sangnd.gwt.faceme.client.event.HasChessSelectHandler;

/**
 * @author heroandtn3
 *
 */
public interface BoardView extends IsWidget {
	
	/**
	 * Ve ban co
	 * @param board
	 */
	void renderBoard(final Board board);
	
	/**
	 * Di chuyen quan co
	 * @param oldPos
	 * @param newPos
	 */
	void renderMoveChess(final ChessPosition oldPos, final ChessPosition newPos);
	
	/**
	 * Ve quan co dang duoc chon
	 * @param pos
	 */
	void renderChessSelect(final ChessPosition pos);
	
	/**
	 * Ve cac vi tri quan co co the di
	 * @param posCanMv
	 */
	void renderPosCanMove(final List<ChessPosition> posCanMv);
	
	HasChessSelectHandler getWidgetSelectChess();

	void renderWarnKing(boolean warning);
	
	void renderMatchFinish(GameState state);

}
