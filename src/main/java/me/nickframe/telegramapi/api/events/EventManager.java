package me.nickframe.telegramapi.api.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
	
	private static List<EventHandler> ehs = new ArrayList<EventHandler>();
	
	public static void registrEvent(EventHandler event) {
		ehs.add(event);
	}
	
	public static List<EventHandler> getEventHandlers() {
		return ehs;
	}
	
	public static void callEvent(Event event) {
		for(EventHandler eh : ehs) {
			for(Method m : eh.getClass().getMethods()) {
				if(m.isAnnotationPresent(EventMethod.class)) {
					EventMethod em = m.getAnnotation(EventMethod.class);
					if(event.getEventName().equalsIgnoreCase(em.eventName())){
						try {
							m.invoke(eh, new Object[] {event});
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

}
