package solubris.marketmon.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

import org.hibernate.annotations.Index;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import solubris.marketmon.domain.types.MarketStatus;

@RooJavaBean
@RooToString
@Embeddable
@RooJson
public class MarketState {

    private Double totalMatched;

    private Double totalAvailable;

    @Column(name = "betfair_version")
    private Long version;

    private Short numberOfWinners;

    private Short numberOfRunners;

    private Short numberOfActiveRunners;

    private Short betDelay;

    private Boolean runnersVoidable;

    private Boolean bspReconciled;

    private Boolean complete;

    private Boolean inplay;

    private Boolean crossMatching;

    @Enumerated
    @Column(columnDefinition="tinyint")
    @Index(name="IDX_STATUS")
    private MarketStatus status;
}
