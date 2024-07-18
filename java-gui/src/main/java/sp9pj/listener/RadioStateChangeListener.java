package sp9pj.listener;

import com.google.common.eventbus.Subscribe;
import sp9pj.event.RadioStateChangeEvent;

import java.util.function.Function;

public class RadioStateChangeListener {

    private final Function<RadioStateChangeEvent, Object> callback;

    public RadioStateChangeListener(Function<RadioStateChangeEvent, Object> callback) {
        this.callback = callback;
    }

    @Subscribe
    public void messageEventEvent(RadioStateChangeEvent event) {
        callback.apply(event);
    }
}
