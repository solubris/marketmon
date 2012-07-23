package solubris.marketmon.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import solubris.marketmon.domain.types.RunnerStatus;

@RooJavaBean
@RooToString
@Embeddable
@RooJson
public class RunnerState {

    private Double totalMatched;

    private Short sortPriority;

    private Double lastPriceTraded;

    @Enumerated
    @Column(columnDefinition="tinyint")
    private RunnerStatus status;
}
