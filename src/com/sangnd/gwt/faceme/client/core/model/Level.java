/**
 * Copyright 2013 heroandtn3 (@sangnd.info)
 */
/*
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sangnd.gwt.faceme.client.core.model;

/**
 * @author heroandtn3
 * @date Jan 7, 2013
 */
public class Level {

	private int attack; // tan cong
	private int defence; // phong thu
	private int experience; // kinh nghiem
	private String name;

	/**
	 * 
	 */
	public Level() {
		defaultInit();
	}
	
	public Level(int level) {
		switch (level) {
			case 1:
				name = "American Captain";
				attack = 20;
				defence = 50;
				experience = 60;
				break;
			case 2:
				name = "Thor";
				attack = 63;
				defence = 47;
				experience = 55;
			case 3:
				name = "Iron man";
				attack = 72;
				defence = 59;
				experience = 86;
				break;
			case 4:
				name = "Hulk";
				attack = 91;
				defence = 89;
				experience = 62;
				break;
			default:
				defaultInit();
		}
	}

	private void defaultInit() {
		name = "Computer";
		attack = 50;
		defence = 50;
		experience = 50;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
