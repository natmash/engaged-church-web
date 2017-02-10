package com.vaadin.demo.dashboard.view;

import com.churchmanager.view.events.EventsView;
import com.churchmanager.view.groups.GroupsView;
import com.churchmanager.view.users.UsersView;
import com.vaadin.demo.dashboard.view.dashboard.DashboardView;
import com.vaadin.demo.dashboard.view.reports.ReportsView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum DashboardViewType {
	DASHBOARD("dashboard", DashboardView.class, FontAwesome.HOME, true), APP_USERS("App Users", UsersView.class,
			FontAwesome.USER, false), APP_GROUPS("App Groups", GroupsView.class, FontAwesome.GROUP,
					false), EVENTS("Events", EventsView.class, FontAwesome.CALENDAR_O, false), ATTENDANCE("Attendance",
							GroupsView.class, FontAwesome.CHECK_CIRCLE,
							false), VOLUNTEERS("Volunteers", GroupsView.class, FontAwesome.LIFE_SAVER, false), MESSAGES(
									"Messages", ReportsView.class, FontAwesome.COMMENTS,
									true), DIRECTORY("Directory", ReportsView.class, FontAwesome.BOOK, true);

	private final String viewName;
	private final Class<? extends View> viewClass;
	private final Resource icon;
	private final boolean stateful;

	private DashboardViewType(final String viewName, final Class<? extends View> viewClass, final Resource icon,
			final boolean stateful) {
		this.viewName = viewName;
		this.viewClass = viewClass;
		this.icon = icon;
		this.stateful = stateful;
	}

	public boolean isStateful() {
		return stateful;
	}

	public String getViewName() {
		return viewName;
	}

	public Class<? extends View> getViewClass() {
		return viewClass;
	}

	public Resource getIcon() {
		return icon;
	}

	public static DashboardViewType getByViewName(final String viewName) {
		DashboardViewType result = null;
		for (DashboardViewType viewType : values()) {
			if (viewType.getViewName().equals(viewName)) {
				result = viewType;
				break;
			}
		}
		return result;
	}

}
