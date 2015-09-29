package edu.ntust.afda.adapter;

import java.util.List;

import edu.ntust.afda.R;
import edu.ntust.afda.activity.ApkFileActivity;
import edu.ntust.afda.domain.FileChooserItem;
import edu.ntust.afda.util.APKFileHandler;
import edu.ntust.afda.util.ImageTransformer;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public class FileChooserArrayAdapter extends ArrayAdapter<FileChooserItem> {

	private Context context;
	private int id;
	private List<FileChooserItem> items;
	private int heightPixels;
	private int widthPixels;

	public FileChooserArrayAdapter(Context context, int resource,
			List<FileChooserItem> items) {
		super(context, resource, items);

		this.context = context;
		this.id = resource;
		this.items = items;
		 DisplayMetrics metrics = new DisplayMetrics();
	        WindowManager windowManager = (WindowManager) context
	                .getSystemService(Context.WINDOW_SERVICE);
	        windowManager.getDefaultDisplay().getMetrics(metrics);
	        heightPixels=metrics.heightPixels;
	        widthPixels=metrics.widthPixels;
		

	}

	@Override
	public FileChooserItem getItem(int position) {
		return super.getItem(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(id, null);
		}

		
		FileChooserItem o = items.get(position);
		TextView textViewName = (TextView) v.findViewById(R.id.textViewName);
		TextView textViewDate = (TextView) v.findViewById(R.id.textViewDate);
		TextView textViewItem = (TextView) v.findViewById(R.id.textViewItem);
		ImageView imageViewIcon = (ImageView) v	.findViewById(R.id.imageViewIcon);
		LinearLayout linearLayoutItem=(LinearLayout)v.findViewById(R.id.LinearLayoutFileItem);
//		if(linearLayoutItem!=null)
//		{
//			linearLayoutItem.getLayoutParams().width=heightPixels/10;
//			linearLayoutItem.getLayoutParams().height=heightPixels/10;
//		}
		
		
		
		if (o != null) {
			if (textViewDate != null) {
				textViewDate.setText(o.getDate());
			}
			if (textViewItem != null) {
				textViewItem.setText(o.getData());
			}
			if (textViewName != null) {
				textViewName.setText(o.getName());
			}

			if (imageViewIcon != null) {
				
//				imageViewIcon.getLayoutParams().width=heightPixels/12;
//				 imageViewIcon.getLayoutParams().height=heightPixels/12;
				imageViewIcon.setBackground(null);

				if (o.getData().contains("Folder")) {
					imageViewIcon.setImageResource(R.drawable.folder);
				} else if (o.getData().contains("Parent Directory")) {
					imageViewIcon.setImageResource(R.drawable.back);
				} else {
					String name = o.getName().toLowerCase();
					 if (name.endsWith(".apk"))
					{
//						imageViewIcon.setBackground(APKFileHandler.getApkIcon(context, o.getPath()));
//						imageViewIcon.setImageBitmap(ImageTransformer.drawableToBitmap(APKFileHandler.getApkIcon(context, o.getPath())));
						Bitmap apkBitmap=ImageTransformer.drawableToBitmap(APKFileHandler.getApkIcon(context, o.getPath()));
//						
						imageViewIcon.setImageBitmap(apkBitmap);
					}
					else
						imageViewIcon.setImageResource(R.drawable.whitepage32);

				}
			}
		}
		return v;
	}

}
