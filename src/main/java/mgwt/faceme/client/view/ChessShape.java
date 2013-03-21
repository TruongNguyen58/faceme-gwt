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
package mgwt.faceme.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;

/**
 * @author heroandtn3
 *
 */
public class ChessShape extends Composite {

	/**
	 * 
	 */
	public ChessShape(String imgUrl) {
		
	}
	
	public ChessShape(Image img) {
		initWidget(new Image(img.getUrl()));
	}
	
	public void setSelected() {
		setStyleName("gwt-Image Chess-Selected");
	}
}
