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
import com.googlecode.mgwt.ui.client.dialog.ConfirmDialog.ConfirmCallback;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.sangnd.gwt.faceme.client.ClientFactory;
import com.sangnd.gwt.faceme.client.activities.BaseActivity;
import com.sangnd.gwt.faceme.client.activities.home.HomePlace;
import com.sangnd.gwt.faceme.client.activities.play.PlayPlace;
import com.sangnd.gwt.faceme.client.activities.setting.SettingPlace;
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
public class PlayInitActivity extends BaseActivity {

	/**
	 * 
	 */
	public PlayInitActivity(ClientFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		clientFactory.getGameSession().newMatch();
		
		final PlayInitView view = clientFactory.getPlayInitView();
		view.getLeftButtonText().setText("Home");
		view.getPlayButtonText().setText("Play");
		
		addHandlerRegistration(view.getLeftButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				if (clientFactory.getGameSession().isPlayonline()) {
					Dialogs.confirm("Thong bao", "Ban that su muon huy tran dau nay?", new ConfirmCallback() {
						
						@Override
						public void onOk() {
							doCancelMatch(view);
							goTo(new HomePlace());
						}
						
						@Override
						public void onCancel() {
						}
					});
				} else {
					doCancelMatch(view);
				}
			}
		}));
		
		if (clientFactory.getGameSession().isPlayonline()) {
			view.getGameModeList().setItemSelected(2, true);
			view.getGameModeList().setEnabled(false);
			view.renderOpponent(clientFactory.getUserDb().getUserById("sang"));
			view.setWaiting(true);
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
		
		addHandlerRegistration(view.getPlayButton().addTapHandler(new TapHandler() {
			@Override
			public void onTap(TapEvent event) {
				doStart(view);
			}
		}));
		
		addHandlerRegistration(eventBus.addHandler(StartPlayEvent.TYPE, new StartPlayHandler() {
			
			@Override
			public void onStart(StartPlayEvent event) {
				view.setWaiting(false);
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
							goTo(new SettingPlace());
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
		
		addHandlerRegistration(view.getCancelButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				Dialogs.confirm("Thong bao", "Ban that su muon huy?", new ConfirmCallback() {
					
					@Override
					public void onOk() {
						doCancelMatch(view);
					}
					
					@Override
					public void onCancel() {
						// TODO Auto-generated method stub
						
					}
				});
			}
		}));
		
		super.initBaseHandler(view, clientFactory);
		panel.setWidget(view.asWidget());
	}
	
	private void doCancelMatch(PlayInitView view) {
		clientFactory.getGameSession().setPlayonline(false);
		view.setWaiting(false);
		view.getGameModeList().setItemSelected(2, true);
		view.getGameModeList().setEnabled(true);
		view.renderOpponent(null);
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
				clientFactory.getRoom().startPlay();
				Side currentSide = clientFactory.getGameSetting().getCurrentSide();
				clientFactory.getGameSession().getMatch().setCurrentSide(currentSide);
				break;
		}
		
		clientFactory.getGameSession().getMatch().setGameMode(gameMode);
		goTo(new PlayPlace());
	}

}
