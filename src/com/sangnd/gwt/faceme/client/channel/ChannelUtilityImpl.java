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
package com.sangnd.gwt.faceme.client.channel;

import com.google.web.bindery.event.shared.EventBus;
import com.sangnd.gwt.channelapi.client.Channel;
import com.sangnd.gwt.channelapi.client.ChannelListener;
import com.sangnd.gwt.faceme.client.model.User;

/**
 * @author heroandtn3
 *
 */
public class ChannelUtilityImpl implements ChannelUtility {
	
	private Channel channel;
	private EventBus eventBus;

	/**
	 * 
	 */
	public ChannelUtilityImpl(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void sendMessage(User receiver, ChannelMessage message) {
		channel.sendMessage(receiver.getId(), message.toJson());
	}

	@Override
	public void initChannel(final User user) {
		// workaround: avoid being multiple event firing
		if (channel != null) return;
		channel = new Channel(user.getId());
		channel.join(new ChannelListener() {
			
			@Override
			public void onOpen() {
				System.out.println("Open: " + user.getId());
				
			}
			
			@Override
			public void onMessage(String message) {
				System.out.println("Receive: " + message);
				try {
					ChannelMessage channelMessage = ChannelMessage
							.fromJson(message);
					eventBus.fireEvent(new ChannelEvent(channelMessage));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
			}
			
			@Override
			public void onError(int code, String description) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClose() {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void sendMessage(String receiverId, ChannelMessage message) {
		channel.sendMessage(receiverId, message.toJson());
	}

}
