package org.goit.servlets;


import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine engine;
    String time;
    ZoneId zoneId;
    String val;
    DateTimeFormatter formatter;

    @Override
    public void init() throws ServletException {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix(getClass().getClassLoader().getResource("templates").getPath());
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("<service>");
        resp.setContentType("text/html; charset=UTF-8");
        val = req.getParameter("timezone");

        if(val !=  null && !val.isEmpty()){
            log("if(val !=  null)");
            resp.addCookie(new Cookie("timezone", val));
            log("val = "+val);
            zoneId = ZoneId.of(val);
        }
        else{
            log("else: ");
            zoneId = ZoneId.of(getTimeZoneCookie(req));
        }

        ZonedDateTime zoneTime = ZonedDateTime.now(zoneId);
        time = zoneTime.format(formatter);

        Context timeContext = new Context();
        timeContext.setVariable("time", time);
        engine.process("timePage", timeContext, resp.getWriter());
        resp.getWriter().close();
        log("</service>");
    }
    @Override
    public void destroy() {
        time = null;
    }
    private String getTimeZoneCookie(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        log("<getTimeZoneCookie>");
        if (cookies != null) {
            log("if (cookies != null)");
            for (Cookie cookie : cookies) {
                log("for: " + cookie.getName()+"="+cookie.getValue());
                if(cookie.getName().equals("timezone")){
                    log("if(cookie.getName()==timezone");
                    log("cookie.getValue() = " + cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        log("cookies = null");
        log("</getTimeZoneCookie>");
        return "UTC";
    }

}
