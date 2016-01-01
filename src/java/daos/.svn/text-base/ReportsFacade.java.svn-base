/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pojos.Reports;

/**
 *
 * @author tmiller
 */
@Stateless
public class ReportsFacade extends AbstractFacade<Reports> {
    @PersistenceContext(unitName = "eBayPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReportsFacade() {
        super(Reports.class);
    }
    
}
