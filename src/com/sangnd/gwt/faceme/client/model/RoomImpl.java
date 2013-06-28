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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sangnd.gwt.faceme.client.ClientFactory;
import com.sangnd.gwt.faceme.client.activities.playinit.PlayInitPlace;
import com.sangnd.gwt.faceme.client.channel.ChannelEvent;
import com.sangnd.gwt.faceme.client.channel.ChannelEventHandler;
import com.sangnd.gwt.faceme.client.channel.ChannelMessage;
import com.sangnd.gwt.faceme.client.channel.ChannelUtility;
import com.sangnd.gwt.faceme.client.channel.PositionMessage;
import com.sangnd.gwt.faceme.client.core.model.ChessPosition;
import com.sangnd.gwt.faceme.client.core.model.Side;
import com.sangnd.gwt.faceme.client.event.ChessSelectEvent;
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

	private User opponent;
	private User currentUser;
	private boolean inviting;
	private ChannelUtility channelUtility;
	private ClientFactory clientFactory;

	/**
	 * 
	 */
	public RoomImpl(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.channelUtility = clientFactory.getChannelUtility();
		
	}

	
	@Override
	public void createRoom(User currentUser) {
		this.currentUser = currentUser;
		
		clientFactory.getEventBus().addHandler(ChannelEvent.TYPE, new ChannelEventHandler() {
			
			@Override
			public void onMessage(ChannelEvent event) {
				ChannelMessage message = event.getMessage();
				String content = message.getContent();
				if (content.equals("invite")) {
					System.out.println("Receive invitation from: " + message.getSenderId());
					
					clientFactory.getUserDb().getUserById(message.getSenderId(), new AsyncCallback<User>() {

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
						}

						@Override
						public void onSuccess(User u) {
							if (u != null) {
								clientFactory.getGameSession().getInvitations().add(new Invitation(u));
								clientFactory.getEventBus().fireEvent(new NewInvitationEvent());
							}
						}
						
					});
					
					
				} else if (content.equals("agree")) {
					clientFactory.getUserListDialog().hide();
					clientFactory.getEventBus().fireEvent(new InvitationActionEvent(true));
				} else if (content.equals("refuse")) {
					clientFactory.getEventBus().fireEvent(new InvitationActionEvent(false));
				} else if (content.equals("start")) {
					clientFactory.getEventBus().fireEvent(new StartPlayEvent());
				} else {
					PositionMessage pm = PositionMessage.fromJson(content);
					ChessPosition pos = new ChessPosition(pm.getRow(), pm.getCol());
					pos.setKillable(pm.isKillable());
					clientFactory.getEventBus().fireEvent(new ChessSelectEvent(pos));
				}
			}
		});
		
		clientFactory.getNotiDialogView().addInvitationActionHandler(new InvitationActionHandler() {
			
			@Override
			public void onAction(InvitationActionEvent event) {
				List<Invitation> invis = clientFactory.getGameSession().getInvitations();
				User fromUser = invis.get(event.getSelectedIndex()).getFromUser();
				if (event.isAccept()) {
					agreeInvitationFrom(fromUser);
				} else {
					refuseInvitationFrom(fromUser);
				}
				invis.remove(event.getSelectedIndex());
			}
		});
		
		clientFactory.getUserListDialog().addInviteUserHandler(new InviteUserHandler() {
			
			@Override
			public void onInvite(final InviteUserEvent event) {
				clientFactory.getUserDb().getOnlineUser(new AsyncCallback<List<User>>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(List<User> users) {
						User u = users.get(event.getSelectedIndex());
						inviteOpponent(u);
					}
				});
				
			}
		});
	}

	@Override
	public void sendPos(ChessPosition pos) {
		System.out.println("Sending message to other player...");
		try {
		PositionMessage pm = (PositionMessage) JavaScriptObject.createObject().cast();
		pm.setRow(pos.getRow());
		pm.setCol(pos.getCol());
		pm.setKillable(pos.isKillable());
		ChannelMessage message = ChannelMessage.create(currentUser.getId(), pm.toJson());
		channelUtility.sendMessage(opponent.getId(), message);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void inviteOpponent(User user) {
		opponent = user;
		if (inviting == false) {
			channelUtility.sendMessage(opponent, ChannelMessage.create(currentUser.getId(), "invite"));
			inviting = true;
		}
	}

	@Override
	public void cancelInvitation() {
		opponent = null;
		inviting = false;
	}

	@Override
	public void agreeInvitationFrom(User user) {
		opponent = user;
		try {
		channelUtility.sendMessage(opponent.getId(), ChannelMessage.create(currentUser.getId(), "agree"));
		clientFactory.getGameSetting().setCurrentSide(Side.ENERMY);
		clientFactory.getGameSession().setPlayonline(true);
		clientFactory.getNotiDialogView().hide();
		
		clientFactory.getPlaceController().goTo(new PlayInitPlace());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void refuseInvitationFrom(User user) {
		channelUtility.sendMessage(user, ChannelMessage.create(currentUser.getId(), "refuse"));
	}


	@Override
	public void startPlay() {
		channelUtility.sendMessage(opponent, ChannelMessage.create(currentUser.getId(), "start"));
	}


	@Override
	public User getOpponent() {
		return opponent;
	}

}
