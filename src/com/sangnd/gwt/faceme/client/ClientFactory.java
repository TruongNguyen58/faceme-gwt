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

package com.sangnd.gwt.faceme.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.sangnd.gwt.faceme.client.activities.home.HomeView;
import com.sangnd.gwt.faceme.client.activities.login.LoginView;
import com.sangnd.gwt.faceme.client.activities.play.PlayView;
import com.sangnd.gwt.faceme.client.activities.playinit.PlayInitView;
import com.sangnd.gwt.faceme.client.activities.profile.ProfileView;
import com.sangnd.gwt.faceme.client.activities.userdetail.UserDetailView;
import com.sangnd.gwt.faceme.client.channel.ChannelUtility;
import com.sangnd.gwt.faceme.client.model.GameSession;
import com.sangnd.gwt.faceme.client.model.GameSetting;
import com.sangnd.gwt.faceme.client.model.Room;
import com.sangnd.gwt.faceme.client.model.dao.UserDb;

/**
 * @author heroandtn3
 * 
 */
public interface ClientFactory {

	public EventBus getEventBus();

	public PlaceController getPlaceController();

	public HomeView getHomeView();

	public PlayView getPlayView();

	public PlayInitView getPlayInitView();
	
	public GameSetting getGameSetting();

	public LoginView getLoginView();

	public ProfileView getProfileView();

	public GameSession getGameSession();
	
	public UserDb getUserDb();

	public UserDetailView getUserDetailView();
	
	public ChannelUtility getChannelUtility();

	public Room getRoom();

}
