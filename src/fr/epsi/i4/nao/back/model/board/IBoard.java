package fr.epsi.i4.nao.back.model.board;

import fr.epsi.i4.nao.back.model.board.content.Content;

/**
 * Les coordonnées du FrontBoard sont inversées quand on attaque directement le tableaux des Cases
 * Il faut donc utiliser les méthodes dédiées comme getCase ou addCaseContent pour avoir les
 * coordonnées dans le bon ordre et éviter les erreurs
 *
 * Created by tkint on 14/12/2017.
 */
public interface IBoard {

    /**
     * Retourne la case demandée selon les coordonnées
     * @param x
     * @param y
     * @return
     */
    Case getCase(int x, int y);

    /**
     * Génère le board
     * @param width
     * @param height
     * @param pitsPercentage
     */
    void generate(int width, int height, int pitsPercentage);

    /**
     * Ajoute un contenu à la case spécifiée
     * @param x
     * @param y
     * @param content
     */
    void addCaseContent(Content content, int x, int y);
}
