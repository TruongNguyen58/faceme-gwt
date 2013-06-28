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
package com.sangnd.gwt.faceme.client.activities.playinit;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.dialog.ConfirmDialog.ConfirmCallback;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.sangnd.gwt.faceme.client.ClientFactory;
import com.sangnd.gwt.faceme.client.activities.home.HomePlace;
import com.sangnd.gwt.faceme.client.activities.login.LoginPlace;
import com.sangnd.gwt.faceme.client.activities.play.PlayPlace;
import com.sangnd.gwt.faceme.client.core.model.GameMode;
import com.sangnd.gwt.faceme.client.core.model.Level;
import com.sangnd.gwt.faceme.client.core.model.Side;
import com.sangnd.gwt.faceme.client.event.InvitationActionEvent;
import com.sangnd.gwt.faceme.client.event.InvitationActionHandler;
import com.sangnd.gwt.faceme.client.event.StartPlayEvent;
import com.sangnd.gwt.faceme.client.event.StartPlayHandler;
import com.sangnd.gwt.faceme.client.model.User;

/**
 * @author heroandtn3
 * 
 */
public class PlayInitActivity extends MGWTAbstractActivity {

	private ClientFactory clientFactory;

	/**
	 * 
	 */
	public PlayInitActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		clientFactory.getGameSession().newMatch();
		
		final PlayInitView view = clientFactory.getPlayInitView();
		view.getBackButtonText().setText("Home");
		view.getPlayButtonText().setText("Play");
		
		if (clientFactory.getGameSession().isPlayonline()) {
			view.getGameModeList().setItemSelected(2, true);
			view.getGameModeList().setEnabled(false);
			view.renderOpponent(clientFactory.getUserDb().getUserById("sang"));
			((Button)view.getPlayButton()).setVisible(false);
		}
		
		addHandlerRegistration(eventBus.addHandler(InvitationActionEvent.TYPE, new InvitationActionHandler() {
			
			@Override
			public void onAction(InvitationActionEvent event) {
				if (event.isAccept()) {
					User user = clientFactory.getUserDb().getAllUser().get(event.getSelectedIndex());
					view.renderOpponent(user);
				}
			}
		}));
		
		addHandlerRegistration(view.getBackButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				clientFactory.getPlaceController().goTo(new HomePlace());
			}
		}));
		
		addHandlerRegistration(view.getPlayButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				clientFactory.getRoom().startPlay();
				doStart(view);
			}
		}));
		
		addHandlerRegistration(eventBus.addHandler(StartPlayEvent.TYPE, new StartPlayHandler() {
			
			@Override
			public void onStart(StartPlayEvent event) {
				doStart(view);
			}
		}));
		
		addHandlerRegistration(view.getSelectOpponentButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				if (clientFactory.getGameSession().getUser() == null) {
					Dialogs.confirm("Thong bao", "Ban chua dang nhap", new ConfirmCallback() {
						
						@Override
						public void onOk() {
							clientFactory.getPlaceController().goTo(new LoginPlace());
						}
						
						@Override
						public void onCancel() {
							// TODO Auto-generated method stub
							
						}
					});
				} else {
					clientFactory.getUserListDialog().renderUserList(clientFactory.getUserDb().getAllUser());
				}
				
			}
		}));
		
		panel.setWidget(view.asWidget());
	}
	
	private void doStart(PlayInitView view) {
		int mode = view.getGameModeList().getSelectedIndex();
		GameMode gameMode = GameMode.PLAY_WITH_COMPUTER;
		switch(mode) {
			case 0:
				gameMode = GameMode.PLAY_WITH_COMPUTER;
				int levelIndex = view.getLevelList().getSelectedIndex();
				clientFactory.getGameSetting().setLevel(new Level(levelIndex + 1));
				break;
			case 1:
				gameMode = GameMode.TWO_PLAYER_OFFLINE;
				break;
			case 2:
				gameMode = GameMode.TWO_PLAYER_ONLINE;
				Side currentSide = clientFactory.getGameSetting().getCurrentSide();
				clientFactory.getGameSession().getMatch().setCurrentSide(currentSide);
				break;
		}
		
		clientFactory.getGameSession().getMatch().setGameMode(gameMode);
		clientFactory.getPlaceController().goTo(new PlayPlace());
	}

}
