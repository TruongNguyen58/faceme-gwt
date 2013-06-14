/**
 * 
 */

/*
Copyright 2013 heroandtn3 (@sangnd.info)

This file is a part of FacemeGwt

FacemeGwt is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

FacemeGwt is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sangnd.gwt.faceme.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.sangnd.gwt.faceme.client.core.model.Constant;

/**
 * @author heroandtn3
 *
 */
public class ChessShape extends Composite {

	private int type;
	private final String CHESS_DIR = "images/chess/";
	private Image img;
	private String[] CHESS_NAME;
	private boolean reserve;

	/**
	 * 
	 */
	
	public ChessShape() {
		this.reserve = false;
		this.init();
		this.setType(0);
	}
	
	public ChessShape(int type, boolean reserve) {
		this.reserve = reserve;
		this.init();
		this.setType(type);
	}
	
	private void init() {
		//img = new Image("images/none.png");
		img = new Image();
		initWidget(img);
		CHESS_NAME = new String[] {
			"tuong",
			"sy",
			"tinh",
			"xe",
			"phao",
			"ma",
			"tot"
		};
		
		this.getElement().getStyle().setProperty("position", "absolute");
		this.getElement().getStyle().setProperty("display", "block");
		
		this.getElement().getStyle().setProperty("border", "1px solid #cccccc"); // for debug
	}
	
	public void setPos(int left, int top) {
		
		this.getElement().getStyle().setProperty("left", left + "px");
		this.getElement().getStyle().setProperty("top", top + "px");
		
		int size = (int) (Constant.SCREEN_RATIO * 42);
		
		this.getElement().getStyle().setProperty("width", size + "px");
		this.getElement().getStyle().setProperty("height", size + "px");
		this.getElement().getStyle().setProperty("backgroundSize", size + "px " + size + "px");
	}
	
	public void setReserve(boolean reserve) {
		this.reserve = reserve;
		this.draw();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		this.draw();
	}
	
	private void draw() {
		if (type == 0) {
			img.setUrl("images/none.png");
			return;
		}
		
		String color = reserve ? "den.png" : "do.png";
		int tmp = type;
		if (type < 0) {
			color = reserve ? "do.png" : "den.png";
			tmp = -type;
		}
		
		for (int i = 1; i <= 7; i++) {
			if (i == tmp) {
				img.setUrl(CHESS_DIR + CHESS_NAME[i-1] + color);
				break;
			}
		}
	}

	public void setSelected(boolean select) {
		if (select) {
			setStyleName("gwt-Image Chess-Selected");
		} else {
			setStyleName("gwt-Image");
		}
		
	}
	
	public void setCanMove(boolean canmove) {
		if (canmove) {
			setStyleName("gwt-Image Chess-CanMove");
		} else {
			setStyleName("gwt-Image");
		}
	}
}
