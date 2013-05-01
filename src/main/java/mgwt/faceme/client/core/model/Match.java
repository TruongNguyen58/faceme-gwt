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
package mgwt.faceme.client.core.model;

import java.util.ArrayList;
import java.util.List;

import mgwt.faceme.client.core.control.Computer;
import mgwt.faceme.client.core.control.ComputerMinmax;
import mgwt.faceme.client.core.model.chess.Advisor;
import mgwt.faceme.client.core.model.chess.Bishop;
import mgwt.faceme.client.core.model.chess.Cannon;
import mgwt.faceme.client.core.model.chess.Chess;
import mgwt.faceme.client.core.model.chess.King;
import mgwt.faceme.client.core.model.chess.Knight;
import mgwt.faceme.client.core.model.chess.Pawn;
import mgwt.faceme.client.core.model.chess.Rook;
import mgwt.faceme.client.view.GamePanel;


/**
 * @author heroandtn3
 * @date Jan 7, 2013
 */
public class Match {
	
	private GamePanel gamePanel;

	// game attributes
	private Level level;
	private GameState state;
	private Side currentSide;
	private Board board;
	private Chess[] chess;
	private Computer computer;
	private final boolean playWithCom = false; // co dinh
	
	private int[][] table = {
			{6,  4,  3,  2,  7,  2,  3,  4,  6},
			{0,  0,  0,  0,  0,  0,  0,  0,  0},
			{0,  5,  0,  0,  0,  0,  0,  5,  0},
			{1,  0,  1,  0,  1,  0,  1,  0,  1},
			{0,  0,  0,  0,  0,  0,  0,  0,  0},
			{0,  0,  0,  0,  0,  0,  0,  0,  0},
			{-1, 0, -1,  0, -1,  0, -1,  0, -1},
			{0, -5,  0,  0,  0,  0,  0, -5,  0},
			{0,  0,  0,  0,  0,  0,  0,  0,  0},
			{-6,-4, -3, -2, -7, -2, -3, -4, -6} 
		};
	
	// game state attribues
	private List<ChessPosition> posCanMove;
	// vi tri cu va moi, dung de luu vet, undo,redo,...
	private ChessPosition oldPos, newPos;
	


	/**
	 * 
	 */
	public Match() {
		board = new Board(table);
		initChess();
		oldPos = newPos = null;
		posCanMove = new ArrayList<ChessPosition>();
		state = GameState.PLAYING;
		currentSide = Side.BLACK;
		initComputer();
	}
	
	private void initChess() {
		chess = new Chess[8];
		chess[0] = null;
		chess[1] = new Pawn(board);
		chess[2] = new Advisor(board);
		chess[3] = new Bishop(board);
		chess[4] = new Knight(board);
		chess[5] = new Cannon(board);
		chess[6] = new Rook(board);
		chess[7] = new King(board);
		
		
	}
	
	private void initComputer() {
		if (playWithCom) {
			computer = new ComputerMinmax(this,
					(currentSide == Side.BLACK) ? Side.RED : Side.BLACK);
		}
	}
	
	/**
	 * Ham thuc hien di chuyen quan co tu vi tri cu sang vi tri moi
	 * @param oldPos
	 * @param newPos
	 */
	public void move(ChessPosition oldPos, ChessPosition newPos) {
		int[][] mt = board.getTable();
		
		// thay gia tri newPos bang gia tri o oldPos
		int tmp = mt[oldPos.getRow()][oldPos.getCol()];
		mt[oldPos.getRow()][oldPos.getCol()] = 0; // vi tri cu chuyen ve 0
		mt[newPos.getRow()][newPos.getCol()] = tmp;
		
		// clear posCanMove
		this.posCanMove.clear();
		
		// switch player
		currentSide = (currentSide == Side.BLACK) ?
				Side.RED : Side.BLACK;
		
		// danh dau la da thay doi va gui yeu cau update den cac observers
		gamePanel.move(oldPos, newPos);
	}
	
	private void updatePosCanMove() {
		if (oldPos == null) {
			posCanMove.clear();
		} else {
			int row = oldPos.getRow();
			int col = oldPos.getCol();
			int value = Math.abs(table[row][col]);
			ChessPosition currentPos = 
					new ChessPosition(row, col);
			posCanMove = chess[value].getPosCanMove(currentPos);
		}
		// thong bao cho view update
		gamePanel.select(oldPos, posCanMove);
	}
	
	private boolean isChooseForMove(ChessPosition pos) {
		int value = table[pos.getRow()][pos.getCol()];
		if ((currentSide == Side.RED && value > 0) ||
			(currentSide == Side.BLACK && value < 0)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setPos(ChessPosition pos) {
		if (isChooseForMove(pos)) { // chon quan de di chuyen
			// loai bo pos khong hop le
			if (table[pos.getRow()][pos.getCol()] == 0) return;
			
			oldPos = pos; // neu hop le thi danh dau oldPos da chon
			newPos = null; // va bo danh dau newPos
			
			// update pos can move
			updatePosCanMove();
		} else if (oldPos != null){ // neu da co pos duoc chon
			// thi di chuyen quan den vi tri moi
			
			// vi tri di chuyen den phai nam trong posCanMove
			boolean validPos = false;
			for (ChessPosition posCM : posCanMove) {
				if (posCM.getRow() == pos.getRow() &&
					posCM.getCol() == pos.getCol()) {
					validPos = true;
					break;
				}
			}
			if (validPos == false) return;
		
			// vi tri hop le thi tien hanh di chuyens
			newPos = pos;
			this.move(oldPos, newPos);
			
			// may tinh di chuyen
			if (playWithCom) {
				// ai thread
				synchronized (computer) {
/*					computer.notify();*/
				}
			}
		}
		
	}

	/* get, set -------------------------------------------------------------*/
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

	public Chess[] getChess() {
		return chess;
	}

	public void setChess(Chess[] chess) {
		this.chess = chess;
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

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public Side getCurrentSide() {
		return currentSide;
	}

	public void setCurrentSide(Side currentSide) {
		this.currentSide = currentSide;
	}
}
