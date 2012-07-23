// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package solubris.marketmon.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import solubris.marketmon.domain.MarketDescription;

privileged aspect MarketDescription_Roo_Json {
    
    public String MarketDescription.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static MarketDescription MarketDescription.fromJsonToMarketDescription(String json) {
        return new JSONDeserializer<MarketDescription>().use(null, MarketDescription.class).deserialize(json);
    }
    
    public static String MarketDescription.toJsonArray(Collection<MarketDescription> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<MarketDescription> MarketDescription.fromJsonArrayToMarketDescriptions(String json) {
        return new JSONDeserializer<List<MarketDescription>>().use(null, ArrayList.class).use("values", MarketDescription.class).deserialize(json);
    }
    
}
