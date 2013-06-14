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

/**
 * @author heroandtn3
 * @date Jan 7, 2013
 */
public interface Board {



	/**
	 * 
	 */
	
	public void move(ChessPosition oldPos, ChessPosition newPos);
	
	/**
	 * Undo nhung nuoc da di
	 * @param times so luot di quan can undo
	 * @param soft true khi undo chi la quay lai tam thoi va khong anh
	 * huong den trang thai ban co
	 */
	public void undo(int times, boolean soft);
	
	/**
	 * Tuong do undo
	 * @param times
	 * @param soft
	 */
	public void redo(int times, boolean soft);
	
	public int getPiece(ChessPosition pos);

	public int[][] getShortTable();

	public int[][] getTable();

	public void setTable(int[][] table);
}
