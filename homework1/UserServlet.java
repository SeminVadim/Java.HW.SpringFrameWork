package ru.geekbrains;

import ru.geekbrains.persist.UserServer;
import ru.geekbrains.persist.UserServerRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/user/*")
public class UserServlet extends HttpServlet {

    private static final Pattern PARAM_PATTERN = Pattern.compile("\\/(\\d+)");

    private UserServerRepo userServerRepo;
    @Override
    public void init() throws ServletException {
        this.userServerRepo = (UserServerRepo) getServletContext().getAttribute("userServerRepo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Matcher matcher = PARAM_PATTERN.matcher(req.getPathInfo());
        if (!matcher.matches()) {
            resp.getWriter().println("<p>Bat request</p>");
            resp.setStatus(400);
            return;
        }
        long id = Long.parseLong(matcher.group(1));
        UserServer userServer = userServerRepo.findById(id);

        if (userServer == null) {
            resp.getWriter().println("<p>User not found </p>");
            resp.setStatus(404);
            return;
        }
        resp.getWriter().println("<p>userServerId = " + userServer.getId() +"</p>");
        resp.getWriter().println("<p>userServerName = " + userServer.getUsername() +"</p>");


    }
}
