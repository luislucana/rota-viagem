package br.com.teste.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = "/uppercase")
public class EmptyParamFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		String inputString = request.getParameter("input");

		if (inputString != null && inputString.matches("[A-Za-z0-9]+")) {
			filterChain.doFilter(request, response);
		} else {
			response.getWriter().println("Missing input parameter");
		}
	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}
}