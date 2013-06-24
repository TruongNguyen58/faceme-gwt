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
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.sangnd.gwt.faceme.client.activities.home.HomeView;
import com.sangnd.gwt.faceme.client.activities.login.LoginView;
import com.sangnd.gwt.faceme.client.activities.play.PlayView;
import com.sangnd.gwt.faceme.client.activities.playinit.PlayInitView;
import com.sangnd.gwt.faceme.client.activities.profile.ProfileView;
import com.sangnd.gwt.faceme.client.activities.userdetail.UserDetailView;
import com.sangnd.gwt.faceme.client.channel.ChannelUtility;
import com.sangnd.gwt.faceme.client.channel.ChannelUtilityImpl;
import com.sangnd.gwt.faceme.client.model.GameSession;
import com.sangnd.gwt.faceme.client.model.GameSetting;
import com.sangnd.gwt.faceme.client.model.Room;
import com.sangnd.gwt.faceme.client.model.RoomImpl;
import com.sangnd.gwt.faceme.client.model.dao.UserDb;
import com.sangnd.gwt.faceme.client.view.HomeViewMGwt;
import com.sangnd.gwt.faceme.client.view.LoginViewMGwt;
import com.sangnd.gwt.faceme.client.view.PlayInitViewMGwt;
import com.sangnd.gwt.faceme.client.view.PlayViewMGwt;
import com.sangnd.gwt.faceme.client.view.ProfileViewMGwt;
import com.sangnd.gwt.faceme.client.view.UserDetailViewMGwt;

/**
 * @author heroandtn3
 * 
 */
public class ClientFactoryImpl implements ClientFactory {

	private EventBus eventBus;
	private PlaceController placeController;
	private HomeView homeView;
	private PlayView playView;
	private PlayInitView playInitView;
	private GameSetting gameSetting;
	private LoginView loginView;
	private ProfileView profileView;
	private GameSession gameSession;
	private UserDb userDb;
	private UserDetailView userDetailView;
	private ChannelUtility channelUtility;
	private Room room;

	public ClientFactoryImpl() {
		eventBus = new SimpleEventBus();

		placeController = new PlaceController(eventBus);

	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public HomeView getHomeView() {
		if (homeView == null) {
			homeView = new HomeViewMGwt();
		}
		return homeView;
	}

	@Override
	public PlayView getPlayView() {
		if (playView == null) {
			playView = new PlayViewMGwt();
		}
		playView.getBoardView().clearContent();
		return playView;
	}

	@Override
	public PlayInitView getPlayInitView() {
		if (playInitView == null) {
			playInitView = new PlayInitViewMGwt();
		}
		return playInitView;
	}

	@Override
	public GameSetting getGameSetting() {
		if (gameSetting == null) {
			gameSetting = new GameSetting();
		}
		return gameSetting;
	}

	@Override
	public LoginView getLoginView() {
		if (loginView == null) {
			loginView = new LoginViewMGwt();
		}
		return loginView;
	}

	@Override
	public ProfileView getProfileView() {
		if (profileView == null) {
			profileView = new ProfileViewMGwt();
		}
		return profileView;
	}

	@Override
	public GameSession getGameSession() {
		if (gameSession == null) {
			gameSession = new GameSession();
		}
		return gameSession;
	}

	@Override
	public UserDb getUserDb() {
		if (userDb == null) {
			userDb = new UserDb();
		}
		return userDb;
	}

	@Override
	public UserDetailView getUserDetailView() {
		if (userDetailView == null) {
			userDetailView = new UserDetailViewMGwt();
		}
		return userDetailView;
	}

	@Override
	public ChannelUtility getChannelUtility() {
		if (channelUtility == null) {
			channelUtility = new ChannelUtilityImpl(eventBus);
		}
		return channelUtility;
	}

	@Override
	public Room getRoom() {
		if (room == null) {
			room = new RoomImpl(this);
		}
		return room;
	}

}
