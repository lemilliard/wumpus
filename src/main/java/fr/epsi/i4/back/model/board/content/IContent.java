/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.i4.back.model.board.content;

/**
 *
 * @author cesar
 */
@Deprecated
public abstract class IContent {

    private int weight;

    public IContent() {}

    public IContent(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
