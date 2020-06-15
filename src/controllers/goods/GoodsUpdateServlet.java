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
 * Servlet implementation class GoodsUpdateServlet
 */
@WebServlet("/goods/update")
public class GoodsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodsUpdateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Good g = em.createNamedQuery("getGoodID", Good.class)
                    .setParameter("employee", (Employee)request.getSession().getAttribute("login_employee"))
                    .setParameter("report", em.find(Report.class, Integer.parseInt(request.getParameter("report_id"))))
                    .getSingleResult();

            g.setValue(Integer.parseInt(request.getParameter("reaction")));
            g.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();

            request.getSession().setAttribute("flush", "評価の更新が完了しました。");
            response.sendRedirect(request.getContextPath() + "/reports/show?id=" + Integer.parseInt(request.getParameter("report_id")));

        }
    }

}
