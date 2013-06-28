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
package com.sangnd.gwt.faceme.client.activities.setting;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.sangnd.gwt.faceme.client.ClientFactory;
import com.sangnd.gwt.faceme.client.activities.BaseActivity;
import com.sangnd.gwt.faceme.client.activities.home.HomePlace;
import com.sangnd.gwt.faceme.client.activities.register.RegisterPlace;
import com.sangnd.gwt.faceme.client.model.User;

/**
 * @author heroandtn3
 * 
 */
public class SettingActivity extends BaseActivity {

	/**
	 * 
	 */
	public SettingActivity(ClientFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		final SettingView view = clientFactory.getSettingView();
		view.getTitle().setText("Setting");
		view.getLeftButtonText().setText("Home");
		
		addHandlerRegistration(view.getLeftButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				goTo(new HomePlace());
			}
		}));
		
		User user = clientFactory.getGameSession().getUser();
		if (user == null) {
			((HeaderButton) view.getRightButton()).setVisible(false);
			view.setLogon(false);
		} else {
			((HeaderButton) view.getRightButton()).setVisible(true);
			view.getEmail().setText(user.getEmail());
			view.getPass().setText(user.getPass());
			view.setLogon(true);
		}
		
		addHandlerRegistration(view.getLoginButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				final String email = view.getEmail().getText();
				final String pass = view.getPass().getText();
				
				if (email.length() == 0 || pass.length() == 0) {
					view.alert("Co loi xay ra", "Ban chua nhap du truong du lieu", null);
				} else {
					clientFactory.getUserDb().getUserByEmail(email, new AsyncCallback<User>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
						}

						@Override
						public void onSuccess(User u) {
							if (u != null) {
								if (u.getPass().equals(pass)) {
									clientFactory.getGameSession().setUser(u);
									clientFactory.getChannelUtility().initChannel(u);
									clientFactory.getRoom().createRoom(u);
									u.setLogon(true);
									clientFactory.getUserDb().updateUser(u, new AsyncCallback<Boolean>() {

										@Override
										public void onFailure(Throwable caught) {
											caught.printStackTrace();
										}

										@Override
										public void onSuccess(Boolean result) {
											view.alert("Thong bao", "Dang nhap thanh cong", null);
										}
									});
									
									view.getEmail().setText(email);
									view.getPass().setText(pass);
									
									view.setLogon(true);
									((HeaderButton) view.getRightButton()).setVisible(true);
									initBaseHandler(view, clientFactory);
									
									return;
								}
							}
							view.alert("Dang nhap that bai", "Thong tin email va pass cua ban khong dung. Xin hay nhap lai", null);
						}
						
					});
					
				}
				
			}
		}));
		
		addHandlerRegistration(view.getLogoutButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				final User u = clientFactory.getGameSession().getUser();
				u.setLogon(false);
				clientFactory.getUserDb().updateUser(u, new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Boolean result) {
						if (result == true) {
							clientFactory.getGameSession().setUser(null);
							view.setLogon(false);
							view.alert("Thong bao", "Dang xuat thanh cong", null);
						}
					}
				});
			}
		}));
		
		addHandlerRegistration(view.getRegisterButton().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				goTo(new RegisterPlace());
			}
		}));
		
		super.initBaseHandler(view, clientFactory);
		panel.setWidget(view.asWidget());
	}
}
