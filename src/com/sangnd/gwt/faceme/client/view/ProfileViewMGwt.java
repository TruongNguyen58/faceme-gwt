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

import java.util.List;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.googlecode.mgwt.ui.client.dialog.ConfirmDialog.ConfirmCallback;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.celllist.HasCellSelectedHandler;
import com.sangnd.gwt.faceme.client.activities.profile.ProfileView;
import com.sangnd.gwt.faceme.client.model.User;

/**
 * @author heroandtn3
 * 
 */
public class ProfileViewMGwt extends BaseViewMGwt implements ProfileView {

	private HTML name;
	private CellList<User> userCell;

	/**
	 * 
	 */
	public ProfileViewMGwt() {

		butLeft.setText("Home");

		userCell = new CellList<User>(new UserCell());
		userCell.setRound(true);
		userCell.setGroup(false);
		scrollPanel.setWidget(userCell);

	}

	@Override
	public HasText getName() {
		return name;
	}

	@Override
	public void renderUserList(List<User> users) {
		userCell.render(users);
	}

	@Override
	public HasCellSelectedHandler getUserList() {
		return userCell;
	}

	@Override
	public void confirmSomeStuff(String title, String message,
			ConfirmCallback callback) {
		Dialogs.confirm(title, message, callback);

	}

}
