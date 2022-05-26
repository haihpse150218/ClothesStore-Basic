package entities;

import entities.Account;
import entities.OrderHeader;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-05-26T18:58:42")
@StaticMetamodel(Employee.class)
public class Employee_ { 

    public static volatile SingularAttribute<Employee, Integer> departmentId;
    public static volatile ListAttribute<Employee, OrderHeader> orderHeaderList;
    public static volatile SingularAttribute<Employee, Integer> id;
    public static volatile SingularAttribute<Employee, BigDecimal> salary;
    public static volatile SingularAttribute<Employee, Account> account;

}