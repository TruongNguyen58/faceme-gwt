/**
 * 
 */
package mgwt.faceme.client.view;

import java.util.List;

import mgwt.faceme.client.core.model.ChessPosition;
import mgwt.faceme.client.core.model.Constant;
import mgwt.faceme.client.core.model.Match;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;


/**
 * @author heroandtn3
 *
 */
public class GamePanel extends AbsolutePanel {
	
	private Match match = new Match();
	private int[][] table = match.getBoard().getTable();
	private ChessShape[] chessShapes = new ChessShape[32];
	private int[][] csPos = new int[10][9];
	
	/**
	 * 
	 */
	public GamePanel() {
		match.setGamePanel(this);
		init();
		initGUI();
		drawChess();
	}
	
	private void init() {
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 9; col++) {
				csPos[row][col] = -1;
			}
		}
	}
	
	private void initGUI() {
		addStyleName("GamePanel");
	}
	
	private void drawChess() {
		int count = 0;
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 9; col++) {
				int value = table[row][col];
				if (value != 0) { // kiem tra xem co quan co khong
					// neu co thi tien hanh ve
					Image img = match.getChess()[Math.abs(value)]
							.getShape(value); // lay hinh anh cua quan co de ve
					
					if (img != null) {
						// chuyen sang toa do x, y de ve
						int[] pos = convertToXY(new ChessPosition(row, col));
						ChessShape cs = new ChessShape(img);
						
						add(cs);

						setWidgetPosition(cs, pos[0] - 21, pos[1] - 21);
						
						csPos[row][col] = count;
						chessShapes[count] = cs;
						count++;
					}
				}
			}
		}
	}
	
	public void reDrawChess() {
		int count = 0;
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 9; col++) {
				int value = table[row][col];
				if (value != 0) { // kiem tra xem co quan co khong
						// chuyen sang toa do x, y
						int[] pos = convertToXY(new ChessPosition(row, col));
						setWidgetPosition(chessShapes[count], pos[0] - 21, pos[1] - 21);
						count++;
					}
				}
			}
	}
	
	public void move(ChessPosition oldPos, ChessPosition newPos) {
		new MoveAnimation().move(oldPos, newPos);;
	}
	
	public void select(ChessPosition pos, List<ChessPosition> posCanMove) {
		ChessShape csSelected = null;
		// find csSelected
		if (csPos[pos.getRow()][pos.getCol()] != -1) {
			csSelected = chessShapes[csPos[pos.getRow()][pos.getCol()]];
		}
		if (csSelected == null) return;
		csSelected.setSelected();
	}
	
	/*some utilities---------------------------------------------------------*/

	/**
	 * Chuyen ChessPosition sang toa do x, y
	 * @param row
	 * @param col
	 * @return: mang 1 chieu chua 2 phan tu: 
	 * [0]: x
	 * [1]: y 
	 */
	private int[] convertToXY(ChessPosition pos) {
		int x = (int)((float) Constant.SCREEN_RATIO *30) + pos.getCol() * (int)((float) Constant.SCREEN_RATIO *50);
		int y = (int)((float) Constant.SCREEN_RATIO *25) + pos.getRow() * (int)((float) Constant.SCREEN_RATIO *47);
		return (new int[] { x, y });
	}
	
	/**
	 * Ham convert tu x, y sang ChessPosition
	 * @param x
	 * @param y
	 * @return: Neu x, y hop le thi tra ve 1 ChessPosition
	 * 			Neu khong thi tra ve: null
	 */
	public ChessPosition convertToChessPos(int x, int y) {
		int row = (y - 25 + 21) / 50;
		int col = (x - 30 + 21) / 53;
		if (row >= 0 && row < 10 && col >= 0 && col < 9) {
			return (new ChessPosition(row, col));
		} else {
			return null;
		}
	}
	
	/*Animation---------------------------------------------------------------*/
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
			setWidgetPosition(csOld, x, y);
		}
		
		public void move(ChessPosition oldPos, ChessPosition newPos) {
			// find oldPos widget
			csOld = chessShapes[csPos[oldPos.getRow()][oldPos.getCol()]];
			
			if (csOld == null) return;
			
			// find newPos widget
			if (csPos[newPos.getRow()][newPos.getCol()] != -1) {
				csNew = chessShapes[csPos[newPos.getRow()][newPos.getCol()]];
			}
			
			csPos[newPos.getRow()][newPos.getCol()] = 
					csPos[oldPos.getRow()][oldPos.getCol()];
			csPos[oldPos.getRow()][oldPos.getCol()] = -1;
			
			
			int[] pos = convertToXY(oldPos);
			startX = pos[0] - 21;
			startY = pos[1] - 21;

			pos = convertToXY(newPos);
			tarrgetX = pos[0] - 21;
			tarrgetY = pos[1] - 21;
			
			run(700);
		}
		
		@Override
		public void onComplete() {
			super.onComplete();
			if (csNew != null) {
				csNew.setVisible(false);
			}
		}
	}

	public Match getMatch() {
		return match;
	}
}
