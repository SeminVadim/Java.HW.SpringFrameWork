package ru.geekbrains;

import ru.geekbrains.persist.UserServer;
import ru.geekbrains.persist.UserServerRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet ("/first-http-servlet/*")
public class FirstHttpServlet extends HttpServlet {

    private UserServerRepo userServerRepo;
    @Override
    public void init() throws ServletException {
        this.userServerRepo = (UserServerRepo)getServletContext().getAttribute("userServerRepo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Hi world</h1>");
        resp.getWriter().println("<p>getContextPath()= " + req.getContextPath() + "</p>");
        resp.getWriter().println("<p>getServletPath()= " + req.getServletPath()+ "</p>");
        resp.getWriter().println("<p>getPathInfo()= " + req.getPathInfo()+ "</p>");
        resp.getWriter().println("<p>req.getQueryString()= " + req.getQueryString()+ "</p>");
        resp.getWriter().println("<p>req.getParameter(param1)= " + req.getParameter("param1")+ "</p>");
        resp.getWriter().println("<p>req.getParameter(param2)= " + req.getParameter("param2")+ "</p>");

    }
}
