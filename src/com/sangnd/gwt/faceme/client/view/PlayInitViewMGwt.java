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

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.dialog.PopinDialog;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.googlecode.mgwt.ui.client.widget.ProgressBar;
import com.googlecode.mgwt.ui.client.widget.RoundPanel;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedHandler;
import com.sangnd.gwt.faceme.client.activities.playinit.PlayInitView;
import com.sangnd.gwt.faceme.client.core.model.GameMode;
import com.sangnd.gwt.faceme.client.event.HasInviteUserHandler;
import com.sangnd.gwt.faceme.client.event.HasSelectGameModeHandler;
import com.sangnd.gwt.faceme.client.event.InviteUserEvent;
import com.sangnd.gwt.faceme.client.event.InviteUserHandler;
import com.sangnd.gwt.faceme.client.event.SelectGameModeEvent;
import com.sangnd.gwt.faceme.client.event.SelectGameModeHandler;
import com.sangnd.gwt.faceme.client.model.User;

/**
 * @author heroandtn3
 * 
 */
public class PlayInitViewMGwt implements PlayInitView, HasSelectGameModeHandler, HasInviteUserHandler {

	private HeaderButton butBack;
	private HeaderButton butPlay;
	private Button butDanCo;
	private LayoutPanel panel;
	private FormListEntry formOpponent;
	private MListBox gameModeList;
	private MListBox levelList;
	private Button butSelectOpp;
	private PopinDialog userListDialog;
	private CellList<User> userCell;
	private Button butInvite;
	private HandlerManager handlerManager;
	private int selectedIndex;
	private HorizontalPanel opponentPanel;
	private RoundPanel rp;
	private ProgressBar progressBar;

	/**
	 * 
	 */
	public PlayInitViewMGwt() {
		handlerManager = new HandlerManager(this);
		selectedIndex = -1;
		
		panel = new LayoutPanel();

		HeaderPanel headerPanel = new HeaderPanel();
		panel.add(headerPanel);

		butBack = new HeaderButton();
		butBack.setBackButton(true);
		headerPanel.setLeftWidget(butBack);

		butPlay = new HeaderButton();
		butPlay.setForwardButton(true);
		headerPanel.setRightWidget(butPlay);

		ScrollPanel scrollPanel = new ScrollPanel();
		panel.add(scrollPanel);
		LayoutPanel container = new LayoutPanel();
		scrollPanel.add(container);
		
		

		final WidgetList settingList = new WidgetList();
		settingList.setRound(true);
		container.add(settingList);
		
		
		gameModeList = new MListBox();
		settingList.add(new FormListEntry("Chế độ chơi", gameModeList.asWidget()));
		gameModeList.addItem("Play offline with computer");
		gameModeList.addItem("Play offline with a friend");
		gameModeList.addItem("Play online with a friend");
		
		gameModeList.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				GameMode mode;
				switch (gameModeList.getSelectedIndex()) {
					case 0:
						formOpponent.setWidget("Đối thủ", levelList);
						mode = GameMode.PLAY_WITH_COMPUTER;
						break;
					case 1:
						mode = GameMode.TWO_PLAYER_OFFLINE;
						break;
					case 2:
						formOpponent.setWidget("Đối thủ", opponentPanel);
						mode = GameMode.TWO_PLAYER_ONLINE;
						break;
					default:
						mode = GameMode.PLAY_WITH_COMPUTER;
				}
				
				fireEvent(new SelectGameModeEvent(mode));
			}
		});
		
		levelList = new MListBox();
		levelList.addItem("Captain American");
		levelList.addItem("Thor");
		levelList.addItem("Iron Man");
		levelList.addItem("Hulk");
		
		formOpponent = new FormListEntry("Đối thủ", levelList); 
		settingList.add(formOpponent);
		
		butSelectOpp = new Button("Chon");
		butSelectOpp.setSmall(true);
		
		opponentPanel = new HorizontalPanel();
		opponentPanel.add(butSelectOpp);
		
		userListDialog = new PopinDialog();
		userListDialog.setShadow(true);
		userListDialog.setHideOnBackgroundClick(true);
		
		rp = new RoundPanel();
		userListDialog.add(rp);
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
					userListDialog.setHideOnBackgroundClick(false);
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
	public Widget asWidget() {
		return panel;
	}

	@Override
	public HasTapHandlers getBackButton() {
		return butBack;
	}

	@Override
	public HasText getBackButtonText() {
		return butBack;
	}

	@Override
	public HasTapHandlers getPlayButton() {
		return butPlay;
	}

	@Override
	public HasTapHandlers getDancoButton() {
		return butDanCo;
	}

	@Override
	public HasText getPlayButtonText() {
		return butPlay;
	}

	@Override
	public MListBox getLevelList() {
		return levelList;
	}

	@Override
	public HasSelectGameModeHandler getGameModeList() {
		return this;
	}

	@Override
	public HandlerRegistration addSelectGameModeHanlder(
			SelectGameModeHandler handler) {
		return handlerManager.addHandler(SelectGameModeEvent.TYPE, handler);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		handlerManager.fireEvent(event);
	}

	@Override
	public void renderOpponent(User opponent) {
		butInvite.setText("Invite");
		progressBar.removeFromParent();
		userListDialog.setHideOnBackgroundClick(true);
		userListDialog.hide();
		opponentPanel.clear();
		opponentPanel.add(new HTML(opponent.getName()));
		opponentPanel.add(butSelectOpp);
		butSelectOpp.setText("Select other");
		
	}

	@Override
	public HasTapHandlers getSelectOpponentButton() {
		return butSelectOpp;
	}

	@Override
	public void renderUserList(List<User> users) {
		if (users == null) {
			return;
		}
		userCell.render(users);
		userListDialog.center();
	}

	@Override
	public HandlerRegistration addInviteUserHandler(InviteUserHandler handler) {
		return handlerManager.addHandler(InviteUserEvent.TYPE, handler);
	}

	@Override
	public HasInviteUserHandler getInviteUserWidget() {
		return this;
	}

}
