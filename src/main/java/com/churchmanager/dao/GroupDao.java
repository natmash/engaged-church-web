package com.churchmanager.dao;

import java.util.List;

import com.churchmanager.dto.Group;
import com.churchmanager.dto.Member;

public interface GroupDao {

	public Group getGroup(final String name);

	public List<Group> getAll();

	public List<Member> getMembers(final String name);

	public void storeGroup(final Group group);

	public void deleteGroup(final String name);

	public void updateGroup(final Group group, final String updatedName);
}
