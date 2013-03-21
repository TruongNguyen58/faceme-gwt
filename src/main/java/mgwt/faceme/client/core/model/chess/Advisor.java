/**
 * Quan sy
 */
/*
 * Author : HoangNV , 28.9.2012
 */
package mgwt.faceme.client.core.model.chess;

import java.util.ArrayList;
import java.util.List;

import mgwt.faceme.client.core.model.Board;
import mgwt.faceme.client.core.model.ChessPosition;
import mgwt.faceme.client.core.model.Constant;

import com.google.gwt.user.client.ui.Image;



public class Advisor extends Chess {

	
	public Advisor(Board board) {
		super(board);
		imgRed = new Image(Constant.CHESS_DIR + "sydo.png");
		imgBlack = new Image(Constant.CHESS_DIR + "syden.png");
	}

	@Override
	public List <ChessPosition> getPosCanMove(ChessPosition current) {
		List <ChessPosition> pos = new ArrayList<ChessPosition>();
		ChessPosition CpTemp ;
		int x,y,upBound,lowBound,leftBound,rightBound,value;
		int dx[] = {0,1,1,-1,-1};
		int dy[] = {0,1,-1,1,-1};
		//khoi tao gioi han di chuyen cho quan sy
		 
		x = current.getCol() ;
		y = current.getRow() ;
		value = board.getTable()[y][x];

		if (y <=2 ) {
			upBound = 0;
			lowBound= 2;
		} else {
			upBound = 7;
			lowBound = 9 ;
		}
		leftBound = 3 ;
		rightBound = 5;
		
		//Xet 4 o quanh o sy, kiem tra hop le, neu hop le thi cho di
		
		for (int i=1 ; i<=4 ; i++){
			x=current.getCol()+dx[i];
			y=current.getRow()+dy[i];
			if (((x>=leftBound)&&(x<=rightBound))&&((y>=upBound)&&(y<=lowBound))){
				if ((board.getTable()[y][x]==0)||(board.getTable()[y][x]*value < 0)){
					if (board.getTable()[y][x]*value < 0) {
						CpTemp = new ChessPosition(x,y,true);
					} else {
						CpTemp = new ChessPosition(x,y,false);
					}
					pos.add(CpTemp);
				}
				
			}
		}
				return pos;
	}
	
	@Override
	public List <ChessPosition> getTargetPos(ChessPosition current) {
		List <ChessPosition> pos = new ArrayList<ChessPosition>();
		ChessPosition CpTemp ;
		int x,y,upBound,lowBound,leftBound,rightBound,value;
		int dx[] = {0,1,1,-1,-1};
		int dy[] = {0,1,-1,1,-1};
		//khoi tao gioi han di chuyen cho quan sy
		 
		x = current.getCol() ;
		y = current.getRow() ;
		value = board.getTable()[y][x];

		if (y <=2 ) {
			upBound = 0;
			lowBound= 2;
		} else {
			upBound = 7;
			lowBound = 9 ;
		}
		leftBound = 3 ;
		rightBound = 5;
		
		//Xet 4 o quanh o sy, kiem tra hop le, neu hop le thi cho di
		
		for (int i=1 ; i<=4 ; i++){
			x=current.getCol()+dx[i];
			y=current.getRow()+dy[i];
			if (((x>=leftBound)&&(x<=rightBound))&&((y>=upBound)&&(y<=lowBound))){
				if (board.getTable()[y][x]!=0){
					if (board.getTable()[y][x]*value < 0) {
						CpTemp = new ChessPosition(x,y,true);
					} else {
						CpTemp = new ChessPosition(x,y,false);
					}
					pos.add(CpTemp);
				}
				
			}
		}
				return pos;
	}

}
