/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.i4.nao.unit;

/**
 *
 * @author cesar
 */
public class Agent {
    private static Integer turn;
    private Integer arrow;
    private Coordinate position;
    private Integer direction; //modulo
    
    public Agent(){
        this.arrow = 1;
        this.position = new Coordinate(0,0);
        this.direction = direction;
    }

    public static Integer getTurn() {
        return turn;
    }

    public static void setTurn(Integer turn) {
        Agent.turn = turn;
    }

    public Integer getArrow() {
        return arrow;
    }

    public void setArrow(Integer arrow) {
        this.arrow = arrow;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }
    
}
