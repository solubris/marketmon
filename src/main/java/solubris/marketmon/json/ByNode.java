package solubris.marketmon.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

import flexjson.JSONDeserializer;
import flexjson.transformer.DateTransformer;

@RooJavaBean
@RooJson
@Configurable
public class ByNode {

    private static final String JSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private List<Node> nodes = new ArrayList<Node>();

    private Boolean indicative;

    public static ByNode fromJsonToByNode(String json) {
    	ByNode byNode=new JSONDeserializer<ByNode>().use(Date.class, new DateTransformer(JSON_DATE_FORMAT)).use(null, ByNode.class).deserialize(json);
        return byNode;
    }

	public static Collection<ByNode> fromJsonArrayToByNodes(String json) {
        return new JSONDeserializer<List<ByNode>>().use(Date.class, new DateTransformer(JSON_DATE_FORMAT)).use(null, ArrayList.class).use("values", ByNode.class).deserialize(json);
    }
}
