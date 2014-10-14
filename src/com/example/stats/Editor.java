package com.example.stats;

import java.util.Collections;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

public class Editor extends Activity{

	ListView lv;
	ArrayAdapter<Double> valAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_layout);
		lv = (ListView)findViewById(R.id.pointView);
		Collections.sort(MyStats.vals);
		valAdapter = new ArrayAdapter<Double>(this, android.R.layout.simple_list_item_checked, MyStats.vals);
		lv.setAdapter(valAdapter);
	}	

	
	public void deletePoints(View v){
		SparseBooleanArray checked = lv.getCheckedItemPositions();
		for(int i = checked.size() - 1; i >= 0; i--)	
			MyStats.vals.remove(checked.keyAt(i));		
		valAdapter = new ArrayAdapter<Double>(this, android.R.layout.simple_list_item_checked, MyStats.vals);
		lv.setAdapter(valAdapter);
		//valAdapter.notifyDataSetChanged();
	}
}