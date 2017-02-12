package com.churchmanager.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;

import com.churchmanager.dao.GroupDao;
import com.churchmanager.dto.Group;
import com.churchmanager.dto.Member;
import com.churchmanager.util.DatabaseUtil;
import com.google.gson.Gson;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

public class RealTimeGroupDao implements GroupDao {

	@Override
	public Group getGroup(final String name) {
		return null;
	}

	@Override
	public List<Group> getAll() {
		final List<Group> groups = new ArrayList<>();
		final Connection conn = DatabaseUtil.connection();
		final Cursor<?> cursor = DatabaseUtil.db().table("groups").run(conn);
		final Gson gson = new Gson();
		for (final Object obj : cursor) {
			final Group g = gson.fromJson(obj.toString(), Group.class);
			groups.add(g);
		}
		conn.close();
		return groups;
	}

	@Override
	public List<Member> getMembers(final String name) {
		final List<Member> members = new ArrayList<>();
		final Connection conn = DatabaseUtil.connection();
		final Cursor<?> cursor = DatabaseUtil.db().table("groups").filter(row -> row.g("name").eq(name)).run(conn);
		for (final Object obj : cursor) {
			System.out.println(obj.toString());
		}
		conn.close();
		return members;
	}

	@Override
	public void storeGroup(final Group group) {

	}

	@Override
	public void deleteGroup(@Bind("name") final String name) {

	}

	@Override
	public void updateGroup(final Group group, final String updatedName) {
		final String oldName = group.getName();
		group.setName(updatedName);
		storeGroup(group);
		deleteGroup(oldName);
	}
}
