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

import java.util.List;

import com.sangnd.gwt.faceme.client.ClientFactory;
import com.sangnd.gwt.faceme.client.activities.play.PlayPlace;
import com.sangnd.gwt.faceme.client.channel.ChannelEvent;
import com.sangnd.gwt.faceme.client.channel.ChannelEventHandler;
import com.sangnd.gwt.faceme.client.channel.ChannelMessage;
import com.sangnd.gwt.faceme.client.channel.ChannelUtility;
import com.sangnd.gwt.faceme.client.core.model.ChessPosition;
import com.sangnd.gwt.faceme.client.core.model.Match;
import com.sangnd.gwt.faceme.client.event.InvitationActionEvent;
import com.sangnd.gwt.faceme.client.event.InvitationActionHandler;
import com.sangnd.gwt.faceme.client.event.InviteUserEvent;
import com.sangnd.gwt.faceme.client.event.InviteUserHandler;
import com.sangnd.gwt.faceme.client.event.NewInvitationEvent;
import com.sangnd.gwt.faceme.client.event.StartPlayEvent;

/**
 * @author heroandtn3
 *
 */
public class RoomImpl implements Room {

	private String opponentId;
	private String currentId;
	private boolean inviting;
	private ChannelUtility channelUtility;
	private ClientFactory clientFactory;
	private Match match;

	/**
	 * 
	 */
	public RoomImpl(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.channelUtility = clientFactory.getChannelUtility();
		
	}
	
	@Override
	public Match getMatch() {
		return match;
	}
	
	@Override
	public void createRoom(String currentId) {
		this.currentId = currentId;
		
		clientFactory.getEventBus().addHandler(ChannelEvent.TYPE, new ChannelEventHandler() {
			
			@Override
			public void onMessage(ChannelEvent event) {
				ChannelMessage message = event.getMessage();
				String content = message.getContent();
				if (content.equals("invite")) {
					System.out.println("Receive invitation from: " + message.getSenderId());
					clientFactory.getGameSession().getInvitations().add(new Invitation(message.getSenderId()));
					clientFactory.getEventBus().fireEvent(new NewInvitationEvent());
				} else if (content.equals("agree")) {
					clientFactory.getUserListDialog().hide();
					doStartMatch();
				} else if (content.equals("refuse")) {
				}
			}
		});
		
		clientFactory.getNotiDialogView().addInvitationActionHandler(new InvitationActionHandler() {
			
			@Override
			public void onAction(InvitationActionEvent event) {
				List<Invitation> invis = clientFactory.getGameSession().getInvitations();
				String fromUserId = invis.get(event.getSelectedIndex()).getFromUserId();
				if (event.isAccept()) {
					agreeInvitationFrom(fromUserId);
					clientFactory.getNotiDialogView().hide();
				} else {
					refuseInvitationFrom(fromUserId);
				}
				invis.remove(event.getSelectedIndex());
			}
		});
		
		clientFactory.getUserListDialog().addInviteUserHandler(new InviteUserHandler() {
			
			@Override
			public void onInvite(InviteUserEvent event) {
				List<User> users = clientFactory.getUserDb().getAllUser();
				String userId = users.get(event.getSelectedIndex()).getId();
				System.out.println(userId + event.getSelectedIndex());
				inviteOpponent(userId);
			}
		});
	}
	
	private void doStartMatch() {
		match = new Match();
		clientFactory.getPlaceController().goTo(new PlayPlace());
	}

	@Override
	public void sendPos(ChessPosition pos) {
		channelUtility.sendMessage(opponentId, null);
	}

	@Override
	public void inviteOpponent(String opId) {
		this.opponentId = opId;
		if (inviting == false) {
			channelUtility.sendMessage(opId, ChannelMessage.create(currentId, "invite"));
			inviting = true;
		}
	}

	@Override
	public void cancelInvitation() {
		opponentId = null;
		inviting = false;
	}

	@Override
	public void agreeInvitationFrom(String userId) {
		this.opponentId = userId;
		channelUtility.sendMessage(userId, ChannelMessage.create(currentId, "agree"));
		doStartMatch();
	}

	@Override
	public void refuseInvitationFrom(String userId) {
		channelUtility.sendMessage(userId, ChannelMessage.create(currentId, "refuse"));
	}

}
