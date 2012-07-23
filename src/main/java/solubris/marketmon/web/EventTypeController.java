package solubris.marketmon.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import solubris.marketmon.domain.EventType;

@RequestMapping("/eventtypes")
@Controller
@RooWebScaffold(path = "eventtypes", formBackingObject = EventType.class, create=false, update=false, delete=false)
@RooWebJson(jsonObject = EventType.class)
public class EventTypeController {
}
