/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pojos.Logs;

/**
 *
 * @author tmiller
 */
@Stateless
public class LogsFacade extends AbstractFacade<Logs> {
    @PersistenceContext(unitName = "eBayPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LogsFacade() {
        super(Logs.class);
    }

    public List viewlog() {
        Query query=em.createNamedQuery("Logs.findAllByLogID");
        return query.getResultList();
    }
    
}
