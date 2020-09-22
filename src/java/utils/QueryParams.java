/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.HashMap;

/**
 *
 * @author manohisoa
 */
public class QueryParams {

    private static final HashMap<String, String> DATA = new HashMap<>();

    public QueryParams() {
        DATA.clear();
    }

    public void add(String name, String data) {
        DATA.put(name, data);
    }

    public Object[] getKeys() {
        return DATA.keySet().toArray();
    }

    public String get(String name) {
        return DATA.get(name);
    }

    public int getSize() {
        return DATA.size();
    }

}
