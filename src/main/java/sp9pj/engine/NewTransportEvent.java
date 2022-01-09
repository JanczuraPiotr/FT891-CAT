package sp9pj.engine;

import javafx.event.Event;
import javafx.event.EventType;

public class NewTransportEvent extends Event {

    public static final EventType<NewTransportEvent> NEW_TRANSPORT =
            new EventType<>(Event.ANY, "NEW_TRANSPORT");

    public NewTransportEvent(byte[] buffer)
    {
        super(NEW_TRANSPORT);
        readingBuffer = buffer;
    }

    public NewTransportEvent() {
        super(NEW_TRANSPORT);
    }

    public void invokeHandler(NewTransportHandler handler)
    {
        handler.newTransport(readingBuffer);
    }

    public byte[] getReadingBuffer()
    {
        return readingBuffer;
    }

    private byte[] readingBuffer;
}
