package solubris.marketmon.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import solubris.marketmon.domain.Market;
import solubris.marketmon.domain.MarketState;
import solubris.marketmon.domain.types.MarketStatus;

@RequestMapping("/markets")
@Controller
@RooWebScaffold(path = "markets", formBackingObject = Market.class, update=false)
@RooWebJson(jsonObject = Market.class)
public class MarketController {

    /**
     * By default, set market status as open
     * 
     * @param market
     * @param bindingResult
     * @param uiModel
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Market market, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, market);
            return "markets/create";
        }
        uiModel.asMap().clear();
        MarketState state=new MarketState();
        state.setStatus(MarketStatus.OPEN);
		market.setState(state);
//		market.setInplayTime(null);
//		market.setInplayTime(new Date(0L));
        market.persist();
        return "redirect:/markets/" + encodeUrlPathSegment(market.getId().toString(), httpServletRequest);
    }
    
}
