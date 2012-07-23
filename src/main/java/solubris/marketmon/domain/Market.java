package solubris.marketmon.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import solubris.marketmon.domain.types.MarketStatus;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionType = Short.class)
@RooJson
public class Market {

	@Id
    @NotNull
    @Min(1)
    private Long id;

    private Short exchangeId;

    @OrderBy("state.sortPriority ASC")
    @ManyToMany
    @JoinTable(name = "market_runner", joinColumns={@JoinColumn(name="market")}, inverseJoinColumns={@JoinColumn(name="runner")} )
    private List<Runner> runners = new ArrayList<Runner>();

    @ManyToOne(optional=true)
    Event event;

    private Boolean marketDataDelayed;

    @Embedded
    private MarketState state;

    @Embedded
    private MarketDescription description;

    /**
     * Time market went inplay
     * This is by observation only (not in API)
     * 
     * User mysql timestamp instead of datetime as its half the size
     */
//    @DateTimeFormat(style = "SS")
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(nullable = true, columnDefinition="timestamp")
//    private Date inplayTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "market")
    private List<MarketStatusTime> statusTimes = new ArrayList<MarketStatusTime>();

    public String getMarketId() {
    	if(exchangeId==null) {
    		return null;
    	}
    	if(id==null) {
    		return null;
    	}
        return exchangeId + "." + id;
    }

    public void setMarketId(String marketId) {
        String[] marketParts = marketId.split("[.]");
        exchangeId = Short.parseShort(marketParts[0]);
        id = Long.parseLong(marketParts[1]);
    }

    /**
     * TODO include market time for horse races
     * 
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString() {
		return String
				.format("%s [id=%s, state=%s]",
						(description==null?"":description.getMarketName()), getMarketId(), (state==null?"":state.getStatus()) );
	}

    public static List<Market> findMarketsByStatus(MarketStatus status) {
        if (status == null) throw new IllegalArgumentException("The status argument is required");
        EntityManager em = Market.entityManager();
        TypedQuery<Market> q = em.createQuery("SELECT o FROM Market AS o WHERE o.state.status = :status", Market.class);
        q.setParameter("status", status);
        return q.getResultList();
    }

    public static List<Market> findMarketsByStatusOrStatus(MarketStatus status1, MarketStatus status2) {
        if (status1 == null) throw new IllegalArgumentException("The status1 argument is required");
        if (status2 == null) throw new IllegalArgumentException("The status2 argument is required");
        EntityManager em = Market.entityManager();
        TypedQuery<Market> q = em.createQuery("SELECT o FROM Market AS o WHERE o.state.status = :status1 OR o.state.status = :status2", Market.class);
        q.setParameter("status1", status1);
        q.setParameter("status2", status2);
        return q.getResultList();
    }

	/**
	 * Convenience method to set market status
	 */
	public void setStatusOpen() {
		if(state==null) {
			state=new MarketState();
		}
		state.setStatus(MarketStatus.OPEN);
	}

	/**
	 * Convenience method to set market status
	 */
	public void setStatusClosed() {
		if(state==null) {
			state=new MarketState();
		}
		state.setStatus(MarketStatus.CLOSED);
	}

	/**
	 * Convenience method to set market status
	 */
	public void setStatusSuspended() {
		if(state==null) {
			state=new MarketState();
		}
		state.setStatus(MarketStatus.SUSPENDED);
	}
}
