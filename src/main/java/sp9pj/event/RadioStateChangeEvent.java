package sp9pj.event;

public class RadioStateChangeEvent {

    public RadioStateChangeEvent(String message) {
        this.message = message;
        System.out.println("MessageEvent = " + message);
    }

    public String getMessage() {
        return message;
    }

    private final String message;
}
