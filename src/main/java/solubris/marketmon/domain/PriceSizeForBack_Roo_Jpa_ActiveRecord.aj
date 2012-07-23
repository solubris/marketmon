// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package solubris.marketmon.domain;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import solubris.marketmon.domain.PriceSizeForBack;

privileged aspect PriceSizeForBack_Roo_Jpa_ActiveRecord {
    
    public static long PriceSizeForBack.countPriceSizeForBacks() {
        return entityManager().createQuery("SELECT COUNT(o) FROM PriceSizeForBack o", Long.class).getSingleResult();
    }
    
    public static List<PriceSizeForBack> PriceSizeForBack.findAllPriceSizeForBacks() {
        return entityManager().createQuery("SELECT o FROM PriceSizeForBack o", PriceSizeForBack.class).getResultList();
    }
    
    public static PriceSizeForBack PriceSizeForBack.findPriceSizeForBack(Long id) {
        if (id == null) return null;
        return entityManager().find(PriceSizeForBack.class, id);
    }
    
    public static List<PriceSizeForBack> PriceSizeForBack.findPriceSizeForBackEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PriceSizeForBack o", PriceSizeForBack.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public PriceSizeForBack PriceSizeForBack.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PriceSizeForBack merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
