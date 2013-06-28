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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.sangnd.gwt.faceme.client.activities.playinit.PlayInitView;
import com.sangnd.gwt.faceme.client.model.User;

/**
 * @author heroandtn3
 * 
 */
public class PlayInitViewMGwt extends BaseViewMGwt implements PlayInitView {

	private Button butPlay;
	private Button butDanCo;
	private FormListEntry formOpponent;
	private MListBox gameModeList;
	private MListBox levelList;
	private Button butSelectOpp;

	private HorizontalPanel opponentPanel;

	/**
	 * 
	 */
	public PlayInitViewMGwt() {
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
				switch (gameModeList.getSelectedIndex()) {
				case 0:
					formOpponent.setWidget("Đối thủ", levelList);
					break;
				case 1:
					formOpponent.setWidget("Đối thủ", new HTML("Ban cua ban"));
					break;
				case 2:
					formOpponent.setWidget("Đối thủ", opponentPanel);
					break;
				default:
					break;
				}
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
	public MListBox getGameModeList() {
		return gameModeList;
	}

	@Override
	public void renderOpponent(User opponent) {
		opponentPanel.clear();
		opponentPanel.add(new HTML(opponent.getName()));
		opponentPanel.add(butSelectOpp);
		butSelectOpp.setText("Chọn người khác");
		formOpponent.setWidget("Đối thủ", opponentPanel);
	}

	@Override
	public HasTapHandlers getSelectOpponentButton() {
		return butSelectOpp;
	}

}
