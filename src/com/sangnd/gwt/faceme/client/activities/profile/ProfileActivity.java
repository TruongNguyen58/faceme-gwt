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
package com.sangnd.gwt.faceme.client.activities.profile;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.dialog.ConfirmDialog.ConfirmCallback;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedHandler;
import com.sangnd.gwt.faceme.client.ClientFactory;
import com.sangnd.gwt.faceme.client.activities.home.HomePlace;
import com.sangnd.gwt.faceme.client.activities.play.PlayPlace;
import com.sangnd.gwt.faceme.client.activities.userdetail.UserDetailPlace;
import com.sangnd.gwt.faceme.client.channel.ChannelEvent;
import com.sangnd.gwt.faceme.client.channel.ChannelEventHandler;
import com.sangnd.gwt.faceme.client.core.model.GameMode;
import com.sangnd.gwt.faceme.client.core.model.Match;
import com.sangnd.gwt.faceme.client.event.StartPlayEvent;
import com.sangnd.gwt.faceme.client.model.RoomListener;
import com.sangnd.gwt.faceme.client.model.User;
import com.sangnd.gwt.faceme.client.model.dao.UserDb;

/**
 * @author heroandtn3
 * 
 */
public class ProfileActivity extends MGWTAbstractActivity {

	private ClientFactory clientFactory;

	/**
	 * 
	 */
	public ProfileActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		super.start(panel, eventBus);
		
		final ProfileView view = clientFactory.getProfileView();
		panel.setWidget(view.asWidget());
		
		addHandlerRegistration(view.getBackButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				
				clientFactory.getPlaceController().goTo(new HomePlace());
			}
		}));
		
			
		final User user = clientFactory.getGameSession().getUser();
		if (user == null) {
			return;
		}
		
		clientFactory.getChannelUtility().initChannel(user);
		clientFactory.getRoom().setCurrentId(user.getId(), new RoomListener() {
			
			@Override
			public void onRefuse() {
				clientFactory.getRoom().cancelInvitation();
			}
			
			@Override
			public void onInvited(final String userId) {
				view.confirmSomeStuff(userId, "Chơi cờ không?", new ConfirmCallback() {
					
					@Override
					public void onOk() {
						clientFactory.getRoom().agreeInvitationFrom(userId);
						doStartMatch();
					}
					
					@Override
					public void onCancel() {
						clientFactory.getRoom().refuseInvitationFrom(userId);
					}
				});
			}
			
			@Override
			public void onAgree() {
				System.out.println("He agreed!");
				doStartMatch();
			}
		});
		
		view.getTitle().setText(user.getName());
		view.getNotiText().setText("[" + user.getNotiNumber() + "]");
		//view.getName().setText(user.getName());
		
		UserDb udb = clientFactory.getUserDb();
		view.renderUserList(udb.getAllUser());
		
		addHandlerRegistration(view.getUserList().addCellSelectedHandler(new CellSelectedHandler() {
			
			@Override
			public void onCellSelected(CellSelectedEvent event) {
				clientFactory.getPlaceController().goTo(new UserDetailPlace("" + event.getIndex()));
			}
		}));
		
		addHandlerRegistration(eventBus.addHandler(ChannelEvent.TYPE, new ChannelEventHandler() {
			
			@Override
			public void onMessage(ChannelEvent event) {
				//view.confirmSomeStuff(event.getMessage().getSenderId(), event.getMessage().getContent(), null);
				int notiNumber = user.getNotiNumber() + 1;
				user.setNotiNumber(notiNumber);
				view.getNotiText().setText("[" + user.getNotiNumber() + "]");
			}
		}));
		
		addHandlerRegistration(view.getNotiWidget().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				view.getNotiDialogView().show();
			}
		}));
	}
	
	private void doStartMatch() {
		clientFactory.getPlaceController().goTo(new PlayPlace());
		Match match = new Match();
		match.setGameMode(GameMode.TWO_PLAYER_ONLINE);
		System.out.println("Do start");
		clientFactory.getEventBus().fireEvent(new StartPlayEvent(match));
	}
}
