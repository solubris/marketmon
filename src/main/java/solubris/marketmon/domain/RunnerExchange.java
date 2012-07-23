package solubris.marketmon.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@Embeddable
@RooJson
public class RunnerExchange {

    @OneToMany(cascade = CascadeType.ALL, mappedBy="runner", orphanRemoval=false)
    private List<PriceSizeForBack> availableToBack = new ArrayList<PriceSizeForBack>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="runner", orphanRemoval=false)
    private List<PriceSizeForLay> availableToLay = new ArrayList<PriceSizeForLay>();
}
