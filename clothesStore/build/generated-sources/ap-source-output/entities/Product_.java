package entities;

import entities.Category;
import entities.OrderDetail;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-05-26T18:58:42")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile ListAttribute<Product, OrderDetail> orderDetailList;
    public static volatile SingularAttribute<Product, Double> price;
    public static volatile SingularAttribute<Product, String> description;
    public static volatile SingularAttribute<Product, Double> discount;
    public static volatile SingularAttribute<Product, Integer> id;
    public static volatile SingularAttribute<Product, Category> categoryId;

}