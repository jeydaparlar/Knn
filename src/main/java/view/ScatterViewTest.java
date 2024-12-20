package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.*;
import utils.KNNAlgo;
import utils.Observable;
import utils.Observer;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScatterViewTest extends Stage implements Observer {

   public static final String TITLE = "Nuage de points";
    private final List<XYChart.Series<Number, Number>> series = new ArrayList<>();
    public final TableDataPoint observable;
    public final NumberAxis xAxis;
    public final NumberAxis yAxis;
    public final ListView<Iris> listView;
    public final ScatterChart<Number, Number> sc;
    public List<String> attributes = new ArrayList<>();
    public String qualifie = "";
    public ArrayList<String> differentQualife = new ArrayList<>();

    public ListView<DataPoint> liste = new ListView<>();

    public static String axeXDefault = "";
    public static String axeYDefault = "";

    public ScatterViewTest(TableDataPoint observable) {
        Stage stage = new Stage();
        stage.setTitle(TITLE);
        this.listView = new ListView<>();
        this.observable = observable;
        observable.attach(this);

        xAxis = new NumberAxis(0, 100,10);
        yAxis = new NumberAxis(0, 100,10);

        this.sc = new ScatterChart<>(xAxis, yAxis);
        sc.setOnMouseClicked(event -> {
            // Trouver le point le plus proche du clic
            DataPoint nearestPoint = observable.findPoint(event.getX(), event.getY(), axeXDefault, axeYDefault);
            if (nearestPoint != null) {
                // Sélectionner l'élément correspondant dans la ListView
                int index = observable.getData().indexOf(nearestPoint);
                liste.getSelectionModel().select(index);
            }
        });

        if(!observable.getData().isEmpty()) {
            axeXDefault = observable.getData(1).getAttribute(0).toString();
            axeYDefault = observable.getData(1).getAttribute(2).toString();
            attributes = observable.getData(1).getAttributes();
            addQualifie();
            this.differentQualife.clear();
            this.differentQualife.addAll(observable.getDifferentQualifie(this.qualifie));
            changementAxe(axeXDefault, axeYDefault, this.observable);
            remplirGraphique();
        }

        HBox hboxRigthLeft = new HBox();
        VBox vboxRight = new VBox();
        HBox hboxButton = createButtons();
        VBox vboxListe = new VBox();
        VBox vboxUnderListe = new VBox();
        vboxUnderListe.setAlignment(Pos.CENTER);
        vboxUnderListe.setSpacing(10);


        Button buttonCharger = new Button("Charger un fichier");
        buttonCharger.setOnAction(e -> loadFile());
        buttonCharger.getStyleClass().add("button-charger");
        vboxUnderListe.setMargin(buttonCharger, new javafx.geometry.Insets(10, 10, 10, 10));

        vboxRight.getChildren().addAll(sc, hboxButton);
        vboxUnderListe.getChildren().addAll(buttonCharger);

        Label labelNbPoints = new Label("Nombres de points : " + observable.getData().size());
        vboxListe.getChildren().addAll(liste,labelNbPoints, vboxUnderListe);
        hboxRigthLeft.getChildren().addAll(vboxRight,vboxListe);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Scene scene = new Scene(hboxRigthLeft,bounds.getWidth(), bounds.getHeight()-25);
        sc.setPrefSize(bounds.getWidth()/1.3, bounds.getHeight()/1.5);
        vboxListe.setPrefSize(bounds.getWidth()-(bounds.getWidth()/1.3), bounds.getHeight());
        liste.setPrefSize(bounds.getWidth()-(bounds.getWidth()/1.3), bounds.getHeight()/1.3);
        scene.getStylesheets().add("style.css"); // Charge le fichier CSS
        this.setScene(scene);
        this.setTitle(TITLE);
        this.show();
    }

    private void changementAxe(String axeX, String axeY, TableDataPoint observable) {
        NumberAxis xAxis = (NumberAxis) sc.getXAxis();
        xAxis.setLabel(axeX);
        if(!observable.getData().isEmpty()) {
            xAxis.setLowerBound((int) Math.floor(observable.minDonnee(axeX)) - 1);
        } else {
            xAxis.setLowerBound(100);
        }
        if(!observable.getData().isEmpty()) {
            xAxis.setUpperBound((int) Math.ceil(observable.maxDonnee(axeX)) + 1);
        } else {
            xAxis.setUpperBound(100);
        }
        axeXDefault = axeX;

        xAxis.setTickUnit(0.5);

        NumberAxis yAxis = (NumberAxis) sc.getYAxis();
        yAxis.setLabel(axeY);
        if(!observable.getData().isEmpty()) {
            yAxis.setLowerBound((int) Math.floor(observable.minDonnee(axeY)) - 1);
        } else {
            yAxis.setLowerBound(100);
        }
        if(!observable.getData().isEmpty()) {
            yAxis.setUpperBound((int) Math.ceil(observable.maxDonnee(axeY)) + 1);
        } else {
            yAxis.setUpperBound(100);
        }
        axeYDefault = axeY;

        yAxis.setTickUnit(0.5);
        sc.setVisible(true);
        remplirGraphique();
    }


    private void remplirGraphique() {
        series.clear();
        this.differentQualife.clear();
        this.differentQualife.addAll(observable.getDifferentQualifie(this.qualifie));
        for(int i = 0; i < this.differentQualife.size(); i++) {
            series.add(new XYChart.Series<Number,Number>());
        }


        for (DataPoint i : this.observable.getData()) {
                for(int j = 0; j < this.differentQualife.size(); j++) {
                    if(i.getAttribute(qualifie) != null && i.getAttribute(qualifie).equals(this.differentQualife.get(j))) {
                        XYChart.Data<Number,Number> data=new XYChart.Data<>((Number) Double.parseDouble(i.getAttributeDouble(axeXDefault)),(Number) Double.parseDouble(i.getAttributeDouble(axeYDefault)));
                        series.get(j).getData().add(data);
                        Tooltip tooltip= new Tooltip(xAxis.getLabel() + ": " + i.getAttribute(xAxis.getLabel()) + ", " + yAxis.getLabel() + " : " + i.getAttribute(yAxis.getLabel()));
                        data.nodeProperty().addListener((obs, oldNode, newNode)->{
                            if(newNode != null){
                                Tooltip.install(newNode,tooltip);
                            }
                        });
                    }
                }

        }
        for(int i = 0; i < sc.getData().size(); i++) {
            if (!sc.getData().isEmpty()) {
                sc.getData().get(i).getData().clear();
            }
        }
        for(int i = 0; i < series.size(); i++) {

            if(sc.getData().size() <= i) {
                sc.getData().add(series.get(i));
            } else {
                sc.getData().get(i).getData().addAll(series.get(i).getData());
            }
        }



        liste.getItems().clear();
        for(DataPoint i : observable.getData()) {
            liste.getItems().add(i);
        }


    }



    private void openNewScatterChart() {
        new ScatterViewTest(this.observable);
    }

    private void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir un fichier texte");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(this);

        if (selectedFile != null) {
            sc.getData().clear();
            this.observable.removeAllData();
            this.observable.ajouterCsv(selectedFile.getAbsolutePath());
            // met les valeurs axeXdefault et axeYDefault à jour en sachant que il faut vérifier que ce ne'st pas un string
            axeXDefault = observable.getData(1).getKeysWithDoubleValue().get(0);

            axeYDefault = observable.getData(1).getKeysWithDoubleValue().get(1);
            attributes = observable.getData(1).getAttributes();
            addQualifie();
            this.differentQualife.addAll(observable.getDifferentQualifie(this.qualifie));
            this.differentQualife.add("Inconnu");
        }
    }

    private void addQualifie() {
        Stage ajoutQualifie = new Stage();
        ajoutQualifie.initModality(Modality.APPLICATION_MODAL);
        ajoutQualifie.initOwner(this);
        ajoutQualifie.setTitle("Ajouter une qualification");
        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        Label label1 = new Label("Qualification : ");
        HBox hboxlabel = new HBox(label1);
        hboxlabel.getStyleClass().add("width-50");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getStyleClass().add("choice-box");
        HBox hboxChoiceBox = new HBox(choiceBox);
        hboxChoiceBox.getStyleClass().add("width-50");
        choiceBox.getItems().addAll(observable.getData(1).getKeysWithStringValue());
        hbox1.getChildren().addAll(hboxlabel, hboxChoiceBox);
        vbox.getChildren().addAll(hbox1);
        Button valider = new Button("Valider");
        valider.setOnAction(event -> {
            this.qualifie = choiceBox.getValue();
            this.observable.setQualify(this.qualifie);
            changementAxe(axeXDefault, axeYDefault, this.observable);
            ajoutQualifie.close();
        });
        valider.getStyleClass().add("button-valider");
        VBox.setMargin(valider, new javafx.geometry.Insets(10, 10, 10, 10));
        vbox.getChildren().add(valider);
        Scene scene = new Scene(vbox, 300, 200);
        scene.getStylesheets().add("style.css");
        ajoutQualifie.setScene(scene);
        ajoutQualifie.show();
    }

    private void changeAxes() {
        Stage modifAxes = new Stage();
        modifAxes.initModality(Modality.APPLICATION_MODAL);
        modifAxes.initOwner(this);
        modifAxes.setTitle("Changer les axes");
        VBox vbox = new VBox();
        if(!observable.getData().isEmpty()) {
            HBox hbox1 = new HBox();
            HBox hbox2 = new HBox();
            Label labelX = new Label("Axe X : ");
            ComboBox<String> comboX = new ComboBox<>();
            for (String s : observable.getData(1).getKeysWithDoubleValue()) {
                comboX.getItems().add(s);
            }

            Label labelY = new Label("Axe Y : ");
            ComboBox<String> comboY = new ComboBox<>();
            for(String s : observable.getData(1).getKeysWithDoubleValue()) {
                comboY.getItems().add(s);
            }
            hbox1.getChildren().addAll(labelX, comboX);
            hbox2.getChildren().addAll(labelY, comboY);
            vbox.getChildren().addAll(hbox1, hbox2);
            Button valider = new Button("Valider");
            valider.setOnAction(event -> {
                changementAxe(comboX.getValue(), comboY.getValue(), this.observable);
                remplirGraphique();
                modifAxes.close();
            });
            valider.getStyleClass().add("button-valider");
            VBox.setMargin(valider, new javafx.geometry.Insets(10, 10, 10, 10));
            vbox.getChildren().add(valider);
        } else {
            Label label = new Label("Il n'y a pas de données pour le moment.");
            vbox.getChildren().add(label);
        }
        Scene scene = new Scene(vbox, 300, 200);
        scene.getStylesheets().add("style.css");
        modifAxes.setScene(scene);
        modifAxes.show();
    }

    private void affichageNouveauPoint(DataPoint nouveauPoint) {
        if(xAxis.getLabel() == null) {
            xAxis.setLabel(axeXDefault);
        }
        if(yAxis.getLabel() == null) {
            yAxis.setLabel(axeYDefault);
        }
        changementAxe(axeXDefault, axeYDefault, this.observable);
        this.observable.addData(nouveauPoint);
        remplirGraphique();
    }

    private void addPoint() {
        Stage ajoutPoint = new Stage();
        ajoutPoint.initModality(Modality.APPLICATION_MODAL);
        ajoutPoint.initOwner(this);
        ajoutPoint.setTitle("Ajouter un point");
        VBox vbox = new VBox();
        List<HBox> hboxList = new ArrayList<>();
        if(!observable.getData().isEmpty()) {
            List<TextField> listHbox = new ArrayList<>();

            for(String s : observable.getData().get(1).getKeysWithDoubleValue()) {
                if(!s.equals(qualifie)) {
                    HBox hbox = new HBox();
                    Label label = new Label(s + " : ");
                    label.getStyleClass().add("label-form");
                    HBox hboxLabel = new HBox(label);
                    hboxLabel.getStyleClass().add("width-50");
                    TextField textField = new TextField();
                    textField.getStyleClass().add("input-form");
                    HBox hboxTextField = new HBox(textField);
                    hboxTextField.getStyleClass().add("width-50");
                    hbox.getChildren().addAll(hboxLabel, hboxTextField);
                    listHbox.add(textField);
                    hboxList.add(hbox);
                }
            }
            for(HBox hbox : hboxList) {
                vbox.getChildren().add(hbox);
            }
            Button valider = new Button("Valider");
            valider.setOnAction(event -> {
                try {
                    List<String> attributesDouble = observable.getData(1).getKeysWithDoubleValue();
                    DataPoint nouveauPoint = new DataPoint();
                    for(int i = 0; i < attributes.size(); i++) {
                        //verifie que c'est soit un string ou un Double
                        if (!attributesDouble.contains(attributes.get(i)) && !attributes.get(i).equals(qualifie)) {
                            nouveauPoint.addAttribute(attributes.get(i), "Test");

                        } else if (attributes.get(i).equals(qualifie)) {
                            nouveauPoint.addAttribute(attributes.get(i), "Inconnu");

                        } else {
                            for (int j = 0; j < attributesDouble.size(); j++) {
                                if(attributesDouble.get(j).equals(attributes.get(i))) {
                                    nouveauPoint.addAttribute(attributes.get(i), Double.parseDouble(listHbox.get(j).getText()));
                                }
                            }
                        }

                    }

                    affichageNouveauPoint(nouveauPoint);
                    changementAxe(axeXDefault, axeYDefault, this.observable);
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
            valider.getStyleClass().add("button-valider");
            vbox.setAlignment(Pos.CENTER);
            VBox.setMargin(valider, new Insets(10, 10, 10, 10));
        } else {
            Label label = new Label("Il n'y a pas de données pour le moment.");
            vbox.getChildren().add(label);
        }
        Scene scene = new Scene(vbox, 300, attributes.size() * 18 + 100);
        scene.getStylesheets().add("style.css");
        ajoutPoint.setScene(scene);
        ajoutPoint.show();
    }

    public void modifierQualifie() {
        Stage ajoutQualifie = new Stage();
        ajoutQualifie.initModality(Modality.APPLICATION_MODAL);
        ajoutQualifie.initOwner(this);
        ajoutQualifie.setTitle("Modifier la qualification");
        VBox vbox = new VBox();
        if(!observable.getData().isEmpty()) {
            HBox hbox1 = new HBox();
            Label label1 = new Label("Qualification : ");
            HBox hboxlabel = new HBox(label1);
            hboxlabel.getStyleClass().add("width-50");
            ChoiceBox<String> choiceBox = new ChoiceBox<>();
            choiceBox.getStyleClass().add("choice-box");
            HBox hboxChoiceBox = new HBox(choiceBox);
            hboxChoiceBox.getStyleClass().add("width-50");
            choiceBox.getItems().addAll(observable.getData(1).getKeysWithStringValue());
            hbox1.getChildren().addAll(hboxlabel, hboxChoiceBox);
            vbox.getChildren().addAll(hbox1);
            Button valider = new Button("Valider");
            valider.getStyleClass().add("button-valider");
            VBox.setMargin(valider, new javafx.geometry.Insets(10, 10, 10, 10));
            valider.setOnAction(event -> {
                this.qualifie = choiceBox.getValue();
                observable.setQualify(this.qualifie);
                changementAxe(axeXDefault, axeYDefault, this.observable);
                ajoutQualifie.close();
            });
            vbox.getChildren().add(valider);
        } else {
            Label label = new Label("Il n'y a pas de données pour le moment.");
            vbox.getChildren().add(label);
        }
        Scene scene = new Scene(vbox, 300, 200);
        scene.getStylesheets().add("style.css");
        ajoutQualifie.setScene(scene);
        ajoutQualifie.show();
    }

    private void classificationDonnee(DataPoint i) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(differentQualife.size());
        i.addAttribute(qualifie, differentQualife.get(randomIndex));
    }

    private void classifyUnknownPoints() {
        for (DataPoint i : observable.getData()) {
            if (i.getAttribute(qualifie) == "Inconnu") {
                classificationDonnee(i);
            }
        }
        remplirGraphique();

    }

    private void classifier() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(this);
        popupStage.setTitle("KNN Parametres");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));


       if(!observable.getData().isEmpty()) {
           Label calculusLabel = new Label("Type de calcul:");
           ChoiceBox<String> calculusChoiceBox = new ChoiceBox<>();
           calculusChoiceBox.getItems().addAll("Euclidienne", "Manhattan");


           Label kLabel = new Label("Combien de k voisins:");
           TextField kTextField = new TextField();


           Button calculateButton = new Button("Calculer Robustesse");

           Label resultLabel = new Label();

           calculateButton.setOnAction(event -> {
               String calculusType = calculusChoiceBox.getValue();
               int k;
               try {
                   k = Integer.parseInt(kTextField.getText());
                   double result = KNNAlgo.validationCroisee(observable,k, calculusType, axeXDefault, axeYDefault);
                   resultLabel.setText("Robustesse: " + result);
               } catch (NumberFormatException e) {
                   resultLabel.setText("Entrez une valeur correcte pour k.");
               }
           });
           calculateButton.getStyleClass().add("button-style");
           Button validateButton = new Button("Valider");

           validateButton.setOnAction(event -> {
               String calculusType = calculusChoiceBox.getValue();
               int k;
               try {
                   k =(int) Integer.parseInt(kTextField.getText());
                   String result = KNNAlgo.knnClassification(k, observable, "Euclidienne", axeXDefault, axeYDefault);
                   resultLabel.setText("Classe majoritaire: " + result);
                   remplirGraphique();
               } catch (NumberFormatException e) {
                   resultLabel.setText("Entrez une valeur correcte pour k.");
               }
           });
           validateButton.getStyleClass().add("button-style");

           vbox.getChildren().addAll(calculusLabel, calculusChoiceBox, kLabel, kTextField, calculateButton, resultLabel ,validateButton);
       } else {
              Label label = new Label("Il n'y a pas de données pour le moment.");
              vbox.getChildren().add(label);
       }

        Scene scene = new Scene(vbox, 300, 300);
        scene.getStylesheets().add("style.css");
        popupStage.setScene(scene);
        popupStage.show();
    }

    private HBox createButtons() {
        Button button1 = new Button("Changer les axes");
        button1.setOnAction(e -> changeAxes());
        button1.getStyleClass().add("button-style");

        Button button3 = new Button("Ajouter un point");
        button3.setOnAction(e -> addPoint());
        button3.getStyleClass().add("button-style");

        Button button4 = new Button("Classer Random inconnus");
        button4.setOnAction(e -> classifyUnknownPoints());
        button4.getStyleClass().add("button-style");

        Button newChartButton = new Button("Open New Scatter Chart");
        newChartButton.setOnAction(e -> openNewScatterChart());
        newChartButton.getStyleClass().add("button-style");

        Button remplir = new Button("Remplir");
        remplir.setOnAction(e -> remplirGraphique());
        remplir.getStyleClass().add("button-style");

        Button modifierQualifie = new Button("Modifier la qualification");
        modifierQualifie.setOnAction(e -> modifierQualifie());
        modifierQualifie.getStyleClass().add("button-style");

        Button classifier = new Button("Classifier");
        classifier.setOnAction(e -> classifier());
        classifier.getStyleClass().add("button-style");

        HBox answer = new HBox(button1,button3,newChartButton,remplir,modifierQualifie, classifier,button4);
        answer.setMargin(button4, new javafx.geometry.Insets(10, 10, 10, 10));
        answer.setMargin(button1, new javafx.geometry.Insets(10, 10, 10, 10));
        answer.setMargin(button3, new javafx.geometry.Insets(10, 10, 10, 10));
        answer.setMargin(newChartButton, new javafx.geometry.Insets(10, 10, 10, 10));
        answer.setMargin(remplir, new javafx.geometry.Insets(10, 10, 10, 10));
        answer.setMargin(modifierQualifie, new javafx.geometry.Insets(10, 10, 10, 10));
        answer.setMargin(classifier, new javafx.geometry.Insets(10, 10, 10, 10));
        return answer;
    }

    @Override
    public void update(Observable o) {
        remplirGraphique();
    }

    @Override
    public void update(Observable o, Object arg) {
        remplirGraphique();
    }
}