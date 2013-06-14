/**
 * Quan tuong
 */
/*
 * Author HoangNv, 29.09.2012
 */
package com.sangnd.gwt.faceme.client.core.model.chess;

import java.util.ArrayList;
import java.util.List;

import com.sangnd.gwt.faceme.client.core.model.Board;
import com.sangnd.gwt.faceme.client.core.model.ChessPosition;

public class King implements Chess {

	private Board board;

	public King(Board board) {
		this.board = board;
	}

	@Override
	public List<ChessPosition> getPosCanMove(ChessPosition current) {
		List<ChessPosition> pos = new ArrayList<ChessPosition>();
		int x, y, upBound, lowBound, leftBound, rightBound, value, i, j;
		boolean loMatTuong;
		int dx[] = { 0, 1, -1, 0, 0 };
		int dy[] = { 0, 0, 0, 1, -1 };
		ChessPosition CpTemp;
		x = current.getCol();
		y = current.getRow();
		value = board.getTable()[y][x];

		// khoi tao gioi han di chuyen cho quan tuong
		if (y <= 2) {
			upBound = 0;
			lowBound = 2;
		} else {
			upBound = 7;
			lowBound = 9;
		}
		leftBound = 3;
		rightBound = 5;
		/*
		 * Xet 4 o quanh o tuong, kiem tra hop le, neu hop le thi cho diChu y
		 * xet ca lo mat tuong, face to face
		 */

		for (i = 1; i <= 4; i++) {
			x = current.getCol() + dx[i];
			y = current.getRow() + dy[i];
			if (((x >= leftBound) && (x <= rightBound)) &&
				((y >= upBound) && (y <= lowBound))) {
				if ((board.getTable()[y][x] == 0) ||
					(board.getTable()[y][x] * value < 0)) {
					// Kiem tra xem co lo mat tuong ?
					loMatTuong = false;
					if (upBound == 0) {
						for (j = y + 1; j <= 9; j++) {
							if (board.getTable()[j][x] != 0) {
								if (board.getTable()[j][x] + value == 0) {
									loMatTuong = true;
								}
								break;
							}
						}
					} else {
						for (j = y - 1; j >= 0; j--) {
							if (board.getTable()[j][x] != 0) {
								if (board.getTable()[j][x] + value == 0) {
									loMatTuong = true;
								}
								break;
							}
						}

					}

					if (!loMatTuong) {

						if (board.getTable()[y][x] * value < 0) {
							CpTemp = new ChessPosition(x, y, true);
						} else {
							CpTemp = new ChessPosition(x, y, false);
						}
						pos.add(CpTemp);
					}
				}
			}
		}

		return pos;
	}

	@Override
	public List<ChessPosition> getTargetPos(ChessPosition current) {
		List<ChessPosition> pos = new ArrayList<ChessPosition>();
		int x, y, upBound, lowBound, leftBound, rightBound, value, i, j;
		boolean loMatTuong;
		int dx[] = { 0, 1, -1, 0, 0 };
		int dy[] = { 0, 0, 0, 1, -1 };
		ChessPosition CpTemp;
		x = current.getCol();
		y = current.getRow();
		value = board.getTable()[y][x];

		// khoi tao gioi han di chuyen cho quan tuong
		if (y <= 2) {
			upBound = 0;
			lowBound = 2;
		} else {
			upBound = 7;
			lowBound = 9;
		}
		leftBound = 3;
		rightBound = 5;
		/*
		 * Xet 4 o quanh o tuong, kiem tra hop le, neu hop le thi cho diChu y
		 * xet ca lo mat tuong, face to face
		 */

		for (i = 1; i <= 4; i++) {
			x = current.getCol() + dx[i];
			y = current.getRow() + dy[i];
			if (((x >= leftBound) && (x <= rightBound))
					&& ((y >= upBound) && (y <= lowBound))) {
				if (board.getTable()[y][x] != 0) {
					// Kiem tra xem co lo mat tuong ?
					loMatTuong = false;
					if (upBound == 0) {
						for (j = y + 1; j <= 9; j++) {
							if (board.getTable()[j][x] != 0) {
								if (board.getTable()[j][x] + value == 0) {
									loMatTuong = true;
								}
								break;
							}
						}
					} else {
						for (j = y - 1; j >= 0; j--) {
							if (board.getTable()[j][x] != 0) {
								if (board.getTable()[j][x] + value == 0) {
									loMatTuong = true;
								}
								break;
							}
						}

					}

					if (!loMatTuong) {

						if (board.getTable()[y][x] * value < 0) {
							CpTemp = new ChessPosition(x, y, true);
						} else {
							CpTemp = new ChessPosition(x, y, false);
						}
						pos.add(CpTemp);
					}
				}
			}
		}

		return pos;
	}
}
