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

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

/**
 * @author heroandtn3
 *
 */
public class NoticationShape extends Composite {

	private HTML content;
	private NotiAnimation animation;

	/**
	 * 
	 */
	public NoticationShape() {
		init();
	}

	private void init() {
		content = new HTML();
		this.initWidget(content);
		
		
		this.getElement().getStyle().setProperty("position", "absolute");
		this.getElement().getStyle().setProperty("display", "block");
		this.getElement().getStyle().setProperty("fontSize", "20px");
		this.getElement().getStyle().setProperty("color", "#FFF");
		this.getElement().getStyle().setProperty("textAlign", "center");
		this.getElement().getStyle().setProperty("paddingTop", "20px");
		this.getElement().getStyle().setProperty("backgroundColor", "#000");
		this.getElement().getStyle().setProperty("opacity", "0.4");

		this.getElement().getStyle().setProperty("width","200px");
		this.getElement().getStyle().setProperty("height", "40px");
		
		this.getElement().getStyle().setProperty("border", "1px solid #cccccc"); // for debug
		
		content.setVisible(false);
		
		animation = new NotiAnimation();
		
	}
	
	public void notice(String message) {
		if (message != null) {
			content.setText(message);
			if (message.length() == 0) {
				content.setVisible(false);
			} else {
				content.setVisible(true);
			}
		} else {
			content.setVisible(false);
		}
		
		//animation.run(700);
		
	}
	
	private class NotiAnimation extends Animation {

		@Override
		protected void onUpdate(double progress) {
			
		}
		
		@Override
		protected void onComplete() {
			super.onComplete();
			notice("");
		}
		
	}

}
