package com.epam.servlet.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter",
        urlPatterns = {"*.do"},
        initParams = {
                @WebInitParam(name = "characterEncoding", value = "utf-8")})

public class EncodingFilter implements Filter{
    private static Logger logger = Logger.getLogger(EncodingFilter.class);
    private String encoding;
    private ServletContext servletContext;

    @Override
    public void init(FilterConfig fConfig) {
        encoding = fConfig.getInitParameter("characterEncoding");
        servletContext=fConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        servletContext.log("charset is set");
        chain.doFilter(request, response);
    }


    @Override
    public void destroy() { }
    }

