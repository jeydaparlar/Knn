package model;

import javafx.scene.chart.NumberAxis;

import java.util.Objects;

import static view.ScatterView.axeXDefault;

/*Classe Iris permet de caractériser l'ensemble des iris qui sont présentes sur notre graphique */
public class Iris {
    /** sepal_length Représente la longueur du sepale de l'Iris*/
    private Double sepal_length;
    /** sepal_width Représente la largeur du sepale de l'Iris*/
    private Double sepal_width;
    /** petal_length Représente la longeur de pétale de l'Iris*/
    private Double petal_length;
    /** petal_width Représente la largeur de pétale de l'Iris*/
    private Double petal_width;
    /** variety Représente la varitété de l'Iris
     * @see VARIETY*/
    private VARIETY variety;
    /** ORIGINUNKOWN représente .... à l'aide d'un boolean */
    private boolean ORIGINUNKOWN;

    /** Constructeur qui ne prend aucun paramètre */
    public Iris() {
        this.sepal_length = 0.0;
        this.sepal_width = 0.0;
        this.petal_length = 0.0;
        this.petal_width = 0.0;
        this.variety = null;
    }

    /** Constructeur qui prend en paramètre
     * @param sepal_length la longeur du sépale de l'Iris
     * @param sepal_width la largeur du sépale de l'Iris
     * @param petal_length la longeur de pétale de l'Iris
     * @param petal_width la largeur de pétale de l'Iris
     * @param variety la variété de l'Iris
     * @param ORIGINUNKOWN boolean permettant de savoir si l'origine de l'Iris est connu
     */
    public Iris(Double sepal_length, Double sepal_width, Double petal_length, Double petal_width, VARIETY variety, boolean ORIGINUNKOWN) {
        this.sepal_length = sepal_length;
        this.sepal_width = sepal_width;
        this.petal_length = petal_length;
        this.petal_width = petal_width;
        this.variety = variety;
        this.ORIGINUNKOWN = ORIGINUNKOWN;
    }

    /** Constructeur qui prend en paramètre
     * @param sepal_length la longeur du sépale de l'Iris
     * @param sepal_width la largeur du sépale de l'Iris
     * @param petal_length la longueur de pétale de l'Iris
     * @param petal_width la largeur de pétale de l'Iris
     */
    public Iris(Double sepal_length, Double sepal_width, Double petal_length, Double petal_width) {
        this(sepal_length, sepal_width, petal_length, petal_width, null, false);

    }

    /** @return permet de récupérer un Double représentant la longeur du sépale de l'Iris*/
    public Double getSepal_length() {
        return sepal_length;
    }

    /** Permet de changer la longeur du sépale de l'Iris
     * @param sepal_length la longeur du sépale de l'Iris
     */
    public void setSepal_length(Double sepal_length) {
        this.sepal_length = sepal_length;
    }

    /**
     * @return permet de récupérer un Double représentant la largeur du sépale de l'Iris
     */
    public Double getSepal_width() {
        return sepal_width;
    }

    /** Permet de changer la largeur du sépale de l'Iris
     * @param sepal_width la largeur du sépale de l'Iris
     */
    public void setSepal_width(Double sepal_width) {
        this.sepal_width = sepal_width;
    }

    /** @return permet de récupérer un Double représentant la longeur de la pétal de l'Iris
     */
    public Double getPetal_length() {
        return petal_length;
    }

    /** Permet de changer la longeur de la pétal de l'Iris
     * @param petal_length la longeur de la pétal de l'Iris
     */
    public void setPetal_length(Double petal_length) {
        this.petal_length = petal_length;
    }

    /** @return permet de récupérer la largeur de la pétal de l'Iris
     */
    public Double getPetal_width() {
        return petal_width;
    }

    /** Permet de modifier la largeur de la pétal de l'Iris
     * @param petal_width la largeur de la pétal de l'Iris
     */
    public void setPetal_width(Double petal_width) {
        this.petal_width = petal_width;
    }

    /** @return permet de récupérer le type d'Iris
     */
    public VARIETY getVariety() {
        return variety;
    }

    /** Permet de modifier le type de l'Iris
     * @param variety le type de l'Iris
     */
    public void setVariety(VARIETY variety) {
        this.variety = variety;
    }

    /** @return permet de savoir si l'origine de l'Iris est connu
     */
    public boolean isOriginUnkown() {
        return ORIGINUNKOWN;
    }

    /** méthode toString
     * @return un string contenant les informations de l'Iris
     */
    @Override
    public String toString() {
        return "Iris{" +
                "sepal_length=" + sepal_length +
                ", sepal_width=" + sepal_width +
                ", petal_length=" + petal_length +
                ", petal_width=" + petal_width +
                ", variety=" + variety +
                '}';
    }

    /** méthode equals
     * @param o représentant un Iris
     * @return un boolean nous indiquant si les deux Iris sont égales
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Iris iris = (Iris) o;
        return Objects.equals(sepal_length, iris.sepal_length) && Objects.equals(sepal_width, iris.sepal_width) && Objects.equals(petal_length, iris.petal_length) && Objects.equals(petal_width, iris.petal_width) && variety == iris.variety;
    }

    /** @return permet de récupérer le haché de l'Iris
     */
    @Override
    public int hashCode() {
        return Objects.hash(sepal_length, sepal_width, petal_length, petal_width, variety);
    }


    public static Double getAxisValue(Iris iris, NumberAxis axis) {
        if (axis.getLabel() == null) {
            axis.setLabel(axeXDefault);
        }
        return switch (axis.getLabel()) {
            case "sepal_length" -> iris.getSepal_length();
            case "sepal_width" -> iris.getSepal_width();
            case "petal_length" -> iris.getPetal_length();
            case "petal_width" -> iris.getPetal_width();
            default -> 0.0;
        }; //TODO deplacer les switch dans la classe Iris pour faire les calculs peut importe le nom
    }
}
