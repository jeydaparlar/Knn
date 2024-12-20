package utils;

import javafx.application.Platform;
import javafx.scene.chart.NumberAxis;
import model.DataPoint;
import model.TableDataPoint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCalcMan {
    TableDataPoint tableDataPoint = new TableDataPoint();
    DataPoint dataPoint1 = new DataPoint();
    DataPoint dataPoint2 = new DataPoint();
    DataPoint dataPoint3 = new DataPoint();
    DataPoint dataPoint4 = new DataPoint();
    String axeX;
    String axeY;

    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {});
    }

    @BeforeEach
    void initialisation() {
        dataPoint1.addAttribute("taille", 0);
        dataPoint1.addAttribute("poids", 0);
        dataPoint2.addAttribute("taille", 3);
        dataPoint2.addAttribute("poids", 4);
        dataPoint3.addAttribute("taille", 1);
        dataPoint3.addAttribute("poids", 1);
        dataPoint4.addAttribute("taille", 4);
        dataPoint4.addAttribute("poids", 5);
        tableDataPoint.addData(dataPoint1);
        tableDataPoint.addData(dataPoint2);
        tableDataPoint.addData(dataPoint3);
        tableDataPoint.addData(dataPoint4);
        axeX = "taille";
        axeY = "poids";
    }

    @Test
    public void test_calc_dist_eucli(){
        assertEquals(7, CalculDistanceMan.calculDistanceMan(0, 3, 0, 4));
        assertEquals(7, CalculDistanceMan.distanceMan(dataPoint1, dataPoint2, axeX, axeY));
    }
}
