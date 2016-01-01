/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pojos.Users;

/**
 *
 * @author tmiller
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "eBayPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }

    
    /**
     * Method to Check User Name and Password...
     * 
     * @param loginuser
     * 
     * @return Full User Object 
     */
    public Users findByUserNameAndPassword(Users loginuser) {
        try {
            Query query = getEntityManager().createNamedQuery("Users.findByUserNameAndPassword");
            query.setParameter("userName", loginuser.getUserName());
            query.setParameter("password", loginuser.getPassword());
            Users user = (Users) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Method to get User by His Mail...
     * 
     * @param mail
     * 
     * @return Users 
     */
    public Users findByEmail(String mail) {
        try {
            Query query = getEntityManager().createNamedQuery("Users.findByEmail");
            query.setParameter("email", mail);
            Users user = (Users) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    
    /**
     * Method to Get User by User Name...
     * 
     * @param UserName
     * 
     * @return Users
     */
    public Users findByUserName(String UserName) {
        try {
            Query query = getEntityManager().createNamedQuery("Users.findByUserName");
            query.setParameter("userName", UserName);
            Users user = (Users) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
