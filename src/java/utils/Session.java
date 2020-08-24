/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author P11A-MANOHISOA
 */
public class Session{

    private static HashMap<String,Object>data;
    private static HttpSession s;
    private static HttpServletRequest req;

   
    
    public Session() {
        
    }
    public Object get(String field){
        //data=(HashMap<String, Object>) s.getAttribute("data");
        return data.get(field);
    }
    public void set(String field,Object value){
        data.put(field, value);
        s.setAttribute("data", data);  
    }
    public void remove(String field){
        data.remove(field);
        s.setAttribute("data", data); 
    }
    public void beginSession(){
        endSession();
        data=new HashMap<>();
        s=req.getSession();
        s.setAttribute("data", data);
    }
    public void endSession(){
        if(s!=null && data!=null){
            s.invalidate();
            data.clear();
        }
        
    }
    public void setReq(HttpServletRequest aReq) {
        req = aReq;
        if(req.getSession(false)!=null){
            s=req.getSession();
        }
    }
    public boolean isCookie(){
        return req.isRequestedSessionIdFromCookie();
    }
    public boolean  isValide(){
        return !data.isEmpty() && req.getSession(false)!=null;
    }
}
