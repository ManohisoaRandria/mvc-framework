/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author manohisoa
 */
public class Personne {

    private String a;
    private int b;
    private String[] check;

    public Personne(String a, int b, String[] check) {
        this.a = a;
        this.b = b;
        this.check = check;
    }

    public Personne() {
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String[] getCheck() {
        return check;
    }

    public void setCheck(String[] check) {
        this.check = check;
    }
}
