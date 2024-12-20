package utils;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import model.DataPoint;
import model.Iris;
import model.TableDataPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KNNAlgoTest {

    TableDataPoint tableDataPoint;
    List<DataPoint> list;
    DataPoint i1;
    DataPoint i2;
    DataPoint i3;
    DataPoint i4;
    DataPoint ptAjouter;
    String typeCalcul;

    List<DataPoint> voisins;
    List<Double> distance;

    String axeX;
    String axeY;

    @BeforeEach
    void initialisation() {
        list = new ArrayList<>();
        tableDataPoint = new TableDataPoint();

        i1 = new DataPoint();
        i2 = new DataPoint();
        i3 = new DataPoint();
        i4 = new DataPoint();
        ptAjouter = new DataPoint();
        i1.addAttribute("taille", 1.2);
        i1.addAttribute("poids", 2.3);
        i1.addAttribute("variety", "setosa");
        i2.addAttribute("taille", 2.5);
        i2.addAttribute("poids", 3.6);
        i2.addAttribute("variety", "versicolor");
        i3.addAttribute("taille", 2.4);
        i3.addAttribute("poids", 3.2);
        i3.addAttribute("variety", "setosa");
        i4.addAttribute("taille", 1.5);
        i4.addAttribute("poids", 2.6);
        i4.addAttribute("qualify", "setosa");
        ptAjouter.addAttribute("taille", 1.7);
        ptAjouter.addAttribute("poids", 2.7);
        ptAjouter.addAttribute("variety", null);
        list.add(i1);
        list.add(i2);
        list.add(i3);
        list.add(i4);
        tableDataPoint.addData(i1);
        tableDataPoint.addData(i2);
        tableDataPoint.addData(i3);
        tableDataPoint.addData(i4);
        tableDataPoint.setQualify("variety");
        typeCalcul = "Euclidienne";
        voisins = new ArrayList<>();
        distance = new ArrayList<>();
        axeX = "taille";
        axeY = "poids";
    }

    @Test
    void testGetKVoisinsEuclidien() {
        KNNAlgo.calculerVoisinsDistances(ptAjouter, list, typeCalcul, axeX, axeY, tableDataPoint, voisins, distance);
        System.out.println(distance);
        List<DataPoint> kvoisins = KNNAlgo.getKVoisins(voisins, distance, 2);
        System.out.println(kvoisins);
        assertEquals(2, kvoisins.size());
        assertEquals(i4, kvoisins.get(0));
        assertEquals(i1, kvoisins.get(1));
    }

    @Test
    void testGetKVoisinsManhattan() {
        typeCalcul = "Manhattan";
        KNNAlgo.calculerVoisinsDistances(ptAjouter, list, typeCalcul, axeX, axeY, tableDataPoint, voisins, distance);
        System.out.println(distance);
        List<DataPoint> kvoisins = KNNAlgo.getKVoisins(voisins, distance, 2);
        System.out.println(kvoisins);
        assertEquals(2, kvoisins.size());
        assertEquals(i4, kvoisins.get(0));
        assertEquals(i1, kvoisins.get(1));
    }

    @Test
    void testMostClass(){
        List<DataPoint> kvoisins = new ArrayList<>();
        kvoisins.add(i1);
        kvoisins.add(i2);
        kvoisins.add(i3);
        kvoisins.add(i4);
        assertEquals("setosa", KNNAlgo.mostClass(kvoisins, tableDataPoint));
    }


    @Test
    void testKnnClassification(){
        tableDataPoint.addData(ptAjouter);
        assertEquals("Iris-setosa", KNNAlgo.knnClassification(2, tableDataPoint, typeCalcul, axeX, axeY));
    }

    @Test
    void testKnnSolo(){
        assertEquals("Iris-setosa", KNNAlgo.knnSolo(2, ptAjouter, list, typeCalcul, axeX, axeY, tableDataPoint));
    }

    @Test
    void testRobustesse(){
        System.out.println(KNNAlgo.robustesse(2, tableDataPoint, typeCalcul, axeX, axeY));
    }

    @Test
    void verifCroiseeEuclidienne(){
        System.out.println(KNNAlgo.validationCroisee(tableDataPoint, 1, typeCalcul, axeX, axeY ));
        //Pas assez de données pour faire les tests avec les autres
        //System.out.println(KNNAlgo.validationCroisee(tableDataPoint, 3, typeCalcul, axeX, axeY ));
        //System.out.println(KNNAlgo.validationCroisee(tableDataPoint, 5, typeCalcul, axeX, axeY ));
        //System.out.println(KNNAlgo.validationCroisee(tableDataPoint, 7, typeCalcul, axeX, axeY ));
    }

    @Test
    void verifCroiseeManhattan(){
        System.out.println(KNNAlgo.validationCroisee(tableDataPoint, 1, "Manhattan", axeX, axeY ));
        //Pas assez de données pour faire les tests avec les autres
        //System.out.println(KNNAlgo.validationCroisee(tableDataPoint, 3, "Manhattan", axeX, axeY ));
        //System.out.println(KNNAlgo.validationCroisee(tableDataPoint, 5, "Manhattan", axeX, axeY ));
        //System.out.println(KNNAlgo.validationCroisee(tableDataPoint, 7, "Manhattan", axeX, axeY ));
    }
}
