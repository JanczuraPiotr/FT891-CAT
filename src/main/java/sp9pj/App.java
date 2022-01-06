package sp9pj;

import java.io.OutputStream;
import java.util.Enumeration;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



// wyłącz : PSO
// włącz : PS1


/**
 * JavaFX App
 */
public class App extends Application {

    public App() {

        this.transport = new Transport(2);
    }

    @Override
    public void start(Stage stage) {

        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);

        stage.show();

        transport.connect();
        //transport.on();
        transport.startListening();
        System.out.println("App::Start");
    }

    @Override
    public void stop() {
        System.out.println("App::Stop");
        //transport.close();
    }

    public static void main(String[] args) {

        launch();
        System.out.println("zamykam program");
    }

    private final Transport transport;

}