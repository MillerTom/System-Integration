package pojos;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Users;
import pojos.ValidEntry;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-09-04T12:37:14")
@StaticMetamodel(Reports.class)
public class Reports_ { 

    public static volatile SingularAttribute<Reports, String> reportTime;
    public static volatile SingularAttribute<Reports, ValidEntry> reportType;
    public static volatile SingularAttribute<Reports, ValidEntry> status;
    public static volatile SingularAttribute<Reports, String> fileName;
    public static volatile SingularAttribute<Reports, ValidEntry> loadStatus;
    public static volatile SingularAttribute<Reports, Date> reportDate;
    public static volatile SingularAttribute<Reports, Users> user;
    public static volatile SingularAttribute<Reports, Integer> rId;

}