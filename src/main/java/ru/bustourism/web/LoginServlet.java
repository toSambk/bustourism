package ru.bustourism.web;

import ru.bustourism.dao.UserDAO;
import ru.bustourism.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        EntityManagerFactory factory = StartupListener.getFactory(req.getServletContext());
        EntityManager manager = factory.createEntityManager();
        UserDAO userDAO = new UserDAO(manager);
        try {
            User user = userDAO.findByLoginAndPassword(login, password);
            req.getSession().setAttribute("userId", user.getId());
            resp.sendRedirect("/dashboard");
        } catch(NoResultException notFound) {
            req.getRequestDispatcher("/index.jsp?login=" + login).forward(req, resp);
        } finally {
            manager.close();
        }
    }
}
