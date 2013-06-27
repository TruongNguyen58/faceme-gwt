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
import com.sangnd.gwt.faceme.client.ClientFactory;
import com.sangnd.gwt.faceme.client.activities.home.HomePlace;
import com.sangnd.gwt.faceme.client.activities.login.LoginPlace;
import com.sangnd.gwt.faceme.client.activities.play.PlayPlace;
import com.sangnd.gwt.faceme.client.core.model.Level;
import com.sangnd.gwt.faceme.client.event.SelectGameModeEvent;
import com.sangnd.gwt.faceme.client.event.SelectGameModeHandler;

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
		
		final PlayInitView view = clientFactory.getPlayInitView();
		view.getBackButtonText().setText("Home");
		view.getPlayButtonText().setText("Play");
		
		addHandlerRegistration(view.getBackButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				clientFactory.getPlaceController().goTo(new HomePlace());
			}
		}));
		
		addHandlerRegistration(view.getPlayButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				
				int levelIndex = view.getLevelList().getSelectedIndex();
				clientFactory.getGameSetting().setLevel(new Level(levelIndex + 1));
				
				clientFactory.getPlaceController().goTo(new PlayPlace());
				
			}
		}));
		
		addHandlerRegistration(view.getGameModeList().addSelectGameModeHanlder(new SelectGameModeHandler() {
			
			@Override
			public void onSelect(SelectGameModeEvent event) {
				switch (event.getMode()) {
					case PLAY_WITH_COMPUTER:
						break;
					case TWO_PLAYER_OFFLINE:
						break;
					case TWO_PLAYER_ONLINE:
						break;
					default:
						break;
				}
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

}
