package controllers.goods;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Good;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class GoodsCreateServlet
 */
@WebServlet("/goods/create")
public class GoodsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodsCreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Good g = new Good();

            g.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
            g.setReport(em.find(Report.class, Integer.parseInt(request.getParameter("report_id"))));
            g.setValue(Integer.parseInt(request.getParameter("reaction")));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            g.setCreated_at(currentTime);
            g.setUpdated_at(currentTime);

            em.getTransaction().begin();
            em.persist(g);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "評価が完了しました。");

            response.sendRedirect(request.getContextPath() + "/reports/show?id=" + Integer.parseInt(request.getParameter("report_id")));

        }

    }

}
