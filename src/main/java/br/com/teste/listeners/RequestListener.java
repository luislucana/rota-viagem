package br.com.teste.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@WebListener
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent event) {
    }

    @Override
    public void requestDestroyed(ServletRequestEvent event) {
        HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
        
        if (!request.getServletPath().equals("/counter")) {
            ServletContext context = event.getServletContext();
            
            String counterAttribute = context.getAttribute("counter") != null ? String.valueOf(context.getAttribute("counter")) : null;
            Integer counter = counterAttribute != null ? Integer.valueOf(counterAttribute) : 0;
            
        	context.setAttribute("counter", counter + 1);
        }
    }
}