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
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.sangnd.gwt.faceme.client.activities.playinit.PlayInitView;
import com.sangnd.gwt.faceme.client.core.model.GameMode;
import com.sangnd.gwt.faceme.client.event.HasSelectGameModeHandler;
import com.sangnd.gwt.faceme.client.event.SelectGameModeEvent;
import com.sangnd.gwt.faceme.client.event.SelectGameModeHandler;
import com.sangnd.gwt.faceme.client.model.User;

/**
 * @author heroandtn3
 * 
 */
public class PlayInitViewMGwt implements PlayInitView, HasSelectGameModeHandler {

	private HeaderButton butBack;
	private Button butPlay;
	private Button butDanCo;
	private LayoutPanel panel;
	private FormListEntry formOpponent;
	private MListBox gameModeList;
	private MListBox levelList;
	private Button butSelectOpp;
	private HandlerManager handlerManager;

	private HorizontalPanel opponentPanel;

	/**
	 * 
	 */
	public PlayInitViewMGwt() {
		handlerManager = new HandlerManager(this);

		panel = new LayoutPanel();

		HeaderPanel headerPanel = new HeaderPanel();
		panel.add(headerPanel);

		butBack = new HeaderButton();
		butBack.setBackButton(true);
		headerPanel.setLeftWidget(butBack);

		

		ScrollPanel scrollPanel = new ScrollPanel();
		panel.add(scrollPanel);
		LayoutPanel container = new LayoutPanel();
		scrollPanel.add(container);

		final WidgetList settingList = new WidgetList();
		settingList.setRound(true);
		container.add(settingList);

		gameModeList = new MListBox();
		settingList.add(new FormListEntry("Chế độ chơi", gameModeList
				.asWidget()));
		gameModeList.addItem("Chơi với máy");
		gameModeList.addItem("Chơi với bạn bè");
		gameModeList.addItem("Chơi trực tuyến");

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

		butSelectOpp = new Button("Mời bạn bè");
		butSelectOpp.setSmall(true);

		opponentPanel = new HorizontalPanel();
		opponentPanel.add(butSelectOpp);
		
		butPlay = new Button();
		butPlay.setConfirm(true);
		butPlay.setSmall(true);
		container.add(butPlay);
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
		opponentPanel.clear();
		opponentPanel.add(new HTML(opponent.getName()));
		opponentPanel.add(butSelectOpp);
		butSelectOpp.setText("Chọn người khác");

	}

	@Override
	public HasTapHandlers getSelectOpponentButton() {
		return butSelectOpp;
	}

}
