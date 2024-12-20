package model;

import java.util.*;
import java.util.stream.Collectors;

/**La classe DataPoint permet de représenter les données du fichier importé*/
public class DataPoint {
    /** attribute représente l'ensemble des attributs d'un point ex. le type d'un pokémon*/
    private Map<String, Object> attributes = new TreeMap<>();

    /** Méthode qui permet d'ajouter un nouvel attribut à un point
     * @param key le nom de l'attribut
     * @param value la valeur attribuée à l'attribut pour le point
     */
    public void addAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    /** Méthode qui permet d'avoir la valeur d'un attribut passé en paramètre
     * @param key le nom de l'attribut de type String
     * @return la valeur de l'attribut passer en paramètre de type Object
     */
    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    /** Méthode qui permet d'avoir la valeur d'un attribut passé en paramètre
     * @param key le nom de l'attribut de type Object
     * @return la valeur de l'attribut passer en paramètre de type String
     */
    public String getAttributeDouble(Object key) {
        return "" + attributes.get(key);
    }

    /** Méthode qui permet d'avoir la valeur d'un attribut passé en paramètre
     * @param index l'index de l'attribut de type int
     * @return la valeur de l'attribut passer en paramètre de type String
     */
    public String getAttribute(int index) {
        List<Object> myList = new ArrayList<>(attributes.keySet());
        return (String) myList.get(index);
    }

    /**Méthode qui permet de récupérer la liste des attributs du point
     * @return une List des attributs du point
     */
    public List<String> getAttributes() {
        List list = new ArrayList();
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            if(entry.getValue() instanceof String) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    /** Méthode qui récupère les attributs qui correspondent à un String
     * @return une List d'attributs
     */
    public List<String> getKeysWithStringValue() {
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            Object value = entry.getValue();
            try {
                Double.parseDouble(value.toString());
            } catch (NumberFormatException e) {
                if (value instanceof String) {
                    keys.add(entry.getKey());
                }
            }
        }
        return keys;
    }

    /** Méthode qui récupère les attributs qui correspondent à un double
     * @return une List d'attributs
     */
    public List<String> getKeysWithDoubleValue() {
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            Object value = entry.getValue();
            try {
                Double.parseDouble(value.toString());
                keys.add(entry.getKey());
            } catch (NumberFormatException e) {
            }
        }
        return keys;
    }

    /**Méthode qui permet de supprimer un attribut
     * @param key l'attribut qui doit être enlever de type String
     */
    // Autres méthodes utiles :
    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    /**Méthode qui permet de savoir si le point possède bien cet attribut
     * @param key l'attribut à tester
    * @return true si le point possède cet attribut, false sinon
     */
    public boolean containsAttribute(String key) {
        return attributes.containsKey(key);
    }

    /**Méthode qui permet d'avoir une écriture lisible de l'ensemble des attributs du point
     * @return un String contenant l'écriture */
    public String toString() {
       // fait le toString
        String res = "";
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            res += entry.getKey() + " : " + entry.getValue() + "\n";
        }
        return res;
    }

    /**Méthode permettant de comparer deux points et de savoir s'ils sont égaux
     * @return true si les deux points sont égaux, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataPoint dataPoint = (DataPoint) o;

        return Objects.equals(attributes, dataPoint.attributes);
    }

    /**Méthode qui permet de récupérer le hash du point
     * @return le hash du point
     */
    @Override
    public int hashCode() {
        return Objects.hash(attributes);
    }

}