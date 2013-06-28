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
package com.sangnd.gwt.faceme.client.model.dao;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sangnd.gwt.faceme.client.model.User;
import com.sangnd.gwt.faceme.shared.UserDbService;
import com.sangnd.gwt.faceme.shared.UserDbServiceAsync;

/**
 * @author heroandtn3
 *
 */
public class UserDb {
	
	UserDbServiceAsync udbService;

	/**
	 * 
	 */
	public UserDb() {
		udbService = GWT.create(UserDbService.class);
	}
	
	public void insert(User user, AsyncCallback<Boolean> callback) {
		udbService.insertUser(user, callback);
	}
	
	public void updateUser(User user, AsyncCallback<Boolean> callback) {
		udbService.updateUser(user, callback);
	}
	
	public void getUserById(Long id, AsyncCallback<User> callback) {
		udbService.getUserById(id, callback);
	}
	
	public void getUserByEmail(String email, AsyncCallback<User> callback) {
		udbService.getUserByEmail(email, callback);
	}
	
	
	public void getOnlineUser(AsyncCallback<List<User>> callback) {
		udbService.getOnlineUser(callback);
	}

	

}
