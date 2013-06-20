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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Random;
import com.sangnd.gwt.faceme.client.model.User;

/**
 * @author heroandtn3
 *
 */
public class UserDb {

	/**
	 * 
	 */
	public UserDb() {
		// TODO Auto-generated constructor stub
	}
	
	public List<User> getAllUser() {
		List<User> list = new ArrayList<User>();
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setName("Player " + i);
			user.setEmail("user" + i + "@gmail.com");
			user.setAge(i);
			user.setLogon(Random.nextBoolean());
			user.setPlaying(Random.nextBoolean());
			list.add(user);
		}
		return list;
	}

	public User getUserById(String id) {
		User user = new User();
		user.setName("Player " + id);
		user.setEmail("user" + id + "@gmail.com");
		user.setAge(99);
		return user;
	}

}
