package pojos;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.ValidEntry;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-09-04T12:37:14")
@StaticMetamodel(ValidEntryTypes.class)
public class ValidEntryTypes_ { 

    public static volatile SingularAttribute<ValidEntryTypes, String> vetDescription;
    public static volatile CollectionAttribute<ValidEntryTypes, ValidEntry> validEntryCollection;
    public static volatile SingularAttribute<ValidEntryTypes, Integer> vetId;

}