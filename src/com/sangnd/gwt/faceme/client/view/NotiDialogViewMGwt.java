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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.dialog.PopinDialog;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.RoundPanel;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedHandler;
import com.sangnd.gwt.faceme.client.event.InvitationActionEvent;
import com.sangnd.gwt.faceme.client.event.InvitationActionHandler;
import com.sangnd.gwt.faceme.client.model.Invitation;

/**
 * @author heroandtn3
 * 
 */
public class NotiDialogViewMGwt implements NotiDialogView {

	private PopinDialog dialog;
	private CellList<Invitation> inviCell;
	private RoundPanel roundPanel;
	private Button butAccept;
	private Button butDelice;
	private HandlerManager handlerManager;
	private int selectedIndex;

	/**
	 * 
	 */

	public NotiDialogViewMGwt() {
		handlerManager = new HandlerManager(this);
		selectedIndex = -1;
		
		dialog = new PopinDialog();
		dialog.setHideOnBackgroundClick(true);
		dialog.setShadow(true);
		
		roundPanel = new RoundPanel();
		dialog.add(roundPanel);
		roundPanel.add(new HTML("People invite you play"));
		ScrollPanel scrollPanel = new ScrollPanel();
		inviCell = new CellList<Invitation>(new InvitationCell());
		inviCell.setGroup(false);
		inviCell.setRound(true);
		scrollPanel.add(inviCell);
		roundPanel.add(scrollPanel);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		butAccept = new Button("Accept");
		horizontalPanel.add(butAccept);
		butDelice = new Button("Delice");
		horizontalPanel.add(butDelice);
		
		roundPanel.add(horizontalPanel);
		
		initHandler();
	}
	
	private void initHandler() {
		inviCell.addCellSelectedHandler(new CellSelectedHandler() {
			
			@Override
			public void onCellSelected(CellSelectedEvent event) {
				if (selectedIndex != -1) {
					inviCell.setSelectedIndex(selectedIndex, false);
				}
				selectedIndex = event.getIndex();
				inviCell.setSelectedIndex(selectedIndex, true);
				
			}
		});
		
		butAccept.addTapHandler(new TapHandler() {
			@Override
			public void onTap(TapEvent event) {
				if (selectedIndex != -1) {
					fireEvent(new InvitationActionEvent(selectedIndex, true));
					System.out.println("Accept");
				}
			}
		});
		
		butDelice.addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				if (selectedIndex != -1) {
					fireEvent(new InvitationActionEvent(selectedIndex, false));
				}
			}
		});
	}

	@Override
	public void renderInvitations(List<Invitation> invitations) {
		if (invitations == null) {
			dialog.hide();
			return;
		}
		inviCell.render(invitations);
		dialog.center();
	}

	@Override
	public HandlerRegistration addInvitationActionHandler(
			InvitationActionHandler handler) {
		return handlerManager.addHandler(InvitationActionEvent.TYPE, handler);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		handlerManager.fireEvent(event);
	}

	@Override
	public void hide() {
		dialog.hide();
	}

}
