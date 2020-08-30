/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import annotation.ModelParam;
import annotation.Param;
import annotation.Vue;
import java.util.Arrays;
import java.util.Date;
import utils.ModelView;
import utils.Session;

/**
 *
 * @author Ihagatiana
 */
public class Empcontroller {

    private Session sess;

    //reha tsis anle redirect io de mampiasa anle requestdispatcher par defaut
    @Vue(vue = "redirect.jsp", redirect = true)
    public ModelView lister(@Param(name = "a") String a, @Param(name = "b") int b, @Param(name = "dt") Date dt) throws Exception {
        System.out.println("le a est=" + a);
        System.out.println("le b est=" + b);
        System.out.println("le dt est=" + dt.toString());
        String[] emp = {"A", "B", "C", "D", "E"};
        ModelView map = new ModelView();
        map.add("emp", emp);
        map.add("a", a);
        map.add("b", b);
        sess.endSession();
//        sess.beginSession();
//        sess.set("haha", 2);
        return map;
    }

    public void test(@ModelParam() Personne a, @Param(name = "check") String[] c) {
        System.out.println("le a est=" + a.getA());
        System.out.println("le b est=" + a.getB());
        System.out.println("le check est=" + Arrays.toString(a.getCheck()));
        System.out.println("le check2 est=" + Arrays.toString(c));
    }

    @Vue(vue = "session.jsp")
    public void getSession() throws Exception {
//        ModelView map = new ModelView();
        System.out.println("session=" + sess);
        System.out.println("isCookie=" + sess.isCookie());
        System.out.println("isvalid=" + sess.isValide());

//        int haha = (int) sess.get("haha");
//        map.add("sessA", haha);
//        return map;
    }

    public Session getSess() {
        return sess;
    }

    public void setSess(Session sess) {
        this.sess = sess;
    }
}
