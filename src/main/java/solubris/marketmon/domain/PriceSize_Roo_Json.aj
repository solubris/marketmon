// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package solubris.marketmon.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import solubris.marketmon.domain.PriceSize;

privileged aspect PriceSize_Roo_Json {
    
    public String PriceSize.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static PriceSize PriceSize.fromJsonToPriceSize(String json) {
        return new JSONDeserializer<PriceSize>().use(null, PriceSize.class).deserialize(json);
    }
    
    public static String PriceSize.toJsonArray(Collection<PriceSize> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<PriceSize> PriceSize.fromJsonArrayToPriceSizes(String json) {
        return new JSONDeserializer<List<PriceSize>>().use(null, ArrayList.class).use("values", PriceSize.class).deserialize(json);
    }
    
}
