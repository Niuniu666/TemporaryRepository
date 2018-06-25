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
import android.util.AttributeSet;
import android.view.View;

public class HKDigitalClock extends View {

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
	public int mNum0 = 0;
	public int mNum1 = 0;
	public int mNum2 = 0;
	public int mNum3 = 0;
	public int mNum4 = 0;
	public int mNum5 = 0;
	public int mNum6 = 0;
	public int mNum7 = 0;
	public int mNum8 = 0;
	public int mNum9 = 0;
	public int mAM = 0;
	public int mPM = 0;
	public int mColon = 0;
	public Bitmap mBmpNum0 = null; // 数字0
	public Bitmap mBmpNum1 = null; // 数字1
	public Bitmap mBmpNum2 = null; // 数字2
	public Bitmap mBmpNum3 = null; // 数字3
	public Bitmap mBmpNum4 = null; // 数字4
	public Bitmap mBmpNum5 = null; // 数字5
	public Bitmap mBmpNum6 = null; // 数字6
	public Bitmap mBmpNum7 = null; // 数字7
	public Bitmap mBmpNum8 = null; // 数字8
	public Bitmap mBmpNum9 = null; // 数字9
	public Bitmap mBmpAM = null; // AM
	public Bitmap mBmpPM = null; // PM
	public Bitmap mBmpColon = null; // 冒号
	public boolean mIsScale = false; // 是否根据屏幕密度缩放图片
	public boolean mIgnoreSecond = false; // 忽略秒显示
	public int mModeGravity = 6; // 时间模式对齐方式（默认是右上）
	// 内部变量
	private Context mContext;
	private int mLayoutWidth = 0;
	private int mLayoutHeight = 0;
	private int mHour = -1;
	private int mMinute = -1;
	private int mSecond = -1;
	private int mModeX = 0;
	private int mModeY = 0;
	private HKTimer mTimer;
	private final int FUNC_ID_YEAR = 0; // 年
	private final int FUNC_ID_MONTH = 1; // 月份
	private final int FUNC_ID_DAY = 2; // 日
	private final int FUNC_ID_HOUR = 3; // 小时
	private final int FUNC_ID_MINUTE = 4; // 分钟
	private final int FUNC_ID_SECOND = 5; // 秒
	private final int FUNC_ID_WEEK = 6; // 星期
	private final int FUNC_ID_TIME_MODE = 7; // 时间模式
	private int[] mShowArray = new int[9]; // 00:00:00am 用9个位来表示每个位的应该显示什么图片
											// 10表示am，11表示pm，12表示冒号

	public HKDigitalClock(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Init(context);
	}

	public HKDigitalClock(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Init(context);
		// 获取属性
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.HKDigitalClock);
		mNum0 = typedArray.getResourceId(
				R.styleable.HKDigitalClock_dgclock_num0, 0);
		mNum1 = typedArray.getResourceId(
				R.styleable.HKDigitalClock_dgclock_num1, 0);
		mNum2 = typedArray.getResourceId(
				R.styleable.HKDigitalClock_dgclock_num2, 0);
		mNum3 = typedArray.getResourceId(
				R.styleable.HKDigitalClock_dgclock_num3, 0);
		mNum4 = typedArray.getResourceId(
				R.styleable.HKDigitalClock_dgclock_num4, 0);
		mNum5 = typedArray.getResourceId(
				R.styleable.HKDigitalClock_dgclock_num5, 0);
		mNum6 = typedArray.getResourceId(
				R.styleable.HKDigitalClock_dgclock_num6, 0);
		mNum7 = typedArray.getResourceId(
				R.styleable.HKDigitalClock_dgclock_num7, 0);
		mNum8 = typedArray.getResourceId(
				R.styleable.HKDigitalClock_dgclock_num8, 0);
		mNum9 = typedArray.getResourceId(
				R.styleable.HKDigitalClock_dgclock_num9, 0);
		mAM = typedArray
				.getResourceId(R.styleable.HKDigitalClock_dgclock_am, 0);
		mPM = typedArray
				.getResourceId(R.styleable.HKDigitalClock_dgclock_pm, 0);
		mColon = typedArray.getResourceId(
				R.styleable.HKDigitalClock_dgclock_colon, 0);
		boolean isScale = typedArray.getBoolean(
				R.styleable.HKDigitalClock_dgclock_pic_scale, false);
		boolean ignoreSecond = typedArray.getBoolean(
				R.styleable.HKDigitalClock_dgclock_ignore_second, false);
		int modeGravity = typedArray.getInt(
				R.styleable.HKDigitalClock_dgclock_mode_gravity, 6);
		typedArray.recycle();
		// 将属性值分配到相应变量
		mIsScale = isScale;
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = mIsScale;
		if (mNum0 != 0)
			mBmpNum0 = BitmapFactory.decodeResource(getResources(), mNum0,
					bitmapOptions);
		if (mNum1 != 0)
			mBmpNum1 = BitmapFactory.decodeResource(getResources(), mNum1,
					bitmapOptions);
		if (mNum2 != 0)
			mBmpNum2 = BitmapFactory.decodeResource(getResources(), mNum2,
					bitmapOptions);
		if (mNum3 != 0)
			mBmpNum3 = BitmapFactory.decodeResource(getResources(), mNum3,
					bitmapOptions);
		if (mNum4 != 0)
			mBmpNum4 = BitmapFactory.decodeResource(getResources(), mNum4,
					bitmapOptions);
		if (mNum5 != 0)
			mBmpNum5 = BitmapFactory.decodeResource(getResources(), mNum5,
					bitmapOptions);
		if (mNum6 != 0)
			mBmpNum6 = BitmapFactory.decodeResource(getResources(), mNum6,
					bitmapOptions);
		if (mNum7 != 0)
			mBmpNum7 = BitmapFactory.decodeResource(getResources(), mNum7,
					bitmapOptions);
		if (mNum8 != 0)
			mBmpNum8 = BitmapFactory.decodeResource(getResources(), mNum8,
					bitmapOptions);
		if (mNum9 != 0)
			mBmpNum9 = BitmapFactory.decodeResource(getResources(), mNum9,
					bitmapOptions);
		if (mAM != 0)
			mBmpAM = BitmapFactory.decodeResource(getResources(), mAM,
					bitmapOptions);
		if (mPM != 0)
			mBmpPM = BitmapFactory.decodeResource(getResources(), mPM,
					bitmapOptions);
		if (mColon != 0)
			mBmpColon = BitmapFactory.decodeResource(getResources(), mColon,
					bitmapOptions);
		mIgnoreSecond = ignoreSecond;
		mModeGravity = modeGravity;
		// 计算控件尺寸
		int layoutWidth = 0;
		int layoutHeight = 0;
		if (mBmpNum0 != null) {
			if (mIgnoreSecond)
				layoutWidth += mBmpNum0.getWidth() * 4;
			else
				layoutWidth += mBmpNum0.getWidth() * 6;
			layoutHeight = mBmpNum0.getHeight();
		}
		if (mBmpColon != null) {
			if (mIgnoreSecond)
				layoutWidth += mBmpColon.getWidth();
			else
				layoutWidth += mBmpColon.getWidth() * 2;
		}
		int timeMode = GetSystemTime(FUNC_ID_TIME_MODE);
		if (timeMode != 0) { // 12小时制
			if (mBmpAM != null && mBmpPM != null) {
				if (mModeGravity >= 0 && mModeGravity <= 2) { // 左边
					mModeX = 0;
					switch (mModeGravity) {
					case 0:
						mModeY = 0;
						break;
					case 1:
						mModeY = (layoutHeight - mBmpAM.getHeight()) / 2;
						break;
					case 2:
						mModeY = layoutHeight - mBmpAM.getHeight();
						break;
					}
				} else if (mModeGravity >= 6 && mModeGravity <= 8) { // 右边
					mModeX = layoutWidth;
					switch (mModeGravity) {
					case 6:
						mModeY = 0;
						break;
					case 7:
						mModeY = (layoutHeight - mBmpAM.getHeight()) / 2;
						break;
					case 8:
						mModeY = layoutHeight - mBmpAM.getHeight();
						break;
					}
				}
				layoutWidth += mBmpAM.getWidth();
			}
		}
		SetLayoutSize(layoutWidth, layoutHeight);
		UpdateTime();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画图片
		Draw(canvas);
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
		ReleaseBitmap(); // 释放资源 add by lyb 20160201
	}

	// 初始化控件
	private void Init(Context context) {
		mContext = context;
		mTimer = new HKTimer(context);
		mTimer.SetHKTimeUpListener(new OnHKTimeUpListener() {
			@Override
			public void OnHKTimeUp(View view) {
				// TODO Auto-generated method stub
				UpdateTime();
			}
		});
	}

	// 绘画控件
	private void Draw(Canvas canvas) {
		int x = 0;
		for (int i = 0; i < mShowArray.length; i++) {
			int value = mShowArray[i];
			if (value == -1)
				break;
			Bitmap bmp = GetBitmap(value);
			if (bmp != null && !bmp.isRecycled()) {
				if (value == 10 || value == 11)
					canvas.drawBitmap(bmp, x, mModeY, null);
				else
					canvas.drawBitmap(bmp, x, 0, null);
				x += bmp.getWidth();
			}
		}
	}

	// 更新时间
	private void UpdateTime() {
		int index = 0;
		int hour = GetSystemTime(FUNC_ID_HOUR);
		int minute = GetSystemTime(FUNC_ID_MINUTE);
		int second = GetSystemTime(FUNC_ID_SECOND);
		if (hour != mHour || minute != mMinute || second != mSecond) {
			ClearArray();
			mHour = hour;
			mMinute = minute;
			mSecond = second;
			int timeMode = GetSystemTime(FUNC_ID_TIME_MODE);
			if (mModeGravity >= 0 && mModeGravity <= 2) // 左边
			{
				if (timeMode != 0) // 12小时制
				{
					if (timeMode == 1) // am
					{
						mShowArray[index++] = 10;
					} else if (timeMode == 2) // pm
					{
						mShowArray[index++] = 11;
					}
				}
			}
			if (mHour < 10) // 时
			{
				mShowArray[index++] = 0;
				mShowArray[index++] = mHour;
			} else {
				mShowArray[index++] = (int) (mHour / 10);
				mShowArray[index++] = mHour % 10;
			}
			mShowArray[index++] = 12; // 冒号
			if (mMinute < 10) // 分
			{
				mShowArray[index++] = 0;
				mShowArray[index++] = mMinute;
			} else {
				mShowArray[index++] = (int) (mMinute / 10);
				mShowArray[index++] = mMinute % 10;
			}
			if (!mIgnoreSecond) // 需要秒显示
			{
				mShowArray[index++] = 12; // 冒号
				if (mSecond < 10) // 秒
				{
					mShowArray[index++] = 0;
					mShowArray[index++] = mSecond;
				} else {
					mShowArray[index++] = (int) (mSecond / 10);
					mShowArray[index++] = mSecond % 10;
				}
			}
			if (mModeGravity >= 6 && mModeGravity <= 8) // 右边
			{
				if (timeMode != 0) // 12小时制
				{
					if (timeMode == 1) // am
					{
						mShowArray[index++] = 10;
					} else if (timeMode == 2) // pm
					{
						mShowArray[index++] = 11;
					}
				}
			}
			invalidate();
		}
	}

	// 清空数组
	private void ClearArray() {
		for (int i = 0; i < mShowArray.length; i++) {
			mShowArray[i] = -1;
		}
	}

	private Bitmap GetBitmap(int id) {
		Bitmap result = null;
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = mIsScale;
		switch (id) {
		case 0:
			if (mBmpNum0 == null && mNum0 != 0) {
				mBmpNum0 = BitmapFactory.decodeResource(getResources(), mNum0,
						bitmapOptions);
			}
			result = mBmpNum0;
			break;
		case 1:
			if (mBmpNum1 == null && mNum1 != 0) {
				mBmpNum1 = BitmapFactory.decodeResource(getResources(), mNum1,
						bitmapOptions);
			}
			result = mBmpNum1;
			break;
		case 2:
			if (mBmpNum2 == null && mNum2 != 0) {
				mBmpNum2 = BitmapFactory.decodeResource(getResources(), mNum2,
						bitmapOptions);
			}
			result = mBmpNum2;
			break;
		case 3:
			if (mBmpNum3 == null && mNum3 != 0) {
				mBmpNum3 = BitmapFactory.decodeResource(getResources(), mNum3,
						bitmapOptions);
			}
			result = mBmpNum3;
			break;
		case 4:
			if (mBmpNum4 == null && mNum4 != 0) {
				mBmpNum4 = BitmapFactory.decodeResource(getResources(), mNum4,
						bitmapOptions);
			}
			result = mBmpNum4;
			break;
		case 5:
			if (mBmpNum5 == null && mNum5 != 0) {
				mBmpNum5 = BitmapFactory.decodeResource(getResources(), mNum5,
						bitmapOptions);
			}
			result = mBmpNum5;
			break;
		case 6:
			if (mBmpNum6 == null && mNum6 != 0) {
				mBmpNum6 = BitmapFactory.decodeResource(getResources(), mNum6,
						bitmapOptions);
			}
			result = mBmpNum6;
			break;
		case 7:
			if (mBmpNum7 == null && mNum7 != 0) {
				mBmpNum7 = BitmapFactory.decodeResource(getResources(), mNum7,
						bitmapOptions);
			}
			result = mBmpNum7;
			break;
		case 8:
			if (mBmpNum8 == null && mNum8 != 0) {
				mBmpNum8 = BitmapFactory.decodeResource(getResources(), mNum8,
						bitmapOptions);
			}
			result = mBmpNum8;
			break;
		case 9:
			if (mBmpNum9 == null && mNum9 != 0) {
				mBmpNum9 = BitmapFactory.decodeResource(getResources(), mNum9,
						bitmapOptions);
			}
			result = mBmpNum9;
			break;
		case 10:
			if (mBmpAM == null && mAM != 0) {
				mBmpAM = BitmapFactory.decodeResource(getResources(), mAM,
						bitmapOptions);
			}
			result = mBmpAM;
			break;
		case 11:
			if (mBmpPM == null && mPM != 0) {
				mBmpPM = BitmapFactory.decodeResource(getResources(), mPM,
						bitmapOptions);
			}
			result = mBmpPM;
			break;
		case 12:
			if (mBmpColon == null && mColon != 0) {
				mBmpColon = BitmapFactory.decodeResource(getResources(), mColon,
						bitmapOptions);
			}
			result = mBmpColon;
			break;
		}
		return result;
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
		if (mBmpNum0 != null && mNum0 != 0) {
			mBmpNum0.recycle();
			mBmpNum0 = null;
		}		
		if (mBmpNum1 != null && mNum1 != 0) {
			mBmpNum1.recycle();
			mBmpNum1 = null;
		}		
		if (mBmpNum2 != null && mNum2 != 0) {
			mBmpNum2.recycle();
			mBmpNum2 = null;
		}		
		if (mBmpNum3 != null && mNum3 != 0) {
			mBmpNum3.recycle();
			mBmpNum3 = null;
		}		
		if (mBmpNum4 != null && mNum4 != 0) {
			mBmpNum4.recycle();
			mBmpNum4 = null;
		}		
		if (mBmpNum5 != null && mNum5 != 0) {
			mBmpNum5.recycle();
			mBmpNum5 = null;
		}		
		if (mBmpNum6 != null && mNum6 != 0) {
			mBmpNum6.recycle();
			mBmpNum6 = null;
		}		
		if (mBmpNum7 != null && mNum7 != 0) {
			mBmpNum7.recycle();
			mBmpNum7 = null;
		}		
		if (mBmpNum8 != null && mNum8 != 0) {
			mBmpNum8.recycle();
			mBmpNum8 = null;
		}		
		if (mBmpNum9 != null && mNum9 != 0) {
			mBmpNum9.recycle();
			mBmpNum9 = null;
		}		
		if (mBmpAM != null && mAM != 0) {
			mBmpAM.recycle();
			mBmpAM = null;
		}		
		if (mBmpPM != null && mPM != 0) {
			mBmpPM.recycle();
			mBmpPM = null;
		}		
		if (mBmpColon != null && mColon != 0) {
			mBmpColon.recycle();
			mBmpColon = null;
		}
	}
}
