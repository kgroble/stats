package com.example.stats;



import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



public class AddEntries extends Activity {
	
	EditText newPoint;
	TextView sd;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_layout);
		MyStats.vals = new ArrayList<Double>();
		newPoint = (EditText)findViewById(R.id.newPointText);
		sd = (TextView)findViewById(R.id.statsDisplay);
	}

	public void addPoint(View v){
		if(!newPoint.getText().toString().equals("")) //ignores a request to enter a data point when nothing has been entered
			MyStats.vals.add(Double.parseDouble(newPoint.getText().toString()));
		newPoint.setText("");
	}
	
	public void getStats(View v){
		sd.setText("Sample Size: " + MyStats.vals.size() + "\nMean: " + getMean());
	}
	
	public double getMean(){
		double sum = 0;
		int size = MyStats.vals.size();
		if(size == 0)
			return 0;
		for(int i = 0; i < size; i++)
			sum += MyStats.vals.get(i);
		return sum/size;
	}
	
}
