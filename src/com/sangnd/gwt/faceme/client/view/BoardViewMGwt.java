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
package com.sangnd.gwt.faceme.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.googlecode.mgwt.ui.client.widget.touch.TouchPanel;
import com.sangnd.gwt.faceme.client.core.model.Board;
import com.sangnd.gwt.faceme.client.core.model.ChessPosition;
import com.sangnd.gwt.faceme.client.core.model.Constant;
import com.sangnd.gwt.faceme.client.core.model.GameState;
import com.sangnd.gwt.faceme.client.event.ChessSelectEvent;
import com.sangnd.gwt.faceme.client.event.ChessSelectHandler;
import com.sangnd.gwt.faceme.client.event.HasChessSelectHandler;

/**
 * @author heroandtn3
 * 
 */
public class BoardViewMGwt extends TouchPanel implements BoardView,
		HasChessSelectHandler {

	private ChessShape[][] chessShapes = new ChessShape[10][9];
	private AbsolutePanel boardPanel;
	private ChessPosition posSelected;
	private ChessPosition posMovedTo;
	private List<ChessPosition> oldPosCanMv;
	private NoticationShape notiShape;
	private ChessShape csTmp;
	private MoveAnimation animation;
	private boolean warnKing;
	private boolean matchFinish;
	private int chessRadius;
	private double leftPadding;
	private double topPadding;
	private double boxWidth;
	private double boxHeight;
	private double boardWidth;
	private double boardHeight;

	public BoardViewMGwt() {
		init();
		initGUI();
	}

	private void init() {
		oldPosCanMv = new ArrayList<ChessPosition>();
		animation = new MoveAnimation();
		warnKing = false;
		matchFinish = false;
	}

	private void initGUI() {

		this.getElement().getStyle().setProperty("padding", "10px");
		this.getElement().getStyle().setProperty("margin", "10px auto");

		boardPanel = new AbsolutePanel();
		this.add(boardPanel);

		boardPanel.addStyleName("GamePanel");
		boardPanel.getElement().getStyle().setProperty("padding", "0px");
		boardPanel.getElement().getStyle().setProperty("margin", "0px");
		boardPanel.getElement().getStyle().setProperty("width", "100%");
		boardPanel.getElement().getStyle().setProperty("height", "100%");

		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 9; col++) {
				chessShapes[row][col] = new ChessShape();
				boardPanel.add(chessShapes[row][col]);
			}
		}

		csTmp = new ChessShape();
		boardPanel.add(csTmp);

		notiShape = new NoticationShape();
		boardPanel.add(notiShape);

		this.addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent e) {

				int x = e.getStartX()
						- (int) (Constant.SCREEN_RATIO * 30)
						- boardPanel.getAbsoluteLeft();
				int y = e.getStartY()
						- (int) (Constant.SCREEN_RATIO * 24)
						- boardPanel.getAbsoluteTop();

				if (x < 0)
					x = 0;
				if (y < 0)
					y = 0;
				ChessPosition pos = convertToChessPos(x, y);

				if (pos != null) {
					fireEvent(new ChessSelectEvent(pos));
				}

			}
		});

		resizeScreen();
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				resizeScreen();
			}
		});
	}

	private void resizeScreen() {
		updateScreenRatio();
		reDrawBoard();
		reDrawChess();
	}

	private void updateScreenRatio() {
		int w = Window.getClientWidth();
		int h = Window.getClientHeight() - 80;
		double rw = (double) w / Constant.SCREEN_WIDTH;
		double rh = (double) h / Constant.SCREEN_HEIGHT;
		if (rw < rh) {
			Constant.SCREEN_RATIO = rw;
		} else {
			Constant.SCREEN_RATIO = rh;
		}
		
		boardWidth = Constant.SCREEN_WIDTH * Constant.SCREEN_RATIO;
		boardHeight = Constant.SCREEN_HEIGHT * Constant.SCREEN_RATIO;
		
		// 21 la ban kinh chuan cua quan co
		chessRadius = (int) (Constant.SCREEN_RATIO * 21);
		
		// 30 va 25 lan luot la padding chuan cua ban co
		leftPadding = Constant.SCREEN_RATIO * 30;
		topPadding = Constant.SCREEN_RATIO * 25;

		// 50 va 47 lan luot la kich thuoc chuan cua o co
		boxWidth = Constant.SCREEN_RATIO * 50;
		boxHeight = Constant.SCREEN_RATIO * 47;
	}
	
	private void reDrawBoard() {
		this.setWidth(boardWidth + "px");
		this.setHeight(boardHeight + "px");
		boardPanel.getElement().getStyle()
				.setProperty("backgroundSize", boardWidth + "px " + boardHeight + "px");

		notiShape.getElement().getStyle()
				.setProperty("left", (boardWidth - 200) / 2 + "px");
		notiShape.getElement().getStyle()
				.setProperty("top", (boardHeight - 40) / 2 + "px");
	}

	private void reDrawChess() {
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 9; col++) {
				int[] pos = convertToXY(new ChessPosition(row, col));
				chessShapes[row][col].setPos(pos[0] - chessRadius, pos[1]
						- chessRadius);
			}
		}

	}

	@Override
	public void renderBoard(final Board board) {
		int[][] table = board.getTable();
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 9; col++) {
				int value = table[row][col];
				chessShapes[row][col].setType(value);
				int[] pos = convertToXY(new ChessPosition(row, col));
				chessShapes[row][col].setPos(pos[0] - chessRadius, pos[1]
						- chessRadius);
			}
		}
	}

	/**
	 * Chuyen ChessPosition sang toa do x, y
	 * 
	 * @param row
	 * @param col
	 * @return: mang 1 chieu chua 2 phan tu: [0]: x [1]: y
	 */
	private int[] convertToXY(ChessPosition pos) {
		int x = (int)((boardWidth - 2*leftPadding + 2) * pos.getCol()/8 + leftPadding);
		int y = (int)((boardHeight - 2*topPadding + 2) * pos.getRow()/9 + topPadding);
		return (new int[] { x, y });
	}

	/**
	 * Ham convert tu x, y sang ChessPosition
	 * 
	 * @param x
	 * @param y
	 * @return: Neu x, y hop le thi tra ve 1 ChessPosition 
	 * Neu khong thi tra ve: null
	 */
	public ChessPosition convertToChessPos(int x, int y) {
		int row = (int) ((y + chessRadius) / boxHeight);
		int col = (int) ((x + chessRadius) / boxWidth);
		if (row >= 0 && row < 10 && col >= 0 && col < 9) {
			return (new ChessPosition(row, col));
		} else {
			return null;
		}
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void renderMoveChess(final ChessPosition oldPos,
			final ChessPosition newPos) {

		animation.move(oldPos, newPos);

	}

	/* Animation--------------------------------------------------------------- */
	class MoveAnimation extends Animation {
		private int startX, startY;
		private int tarrgetX, tarrgetY;

		ChessShape csOld = null;
		ChessShape csNew = null;

		@Override
		protected void onUpdate(double progress) {
			updateCurrentPos(progress);

		}

		private void updateCurrentPos(double progress) {
			int x = (int) (startX + (progress * (tarrgetX - startX)));
			int y = (int) (startY + (progress * (tarrgetY - startY)));
			csNew.setPos(x, y);
		}

		public void move(ChessPosition oldPos, ChessPosition newPos) {
			clearPosCanMove();

			int row1 = oldPos.getRow();
			int col1 = oldPos.getCol();
			int row2 = newPos.getRow();
			int col2 = newPos.getCol();

			int[] pos = convertToXY(oldPos);

			startX = pos[0] - chessRadius;
			startY = pos[1] - chessRadius;

			pos = convertToXY(newPos);
			tarrgetX = pos[0] - chessRadius;
			tarrgetY = pos[1] - chessRadius;

			csOld = chessShapes[row1][col1];
			csNew = chessShapes[row2][col2];

			csTmp.setVisible(true);
			csTmp.setPos(tarrgetX, tarrgetY);
			csTmp.setSelected(true);
			csTmp.setType(csNew.getType());

			csNew.setPos(startX, startY);
			csNew.setType(csOld.getType());
			csOld.setType(0);
			markPos(newPos, true);
			posMovedTo = newPos;

			run(700);
		}

		@Override
		public void onComplete() {
			super.onComplete();
			csTmp.setVisible(false);
			if (warnKing) {
				notiShape.notice("chieu tuong...");
				warnKing = false;
			} else {
				notiShape.notice("");
			}

			if (matchFinish) {
				Dialogs.alert("Hết cờ!", "Bạn hoặc đối phương đã dành chiến thắng!", null);
			}
		}
	}

	@Override
	public HasChessSelectHandler getWidgetSelectChess() {
		return this;
	}

	@Override
	public HandlerRegistration addChessSelectHandler(ChessSelectHandler handler) {
		return addHandler(handler, ChessSelectEvent.TYPE);
	}

	@Override
	public void renderChessSelect(ChessPosition pos) {
		markPos(posSelected, false);
		posSelected = null;
		markPos(posMovedTo, false);
		posMovedTo = null;

		markPos(pos, true);
		posSelected = pos;

	}

	@Override
	public void renderPosCanMove(List<ChessPosition> posCanMv) {
		clearPosCanMove();

		for (ChessPosition pos : posCanMv) {
			chessShapes[pos.getRow()][pos.getCol()].setCanMove(true);
			oldPosCanMv.add(pos);
		}

	}

	private void clearPosCanMove() {
		for (ChessPosition pos : oldPosCanMv) {
			chessShapes[pos.getRow()][pos.getCol()].setCanMove(false);
		}
		oldPosCanMv.clear();
	}

	private void markPos(ChessPosition pos, boolean mark) {
		if (pos != null) {
			chessShapes[pos.getRow()][pos.getCol()].setSelected(mark);
		}
	}

	@Override
	public void renderWarnKing(boolean warning) {
		warnKing = warning;
	}

	@Override
	public void renderMatchFinish(GameState state) {
		if (state == GameState.ENERMY_WON || state == GameState.FRIEND_WON) {
			matchFinish = true;
		}

	}

	@Override
	public void clearContent() {
		warnKing = false;
		matchFinish = false;

		markPos(posSelected, false);
		posSelected = null;
		markPos(posMovedTo, false);
		posMovedTo = null;

		notiShape.notice("");
	}

}
