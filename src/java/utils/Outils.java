/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author manohisoa
 */
public class Outils {

    public static List<Field> getAllField(Class instance) throws Exception {
        Class superClasse;
        List<Field> field = new ArrayList();
        superClasse = instance;
        while (!superClasse.getName().equals("java.lang.Object")) {
            Field[] attribut = superClasse.getDeclaredFields();
            field.addAll(Arrays.asList(attribut));
            superClasse = superClasse.getSuperclass();
        }

        return field;
    }

    public static void initSession(Object o, HttpServletRequest request) throws Exception {//initialise la session si elle exist dans le controlleur

        Field[] attributs = o.getClass().getDeclaredFields();
        Field f = null;
        int compterSession = 0;
        for (Field attribut : attributs) {
            if (attribut.getType().getName().equals("utils.Session")) {
                f = attribut;
                compterSession++;
            }
        }
        if (compterSession == 1) {//l'attribut de type session existe dans le controlleur
            Session sess = new Session();
            sess.setReq(request);
            sess.beginSession();
            o.getClass().getDeclaredMethod("set" + toUpperCase(f.getName()), Session.class).invoke(o, sess);
        } else if (compterSession > 1) {// si il ya plus de 1 attribut session
            throw new Exception("session attribute more than 1");
        }

    }

    public static String toUpperCase(String arg) {
        char[] name = arg.toCharArray();
        name[0] = Character.toUpperCase(name[0]);
        arg = String.valueOf(name);
        return arg;
    }
}
