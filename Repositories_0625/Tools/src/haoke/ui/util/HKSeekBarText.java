package haoke.ui.util;

import haoke.lib.tools.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.BitmapFactory.Options;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HKSeekBarText extends LinearLayout {
	
	// ------------------------------外部接口 start------------------------------
	/**
	 * 设置控件的触摸监听函数
	 * 
	 * @param OnHKTouchListener
	 */
	public void SetHKTouchListener(OnHKTouchListener listener) {
		mTouchedListener = listener;
	}

	/**
	 * 设置控件的值变化监听函数
	 * 
	 * @param OnHKChangedListener
	 */
	public void SetHKChangedListener(OnHKChangedListener listener) {
		mChangedListener = listener;
	}

	/**
	 * 设置最大值
	 * 
	 * @param num
	 */
	public void SetMaxNum(int num) {
		mSeekBar.SetMaxNum(num);
		ShowText();
	}

	/**
	 * 获取最大值
	 * 
	 * @return int
	 */
	public int GetMaxNum() {
		return mSeekBar.GetMaxNum();
	}

	/**
	 * 设置最小值
	 * 
	 * @param num
	 */
	public void SetMinNum(int num) {
		mSeekBar.SetMinNum(num);
		ShowText();
	}

	/**
	 * 获取最小值
	 * 
	 * @return int
	 */
	public int GetMinNum() {
		return mSeekBar.GetMinNum();
	}

	/**
	 * 设置当前值
	 * 
	 * @param num
	 * @return int 当前值
	 */
	public int SetCurNum(int num) {
		int curNum = mSeekBar.SetCurNum(num);
		ShowText();
		return curNum;
	}

	/**
	 * 获取当前值
	 * 
	 * @return int
	 */
	public int GetCurNum() {
		return mSeekBar.GetCurNum();
	}

	/**
	 * 设置可滑动的距离
	 * 
	 * @param len
	 */
	public void SetSlideDistance(int len) {
		mSeekBar.SetSlideDistance(len);
	}

	/**
	 * 获取可滑动的距离
	 * 
	 * @return int
	 */
	public int GetSlideDistance() {
		return mSeekBar.GetSlideDistance();
	}

	/**
	 * 设置控件有效性
	 * 
	 * @param enable
	 */
	public void SetEnable(boolean enable) {
		mSeekBar.SetEnable(enable);
	}

	/**
	 * 获取控件有效性
	 * 
	 * @return boolean
	 */
	public boolean GetEnable() {
		return mSeekBar.GetEnable();
	}
	// ------------------------------外部接口 end------------------------------

	// 属性（这里只显示文本的属性，其他的属性请查看"HKSeekBar"类）
	public float mTextSize = 20; // 文本大小
	public int mTextColor = Color.BLACK; // 文本颜色
	public int mTextWidth = -1; // 文本宽度
	public int mTextHeight = -1; // 文本高度
	public String mUnit = ""; // 单位
	public boolean mLShow = true; // 左文本显示
	public boolean mRShow = true; // 右文本显示
	public boolean mLRChange = false; // 左右文本交换
	public int mTextFormat = 0; // 文本格式：0数字；1时间
	// 内部变量
	private Context mContext;
	private HKSeekBar mSeekBar;
	private TextView mLTextView;
	private TextView mRTextView;
	private FrameLayout mLTextLayout;
	private FrameLayout mRTextLayout;
	private final int DEFAULT_LEN = 50;
	private OnHKTouchListener mTouchedListener = null;
	private OnHKChangedListener mChangedListener = null;

	public HKSeekBarText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Init(context);
	}

	public HKSeekBarText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Init(context);
		// 获取属性
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.HKSeekBar);
		int maxNum = typedArray.getInt(R.styleable.HKSeekBar_skb_max_num, 0);
		int minNum = typedArray.getInt(R.styleable.HKSeekBar_skb_min_num, 0);
		int step = typedArray.getInt(R.styleable.HKSeekBar_skb_step, 1);
		String unit = typedArray.getString(R.styleable.HKSeekBar_skb_unit);
		float slideDistance = typedArray.getDimension(
				R.styleable.HKSeekBar_skb_slide_distance, 0);
		mSeekBar.mBgUpId = typedArray.getResourceId(R.styleable.HKSeekBar_skb_bg_up,
				0);
		mSeekBar.mBgDownId = typedArray.getResourceId(
				R.styleable.HKSeekBar_skb_bg_down, 0);
		mSeekBar.mBgDisableId = typedArray.getResourceId(
				R.styleable.HKSeekBar_skb_bg_disable, 0);
		mSeekBar.mColorUpId = typedArray.getResourceId(
				R.styleable.HKSeekBar_skb_color_up, 0);
		mSeekBar.mColorDownId = typedArray.getResourceId(
				R.styleable.HKSeekBar_skb_color_down, 0);
		mSeekBar.mColorDisableId = typedArray.getResourceId(
				R.styleable.HKSeekBar_skb_color_disable, 0);
		mSeekBar.mBlockUpId = typedArray.getResourceId(
				R.styleable.HKSeekBar_skb_block_up, 0);
		mSeekBar.mBlockDownId = typedArray.getResourceId(
				R.styleable.HKSeekBar_skb_block_down, 0);
		mSeekBar.mBlockDisableId = typedArray.getResourceId(
				R.styleable.HKSeekBar_skb_block_disable, 0);
		boolean isScale = typedArray.getBoolean(
				R.styleable.HKSeekBar_skb_pic_scale, false);
		float colorX = typedArray.getDimension(
				R.styleable.HKSeekBar_skb_color_x, 0);
		float colorY = typedArray.getDimension(
				R.styleable.HKSeekBar_skb_color_y, 0);
		float blockX = typedArray.getDimension(
				R.styleable.HKSeekBar_skb_block_x, 0);
		float blockY = typedArray.getDimension(
				R.styleable.HKSeekBar_skb_block_y, 0);
		float hotWidth = typedArray.getDimension(
				R.styleable.HKSeekBar_skb_hot_width, 0);
		float hotHeight = typedArray.getDimension(
				R.styleable.HKSeekBar_skb_hot_height, 0);
		int direction = typedArray.getInt(R.styleable.HKSeekBar_skb_direction,
				0);
		boolean releaseLocation = typedArray.getBoolean(
				R.styleable.HKSeekBar_skb_release_location, false);
		float textSize = typedArray.getDimension(
				R.styleable.HKSeekBar_skb_text_size, 20);
		int textColor = typedArray.getColor(
				R.styleable.HKSeekBar_skb_text_color, Color.BLACK);
		float textWidth = typedArray.getDimension(
				R.styleable.HKSeekBar_skb_text_width, -1);
		float textHeight = typedArray.getDimension(
				R.styleable.HKSeekBar_skb_text_height, -1);
		boolean lShow = typedArray.getBoolean(
				R.styleable.HKSeekBar_skb_text_lshow, true);
		boolean rShow = typedArray.getBoolean(
				R.styleable.HKSeekBar_skb_text_rshow, true);
		boolean lrChange = typedArray.getBoolean(
				R.styleable.HKSeekBar_skb_text_lrchange, false);
		int textFormat = typedArray.getInt(
				R.styleable.HKSeekBar_skb_text_format, 0);
		typedArray.recycle();
		// 将属性值分配到相应变量
		mSeekBar.mIsScale = isScale;
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = isScale;
		mSeekBar.mMaxNum = maxNum;
		mSeekBar.mMinNum = minNum;
		mSeekBar.mStep = step;
		mUnit = unit == null ? "" : unit;
		if (mSeekBar.mBgUpId != 0) {
			// 松开图片
			mSeekBar.mBgUp = BitmapFactory.decodeResource(getResources(),
					mSeekBar.mBgUpId, bitmapOptions);
		}
		if (mSeekBar.mColorUpId != 0) {
			// 松开图片
			mSeekBar.mColorUp = BitmapFactory.decodeResource(getResources(),
					mSeekBar.mColorUpId, bitmapOptions);
		}
		if (mSeekBar.mBlockUpId != 0) {
			// 松开图片
			mSeekBar.mBlockUp = BitmapFactory.decodeResource(getResources(),
					mSeekBar.mBlockUpId, bitmapOptions);
		}
		mSeekBar.mColorX = colorX;
		mSeekBar.mColorY = colorY;
		mSeekBar.mStartX = blockX;
		mSeekBar.mStartY = blockY;
		mSeekBar.mHotWidth = (int) hotWidth;
		mSeekBar.mHotHeight = (int) hotHeight;
		mSeekBar.mDirection = direction;
		mSeekBar.mReleaseLocation = releaseLocation;
		mSeekBar.mSlideDistance = (int) slideDistance;
		if (mSeekBar.mSlideDistance == 0) { // 移动距离没有设置，根据图片计算		
			if (mSeekBar.mBgUp != null && mSeekBar.mBlockUp != null) {
				if (mSeekBar.mDirection == 0) // 水平方向
					mSeekBar.mSlideDistance = mSeekBar.mBgUp.getWidth()
							- mSeekBar.mBlockUp.getWidth();
				else
					mSeekBar.mSlideDistance = mSeekBar.mBgUp.getHeight()
							- mSeekBar.mBlockUp.getHeight();
			} else if (mSeekBar.mColorUp != null && mSeekBar.mBlockUp == null) {
				if (mSeekBar.mDirection == 0) // 水平方向
					mSeekBar.mSlideDistance = mSeekBar.mColorUp.getWidth();
				else
					mSeekBar.mSlideDistance = mSeekBar.mColorUp.getHeight();
			}
		}
		mTextSize = new Util_Global().getFontSizeDp(context, textSize); // 文本大小
		mTextColor = textColor; // 文本颜色
		mTextWidth = (int) textWidth; // 文本宽度
		mTextHeight = (int) textHeight; // 文本高度
		mLShow = lShow; // 左文本显示
		mRShow = rShow; // 右文本显示
		mLRChange = lrChange; // 左右文本交换
		mTextFormat = textFormat;
		// 计算控件尺寸
		int layoutWidth = 0;
		int layoutHeight = 0;
		this.setOrientation(direction);
		mSeekBar.InitProperty();
		// need to sync lyb 20160114
		if (mLShow || mRShow) {
			if (direction == 0) { // 水平方向			
				if (mTextWidth == -1) {
					mTextWidth = DEFAULT_LEN;
				}
				if (mTextHeight == -1) {
					mTextHeight = getFontHeight(mTextSize);
				}
				layoutHeight = mSeekBar.GetHeight() > mTextHeight ? mSeekBar
						.GetHeight() : mTextHeight;
				mLTextLayout.setLayoutParams(new LayoutParams(mTextWidth,
						layoutHeight));
				mRTextLayout.setLayoutParams(new LayoutParams(mTextWidth,
						layoutHeight));
				mSeekBar.setY((layoutHeight - mSeekBar.GetHeight()) / 2);
			} else { // 垂直方向			
				if (mTextWidth == -1) {
					mTextWidth = DEFAULT_LEN;
				}
				if (mTextHeight == -1) {
					mTextHeight = LayoutParams.WRAP_CONTENT; // getFontHeight(mTextSize);
				}
				layoutWidth = mSeekBar.GetWidth() > mTextWidth ? mSeekBar
						.GetWidth() : mTextWidth;
				mLTextLayout.setLayoutParams(new LayoutParams(layoutWidth,
						mTextHeight));
				mRTextLayout.setLayoutParams(new LayoutParams(layoutWidth,
						mTextHeight));
				mSeekBar.setX((layoutWidth - mSeekBar.GetWidth()) / 2);
			}
			mLTextView.setTextSize(mTextSize);
			mLTextView.setTextColor(mTextColor);
			mLTextView.setGravity(Gravity.CENTER);
			mRTextView.setTextSize(mTextSize);
			mRTextView.setTextColor(mTextColor);
			mRTextView.setGravity(Gravity.CENTER);
			mLTextLayout.addView(mLTextView);
			mRTextLayout.addView(mRTextView);

			this.addView(mLTextLayout);
			this.addView(mSeekBar);
			this.addView(mRTextLayout);
			if (mLShow == false)
				mLTextLayout.setVisibility(View.GONE);
			if (mRShow == false)
				mRTextLayout.setVisibility(View.GONE);
			ShowText();
		} else
			this.addView(mSeekBar);
	}

	// 初始化控件
	private void Init(Context context) {
		mContext = context;
		mSeekBar = new HKSeekBar(context);
		mLTextView = new TextView(context);
		mRTextView = new TextView(context);
		mLTextLayout = new FrameLayout(context);
		mRTextLayout = new FrameLayout(context);
		mSeekBar.SetHKChangedListener(new OnHKChangedListener() {
			@Override
			public void OnHKChangedEvent(View view, int curValue) {
				// TODO Auto-generated method stub
				ShowText();
				if (mChangedListener != null)
					mChangedListener.OnHKChangedEvent(HKSeekBarText.this,
							curValue);
			}
		});
		mSeekBar.SetHKTouchListener(new OnHKTouchListener() {
			@Override
			public void OnHKTouchEvent(View view, TOUCH_ACTION action) {
				// TODO Auto-generated method stub
				if (mTouchedListener != null)
					mTouchedListener.OnHKTouchEvent(HKSeekBarText.this, action);
			}
		});
	}

	// 获取字体的高度
	@SuppressLint("NewApi")
	private int getFontHeight(float fontSize) {
		Configuration config = mContext.getResources().getConfiguration();
		float density = (float) config.densityDpi / 160;
		Paint paint = new Paint();
		paint.setTextSize(fontSize);
		FontMetrics fm = paint.getFontMetrics();
		int height = (int) Math.ceil(fm.descent - fm.top + 2);
		height = (int) (height * density);
		return height;
	}

	// 显示文本
	private void ShowText() {
		String sMaxNum = "";
		String sCurNum = "";
		int maxNum = mSeekBar.GetMaxNum();
		int curNum = mSeekBar.GetCurNum();
		if (mTextFormat == 1) {
			sCurNum = TimeFormat(curNum);
			sMaxNum = TimeFormat(maxNum);
		} else if (mTextFormat == 2) {
			sCurNum = TimeFormat_HMS(curNum);
			sMaxNum = TimeFormat_HMS(maxNum);
		} else {
			sCurNum = curNum + "";
			sMaxNum = maxNum + "";
		}
		if (mLRChange) {
			if (mLTextView.getText() != sMaxNum)
				mLTextView.setText(sMaxNum + mUnit);
			if (mRTextView.getText() != sCurNum)
				mRTextView.setText(sCurNum + mUnit);
		} else {
			if (mLTextView.getText() != sCurNum)
				mLTextView.setText(sCurNum + mUnit);
			if (mRTextView.getText() != sMaxNum)
				mRTextView.setText(sMaxNum + mUnit);
		}
	}

	// 转化为时间格式（分秒）
	private String TimeFormat(int num) {
		String sTime = "";
		int minute = (int) (num / 60);
		int second = (int) (num % 60);
		String sMinute = ConvertToDoubleNum(minute);
		String sSecond = ConvertToDoubleNum(second);
		sTime = sMinute + ":" + sSecond;
		return sTime;
	}

	// 转化为时间格式（时分秒）
	private String TimeFormat_HMS(int num) {
		String sTime = "";
		int hour = (int) (num / 3600);
		int minute = (int) (num / 60) % 60;
		int second = (int) (num % 60);
		String sHour = ConvertToDoubleNum(hour);
		String sMinute = ConvertToDoubleNum(minute);
		String sSecond = ConvertToDoubleNum(second);
		sTime = sHour + ":" + sMinute + ":" + sSecond;
		return sTime;
	}

	// 将数字转化为两位字符串
	private String ConvertToDoubleNum(int num) {
		String result = "";
		if (num < 10) {
			result = "0" + num;
		} else {
			result = num + "";
		}
		return result;
	}

	// 释放图片资源
	public void ReleaseBitmap() {
		mSeekBar.ReleaseBitmap();
	}
	
	
}
