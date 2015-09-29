package edu.ntust.afda.adapter;


import java.util.ArrayList;

import edu.ntust.afda.R;
import edu.ntust.afda.domain.FileChooserItem;
import edu.ntust.afda.util.APKFileHandler;
import edu.ntust.afda.util.ImageTransformer;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public  class FileChooserAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<FileChooserItem> items;
	   

	public FileChooserAdapter(Context context, ArrayList<FileChooserItem> items){
    	this.items = items;
        this.context = context;
    }

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		FileHolder holder = null;
    	View v = convertView;
    	
    	 if (v == null) {
         	holder = new FileHolder();
             v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_apkfile_chooser,null);
         	holder.textViewName = (TextView) v.findViewById(R.id.textViewName);	
         	holder.textViewDate = (TextView) v.findViewById(R.id.textViewDate);	
         	holder.textViewItem = (TextView) v.findViewById(R.id.textViewItem);	
         	holder.imageViewIcon=(ImageView)v.findViewById(R.id.imageViewIcon);
         	
             v.setTag(holder);
         }else{
         	holder = (FileHolder)v.getTag();
         }
    	 FileChooserItem o = items.get(position);
    	 
    	 
    	 if (o != null) {
 			if(holder.textViewDate!=null)
 			{
 				holder.textViewDate.setText(o.getDate());
 			}
 			if(holder.textViewItem!=null)
 			{
 				holder.textViewItem.setText(o.getData());
 			}
 			if(holder.textViewItem!=null)
 			{
 				holder.textViewItem.setText(o.getName());
 			}
 			
 			if(holder.imageViewIcon!=null)
 			{
 				
 				if (o.getData().contains("folder")) {
 					holder.imageViewIcon.setImageResource(R.drawable.folder);
				} else if (o.getData().contains("parent directory")) {
					holder.imageViewIcon.setImageResource(R.drawable.back);
					
				} else {
 				String name = o.getName().toLowerCase();
					if (name.endsWith(".apk"))

						holder.imageViewIcon.setBackground(APKFileHandler
								.getApkIcon(context, o.getPath()));

					else
						holder.imageViewIcon
								.setImageResource(R.drawable.whitepage32);
				
 				 
				} 
 			}
 			
             			
 		}
     		
     	return v;	 
	}
	
	
	  public static class FileHolder {
			public TextView textViewName;
			public TextView textViewDate;
			public TextView textViewItem;
	    	public ImageView imageViewIcon;
	        
	    }

}
