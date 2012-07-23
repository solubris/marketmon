package solubris.marketmon.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;

import solubris.marketmon.domain.EventDescription;
import solubris.marketmon.domain.MarketDescription;
import solubris.marketmon.domain.MarketState;
import solubris.marketmon.domain.RunnerDescription;
import solubris.marketmon.domain.RunnerExchange;
import solubris.marketmon.domain.RunnerState;

public class EmbeddedObjectRegistrar implements FormatterRegistrar {

    public Converter<MarketDescription, String> getMarketDescriptionToStringConverter() {
        return new Converter<MarketDescription, java.lang.String>() {
            public String convert(MarketDescription marketDescription) {
                return new StringBuilder().append(marketDescription.getMarketName()).toString();
            }
        };
    }

    public Converter<MarketState, String> getMarketStateToStringConverter() {
        return new Converter<MarketState, java.lang.String>() {
            public String convert(MarketState marketState) {
                return new StringBuilder().append(marketState.getStatus()).toString();
            }
        };
    }

    public Converter<RunnerDescription, String> getRunnerDescriptionToStringConverter() {
        return new Converter<RunnerDescription, java.lang.String>() {
            public String convert(RunnerDescription marketDescription) {
                return new StringBuilder().append(marketDescription.getRunnerName()).toString();
            }
        };
    }

    public Converter<RunnerState, String> getRunnerStateToStringConverter() {
        return new Converter<RunnerState, java.lang.String>() {
            public String convert(RunnerState marketState) {
                return new StringBuilder().append(marketState.getStatus()).toString();
            }
        };
    }

    public Converter<RunnerExchange, String> getRunnerExchangeToStringConverter() {
        return new Converter<RunnerExchange, java.lang.String>() {
            public String convert(RunnerExchange marketExchange) {
                return new StringBuilder().append("toBack: " + marketExchange.getAvailableToBack().size()).append(" toLay: " + marketExchange.getAvailableToLay().size()).toString();
            }
        };
    }

    public Converter<EventDescription, String> getEventDescriptionToStringConverter() {
        return new Converter<EventDescription, java.lang.String>() {
            public String convert(EventDescription marketDescription) {
                return new StringBuilder().append(marketDescription.getEventName()).toString();
            }
        };
    }

    @Override
	public void registerFormatters(FormatterRegistry registry) {
		
		registry.addConverter(getEventDescriptionToStringConverter());
		registry.addConverter(getRunnerExchangeToStringConverter());
		registry.addConverter(getRunnerStateToStringConverter());
		registry.addConverter(getRunnerDescriptionToStringConverter());
		registry.addConverter(getMarketDescriptionToStringConverter());
		registry.addConverter(getMarketStateToStringConverter());
	}

}
