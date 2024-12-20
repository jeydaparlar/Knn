package utils;

import java.util.Collection;
import java.util.HashSet;

/**
 * Classe observable
 */
public abstract class Observable {
    protected final Collection<Observer> attached = new HashSet<>();
    protected final Collection<Observer> toDetach = new HashSet<>();

    /**
     * Permet d'ajouter un nouvel observeur dans la liste d'observeur de l'observé
     * @param obs un observeur
     */
    public void attach(Observer obs) {
        attached.add(obs);
    }

    /**
     * Permet de supprimer un observeur de la liste des observeur de l'observé
     * @param obs un observeur
     */
    public void detach(Observer obs) {
        this.toDetach.add(obs);
    }

    /**
     * Notifie les observer de l'objet observable d'un changement chez celui-ci
     */
    public void notifyObservers() {
        this.updateList();
        for (Observer o : attached) {
            o.update(this);
        }
    }

    /**
     * Notifie les observeurs de l'observable
     * @param data donnée à envoyer aux observeurs
     */
    protected void notifyObservers(Object data) {
        this.updateList();
        for (Observer o : attached) {
            o.update(this, data);
        }
    }

    /**
     * Update la liste des observateurs
     */
    private void updateList() {
        this.attached.removeAll(toDetach);
        this.toDetach.clear();
    }

}

