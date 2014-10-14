package com.example.stats;



import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;



public class Main extends Activity {
	
	EditText newPoint;
	TextView sd;
	CheckBox labels;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_layout);
		MyStats.vals = new ArrayList<Double>();
		newPoint = (EditText)findViewById(R.id.newPointText);
		sd = (TextView)findViewById(R.id.statsDisplay);
		labels =  (CheckBox)findViewById(R.id.labelsCB);
		labels.setChecked(true);
	}

	public void addPoint(View v){
		if(!newPoint.getText().toString().equals("")) //ignores a request to enter a data point when nothing has been entered
			MyStats.vals.add(Double.parseDouble(newPoint.getText().toString()));
		newPoint.setText("");
	}
	
	public void plotData(View v){
		
		if(labels.isChecked())
			MyStats.labels = true;
		else
			MyStats.labels = false;
		Intent i = new Intent(Main.this, Plot.class);
		startActivity(i);
	}
	
	public void editData(View v){
		Intent i = new Intent(Main.this, Editor.class);
		startActivity(i);
	}
	
	public void getStats(View v){
		sd.setText("Sample Size: " + MyStats.vals.size() + "\nRange: " + getRange(MyStats.vals) + "\nMean: " + getMean(MyStats.vals) 
				+ "\nStandard Deviation: " + getStdDev(MyStats.vals) + "\nMedian: " + getMedian(MyStats.vals));
	}
	

	public void clearData(View v){
		MyStats.vals.clear();
		sd.setText("");
	}

	public static double getStdDev(ArrayList<Double> vals) {
		double mean = getMean(vals);
		int size = vals.size();
		if(size == 0)
			return 0;
		double sum = 0;
		for(int i = 0; i < size; i++)
			sum += Math.pow(vals.get(i)-mean, 2);
		return Math.sqrt(sum/size);
		
	}
	
	public static double getRange(ArrayList<Double> vals){
		int size = vals.size();
		if(size == 0)
			return 0;
		double max, min;
		max = min = vals.get(0);
		for(int i = 1; i < size; i++){
			if(vals.get(i) > max)
				max = MyStats.vals.get(i);
			if(vals.get(i) < min)
				min = MyStats.vals.get(i);
		}
		return max - min;
	}
	
	public static double getMedian(ArrayList<Double> vals){
		int size = vals.size();
		if(size == 0)
			return 0;
		Collections.sort(vals);
		if(size%2 == 0)
			return((vals.get(size/2) + vals.get(size/2 - 1))/2);
		else
			return vals.get((size-1)/2);
	}
	
	public static double getMean(ArrayList<Double> vals){
		int size = vals.size();
		if(size == 0)
			return 0;
		double sum = 0;
		for(int i = 0; i < size; i++)
			sum += vals.get(i);
		return sum/size;
	}
	
}
