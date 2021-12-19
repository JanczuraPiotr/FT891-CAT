package sp9pj;

import com.fazecast.jSerialComm.SerialPort;
import java.io.OutputStream;
import java.util.Enumeration;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        // Start high level operation

        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static Enumeration portList;
    static String messageString = "Hello, world!\n";
    static SerialPort serialPort;
    static OutputStream outputStream;

    public static void main(String[] args) {
        SerialPort[] commPorts = serialPort.getCommPorts();
        for (int i = 0; i < commPorts.length; ++i) {
            System.out.println(commPorts[i].getSystemPortName());
        }


        SerialPort comPort = SerialPort.getCommPorts()[4];
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        OutputStream in = comPort.getOutputStream();
        try {
            in.write( (byte)('P'));
            in.write( (byte)('S'));
            in.write( (byte)('0'));
            in.write( (byte)(';'));
            in.write( (byte)('\n'));
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        comPort.closePort();


        launch();
    }

}