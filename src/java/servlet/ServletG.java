/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import annotation.Param;
import annotation.Vue;
import converter.MainConverter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Session;

/**
 *
 * @author Ihagatiana
 */
public class ServletG extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = (String) request.getAttribute("url");
        String[] classMethode = url.split("-");
        String className = this.changeClassNameCase(classMethode[0]);
        String methodName = classMethode[1];
        try {
            Class<?> c = Class.forName("controller." + className);
            Object obj = c.newInstance();
            Method[] methods = c.getDeclaredMethods(); //récupération de toutes le méthodes
            Method methode = null;
            //comparaison des méthode selon leur noms
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    methode = method;
                    break;
                }
            }
            //récupération des arguments de la méthode si la methode n'est pas null
            if (methode != null) {

                Parameter[] paramNeed = methode.getParameters();
                Annotation[][] an = methode.getParameterAnnotations();
                //raha misy parametre
                if (paramNeed.length != 0) {
                    try {
                        //test oe tsmaints misy annotation ny parametre akotran session
                        //ary tsy miotran ray ny annotation amna paramatre ray
                        countAnnotaion(an, paramNeed);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    Object[] paramsValue = new Object[paramNeed.length];

                    //récupération des paramètres de l'url et stockage dans un tableau
                    Param[] queryparam = null;

                    if (an.length != 0) {
                        Param temp;
                        queryparam = new Param[an.length];
                        System.out.println("param number=" + an.length);
                        int j = 0;
                        for (Annotation[] annotArray : an) {
                            //raha misy session
                            if (annotArray.length == 0) {
                                queryparam[j] = null;
                            } else {
                                for (Annotation individualAnnot : annotArray) {
                                    temp = (Param) individualAnnot;
                                    queryparam[j] = temp;
                                }
                            }
                            j++;
                        }
                    }
                    if (queryparam != null) {
                        int i = 0;
                        for (Parameter parametre : paramNeed) {
                            try {
                                //raha tsis session
                                if (queryparam[i] != null) {
                                    if (request.getParameter(queryparam[i].name()) == null) {
                                        throw new Exception("nom parametre '" + queryparam[i].name() + "' introuvable dans l'url");
                                    }
                                    paramsValue[i] = MainConverter.convert(request.getParameter(queryparam[i].name()),
                                            parametre.getType());
                                } //raha misy session
                                else {
                                    //mamorona anle objet session
                                    Session sess = (Session) parametre.getType().newInstance();
                                    sess.setReq(request);
                                    paramsValue[i] = sess;
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            i++;
                        }
                    }
                    String vue = "";
                    if (methode.getReturnType().getTypeName().equals("void")) {
                        methode.invoke(obj, paramsValue);

                        if (methode.getAnnotation(Vue.class).vue() != null) {
                            vue = methode.getAnnotation(Vue.class).vue();
                            request.getRequestDispatcher(vue).forward(request, response);
                        }
                    } else {
                        Object data = methode.invoke(obj, paramsValue);
                        request.setAttribute("data", data);

                        if (methode.getAnnotation(Vue.class).vue() != null) {
                            vue = methode.getAnnotation(Vue.class).vue();
                            request.getRequestDispatcher(vue).forward(request, response);
                        }
                    }

                } else {
                    String vue = "";
                    if (methode.getReturnType().getTypeName().equals("void")) {
                        methode.invoke(obj, (Object) null);

                        if (methode.getAnnotation(Vue.class).vue() != null) {
                            vue = methode.getAnnotation(Vue.class).vue();
                            request.getRequestDispatcher(vue).forward(request, response);
                        }
                    } else {
                        Object data = methode.invoke(obj, (Object) null);
                        request.setAttribute("data", data);

                        if (methode.getAnnotation(Vue.class).vue() != null) {
                            vue = methode.getAnnotation(Vue.class).vue();
                            request.getRequestDispatcher(vue).forward(request, response);
                        }
                    }
                }

            }

        } catch (IOException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException
                | InstantiationException | SecurityException | InvocationTargetException | ServletException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected String changeClassNameCase(String className) {
        char[] toArray = className.toLowerCase().toCharArray();
        String first = String.valueOf(toArray[0]).toUpperCase();
        toArray[0] = first.charAt(0);
        return String.valueOf(toArray);
    }

    private int countAnnotaion(Annotation[][] an, Parameter[] paramneed) throws Exception {
        int count = 0;
        int session = 0;
        int i = 0;
        for (Annotation[] an1 : an) {
            if (!paramneed[i].getType().getName().equals("utils.Session")) {
                if (an1.length == 1) {
                    count++;
                } else if (an1.length == 0) {
                    throw new Exception("mila misy annotaion 1 au moins ny parametre");
                } else if (an1.length > 1) {
                    throw new Exception("ray ihany ny annotation amna parametre");
                }
            } else {
                session++;
            }
            i++;
        }
        if (session != 1) {
            throw new Exception("ray ihany ny session amna parametre fonction ray");
        }
        return count;
    }

}
