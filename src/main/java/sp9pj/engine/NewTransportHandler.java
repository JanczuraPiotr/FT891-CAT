package sp9pj.engine;

import javafx.event.EventHandler;

public abstract class NewTransportHandler implements EventHandler<NewTransportEvent> {

    public abstract void newTransport(byte [] buffer);

    @Override
    public void handle(NewTransportEvent event) {
        event.invokeHandler(this);
    }
}
