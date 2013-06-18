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

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.sangnd.gwt.faceme.client.ClientFactory;
import com.sangnd.gwt.faceme.client.activities.home.HomePlace;
import com.sangnd.gwt.faceme.client.core.model.Match;
import com.sangnd.gwt.faceme.client.event.ChessSelectEvent;
import com.sangnd.gwt.faceme.client.event.ChessSelectHandler;
import com.sangnd.gwt.faceme.client.event.MoveCompleteEvent;
import com.sangnd.gwt.faceme.client.event.MoveCompleteHandler;

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
		match = new Match();
		match.setPlayWithCom(clientFactory.getGameSetting().isPlayWithCom());
		match.setLevel(clientFactory.getGameSetting().getLevel());

		final PlayView view = clientFactory.getPlayView();
		Place place = clientFactory.getPlaceController().getWhere();

		if (place instanceof PlayPlace) {
			view.getBackButtonText().setText("Home");
			addHandlerRegistration(view.getBackButton().addTapHandler(
					new TapHandler() {

						@Override
						public void onTap(TapEvent event) {
							clientFactory.getPlaceController().goTo(
									new HomePlace());
						}
					}));

			view.getBoardView().renderBoard(match.getBoard());
			
			ChessSelectHandler handler = new ChessSelectHandler() {

				@Override
				public void onSelect(ChessSelectEvent event) {
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
			
			};

			addHandlerRegistration(view.getBoardView().getWidgetSelectChess()
					.addChessSelectHandler(handler));
			addHandlerRegistration(match.getComputer().addChessSelectHandler(handler));
			
			addHandlerRegistration(view.getBoardView().getWidgetMoveChess().addMoveCompleteHandler(new MoveCompleteHandler() {
				
				@Override
				public void onComplete(MoveCompleteEvent event) {
					if (match.isPlayWithCom()) {
						if (match.getComputer().getSide() == match.getCurrentSide()) {
							match.getComputer().move();
						}
					}
				}
			}));

		}

		panel.setWidget(view.asWidget());
	}

}
