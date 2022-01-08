package sp9pj;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sp9pj.engine.Transport;
import sp9pj.gui.ControlPanel;


// wyłącz : PSO
// włącz : PS1


/**
 * JavaFX App
 */
public class App extends Application {

    public App() {
        controlPanel = new ControlPanel();
    }

    @Override
    public void start(Stage stage) {

        var scene = new Scene(controlPanel, 640, 480);
        stage.setScene(scene);
        stage.show();

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

    private final ControlPanel controlPanel;

}