/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Ihagatiana
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Authentification {

    //ny test auth tsmaints misy anaty session dol vo mety
    String[] testAuth();

    String[] roles();

    String errorRedirection();

    //"and" par defaut
    //zany hoe rehefa mtest anle roles de manao "and"
    //donc ra and de tsmaintsy misy any anaty session dol izy rehetra
    //raha or de afaka tsy misy anaty session ny sasany
    boolean roleOr() default false;

}
