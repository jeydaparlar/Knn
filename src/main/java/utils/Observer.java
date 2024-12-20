package utils;

/**
 * Classe Observer
 */
public interface Observer {
    /**
     * Update de l'observeur par rapport à l'observé
     * @param observable un observable
     */
    void update(Observable observable);

    /**
     * Update de l'observeur par rapport à l'observé
     * @param observable un observable
     * @param data donnée à envoyer à l'observeur
     */
    void update(Observable observable, Object data);
}

