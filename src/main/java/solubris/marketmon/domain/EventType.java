package solubris.marketmon.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Id;
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
public class EventType {

	@Id
    @NotNull
    @Min(1)
    private Long eventTypeId;
	
	private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventType")
    private List<Event> eventNodes = new ArrayList<Event>();

    @Override
	public String toString() {
		return String.format("EventType [eventTypeId=%s]", eventTypeId);
	}
}
