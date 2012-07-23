package solubris.marketmon.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import org.hibernate.annotations.Table;
import org.hibernate.annotations.Index;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(mappedSuperclass = true, versionType = Short.class)
@RooJson
public class PriceSize {

    private Double size;

    private Double price;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "SS")
    @Column(nullable = false, columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP")
    Date createdAt = new Date();

    @ManyToOne(optional = false)
    Market market;

    @ManyToOne(optional = false)
    Runner runner;

    @Transactional
    public void mergeAndRetainVersion() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PriceSize merged = this.entityManager.merge(this);
        this.entityManager.flush();
        this.setVersion(merged.getVersion());
    }

    /*
    public static List<PriceSize> findPriceSizesByMarketAndRunner(Market market, Runner runner) {
        if (market == null) throw new IllegalArgumentException("The market argument is required");
        if (runner == null) throw new IllegalArgumentException("The runner argument is required");
        EntityManager em = PriceSize.entityManager();
        TypedQuery<PriceSize> q = em.createQuery("SELECT o FROM PriceSize AS o WHERE o.market = :market AND o.runner = :runner", PriceSize.class);
        q.setParameter("market", market);
        q.setParameter("runner", runner);
        return q.getResultList();
    }
    */
}
