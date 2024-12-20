package utils;

import javafx.scene.chart.NumberAxis;
import model.DataPoint;


/**Permet de calculer la distance entre deux points grâce au calcul de distance manhattan*/
public class CalculDistanceMan {
    /**Méthode qui effectue le calcul de manhattan entre deux points passés en paramètre
     * @param x1 coordonée en x du premier point
     * @param x2 coordonée en x du deuxième point
     * @param y1 coordonée en y du premier point
     * @param y2 coordonée en y du deuxième point
     * @return double la distance entre les deux points*/
    public static double calculDistanceMan(double x1, double x2, double y1, double y2){
        return round(Math.abs(x2 - x1) + Math.abs(y2 - y1));
    }

    /**Permet de calculer la distance entre deux points grâce au calcul de distance de manhattan
     * @param a un point
     * @param b un deuxième point
     * @param xAxis la valeur de l'axe des abscisses
     * @param yAxis la valeur de l'axe des ordonnées
     * @@return double la distance entre les deux points*/
    public static double distanceMan(DataPoint a, DataPoint b, String xAxis, String yAxis) {
        double xA=Double.parseDouble(a.getAttributeDouble(xAxis));
        double xB=Double.parseDouble(b.getAttributeDouble(xAxis));
        double yA=Double.parseDouble(a.getAttributeDouble(yAxis));
        double yB=Double.parseDouble(b.getAttributeDouble(yAxis));
        return calculDistanceMan(xA, xB, yA, yB);

    }

    /***/
    public static double round(double value) {
        return Double.parseDouble(String.format("%.3f", value).replace(',', '.'));
    }
}
