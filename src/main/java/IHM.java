//import Display.MenuView;
import Display.MenuView;
import model.TableDataPoint;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ScatterViewTest;

public class IHM extends Application {
    TableDataPoint tableiris = new TableDataPoint();

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        new MenuView(tableiris);
    }

}