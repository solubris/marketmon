package solubris.marketmon.domain;

import java.awt.Paint;
import java.awt.Color;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import solubris.marketmon.domain.types.MarketStatus;

/**
 * Records time of any changes in market status
 * 
 * @author walterst
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class MarketStatusTime {
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "SS")
    @Column(nullable = false, columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP")
    Date createdAt = new Date();

	@ManyToOne(optional = false)
    Market market;

    /**
     * only set if inplay field has changed
     */
    private Boolean inplay;

    @Enumerated
    private MarketStatus status;

    @Override
	public String toString() {
    	if(inplay!=null) {
    		return String.format("%s @ %s", inplay?"INPLAY_ON":"INPLAY_OFF", createdAt);
    	} else {
    		return String.format("%s @ %s", status, createdAt);
    	}
	}

	public String getLabelText() {
    	if(inplay!=null) {
    		return inplay?"INPLAY_ON":"INPLAY_OFF";
    	} else {
    		return status.toString();
    	}
	}

	public Paint getLabelColour() {
    	if(inplay!=null) {
    		return Color.green;
    	} else {
    		return status.getLabelColour();
    	}
	}
}
