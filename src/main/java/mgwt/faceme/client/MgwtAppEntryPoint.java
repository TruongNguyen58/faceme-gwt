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
import mgwt.faceme.client.model.entities.User;
import mgwt.faceme.client.view.GamePanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.RoundPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.googlecode.mgwt.ui.client.widget.celllist.BasicCell.Template;
import com.googlecode.mgwt.ui.client.widget.celllist.Cell;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedHandler;

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
	private RoundPanel onlineUserPanel;
	private CellList<User> listUserOnline;

	@Override
	public void onModuleLoad() {
		// set viewport and other settings for mobile
		MGWT.applySettings(MGWTSettings.getAppSetting());
		animationHelper = new AnimationHelper();
		RootPanel.get().add(animationHelper);

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
		gamePanel = new GamePanel();
		playPanel.add(gamePanel);
		playPanel.setWidth("483px");

		playPanel.addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent e) {
				int x = e.getStartX();
				int y = e.getStartY();
				ChessPosition pos = gamePanel.convertToChessPos(x, y);

				// kiem tra tinh hop le
				// loai bo neu la null
				if (pos == null)
					return;

				gamePanel.getMatch().setPos(pos);

			}
		});
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
				final String fullName = tbFullName.getText();
				final String email = tbEmail.getText();
				final String password = tbPassword.getText();
				databaseService.getUserByEmail(email,
						new AsyncCallback<User>() {

							@Override
							public void onSuccess(User result) {
								if (result != null) {
									if (result.getPassword().equals(password)) {
										initOnlineUserPanel();
										animationHelper.goTo(onlineUserPanel,
												Animation.FADE);

									} else {
										System.out.println("Login that bai");
									}
								} else {
									User user = new User(fullName, email,
											password);
									insertUser(user);

								}

							}

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}
						});
			}
		});
	}

	private void initOnlineUserPanel() {
		onlineUserPanel = new RoundPanel();
		listUserOnline = new CellList<User>(new Cell<User>() {
			private Template TEMPLATE = GWT.create(Template.class);

			@Override
			public void render(SafeHtmlBuilder safeHtmlBuilder, User model) {
				safeHtmlBuilder.append(TEMPLATE.content("",
						SafeHtmlUtils.htmlEscape(model.getFullName())));

			}

			@Override
			public boolean canBeSelected(User model) {
				return true;
			}
		});
		listUserOnline.setRound(true);
		listUserOnline.addCellSelectedHandler(new CellSelectedHandler() {

			@Override
			public void onCellSelected(CellSelectedEvent event) {
				listUserOnline.setSelectedIndex(event.getIndex(), true);
			}
		});
		onlineUserPanel.add(listUserOnline);
		databaseService.getOnlineUsers(new AsyncCallback<List<User>>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<User> result) {
				listUserOnline.render(result);
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
