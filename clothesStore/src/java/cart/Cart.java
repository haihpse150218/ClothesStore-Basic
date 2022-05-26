package cart;

import entities.Product;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import sessionbeans.ProductFacade;

public class Cart {
    ProductFacade pf = lookupProductFacadeBean();
    Map<Integer, Item> map = null;

    public Cart() {
        map = new HashMap();
    }
    public void add(int id, int quantity) throws SQLException {
        if (map.get(id) == null) {
            Product product = pf.find(id);
            Item item = new Item(product.getId(), product.getDescription(),
                    product.getPrice(), product.getDiscount(), quantity);
            map.put(id, item);
        } else {
            Item item = map.get(id);
            int oldQantity = item.getQuantity();
            int newQantity = oldQantity + quantity;
            update(id, newQantity);
        }

    }
    public int getNumOfProducts() {
//        int numOfPro = 0;
//        for (Item item : map.values()) {
//            numOfPro += item.getQuantity();
//        }
//        return numOfPro;
        return map.size();
    }

    public Collection<Item> getItems() {
        return map.values();
    }

    public double getTotal() {
        double total = 0;
        for (Item item : map.values()) {
            total += item.getCost();
        }
        return total;
    }

    public void update(int id, int quantity) {
//        Lay item tu cart trong gio hang ra
        Item item = map.get(id);
//        update quantity
        item.setQuantity(quantity);
    }

    public void delete(int id) {
//        xoa item trong cart
        map.remove(id);
    }

    public void empty() {
        map.clear();
    }

    private ProductFacade lookupProductFacadeBean() {
        try {
            Context c = new InitialContext();
            return (ProductFacade) c.lookup("java:global/clothesStore/ProductFacade!sessionbeans.ProductFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
