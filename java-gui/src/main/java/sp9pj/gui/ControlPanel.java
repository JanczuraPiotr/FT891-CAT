package sp9pj.gui;

import com.google.common.eventbus.EventBus;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sp9pj.event.RadioStateChangeEvent;
import sp9pj.listener.RadioStateChangeListener;
import sp9pj.engine.Transport;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ControlPanel extends Pane {

    public ControlPanel(EventBus eventBus) {
        super();

        this.eventBus = eventBus;
        this.eventBus.register(new RadioStateChangeListener(s -> {
            messageEventEvent(s);
            return null;
        }));
        /// @Task usunwanie listenera

        tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS | ");
        mainLayout = new VBox(3);
        mainLayout.prefWidthProperty().bind(this.widthProperty());
        mainLayout.prefHeightProperty().bind(this.heightProperty());

        header = new HBox(3);
        header.prefWidthProperty().bind(mainLayout.widthProperty());



        dashboard = new TextArea();
        dashboard.setStyle("-fx-font-family: monospace");
        dashboard.prefWidthProperty().bind(mainLayout.widthProperty());
        dashboard.prefHeightProperty().bind(mainLayout.heightProperty());

        statusBar = new HBox(3);
        statusBar.prefWidthProperty().bind(mainLayout.widthProperty());
        statusBar.prefHeightProperty().set(100);


        this.transport = new Transport(eventBus, 1, this);

        VBox mainBox = new VBox(2);

        Button connectBtn = new Button("Connect");
        connectBtn.setOnAction(value->transportConnect());

        Button disconnectBtn = new Button("Disconnect");
        disconnectBtn.setOnAction(value-> transportDisconnect());

        Button startButton = new Button("Start");
        startButton.setOnAction(value->transportStartListening());

        Button stopButton = new Button("Stop");
        stopButton.setOnAction(value->transportStopListening());

        header.getChildren().addAll(connectBtn, disconnectBtn, startButton, stopButton);

        mainLayout.getChildren().addAll(header, dashboard, statusBar);

        getChildren().add(mainLayout);

    }

    public void connect()
    {

    }

    public void disconnect()
    {

    }

    public void exit()
    {
        transportDisconnect();
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

    }

    public void transportDisconnect()
    {
        transport.close();
    }

    private final Transport transport;

    public void messageEventEvent(RadioStateChangeEvent messageEvent) {
        System.out.println(tf.format(new Date()) + "ControlPanel::onNewTransport -> str : " + messageEvent.getMessage());
        dashboard.appendText(tf.format(new Date()) + messageEvent.getMessage());
        dashboard.appendText("\n");
    }

    private final SimpleDateFormat tf;
    private final VBox mainLayout;
    private final HBox header;
    private final TextArea dashboard;
    private final HBox statusBar;
    private EventBus eventBus = new EventBus();
}
