package ui.legui.system.handler;

import ui.legui.system.event.SystemCharEvent;
import ui.legui.system.event.SystemCursorPosEvent;
import ui.legui.system.event.SystemEvent;
import ui.legui.system.event.SystemKeyEvent;
import ui.legui.system.event.SystemMouseClickEvent;
import ui.legui.system.event.SystemScrollEvent;
import ui.legui.system.event.SystemWindowCloseEvent;
import ui.legui.system.event.SystemWindowFocusEvent;
import ui.legui.system.event.SystemWindowIconifyEvent;
import ui.legui.system.event.SystemWindowPosEvent;
import ui.legui.system.event.SystemWindowRefreshEvent;
import ui.legui.system.event.SystemWindowSizeEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ShchAlexander on 1/25/2017.
 */
public class SystemEventHandlerProvider {

    private Map<Class<? extends SystemEvent>, SystemEventHandler<? extends SystemEvent>> processorMap = new ConcurrentHashMap<>();

    private SystemEventHandlerProvider() {
        registerProcessor(SystemWindowCloseEvent.class, new WindowCloseEventHandler());
        registerProcessor(SystemWindowIconifyEvent.class, new WindowIconifyEventHandler());
        registerProcessor(SystemScrollEvent.class, new ScrollEventHandler());
        registerProcessor(SystemWindowFocusEvent.class, new WindowFocusEventHandler());
        registerProcessor(SystemWindowPosEvent.class, new WindowPosEventHandler());
        registerProcessor(SystemWindowRefreshEvent.class, new WindowRefreshEventHandler());
        registerProcessor(SystemWindowSizeEvent.class, new WindowSizeEventHandler());
        registerProcessor(SystemCursorPosEvent.class, new CursorPosEventHandler());
        registerProcessor(SystemMouseClickEvent.class, new MouseClickEventHandler());
        registerProcessor(SystemKeyEvent.class, new KeyEventHandler());
        registerProcessor(SystemCharEvent.class, new CharEventHandler());

    }

    public static SystemEventHandlerProvider getInstance() {
        return LSEPPH.I;
    }

    public <E extends SystemEvent> void registerProcessor(Class<E> eventClass, SystemEventHandler<E> processor) {
        processorMap.put(eventClass, processor);
    }

    public <E extends SystemEvent> SystemEventHandler<E> getProcessor(Class<E> eventClass) {
        return (SystemEventHandler<E>) processorMap.get(eventClass);
    }

    private static class LSEPPH {

        private static final SystemEventHandlerProvider I = new SystemEventHandlerProvider();
    }
}
