package com.churchmanager.view.util;

import java.util.Collection;

import com.churchmanager.event.DashboardEventBus;
import com.churchmanager.event.DashboardEvent.NotificationsCountUpdatedEvent;
import com.google.common.eventbus.Subscribe;
import com.vaadin.demo.dashboard.DashboardUI;
import com.vaadin.demo.dashboard.domain.DashboardNotification;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class ViewUtil {

	private static Window notificationsWindow;

	private ViewUtil() {
	}

	public static Component buildHeader(final String title) {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName("viewheader");
		header.setSpacing(true);
		Responsive.makeResponsive(header);

		Label titleLabel = new Label(title);
		titleLabel.setId(title);
		titleLabel.setSizeUndefined();
		titleLabel.addStyleName(ValoTheme.LABEL_H1);
		titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		header.addComponent(titleLabel);

		NotificationsButton button = buildNotificationsButton();
		HorizontalLayout tools = new HorizontalLayout(button);
		tools.setSpacing(true);
		tools.addStyleName("toolbar");
		header.addComponent(tools);

		return header;
	}

	private static NotificationsButton buildNotificationsButton() {
		NotificationsButton result = new NotificationsButton();
		result.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				openNotificationsPopup(event, event.getButton().getUI());
			}
		});
		return result;
	}

	public static final class NotificationsButton extends Button {
		private static final long serialVersionUID = 1L;
		private static final String STYLE_UNREAD = "unread";
		public static final String ID = "dashboard-notifications";

		public NotificationsButton() {
			setIcon(FontAwesome.BELL);
			setId(ID);
			addStyleName("notifications");
			addStyleName(ValoTheme.BUTTON_ICON_ONLY);
			DashboardEventBus.register(this);
		}

		@Subscribe
		public void updateNotificationsCount(final NotificationsCountUpdatedEvent event) {
			System.out.println("got a notification");
			setUnreadCount(DashboardUI.getDataProvider().getUnreadNotificationsCount() + 1);
		}

		public void setUnreadCount(final int count) {
			setCaption(String.valueOf(count));

			String description = "Notifications";
			if (count > 0) {
				addStyleName(STYLE_UNREAD);
				description += " (" + count + " unread)";
			} else {
				removeStyleName(STYLE_UNREAD);
			}
			setDescription(description);
		}
	}

	private static void openNotificationsPopup(final ClickEvent event, final UI ui) {
		VerticalLayout notificationsLayout = new VerticalLayout();
		notificationsLayout.setMargin(true);
		notificationsLayout.setSpacing(true);

		Label title = new Label("Notifications");
		title.addStyleName(ValoTheme.LABEL_H3);
		title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		notificationsLayout.addComponent(title);

		Collection<DashboardNotification> notifications = DashboardUI.getDataProvider().getNotifications();
		DashboardEventBus.post(new NotificationsCountUpdatedEvent());

		for (DashboardNotification notification : notifications) {
			VerticalLayout notificationLayout = new VerticalLayout();
			notificationLayout.addStyleName("notification-item");

			Label titleLabel = new Label(
					notification.getFirstName() + " " + notification.getLastName() + " " + notification.getAction());
			titleLabel.addStyleName("notification-title");

			Label timeLabel = new Label(notification.getPrettyTime());
			timeLabel.addStyleName("notification-time");

			Label contentLabel = new Label(notification.getContent());
			contentLabel.addStyleName("notification-content");

			notificationLayout.addComponents(titleLabel, timeLabel, contentLabel);
			notificationsLayout.addComponent(notificationLayout);
		}

		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		footer.setWidth("100%");
		Button showAll = new Button("View All Notifications", new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				Notification.show("Not implemented in this demo");
			}
		});
		showAll.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		showAll.addStyleName(ValoTheme.BUTTON_SMALL);
		footer.addComponent(showAll);
		footer.setComponentAlignment(showAll, Alignment.TOP_CENTER);
		notificationsLayout.addComponent(footer);

		if (notificationsWindow == null) {
			notificationsWindow = new Window();
			notificationsWindow.setWidth(300.0f, Unit.PIXELS);
			notificationsWindow.addStyleName("notifications");
			notificationsWindow.setClosable(false);
			notificationsWindow.setResizable(false);
			notificationsWindow.setDraggable(false);
			notificationsWindow.setCloseShortcut(KeyCode.ESCAPE, null);
			notificationsWindow.setContent(notificationsLayout);
		}

		if (!notificationsWindow.isAttached()) {
			notificationsWindow.setPositionY(event.getClientY() - event.getRelativeY() + 40);
			ui.addWindow(notificationsWindow);
			notificationsWindow.focus();
		} else {
			notificationsWindow.close();
		}
	}

}
