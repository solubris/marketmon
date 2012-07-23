package solubris.marketmon.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import solubris.marketmon.domain.MarketStatusTime;

@RequestMapping("/marketstatustimes")
@Controller
@RooWebScaffold(path = "marketstatustimes", formBackingObject = MarketStatusTime.class)
public class MarketStatusTimeController {
}
