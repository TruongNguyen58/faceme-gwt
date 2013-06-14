/*

Copyright (c) 2013 heroandtn3 (@sangnd.info), khanhoatink4, igisik

This file is part of Faceme.

Faceme is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Faceme is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sangnd.gwt.faceme.client.core.control;

import java.util.ArrayList;
import java.util.List;

import com.sangnd.gwt.faceme.client.core.model.Board;
import com.sangnd.gwt.faceme.client.core.model.ChessPosition;
import com.sangnd.gwt.faceme.client.core.model.Match;
import com.sangnd.gwt.faceme.client.core.model.Side;




/**
 * @author heroandtn3
 * @date Jan 30, 2013
 */
public class MoveGeneratorNormal implements MoveGenerator {

	@Override
	public List<ChessPosition[]> getMoves(Board board, Side side) {
		List<ChessPosition[]> allMoves = new ArrayList<ChessPosition[]>();

		List<ChessPosition> posCanMove;
		int[][] table = board.getTable();
		
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 9; col++) {
				int value = table[row][col];
				if (isInTeam(side, value)) { // o nay chua quan thuoc ve side
					// vi tri hien tai
					ChessPosition currentPos = new ChessPosition(row, col);
					
					// cac vi tri co the di
					posCanMove = Match.CHESSs[Math.abs(value)]
							.getPosCanMove(currentPos);
					
					// them tat ca cac nuoc di vao list
					for (ChessPosition pos : posCanMove) {
						allMoves.add(new ChessPosition[] {currentPos, pos});
					}
				}
			}
		}
		return allMoves;
	}
	
	/**
	 * Ham kiem tra quan co co gia tri value co nam trong team side khong
	 * @param side FRIEND hoac ENERMY
	 * @param value gia tri cua o dang xet
	 * @return true neu quan co nam cung team, nguoc lai thi false
	 */
	private boolean isInTeam(Side side, int value) {
		if ((side == Side.ENERMY && value < 0) ||
			(side == Side.FRIEND && value > 0)) {
			return true;
		} else {
			return false;
		}
	}

}
