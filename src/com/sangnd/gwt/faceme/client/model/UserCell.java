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
package com.sangnd.gwt.faceme.client.model;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.googlecode.mgwt.ui.client.widget.celllist.Cell;

/**
 * @author heroandtn3
 *
 */
public class UserCell implements Cell<User> {
	
	private static Template TEMPLATE = GWT.create(Template.class);
	
	public interface Template extends SafeHtmlTemplates {
		 @SafeHtmlTemplates.Template("<div>{0}</div>")
		 SafeHtml content(String name);
	}

	/**
	 * 
	 */
	public UserCell() {
	}

	@Override
	public void render(SafeHtmlBuilder safeHtmlBuilder, User model) {
		if (model == null) return;
		SafeHtml content = TEMPLATE.content(model.getName());
		safeHtmlBuilder.append(content);
	}

	@Override
	public boolean canBeSelected(User model) {
		return true;
	}

}
