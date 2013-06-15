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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MCheckBox;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.sangnd.gwt.faceme.client.activities.playinit.PlayInitView;

/**
 * @author heroandtn3
 * 
 */
public class PlayInitViewMGwt implements PlayInitView {

	private HeaderButton butBack;
	private HeaderButton butPlay;
	private Button butDanCo;
	private LayoutPanel panel;
	private FormListEntry formLevelList;
	private MCheckBox cbPlayWithCom;
	private MListBox levelList;

	/**
	 * 
	 */
	public PlayInitViewMGwt() {
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
		
		

		WidgetList list1 = new WidgetList();
		list1.setRound(true);
		container.add(list1);
		
		
		cbPlayWithCom = new MCheckBox();
		list1.add(new FormListEntry("Chơi với máy?", cbPlayWithCom.asWidget()));
		
		cbPlayWithCom.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				formLevelList.setVisible(event.getValue());
			}
		});
		
		levelList = new MListBox();
		formLevelList = new FormListEntry("Độ khó", levelList); 
		list1.add(formLevelList);
		levelList.addItem("Captain American");
		levelList.addItem("Thor");
		levelList.addItem("Iron Man");
		levelList.addItem("Hulk");

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
	public HasValue<Boolean> getPlayWithComCheckbox() {
		return cbPlayWithCom;
	}

	@Override
	public MListBox getLevelList() {
		return levelList;
	}

}
