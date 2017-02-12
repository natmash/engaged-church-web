package com.churchmanager.util;

import com.churchmanager.constants.DbConstants;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.ast.Db;
import com.rethinkdb.net.Connection;

public class DatabaseUtil {

	public static Connection connection() {
		final RethinkDB r = RethinkDB.r;
		return r.connection().hostname(DbConstants.DB_HOST).port(DbConstants.DB_PORT).connect();
	}
	
	public static Db db(){
		return RethinkDB.r.db(DbConstants.DB_NAME);
	}
}