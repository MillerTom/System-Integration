package pojos;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-09-04T12:37:14")
@StaticMetamodel(Logs.class)
public class Logs_ { 

    public static volatile SingularAttribute<Logs, String> message;
    public static volatile SingularAttribute<Logs, Date> actionDate;
    public static volatile SingularAttribute<Logs, Integer> lId;
    public static volatile SingularAttribute<Logs, String> userName;
    public static volatile SingularAttribute<Logs, String> messageType;
    public static volatile SingularAttribute<Logs, String> actionTime;

}