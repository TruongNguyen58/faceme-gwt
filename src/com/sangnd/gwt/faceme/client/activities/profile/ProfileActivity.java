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

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedHandler;
import com.sangnd.gwt.faceme.client.ClientFactory;
import com.sangnd.gwt.faceme.client.activities.BaseActivity;
import com.sangnd.gwt.faceme.client.activities.home.HomePlace;
import com.sangnd.gwt.faceme.client.activities.userdetail.UserDetailPlace;
import com.sangnd.gwt.faceme.client.model.User;
import com.sangnd.gwt.faceme.client.model.dao.UserDb;

/**
 * @author heroandtn3
 * 
 */
public class ProfileActivity extends BaseActivity {

	/**
	 * 
	 */
	public ProfileActivity(ClientFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		super.start(panel, eventBus);
		final ProfileView view = clientFactory.getProfileView();

		addHandlerRegistration(view.getLeftButton().addTapHandler(
				new TapHandler() {

					@Override
					public void onTap(TapEvent event) {
						goTo(new HomePlace());
					}
				}));

		final User user = clientFactory.getGameSession().getUser();
		if (user == null) {
			return;
		}
		view.getTitle().setText(user.getName());

		UserDb udb = clientFactory.getUserDb();
		udb.getOnlineUser(new AsyncCallback<List<User>>() {
			
			@Override
			public void onSuccess(List<User> result) {
				view.renderUserList(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		});
		

		addHandlerRegistration(view.getUserList().addCellSelectedHandler(
				new CellSelectedHandler() {

					@Override
					public void onCellSelected(CellSelectedEvent event) {
						goTo(new UserDetailPlace("" + event.getIndex()));
					}
				}));

		super.initBaseHandler(view, clientFactory);
		panel.setWidget(view.asWidget());
	}
}
