// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package solubris.marketmon.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import solubris.marketmon.domain.MarketState;

privileged aspect MarketState_Roo_Json {
    
    public String MarketState.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static MarketState MarketState.fromJsonToMarketState(String json) {
        return new JSONDeserializer<MarketState>().use(null, MarketState.class).deserialize(json);
    }
    
    public static String MarketState.toJsonArray(Collection<MarketState> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<MarketState> MarketState.fromJsonArrayToMarketStates(String json) {
        return new JSONDeserializer<List<MarketState>>().use(null, ArrayList.class).use("values", MarketState.class).deserialize(json);
    }
    
}
