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
package com.sangnd.gwt.faceme.client.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heroandtn3
 *
 */
public class BoardImpl implements Board {

	private int[][] table;
	private int[][] shortTable;
	private boolean shortTableOutdate;
	private List<ChessPosition[]> historyMove = new ArrayList<ChessPosition[]>();
	private List<int[]> historyChess = new ArrayList<int[]>();
	private int undoIndexPoint = 0;

	/**
	 * 
	 */
	
	public BoardImpl() {
		int[][] table = {
				{-4,-6, -3, -2, -1, -2, -3, -6, -4},
				{0,  0,  0,  0,  0,  0,  0,  0,  0},
				{0, -5,  0,  0,  0,  0,  0, -5,  0},
				{-7, 0, -7,  0, -7,  0, -7,  0, -7},
				{0,  0,  0,  0,  0,  0,  0,  0,  0},
				{0,  0,  0,  0,  0,  0,  0,  0,  0},
				{7,  0,  7,  0,  7,  0,  7,  0,  7},
				{0,  5,  0,  0,  0,  0,  0,  5,  0},
				{0,  0,  0,  0,  0,  0,  0,  0,  0},
				{4,  6,  3,  2,  1,  2,  3,  6,  4} 
			};
		this.table = table;
		this.shortTable = new int[32][3];
		shortTableOutdate = true;
	}
	
	public BoardImpl(int[][] table) {
		this.table = table;
		this.shortTable = new int[32][3];
		
		int count = 0;
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 9; col++) {
				if (table[row][col] != 0) {
					shortTable[count] = new int[] {row, col, table[row][col]};
					count++;
				}
			}
		}
		shortTableOutdate = false;
	}
	
	@Override
	public int[][] getTable() {
		return table;
	}
	
	@Override
	public void setTable(int[][] table) {
		this.table = table;
	}

	@Override
	public void move(ChessPosition oldPos, ChessPosition newPos) {
		int oldChess = table[oldPos.getRow()][oldPos.getCol()];
		int newChess = table[newPos.getRow()][newPos.getCol()];
		
		table[newPos.getRow()][newPos.getCol()] = oldChess;
		table[oldPos.getRow()][oldPos.getCol()] = 0;
		shortTableOutdate = true;
		
		// luu lai history
		historyMove.add(new ChessPosition[] {oldPos, newPos});
		historyChess.add(new int[] {oldChess, newChess});
	}

	@Override
	public int getPiece(ChessPosition pos) {
		return this.table[pos.getRow()][pos.getCol()];
	}

	@Override
	public int[][] getShortTable() {
		if (shortTableOutdate == true) {
			int count = 0;
			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 9; col++) {
					if (table[row][col] != 0) {
						shortTable[count] = new int[] {row, col, table[row][col]};
						count++;
					}
				}
			}
		}
		return this.shortTable;
	}

	@Override
	public void undo(int times, boolean soft) {
		int index = historyMove.size() - 1;
		if (soft) {
			undoIndexPoint = index - times; 
		} else {
			undoIndexPoint = 0;
		}
		
		for (int i = 0; i < times; i++) {
			int oldRow = historyMove.get(index)[0].getRow();
			int oldCol = historyMove.get(index)[0].getCol();
			int newRow = historyMove.get(index)[1].getRow();
			int newCol = historyMove.get(index)[1].getCol();
			
			int oldChess = historyChess.get(index)[0];
			int newChess = historyChess.get(index)[1];
			
			table[oldRow][oldCol] = oldChess;
			table[newRow][newCol] = newChess;
			
			if (!soft) {
				historyMove.remove(index);
				historyChess.remove(index);
			}
			
			index--;
		}
		
		
	}

	@Override
	public void redo(int times, boolean soft) {
		if (undoIndexPoint !=0) {
			int targetIndex = undoIndexPoint + times;
			int maxIndex = historyMove.size() - 1;
			if (targetIndex > maxIndex) {
				targetIndex = maxIndex;
			}
			
			for (int i = undoIndexPoint; i <= targetIndex; i++) {
				int oldRow = historyMove.get(i)[0].getRow();
				int oldCol = historyMove.get(i)[0].getCol();
				int newRow = historyMove.get(i)[1].getRow();
				int newCol = historyMove.get(i)[1].getCol();
				
				int oldChess = historyChess.get(i)[0];
				
				table[oldRow][oldCol] = 0;
				table[newRow][newCol] = oldChess;
			}
			
			if (soft) {
				undoIndexPoint = targetIndex; 
			}
		}
		
	}

}
