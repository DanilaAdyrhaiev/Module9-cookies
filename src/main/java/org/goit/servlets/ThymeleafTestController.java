package org.goit.servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(value = "/test-template")
public class ThymeleafTestController extends HttpServlet {
    private TemplateEngine engine;
    @Override
    public void init() throws ServletException {
        engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("./templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private Map<String, String> getCookies(HttpServletRequest req){
        String cookies = req.getHeader("Cookie");
        if(cookies == null)
            return Collections.emptyMap();

        Map<String, String> res = new HashMap<>();
        String[] separateCookies = cookies.split(";");
        log("getCookies().separateCookies: "+ separateCookies.toString());
        for (String pair : separateCookies){
            String[] key = pair.split("=");
            res.put(key[0], key[1]);
        }
        log(res.toString());
        return res;
    }
}
