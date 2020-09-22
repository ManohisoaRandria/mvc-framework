/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import annotation.Authentification;
import annotation.Json;
import annotation.ModelParam;
import annotation.Param;
import annotation.UploadParam;
import annotation.Vue;
import com.google.gson.Gson;
import converter.MainConverter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utils.Outils;
import utils.QueryParams;
import utils.UploadFile;

/**
 *
 * @author Ihagatiana
 */
public class ServletG extends HttpServlet {

    private List fileItems;
    private String authView = null;

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
        Method methode = null;
        try {
            Class<?> c = Class.forName("controller." + className);

            Object obj = c.newInstance();
            //initialiser la session sy queryParam raha misy
            //mamorona anle objet session
            Outils.initSession(obj, request);
            boolean isQueryP = Outils.initQueryParam(obj);
            Method[] methods = c.getDeclaredMethods(); //récupération de toutes le méthodes
            //comparaison des méthode selon leur noms
            for (Method method : methods) {
                if (method.getName().equalsIgnoreCase(methodName)) {
                    methode = method;
                    break;
                }
            }
            //récupération des arguments de la méthode si la methode n'est pas null
            if (methode != null) {
                //vérification si l'authentification est requise
                if (methode.getAnnotation(Authentification.class) != null) {
                    Authentification auth = (Authentification) methode.getAnnotation(Authentification.class);

                    //authentication
                    boolean testauth = Outils.authentication(auth, request);
                    if (!testauth) {
                        this.authView = methode.getAnnotation(Authentification.class).errorRedirection();
                        throw new Exception("Authentication failed");
                    }
                }
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
                    boolean dejaInit = false;
                    for (Parameter parametre : paramNeed) {
                        try {
                            //testena aloha hoe multipart ve
                            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                            if (isMultipart) {
                                //initialiserna ilay listItem
                                if (!dejaInit) {
                                    DiskFileItemFactory factory = new DiskFileItemFactory();
                                    ServletFileUpload upload = new ServletFileUpload(factory);
                                    this.fileItems = upload.parseRequest(request);
                                    dejaInit = true;
                                }
                            }
                            //raha ho mapperna anaty class fa tsy anaty variable tsotra
                            if (an[i][0].annotationType().getName().equalsIgnoreCase("annotation.ModelParam")) {
                                paramsValue[i] = MainConverter.convertObject(request, parametre, isMultipart, this.fileItems);
                                //raha hanao upload
                            } else if (an[i][0].annotationType().getName().equalsIgnoreCase("annotation.UploadParam")) {
                                if (!parametre.getType().getName().equalsIgnoreCase("utils.UploadFile")) {
                                    throw new Exception("le type de parameter pour upload doit etre: utils.UploadFile");
                                }

                                if (!isMultipart) {
                                    throw new Exception("le form doit contenir l'attribut enctype = 'multipart/form-data'");
                                }
                                //miload anle sary amzay
                                UploadParam annotUpload = (UploadParam) an[i][0];
                                //max size file par defaut 1.024mo
                                UploadFile up = new UploadFile(annotUpload.maxFileSize(), request);
                                String filepath;

                                if (annotUpload.fileFolder().trim().equals("")) {
                                    //raha tsy nametraka path izy de par defaut hata anaty upload/ ao am racine anle projet
                                    filepath = Outils.getBaseUrl("upload/", request);
                                } else {
                                    if (annotUpload.fileFolder().trim().endsWith("/")) {
                                        filepath = Outils.getBaseUrl(annotUpload.fileFolder().trim(), request);
                                    } else {
                                        filepath = Outils.getBaseUrl(annotUpload.fileFolder().trim() + "/", request);
                                    }
                                }
                                up.setFilePath(filepath);

                                //jerena le content type restriction ho misy ve
                                if (annotUpload.allowedType().length != 0) {
                                    up.setContentTypeTest(annotUpload.allowedType());
                                }
//                                //raha tsisy file name de le nom par defaut anle fichier iny ihany no eo
//                                //raha nanisy file name le olona
//
                                //loaderna amzay le file
                                up.loadFile(this.fileItems);
                                paramsValue[i] = up;
                            } else {
                                paramAnnotTemp = (Param) an[i][0];
                                //raha string [] de checkbox zay
                                if (parametre.getType().getName().equalsIgnoreCase("[Ljava.lang.String;")) {
                                    paramsValue[i] = Outils.getParameterValues(this.fileItems, request, paramAnnotTemp.name(), parametre.getType(), true, isMultipart);
                                } else {

                                    paramsValue[i] = Outils.getParameterValues(this.fileItems, request, paramAnnotTemp.name(), parametre.getType(), false, isMultipart);
//                                    System.out.println(request.getPart(paramAnnotTemp.name()));
//                                    if (request.getParameter(paramAnnotTemp.name()) == null) {
//                                        throw new Exception("nom parametre '" + paramAnnotTemp.name() + "' introuvable dans l'url");
//                                    }
//                                    paramsValue[i] = MainConverter.convert(request.getParameter(paramAnnotTemp.name()),
//                                            parametre.getType());
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
                            vue = setView(methode, false, isQueryP, obj, null);

                            if (!methode.getAnnotation(Vue.class).redirect()) {
                                request.getRequestDispatcher(vue).forward(request, response);
                            } else {
                                response.sendRedirect(vue);
                            }
                        }
                    } else {
                        Object data = methode.invoke(obj, paramsValue);
                        if (methode.getAnnotation(Json.class) != null) {
                            //raha donnée json no tiany alaina
                            Gson gson = new Gson();
                            String json = gson.toJson(data);
                            request.setAttribute("data", json);
                        } else {
                            request.setAttribute("data", data);
                        }

                        if (methode.getAnnotation(Vue.class) != null) {
                            vue = setView(methode, false, isQueryP, obj, null);

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
                            vue = setView(methode, false, isQueryP, obj, null);

                            if (!methode.getAnnotation(Vue.class).redirect()) {
                                request.getRequestDispatcher(vue).forward(request, response);
                            } else {
                                response.sendRedirect(vue);
                            }
                        }
                    } else {

                        Object data = methode.invoke(obj, new Object[0]);
                        if (methode.getAnnotation(Json.class) != null) {

                            //raha donnée json no tiany alaina
                            Gson gson = new Gson();
                            String json = gson.toJson(data);
                            request.setAttribute("data", json);
                        } else {
                            request.setAttribute("data", data);
                        }

                        if (methode.getAnnotation(Vue.class) != null) {
                            vue = setView(methode, false, isQueryP, obj, null);

                            if (!methode.getAnnotation(Vue.class).redirect()) {
                                request.getRequestDispatcher(vue).forward(request, response);
                            } else {
                                response.sendRedirect(vue);
                            }
                        }
                    }
                }

            }

        } catch (InvocationTargetException e) {
            if (methode != null) {
                if (methode.getAnnotation(Vue.class) != null) {
                    String vue = setView(methode, true, false, null, null);

                    response.sendRedirect(vue + "?error=" + e.getCause().getLocalizedMessage());

                }

            } else {
                throw e;
            }
        } catch (IOException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException
                | InstantiationException | SecurityException | ServletException ex) {
            if (ex.getMessage().equals("Authentication failed")) {
                String vue = setView(methode, true, false, null, this.authView);
                response.sendRedirect(vue + "?error=Authentication failed");
            } else {
                throw ex;
            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            response.sendRedirect("error/error.jsp?err=" + ex.getMessage());
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
            response.sendRedirect("error/error.jsp?err=" + ex.getMessage());
        }
    }

    protected String setView(Method methode, boolean isError, boolean isQueryP, Object o, String authView) throws Exception {
        String vue = "";
        if (authView != null) {
            vue = authView;
        } else {
            if (isError) {
                vue = methode.getAnnotation(Vue.class).errorPage();
            } else {
                vue = methode.getAnnotation(Vue.class).vue();
            }
        }

        if (vue.equals("")) {
            vue = "index.jsp";
        }
        //raha misy query param
        if (isQueryP) {
            QueryParams qp = Outils.getQueryParam(o);
            if (qp.getSize() != 0) {
                Object[] keys = qp.getKeys();
                vue += "?";
                for (int i = 0; i < qp.getSize(); i++) {
                    if (i != (qp.getSize() - 1)) {
                        vue += (String) keys[i] + "=" + qp.get((String) keys[i]) + "&";
                    } else {
                        vue += (String) keys[i] + "=" + qp.get((String) keys[i]);
                    }
                }
            }
        }

        return vue;
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
