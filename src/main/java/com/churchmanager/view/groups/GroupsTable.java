package com.churchmanager.view.groups;

import java.util.List;

import com.churchmanager.dao.GroupDao;
import com.churchmanager.dao.impl.RealTimeGroupDao;
import com.churchmanager.dto.Group;
import com.churchmanager.dto.Status;
import com.churchmanager.view.groups.windows.DeleteWindow;
import com.churchmanager.view.groups.windows.MembersWindow;
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

public class GroupsTable extends Table {

	private static final long serialVersionUID = 1L;

	private static final String NAME_COLUMN = "name";

	private static final String STATUS_COLUMN = "status";

	private static final String EDIT_COLUMN = "edit";

	public GroupsTable() {
		// JDBCConnectionPool pool = new SimpleJDBCConnectionPool("postgresql",
		// "localhost/church_manager", "church",
		// "password");
		// DBI dbi = new DBI(pool);
		//
		// final GroupDao dao = dbi.onDemand(GroupDao.class);
		addStyleName(ValoTheme.TABLE_NO_STRIPES);
		addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		// setRowHeaderMode(RowHeaderMode.INDEX);
		// setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		setSizeFull();

		final GroupDao dao = new RealTimeGroupDao();
		List<Group> groups = dao.getAll();

		final IndexedContainer container = new IndexedContainer();
		container.addContainerProperty(NAME_COLUMN, String.class, null);
		container.addContainerProperty(STATUS_COLUMN, Status.class, null);
		container.addContainerProperty(EDIT_COLUMN, CssLayout.class, null);

		for (final Group g : groups) {
			final Item item = container.addItem(g.getName());

			item.getItemProperty(NAME_COLUMN).setValue(g.getName());
			item.getItemProperty(STATUS_COLUMN).setValue(g.getStatus());

			final Button memberButton = createMember(g);
			final Button editButton = createEdit(g);
			final Button deleteButton = createDelete(g);

			CssLayout layout = new CssLayout();
			layout.addComponent(memberButton);
			layout.addComponent(editButton);
			layout.addComponent(deleteButton);

			item.getItemProperty(EDIT_COLUMN).setValue(layout);
		}

		setContainerDataSource(container);

		setVisibleColumns(NAME_COLUMN, STATUS_COLUMN, EDIT_COLUMN);
		setColumnAlignment(STATUS_COLUMN, Align.RIGHT);
		setColumnHeaders("Group Name", "Active", "");

		setColumnExpandRatio(NAME_COLUMN, 1);

		setSortContainerPropertyId("name");
		setSortAscending(false);
	}

	// TODO
	private Button createMember(final Group group) {
		final Button editButton = new Button("Members");
		editButton.setEnabled(true);
		editButton.setImmediate(true);
		editButton.setVisible(true);
		editButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				final MembersWindow dialog = new MembersWindow(group.getName());
				getUI().addWindow(dialog);
			}
		});
		return editButton;
	}

	private Button createEdit(final Group group) {
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

				subContent.addComponent(new Label(group.getName()));
				// subContent.addComponent(new Title);

				subContent.addComponent(new Button("Apply"));

				window.center();

				// Open it in the UI
				getUI().addWindow(window);
			}
		});
		return editButton;
	}

	private Button createDelete(final Group group) {
		final Button deleteButton = new Button("Delete");
		deleteButton.setEnabled(true);
		deleteButton.setImmediate(true);
		deleteButton.setVisible(true);
		deleteButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				final DeleteWindow window = new DeleteWindow(group.getName());
				getUI().addWindow(window);
				remove(group.getName());
			}
		});
		return deleteButton;
	}

	private void remove(final String group) {
		removeItem(group);
		// dao remove group here
	}
}
