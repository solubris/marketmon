// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package solubris.marketmon.domain;

import java.util.Date;
import solubris.marketmon.domain.EventDescription;

privileged aspect EventDescription_Roo_JavaBean {
    
    public String EventDescription.getCountryCode() {
        return this.countryCode;
    }
    
    public void EventDescription.setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public String EventDescription.getTimezone() {
        return this.timezone;
    }
    
    public void EventDescription.setTimezone(String timezone) {
        this.timezone = timezone;
    }
    
    public Date EventDescription.getOpenDate() {
        return this.openDate;
    }
    
    public void EventDescription.setOpenDate(Date openDate) {
        this.openDate = openDate;
    }
    
    public String EventDescription.getEventName() {
        return this.eventName;
    }
    
    public void EventDescription.setEventName(String eventName) {
        this.eventName = eventName;
    }
    
}
