package solubris.marketmon.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionType = Short.class)
@RooJson
public class Event {

	@Id
    @NotNull
    @Min(1)
    private Long eventId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<Market> marketNodes = new ArrayList<Market>();

    /**
     * Needs to be optional as market it stored to db before event and eventType
     */
    @ManyToOne(optional = true)
    EventType eventType;

    @Embedded
    private EventDescription event;

    @Override
	public String toString() {
		return String.format(
				"%s [%d]", (event==null?"":event.getEventName()), eventId);
	}
}
