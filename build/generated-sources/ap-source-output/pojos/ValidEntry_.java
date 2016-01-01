package pojos;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Files;
import pojos.Reports;
import pojos.Users;
import pojos.ValidEntryTypes;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-09-04T12:37:14")
@StaticMetamodel(ValidEntry.class)
public class ValidEntry_ { 

    public static volatile CollectionAttribute<ValidEntry, Files> filesCollection;
    public static volatile SingularAttribute<ValidEntry, String> veDescribtion;
    public static volatile SingularAttribute<ValidEntry, ValidEntryTypes> veCategory;
    public static volatile CollectionAttribute<ValidEntry, Users> usersCollection;
    public static volatile SingularAttribute<ValidEntry, Integer> veId;
    public static volatile CollectionAttribute<ValidEntry, Reports> reportsCollection1;
    public static volatile SingularAttribute<ValidEntry, String> veName;
    public static volatile CollectionAttribute<ValidEntry, Reports> reportsCollection2;
    public static volatile CollectionAttribute<ValidEntry, Files> filesCollection1;
    public static volatile CollectionAttribute<ValidEntry, Reports> reportsCollection;
    public static volatile CollectionAttribute<ValidEntry, Files> filesCollection2;

}