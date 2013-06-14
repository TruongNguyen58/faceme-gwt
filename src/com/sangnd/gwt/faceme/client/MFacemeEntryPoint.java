package com.sangnd.gwt.faceme.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.mvp.client.AnimatableDisplay;
import com.googlecode.mgwt.mvp.client.AnimatingActivityManager;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.sangnd.gwt.faceme.client.activities.AppPlaceHistoryMapper;
import com.sangnd.gwt.faceme.client.activities.PhoneActivityMapper;
import com.sangnd.gwt.faceme.client.activities.PhoneAnimationMapper;
import com.sangnd.gwt.faceme.client.activities.home.HomePlace;


public class MFacemeEntryPoint implements EntryPoint {

	private void start() {

		// set viewport and other settings for mobile
		MGWT.applySettings(MGWTSettings.getAppSetting());

		final ClientFactory clientFactory = new ClientFactoryImpl();

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper = GWT
				.create(AppPlaceHistoryMapper.class);
		final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				historyMapper);

		historyHandler.register(clientFactory.getPlaceController(),
				clientFactory.getEventBus(), new HomePlace());

		createPhoneDisplay(clientFactory);

		historyHandler.handleCurrentHistory();

	}

	private void createPhoneDisplay(ClientFactory clientFactory) {
		AnimatableDisplay display = GWT.create(AnimatableDisplay.class);

		PhoneActivityMapper appActivityMapper = new PhoneActivityMapper(
				clientFactory);

		PhoneAnimationMapper appAnimationMapper = new PhoneAnimationMapper();

		AnimatingActivityManager activityManager = new AnimatingActivityManager(
				appActivityMapper, appAnimationMapper,
				clientFactory.getEventBus());

		activityManager.setDisplay(display);

		RootPanel.get().add(display);

	}

	@Override
	public void onModuleLoad() {

		/*
		 * GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
		 * 
		 * @Override public void onUncaughtException(Throwable e) { // TODO put
		 * in your own meaninful handler Window.alert("uncaught: " +
		 * e.getMessage()); e.printStackTrace();
		 * 
		 * } });
		 * 
		 * new Timer() {
		 * 
		 * @Override public void run() { start();
		 * 
		 * } }.schedule(1);
		 */
		RootPanel.getBodyElement().getStyle().setProperty("margin", "0px");
		start();

	}

}
