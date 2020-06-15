package controllers.reports;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        //現在未評価の場合500errorとなってしまうためクエリ取得できない場合は0(未評価)を返す
        try {
            Good g = em.createNamedQuery("getGoodID", Good.class)
                .setParameter("employee", login_employee)
                .setParameter("report", r)
                .getSingleResult();

            request.setAttribute("reaction", g.getValue());

            List<Good> goods = em.createNamedQuery("getGoodsByRep", Good.class)
                    .setParameter("report", r)
                    .getResultList();

            request.setAttribute("goods", goods);


        }catch (Exception e){
            request.setAttribute("reaction", 0);
            request.setAttribute("goods", new Good());

        }
        long good_count = (long)em.createNamedQuery("getGoodsCount", Long.class)
                .setParameter("report", r)
                .getSingleResult();
        long bad_count = (long)em.createNamedQuery("getBadsCount", Long.class)
                .setParameter("report", r)
                .getSingleResult();

        em.close();

        request.setAttribute("report", r);
        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("good_count", good_count);
        request.setAttribute("bad_count", bad_count);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}