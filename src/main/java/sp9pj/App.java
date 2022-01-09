package sp9pj;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sp9pj.gui.ControlPanel;


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