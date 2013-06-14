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
import com.sangnd.gwt.faceme.client.activities.play.PlayView;
import com.sangnd.gwt.faceme.client.activities.playinit.PlayInitView;
import com.sangnd.gwt.faceme.client.view.HomeViewMGwt;
import com.sangnd.gwt.faceme.client.view.PlayInitViewMGwt;
import com.sangnd.gwt.faceme.client.view.PlayViewMGwt;

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

}
