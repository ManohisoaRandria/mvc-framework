/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import annotation.Authentification;
import annotation.Param;
import annotation.Vue;
import java.util.Date;
import utils.ModelView;
import utils.Session;

/**
 *
 * @author Ihagatiana
 */
public class Empcontroller {

    @Vue(vue = "liste.jsp")
    @Authentification(redirection = "")
    public ModelView lister(@Param(name = "a") String a, @Param(name = "b") int b, @Param(name = "dt") Date dt, Session sess) throws Exception {
        System.out.println("le a est=" + a);
        System.out.println("le b est=" + b);
        System.out.println("le dt est=" + dt.toString());
        String[] emp = {"A", "B", "C", "D", "E"};
        ModelView map = new ModelView();
        map.add("emp", emp);
        map.add("a", a);
        map.add("b", b);

//        sess.beginSession();
//        sess.set("haha", b);
        sess.endSession();

        return map;
    }

    @Vue(vue = "session.jsp")
    //test session raha efa natao begin teo ambony de tokony tsy mila begin tsony eto
    //fa tonga de hitany
    public ModelView getSession(Session sess) throws Exception {
        ModelView map = new ModelView();
        System.out.println("isCookie=" + sess.isCookie());
        System.out.println("isvalid=" + sess.isValide());
        int haha = (int) sess.get("haha");
        map.add("sessA", haha);
        return map;
    }
}
