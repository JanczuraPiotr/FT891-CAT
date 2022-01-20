package sp9pj.gui;

import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sp9pj.engine.NewTransportEvent;
import sp9pj.engine.NewTransportHandler;
import sp9pj.engine.Transport;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ControlPanel extends Pane {

    public ControlPanel() {
        super();
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


        this.transport = new Transport(2, this);

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

        addEventHandler(NewTransportEvent.NEW_TRANSPORT,new NewTransportHandler() {
            public void newTransport(byte [] buffer) {
                onNewTransport(buffer);
            }
        });
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

    public void onNewTransport(byte[] buffer) {
        dashboard.appendText(new String(buffer, StandardCharsets.UTF_8));
        dashboard.appendText("\n-----------------------------------------------------------\n");
        dashboard.setScrollTop(Double.MAX_VALUE);
    }

    private final SimpleDateFormat tf;
    private final VBox mainLayout;
    private final HBox header;
    private final TextArea dashboard;
    private final HBox statusBar;

}
