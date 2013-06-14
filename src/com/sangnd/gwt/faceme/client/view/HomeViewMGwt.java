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
package com.sangnd.gwt.faceme.client.view;

import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.sangnd.gwt.faceme.client.activities.home.HomeView;

/**
 * @author heroandtn3
 *
 */
public class HomeViewMGwt implements HomeView {


	private ScrollPanel panel;
	private Button butPlay;
	private Button butOnlinePlay;
	private Button butCothePlay;
	private Button butSetting;
	private Button butAbout;
	private LayoutPanel container;

	/**
	 * 
	 */
	public HomeViewMGwt() {
		panel = new ScrollPanel();
		container = new LayoutPanel();
		
		panel.add(container);
		
		initGui();
	}

	private void initGui() {
		butPlay = new Button("Chơi luôn");
		container.add(butPlay);
		
		butOnlinePlay = new Button("Chơi trực tuyến");
		container.add(butOnlinePlay);
		
		butCothePlay = new Button("Cờ thế");
		container.add(butCothePlay);
		
		butSetting = new Button("Cài đặt");
		container.add(butSetting);
		
		butAbout = new Button("Thông tin");
		container.add(butAbout);
		
	}

	@Override
	public Widget asWidget() {
		return panel;
	}

	@Override
	public HasTapHandlers getPlayButton() {
		return butPlay;
	}

	@Override
	public HasTapHandlers getCotheButton() {
		return butCothePlay;
	}

	@Override
	public HasTapHandlers getPlayOnlineButton() {
		return butOnlinePlay;
	}

	@Override
	public HasTapHandlers getSettingButton() {
		return butSetting;
	}

	@Override
	public HasTapHandlers getAboutButton() {
		return butAbout;
	}



}
