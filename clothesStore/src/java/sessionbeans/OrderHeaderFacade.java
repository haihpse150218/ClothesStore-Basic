/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.OrderHeader;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author SE150218 Hoang Phi Hai
 */
@Stateless
public class OrderHeaderFacade extends AbstractFacade<OrderHeader> {

    @PersistenceContext(unitName = "clothesStorePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderHeaderFacade() {
        super(OrderHeader.class);
    }
    
}
