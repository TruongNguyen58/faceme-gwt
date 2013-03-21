/**
 * Program starts from this class
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
package mgwt.faceme.client;

import mgwt.faceme.client.core.model.ChessPosition;
import mgwt.faceme.client.view.GamePanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.RoundPanel;

/**
 * @author Daniel Kurka
 * 
 */
public class MgwtAppEntryPoint implements EntryPoint {
	
	AnimationHelper animationHelper = new AnimationHelper();
	RoundPanel roundPanel = new RoundPanel();

	@Override
	public void onModuleLoad() {
		// set viewport and other settings for mobile
		MGWT.applySettings(MGWTSettings.getAppSetting());
		
		
		RootPanel.get().add(animationHelper);

		final GamePanel gamePanel = new GamePanel();
		
		Button button = new Button("Play");
		button.addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				switchToGame();
				
			}
		});
		
		animationHelper.goTo(button, Animation.FADE);

		
		roundPanel.add(gamePanel);
		
		RootPanel.get().add(roundPanel);
		
		roundPanel.setWidth("483px");
		roundPanel.setVisible(false);
		
		roundPanel.addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent e) {
				int x = e.getStartX();
				int y = e.getStartY();
				ChessPosition pos = gamePanel.convertToChessPos(x, y);
				
				// kiem tra tinh hop le
				// loai bo neu la null
				if (pos == null) return; 
				
				gamePanel.getMatch().setPos(pos);
				
			}
		});
		
	}

	private void switchToGame() {
		animationHelper.goTo(roundPanel, Animation.SLIDE);
		roundPanel.setVisible(true);
	}
}
