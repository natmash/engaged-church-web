package com.churchmanager.view.groups;

import com.churchmanager.view.util.ViewUtil;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public class GroupsView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public GroupsView() {
		setSizeFull();
		addStyleName("groups");

		addComponent(ViewUtil.buildHeader("Groups"));

		final GroupsTable table = new GroupsTable();
		addComponent(table);
		setExpandRatio(table, 1);
	}

	@Override
	public void enter(final ViewChangeEvent event) {
	}
}
