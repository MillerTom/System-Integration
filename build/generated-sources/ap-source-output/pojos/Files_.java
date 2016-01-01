package pojos;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.ValidEntry;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-09-04T12:37:14")
@StaticMetamodel(Files.class)
public class Files_ { 

    public static volatile SingularAttribute<Files, ValidEntry> requestStatus;
    public static volatile SingularAttribute<Files, Date> fileDate;
    public static volatile SingularAttribute<Files, ValidEntry> fileType;
    public static volatile SingularAttribute<Files, ValidEntry> responseStatus;
    public static volatile SingularAttribute<Files, String> filePath;
    public static volatile SingularAttribute<Files, String> fileName;
    public static volatile SingularAttribute<Files, String> downloadFilePath;
    public static volatile SingularAttribute<Files, Integer> fId;
    public static volatile SingularAttribute<Files, String> fileTime;

}