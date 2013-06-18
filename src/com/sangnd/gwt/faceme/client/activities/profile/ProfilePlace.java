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

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * @author heroandtn3
 *
 */
public class ProfilePlace extends Place {

	private String id;

	/**
	 * 
	 */
	public ProfilePlace(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public static class Tokenizer implements PlaceTokenizer<ProfilePlace> {

		@Override
		public ProfilePlace getPlace(String token) {
			return new ProfilePlace(token);
		}

		@Override
		public String getToken(ProfilePlace place) {
			return place.getId();
		}
		
	}
	
	

}
