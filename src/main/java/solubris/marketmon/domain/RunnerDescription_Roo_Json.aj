// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package solubris.marketmon.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import solubris.marketmon.domain.RunnerDescription;

privileged aspect RunnerDescription_Roo_Json {
    
    public String RunnerDescription.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static RunnerDescription RunnerDescription.fromJsonToRunnerDescription(String json) {
        return new JSONDeserializer<RunnerDescription>().use(null, RunnerDescription.class).deserialize(json);
    }
    
    public static String RunnerDescription.toJsonArray(Collection<RunnerDescription> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<RunnerDescription> RunnerDescription.fromJsonArrayToRunnerDescriptions(String json) {
        return new JSONDeserializer<List<RunnerDescription>>().use(null, ArrayList.class).use("values", RunnerDescription.class).deserialize(json);
    }
    
}
