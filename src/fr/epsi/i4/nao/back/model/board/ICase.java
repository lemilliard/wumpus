package fr.epsi.i4.nao.back.model.board;

import fr.epsi.i4.nao.back.model.board.content.Content;

/**
 * Created by tkint on 17/12/2017.
 */
public interface ICase {

    /**
     * Ajoute du contenu à la case
     *
     * @param content
     * @return
     */
    Content addContent(Content content);

    /**
     * Retourne true si la case contient un objet de type précis
     *
     * @param content
     * @return
     */
    boolean containsContent(Content content);

    /**
     * Retourne true si la case contient un objet d'un des types précisés
     *
     * @param contents
     * @return
     */
    boolean containsContents(Content... contents);

    /**
     * Retourne true si la case contient n'importe quoi sauf l'agent
     *
     * @return
     */
    boolean containsAnythingButAgent();

    /**
     * Retourne le poids calculé en fonction du contenu
     *
     * @return
     */
    int calculateWeight();

    /**
     * Retourne true si la case a déjà été visitée
     *
     * @return
     */
    boolean hasBeenVisited();

    /**
     * Retourne le niveau de visite entre 1 et 4
     *
     * @return
     */
    int calculateSafety();
}
