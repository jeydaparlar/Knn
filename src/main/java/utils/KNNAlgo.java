package utils;

import javafx.scene.chart.NumberAxis;
import model.*;

import java.util.*;

import static model.Iris.getAxisValue;

public class KNNAlgo<E> {

    /** Méthode qui permet de récupérer les k voisins les plus proches
     * @param points la liste des points du jeu de données
     * @param distances la liste des distances entre le point et les autres points
     * @param k le nombre de voisins à retourner
     * @return la liste des k voisins les plus proches
     */
    public static List<DataPoint> getKVoisins(List<DataPoint> points, List<Double> distances, int k){
        ArrayList<DataPoint> closestPoints = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            int index = distances.indexOf(Collections.min(distances));
            closestPoints.add(points.get(index));
            distances.set(index, Double.MAX_VALUE);
        }
        return closestPoints;
    }

    /** Méthode qui permet de trouver la classe majoritaire parmi les voisins
     * @param voisins la liste des voisins
     * @param observable le jeu de données complet
     * @return la classe majoritaire en chaine de caractères
     */
    public static String mostClass(List<DataPoint> voisins, TableDataPoint observable){
        List<String> classes = observable.getDifferentQualifie(observable.getQualify());
        Map<String, Integer> map = new HashMap<>();
        for(String classe : classes){
            map.put(classe, 0);
        }
        for (DataPoint voisin : voisins) {
            Object variety = voisin.getAttribute(observable.getQualify());
            if (variety != null) {
                map.put((String)variety, map.get(variety) + 1);
            }
        }
        return map.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();
    }

    /** Méthode qui permet de calculer la distance entre deux points
     * @param a le premier point
     * @param b le deuxième point
     * @param typeCalcul le type de calcul de distance
     * @param xAxis l'axe des abscisses
     * @param yAxis l'axe des ordonnées
     * @return la distance entre les deux points
     */
    public static double calculerDistance(DataPoint a, DataPoint b, String typeCalcul, String xAxis, String yAxis) {
        double distance = 0.0;
        if (typeCalcul.equals("Manhattan")) {
            distance = CalculDistanceMan.distanceMan(a, b, xAxis, yAxis);
        } else if (typeCalcul.equals("Euclidienne")) {
            distance = CalculDistanceEucli.calculerDistanceEucli(a, b, xAxis, yAxis);
        }
        return distance;
    }

    /** Méthode qui permet de calculer les distances entre un point et tous les autres points du jeu de données
     * @param unknown le point dont on veut trouver les voisins
     * @param list la liste des points du jeu de données
     * @param typeCalcul le type de calcul de distance
     * @param xAxis l'axe des abscisses
     * @param yAxis l'axe des ordonnées
     * @param observable le jeu de données complet
     * @param voisins la liste des voisins
     * @param distances la liste des distances
     */
    public static void calculerVoisinsDistances(DataPoint unknown, List<DataPoint> list, String typeCalcul, String xAxis, String yAxis, TableDataPoint observable, List<DataPoint> voisins, List<Double> distances) {
        for (DataPoint point : list) {
            if (point.getAttribute(observable.getQualify()) != null) {
                double distance = calculerDistance(unknown, point, typeCalcul, xAxis, yAxis);
                distances.add(distance);
                voisins.add(point);
            }
        }
    }

    /** Méthode qui permet de calculer la classification KNN pour l'ensemble de données
     * @param k le nombre de voisins à prendre en compte
     * @param observable le jeu de données complet
     * @param typeCalcul le type de calcul de distance
     * @param xAxis l'axe des abscisses
     * @param yAxis l'axe des ordonnées
     * @return la classe majoritaire en chaine de caractères
     */
    public static String knnClassification(int k, TableDataPoint observable, String typeCalcul, String xAxis, String yAxis) {
        List<DataPoint> kVoisins = new ArrayList<>();
        for (DataPoint unknown : observable.getData()) {
            if (unknown.getAttribute(observable.getQualify()) == "Inconnu") {
                List<DataPoint> voisins = new ArrayList<>();
                List<Double> distances = new ArrayList<>();
                calculerVoisinsDistances(unknown, observable.getData(), typeCalcul, xAxis, yAxis, observable, voisins, distances);
                kVoisins = getKVoisins(voisins,distances, k);
                unknown.addAttribute(observable.getQualify(), mostClass(kVoisins, observable));
                System.out.println("K : " + k);
                System.out.println("Classe : " + unknown.getAttribute(observable.getQualify()));
            }
        }
        return mostClass(kVoisins, observable);
    }

    /** Méthode qui permet de calculer la classification KNN pour un seul élément
     * @param k le nombre de voisins à prendre en compte
     * @param elt le point dont on veut trouver les voisins
     * @param list la liste des points du jeu de données
     * @param typeCalcul le type de calcul de distance
     * @param xAxis l'axe des abscisses
     * @param yAxis l'axe des ordonnées
     * @param observable le jeu de données complet
     * @return la classe majoritaire en chaine de caractères
     */
    public static String knnSolo(int k, DataPoint elt, List<DataPoint> list, String typeCalcul, String xAxis, String yAxis, TableDataPoint observable) {
        List<DataPoint> voisins = new ArrayList<>();
        List<Double> distances = new ArrayList<>();
        calculerVoisinsDistances(elt, list, typeCalcul, xAxis, yAxis, observable, voisins, distances);
        List<DataPoint> kVoisins = getKVoisins(voisins,distances, k);
        return mostClass(kVoisins, observable);
    }

    /** Méthode qui permet de calculer la robustesse du modèle
     * @param k le nombre de voisins à prendre en compte
     * @param observable le jeu de données complet
     * @param typeCalcul le type de calcul de distance
     * @param xAxis l'axe des abscisses
     * @param yAxis l'axe des ordonnées
     * @return le taux de réussite de la classification entre 0 et 1 (1 étant le meilleur score)
     */
    public static double robustesse(int k, TableDataPoint observable, String typeCalcul, String xAxis, String yAxis) {
        double count = 0;
        for (DataPoint unknown : observable.getData()) {
            String classe = knnSolo(k, unknown, observable.getData(), typeCalcul, xAxis, yAxis, observable);
            if (classe.equals(unknown.getAttribute(observable.getQualify()))) {
                count++;
            }
        }
        return count/observable.getData().size();
    }


    /** Méthode qui permet de calculer la robustesse du modèle
     * @param k le nombre de voisins à prendre en compte
     * @param list le jeu de données complet
     * @param distance le type de calcul de distance
     * @param xAxis l'axe des abscisses
     * @param yAxis l'axe des ordonnées
     * @return le taux de réussite de la classification entre 0 et 1 (1 étant le meilleur score)
     */
    public static double validationCroisee(TableDataPoint list,Integer k, String distance, String xAxis, String yAxis){

        Collections.shuffle(list.getData());
        int catExact = 0;
        int nbElement = list.getData().size()/(k+1)-1;
        String qualify = list.getQualify() ;
        TableDataPoint subList;
        String oldQualifie = "";
        String newQualifie = "";
        int index = 0;
        Random random = new Random();
        int pointeur = 0;
        for(int i=0 ; i< nbElement; i ++){
            subList = new TableDataPoint(list.getData().subList(pointeur, pointeur+k));
            index  = random.nextInt(k);
            oldQualifie = (String) subList.getData(index).getAttribute(qualify);

            List<DataPoint> voisins = new ArrayList<>();
            List<Double> distances = new ArrayList<>();
            calculerVoisinsDistances(subList.getData(index), subList.getData(), distance, xAxis, yAxis, list, voisins, distances);
            List<DataPoint> kVoisins = getKVoisins(voisins,distances, k);
            newQualifie = mostClass(kVoisins, list);
            if(newQualifie.equals(oldQualifie)){
                catExact++;
            }
            pointeur += k+1;
        }

        System.out.println("nb : " + catExact + '\n' + "sous ensemble :" + nbElement);
        return (double) catExact /nbElement;
    }
}
