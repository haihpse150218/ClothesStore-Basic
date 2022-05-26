/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Product;
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
public class ProductFacade extends AbstractFacade<Product> {

    @PersistenceContext(unitName = "clothesStorePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }
    
    public List<Product> findAllByCateId(int cateId){
        List<Product> list = new ArrayList<>();
        List<Product> tmp = this.findAll();
        for (Product product : tmp) {
            if(product.getCategoryId().getId() == cateId){
                list.add(product);
            }
        }
        return list;
    }
    public int countListCate(int cateId){
        List<Product> tmp = this.findAllByCateId(cateId);
        return tmp.size();
    }
    public List<Product> findByCateIdRange(int cateId, int[] range){
        List<Product> list = new ArrayList<>();
        List<Product> tmp = this.findAllByCateId(cateId);
        int n1 = range[0];
        int n2 = range[1];
        if(range[1] >= tmp.size()){
            n2 = tmp.size()-1;
        }
        for (int i = n1; i <= n2; i++) {
            list.add(tmp.get(i));
        }
        return list;
    }
    
}
