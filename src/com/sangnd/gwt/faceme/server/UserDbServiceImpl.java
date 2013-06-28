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
package com.sangnd.gwt.faceme.server;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.ObjectifyService;
import com.sangnd.gwt.faceme.client.model.User;
import com.sangnd.gwt.faceme.shared.UserDbService;

/**
 * @author heroandtn3
 *
 */
public class UserDbServiceImpl extends RemoteServiceServlet implements UserDbService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public UserDbServiceImpl() {
	}

	@Override
	public boolean insertUser(User user) {
		ObjectifyService.register(User.class);
		if (user != null) {
			if (getUserByEmail(user.getEmail()) != null) {
				return false;
			}
			ofy().save().entity(user).now();
			return true;
		} else {
			throw new NullPointerException();
		}
	}
	
	@Override
	public boolean updateUser(User user) {
		ObjectifyService.register(User.class);
		if (user != null) {
			ofy().save().entity(user);
			return true;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public User getUserById(Long id) {
		ObjectifyService.register(User.class);
		User user = null;
		user = ofy().load().type(User.class).id(id).get();
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		ObjectifyService.register(User.class);
		if (email == null) throw new NullPointerException();
		return ofy().load().type(User.class).filter("email", email).first().get();
	}

	@Override
	public List<User> getOnlineUser() {
		ObjectifyService.register(User.class);
		List<User> users = new ArrayList<User>();
		List<User> q = ofy().load().type(User.class).filter("logon", true)
				.list();
		if (q != null) {
			for (User user : q) {
				users.add(user);
			}
		}
		return users;
	}

}
