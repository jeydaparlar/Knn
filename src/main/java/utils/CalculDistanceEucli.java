package utils;

import javafx.scene.chart.NumberAxis;
import model.DataPoint;
import model.Iris;
import view.ScatterView;
import model.TableIris;

//import static view.ScatterView.getAxisValue;

/**Permet de calculer la distance entre deux points grâce au calcul de distance euclidien*/
public class CalculDistanceEucli {
    /**Méthode qui effectue le calcul euclidien entre deux points passés en paramètre
     * @param x1 coordonée en x du premier point
     * @param x2 coordonée en x du deuxième point
     * @param y1 coordonée en y du premier point
     * @param y2 coordonée en y du deuxième point
     * @return double la distance entre les deux points*/
    public static double calculDistanceEucli(double x1, double x2, double y1, double y2){
        return round(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
    }

    /**Permet de calculer la distance entre deux points grâce au calcul de distance euclidien
     * @param a un point
     * @param b un deuxième point
     * @param axeX la valeur de l'axe des abscisses
     * @param axeY la valeur de l'axe des ordonnées
     * @@return double la distance entre les deux points*/
    public static double calculerDistanceEucli(DataPoint a, DataPoint b, String axeX, String axeY) {
        double xA = Double.parseDouble(a.getAttributeDouble(axeX));
        double xB = Double.parseDouble(b.getAttributeDouble(axeX));
        double yA = Double.parseDouble(a.getAttributeDouble(axeY));
        double yB = Double.parseDouble(b.getAttributeDouble(axeY));

        return calculDistanceEucli(xA, xB, yA, yB);
    }

    /***/
    public static double round(double value) {
        return Double.parseDouble(String.format("%.3f", value).replace(',', '.'));
    }

}
