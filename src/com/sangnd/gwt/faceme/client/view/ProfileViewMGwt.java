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

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.sangnd.gwt.faceme.client.activities.profile.ProfileView;

/**
 * @author heroandtn3
 *
 */
public class ProfileViewMGwt implements ProfileView {

	private LayoutPanel panel;
	private HeaderButton butBack;
	private HTML title;
	private HTML name;

	/**
	 * 
	 */
	public ProfileViewMGwt() {
		panel = new LayoutPanel();
		
		HeaderPanel headerPanel = new HeaderPanel();
		panel.add(headerPanel);
		
		butBack = new HeaderButton();
		butBack.setText("Home");
		butBack.setBackButton(true);
		headerPanel.setLeftWidget(butBack);
		
		title = new HTML();
		headerPanel.setCenterWidget(title);
		
		name = new HTML();
		panel.add(name);
		
	}

	@Override
	public Widget asWidget() {
		return panel;
	}

	@Override
	public HasText getTitle() {
		return title;
	}

	@Override
	public HasTapHandlers getBackButton() {
		return butBack;
	}


	@Override
	public HasText getName() {
		return name;
	}

}
