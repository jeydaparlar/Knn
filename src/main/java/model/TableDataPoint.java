package model;

import utils.FileManagerTest;
import utils.Observable;

import javax.xml.crypto.Data;
import java.util.*;

/**Représente une table contenant la totalité des points du graphique*/
public class TableDataPoint extends Observable {
    /**data représente l'ensemble des points du graphique*/
    private List<DataPoint> data = new ArrayList<>();
    private String qualify;

    public TableDataPoint(List<DataPoint> data){
        this.data = data;
    }

    public TableDataPoint(){
        this(new ArrayList<>());
    }

    /**Méthode qui permet de rajouter un nouveau point dans la table
     * @param dataPoint le nouveau point à ajouter*/
    public void addData(DataPoint dataPoint) {
        if (!this.data.contains(dataPoint)) {
            this.data.add(dataPoint);
            notifyObservers(dataPoint);
        }
    }

    /**Méthode qui permet de recupérer le point passer en paramètre
     * @param dataPoint le point que l'on souhaite récupérer
     */
    public DataPoint getData(DataPoint dataPoint) {
        if (this.data.contains(dataPoint)) {
            return this.data.get(this.data.indexOf(dataPoint));
        }
        return null;
    }

    /**Méthode qui permet de recupérer le point à l'index passer en paramètre
     * @param index l'index du point que l'on souhaite récupérer
     */
    public DataPoint getData(int index) {
        return this.data.get(index);
    }

    /** Méthode qui permet de modifier la valeur d'un attribut pour un point passer en paramètre
     * @param dataPoint un point du graphe
     * @param key un attribut
     * @param value la valeur attribuer à l'attribut
     */
    public void setAttribute(DataPoint dataPoint, String key, Object value) {
        dataPoint.addAttribute(key, value);
        notifyObservers(dataPoint);
    }


    /**Méthode qui permet de récupérer une List des points du graphe
     * @return une List de points*/
    public List<DataPoint> getData() {
        return new ArrayList<>(data); // Retourne une copie pour éviter les modifications inattendues
    }

    /** Méthode qui permet d'ajouter au fichier passé en paramètre les données présentes dans le graphe
     * @param file chemin vers le fichier
     */
    public void ajouterCsv(String file) {
        List<DataPoint> dataFromCsv = FileManagerTest.readFile(file);
        this.data.addAll(dataFromCsv);
        notifyObservers(dataFromCsv);
    }

    /**Méthode qui permet d'avoir une écriture lisible de l'ensemble des point du graphe
     * @return un String contenant l'écriture */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (DataPoint dataPoint : data) {
            result.append(dataPoint.toString()).append("\n");
        }
        return result.toString();
    }

    /** Renvoie les différentes valeurs possibles pour un attribut (ex. pour type1 [eau,feu,...]
     * @param key un attribut
     * @return une List
     */
    public List<String> getDifferentQualifie(String key) {
        List<String> list = new ArrayList<>();
        for(DataPoint i : this.data) {
            if(i.getAttribute(key) != null) {
                if(!list.contains(i.getAttribute(key).toString())) {
                    list.add(i.getAttribute(key).toString());
                }
            }
        }
        return list;
    }

    /** Méthode qui permet de récupérer la valeur maximale présente dans le graphe pour un attribut
     * @param donnee la valeur d'un attribut
     * @return la valeur maximale présente dans le graphe*/
    public double maxDonnee(String donnee) {
        double max = 0;
        for (DataPoint i : this.data) {

                if (Double.parseDouble(i.getAttributeDouble(donnee)) > max) {
                    max = Double.parseDouble(i.getAttributeDouble(donnee));
                }

        }
        return max;
    }

    /** Méthode qui permet de récupérer la valeur minimale présente dans le graphe pour un attribut
     * @param donnee la valeur d'un attribut
     * @return la valeur minimale présente dans le graphe*/
    public double minDonnee(String donnee) {
        double min = Double.MAX_VALUE;
        for (DataPoint i : this.data) {
            if (Double.parseDouble(i.getAttributeDouble(donnee)) < min) {
                min = Double.parseDouble(i.getAttributeDouble(donnee));
            }
        }
        return min;
    }

    /**Méthode qui permet de supprimer toutes les données du graphe*/
    public void removeAllData() {
        this.data.clear();
        notifyObservers();
    }

    /** Permet de trouver le point dont les coordonées sont passées en paramètre
     * @param x coordonée en x du point qui est recherché
     * @param y coordonée en y du point qui est recherché
     * @param axeX valeur de l'axe X
     * @param axeY valeur de l'axe Y
     * @return Datapoint le point qi était recherché*/
    public DataPoint findPoint(Double x, Double y, String axeX, String axeY) {
        for (DataPoint i : this.data) {
            if (i.getAttributeDouble(axeX).equals(x.toString()) && i.getAttributeDouble(axeY).equals(y.toString())) {
                return i;
            }
        }
        return null;
    }

    /** Permet de modifier la qualification sur laquelle on qualifie les points
     * @param qualify*/
    public void setQualify(String qualify) {
        this.qualify = qualify;
    }

    /**Permet de récupérer la qualification sur laquelle on qualifie
     * @return String la valeur de qualification*/
    public String getQualify() {
        return this.qualify;
    }
}