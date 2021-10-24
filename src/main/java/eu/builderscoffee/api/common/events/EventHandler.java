package eu.builderscoffee.api.common.events;

import lombok.NonNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is used to call or catch events in the BuildersCoffee server environment
 */
public final class EventHandler {

    /**
     * EventHandler
     */
    private ArrayList<EventListener> listeners = new ArrayList<>();

    /**
     * Singleton
     */
    private EventHandler() {
    }

    public static EventHandler getInstance() {
        return EventHandlerHolder.INSTANCE;
    }

    /**
     * Call the event
     *
     * @param event The event
     */
    public void callEvent(@NonNull Event event) {
        if (Objects.isNull(listeners)) return;
        listeners.stream()
                .filter(listener -> listener.getClass().getDeclaredMethods().length > 0)
                .forEach(listener -> {

                    for (Method method : listener.getClass().getDeclaredMethods()) {
                        // Check if the event correspond to the method
                        if (method.isAnnotationPresent(ProcessEvent.class)
                                && method.getParameterTypes().length == 1
                                && method.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
                            try {
                                // Invoke the event method
                                method.invoke(listener, event);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    /**
     * Add event listener
     *
     * @param eventListener The listener
     * @return Returns true if added
     */
    public boolean addListener(@NonNull EventListener eventListener) {
        return listeners.add(eventListener);
    }

    /**
     * Removes event listener
     *
     * @param eventListener The listener
     * @return Returns true if removed
     */
    public boolean removeListener(@NonNull EventListener eventListener) {
        return listeners.remove(eventListener);
    }

    private static class EventHandlerHolder {
        private static final EventHandler INSTANCE = new EventHandler();
    }
}
