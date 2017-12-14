package fr.epsi.i4.nao.back.model;

/**
 * Created by tkint on 14/12/2017.
 */
public interface IBoard {

    /**
     * Génère le board
     * @param width
     * @param height
     */
    void generate(int width, int height);

    /**
     * Ajoute les puits selon le pourcentage d'occupation
     * @param percentage
     */
    void addPuits(double percentage);

    /**
     * Ajoute l'agent
     */
    void addAgent();

    /**
     * Ajoute le Wumpus
     */
    void addWumpus();

    /**
     * Ajoute l'or
     */
    void addGold();

    /**
     * Ajoute les vents autour des puits
     */
    void addWinds();

    /**
     * Ajoute les odeurs autour du Wumpus
     */
    void addSmell();
}
