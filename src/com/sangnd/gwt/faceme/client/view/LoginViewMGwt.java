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
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.sangnd.gwt.faceme.client.activities.login.LoginView;

/**
 * @author heroandtn3
 *
 */
public class LoginViewMGwt implements LoginView {

	private LayoutPanel panel;
	private HeaderButton butBack;
	private HTML title;
	private MTextBox tbEmail;
	private MTextBox tbPass;
	private Button butLogin;
	private HeaderButton butRegister;

	/**
	 * 
	 */
	public LoginViewMGwt() {
		panel = new LayoutPanel();
		
		HeaderPanel headerPanel = new HeaderPanel();
		panel.add(headerPanel);
		
		butBack = new HeaderButton();
		butBack.setText("Home");
		butBack.setBackButton(true);
		headerPanel.setLeftWidget(butBack);
		
		butRegister = new HeaderButton();
		butRegister.setText("Register");
		headerPanel.setRightWidget(butRegister);
		
		title = new HTML();
		headerPanel.setCenterWidget(title);
		
		WidgetList list = new WidgetList();
		panel.add(list);
		
		tbEmail = new MTextBox();
		list.add(new FormListEntry("Email", tbEmail));
		
		tbPass = new MTextBox();
		list.add(new FormListEntry("Password", tbPass));
		
		butLogin = new Button("Login");
		panel.add(butLogin);
	}

	@Override
	public HasTapHandlers getBackButton() {
		return butBack;
	}

	@Override
	public HasTapHandlers getLoginButton() {
		return butLogin;
	}

	@Override
	public HasTapHandlers getRegisterButton() {
		return butRegister;
	}

	@Override
	public HasText getEmail() {
		return tbEmail;
	}

	@Override
	public HasText getPass() {
		return tbPass;
	}

	@Override
	public HasText getTitle() {
		return title;
	}

	@Override
	public Widget asWidget() {
		return panel;
	}

}
