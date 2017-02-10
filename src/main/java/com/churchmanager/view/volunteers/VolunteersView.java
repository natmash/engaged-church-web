package com.churchmanager.view.volunteers;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.churchmanager.event.DashboardEventBus;
import com.churchmanager.view.util.ViewUtil;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings({ "serial", "unchecked" })
public final class VolunteersView extends VerticalLayout implements View {

	private Button createReport;
	private static final DateFormat DATEFORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
	private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");
	private static final String[] DEFAULT_COLLAPSIBLE = { "country", "city", "theater", "room", "title", "seats" };

	public VolunteersView() {
		setSizeFull();
		addStyleName("users");
		DashboardEventBus.register(this);

		addComponent(ViewUtil.buildHeader("Users"));

		final VolunteersTable table = new VolunteersTable();
		addComponent(table);
		setExpandRatio(table, 1);
	}

	@Override
	public void detach() {
		super.detach();
		// A new instance of UsersView is created every time it's
		// navigated to so we'll need to clean up references to it on detach.
		DashboardEventBus.unregister(this);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
