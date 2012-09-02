package solubris.marketmon.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionType = Short.class)
@RooJson
@Table(indexes={@Index(name = "IDX_MARKET_RUNNER", columnNames={"market", "runner"})}, appliesTo = "price_size_for_back")
public class PriceSizeForBack extends PriceSize {

	/**
	 * oldest prices first
	 * NOTE: use id for ordering so dont need index on createdAt column
	 * 
	 * @param market
	 * @param runner
	 * @return
	 */
	public static List<? extends PriceSize> findPriceSizesByMarketAndRunner(Market market, Runner runner, Date start, Date finish) {
        if (market == null) throw new IllegalArgumentException("The market argument is required");
        if (runner == null) throw new IllegalArgumentException("The runner argument is required");
        StringBuilder dateRangeQuery=new StringBuilder();
        if(start!=null) {
        	dateRangeQuery.append("AND createdAt >= :start ");
        }
        if(finish!=null) {
        	dateRangeQuery.append("AND createdAt <= :finish ");
        }
        EntityManager em = PriceSize.entityManager();
		String query = "SELECT o FROM PriceSizeForBack AS o WHERE o.market = :market AND o.runner = :runner " + dateRangeQuery + " ORDER BY id";
        TypedQuery<PriceSizeForBack> q = em.createQuery(query, PriceSizeForBack.class);
        q.setParameter("market", market);
        q.setParameter("runner", runner);
        if(start!=null) {
            q.setParameter("start", start);
        }
        if(finish!=null) {
            q.setParameter("finish", finish);
        }
        return q.getResultList();
    }

	/**
	 * newest prices first
	 * NOTE: use id for ordering so dont need index on createdAt column
	 * 
	 * @param market
	 * @param runner
	 * @return
	 */
	public static List<? extends PriceSize> find2MostRecentPriceSizesByMarketAndRunner(Market market, Runner runner) {
        if (market == null) throw new IllegalArgumentException("The market argument is required");
        if (runner == null) throw new IllegalArgumentException("The runner argument is required");
        EntityManager em = PriceSize.entityManager();
        TypedQuery<PriceSizeForBack> q = em.createQuery("SELECT o FROM PriceSizeForBack AS o WHERE o.market = :market AND o.runner = :runner ORDER BY id DESC", PriceSizeForBack.class);
        q.setParameter("market", market);
        q.setParameter("runner", runner);
        q.setMaxResults(2);
        return q.getResultList();
    }
}
