package cn.gcte.awalib.event;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

// stolen from fabric api
public abstract class Event<T> {
    protected volatile T invoker;

    public final T invoker() {
        return invoker;
    }

    public abstract void register(T listener);

    public static final class Factory {
        static Set<Event<?>> EVENTS = new HashSet<>();

        public static <T> Event<T> createArrayBacked(Class<? super T> type, Function<List<T>, T> invokerFactory) {
            ArrayListBackedEvent<T> event = new ArrayListBackedEvent<>(type, invokerFactory);
            EVENTS.add(event);
            return event;
        }
    }
}
