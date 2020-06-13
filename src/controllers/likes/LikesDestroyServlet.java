package controllers.likes;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Like;
import utils.DBUtil;

/**
 * Servlet implementation class LikesDestroyServlet
 */
@WebServlet("/likes/destroy")
public class LikesDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikesDestroyServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Like Target = em.createNamedQuery("getLikeID", Like.class)
                    .setParameter("employee1", (Employee)request.getSession().getAttribute("login_employee"))
                    .setParameter("employee2", em.find(Employee.class, Integer.parseInt(request.getParameter("id"))))
                    .getSingleResult();

            em.getTransaction().begin();
            em.remove(Target);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "フォローを解除しました。");

            response.sendRedirect(request.getContextPath() + "/likes/index");
        }
    }
}
