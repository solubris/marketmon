package solubris.marketmon.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import solubris.marketmon.domain.types.BettingType;

@RooJavaBean
@RooToString
@Embeddable
@RooJson
public class MarketDescription {

    private String marketName;

    private Boolean turnInPlayEnabled;

    private Boolean persistenceEnabled;

    private Boolean bspMarket;

    @DateTimeFormat(style = "SS")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true, columnDefinition="timestamp DEFAULT 0")
    private Date suspendTime;

    @DateTimeFormat(style = "SS")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true, columnDefinition="timestamp DEFAULT 0")
    private Date marketTime;

    @Enumerated
    private BettingType bettingType;
}
