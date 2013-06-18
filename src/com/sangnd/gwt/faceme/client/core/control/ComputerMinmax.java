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

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.sangnd.gwt.faceme.client.core.model.ChessPosition;
import com.sangnd.gwt.faceme.client.core.model.Level;
import com.sangnd.gwt.faceme.client.core.model.Match;
import com.sangnd.gwt.faceme.client.core.model.Side;
import com.sangnd.gwt.faceme.client.event.ChessSelectEvent;
import com.sangnd.gwt.faceme.client.event.ChessSelectHandler;




/**
 * @author heroandtn3
 * @date Jan 7, 2013
 */
public class ComputerMinmax implements Computer, HasHandlers {

	private Match match;
	private Side side;
	private MoveGenerator moveGenerator;
	private HandlerManager handlerManager;
	/**
	 * 
	 */
	public ComputerMinmax(Match match, Side side) {
		this.match = match;
		this.side = side;
		moveGenerator = new MoveGeneratorNormal();
		handlerManager = new HandlerManager(this);
	}

	@Override
	public ChessPosition[] getBestMove(Level level) {
		List<ChessPosition[]> allMoves = moveGenerator.getMoves(match.getBoard(), side);
		int x = (int) (Math.random() * allMoves.size());  
		return moveGenerator.getMoves(match.getBoard(), side).get(x);
	}

	@Override
	public Side getSide() {
		return this.side;
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		 handlerManager.fireEvent(event);
		
	}

	@Override
	public void move() {
		ChessPosition[] mv = getBestMove(null);
		fireEvent(new ChessSelectEvent(mv[0]));
		fireEvent(new ChessSelectEvent(mv[1]));
	}

	@Override
	public HandlerRegistration addChessSelectHandler(ChessSelectHandler handler) {
		return handlerManager.addHandler(ChessSelectEvent.TYPE, handler);
	}

}
