// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package solubris.marketmon.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;
import solubris.marketmon.domain.Runner;

privileged aspect Runner_Roo_Jpa_Entity {
    
    declare @type: Runner: @Entity;
    
    @Version
    @Column(name = "version")
    private Short Runner.version;
    
    public Short Runner.getVersion() {
        return this.version;
    }
    
    public void Runner.setVersion(Short version) {
        this.version = version;
    }
    
}
