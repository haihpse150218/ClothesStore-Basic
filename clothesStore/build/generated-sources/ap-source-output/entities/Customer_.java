package entities;

import entities.Account;
import entities.OrderHeader;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-05-26T18:58:42")
@StaticMetamodel(Customer.class)
public class Customer_ { 

    public static volatile SingularAttribute<Customer, String> shipToAddress;
    public static volatile ListAttribute<Customer, OrderHeader> orderHeaderList;
    public static volatile SingularAttribute<Customer, Integer> id;
    public static volatile SingularAttribute<Customer, String> category;
    public static volatile SingularAttribute<Customer, Account> account;

}