package model;

import utils.FileManager;
import utils.Observable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *  Permet de charger l'ensemble des données des Iris dans une liste
 */
public class TableIris extends Observable  {

    /** Tableau d'Iris*/
    private List<Iris> iris = new ArrayList<>();

    /**
     * Permet d'ajouter un Iris à la liste
     * @param iris Iris à ajouter
     */
    public void addIris(Iris iris) {
        if(!this.iris.contains(iris)) {
            this.iris.add(iris);
            notifyObservers(iris);
        }
    }

    /**
     * Permet d'ajouter un Iris à la liste grâce à ses caractéristiques
     * @param sepal_length longeur du sépale
     * @param sepal_width largeur du sépale
     * @param petal_length longueur du pétale
     * @param petal_width longueur du pétale
     */
    public void addIris(Double sepal_length, Double sepal_width, Double petal_length, Double petal_width) {
        Iris iristmp = new Iris(sepal_length, sepal_width, petal_length, petal_width);
        if(!this.iris.contains(iristmp)) {
            this.iris.add(iristmp);
            notifyObservers(iristmp);
        }
    }

    /**Permet de récupérer l'Iris passé en paramètre
     * @param irisget l'Iris que l'on souhaite récupéré
     * @return L'iris passée en paramètre*/
    public Iris getIris(Iris irisget) {
        if(this.iris.contains(irisget)) {
            return this.iris.get(this.iris.indexOf(irisget));
        }
        return null;
    }

    /**Permet de changer la variété d'une Iris passée en paramètre
     * @param variety variété que l'on souhaite attribuer à l'iris
     * @param iris l'iris dont on souhaite changer la variété*/
    public void setVariety(VARIETY variety, Iris iris) {
        this.getIris(iris).setVariety(variety);
        notifyObservers(iris);
    }

    /** Permet de supprimer un Iris de la liste d'Iris
     * @param iris Iris à supprimer
     */
    public void removeIris(Iris iris) {
        this.iris.remove(iris);
        notifyObservers(iris);
    }

    /**
     * Permet de supprimer plusieurs Iris de la liste d'Iris
     * @param iris ensemble d'Iris à supprimer
     */
    public void removeIris(Collection<Iris> iris) {
        this.iris.removeAll(iris);
        notifyObservers();
    }

    /**
     * Supprime l'ensemble des Iris présentent dans la liste
     */
    public void removeAllIris() {
        this.iris.clear();
        notifyObservers();
    }

    /**
     * Permet de récupérer la liste des Iris
     * @return la liste des Iris
     */
    public List<Iris> getIris() {
        return this.iris;
    }

    /**
     * Méthode toString
     * @return l'ensemble des informations des différentes Iris
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Iris iris : this.iris) {
            result.append(iris.toString()).append("\n");
        }
        return result.toString();
    }


    /**
     * Permet d'ajouter les Iris présentent dans le CSV dans la liste
     * @param file nom du fichier csv
     */
    public void ajouterCsv(String file) {
        List<Iris> tmp = FileManager.readFile(file);
        this.iris = new ArrayList<>(tmp);
        notifyObservers(tmp);
    }

    /**
     * Permet de récupérer le plus grand élément présent dans la liste selon l'attribut passé en paramètre
     * @param donnee attribut souhaité de l'Iris
     * @return la donnée la plus élevée
     */
    public double maxDonnee(String donnee) {
        double max = 0;
        for (Iris i : this.iris) {
            switch (donnee) {
                case "sepal_length":
                    if (i.getSepal_length() > max) {
                        max = i.getSepal_length();
                    }
                    break;
                case "sepal_width":
                    if (i.getSepal_width() > max) {
                        max = i.getSepal_width();
                    }
                    break;
                case "petal_length":
                    if (i.getPetal_length() > max) {
                        max = i.getPetal_length();
                    }
                    break;
                case "petal_width":
                    if (i.getPetal_width() > max) {
                        max = i.getPetal_width();
                    }
                    break;
                default:
                    break;
            }
        }
        return max;
    }
}
