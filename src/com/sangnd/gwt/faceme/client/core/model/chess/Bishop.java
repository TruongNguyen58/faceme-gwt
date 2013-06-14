/**
 * Quan tinh
 */
package com.sangnd.gwt.faceme.client.core.model.chess;

import java.util.ArrayList;
import java.util.List;

import com.sangnd.gwt.faceme.client.core.model.Board;
import com.sangnd.gwt.faceme.client.core.model.ChessPosition;



public class Bishop implements Chess {
	

	private Board board;

	public Bishop(Board board) {
		this.board = board;
	}

	@Override
	public List<ChessPosition> getPosCanMove(ChessPosition current) {
		List<ChessPosition> pos = new ArrayList<ChessPosition>();
		int x,y,upBound,lowBound,leftBound,rightBound,value;
		int dx[] = {0,2,2,-2,-2};
		int dy[] = {0,2,-2,2,-2};
		int cdx[] = {0,1,1,-1,-1};
		int cdy[] = {0,1,-1,1,-1};
		ChessPosition CpTemp  ;
		int tempX=0,tempY=0;
		/*
		 * khoi tao gioi han di chuyen cho quan sy
		 */
		
		x = current.getCol() ;
		y = current.getRow() ;
		value = board.getTable()[y][x];
		if (y<=4) {
			upBound = 0;
			lowBound= 4;
		} else {
			upBound = 5;
			lowBound = 9 ;
		}
		leftBound = 0 ;
		rightBound = 8;
		/*
		 * Xet 4 o quanh o tinh, kiem tra hop le, neu hop le thi cho di
		 */
		for (int i=1 ; i<=4 ; i++){
			x=current.getCol()+dx[i];
			y=current.getRow()+dy[i];
			tempX=current.getCol() + cdx[i];
			tempY=current.getRow() + cdy[i];
			if (((x>=leftBound)&&(x<=rightBound))&&((y>=upBound)&&(y<=lowBound))){
				if ((board.getTable()[y][x]==0)||(board.getTable()[y][x]*value < 0)){
					if (board.getTable()[tempY][tempX]==0) {
						if (board.getTable()[y][x]*value < 0) {
							CpTemp = new ChessPosition(x,y,true);
						} else {
							CpTemp = new ChessPosition(x,y,false);
						}
						pos.add(CpTemp);
					}
					
				}
			}
		}
		// TODO Auto-generated method stub
		return pos;
	}
	
	@Override
	public List<ChessPosition> getTargetPos(ChessPosition current) {
		List<ChessPosition> pos = new ArrayList<ChessPosition>();
		int x,y,upBound,lowBound,leftBound,rightBound,value;
		int dx[] = {0,2,2,-2,-2};
		int dy[] = {0,2,-2,2,-2};
		int cdx[] = {0,1,1,-1,-1};
		int cdy[] = {0,1,-1,1,-1};
		ChessPosition CpTemp  ;
		int tempX=0,tempY=0;
		/*
		 * khoi tao gioi han di chuyen cho quan sy
		 */
		
		x = current.getCol() ;
		y = current.getRow() ;
		value = board.getTable()[y][x];
		if (y<=4) {
			upBound = 0;
			lowBound= 4;
		} else {
			upBound = 5;
			lowBound = 9 ;
		}
		leftBound = 0 ;
		rightBound = 8;
		/*
		 * Xet 4 o quanh o tinh, kiem tra hop le, neu hop le thi cho di
		 */
		for (int i=1 ; i<=4 ; i++){
			x=current.getCol()+dx[i];
			y=current.getRow()+dy[i];
			tempX=current.getCol() + cdx[i];
			tempY=current.getRow() + cdy[i];
			if (((x>=leftBound)&&(x<=rightBound))&&((y>=upBound)&&(y<=lowBound))){
				if (board.getTable()[y][x]!=0){
					if (board.getTable()[tempY][tempX]==0) {
						if (board.getTable()[y][x]*value < 0) {
							CpTemp = new ChessPosition(x,y,true);
						} else {
							CpTemp = new ChessPosition(x,y,false);
						}
						pos.add(CpTemp);
					}
					
				}
			}
		}
		// TODO Auto-generated method stub
		return pos;
	}

}
