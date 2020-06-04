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


@Table(name = "likes")
@NamedQueries({
    @NamedQuery(
            name = "getAllLikes",
            query = "SELECT l FROM Like AS l ORDER BY l.id DESC"
            ),
    @NamedQuery(
            name = "getLikesCounts",
            query = "SELECT COUNT(l) FROM Like AS l"
            ),
})
@Entity
public class Like {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee1", nullable = false)
    private Employee employee1;

    @ManyToOne
    @JoinColumn(name = "employee2", nullable = false)
    private Employee employee2;

    /**
     * @return employee1
     */
    public Employee getEmployee1() {
        return employee1;
    }

    /**
     * @param employee1 セットする employee1
     */
    public void setEmployee1(Employee employee1) {
        this.employee1 = employee1;
    }

    /**
     * @return employee2
     */
    public Employee getEmployee2() {
        return employee2;
    }

    /**
     * @param employee2 セットする employee2
     */
    public void setEmployee2(Employee employee2) {
        this.employee2 = employee2;
    }

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