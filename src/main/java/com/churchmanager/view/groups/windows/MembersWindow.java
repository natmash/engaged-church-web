package com.churchmanager.view.groups.windows;

import com.churchmanager.dao.impl.RealTimeGroupDao;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MembersWindow extends Window {

	private static final long serialVersionUID = 1L;

	private final String group;

	public MembersWindow(final String group) {
		this.group = group;
		init();
	}

	private void init() {
		final Window window = new Window();
		final VerticalLayout subContent = new VerticalLayout();
		subContent.setMargin(true);

		window.setClosable(true);
		window.setModal(true);
		window.setResizable(false);
		window.setImmediate(true);
		window.setContent(subContent);

		subContent.addComponent(new Label(group));
		final Table table = new Table();

		subContent.addComponent(new Button("Apply"));

		new RealTimeGroupDao().getMembers(group);
		
		
		window.center();

		// Open it in the UI
		getUI().addWindow(window);
	}
}
