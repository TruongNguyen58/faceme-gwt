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
package com.sangnd.gwt.faceme.client.activities.register;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.sangnd.gwt.faceme.client.ClientFactory;
import com.sangnd.gwt.faceme.client.activities.BaseActivity;
import com.sangnd.gwt.faceme.client.activities.home.HomePlace;
import com.sangnd.gwt.faceme.client.model.User;

/**
 * @author heroandtn3
 *
 */
public class RegisterActivity extends BaseActivity {


	/**
	 * 
	 */
	public RegisterActivity(ClientFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		final RegisterView view = clientFactory.getLoginView();
		view.getTitle().setText("Register Account");
		view.getLeftButtonText().setText("Home");
		
		addHandlerRegistration(view.getLeftButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				goTo(new HomePlace());
			}
		}));
		
		addHandlerRegistration(view.getRegisterButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				String name = view.getName().getText();
				String email = view.getEmail().getText();
				String pass = view.getPass().getText();
				
				if (name.length() == 0 || email.length() == 0 || pass.length() == 0) {
					view.alert("Co loi xay ra", "Ban chua nhap day du cac truong du lieu", null);
				}
				
				User u = new User();
				u.setName(name);
				u.setEmail(email);
				u.setPass(pass);
				
				clientFactory.getUserDb().insert(u, new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
						if (result == false) {
							view.alert("Co loi xay ra", "Email cua ban da co nguoi khac dung", null);
						} else {
							view.getName().setText("");
							view.getEmail().setText("");
							view.getPass().setText("");
							view.alert("Thanh cong", "Dang ky thanh cong!", null);
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});
				
			}
		}));
		
		super.initBaseHandler(view, clientFactory);
		panel.setWidget(view.asWidget());
	}
	
	

}
