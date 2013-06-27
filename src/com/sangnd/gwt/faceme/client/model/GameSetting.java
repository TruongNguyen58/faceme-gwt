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

import com.sangnd.gwt.faceme.client.core.model.GameMode;
import com.sangnd.gwt.faceme.client.core.model.Level;
import com.sangnd.gwt.faceme.client.core.model.Side;

/**
 * @author heroandtn3
 * 
 */
public class GameSetting {

	private Level level;
	private Side currentSide;
	private GameMode gameMode;

	/**
	 * 
	 */
	public GameSetting() {
		level = new Level();
		currentSide = Side.FRIEND;
		gameMode = GameMode.PLAY_WITH_COMPUTER;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Side getCurrentSide() {
		return currentSide;
	}

	public void setCurrentSide(Side currentSide) {
		this.currentSide = currentSide;
	}
	
	

}
