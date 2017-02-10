package com.churchmanager.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.churchmanager.dao.UserDao;
import com.churchmanager.dto.User;
import com.google.gson.Gson;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

public class RealTimeUserDao implements UserDao {

	private static final String USER_KEY = "users";

	public User getUser(final String username) {
		RethinkDB r = RethinkDB.r;
		Connection conn = r.connection().hostname("localhost").port(28015).connect();
		Cursor cursor = r.table(USER_KEY).eq("username", username).run(conn);
		return null;

	}

	public List<User> getAll() {
		final List<User> users = new ArrayList<>();

		RethinkDB r = RethinkDB.r;
		Connection conn = r.connection().hostname("localhost").port(28015).connect();

		Cursor cursor = r.db("church").table(USER_KEY).run(conn);
		Gson gson = new Gson();
		for (Object obj : cursor) {
			User u = gson.fromJson(obj.toString(), User.class);
			users.add(u);
		}

		return users;
	}

	public void storeUser(final User user) {

	}
}
