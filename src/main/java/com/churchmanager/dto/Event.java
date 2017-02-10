package com.churchmanager.dto;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;

public class Event {
	private String name;

	private String description;

	private User owner;

	private Group group;

	private List<User> invited;

	private List<User> attending;

	private List<User> notAttending;

	private List<User> maybeAttending;

	public Collection<User> awaitingResponse() {
		final List<User> allResponses = Lists.newArrayList();
		allResponses.addAll(attending);
		allResponses.addAll(notAttending);
		allResponses.addAll(maybeAttending);

		return CollectionUtils.disjunction(invited, allResponses);
	}
}
