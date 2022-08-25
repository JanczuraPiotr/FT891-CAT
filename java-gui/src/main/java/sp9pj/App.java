package sp9pj;

import com.google.common.eventbus.EventBus;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sp9pj.gui.ControlPanel;


/**
 * JavaFX App
 */
public class App extends Application {

    public App() {
        eventBus = new EventBus();
        controlPanel = new ControlPanel(eventBus);
    }

    @Override
    public void start(Stage stage) {

        var scene = new Scene(controlPanel, 640, 480);
        stage.setScene(scene);
        stage.setTitle("FT891 CAT");
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                System.out.println("App::zamknięcie aplikacji.");
                Platform.exit();
                System.exit(0);
            }
        });
        stage.show();

        System.out.println("App::Start");
    }

    @Override
    public void stop() {
        System.out.println("App::Stop");
        controlPanel.exit();
    }

    public static void main(String[] args) {
        launch();
        System.out.println("zamykam program");
    }

    private final ControlPanel controlPanel;
    private final EventBus eventBus;

}