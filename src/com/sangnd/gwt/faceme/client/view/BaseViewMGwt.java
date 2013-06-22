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
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;

/**
 * @author heroandtn3
 *
 */
public class BaseViewMGwt implements BaseView {

	protected LayoutPanel panel;
	protected HeaderButton butBack;
	protected ScrollPanel scrollPanel;
	protected HeaderButton notiWidget;
	protected HTML title;
	private NotiDialogView notiDialog;

	/**
	 * 
	 */
	public BaseViewMGwt() {
		panel = new LayoutPanel();
		
		HeaderPanel headerPanel = new HeaderPanel();
		panel.add(headerPanel);
		
		butBack = new HeaderButton(); 
		butBack.setBackButton(true);
		headerPanel.setLeftWidget(butBack);
		
		title = new HTML();
		headerPanel.setCenterWidget(title);
		
		notiWidget = new HeaderButton();
		notiWidget.setRoundButton(true);
		headerPanel.setRightWidget(notiWidget);
		
		scrollPanel = new ScrollPanel();
		panel.add(scrollPanel);
		
		notiDialog = new NotiDialogViewMGwt();
	}

	@Override
	public Widget asWidget() {
		return panel;
	}

	@Override
	public HasTapHandlers getNotiWidget() {
		return notiWidget;
	}

	@Override
	public HasText getNotiText() {
		return notiWidget;
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
	public HasText getTitle() {
		return title;
	}

	@Override
	public NotiDialogView getNotiDialogView() {
		return notiDialog;
	}

}
