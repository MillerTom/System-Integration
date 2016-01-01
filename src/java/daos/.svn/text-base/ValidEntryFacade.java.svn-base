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
import pojos.Users;
import pojos.ValidEntry;

/**
 *
 * @author tmiller
 */
@Stateless
public class ValidEntryFacade extends AbstractFacade<ValidEntry> {
    @PersistenceContext(unitName = "eBayPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ValidEntryFacade() {
        super(ValidEntry.class);
    }

    public List<?> findUserTypes() {
        try {
            Query query = getEntityManager().createNamedQuery("ValidEntry.findBetween");
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
