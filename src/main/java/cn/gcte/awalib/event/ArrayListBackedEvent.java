package cn.gcte.awalib.event;

import java.util.*;
import java.util.function.Function;

class ArrayListBackedEvent<T> extends Event<T> {
    private final Function<List<T>, T> invokerFactory;
    private final Object lock = new Object();
    private final List<T> handlers;

    ArrayListBackedEvent(Class<? super T> type, Function<List<T>, T> invokerFactory) {
        this.invokerFactory = invokerFactory;
        this.handlers = new ArrayList<>();
        update();
    }

    void update() {
        this.invoker = invokerFactory.apply(handlers);
    }

    @Override
    public void register(T listener) {
        Objects.requireNonNull(listener, "Tried to register a null listener!");

        synchronized (lock) {
            handlers.add(listener);
        }
    }

}
