package me.niccolomattei.api.telegram.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventManager {

	private static List<EventHandler> ehs = new ArrayList<>();

	public static void registerEvent(EventHandler event) {
		ehs.add(event);
	}

	public static List<EventHandler> getEventHandlers() {
		return ehs;
	}

	public static void callEvent(Event event) {
		for (EventHandler eh : ehs) {
			for (Method m : eh.getClass().getMethods()) {
				if (m.isAnnotationPresent(EventMethod.class)) {
					if (m.getParameterCount() == 1) {
						if (m.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
							try {
								m.invoke(eh, event);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

}
