package controllers.likes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Like;
import utils.DBUtil;

/**
 * Servlet implementation class LikesIndexServlet
 */
@WebServlet("/likes/index")
public class LikesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikesIndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) { }
        List<Employee> others = em.createNamedQuery("getOtherEmployees", Employee.class)
                                     .setParameter("employee_id", login_employee.getId())
                                     .setFirstResult(15 * (page - 1))
                                     .setMaxResults(15)
                                     .getResultList();

        long other_count = (long)em.createNamedQuery("getOtherCount", Long.class)
                                       .setParameter("employee_id", login_employee.getId())
                                       .getSingleResult();

        List<Like> likes = em.createNamedQuery("getLikes", Like.class)
                .setParameter("employee1", login_employee)
                .getResultList();

        em.close();


        List<Employee> one_like_list = new ArrayList<>();
        for(Like l : likes) {
            one_like_list.add(l.getEmployee2());
        }

        request.setAttribute("others", others);
        request.setAttribute("other_count", other_count);
        request.setAttribute("page", page);
        request.setAttribute("likes", likes);
        request.setAttribute("one_like_list", one_like_list);
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/likes/index.jsp");
        rd.forward(request, response);
    }

}
