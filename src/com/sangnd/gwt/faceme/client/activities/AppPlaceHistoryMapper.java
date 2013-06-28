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
package com.sangnd.gwt.faceme.client.activities;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.sangnd.gwt.faceme.client.activities.home.HomePlace;
import com.sangnd.gwt.faceme.client.activities.play.PlayPlace;
import com.sangnd.gwt.faceme.client.activities.playinit.PlayInitPlace;
import com.sangnd.gwt.faceme.client.activities.profile.ProfilePlace;
import com.sangnd.gwt.faceme.client.activities.register.RegisterPlace;
import com.sangnd.gwt.faceme.client.activities.setting.SettingPlace;
import com.sangnd.gwt.faceme.client.activities.userdetail.UserDetailPlace;

/**
 * @author heroandtn3
 * 
 */
@WithTokenizers({ HomePlace.Tokenizer.class, PlayPlace.Tokenizer.class,
		SettingPlace.Tokenizer.class, PlayInitPlace.Tokenizer.class,
		RegisterPlace.Tokenizer.class, ProfilePlace.Tokenizer.class, UserDetailPlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}