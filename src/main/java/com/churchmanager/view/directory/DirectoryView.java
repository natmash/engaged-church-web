package com.churchmanager.view.directory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.churchmanager.view.util.ViewUtil;
import com.vaadin.demo.dashboard.DashboardUI;
import com.vaadin.demo.dashboard.component.MovieDetailsWindow;
import com.vaadin.demo.dashboard.domain.Movie;
import com.vaadin.demo.dashboard.domain.Transaction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventResize;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;
import com.vaadin.ui.components.calendar.handler.BasicEventMoveHandler;
import com.vaadin.ui.components.calendar.handler.BasicEventResizeHandler;

@SuppressWarnings("serial")
public final class DirectoryView extends CssLayout implements View {

	private Calendar calendar;

	public DirectoryView() {
		setSizeFull();
		addStyleName("schedule");

		addComponent(ViewUtil.buildHeader("Chats"));
		addComponent(buildCalendarView());

	}

	private Component buildCalendarView() {
		VerticalLayout calendarLayout = new VerticalLayout();
		calendarLayout.setMargin(true);

		calendar = new Calendar(new MovieEventProvider());
		calendar.setWidth(100.0f, Unit.PERCENTAGE);
		calendar.setHeight(1000.0f, Unit.PIXELS);

		calendar.setHandler(new EventClickHandler() {
			@Override
			public void eventClick(final EventClick event) {
				MovieEvent movieEvent = (MovieEvent) event.getCalendarEvent();
				MovieDetailsWindow.open(movieEvent.getMovie(), movieEvent.getStart(), movieEvent.getEnd());
			}
		});
		calendarLayout.addComponent(calendar);

		calendar.setHandler(new BasicEventMoveHandler() {
			@Override
			public void eventMove(final MoveEvent event) {
				CalendarEvent calendarEvent = event.getCalendarEvent();
				if (calendarEvent instanceof MovieEvent) {
					MovieEvent editableEvent = (MovieEvent) calendarEvent;

					Date newFromTime = event.getNewStart();

					// Update event dates
					long length = editableEvent.getEnd().getTime() - editableEvent.getStart().getTime();
					setDates(editableEvent, newFromTime, new Date(newFromTime.getTime() + length));
				}
			}

			protected void setDates(final MovieEvent event, final Date start, final Date end) {
				event.start = start;
				event.end = end;
			}
		});
		calendar.setHandler(new BasicEventResizeHandler() {
			@Override
			public void eventResize(final EventResize event) {
				Notification.show("You're not allowed to change the movie duration");
			}
		});

		java.util.Calendar initialView = java.util.Calendar.getInstance();
		initialView.add(java.util.Calendar.DAY_OF_MONTH, -initialView.get(java.util.Calendar.DAY_OF_MONTH) + 1);
		calendar.setStartDate(initialView.getTime());

		initialView.add(java.util.Calendar.MONTH, 1);
		initialView.add(java.util.Calendar.DAY_OF_MONTH, -initialView.get(java.util.Calendar.DAY_OF_MONTH));
		calendar.setEndDate(initialView.getTime());

		return calendarLayout;
	}

	@Override
	public void enter(final ViewChangeEvent event) {
	}

	private class MovieEventProvider implements CalendarEventProvider {

		@Override
		public List<CalendarEvent> getEvents(final Date startDate, final Date endDate) {
			// Users are dynamically fetched from the backend service
			// when needed.
			Collection<Transaction> users = DashboardUI.getDataProvider().getUsersBetween(startDate, endDate);
			List<CalendarEvent> result = new ArrayList<CalendarEvent>();
			for (Transaction transaction : users) {
				Movie movie = DashboardUI.getDataProvider().getMovie(transaction.getMovieId());
				Date end = new Date(transaction.getTime().getTime() + movie.getDuration() * 60 * 1000);
				result.add(new MovieEvent(transaction.getTime(), end, movie));
			}
			return result;
		}
	}

	public final class MovieEvent implements CalendarEvent {

		private Date start;
		private Date end;
		private Movie movie;

		public MovieEvent(final Date start, final Date end, final Movie movie) {
			this.start = start;
			this.end = end;
			this.movie = movie;
		}

		@Override
		public Date getStart() {
			return start;
		}

		@Override
		public Date getEnd() {
			return end;
		}

		@Override
		public String getDescription() {
			return "";
		}

		@Override
		public String getStyleName() {
			return String.valueOf(movie.getId());
		}

		@Override
		public boolean isAllDay() {
			return false;
		}

		public Movie getMovie() {
			return movie;
		}

		public void setMovie(final Movie movie) {
			this.movie = movie;
		}

		public void setStart(final Date start) {
			this.start = start;
		}

		public void setEnd(final Date end) {
			this.end = end;
		}

		@Override
		public String getCaption() {
			return movie.getTitle();
		}

	}

}
