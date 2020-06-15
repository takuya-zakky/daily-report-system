package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "goods")
@NamedQueries({
    @NamedQuery(
            name = "getAllGoods",
            query = "SELECT g FROM Good AS g ORDER BY g.id DESC"
            ),
    @NamedQuery(
            name = "getGoodsByEmp",
            query = "SELECT g FROM Good AS g where g.employee = :employee"
            ),
    @NamedQuery(
            name = "getGoodsByRep",
            query = "SELECT g FROM Good AS g where g.report = :report"
            ),
    @NamedQuery(
            name = "getGoodsCount",
            query = "SELECT COUNT(g) FROM Good AS g where g.report = :report AND g.value=1"
            ),
    @NamedQuery(
            name = "getBadsCount",
            query = "SELECT COUNT(g) FROM Good AS g where g.report = :report AND g.value=2"
            ),
    @NamedQuery(
            name = "getGoodID",
            query = "SELECT g FROM Good AS g where g.employee = :employee AND g.report = :report"
            ),
})

@Entity
public class Good {
    /**
     * @return employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * @param employee セットする employee
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * @return report
     */
    public Report getReport() {
        return report;
    }

    /**
     * @param report セットする report
     */
    public void setReport(Report report) {
        this.report = report;
    }

    /**
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value セットする value
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "report", nullable = false)
    private Report report;

    /**
     * @category 1: いいね！, 2: 微妙・・・
     */
    @Column(name = "value", nullable = false)
    private int value;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id セットする id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @return created_at
     */
    public Timestamp getCreated_at() {
        return created_at;
    }

    /**
     * @param created_at セットする created_at
     */
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    /**
     * @return updated_at
     */
    public Timestamp getUpdated_at() {
        return updated_at;
    }

    /**
     * @param updated_at セットする updated_at
     */
    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }


}
