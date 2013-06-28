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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MPasswordTextBox;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.sangnd.gwt.faceme.client.activities.setting.SettingView;

/**
 * @author heroandtn3
 *
 */
public class SettingViewMGwt extends BaseViewMGwt implements SettingView {

	private MTextBox email;
	private MPasswordTextBox pass;
	private Button butLogin;
	private HorizontalPanel actionWidget;
	private Button butLogout;
	private Button butRegister;

	/**
	 * 
	 */
	public SettingViewMGwt() {
		LayoutPanel container = new LayoutPanel();
		scrollPanel.add(container);
		
		HTML accountTitle = new HTML("Account");
		accountTitle.addStyleName("title");
		container.add(accountTitle);
		WidgetList accountBox = new WidgetList();
		accountBox.setRound(true);
		container.add(accountBox);
		
		email = new MTextBox();
		accountBox.add(new FormListEntry("Email", email));
		
		pass = new MPasswordTextBox();
		accountBox.add(new FormListEntry("Password", pass));
		
		actionWidget = new HorizontalPanel();
		actionWidget.getElement().setAttribute("width", "100%");
		container.add(actionWidget);
		
		butLogin = new Button("Login");
		butLogin.setSmall(true);
		butLogout = new Button("Logout");
		butLogout.setSmall(true);
		butRegister = new Button("Register");
		butRegister.setSmall(true);
	}

	@Override
	public HasText getEmail() {
		return email;
	}

	@Override
	public HasText getPass() {
		return pass;
	}

	@Override
	public HasTapHandlers getLoginButton() {
		return butLogin;
	}

	@Override
	public HasTapHandlers getLogoutButton() {
		return butLogout;
	}

	@Override
	public HasTapHandlers getRegisterButton() {
		return butRegister;
	}

	@Override
	public void setLogon(boolean logon) {
		if (logon) {
			actionWidget.clear();
			actionWidget.add(butLogout);
		} else {
			actionWidget.clear();
			actionWidget.add(butLogin);
			actionWidget.add(butRegister);
			email.setText("");
			pass.setText("");
		}
	}

}
