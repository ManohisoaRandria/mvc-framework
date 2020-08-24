/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtre;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author Ihagatiana
 */

public class Filtree implements Filter {
    private FilterConfig filterConfig = null;
    
    public Filtree() {
    }    
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain)throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String contextPath=httpRequest.getRequestURI();
        System.out.println(contextPath);
        if(contextPath.endsWith(".make")){
            String[] URLpart=httpRequest.getRequestURI().split("/");
            String url="";
            for(String part : URLpart){
                if(part.contains(".make")){
                    String[] tab=part.split(".make");
                    url=tab[0];
                }
            }
            request.setAttribute("url",url);
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            request.getRequestDispatcher("servlet").forward(request, response);
            
        }else{
            chain.doFilter(request, response);
        }
    }
    public void destroy() {        
    }
    public void init(FilterConfig filterConfig) {
    }

    
}
