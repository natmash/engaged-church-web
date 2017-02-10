package com.churchmanager.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;

import com.churchmanager.dao.GroupDao;
import com.churchmanager.dto.Group;
import com.google.gson.Gson;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

public class RealTimeGroupDao implements GroupDao {

	public Group getGroup(final String name) {
		return null;
	}

	public List<Group> getAll() {
		final List<Group> groups = new ArrayList<>();
		RethinkDB r = RethinkDB.r;
		Connection conn = r.connection().hostname("localhost").port(28015).connect();

		Cursor<?> cursor = r.db("church").table("groups").run(conn);
		Gson gson = new Gson();
		for (Object obj : cursor) {
			Group g = gson.fromJson(obj.toString(), Group.class);
			groups.add(g);
		}
		return groups;
	}

	public void storeGroup(final Group group) {

	}

	public void deleteGroup(@Bind("name") final String name) {

	}

	public void updateGroup(final Group group, final String updatedName) {
		final String oldName = group.getName();
		group.setName(updatedName);
		storeGroup(group);
		deleteGroup(oldName);
	}
}
