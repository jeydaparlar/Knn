package Display;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.TableDataPoint;
import model.TableIris;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import view.ScatterViewTest;

public class MenuView extends Stage {

    public static final String TITLE = "Nuage de points";

    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;

    public MenuView(TableDataPoint observable) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        // Définir les contraintes des colonnes
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50); // La première colonne occupe 50% de la largeur
        ColumnConstraints column2 = new ColumnConstraints();

        column2.setPercentWidth(50); // La deuxième colonne occupe 50% de la largeur
        gridPane.getColumnConstraints().addAll(column1, column2);

        // Créer les éléments de l'interface
        HBox hBoxText = new HBox();
        Text textGeneral = new Text("Logiciel Nuage de points");
        textGeneral.setFill(Color.WHITE);
        textGeneral.setFont(Font.font("Elephant", (double) HEIGHT /18   ));
        hBoxText.getChildren().add(textGeneral);
        hBoxText.setAlignment(Pos.CENTER);
        hBoxText.setMinWidth(WIDTH);
        hBoxText.setPadding(new Insets((double) HEIGHT /7));

        Button button1 = new Button("");
        Image iconNew = new Image("logoNew.png");
        ImageView iconNewImageView = new ImageView(iconNew);
        iconNewImageView.setPreserveRatio(true);
        iconNewImageView.setFitWidth(Double.MAX_VALUE);
        iconNewImageView.setFitHeight(Double.MAX_VALUE);

        double buttonWidth = (double) WIDTH / 2;
        double buttonHeight = HEIGHT - (((double) (HEIGHT / 7) * 2) + (double) HEIGHT / 20);
        iconNewImageView.setFitWidth(buttonWidth-buttonHeight/2);
        iconNewImageView.setFitHeight(buttonHeight- buttonHeight/2);

        // Centre l'image dans l'ImageView (unchanged)
        iconNewImageView.setSmooth(true);
        iconNewImageView.setCache(true);

        button1.setMaxWidth(Double.MAX_VALUE);
        button1.setMaxHeight(Double.MAX_VALUE);
        button1.setPrefHeight(GridPane.USE_COMPUTED_SIZE);
        button1.setGraphic(iconNewImageView);
        button1.setStyle("-fx-background-color: #606060;-fx-border-color: #ffffff; -fx-border-width: 2px; -fx-border-radius: 5px;");
        button1.setOnMouseEntered(event -> {
            button1.setStyle("-fx-background-color: #808080;-fx-border-color: #ffffff; -fx-border-width: 2px; -fx-border-radius: 5px;"); // Changement de couleur au survol
        });
        button1.setOnMouseExited(event -> {
            button1.setStyle("-fx-background-color: #606060;-fx-border-color: #ffffff; -fx-border-width: 2px; -fx-border-radius: 5px;");
        });

        button1.setOnAction(event -> {
            TableDataPoint tableDataPoint = new TableDataPoint();
            ScatterViewTest tableView = new ScatterViewTest(tableDataPoint);
            this.close();
            tableView.show();
        });


        Button button2 = new Button("");
        Image iconFenetre = new Image("logoFenetre.png");
        ImageView iconFenetreImageView = new ImageView(iconFenetre);
        iconFenetreImageView.setPreserveRatio(true);
        iconFenetreImageView.setFitWidth(Double.MAX_VALUE);
        iconFenetreImageView.setFitHeight(Double.MAX_VALUE);

        iconFenetreImageView.setFitWidth(buttonWidth-buttonHeight/2);
        iconFenetreImageView.setFitHeight(buttonHeight- buttonHeight/2);

        // Centre l'image dans l'ImageView (unchanged)
        iconFenetreImageView.setSmooth(true);
        iconFenetreImageView.setCache(true);
        button2.setMaxWidth(Double.MAX_VALUE);
        button2.setMaxHeight(Double.MAX_VALUE);
        button2.setPrefHeight(GridPane.USE_COMPUTED_SIZE);
        button2.setGraphic(iconFenetreImageView);
        button2.setStyle("-fx-background-color: #606060;-fx-border-color: #ffffff; -fx-border-width: 2px; -fx-border-radius: 5px;");
        button2.setOnMouseEntered(event -> {
            button2.setStyle("-fx-background-color: #808080;-fx-border-color: #ffffff; -fx-border-width: 2px; -fx-border-radius: 5px;"); // Changement de couleur au survol
        });
        button2.setOnMouseExited(event -> {
            button2.setStyle("-fx-background-color: #606060;-fx-border-color: #ffffff; -fx-border-width: 2px; -fx-border-radius: 5px;");
        });

        button2.setOnAction(event -> {
            TableDataPoint tableDataPoint = new TableDataPoint();
            ScatterViewTest tableView = new ScatterViewTest(tableDataPoint);
            this.close();
            tableView.show();
        });


        GridPane.setVgrow(button1, Priority.ALWAYS);
        GridPane.setVgrow(button2, Priority.ALWAYS);

        // Ajouter les éléments au GridPane
        gridPane.add(hBoxText, 0, 0, 2, 8); // S'étend sur 2 colonnes
        gridPane.add(button1, 0, 8);
        gridPane.add(button2, 1, 8);
        gridPane.setStyle("-fx-background-color: #4c4c4c;");

        // Créer la scène
        Scene scene = new Scene(gridPane, WIDTH, HEIGHT);
        this.setScene(scene);
        this.show();
        // Removed full screen for demonstration purposes
    }
}