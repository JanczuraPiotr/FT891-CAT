package sp9pj.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sp9pj.engine.NewTransportEvent;
import sp9pj.engine.NewTransportHandler;
import sp9pj.engine.Transport;

import java.util.Arrays;

public class ControlPanel extends Pane {

    public ControlPanel() {
        super();
        this.transport = new Transport(2, this);

        VBox mainBox = new VBox(2);

        Button startButton = new Button("Start");
        startButton.setOnAction(value->transportStartListening());

        Button stopButton = new Button("Stop");
        stopButton.setOnAction(value->transportStopListening());

        mainBox.getChildren().add(startButton);
        mainBox.getChildren().add(stopButton);

        getChildren().add(mainBox);

        addEventHandler(NewTransportEvent.NEW_TRANSPORT,new NewTransportHandler() {
            public void newTransport(byte [] buffer) {
                onNewTransport(buffer);
            }
        });
    }

    public void transportConnect()
    {
        transport.connect();
    }

    public void transportStartListening()
    {
        transport.connect();
        transport.startListening();
    }

    public void transportStopListening()
    {
        transportClose();
    }

    public void transportClose()
    {
        transport.close();
    }

    private final Transport transport;

    public void onNewTransport(byte[] buffer) {
        System.out.print("Bufor dostarczony do ControlPanel: ");
        System.out.println(Arrays.toString(buffer));
    }
}
