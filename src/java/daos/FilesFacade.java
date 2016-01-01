/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pojos.Files;

/**
 *
 * @author tmiller
 */
@Stateless
public class FilesFacade extends AbstractFacade<Files> {

    @PersistenceContext(unitName = "eBayPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FilesFacade() {
        super(Files.class);
    }

    public Files findByFileName(String fileName) {
        try {
            Query query = em.createNamedQuery("Files.findByFileName");
            query.setParameter("fileName", fileName);
            Files file = (Files) query.getSingleResult();
            return file;
        } catch (Exception e) {
            return null;
        }
    }
}
