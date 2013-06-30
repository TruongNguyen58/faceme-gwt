/**
 * Quan tot
 */
package com.sangnd.gwt.faceme.client.core.model.chess;

import java.util.ArrayList;
import java.util.List;

import com.sangnd.gwt.faceme.client.core.model.Board;
import com.sangnd.gwt.faceme.client.core.model.ChessPosition;



public class Pawn implements Chess {

	private boolean passedRiver = false;
	private Board board;

	public Pawn(Board board) {
		this.board = board;
	}

	private boolean CheckPassRiver(ChessPosition current, int side) {
		if (side * board.getTable()[current.getRow()][current.getCol()] < 0)
			return true;
		return false;
	}
	
	@Override
	public List<ChessPosition> getPosCanMove(ChessPosition current) {
		List<ChessPosition> list = new ArrayList<ChessPosition>();
		int row = current.getRow();
		int col = current.getCol();
		
		int side = 1;
		if (row <= 4) side = -1;
		
		passedRiver = CheckPassRiver(current, side);
		
		int nextRow;
		int nextCol;
		int value = board.getTable()[row][col];
		
		
		if (passedRiver) {
			
			// go forward
			nextRow = row + side;
			nextCol = col;
			
			if (0 <= nextRow && nextRow <= 9) {
				int nextValue = board.getTable()[nextRow][nextCol];
				if (nextValue == 0) {
					list.add(new ChessPosition(nextCol, nextRow, false));
				} else if (value * nextValue < 0) {
					list.add(new ChessPosition(nextCol, nextRow, true));
				}
			}
			
			// go left
			nextRow = row;
			nextCol = col - 1;
			if (nextCol >= 0) {
				int nextValue = board.getTable()[nextRow][nextCol];
				if (nextValue == 0) {
					list.add(new ChessPosition(nextCol, nextRow, false));
				} else if (value * nextValue < 0) {
					list.add(new ChessPosition(nextCol, nextRow, true));
				}
			}
			
			// go right
			nextRow = row;
			nextCol = col + 1;
			if (nextCol <= 8) {
				int nextValue = board.getTable()[nextRow][nextCol];
				if (nextValue == 0) {
					list.add(new ChessPosition(nextCol, nextRow, false));
				} else if (value * nextValue < 0) {
					list.add(new ChessPosition(nextCol, nextRow, true));
				}
			}
		} else {
			// not passed river yet
			// go forward only
			nextRow = row - side;
			nextCol = col;
			if (0 <= nextRow && nextRow <= 9) {
				int nextValue = board.getTable()[nextRow][nextCol];
				if (nextValue == 0) {
					list.add(new ChessPosition(nextCol, nextRow, false));
				} else if (value * nextValue < 0) {
					list.add(new ChessPosition(nextCol, nextRow, true));
				}
			}
		}
		return list;
	}

	@Override
	public List<ChessPosition> getTargetPos(ChessPosition current) {
		List<ChessPosition> pos = new ArrayList<ChessPosition>();
		ChessPosition CpTemp;
		int x, y, value, omg, tmpX, tmpY;
		x = current.getCol();
		y = current.getRow();
		if (y <= 4)
			omg = 1;
		else
			omg = -1;
		passedRiver = CheckPassRiver(current, omg);
		value = board.getTable()[y][x];
		if (passedRiver) {
			tmpX = x;
			tmpY = y - omg;
			if ((tmpY >= 0) && (tmpY <= 9)
					&& (board.getTable()[tmpY][tmpX] != 0)) {
				if (board.getTable()[tmpY][tmpX] * value < 0) {
					CpTemp = new ChessPosition(tmpX, tmpY, true);
				} else {
					CpTemp = new ChessPosition(tmpX, tmpY, false);
				}
				pos.add(CpTemp);
			}
			tmpX = x - 1;
			tmpY = y;
			if ((tmpX >= 0) && (board.getTable()[tmpY][tmpX] != 0)) {

				if (board.getTable()[tmpY][tmpX] * value < 0) {
					CpTemp = new ChessPosition(tmpX, tmpY, true);
				} else {
					CpTemp = new ChessPosition(tmpX, tmpY, false);
				}
				pos.add(CpTemp);
			}

			tmpX = x + 1;
			tmpY = y;
			if ((tmpX <= 8) && (board.getTable()[tmpY][tmpX] != 0)) {
				if (board.getTable()[tmpY][tmpX] * value < 0) {
					CpTemp = new ChessPosition(tmpX, tmpY, true);
				} else {
					CpTemp = new ChessPosition(tmpX, tmpY, false);
				}
				pos.add(CpTemp);
			}
		} else {
			tmpX = x;
			tmpY = y + omg;
			if ((tmpY >= 0) && (board.getTable()[tmpY][tmpX] != 0)) {
				if (board.getTable()[tmpY][tmpX] * value < 0) {
					CpTemp = new ChessPosition(tmpX, tmpY, true);
				} else {
					CpTemp = new ChessPosition(tmpX, tmpY, false);
				}
				pos.add(CpTemp);
			}

		}

		return pos;
	}
}
