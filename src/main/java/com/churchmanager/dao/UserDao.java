package com.churchmanager.dao;

import java.util.List;

import com.churchmanager.dto.User;

public interface UserDao {

	public User getUser(final String username);

	public List<User> getAll();

	public void storeUser(final User user);
}
