/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import annotation.ModelParam;
import annotation.Param;
import annotation.Vue;
import converter.MainConverter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Outils;

/**
 *
 * @author Ihagatiana
 */
public class ServletG extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String url = (String) request.getAttribute("url");
        String[] classMethode = url.split("-");
        String className = this.changeClassNameCase(classMethode[0]);
        String methodName = classMethode[1];
        try {
            Class<?> c = Class.forName("controller." + className);

            Object obj = c.newInstance();
            //initialiser la session raha misy
            //mamorona anle objet session
            Outils.initSession(obj, request);

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
                        throw ex;
                    }
                    Object[] paramsValue = new Object[paramNeed.length];

                    int i = 0;
                    ModelParam modelAnnotTemp;
                    Param paramAnnotTemp;
                    for (Parameter parametre : paramNeed) {
                        try {
                            //raha ho mapperna anaty class fa tsy anaty variable tsotra
                            if (an[i][0].annotationType().getName().equalsIgnoreCase("annotation.ModelParam")) {
                                paramsValue[i] = MainConverter.convertObject(request, parametre);
                            } else {
                                paramAnnotTemp = (Param) an[i][0];
                                //raha string [] de checkbox zay
                                if (parametre.getType().getName().equalsIgnoreCase("[Ljava.lang.String;")) {
                                    if (request.getParameterValues(paramAnnotTemp.name()) == null) {
                                        throw new Exception("nom parametre '" + paramAnnotTemp.name() + "' introuvable dans l'url");
                                    }
                                    paramsValue[i] = request.getParameterValues(paramAnnotTemp.name());
                                } else {
                                    if (request.getParameter(paramAnnotTemp.name()) == null) {
                                        throw new Exception("nom parametre '" + paramAnnotTemp.name() + "' introuvable dans l'url");
                                    }
                                    paramsValue[i] = MainConverter.convert(request.getParameter(paramAnnotTemp.name()),
                                            parametre.getType());
                                }
                            }

                        } catch (Exception ex) {
                            throw ex;
                        }
                        i++;
                    }

                    String vue = "";
                    if (methode.getReturnType().getTypeName().equals("void")) {
                        methode.invoke(obj, paramsValue);

                        if (methode.getAnnotation(Vue.class) != null) {
                            vue = methode.getAnnotation(Vue.class).vue();
                            if (!methode.getAnnotation(Vue.class).redirect()) {
                                request.getRequestDispatcher(vue).forward(request, response);
                            } else {
                                response.sendRedirect(vue);
                            }
                        }
                    } else {
                        Object data = methode.invoke(obj, paramsValue);
                        request.setAttribute("data", data);

                        if (methode.getAnnotation(Vue.class) != null) {
                            vue = methode.getAnnotation(Vue.class).vue();
                            if (!methode.getAnnotation(Vue.class).redirect()) {
                                request.getRequestDispatcher(vue).forward(request, response);
                            } else {
                                response.sendRedirect(vue);
                            }
                        }
                    }

                } else {
                    String vue = "";
                    if (methode.getReturnType().getTypeName().equals("void")) {
                        methode.invoke(obj, new Object[0]);

                        if (methode.getAnnotation(Vue.class) != null) {
                            vue = methode.getAnnotation(Vue.class).vue();
                            if (!methode.getAnnotation(Vue.class).redirect()) {
                                request.getRequestDispatcher(vue).forward(request, response);
                            } else {
                                response.sendRedirect(vue);
                            }
                        }
                    } else {
                        Object data = methode.invoke(obj, new Object[0]);
                        request.setAttribute("data", data);

                        if (methode.getAnnotation(Vue.class) != null) {
                            vue = methode.getAnnotation(Vue.class).vue();
                            if (!methode.getAnnotation(Vue.class).redirect()) {
                                request.getRequestDispatcher(vue).forward(request, response);
                            } else {
                                response.sendRedirect(vue);
                            }
                        }
                    }
                }

            }

        } catch (IOException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException
                | InstantiationException | SecurityException | InvocationTargetException | ServletException ex) {
            throw ex;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletG.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected String changeClassNameCase(String className) {
        char[] toArray = className.toLowerCase().toCharArray();
        String first = String.valueOf(toArray[0]).toUpperCase();
        toArray[0] = first.charAt(0);
        return String.valueOf(toArray);
    }

    private int countAnnotaion(Annotation[][] an, Parameter[] paramneed) throws Exception {
        int count = 0;
        for (Annotation[] an1 : an) {
            if (an1.length == 1) {
                count++;
            } else if (an1.length == 0) {
                throw new Exception("mila misy annotaion 1 au moins ny parametre");
            } else if (an1.length > 1) {
                throw new Exception("ray ihany ny annotation amna parametre");
            }
        }

        return count;
    }

}
