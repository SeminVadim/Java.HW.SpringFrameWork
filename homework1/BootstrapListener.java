package ru.geekbrains;

import ru.geekbrains.persist.UserServer;
import ru.geekbrains.persist.UserServerRepo;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BootstrapListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        UserServerRepo userServerRepo = new UserServerRepo();
        sc.setAttribute("userServerRepo", userServerRepo);

        userServerRepo.insertUser(new UserServer("user1"));
        userServerRepo.insertUser(new UserServer("user2"));
        userServerRepo.insertUser(new UserServer("user3"));

    }
}
