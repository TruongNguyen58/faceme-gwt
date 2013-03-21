/*

Copyright (c) 2013 heroandtn3 (@sangnd.info), khanhoatink4, igisik

This file is part of Faceme.

Faceme is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Faceme is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package mgwt.faceme.client.core.control;

import java.util.List;

import mgwt.faceme.client.core.model.ChessPosition;
import mgwt.faceme.client.core.model.Match;
import mgwt.faceme.client.core.model.Side;



/**
 * @author heroandtn3
 * @date Jan 30, 2013
 */
public abstract class MoveGenerator {

	protected Match match;
	/**
	 * 
	 */
	public MoveGenerator(Match match) {
		this.match = match;
	}
	
	/**
	 * Ham liet ke tat ca cac nuoc di co the
	 * @param side quan do (Side.RED) hoac quan den (Side.BLACK)
	 * @return danh sach lien ket chua cac phan tu la mang ChessPosition[] co
	 * 2 phan tu chua thong tin buoc di chuyen, trong do:
	 * [0]: vi tri dau
	 * [1]: vi tri cuoi
	 */
	public abstract List<ChessPosition[]> getMoves(Side side);

}
