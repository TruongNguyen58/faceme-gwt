/**
 * Quan ma
 */
/*
 * Author : HoangNv
 */
package mgwt.faceme.client.core.model.chess;

import java.util.ArrayList;
import java.util.List;

import mgwt.faceme.client.core.model.Board;
import mgwt.faceme.client.core.model.ChessPosition;
import mgwt.faceme.client.core.model.Constant;

import com.google.gwt.user.client.ui.Image;



public class Knight extends Chess {

	public Knight(Board board) {
		super(board);
		imgRed = new Image(Constant.CHESS_DIR + "mado.png");
		imgBlack = new Image(Constant.CHESS_DIR + "maden.png");
	}

	@Override
	public List<ChessPosition> getPosCanMove(ChessPosition current) {
		List<ChessPosition> pos = new ArrayList<ChessPosition>();
		int x,y,upBound,lowBound,leftBound,rightBound,value,cy,cx;
		int Tdx[] = {0,-1,1,2,2,1,-1,-2,-2} ;
		int Tdy[] = {0,-2,-2,-1,1,2,2,1,-1} ;
		int dx[] = {0,0,1,0,-1};
		int dy[] = {0,-1,0,1,0};
		ChessPosition CpTemp ;
		x = current.getCol() ;
		y = current.getRow() ;
		value = board.getTable()[y][x];

		//khoi tao gioi han di chuyen cho quan sy
		 
		x = current.getCol() ;
		y = current.getRow() ;
		value = board.getTable()[y][x];

		upBound = 0; lowBound = 9;
		leftBound = 0 ; rightBound = 8;
		
		//Xet 4 o quanh o sy, kiem tra hop le, neu hop le thi cho di
		
		for (int i=1 ; i<=8 ; i++){
			x=current.getCol()+Tdx[i];
			y=current.getRow()+Tdy[i];
			cx=current.getCol() + dx[(i+1)/2];
			cy = current.getRow() + dy[(i+1)/2];
			if (((x>=leftBound)&&(x<=rightBound))&&((y>=upBound)&&(y<=lowBound))){
				if (((board.getTable()[y][x]==0)||(board.getTable()[y][x]*value < 0))&&(board.getTable()[cy][cx]==0)){
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
	public List<ChessPosition> getTargetPos(ChessPosition current) {
		List<ChessPosition> pos = new ArrayList<ChessPosition>();
		int x,y,upBound,lowBound,leftBound,rightBound,value,cy,cx;
		int Tdx[] = {0,-1,1,2,2,1,-1,-2,-2} ;
		int Tdy[] = {0,-2,-2,-1,1,2,2,1,-1} ;
		int dx[] = {0,0,1,0,-1};
		int dy[] = {0,-1,0,1,0};
		ChessPosition CpTemp ;
		x = current.getCol() ;
		y = current.getRow() ;
		value = board.getTable()[y][x];

		//khoi tao gioi han di chuyen cho quan sy
		 
		x = current.getCol() ;
		y = current.getRow() ;
		value = board.getTable()[y][x];

		upBound = 0; lowBound = 9;
		leftBound = 0 ; rightBound = 8;
		
		//Xet 4 o quanh o sy, kiem tra hop le, neu hop le thi cho di
		
		for (int i=1 ; i<=8 ; i++){
			x=current.getCol()+Tdx[i];
			y=current.getRow()+Tdy[i];
			cx=current.getCol() + dx[(i+1)/2];
			cy = current.getRow() + dy[(i+1)/2];
			if (((x>=leftBound)&&(x<=rightBound))&&((y>=upBound)&&(y<=lowBound))){
				if ((board.getTable()[y][x]!=0)&&(board.getTable()[cy][cx]==0)){
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
