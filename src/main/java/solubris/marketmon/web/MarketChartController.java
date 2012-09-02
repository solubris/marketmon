package solubris.marketmon.web;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import solubris.marketmon.domain.Market;
import solubris.marketmon.domain.MarketStatusTime;
import solubris.marketmon.domain.PriceSize;
import solubris.marketmon.domain.PriceSizeForBack;
import solubris.marketmon.domain.PriceSizeForLay;
import solubris.marketmon.domain.Runner;

@RequestMapping("/marketchart/**")
@Controller
public class MarketChartController {
	private static final int NUM_LABELS = 15;
	private final Logger logger = LoggerFactory.getLogger(MarketChartController.class);

    @RequestMapping("/demo-chart.png")
    public void renderChart(String variation, OutputStream stream) throws Exception {
      boolean rotate = "rotate".equals(variation); // add ?variation=rotate to the URL to rotate the chart
      //Market market=Market.findMarketEntries(0, 1).get(0);
      XYDataset data = createDataset(); //createPriceDataset(market.getRunners().get(0));
      JFreeChart chart = markerDemo1(data);
      ChartUtilities.writeChartAsPNG(stream, chart, 750, 400);
    }
    
    /**
     * TODO runner can exist in multiple markets, so need marketId as well
     * 
     * @param selectionId
     * @param width
     * @param height
     * @param stream
     * @throws IOException
     */
    @RequestMapping(value = "/{marketId}/{selectionId}.png", produces = "image/png")
    public void showChart(@PathVariable Long marketId, @PathVariable Long selectionId, @RequestParam() Integer width, @RequestParam() Integer height, @RequestParam(defaultValue="true") boolean showPrice, @RequestParam(defaultValue="true") boolean showSize, @RequestParam(required=false) @DateTimeFormat(pattern = "yyMMddHHmmss") Date start, @RequestParam(required=false) @DateTimeFormat(pattern = "yyMMddHHmmss") Date finish, OutputStream stream) throws IOException {
        boolean rotate = false;//"rotate".equals(variation); // add ?variation=rotate to the URL to rotate the chart
        Runner runner=Runner.findRunner(selectionId);
        Market market=Market.findMarket(marketId);
        PriceSizeDataSet priceSizeDataset = createPriceSizeDataSet(market, runner, showPrice, showSize, start, finish);
        JFreeChart chart = createDifferenceChart(priceSizeDataset, market.getEvent().getEvent().getEventName() + " > " + market.getDescription().getMarketName() + " > " + runner.getDescription().getRunnerName(), market.getStatusTimes());
        ChartUtilities.writeChartAsPNG(stream, chart, width, height);
    }

    private JFreeChart createDifferenceChart(PriceSizeDataSet priceSizeDataset, String title, List<MarketStatusTime> list)
    {
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title, "Time", "Price", priceSizeDataset.priceDataSet, true, true, false);
        jfreechart.setBackgroundPaint(Color.white);
        XYPlot xyplot = (XYPlot)jfreechart.getPlot();
        XYDifferenceRenderer xydifferencerenderer = new XYDifferenceRenderer(Color.green, Color.red, false);
        xydifferencerenderer.setRoundXCoordinates(true);
        xyplot.setDomainCrosshairLockedOnData(true);
        xyplot.setRangeCrosshairLockedOnData(true);
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setRangeCrosshairVisible(true);
        xyplot.setRenderer(xydifferencerenderer);
        xyplot.setBackgroundPaint(Color.lightGray);
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);
        xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
        DateAxis dateaxis = new DateAxis("Time");
        dateaxis.setLowerMargin(0.0D);
        dateaxis.setUpperMargin(0.0D);
        xyplot.setDomainAxis(dateaxis);
        xyplot.setForegroundAlpha(0.5F);
        double count=priceSizeDataset.getMaxPrice();
        double step=(priceSizeDataset.getMaxPrice() - priceSizeDataset.getMinPrice())/NUM_LABELS;
        for(MarketStatusTime marketStatusTime : list) {
//        	inplayTime=new Date(inplayTime.getTime()-100000);
//        	double d = new Second(inplayTime).getFirstMillisecond();
//        	double d = System.currentTimeMillis()- 100000;
	        double d = marketStatusTime.getCreatedAt().getTime();
            logger.warn("using inplay time {} {}", list, d);
	        ValueMarker valuemarker2 = new ValueMarker(d);
//	        RectangleInsets offset=new RectangleInsets();
//	        offset.extendHeight(2);
//			valuemarker2.setLabelOffset(offset);
//	        valuemarker2.setLabelOffsetType(LengthAdjustmentType.EXPAND);
	        valuemarker2.setPaint(marketStatusTime.getLabelColour());
//	        valuemarker2.setLabel("" + priceSizeDataset.getMaxPrice());
//	        int numItems = 4;
//			if((count%numItems)==0) {
//		        valuemarker2.setLabelAnchor(RectangleAnchor.TOP_LEFT);
//		        valuemarker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
//	        } else if((count%numItems)==1) {
//		        valuemarker2.setLabelAnchor(RectangleAnchor.LEFT);
//		        valuemarker2.setLabelTextAnchor(TextAnchor.CENTER_RIGHT);
//	        } else if((count%numItems)==2) {
//		        valuemarker2.setLabelAnchor(RectangleAnchor.LEFT);
//		        valuemarker2.setLabelTextAnchor(TextAnchor.BASELINE_RIGHT);
//	        } else {
//		        valuemarker2.setLabelAnchor(RectangleAnchor.BOTTOM_LEFT);
//		        valuemarker2.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
//	        }
	        xyplot.addDomainMarker(valuemarker2);

	        XYTextAnnotation xytextannotation = null;
	        Font font = new Font("SansSerif", 0, 9);
	        xytextannotation = new XYTextAnnotation(marketStatusTime.getLabelText(), d, count);
	        xytextannotation.setFont(font);
	        xytextannotation.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
	        xyplot.addAnnotation(xytextannotation);

	        count-=step;
        }

        NumberAxis numberaxis1 = new NumberAxis("Size");
        numberaxis1.setUpperMargin(3.0D);
//        numberaxis1.setLowerMargin(0.5D);
        xyplot.setRangeAxis(1, numberaxis1);
        xyplot.setDataset(1, priceSizeDataset.sizeDataSet);
        xyplot.mapDatasetToRangeAxis(1, 1);
        XYBarRenderer.setDefaultShadowsVisible(false);
        XYBarRenderer xybarrenderer = new XYBarRenderer(); //(0.20000000000000001D);
//        xybarrenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator("{0}: ({1}, {2})", new SimpleDateFormat("d-MMM-yyyy"), new DecimalFormat("0,000.00")));
        xyplot.setRenderer(1, xybarrenderer);

        return jfreechart;
    }

    private JFreeChart markerDemo1(XYDataset xydataset)
    {
        JFreeChart jfreechart = ChartFactory.createScatterPlot("Marker Demo 1", "X", "Y", xydataset, PlotOrientation.VERTICAL, true, true, false);
        LegendTitle legendtitle = (LegendTitle)jfreechart.getSubtitle(0);
        legendtitle.setPosition(RectangleEdge.RIGHT);
        XYPlot xyplot = (XYPlot)jfreechart.getPlot();
        xyplot.getRenderer().setBaseToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
        DateAxis dateaxis = new DateAxis("Time");
        dateaxis.setUpperMargin(0.5D);
        xyplot.setDomainAxis(dateaxis);
        ValueAxis valueaxis = xyplot.getRangeAxis();
        valueaxis.setUpperMargin(0.29999999999999999D);
        valueaxis.setLowerMargin(0.5D);
        ValueMarker valuemarker = new ValueMarker(200D);
        valuemarker.setLabelOffsetType(LengthAdjustmentType.EXPAND);
        valuemarker.setPaint(Color.green);
        valuemarker.setLabel("Bid Start Price");
        valuemarker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
        valuemarker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
        xyplot.addRangeMarker(valuemarker);
        ValueMarker valuemarker1 = new ValueMarker(175D);
        valuemarker1.setLabelOffsetType(LengthAdjustmentType.EXPAND);
        valuemarker1.setPaint(Color.red);
        valuemarker1.setLabel("Target Price");
        valuemarker1.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
        valuemarker1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
        xyplot.addRangeMarker(valuemarker1);
        Hour hour = new Hour(2, new Day(22, 5, 2003));
        double d = hour.getFirstMillisecond();
        logger.warn("d={}", d);
        ValueMarker valuemarker2 = new ValueMarker(d);
        valuemarker2.setPaint(Color.orange);
        valuemarker2.setLabel("Original Close (02:00)");
        valuemarker2.setLabelAnchor(RectangleAnchor.TOP_LEFT);
        valuemarker2.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
        xyplot.addDomainMarker(valuemarker2);
        Minute minute = new Minute(15, hour);
        d = minute.getFirstMillisecond();
        ValueMarker valuemarker3 = new ValueMarker(d);
        valuemarker3.setPaint(Color.red);
        valuemarker3.setLabel("Close Date (02:15)");
        valuemarker3.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
        valuemarker3.setLabelTextAnchor(TextAnchor.TOP_LEFT);
        xyplot.addDomainMarker(valuemarker3);
        Hour hour1 = new Hour(2, new Day(22, 5, 2003));
        Minute minute1 = new Minute(10, hour1);
        d = minute1.getFirstMillisecond();
//        CircleDrawer circledrawer = new CircleDrawer(Color.red, new BasicStroke(1.0F), null);
//        XYDrawableAnnotation xydrawableannotation = new XYDrawableAnnotation(d, 163D, 11D, 11D, circledrawer);
//        xyplot.addAnnotation(xydrawableannotation);
        XYPointerAnnotation xypointerannotation = new XYPointerAnnotation("Best Bid", d, 163D, 2.3561944901923448D);
        xypointerannotation.setBaseRadius(35D);
        xypointerannotation.setTipRadius(10D);
        xypointerannotation.setFont(new Font("SansSerif", 0, 9));
        xypointerannotation.setPaint(Color.blue);
        xypointerannotation.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
        xyplot.addAnnotation(xypointerannotation);
        return jfreechart;
    }

//    private JFreeChart generateChart(boolean rotate){
//        Market market=Market.findMarket(104802827L);
//
//        // DefaultCategoryDataset data = getDataset(market);
//        XYDataset data = createPriceDataset(market, market.getRunners().get(0));
//        return ChartFactory.createTimeSeriesChart(
//        	market.getMarketId(), // title
//			"time",  // x-axis label
//			"price",  // y-axis label
//			data,
//			//          rotate ? PlotOrientation.HORIZONTAL : PlotOrientation.VERTICAL,
//			true,      // legend displayed
//			true,      // tooltips displayed
//			false
//        );   // no URLs*/
//    }

    private XYDataset createDataset()
    {
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
        timeseriescollection.addSeries(createSupplier1Bids());
        timeseriescollection.addSeries(createSupplier2Bids());
        return timeseriescollection;
    }

    private TimeSeries createSupplier1Bids()
    {
        Hour hour = new Hour(1, new Day(22, 5, 2003));
        TimeSeries timeseries = new TimeSeries("Supplier 1");
        timeseries.add(new Minute(13, hour), 200D);
        timeseries.add(new Minute(14, hour), 195D);
        timeseries.add(new Minute(45, hour), 190D);
        timeseries.add(new Minute(46, hour), 188D);
        timeseries.add(new Minute(47, hour), 185D);
        timeseries.add(new Minute(52, hour), 180D);
        return timeseries;
    }

    private TimeSeries createSupplier2Bids()
    {
        Hour hour = new Hour(1, new Day(22, 5, 2003));
        Hour hour1 = (Hour)hour.next();
        TimeSeries timeseries = new TimeSeries("Supplier 2");
        timeseries.add(new Minute(25, hour), 185D);
        timeseries.add(new Minute(0, hour1), 175D);
        timeseries.add(new Minute(5, hour1), 170D);
        timeseries.add(new Minute(6, hour1), 168D);
        timeseries.add(new Minute(9, hour1), 165D);
        timeseries.add(new Minute(10, hour1), 163D);
        return timeseries;
    }
    
    class PriceSizeDataSet {

		XYDataset priceDataSet;
    	IntervalXYDataset sizeDataSet;
    	
    	Double minPrice;
    	Double maxPrice;

    	public PriceSizeDataSet(XYDataset priceDataSet,
				IntervalXYDataset sizeDataSet) {
			super();
			this.priceDataSet = priceDataSet;
			this.sizeDataSet = sizeDataSet;
			
			for(int i=0; i<priceDataSet.getSeriesCount(); i++) {
				for(int j=0; j<priceDataSet.getItemCount(i); j++) {
					double price=priceDataSet.getYValue(i, j);
					logger.trace("X={} Y={} price={}", new Object[]{i, j, price});
					if(minPrice==null || price<=minPrice) {
						minPrice=price;
					}
					if(maxPrice==null || price>=maxPrice) {
						maxPrice=price;
					}
				}
			}

			logger.debug("minPrice={} maxPrice={}", minPrice, maxPrice);
		}
		public XYDataset getPriceDataSet() {
			return priceDataSet;
		}

		public IntervalXYDataset getSizeDataSet() {
			return sizeDataSet;
		}

		public Double getMinPrice() {
			return minPrice;
		}
		public Double getMaxPrice() {
			return maxPrice;
		}
    }

    private PriceSizeDataSet createPriceSizeDataSet(Market market, Runner runner, boolean showPrice, boolean showSize, Date start, Date finish)
    {
    	List<? extends PriceSize> priceSizeForBack = PriceSizeForBack.findPriceSizesByMarketAndRunner(market, runner, start, finish);
    	List<? extends PriceSize> priceSizeForLay = PriceSizeForLay.findPriceSizesByMarketAndRunner(market, runner, start, finish);
    	
        TimeSeries timeseries;
        TimeSeriesCollection priceDataSet = new TimeSeriesCollection();
        if(showPrice) {
	        timeseries = createPriceTimeSeries("Back", priceSizeForBack);
	        priceDataSet.addSeries(timeseries);
	        timeseries = createPriceTimeSeries("Lay", priceSizeForLay);
	        priceDataSet.addSeries(timeseries);
        }

        TimeSeriesCollection sizeDataSet = new TimeSeriesCollection();
        if(showSize) {
	        timeseries = createPriceTimeSeries("Back", priceSizeForBack);
	        sizeDataSet.addSeries(timeseries);
	        timeseries = createPriceTimeSeries("Lay", priceSizeForLay);
	        sizeDataSet.addSeries(timeseries);
        }
        
        return new PriceSizeDataSet(priceDataSet, sizeDataSet);
    }

    private XYDataset createPriceDataset(Market market, Runner runner, Date start, Date finish)
    {
        TimeSeriesCollection timeseriesCollection = new TimeSeriesCollection();

        TimeSeries timeseries;
        timeseries = createPriceTimeSeries("Back", PriceSizeForBack.findPriceSizesByMarketAndRunner(market, runner, start, finish));
        timeseriesCollection.addSeries(timeseries);
        timeseries = createPriceTimeSeries("Lay", PriceSizeForLay.findPriceSizesByMarketAndRunner(market, runner, start, finish));
        timeseriesCollection.addSeries(timeseries);
        
        return timeseriesCollection;
    }

    private IntervalXYDataset createVolumeDataset(Market market, Runner runner, Date start, Date finish)
    {
        TimeSeriesCollection timeseriesCollection = new TimeSeriesCollection();

        TimeSeries timeseries;
        timeseries = createSizeTimeSeries("Back", PriceSizeForBack.findPriceSizesByMarketAndRunner(market, runner, start, finish));
        timeseriesCollection.addSeries(timeseries);
        timeseries = createSizeTimeSeries("Lay", PriceSizeForLay.findPriceSizesByMarketAndRunner(market, runner, start, finish));
        timeseriesCollection.addSeries(timeseries);
        
        return timeseriesCollection;
    }

	private TimeSeries createPriceTimeSeries(String title, List<? extends PriceSize> priceSizeList) {
		TimeSeries timeseries = new TimeSeries(title);
	
		for (PriceSize priceSize : priceSizeList){
			logger.error("adding price {} {}", priceSize.getCreatedAt(), priceSize.getPrice());
			timeseries.addOrUpdate(new Second(priceSize.getCreatedAt()), priceSize.getPrice());
		}
		return timeseries;
	}

	private TimeSeries createSizeTimeSeries(String title, List<? extends PriceSize> priceSizeList) {
		TimeSeries timeseries = new TimeSeries(title);
	
		for (PriceSize priceSize : priceSizeList){
			logger.error("adding size {} {}", priceSize.getCreatedAt(), priceSize.getSize());
			timeseries.addOrUpdate(new Second(priceSize.getCreatedAt()), priceSize.getSize());
		}
		return timeseries;
	}
/*
    private DefaultCategoryDataset getDataset(Market market){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for(Runner runner : market.getRunners()) {
	        List<? extends PriceSize> priceSizeList = runner.getExchange().getAvailableToBack();
	        for (PriceSize priceSize : priceSizeList){
	        	logger.error("add data set {} {}", priceSize.getCreatedAt(), priceSize.getPrice());
	            Number count = (Number) priceSize.getPrice();
	//            String category = (String) "price";
	//            String day = dateFormatter.format(priceSize.getCreatedAt());
	            dataset.addValue(count, runner.getDescription().getRunnerName(), priceSize.getCreatedAt());
	        }
        }
        return dataset;
    }
    */
}
