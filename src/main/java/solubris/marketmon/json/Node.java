package solubris.marketmon.json;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

@RooJavaBean
@RooJson
@Configurable
public class Node {

	private String name;
	private String nodeType;
	private String nodeId;
	private Integer rank;
}
