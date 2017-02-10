package com.churchmanager.view.volunteers;

import java.util.List;

import com.churchmanager.dao.UserDao;
import com.churchmanager.dao.impl.RealTimeUserDao;
import com.churchmanager.dto.Status;
import com.churchmanager.dto.User;
import com.churchmanager.view.groups.windows.DeleteWindow;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class VolunteersTable extends Table {

	private static final long serialVersionUID = 1L;

	private static final String FIRST_NAME_COLUMN = "first_name";

	private static final String LAST_NAME_COLUMN = "last_name";

	private static final String USERNAME = "username";

	private static final String STATUS_COLUMN = "status";

	private static final String EDIT_COLUMN = "edit";

	public VolunteersTable() {
		addStyleName(ValoTheme.TABLE_NO_STRIPES);
		addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		// setRowHeaderMode(RowHeaderMode.INDEX);
		// setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		setSizeFull();

		final List<User> users = queryUsers();
		addUsersToTable(users);
	}

	private List<User> queryUsers() {
		final UserDao dao = new RealTimeUserDao();
		return dao.getAll();
	}

	private void addUsersToTable(final List<User> users) {
		final IndexedContainer container = new IndexedContainer();
		container.addContainerProperty(USERNAME, String.class, null);
		container.addContainerProperty(FIRST_NAME_COLUMN, String.class, null);
		container.addContainerProperty(LAST_NAME_COLUMN, String.class, null);
		container.addContainerProperty(STATUS_COLUMN, Status.class, null);
		container.addContainerProperty(EDIT_COLUMN, CssLayout.class, null);

		for (final User user : users) {
			final Item item = container.addItem(user.getUsername());

			item.getItemProperty(USERNAME).setValue(user.getUsername());
			item.getItemProperty(FIRST_NAME_COLUMN).setValue(user.getFirstName());
			item.getItemProperty(LAST_NAME_COLUMN).setValue(user.getLastName());
			item.getItemProperty(STATUS_COLUMN).setValue(user.getStatus());
			item.getItemProperty(LAST_NAME_COLUMN).setValue(user.getLastName());

			final Button editButton = createEdit(user);
			final Button deleteButton = createDelete(user);

			CssLayout layout = new CssLayout();
			layout.addComponent(editButton);
			layout.addComponent(deleteButton);

			item.getItemProperty(EDIT_COLUMN).setValue(layout);
		}

		setContainerDataSource(container);

		setVisibleColumns(USERNAME, FIRST_NAME_COLUMN, LAST_NAME_COLUMN, STATUS_COLUMN, EDIT_COLUMN);
		setColumnAlignment(STATUS_COLUMN, Align.RIGHT);
		setColumnHeaders("Username", "First Name", "Last Name", "Active", "");

		// setColumnExpandRatio(USERNAME, 1);

		setSortContainerPropertyId("username");
		setSortAscending(false);
	}

	private Button createEdit(final User user) {
		final Button editButton = new Button("Edit");
		editButton.setEnabled(true);
		editButton.setImmediate(true);
		editButton.setVisible(true);
		editButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				final Window window = new Window();
				VerticalLayout subContent = new VerticalLayout();
				subContent.setMargin(true);

				window.setClosable(true);
				window.setModal(true);
				window.setResizable(false);
				window.setImmediate(true);
				window.setContent(subContent);

				subContent.addComponent(new Label(user.getFirstName() + " " + user.getLastName()));
				// subContent.addComponent(new Title);

				subContent.addComponent(new Button("Apply"));

				window.center();

				// Open it in the UI
				getUI().addWindow(window);
			}
		});
		return editButton;
	}

	private Button createDelete(final User user) {
		final Button deleteButton = new Button("Delete");
		deleteButton.setEnabled(true);
		deleteButton.setImmediate(true);
		deleteButton.setVisible(true);
		deleteButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				final DeleteWindow window = new DeleteWindow(user.getFirstName() + " " + user.getLastName());
				getUI().addWindow(window);
				remove(user.getUsername());
			}
		});
		return deleteButton;
	}

	private void remove(final String group) {
		removeItem(group);
		// dao remove group here
	}
}
