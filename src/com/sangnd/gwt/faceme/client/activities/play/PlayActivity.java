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
package com.sangnd.gwt.faceme.client.activities.play;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.sangnd.gwt.faceme.client.ClientFactory;
import com.sangnd.gwt.faceme.client.activities.home.HomePlace;
import com.sangnd.gwt.faceme.client.core.model.GameMode;
import com.sangnd.gwt.faceme.client.core.model.Match;
import com.sangnd.gwt.faceme.client.core.model.Side;
import com.sangnd.gwt.faceme.client.event.ChessSelectEvent;
import com.sangnd.gwt.faceme.client.event.ChessSelectHandler;
import com.sangnd.gwt.faceme.client.event.MoveCompleteEvent;
import com.sangnd.gwt.faceme.client.event.MoveCompleteHandler;
import com.sangnd.gwt.faceme.client.event.StartPlayEvent;
import com.sangnd.gwt.faceme.client.event.StartPlayHandler;

/**
 * @author heroandtn3
 * 
 */
public class PlayActivity extends MGWTAbstractActivity {

	private ClientFactory clientFactory;
	private Match match;

	/**
	 * 
	 */
	public PlayActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		final PlayView view = clientFactory.getPlayView();
		view.getBackButtonText().setText("Home");
		
		
		
		addHandlerRegistration(view.getBackButton().addTapHandler(
				new TapHandler() {

					@Override
					public void onTap(TapEvent event) {
						clientFactory.getPlaceController().goTo(
								new HomePlace());
					}
				}));
		
		System.out.println("Start PlayActivity");
		match = clientFactory.getRoom().getMatch();
		System.out.println("start");
		doStartPlay(match, view);

		panel.setWidget(view.asWidget());
	}
	
	private void doStartPlay(final Match match, final PlayView view) {
		view.getBoardView().renderBoard(match.getBoard());

		addHandlerRegistration(view.getBoardView().getWidgetSelectChess()
				.addChessSelectHandler(new ChessSelectHandler() {
					
					@Override
					public void onSelect(ChessSelectEvent event) {
						if (match.getGameMode() == GameMode.PLAY_WITH_COMPUTER ||
								match.getGameMode() == GameMode.TWO_PLAYER_ONLINE) {
								if (match.getCurrentSide() == Side.ENERMY) {
									return;
								}
							}
						doChessSelectEvent(event, view);
					}
				}));
		
		addHandlerRegistration(match.getComputer().addChessSelectHandler(new ChessSelectHandler() {
			
			@Override
			public void onSelect(ChessSelectEvent event) {
				doChessSelectEvent(event, view);
			}
		}));
		
		addHandlerRegistration(view.getBoardView().getWidgetMoveChess().addMoveCompleteHandler(new MoveCompleteHandler() {
			
			@Override
			public void onComplete(MoveCompleteEvent event) {
				if (match.getGameMode() == GameMode.PLAY_WITH_COMPUTER) {
					if (match.getComputer().getSide() == match.getCurrentSide()) {
						match.getComputer().move();
					}
				}
			}
		}));
	}
	
	private void doChessSelectEvent(ChessSelectEvent event, PlayView view) {
		match.setPos(event.getPos());
		
		// This must be before moving chess!!!
		view.getBoardView().renderWarnKing(match.isWarnKing());
		view.getBoardView().renderMatchFinish(match.getState());
		
		if (match.getNewPos() != null) {
			view.getBoardView().renderMoveChess(match.getOldPos(), match.getNewPos());
			match.clearAfterMove();
			
		} else {
			view.getBoardView().renderChessSelect(match.getOldPos());
			view.getBoardView().renderPosCanMove(match.getPosCanMove());
		}
	}

}
