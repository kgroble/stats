package com.example.stats;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class PlotView extends View {

	Paint p = new Paint();
	Context c;
	int w, h;

	public PlotView(Context context) {
		super(context);
		c = context;
		p.setColor(Color.BLACK);
		p.setTextSize(20);
		DisplayMetrics metrics = new DisplayMetrics();
		((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
		w = metrics.widthPixels;
		h = metrics.heightPixels;
	}
	

	@Override
	public void onDraw(Canvas canvas){
		Paint bkg = new Paint();

		//fill background
		bkg.setColor(Color.DKGRAY);
		canvas.drawPaint(bkg);

		//draw x-axis
		p.setStrokeWidth(4);
		canvas.drawLine(0, h/2, w, h/2, p);
		canvas.drawText("0", 10, h/2 - 5, p);

		ArrayList<Double> points = MyStats.vals;
		Collections.sort(points);

		if(points.size() > 0){
			//determine horizontal and vertical scales of the plot
			double vmax = Math.abs(points.get(0));
			if(Math.abs(points.get(points.size()-1)) > vmax)
				vmax = Math.abs(points.get(points.size()-1));
			double vunit = 1;
			if(vmax != 0)
				vunit = (h/2 - 50)/vmax;
			double hunit = 0;
			if(points.size() > 1)
				 hunit = (w-100)/(points.size()-1);


			//get statistical information
			double mean = Main.getMean(points);
			double median = Main.getMedian(points);
			double stdDev = Main.getStdDev(points);
			double range = Main.getRange(points);

			//draw line through the mean
			p.setStrokeWidth(3);
			p.setColor(Color.BLUE);
			canvas.drawLine(0, ((Double)(h/2 - mean*vunit)).floatValue(), w, ((Double)(h/2 - mean*vunit)).floatValue(), p);
			canvas.drawText("Mean: " + String.format("%.5g%n", mean), 10, 50, p);

			//draw line through the median
			p.setColor(Color.RED);
			canvas.drawLine(0, ((Double)(h/2 - median*vunit)).floatValue(), w, ((Double)(h/2 - median*vunit)).floatValue(), p);
			canvas.drawText("Median: " + String.format("%.5g%n", median), 10, 80, p);

			//draw lines for standard deviation above and below the mean
			p.setStrokeWidth(1);
			p.setColor(Color.CYAN);
			canvas.drawLine(0, ((Double)(h/2 - mean*vunit - stdDev*vunit)).floatValue(), w, ((Double)(h/2 - mean*vunit - stdDev*vunit)).floatValue(), p);
			canvas.drawLine(0, ((Double)(h/2 - mean*vunit + stdDev*vunit)).floatValue(), w, ((Double)(h/2 - mean*vunit + stdDev*vunit)).floatValue(), p);
			canvas.drawText("Std. Deviation: +/-" + String.format("%.5g%n",stdDev), 180, 50, p);	

			//print sample size		
			p.setColor(Color.WHITE);
			canvas.drawText("Sample Size: " + MyStats.vals.size() + "        Range: " + String.format("%.5g%n", range), 10, 20, p);

			//draw and label points
			p.setTextSize(16);
			p.setStrokeWidth(5);
			for(int i = 0; i < points.size(); i++){
				canvas.drawPoint(((Double)(25 + i*hunit)).floatValue(), -((Double)(points.get(i)*vunit)).floatValue() + h/2, p);
				if(MyStats.labels)
					canvas.drawText(points.get(i).toString(), ((Double)(25 + i*hunit)).floatValue() + 3, -((Double)(points.get(i)*vunit)).floatValue() + h/2, p);
			}
			
		}
	}
}