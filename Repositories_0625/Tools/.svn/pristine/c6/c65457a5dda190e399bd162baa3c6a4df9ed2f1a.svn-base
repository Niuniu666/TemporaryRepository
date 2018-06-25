package haoke.ui.util;

import java.util.Calendar;
import haoke.lib.tools.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

public class HKClock extends View {
	
	// ------------------------------外部接口 start------------------------------
	/**
	 * 获取控件宽度
	 * 
	 * @return int
	 */
	public int GetWidth() {
		return mLayoutWidth;
	}

	/**
	 * 获取控件高度
	 * 
	 * @return int
	 */
	public int GetHeight() {
		return mLayoutHeight;
	}

	/**
	 * 设置控件在XML中的宽度和高度
	 * 
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 */
	public void SetLayoutSize(int width, int height) {
		mLayoutWidth = width;
		mLayoutHeight = height;
	}
	// ------------------------------外部接口 end------------------------------

	// 属性
	public int mClockBgId = 0;
	public int mClockHourId = 0;
	public int mClockMinuteId = 0;
	public int mClockSecondId = 0;
	public Bitmap mClockBg = null; // 时钟背景
	public Bitmap mClockHour = null; // 时针
	public Bitmap mClockMinute = null; // 分针
	public Bitmap mClockSecond = null; // 秒针
	public boolean mIsScale = false; // 是否根据屏幕密度缩放图片
	// 内部变量
	private Context mContext;
	private int mLayoutWidth = 0;
	private int mLayoutHeight = 0;
	private float mHourX = 0;
	private float mHourY = 0;
	private float mMinuteX = 0;
	private float mMinuteY = 0;
	private float mSecondX = 0;
	private float mSecondY = 0;
	private int mHourDegree = 0;
	private int mMinuteDegree = 0;
	private int mSecondDegree = 0;
	private HKTimer mTimer;
	private final int FUNC_ID_YEAR = 0; // 年
	private final int FUNC_ID_MONTH = 1; // 月份
	private final int FUNC_ID_DAY = 2; // 日
	private final int FUNC_ID_HOUR = 3; // 小时
	private final int FUNC_ID_MINUTE = 4; // 分钟
	private final int FUNC_ID_SECOND = 5; // 秒
	private final int FUNC_ID_WEEK = 6; // 星期
	private final int FUNC_ID_TIME_MODE = 7; // 时间模式

	public HKClock(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Init(context);
	}

	public HKClock(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Init(context);
		// 获取属性
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.HKClock);
		mClockBgId = typedArray.getResourceId(R.styleable.HKClock_clock_bg, 0);
		mClockHourId = typedArray.getResourceId(R.styleable.HKClock_clock_hour,
				0);
		mClockMinuteId = typedArray.getResourceId(
				R.styleable.HKClock_clock_minute, 0);
		mClockSecondId = typedArray.getResourceId(
				R.styleable.HKClock_clock_second, 0);
		boolean isScale = typedArray.getBoolean(
				R.styleable.HKClock_clock_pic_scale, false);
		typedArray.recycle();
		// 将属性值分配到相应变量
		mIsScale = isScale;
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = mIsScale;
		if (mClockBgId != 0) {
			mClockBg = BitmapFactory.decodeResource(getResources(), mClockBgId,
					bitmapOptions);
			SetLayoutSize(mClockBg.getWidth(), mClockBg.getHeight());
		}
		if (mClockHourId != 0) {
			mClockHour = BitmapFactory.decodeResource(getResources(),
					mClockHourId, bitmapOptions);
		}
		if (mClockMinuteId != 0) {
			mClockMinute = BitmapFactory.decodeResource(getResources(),
					mClockMinuteId, bitmapOptions);
		}
		if (mClockSecondId != 0) {
			mClockSecond = BitmapFactory.decodeResource(getResources(),
					mClockSecondId, bitmapOptions);
		}
		CalculateDegree();
	}

	private void InitClockBg() {
		if (mClockBg == null) {
			Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inScaled = mIsScale;
			if (mClockBgId != 0) {
				mClockBg = BitmapFactory.decodeResource(getResources(),
						mClockBgId, bitmapOptions);
			}
		}
	}

	private void InitClockHour() {
		if (mClockHour == null) {
			Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inScaled = mIsScale;
			if (mClockHourId != 0) {
				mClockHour = BitmapFactory.decodeResource(getResources(),
						mClockHourId, bitmapOptions);
			}
		}
	}

	private void InitClockMinute() {
		if (mClockMinute == null) {
			Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inScaled = mIsScale;
			if (mClockMinuteId != 0) {
				mClockMinute = BitmapFactory.decodeResource(getResources(),
						mClockMinuteId, bitmapOptions);
			}
		}
	}

	private void InitClockSecond() {
		if (mClockSecond == null) {
			Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inScaled = mIsScale;
			if (mClockSecondId != 0) {
				mClockSecond = BitmapFactory.decodeResource(getResources(),
						mClockSecondId, bitmapOptions);
			}
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画图片
		InitClockBg();
		InitClockHour();
		InitClockMinute();
		InitClockSecond();
		if (mClockBg != null && !mClockBg.isRecycled())
			canvas.drawBitmap(mClockBg, 0, 0, null);
		if (mClockHour != null && !mClockHour.isRecycled()) {
			Bitmap bmp = mClockHour;
			mHourX = (mLayoutWidth - bmp.getWidth()) / 2;
			mHourY = (mLayoutHeight - bmp.getHeight()) / 2;
			canvas.save();
			canvas.translate(mHourX, mHourY);
			canvas.rotate(mHourDegree, bmp.getWidth() / 2, bmp.getHeight() / 2);
			canvas.drawBitmap(bmp, 0, 0, null);
			canvas.restore();
		}
		if (mClockMinute != null && !mClockMinute.isRecycled()) {
			Bitmap bmp = mClockMinute;
			mMinuteX = (mLayoutWidth - bmp.getWidth()) / 2;
			mMinuteY = (mLayoutHeight - bmp.getHeight()) / 2;
			canvas.save();
			canvas.translate(mMinuteX, mMinuteY);
			canvas.rotate(mMinuteDegree, bmp.getWidth() / 2,
					bmp.getHeight() / 2);
			canvas.drawBitmap(bmp, 0, 0, null);
			canvas.restore();
		}
		if (mClockSecond != null && !mClockSecond.isRecycled()) {
			Bitmap bmp = mClockSecond;
			mSecondX = (mLayoutWidth - bmp.getWidth()) / 2;
			mSecondY = (mLayoutHeight - bmp.getHeight()) / 2;
			canvas.save();
			canvas.translate(mSecondX, mSecondY);
			canvas.rotate(mSecondDegree, bmp.getWidth() / 2,
					bmp.getHeight() / 2);
			canvas.drawBitmap(bmp, 0, 0, null);
			canvas.restore();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		this.setMeasuredDimension(mLayoutWidth, mLayoutHeight);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mTimer != null)
			mTimer.SetTimer(100);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mTimer != null)
			mTimer.StopTimer();
		ReleaseBitmap(); //释放资源 add by lyb 20160201
	}

	// 初始化控件
	private void Init(Context context) {
		mContext = context;
		mTimer = new HKTimer(context);
		mTimer.SetHKTimeUpListener(new OnHKTimeUpListener() {
			@Override
			public void OnHKTimeUp(View view) {
				// TODO Auto-generated method stub
				CalculateDegree();
			}
		});
	}

	// 根据当前时间，计算时分秒角度
	private void CalculateDegree() {
		int hour = GetSystemTime(FUNC_ID_HOUR);
		int minute = GetSystemTime(FUNC_ID_MINUTE);
		int second = GetSystemTime(FUNC_ID_SECOND);

		int hourDegree = (hour % 12) * 30 + minute / 2;
		int minuteDegree = minute * 6 + second / 10;
		int secondDegree = second * 6;

		if (hourDegree != mHourDegree || minuteDegree != mMinuteDegree
				|| secondDegree != mSecondDegree) {
			mHourDegree = (hour % 12) * 30 + minute / 2;
			mMinuteDegree = minute * 6 + second / 10;
			mSecondDegree = second * 6;
			invalidate();
		}
	}

	// 按角度旋转图片
	private Bitmap RotateBitmap(Bitmap srcBmp, int degree) {
		if (degree == 0 || srcBmp == null)
			return srcBmp;
		Matrix matrix = new Matrix();
		matrix.setRotate(degree, srcBmp.getWidth() / 2, srcBmp.getHeight() / 2);
		Bitmap dstBmp = Bitmap.createBitmap(srcBmp, 0, 0, srcBmp.getWidth(),
				srcBmp.getHeight(), matrix, true);
		return dstBmp;
	}

	/**
	 * 获取系统时间日期
	 * 
	 * @return
	 */
	private int GetSystemTime(int data) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		int week = c.get(Calendar.DAY_OF_WEEK);
		int iReturn = 0;
		switch (data) {
		case FUNC_ID_YEAR:
			iReturn = year;
			break;
		case FUNC_ID_MONTH:
			iReturn = month + 1; // 月份需要加一
			break;
		case FUNC_ID_DAY:
			iReturn = day;
			break;
		case FUNC_ID_HOUR:
			if (is24HourFormat())
				iReturn = hour;
			else {
				if (hour > 12) {
					hour = hour - 12;
				} else if (hour == 0) {
					hour = 12;
				}
				iReturn = hour;
			}
			break;
		case FUNC_ID_MINUTE:
			iReturn = minute;
			break;
		case FUNC_ID_SECOND:
			iReturn = second;
			break;
		case FUNC_ID_WEEK:
			iReturn = week - 1;// 星期需要减一，默认第一天为周日
			break;
		case FUNC_ID_TIME_MODE:
			if (is24HourFormat()) {
				iReturn = 0;
			} else {
				if (hour >= 12) // pm
					iReturn = 2;
				else
					// am
					iReturn = 1;
			}
			break;
		}
		return iReturn;
	}

	/**
	 * 判断系统时间制式是否为24小时制
	 * 
	 * @return
	 */
	private boolean is24HourFormat() {
		ContentResolver cv = mContext.getContentResolver();
		String strTimeFormat = android.provider.Settings.System.getString(cv,
				android.provider.Settings.System.TIME_12_24);
		if (strTimeFormat != null && strTimeFormat.equals("24")) {
			return true;
		}
		return false;
	}

	// 释放图片资源
	public void ReleaseBitmap() {
		if (mClockBg != null && mClockBgId != 0) {
			mClockBg.recycle();
			mClockBg = null;
		}		
		if (mClockHour != null && mClockHourId != 0) {
			mClockHour.recycle();
			mClockHour = null;
		}		
		if (mClockMinute != null && mClockMinuteId != 0) {
			mClockMinute.recycle();
			mClockMinute = null;
		}		
		if (mClockSecond != null && mClockSecondId != 0) {
			mClockSecond.recycle();
			mClockSecond = null;
		}
	}
}
