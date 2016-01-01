package pojos;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Reports;
import pojos.ValidEntry;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-09-04T12:37:14")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> lastName;
    public static volatile SingularAttribute<Users, String> phone;
    public static volatile SingularAttribute<Users, String> email;
    public static volatile SingularAttribute<Users, String> userName;
    public static volatile SingularAttribute<Users, Integer> uId;
    public static volatile CollectionAttribute<Users, Reports> reportsCollection;
    public static volatile SingularAttribute<Users, String> firstName;
    public static volatile SingularAttribute<Users, ValidEntry> type;
    public static volatile SingularAttribute<Users, String> password;

}