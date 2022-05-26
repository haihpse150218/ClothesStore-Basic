/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.OrderDetail;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author SE150218 Hoang Phi Hai
 */
@Stateless
public class OrderDetailFacade extends AbstractFacade<OrderDetail> {

    @PersistenceContext(unitName = "clothesStorePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderDetailFacade() {
        super(OrderDetail.class);
    }
    
    public List<OrderDetail> findAllByOrderHeaderId(Integer id){
        List<OrderDetail> tmp = findAll();
        List<OrderDetail> list = new ArrayList<>();
        for (OrderDetail orderDetail : tmp) {
            if(orderDetail.getOrderId().getId().compareTo(id) == 0){
                list.add(orderDetail);
            }
        }
        return list;
    }
    public double caculateTotalByHeaderId(Integer id){
        List<OrderDetail> tmp = findAll();
        double total = 0;
        for (OrderDetail orderDetail : tmp) {
            if(orderDetail.getOrderId().getId().compareTo(id) == 0){
               total+= orderDetail.getPrice();
            }
        }
        return total;
    }
    
}
