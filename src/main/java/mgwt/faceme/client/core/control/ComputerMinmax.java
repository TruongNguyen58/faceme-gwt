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
package mgwt.faceme.client.core.control;

import java.util.List;

import mgwt.faceme.client.core.model.ChessPosition;
import mgwt.faceme.client.core.model.Level;
import mgwt.faceme.client.core.model.Match;
import mgwt.faceme.client.core.model.Side;



/**
 * @author heroandtn3
 * @date Jan 7, 2013
 */
public class ComputerMinmax implements Computer {

	private Match match;
	private Side side;
	private MoveGenerator moveGenerator;
	/**
	 * 
	 */
	public ComputerMinmax(Match match, Side side) {
		this.match = match;
		this.side = side;
		moveGenerator = new MoveGeneratorNormal(match);
	}

	@Override
	public ChessPosition[] getBestMove(Level level) {
		List<ChessPosition[]> allMoves = moveGenerator.getMoves(side);
		int x = (int) (Math.random() * allMoves.size());  
		return moveGenerator.getMoves(side).get(x);
	}

}
