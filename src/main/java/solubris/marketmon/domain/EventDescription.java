package solubris.marketmon.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@Embeddable
public class EventDescription {

    @Size(max = 3)
    private String countryCode;

    private String timezone;

    @DateTimeFormat(style = "SS")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true, columnDefinition="timestamp DEFAULT 0")
    private Date openDate;

    private String eventName;
}
