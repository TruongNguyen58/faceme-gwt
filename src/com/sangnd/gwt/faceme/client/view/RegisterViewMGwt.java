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

import com.google.gwt.user.client.ui.HasText;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.googlecode.mgwt.ui.client.dialog.Dialogs.AlertCallback;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.sangnd.gwt.faceme.client.activities.register.RegisterView;

/**
 * @author heroandtn3
 *
 */
public class RegisterViewMGwt extends BaseViewMGwt implements RegisterView {


	private MTextBox email;
	private MTextBox pass;
	private Button butRegister;
	private MTextBox name;

	/**
	 * 
	 */
	public RegisterViewMGwt() {
		LayoutPanel container = new LayoutPanel();
		scrollPanel.add(container);
		
		WidgetList list = new WidgetList();
		list.setRound(true);
		container.add(list);
		
		name = new MTextBox();
		list.add(new FormListEntry("Name", name));
		
		email = new MTextBox();
		list.add(new FormListEntry("Email", email));
		
		pass = new MTextBox();
		list.add(new FormListEntry("Password", pass));
		
		butRegister = new Button("Register");
		butRegister.setSmall(true);
		container.add(butRegister);
	}

	@Override
	public HasTapHandlers getRegisterButton() {
		return butRegister;
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
	public void alert(String title, String text, AlertCallback callback) {
		Dialogs.alert(title, text, callback);
	}

	@Override
	public HasText getName() {
		return name;
	}
		



}
