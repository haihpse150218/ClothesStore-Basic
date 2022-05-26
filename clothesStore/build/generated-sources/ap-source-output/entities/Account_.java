package entities;

import entities.Customer;
import entities.Employee;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-05-26T18:58:42")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, String> password;
    public static volatile SingularAttribute<Account, String> address;
    public static volatile SingularAttribute<Account, String> role;
    public static volatile SingularAttribute<Account, String> phone;
    public static volatile SingularAttribute<Account, String> name;
    public static volatile SingularAttribute<Account, Integer> id;
    public static volatile SingularAttribute<Account, String> userName;
    public static volatile SingularAttribute<Account, Employee> employee;
    public static volatile SingularAttribute<Account, String> email;
    public static volatile SingularAttribute<Account, Boolean> enabled;
    public static volatile SingularAttribute<Account, Customer> customer;

}