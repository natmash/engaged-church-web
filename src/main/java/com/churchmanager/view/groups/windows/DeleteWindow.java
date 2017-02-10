package com.churchmanager.view.groups.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DeleteWindow extends Window {

	private static final long serialVersionUID = 1L;

	private final String group;

	public DeleteWindow(final String group) {
		this.group = group;
		init();
	}

	private void init() {
		VerticalLayout subContent = new VerticalLayout();
		subContent.setMargin(true);

		setClosable(true);
		setModal(true);
		setResizable(false);
		setImmediate(true);
		setContent(subContent);

		subContent.addComponent(new Label("Are you sure you want to delete " + group));

		Button okButton = new Button("Ok");

		center();

		okButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("Deleted " + group);
				close();
			}
		});
		subContent.addComponent(okButton);
	}
}
