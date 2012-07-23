package solubris.marketmon.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import solubris.marketmon.domain.Runner;

@RequestMapping("/runners")
@Controller
@RooWebScaffold(path = "runners", formBackingObject = Runner.class, create=false, update=false, delete=false)
@RooWebJson(jsonObject = Runner.class)
public class RunnerController {
}
