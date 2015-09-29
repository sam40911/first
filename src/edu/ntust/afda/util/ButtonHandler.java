package edu.ntust.afda.util;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.Button;

public class ButtonHandler {
	
	static Handler handler;
	static Runnable runnable;
	
	public static void set_button_Drawable_center(final Context context,
			final Button button, final int imageID, final int spacing) {
		final Handler handler = new Handler();
		runnable = new Runnable() {
			@Override
			public void run() {
				if (button.getMeasuredWidth() == 0) {
					handler.postDelayed(runnable, 0);
				} else {
					Drawable drawable = context.getResources().getDrawable(
							imageID);
					int width = button.getMeasuredWidth();
					int height = button.getMeasuredHeight();

					Rect bounds = new Rect();
					Paint textPaint = button.getPaint();
					textPaint.getTextBounds(button.getText().toString(), 0,
							button.getText().length(), bounds);
					int txt_height = bounds.height();
					int txt_width = bounds.width();

					int img_width = drawable.getIntrinsicWidth();
					int img_height = drawable.getIntrinsicHeight();
					int content_height = txt_height + img_height + spacing;
					int content_width = txt_width + img_width + spacing;

					int padding_w = 0;
					if (width >= content_width)
						padding_w = width / 2 - content_width / 2;
					int padding_h = height / 2 - content_height / 2;

					button.setCompoundDrawablesWithIntrinsicBounds(drawable,
							null, null, null);
					button.setPadding(padding_w, button.getPaddingTop(), 0, button.getPaddingBottom());
					button.setCompoundDrawablePadding(-padding_w);
				}
			}
		};
		handler.postDelayed(runnable, 0);
	}

}
