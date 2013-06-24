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
package com.sangnd.gwt.faceme.client.activities.userdetail;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.sangnd.gwt.faceme.client.ClientFactory;
import com.sangnd.gwt.faceme.client.activities.profile.ProfilePlace;
import com.sangnd.gwt.faceme.client.model.Status;
import com.sangnd.gwt.faceme.client.model.User;
import com.sangnd.gwt.faceme.client.model.dao.UserDb;

/**
 * @author heroandtn3
 *
 */
public class UserDetailActivity extends MGWTAbstractActivity {

	private ClientFactory clientFactory;

	/**
	 * 
	 */
	public UserDetailActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		UserDetailView view = clientFactory.getUserDetailView();
		panel.setWidget(view.asWidget());
		
		addHandlerRegistration(view.getBackButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				clientFactory.getPlaceController().goTo(new ProfilePlace());
			}
		}));
		
		Place place = clientFactory.getPlaceController().getWhere();
		
		if (place instanceof UserDetailPlace) {
			UserDetailPlace userDetailPlace = (UserDetailPlace) place;
			UserDb udb = clientFactory.getUserDb();
			final User user = udb.getUserById(userDetailPlace.getId());
			view.getTitle().setText(user.getName());
			view.getName().setText(user.getName());
			view.getAge().setText("" + user.getAge());
			if (user.isLogon()) {
				if (user.isPlaying()) {
					view.getStatusWidget().setStatus(Status.BUSY);
				} else {
					view.getStatusWidget().setStatus(Status.AVAI);
				}
			} else {
				view.getStatusWidget().setStatus(Status.OFFLINE);
			}
			
			addHandlerRegistration(view.getInviteButton().addTapHandler(new TapHandler() {
				
				@Override
				public void onTap(TapEvent event) {
					invitePlay(user);
				}
			}));
		}
	}

	private void invitePlay(User user) {
		clientFactory.getRoom().inviteOpponent(user.getId());
		
	}
	
	

}
