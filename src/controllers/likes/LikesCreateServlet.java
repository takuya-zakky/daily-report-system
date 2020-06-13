package controllers.likes;

import java.io.IOException;
import java.sql.Timestamp;

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
 * Servlet implementation class LikesCreateservlet
 */
@WebServlet("/likes/create")
public class LikesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikesCreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Like l = new Like();

            l.setEmployee1((Employee)request.getSession().getAttribute("login_employee"));
            l.setEmployee2(em.find(Employee.class, Integer.parseInt(request.getParameter("id"))));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            l.setCreated_at(currentTime);
            l.setUpdated_at(currentTime);

            em.getTransaction().begin();
            em.persist(l);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "フォローが完了しました。");

            response.sendRedirect(request.getContextPath() + "/likes/index");
        }
    }
}
