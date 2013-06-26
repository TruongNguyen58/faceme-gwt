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

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTML;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.dialog.PopinDialog;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.ProgressBar;
import com.googlecode.mgwt.ui.client.widget.RoundPanel;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedHandler;
import com.sangnd.gwt.faceme.client.event.InviteUserEvent;
import com.sangnd.gwt.faceme.client.event.InviteUserHandler;
import com.sangnd.gwt.faceme.client.model.User;

/**
 * @author heroandtn3
 * 
 */
public class UserListDialogMGwt implements UserListDialog {

	private PopinDialog dialog;
	private CellList<User> userCell;
	private Button butInvite;
	private HandlerManager handlerManager;
	private int selectedIndex;
	private RoundPanel rp;
	private ProgressBar progressBar;

	/**
	 * 
	 */
	public UserListDialogMGwt() {
		handlerManager = new HandlerManager(this);
		selectedIndex = -1;

		dialog = new PopinDialog();
		dialog.setShadow(true);
		dialog.setHideOnBackgroundClick(true);

		rp = new RoundPanel();
		dialog.add(rp);
		rp.add(new HTML("Chon mot nguoi de choi"));

		ScrollPanel sp = new ScrollPanel();
		rp.add(sp);
		sp.setHeight("300px");
		sp.setWidth("300px");

		userCell = new CellList<User>(new UserCell());
		userCell.setRound(true);
		userCell.setGroup(false);
		sp.add(userCell);

		butInvite = new Button("Moi");
		butInvite.setSmall(true);
		rp.add(butInvite);

		progressBar = new ProgressBar();

		initHandler();
	}

	private void initHandler() {
		butInvite.addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				if (selectedIndex != -1) {
					rp.add(progressBar);
					butInvite.setText("Cancel");
					dialog.setHideOnBackgroundClick(false);
					fireEvent(new InviteUserEvent(selectedIndex));
				}
			}
		});

		userCell.addCellSelectedHandler(new CellSelectedHandler() {

			@Override
			public void onCellSelected(CellSelectedEvent event) {
				if (selectedIndex != -1) {
					userCell.setSelectedIndex(selectedIndex, false);
				}
				selectedIndex = event.getIndex();
				userCell.setSelectedIndex(selectedIndex, true);
			}
		});
	}

	@Override
	public HandlerRegistration addInviteUserHandler(InviteUserHandler handler) {
		return handlerManager.addHandler(InviteUserEvent.TYPE, handler);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		handlerManager.fireEvent(event);
	}

	@Override
	public void renderUserList(List<User> users) {
		userCell.render(users);
		dialog.center();
	}

	@Override
	public void hide() {
		dialog.hide();
		progressBar.removeFromParent();
		butInvite.setText("Invite");
		dialog.setHideOnBackgroundClick(true);
	}

}
