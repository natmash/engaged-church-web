package com.churchmanager.view.users;

import com.churchmanager.event.DashboardEventBus;
import com.churchmanager.view.util.ViewUtil;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public final class UsersView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public UsersView() {
		setSizeFull();
		addStyleName("users");
		DashboardEventBus.register(this);

		addComponent(ViewUtil.buildHeader("Users"));

		final UsersTable table = new UsersTable();
		addComponent(table);
		setExpandRatio(table, 1);
	}

	@Override
	public void detach() {
		super.detach();
		DashboardEventBus.unregister(this);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
