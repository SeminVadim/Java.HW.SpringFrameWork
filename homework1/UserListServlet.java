package ru.geekbrains;

import ru.geekbrains.persist.UserServer;
import ru.geekbrains.persist.UserServerRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/userlist")
public class UserListServlet extends HttpServlet {

    private UserServerRepo userServerRepo;
    @Override
    public void init() throws ServletException {
        this.userServerRepo = (UserServerRepo) getServletContext().getAttribute("userServerRepo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<table>");
            resp.getWriter().println("<tr>");
                resp.getWriter().println("<th> id </th>");
                resp.getWriter().println("<th> name </th>");
            resp.getWriter().println("</tr>");

            for(UserServer userServer :userServerRepo.findAll()) {
            resp.getWriter().println("<tr>");
                resp.getWriter().println("<td>"+ userServer.getId()+ "</td>");
                resp.getWriter().println("<td><a href ='"+getServletContext().getContextPath() +
                                                "/user/" + userServer.getId() +"'>"+
                                                userServer.getUsername()+ "</a></td>");
            resp.getWriter().println("</tr>");


        }
        resp.getWriter().println("</table>");
    }
}
