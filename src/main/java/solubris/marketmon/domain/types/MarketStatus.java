package solubris.marketmon.domain.types;

import java.awt.Color;
import java.awt.Paint;


/**
 * NOT_FOUND is not a betfair status, internal only
 * 
 * @author walterst
 *
 */
public enum MarketStatus {

    INACTIVE, OPEN, SUSPENDED, CLOSED, NOT_FOUND;

	public Paint getLabelColour() {
		return Color.blue;
	}
}
