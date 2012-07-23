package solubris.marketmon.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

import solubris.marketmon.domain.Event;
import solubris.marketmon.domain.Market;
import solubris.marketmon.domain.MarketDescription;
import solubris.marketmon.domain.MarketStatusTime;
import solubris.marketmon.domain.Runner;

/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	public Converter<Runner, String> getRunnerToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<Runner, java.lang.String>() {
            public String convert(Runner runner) {
                return new StringBuilder().append(runner.toString()).toString();
            }
        };
    }

	public Converter<Event, String> getEventToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<Event, java.lang.String>() {
            public String convert(Event event) {
                return new StringBuilder().append(event.toString()).toString();
            }
        };
    }

	public Converter<Market, String> getMarketToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<Market, java.lang.String>() {
            public String convert(Market market) {
                return new StringBuilder().append(market.toString()).toString();
            }
        };
    }
    
	public Converter<MarketStatusTime, String> getMarketStatusTimeToStringConverter() {
        return new Converter<MarketStatusTime, java.lang.String>() {
            public String convert(MarketStatusTime marketStatusTime) {
                return new StringBuilder().append(marketStatusTime.toString()).toString();
            }
        };
    }

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
		new EmbeddedObjectRegistrar().registerFormatters(registry);
	}
}
