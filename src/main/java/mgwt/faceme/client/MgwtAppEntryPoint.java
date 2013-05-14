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

import java.util.List;

import mgwt.faceme.client.core.model.ChessPosition;
import mgwt.faceme.client.core.model.Constant;
import mgwt.faceme.client.model.entities.Message;
import mgwt.faceme.client.model.entities.User;
import mgwt.faceme.client.view.GamePanel;
import mgwt.faceme.shared.DatabaseService;
import mgwt.faceme.shared.DatabaseServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;
import com.googlecode.mgwt.ui.client.dialog.ConfirmDialog;
import com.googlecode.mgwt.ui.client.dialog.ConfirmDialog.ConfirmCallback;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.RoundPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.googlecode.mgwt.ui.client.widget.celllist.Cell;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedHandler;
import com.sangnd.gwt.channelapi.client.Channel;
import com.sangnd.gwt.channelapi.client.ChannelListener;
import com.sangnd.gwt.gson.shared.GwtGsonService;
import com.sangnd.gwt.gson.shared.GwtGsonServiceAsync;

/**
 * @author heroandtn3
 * 
 */
public class MgwtAppEntryPoint implements EntryPoint {

	private RoundPanel playPanel;
	private AnimationHelper animationHelper;
	private Button butPlayOffline;
	private GamePanel gamePanel;
	private RoundPanel loginPanel;
	private MTextBox tbFullName;
	private MTextBox tbPassword;
	private LayoutPanel homePanel;
	private Button butPlayOnline;
	private DatabaseServiceAsync databaseService = GWT
			.create(DatabaseService.class);
	private MTextBox tbEmail;
	private LayoutPanel onlineUserPanel;
	private CellList<User> cellListUserOnline;
	private User currentUser;
	private List<User> allUsers;
	private Channel channel;
	private int currentSelectedIndex = -1;
	private GwtGsonServiceAsync gwtGsonService = GWT
			.create(GwtGsonService.class);
	private boolean inviting;
	private String clientIdPlayWith;
	private boolean playOnline = false;

	@Override
	public void onModuleLoad() {
		// set viewport and other settings for mobile
		MGWT.applySettings(MGWTSettings.getAppSetting());
		animationHelper = new AnimationHelper();
		RootPanel.get().add(animationHelper);
		RootPanel.getBodyElement().getStyle().setProperty("padding", "0px");
		RootPanel.getBodyElement().getStyle().setProperty("margin", "0px");
		initHomePanel();
		initPlayPanel();
		initLoginForm();

		animationHelper.goTo(homePanel, Animation.FADE);

	}

	private void initHomePanel() {
		homePanel = new LayoutPanel();
		butPlayOffline = new Button("Play offline");
		homePanel.add(butPlayOffline);
		butPlayOffline.addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				animationHelper.goTo(playPanel, Animation.SLIDE);

			}
		});

		butPlayOnline = new Button("Play online");
		homePanel.add(butPlayOnline);
		butPlayOnline.addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				animationHelper.goTo(loginPanel, Animation.SLIDE);

			}
		});
	}

	private void initPlayPanel() {
		playPanel = new RoundPanel();
		playPanel.getElement().getStyle().setProperty("padding", "10px");
		playPanel.getElement().getStyle().setProperty("margin", "10px auto");
		gamePanel = new GamePanel();
		gamePanel.getElement().getStyle().setProperty("padding", "0px");
		gamePanel.getElement().getStyle().setProperty("margin", "0px");
		gamePanel.getElement().getStyle().setProperty("width", "100%");
		gamePanel.getElement().getStyle().setProperty("height", "100%");
		playPanel.add(gamePanel);
		
		Window.enableScrolling(false);
		resizeScreen();
		
		Window.addResizeHandler(new ResizeHandler() {
		    @Override
		    public void onResize(ResizeEvent event) {
		    	resizeScreen();
		    }
		});
		

		playPanel.addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent e) {
				int x = e.getStartX();
				int y = e.getStartY();
				System.out.println(x + " - " + y);
				ChessPosition pos = gamePanel.convertToChessPos(x, y);

				// kiem tra tinh hop le
				// loai bo neu la null
				if (pos == null)
					return;

				gamePanel.getMatch().setPos(pos);
				if (playOnline) {
					sendMoving(pos);
				}
				
			}
		});
	}
	
	private void resizeScreen() {
		int w = Window.getClientWidth();
		int h = Window.getClientHeight();
		float rw = (float)w/Constant.SCREEN_WIDTH;
		float rh = (float)h/Constant.SCREEN_HEIGHT;
		if (rw < rh) {
			Constant.SCREEN_RATIO = rw;
		} else {
			Constant.SCREEN_RATIO = rh;
		}
		String width = ((int)((float)Constant.SCREEN_WIDTH*Constant.SCREEN_RATIO) - 40) + "px";
		String height = ((int)((float)Constant.SCREEN_HEIGHT*Constant.SCREEN_RATIO) - 40) + "px";
		playPanel.setWidth(width);
		playPanel.setHeight(height); 
		gamePanel.getElement().getStyle().setProperty("backgroundSize", width + " " + height);
		gamePanel.reDrawChess();
	}

	private void sendMoving(ChessPosition pos) {
		gwtGsonService.toJson(pos, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(String result) {
				send(clientIdPlayWith,
						new Message("move", currentUser.getEmail(), result));
			}
		});
	}

	private void receiveMoving(ChessPosition pos) {
		gamePanel.getMatch().setPos(pos);
	}

	private void initLoginForm() {

		loginPanel = new RoundPanel();
		WidgetList loginForm = new WidgetList();
		loginPanel.add(loginForm);
		tbFullName = new MTextBox();
		loginForm.add(new FormListEntry("Full name", tbFullName));
		tbEmail = new MTextBox();
		loginForm.add(new FormListEntry("Email", tbEmail));
		tbPassword = new MTextBox();
		loginForm.add(new FormListEntry("Password", tbPassword));
		Button butSignup = new Button("Sign in");
		loginForm.add(butSignup);
		butSignup.addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				databaseService.getUserByEmail(tbEmail.getText(),
						new AsyncCallback<User>() {

							@Override
							public void onSuccess(User result) {
								checkLogin(result);
							}

							@Override
							public void onFailure(Throwable caught) {
							}
						});
			}
		});
	}

	private void checkLogin(User user) {
		if (user != null) {
			if (user.getPassword().equals(tbPassword.getText())) {
				initOnlineUserPanel();
				animationHelper.goTo(onlineUserPanel, Animation.FADE);
				this.setCurrentUser(user);
				createChannel(currentUser.getEmail());
				System.out.println(currentUser.getFullName()
						+ " join with clientId: " + currentUser.getEmail());

			} else {
				System.out.println("Login that bai");
			}
		} else {
			this.currentUser = new User(tbFullName.getText(),
					tbEmail.getText(), tbPassword.getText());
			insertUser(this.currentUser);
		}
	}

	private void setCurrentUser(User user) {
		this.currentUser = user;
		this.setUserOnline(user.getEmail(), true);
	}

	private void setUserOnline(String email, boolean online) {
		databaseService.updateUserOnlineStatus(email, online,
				new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Void result) {
					}
				});
	}

	public interface Template extends SafeHtmlTemplates {
		@SafeHtmlTemplates.Template("{0} <span style=\"float: right;\">Olline: {1} - Playing: {2}</span>")
		SafeHtml content(String text, boolean online, boolean playing);
	}

	private void initOnlineUserPanel() {

		onlineUserPanel = new LayoutPanel();
		cellListUserOnline = new CellList<User>(new Cell<User>() {
			private Template TEMPLATE = GWT.create(Template.class);

			@Override
			public void render(SafeHtmlBuilder safeHtmlBuilder, User model) {
				safeHtmlBuilder.append(TEMPLATE.content(model.getFullName(),
						model.isOnline(), model.isPlaying()));

			}

			@Override
			public boolean canBeSelected(User model) {
				return true;
			}
		});
		cellListUserOnline.setRound(true);
		cellListUserOnline.setGroup(false);
		cellListUserOnline.addCellSelectedHandler(new CellSelectedHandler() {

			@Override
			public void onCellSelected(CellSelectedEvent event) {
				if (currentSelectedIndex != -1) {
					cellListUserOnline.setSelectedIndex(currentSelectedIndex,
							false);
				}
				currentSelectedIndex = event.getIndex();
				cellListUserOnline.setSelectedIndex(currentSelectedIndex, true);
			}
		});
		onlineUserPanel.add(cellListUserOnline);

		Button butInvite = new Button("Invite");
		butInvite.addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				invitePlayer();
			}

		});
		onlineUserPanel.add(butInvite);

		Button butRefresh = new Button("Refresh");
		butRefresh.addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				updateUserCellList();
			}
		});

		onlineUserPanel.add(butRefresh);
		Button butLogout = new Button("Logout");
		butLogout.addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				channel.close();
				animationHelper.goTo(loginPanel, Animation.SLIDE_REVERSE);
			}
		});
		onlineUserPanel.add(butLogout);
		updateUserCellList();

	}

	private void invitePlayer() {
		User friend = null;
		if (currentSelectedIndex != -1) {
			friend = allUsers.get(currentSelectedIndex);
		}
		if (friend != null) {
			this.clientIdPlayWith = friend.getEmail();
			this.playOnline = true;
			this.inviting = true;
			send(clientIdPlayWith, new Message("play", currentUser.getEmail(),
					null));

		}
	}

	private void send(final String email, Message message) {
		gwtGsonService.toJson(message, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Error when convert message to Json");
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(String result) {
				channel.sendMessage(email, result);
			}
		});
	}

	private void aprovePlay(String clientId) {
		this.clientIdPlayWith = clientId;
		this.playOnline = true;
		send(clientId, new Message("aprove", currentUser.getEmail(), null));
		animationHelper.goTo(playPanel, Animation.FADE);
	}

	private void denyPlay() {

	}

	private void updateUserCellList() {
		databaseService.getAllUsers(new AsyncCallback<List<User>>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<User> result) {
				allUsers = result;
				cellListUserOnline.render(allUsers);
			}
		});
	}

	private void aClientJoin() {
		if (allUsers != null) {
			for (User user : allUsers) {
				send(user.getEmail(),
						new Message("join", currentUser.getEmail(), null));
			}
		}
	}

	private void aClientClose() {
		if (allUsers != null) {
			for (User user : allUsers) {
				send(user.getEmail(),
						new Message("close", currentUser.getEmail(), null));
			}
		}
	}

	private void receiveMessage(final Message message) {
		if (message.getType().equals("join")) {
			updateUserCellList();
			System.out.println(message.getFromClientId() + " has been joined!");
		} else if (message.getType().equals("close")) {
			updateUserCellList();
			System.out.println(message.getFromClientId() + " has been closed!");
		} else if (message.getType().equals("play")) {
			new ConfirmDialog("Invite", "A freind invite you",
					new ConfirmCallback() {

						@Override
						public void onOk() {
							aprovePlay(message.getFromClientId());
						}

						@Override
						public void onCancel() {
							denyPlay();
						}
					}).show();
		} else if (message.getType().equals("aprove")) {
			if (inviting == true) {
				animationHelper.goTo(playPanel, Animation.FADE);
				inviting = false;

			}
		} else if (message.getType().equals("move")) {
			gwtGsonService.fromJson(message.getContent(), new ChessPosition(),
					new AsyncCallback<IsSerializable>() {

						@Override
						public void onFailure(Throwable caught) {
							
						}

						@Override
						public void onSuccess(IsSerializable result) {
							if (result instanceof ChessPosition) {
								receiveMoving((ChessPosition) result);
							}
						}
					});
		} else {
			System.out.println("what the hell");
		}
	}

	private void createChannel(String clientId) {
		channel = new Channel(clientId);
		channel.join(new ChannelListener() {

			@Override
			public void onOpen() {
				System.out.println("On open...");
				aClientJoin();
			}

			@Override
			public void onMessage(String message) {
				System.out.println("On message..." + message);
				gwtGsonService.fromJson(message, new Message(),
						new AsyncCallback<IsSerializable>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(IsSerializable result) {
								if (result instanceof Message) {
									System.out.println("Ok");
									receiveMessage((Message) result);
								} else {
									System.out.println("Oh sis");
								}

							}
						});
			}

			@Override
			public void onError(int code, String description) {
				System.out.println("On error...");

			}

			@Override
			public void onClose() {
				System.out.println("On close...");
				if (currentUser != null) {
					setUserOnline(currentUser.getEmail(), false);
					System.out.println(currentUser.getFullName() + " logout!");
					aClientClose();
				}

			}
		});
	}

	private void insertUser(User user) {
		databaseService.insertUser(user, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Void result) {
				System.out.println("Dang ky tai khoan xong");

			}
		});
	}
}
