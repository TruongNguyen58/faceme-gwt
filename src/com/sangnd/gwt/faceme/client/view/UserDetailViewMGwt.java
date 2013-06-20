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
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.sangnd.gwt.faceme.client.activities.userdetail.UserDetailView;

/**
 * @author heroandtn3
 *
 */
public class UserDetailViewMGwt implements UserDetailView {

	private LayoutPanel panel;
	private HeaderButton butBack;
	private HeaderButton butInvite;
	private HTML name;
	private HTML age;
	private StatusWidget status;
	private HTML title;

	/**
	 * 
	 */
	public UserDetailViewMGwt() {
		panel = new LayoutPanel();
		
		HeaderPanel headerPanel = new HeaderPanel();
		panel.add(headerPanel);
		
		butBack = new HeaderButton();
		butBack.setText("Back");
		butBack.setBackButton(true);
		headerPanel.setLeftWidget(butBack);
		
		butInvite = new HeaderButton();
		butInvite.setText("Invite");
		butInvite.setForwardButton(true);
		headerPanel.setRightWidget(butInvite);
		
		title = new HTML();
		headerPanel.setCenterWidget(title);
		
		WidgetList infoList = new WidgetList();
		panel.add(infoList);
		
		name = new HTML();
		infoList.add(name);
		
		age = new HTML();
		infoList.add(age);
		
		status = new StatusWidget();
		infoList.add(status.asWidget());
	}

	@Override
	public Widget asWidget() {
		return panel;
	}

	@Override
	public HasTapHandlers getBackButton() {
		return butBack;
	}

	@Override
	public HasTapHandlers getInviteButton() {
		return butInvite;
	}

	@Override
	public HasText getName() {
		return name;
	}

	@Override
	public HasText getAge() {
		return age;
	}

	@Override
	public HasStatus getStatusWidget() {
		return status;
	}

	@Override
	public HasText getTitle() {
		return title;
	}

}
