package org.goit.servlets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;


@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String timezoneParam = req.getParameter("timezone");

        if (timezoneParam == null || isValid(timezoneParam)){
            chain.doFilter(req, res);
        }else{
            res.setStatus(400);
            res.setContentType("text/plain;charset=UTF-8");
            res.getWriter().write("Invalid timezone");
            res.getWriter().close();
        }
    }

    private boolean isValid(String timezone){
        try {
            ZoneId.of(timezone);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
