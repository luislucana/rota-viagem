package br.com.teste.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/counter", name = "counterServlet")
public class CounterServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        
		String counter = request.getServletContext().getAttribute("counter") != null
				? String.valueOf(request.getServletContext().getAttribute("counter"))
				: null;
        
        if (counter != null) {
        	Integer count = Integer.valueOf(counter);
        	//int count = (int) request.getServletContext().getAttribute("counter");
        	out.println("Request counter: " + count);
        }
    }
}