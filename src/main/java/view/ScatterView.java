package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Iris;
import model.TableIris;
import model.VARIETY;
import utils.KNNAlgo;
import utils.Observable;
import utils.Observer;

import java.io.File;
import java.util.Random;

import static model.Iris.getAxisValue;

public class ScatterView extends Stage implements Observer {

    public static final String TITLE = "Nuage de points";
    private final XYChart.Series<Number, Number> series1;
    private final XYChart.Series<Number, Number> series2;
    private final XYChart.Series<Number, Number> series3;
    private final XYChart.Series<Number, Number> series4;
    public final TableIris observable;
    public final NumberAxis xAxis;
    public final NumberAxis yAxis;
    public final ListView<Iris> listView;
    public final ScatterChart<Number, Number> sc;

    public static final String axeXDefault = "sepal_length";
    public static final String axeYDefault = "sepal_width";


    public ScatterView(TableIris observable) {
        Stage stage = new Stage();
        stage.setTitle(TITLE);
        this.listView = new ListView<>();
        this.observable = observable;
        observable.attach(this);

        xAxis = new NumberAxis(0, 100, 10);
        yAxis = new NumberAxis(0, 100, 10);

        this.sc = new ScatterChart<>(xAxis, yAxis);

        series1 = new XYChart.Series<>();
        series2 = new XYChart.Series<>();
        series3 = new XYChart.Series<>();
        series4 = new XYChart.Series<>();
        series1.setName("Setosa");
        series2.setName("Versicolor");
        series3.setName("Virginica");
        series4.setName("Unknown");


        sc.getData().addAll(series1, series2, series3, series4);
        if (!observable.getIris().isEmpty()) {
            changementAxe(axeXDefault, axeYDefault, this.observable);
        }

        HBox hbox = createButtons();
        VBox vbox = new VBox(sc, hbox);
        Scene scene = new Scene(vbox, 800, 600);
        this.setScene(scene);
        this.setTitle(TITLE);
        this.show();
    }

    private void changementAxe(String axeX, String axeY, TableIris observable) {
        NumberAxis xAxis = (NumberAxis) sc.getXAxis();
        xAxis.setLabel(axeX);
        xAxis.setLowerBound(0);
        if (!observable.getIris().isEmpty()) {
            xAxis.setUpperBound((int) Math.ceil(observable.maxDonnee(axeX)) + 1);
        } else {
            xAxis.setUpperBound(100);
        }

        xAxis.setTickUnit(0.5);

        NumberAxis yAxis = (NumberAxis) sc.getYAxis();
        yAxis.setLabel(axeY);
        yAxis.setLowerBound(0);
        if (!observable.getIris().isEmpty()) {
            yAxis.setUpperBound((int) Math.ceil(observable.maxDonnee(axeY)) + 1);
        } else {
            yAxis.setUpperBound(100);
        }

        yAxis.setTickUnit(0.5);
        remplirGraphique();

        sc.setVisible(true);

    }


    private void remplirGraphique() {
        this.series1.getData().clear();
        this.series2.getData().clear();
        this.series3.getData().clear();
        this.series4.getData().clear();

        //Bulle qui apparaît avec sur les points survolés par la souris
        for (Iris i : this.observable.getIris()) {
            XYChart.Data<Number, Number> data = new XYChart.Data<>(getAxisValue(i,xAxis), getAxisValue(i,yAxis));
            Tooltip tooltip= new Tooltip(xAxis.getLabel() + ": " + getAxisValue(i, xAxis) + ", " + yAxis.getLabel() + " : " + getAxisValue(i, yAxis) + ", Variety: " + i.getVariety());
            if (i.getVariety() == VARIETY.SETOSA) {
                this.series1.getData().add(data);
            } else if (i.getVariety() == VARIETY.VERSICOLOR) {
                this.series2.getData().add(data);
            } else if (i.getVariety() == VARIETY.VIRGINICA) {
                this.series3.getData().add(data);
            } else if (i.getVariety() == null) {
                this.series4.getData().add(data);
            }

            if (data.getNode() != null) {
                Tooltip.install(data.getNode(), tooltip);
            }
        }

    }


    // Ajoutes un nouveau point et l'affiche sur le graphique
    private void classificationDonnee(Iris nouveauPoint) {
        int rdm = new Random().nextInt(3);
        switch (rdm) {
            case 0 -> this.observable.setVariety(VARIETY.SETOSA, nouveauPoint);
            case 1 -> this.observable.setVariety(VARIETY.VERSICOLOR, nouveauPoint);
            case 2 -> this.observable.setVariety(VARIETY.VIRGINICA, nouveauPoint);
            default -> this.observable.setVariety(null, nouveauPoint);
        }

        remplirGraphique();
    }


    // Affiche un nouveau point sur le graphique met la forme en bordure "noir" si c'est un Versicolor,
    // en rouge si c'est un Setosa et en vert si c'est un Virginica
    private void affichageNouveauPoint(Iris nouveauPoint) {
        if (xAxis.getLabel() == null) {
            xAxis.setLabel(axeXDefault);
        }
        if (yAxis.getLabel() == null) {
            yAxis.setLabel(axeYDefault);
        }
        series4.getData().add(new XYChart.Data<>(getAxisValue(nouveauPoint, xAxis), getAxisValue(nouveauPoint, yAxis)));
        if (nouveauPoint.getVariety() == VARIETY.SETOSA) {
            series1.getNode().setStyle("-fx-stroke: red;");
        } else if (nouveauPoint.getVariety() == VARIETY.VERSICOLOR) {
            series2.getNode().setStyle("-fx-stroke: black;");
        } else if (nouveauPoint.getVariety() == VARIETY.VIRGINICA) {
            series3.getNode().setStyle("-fx-stroke: green;");
        }
        this.observable.addIris(nouveauPoint);
        remplirGraphique();
    }

    private void openNewScatterChart() {
        new ScatterView(this.observable);
    }

    private void changeAxes() {
        Stage modifAxes = new Stage();
        modifAxes.initModality(Modality.APPLICATION_MODAL);
        modifAxes.initOwner(this);
        modifAxes.setTitle("Changer les axes");
        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        Label labelX = new Label("Axe X : ");
        ComboBox<String> comboX = new ComboBox<>();
        comboX.getItems().addAll("sepal_length", "sepal_width", "petal_length", "petal_width");

        Label labelY = new Label("Axe Y : ");
        ComboBox<String> comboY = new ComboBox<>();
        comboY.getItems().addAll("sepal_length", "sepal_width", "petal_length", "petal_width");

        hbox1.getChildren().addAll(labelX, comboX);
        hbox2.getChildren().addAll(labelY, comboY);
        vbox.getChildren().addAll(hbox1, hbox2);
        Button valider = new Button("Valider");
        valider.setOnAction(event -> {
            this.series1.getData().clear();
            this.series2.getData().clear();
            this.series3.getData().clear();
            this.series4.getData().clear();
            changementAxe(comboX.getValue(), comboY.getValue(), this.observable);
            modifAxes.close();
        });
        vbox.getChildren().add(valider);
        Scene scene = new Scene(vbox, 300, 200);
        modifAxes.setScene(scene);
        modifAxes.show();
    }

    private void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir un fichier texte");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(this);

        if (selectedFile != null) {
            this.observable.ajouterCsv(selectedFile.getAbsolutePath());
            changementAxe(axeXDefault, axeYDefault, this.observable);
        }
    }

    private void addPoint() {
        Stage ajoutPoint = new Stage();
        ajoutPoint.initModality(Modality.APPLICATION_MODAL);
        ajoutPoint.initOwner(this);
        ajoutPoint.setTitle("Ajouter un point");
        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();
        HBox hbox4 = new HBox();
        Label label1 = new Label("sepal_length : ");
        TextField sepal_length = new TextField();
        Label label2 = new Label("sepal_width : ");
        TextField sepal_width = new TextField();
        Label label3 = new Label("petal_length : ");
        TextField petal_length = new TextField();
        Label label4 = new Label("petal_width : ");
        TextField petal_width = new TextField();
        hbox1.getChildren().addAll(label1, sepal_length);
        hbox2.getChildren().addAll(label2, sepal_width);
        hbox3.getChildren().addAll(label3, petal_length);
        hbox4.getChildren().addAll(label4, petal_width);
        vbox.getChildren().addAll(hbox1, hbox2, hbox3, hbox4);
        Button valider = new Button("Valider");
        valider.setOnAction(event -> {
            try {
                Iris nouveauPoint = new Iris(Double.parseDouble(sepal_length.getText()), Double.parseDouble(sepal_width.getText()),
                        Double.parseDouble(petal_length.getText()), Double.parseDouble(petal_width.getText()), null, true);
                nouveauPoint.setVariety(null);
                affichageNouveauPoint(nouveauPoint);
                ajoutPoint.close();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez entrer des valeurs numériques valides.");
                alert.showAndWait();
            }
        });
        vbox.getChildren().add(valider);
        Scene scene = new Scene(vbox, 300, 200);
        ajoutPoint.setScene(scene);
        ajoutPoint.show();
    }

    private void classifyUnknownPoints() {
        for (Iris i : observable.getIris()) {
            if (i.getVariety() == null) {
                classificationDonnee(i);
            }
        }
        this.series1.getData().clear();
        this.series2.getData().clear();
        this.series3.getData().clear();
        this.series4.getData().clear();
        remplirGraphique();

    }

    private HBox createButtons() {
        Button button1 = new Button("Changer les axes");
        button1.setOnAction(e -> changeAxes());

        Button button2 = new Button("Charger un fichier");
        button2.setOnAction(e -> loadFile());

        Button button3 = new Button("Ajouter un point");
        button3.setOnAction(e -> addPoint());

        Button button4 = new Button("Classer les points inconnus");
        button4.setOnAction(e -> classifyUnknownPoints());

        Button newChartButton = new Button("Open New Scatter Chart");
        newChartButton.setOnAction(e -> openNewScatterChart());

        Button knnButton = new Button("Exécuter KNN");
        knnButton.setOnAction(e -> executeKNN());

        return new HBox(10, button1, button2, button3, button4, newChartButton, knnButton);
    }


    @Override
    public void update(Observable o) {
        remplirGraphique();
    }

    @Override
    public void update(Observable o, Object arg) {
        remplirGraphique();
    }

    private void executeKNN() {
        Stage knnStage = new Stage();
        knnStage.initModality(Modality.APPLICATION_MODAL);
        knnStage.initOwner(this);
        knnStage.setTitle("Configurer KNN");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        // Champ pour saisir la valeur de k
        Label kLabel = new Label("Valeur de k :");
        TextField kField = new TextField();
        kField.setPromptText("Entrez un entier");

        // Choix du type de distance
        Label distanceLabel = new Label("Type de distance :");
        ComboBox<String> distanceCombo = new ComboBox<>();
        distanceCombo.getItems().addAll("Euclidienne", "Manhattan");
        distanceCombo.setValue("Euclidienne"); // Valeur par défaut

        // Bouton pour exécuter le KNN
        Button executeButton = new Button("Exécuter KNN");
        executeButton.setOnAction(event -> {
            try {
                int k = Integer.parseInt(kField.getText());
                String typeCalcul = distanceCombo.getValue();

                if (k <= 0) {
                    throw new NumberFormatException("k doit être un entier positif.");
                }

                // Créer une instance de KNNAlgo et exécuter la classification
                KNNAlgo<Iris> knnAlgo = new KNNAlgo<>();
                for (Iris iris : observable.getIris()) {
                    if (iris.getVariety() == null) {
                        // Appliquer la classification
                        //VARIETY result = knnAlgo.knnClassification(k, observable, typeCalcul, xAxis, yAxis);
                        //iris.setVariety(result);
                    }
                }
                remplirGraphique();
                knnStage.close();

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez entrer une valeur valide pour k.");
                alert.showAndWait();
            }
        });

        vbox.getChildren().addAll(kLabel, kField, distanceLabel, distanceCombo, executeButton);

        Scene scene = new Scene(vbox, 300, 200);
        knnStage.setScene(scene);
        knnStage.show();
    }

}