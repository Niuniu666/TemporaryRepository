package haoke.ui.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 防止多点触摸时报Java.lang.IllegalArgumentException: pointerIndex out of range错误
 * @author Administrator
 */
public class HKViewPager extends ViewPager {

	public HKViewPager(Context context) {
		super(context);
	}

	public HKViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		try {
			return super.onInterceptTouchEvent(arg0);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		try {
			return super.onTouchEvent(arg0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
