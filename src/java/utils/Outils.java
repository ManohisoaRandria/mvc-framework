/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import annotation.Authentification;
import converter.MainConverter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;

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

    public static Object getParameterValues(List fileitems, HttpServletRequest request, String nomparam, Class<?> type, boolean isCheckBox, boolean ismultipart) throws Exception {
        Object ret = null;
        if (ismultipart) {
            boolean isnull = true;
            Iterator iter = fileitems.iterator();
            if (isCheckBox) {
                ArrayList<String> test = new ArrayList<>();
                while (iter.hasNext()) {
                    FileItem fi = (FileItem) iter.next();
                    if (fi.isFormField()) {
                        if (fi.getFieldName().equals(nomparam)) {
                            test.add(fi.getString());
                            isnull = false;
                        }
                    }
                }
                if (isnull) {
                    ret = null;
//                    throw new Exception("nom parametre '" + nomparam + "' introuvable dans l'url");
                } else {
                    ret = test.toArray(new String[0]);
                }

            } else {

                while (iter.hasNext()) {
                    FileItem fi = (FileItem) iter.next();
                    if (fi.isFormField()) {
                        if (fi.getFieldName().equals(nomparam)) {
                            ret = MainConverter.convert(fi.getString(), type);
                            isnull = false;
                            break;
                        }
                    }
                }
                if (isnull) {
                    throw new Exception("nom parametre '" + nomparam + "' introuvable dans l'url");
                }
            }

        } else {
            if (isCheckBox) {
//                if (request.getParameterValues(nomparam) == null) {
//                    throw new Exception("nom parametre '" + nomparam + "' introuvable dans l'url");
//                }
                ret = request.getParameterValues(nomparam);
            } else {
                if (request.getParameter(nomparam) == null) {
                    throw new Exception("nom parametre '" + nomparam + "' introuvable dans l'url");
                }
                ret = MainConverter.convert(request.getParameter(nomparam), type);
            }
        }
        return ret;
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

    public static String getBaseUrl(String folder, HttpServletRequest req) {
        if (req != null) {
            return req.getServletContext().getRealPath(folder);
        }
        return null;
    }

    public static boolean initQueryParam(Object o) throws Exception {
        Field[] attributs = o.getClass().getDeclaredFields();
        Field f = null;
        int compterQueryParams = 0;
        for (Field attribut : attributs) {
            if (attribut.getType().getName().equals("utils.QueryParams")) {
                f = attribut;
                compterQueryParams++;
            }
        }
        if (compterQueryParams == 1) {//l'attribut de type QueryParams existe dans le controlleur
            QueryParams sess = new QueryParams();
            o.getClass().getDeclaredMethod("set" + toUpperCase(f.getName()), QueryParams.class).invoke(o, sess);
            return true;
        } else if (compterQueryParams > 1) {// si il ya plus de 1 attribut session
            throw new Exception("Query params attribute more than 1");
        }
        return false;
    }

    public static QueryParams getQueryParam(Object o) throws Exception {
        Field[] attributs = o.getClass().getDeclaredFields();
        Field f = null;
        int compterQueryParams = 0;
        QueryParams sess = null;
        for (Field attribut : attributs) {
            if (attribut.getType().getName().equals("utils.QueryParams")) {
                f = attribut;
                compterQueryParams++;
            }
        }
        if (compterQueryParams == 1) {//l'attribut de type QueryParams existe dans le controlleur
            sess = (QueryParams) o.getClass().getDeclaredMethod("get" + toUpperCase(f.getName()), new Class[0]).invoke(o, new Object[0]);
        } else if (compterQueryParams > 1) {// si il ya plus de 1 attribut session
            throw new Exception("Query params attribute more than 1");
        }
        return sess;
    }

    public static boolean authentication(Authentification auth, HttpServletRequest request) {
        String[] testauth = auth.testAuth();
        String[] roles = auth.roles();
        boolean or = auth.roleOr();
        Session sessiontest = new Session();
        sessiontest.setReq(request);
        sessiontest.beginSession();
        //test
        int test = 0;
        for (String string : testauth) {
            if (!string.equals("") && sessiontest.get(string) != null) {
                test++;
            }
        }
        if (test == testauth.length) {
            //nety zany de le roles ndray no jerena
            if (roles.length == 0) {
                return true;
            }

            test = 0;
            for (String role : roles) {
                if (!role.equals("") && sessiontest.get(role) != null) {
                    test++;
                }
            }
            if (or == false && test == roles.length) {
                return true;
            } else if (or == true && test >= 1) {
                return true;
            }
        }
        return false;
    }

    public static String toUpperCase(String arg) {
        char[] name = arg.toCharArray();
        name[0] = Character.toUpperCase(name[0]);
        arg = String.valueOf(name);
        return arg;
    }
}
