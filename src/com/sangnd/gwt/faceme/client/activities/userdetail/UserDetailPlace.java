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
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * @author heroandtn3
 *
 */
public class UserDetailPlace extends Place {

	private String id;

	/**
	 * 
	 */
	public UserDetailPlace(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public static class Tokenizer implements PlaceTokenizer<UserDetailPlace> {

		@Override
		public UserDetailPlace getPlace(String token) {
			return new UserDetailPlace(token);
		}

		@Override
		public String getToken(UserDetailPlace place) {
			return place.getId();
		}
		
	}

}
