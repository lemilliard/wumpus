/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.i4.back.model.board.content;

import fr.epsi.i4.back.model.board.Board;

/**
 *
 * @author cesar
 */
@Deprecated
public class Gold extends IContent {

    private int x;

    private int y;

    private Board board;

    public Gold(Board board) {
        this.board = board;
        this.x = 1;
        this.y = 1;
    }
    
    public int getGoldX() {
        return x;
    }

    public int getGoldY() {
        return y;
    }

    public Board getBoard() {
        return board;
    }
}
