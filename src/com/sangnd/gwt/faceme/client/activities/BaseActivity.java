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
package com.sangnd.gwt.faceme.client.activities;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.sangnd.gwt.faceme.client.ClientFactory;
import com.sangnd.gwt.faceme.client.activities.setting.SettingPlace;
import com.sangnd.gwt.faceme.client.event.InvitationActionEvent;
import com.sangnd.gwt.faceme.client.event.InvitationActionHandler;
import com.sangnd.gwt.faceme.client.event.NewInvitationEvent;
import com.sangnd.gwt.faceme.client.event.NewInvitationHandler;
import com.sangnd.gwt.faceme.client.model.User;

/**
 * @author heroandtn3
 *
 */
public class BaseActivity extends MGWTAbstractActivity {


	protected ClientFactory clientFactory;

	/**
	 * 
	 */
	public BaseActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
	}
	
	protected void initBaseHandler(final BaseView view, final ClientFactory clientFactory) {
		final User user = clientFactory.getGameSession().getUser();
		if (user != null) {
			view.getRightButtonText().setText("[" + user.getNotiNumber() + "]");
			addHandlerRegistration(view.getRightButton().addTapHandler(new TapHandler() {
				
				@Override
				public void onTap(TapEvent event) {
					clientFactory.getNotiDialogView().renderInvitations(clientFactory.getGameSession().getInvitations());
				}
			}));
			
			addHandlerRegistration(clientFactory.getEventBus().addHandler(NewInvitationEvent.TYPE, new NewInvitationHandler() {
				
				@Override
				public void onNew(NewInvitationEvent event) {
					int notiNumber = user.getNotiNumber() + 1;
					user.setNotiNumber(notiNumber);
					view.getRightButtonText().setText("[" + user.getNotiNumber() + "]");
				}
			}));
			
			addHandlerRegistration(clientFactory.getNotiDialogView().addInvitationActionHandler(new InvitationActionHandler() {
				
				@Override
				public void onAction(InvitationActionEvent event) {
					clientFactory.getNotiDialogView().renderInvitations(clientFactory.getGameSession().getInvitations());
				}
			}));
			
		} else {
			view.getRightButtonText().setText("Setting");
			addHandlerRegistration(view.getRightButton().addTapHandler(new TapHandler() {
				
				@Override
				public void onTap(TapEvent event) {
					goTo(new SettingPlace());
				}
			}));
		}
	}
	
	protected void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}
