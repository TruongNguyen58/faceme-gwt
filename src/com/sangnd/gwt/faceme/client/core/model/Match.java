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
package com.sangnd.gwt.faceme.client.core.model;

import java.util.ArrayList;
import java.util.List;

import com.sangnd.gwt.faceme.client.core.control.Computer;
import com.sangnd.gwt.faceme.client.core.control.ComputerMinmax;
import com.sangnd.gwt.faceme.client.core.control.MoveGeneratorNormal;
import com.sangnd.gwt.faceme.client.core.model.chess.Advisor;
import com.sangnd.gwt.faceme.client.core.model.chess.Bishop;
import com.sangnd.gwt.faceme.client.core.model.chess.Cannon;
import com.sangnd.gwt.faceme.client.core.model.chess.Chess;
import com.sangnd.gwt.faceme.client.core.model.chess.King;
import com.sangnd.gwt.faceme.client.core.model.chess.Knight;
import com.sangnd.gwt.faceme.client.core.model.chess.Pawn;
import com.sangnd.gwt.faceme.client.core.model.chess.Rook;

/**
 * @author heroandtn3
 * @date Jan 7, 2013
 */
public class Match {

	// game attributes
	private Level level;
	private GameState state;
	private Side currentSide;
	private Board board;
	public final static Chess[] CHESSs = new Chess[8];
	private boolean playWithCom = false;

	// game state attribues
	private List<ChessPosition> posCanMove;
	// vi tri cu va moi, dung de luu vet, undo,redo,...
	private ChessPosition oldPos, newPos;
	private MoveGeneratorNormal moveGenerator = new MoveGeneratorNormal();
	private boolean warnKing;
	private Computer computer;

	/**
	 * 
	 */
	public Match() {
		board = new BoardImpl();
		oldPos = newPos = null;
		posCanMove = new ArrayList<ChessPosition>();
		state = GameState.PLAYING;
		currentSide = Side.FRIEND;
		initChess();
		initComputer();
	}

	private void initChess() {
		CHESSs[0] = null;
		CHESSs[1] = new King(board);
		CHESSs[2] = new Advisor(board);
		CHESSs[3] = new Bishop(board);
		CHESSs[4] = new Rook(board);
		CHESSs[5] = new Cannon(board);
		CHESSs[6] = new Knight(board);
		CHESSs[7] = new Pawn(board);

	}

	private void initComputer() {
		computer = new ComputerMinmax(this,
				(currentSide == Side.ENERMY) ? Side.FRIEND : Side.ENERMY);
	}

	/**
	 * Ham thuc hien di chuyen quan co tu vi tri cu sang vi tri moi
	 * 
	 * @param oldPos
	 * @param newPos
	 */
	public void move(ChessPosition oldPos, ChessPosition newPos) {
		board.move(oldPos, newPos);
		this.posCanMove.clear();

		updateGame();
	}

	private void updateGame() {

		warnKing = checkWarnKing(currentSide);
		state = checkFinishMatch();

		// switch player
		currentSide = (currentSide == Side.ENERMY) ? Side.FRIEND : Side.ENERMY;
	}

	private GameState checkFinishMatch() {
		Side nextSide = (currentSide == Side.ENERMY) ? Side.FRIEND
				: Side.ENERMY;

		// het co khi: sau khi di chuyen ma tuong van bi chieu
		if (checkWarnKing(nextSide)) {
			System.out.println("TH1");
			if (nextSide == Side.ENERMY) {
				return GameState.ENERMY_WON;
			} else {
				return GameState.FRIEND_WON;
			}
		}
		
		// het co khi: sau khi di chuyen thi bi lo mat tuong
		// tim vi tri tuong cua 2 ben
		int enermyKingRow = -1, enermyKingCol = -1;
		for (int row = 0; row <= 2; row++) {
			for (int col = 3; col <= 5; col++) {
				if (board.getTable()[row][col] == -1) {
					enermyKingRow = row;
					enermyKingCol = col;
					break;
				}
			}
		}
		
		int friendKingRow = -1, friendKingCol = -1;
		for (int row = 7; row <= 9; row++) {
			for (int col = 3; col <= 5; col++) {
				if (board.getTable()[row][col] == 1) {
					friendKingRow = row;
					friendKingCol = col;
				}
			}
		}
		
		System.out.println("Vi tri tuong: " + enermyKingRow + enermyKingCol + friendKingRow + friendKingCol);
		boolean kingMeeting = true;
		if (enermyKingRow != -1 && friendKingRow != -1) {
			if (enermyKingCol == friendKingCol) {
				int col = enermyKingCol;
				for (int row = enermyKingRow + 1; row < friendKingRow; row++) {
					if (board.getTable()[row][col] != 0) {
						kingMeeting = false;
						break;
					}
				}
			}
		}
		if (kingMeeting) {
			System.out.println("Lo mat tuong :(");
			if (nextSide == Side.ENERMY) {
				return GameState.ENERMY_WON;
			} else {
				return GameState.FRIEND_WON;
			}
		}
		
		// het co khi: doi phuong khong co nuoc nao de di
		List<ChessPosition[]> allPos = moveGenerator.getMoves(board, nextSide);
		if (allPos.size() == 0) {
			System.out.println("TH2");
			if (nextSide == Side.ENERMY) {
				return GameState.FRIEND_WON;
			} else {
				return GameState.ENERMY_WON;
			}
		}

		// het co khi: doi phuong di moi nuoc van khong thoat khoi chieu tuong
		if (warnKing) {
			boolean hasEscapeMove = false;
			for (ChessPosition[] mv : allPos) {
				board.move(mv[0], mv[1]);

				List<ChessPosition[]> allPosCanMv = moveGenerator.getMoves(
						board, currentSide);
				// chi can 1 nuoc di de chong chieu tuong la du
				if (!checkWarnKing(board.getTable(), allPosCanMv, currentSide)) {
					hasEscapeMove = true;
					board.undo(1, false);
					break;
				}

				board.undo(1, false);
			}

			if (!hasEscapeMove) {
				System.out.println("TH3");
				if (nextSide == Side.ENERMY) {
					return GameState.FRIEND_WON;
				} else {
					return GameState.ENERMY_WON;
				}
			}
		}

		return GameState.PLAYING;

	}

	/**
	 * Kiem tra xem `side` co dang chieu tuong khong
	 * 
	 * @param side
	 *            side kiem tra
	 * @return
	 */
	private boolean checkWarnKing(Side side) {
		return checkWarnKing(null, null, side);
	}
	
	private boolean checkWarnKing(int[][] table, List<ChessPosition[]> allPos, Side currentSide) {
		if (table == null) {
			table = board.getTable();
		}

		if (allPos == null) {
			allPos = moveGenerator.getMoves(board, currentSide);
		}

		// tim vi tri tuong cua doi phuong
		int startRow = 0, endRow = 2;
		int startCol = 3, endCol = 5;
		int kingCode = -1;
		int kingRow = -1, kingCol = -1;
		
		if (currentSide== Side.ENERMY) {
			startRow = 7; endRow = 9;
			kingCode = 1;
		}
		for (int row = startRow; row <= endRow; row++) {
			for (int col = startCol; col <= endCol; col++) {
				if (kingCode == table[row][col]) {
					kingRow = row;
					kingCol = col;
					break;
				}
			}
		}

		if (kingRow != -1) {
			for (ChessPosition[] pos : allPos) {
				if (pos[1].getRow() == kingRow && pos[1].getCol() == kingCol) {
					return true;
				}
			}
		}

		return false;

	}

	private void updatePosCanMove() {
		if (oldPos == null) {
			posCanMove.clear();
		} else {
			int row = oldPos.getRow();
			int col = oldPos.getCol();
			int value = Math.abs(board.getPiece(oldPos));
			ChessPosition currentPos = new ChessPosition(row, col);
			posCanMove = CHESSs[value].getPosCanMove(currentPos);
		}
	}

	private boolean isChooseForMove(ChessPosition pos) {
		int piece = board.getPiece(pos);
		if ((currentSide == Side.FRIEND && piece > 0)
				|| (currentSide == Side.ENERMY && piece < 0)) {
			return true;
		} else {
			return false;
		}
	}

	public void setPos(ChessPosition pos) {
		if (state != GameState.PLAYING) {
			System.out.println("finish game: " + state);
			return;
		}
		if (isChooseForMove(pos)) { // chon quan de di chuyen
			// loai bo pos khong hop le
			if (board.getPiece(pos) == 0)
				return;

			oldPos = pos; // neu hop le thi danh dau oldPos da chon
			newPos = null;
			updatePosCanMove();

		} else if (oldPos != null) { // neu da co pos duoc chon
			// thi di chuyen quan den vi tri moi

			// vi tri di chuyen den phai nam trong posCanMove
			boolean validPos = false;
			for (ChessPosition posCM : posCanMove) {
				if (posCM.getRow() == pos.getRow()
						&& posCM.getCol() == pos.getCol()) {
					validPos = true;
					break;
				}
			}
			if (validPos == false)
				return;

			// vi tri hop le thi tien hanh di chuyen
			newPos = pos;
			this.move(oldPos, newPos);
		}

	}

	/* get, set ------------------------------------------------------------- */
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public List<ChessPosition> getPosCanMove() {
		return posCanMove;
	}

	public ChessPosition getOldPos() {
		return oldPos;
	}

	public ChessPosition getNewPos() {
		return newPos;
	}

	public void clearAfterMove() {
		oldPos = null;
		newPos = null;
	}

	public Side getCurrentSide() {
		return currentSide;
	}

	public boolean isWarnKing() {
		return warnKing;
	}

	public boolean isPlayWithCom() {
		return playWithCom;
	}

	public void setPlayWithCom(boolean playWithCom) {
		this.playWithCom = playWithCom;
	}

	public Computer getComputer() {
		return computer;
	}
}
