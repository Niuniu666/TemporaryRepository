package haoke.ui.util;

import haoke.lib.tools.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.BitmapFactory.Options;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HKStep extends LinearLayout {
	
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
	 * 设置当前值
	 * 
	 * @param
	 */
	public void SetCurNum(int value) {
		if (mRepeat) {
			if (value < mMinValue) {
				value = mMaxValue;
			} else if (value > mMaxValue) {
				value = mMinValue;
			}
		} else {
			if (value < mMinValue) {
				value = mMinValue;
				mBtnLeft.SetEnable(false);
			} else if (value > mMaxValue) {
				value = mMaxValue;
				mBtnRight.SetEnable(false);
			} else {
				mBtnLeft.SetEnable(true);
				mBtnRight.SetEnable(true);
			}
		}
		mCurValue = value;
		String text = "";
		if (mStringGroup != null) {
			int index = mCurValue - mMinValue;
			if (index >= 0 && index < mStringGroup.length) {
				text = mStringGroup[index];
			} else {
				text = "";
			}
		} else {
			text = mCurValue + "";
		}
		if (mTextView.getText() != text)
			mTextView.setText(text);
	}

	/**
	 * 获取当前值
	 * 
	 * @param
	 */
	public int GetCurNum() {
		return mCurValue;
	}

	/**
	 * 设置当前值对应文本数组
	 * 
	 * @param
	 */
	public void SetStringGroup(String[] group) {
		mStringGroup = group;
		SetCurNum(mCurValue);
	}
	// ------------------------------外部接口 end------------------------------

	// 属性
	private int mLPicUpId = 0;
	private int mLPicDownId = 0;
	private int mLPicDisableId = 0;
	private int mRPicUpId = 0;
	private int mRPicDownId = 0;
	private int mRPicDisableId = 0;
	private int mBgId = 0;
	private Bitmap mLPicUp = null;
	private Bitmap mLPicDown = null;
	private Bitmap mLPicDisable = null;
	private Bitmap mRPicUp = null;
	private Bitmap mRPicDown = null;
	private Bitmap mRPicDisable = null;
	private Bitmap mBg = null;
	private boolean mIsScale = false; // 是否根据屏幕密度缩放图片
	private float mTextSize = 20; // 文本大小
	private int mTextColor = Color.BLACK; // 文本颜色（松开）
	private int mSpace = 0;
	private int mMinValue = 0;
	private int mMaxValue = 0;
	private int mCurValue = 0;
	private boolean mRepeat = false;
	// 内部变量
	private Context mContext;
	private HKButton mBtnLeft;
	private HKButton mBtnRight;
	private HKImageView mImageBg;
	private TextView mTextView;
	private String[] mStringGroup = null;
	private OnHKTouchListener mTouchedListener = null;
	private OnHKChangedListener mChangedListener = null;

	public HKStep(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Init(context);
	}

	public HKStep(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Init(context);
		// 获取属性
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.HKStep);
		int minValue = typedArray.getInt(R.styleable.HKStep_step_min_num, 0);
		int maxValue = typedArray.getInt(R.styleable.HKStep_step_max_num, 0);
		mLPicUpId = typedArray.getResourceId(
				R.styleable.HKStep_step_lbtn_pic_up, 0);
		mLPicDownId = typedArray.getResourceId(
				R.styleable.HKStep_step_lbtn_pic_down, 0);
		mLPicDisableId = typedArray.getResourceId(
				R.styleable.HKStep_step_lbtn_pic_disable, 0);
		mRPicUpId = typedArray.getResourceId(
				R.styleable.HKStep_step_rbtn_pic_up, 0);
		mRPicDownId = typedArray.getResourceId(
				R.styleable.HKStep_step_rbtn_pic_down, 0);
		mRPicDisableId = typedArray.getResourceId(
				R.styleable.HKStep_step_rbtn_pic_disable, 0);
		mBgId = typedArray.getResourceId(R.styleable.HKStep_step_pic_bg, 0);
		float space = typedArray.getDimension(
				R.styleable.HKStep_step_pic_space, 0);
		boolean isScale = typedArray.getBoolean(
				R.styleable.HKStep_step_pic_scale, false);
		float textSize = typedArray.getDimension(
				R.styleable.HKStep_step_text_size, mTextSize);
		int textColor = typedArray.getColor(R.styleable.HKStep_step_text_color,
				mTextColor);
		boolean repeat = typedArray.getBoolean(R.styleable.HKStep_step_repeat,
				false);
		typedArray.recycle();
		// 将属性值分配到相应变量
		mMinValue = minValue;
		mMaxValue = maxValue;
		mIsScale = isScale;
		mSpace = (int) space;
		mRepeat = repeat;
		InitPic();
		mTextSize = new Util_Global().getFontSizeDp(context, textSize);
		mTextColor = textColor;
		mTextView.setTextSize(mTextSize);
		mTextView.setTextColor(mTextColor);

		LinearLayout spaceLayoutLeft = new LinearLayout(mContext);
		spaceLayoutLeft.setLayoutParams(new LayoutParams(mSpace,
				LayoutParams.WRAP_CONTENT));
		LinearLayout spaceLayoutRight = new LinearLayout(mContext);
		spaceLayoutRight.setLayoutParams(new LayoutParams(mSpace,
				LayoutParams.WRAP_CONTENT));
		RelativeLayout textLayout = new RelativeLayout(mContext);
		textLayout.setLayoutParams(new LayoutParams(mImageBg.GetWidth(),
				mImageBg.GetHeight()));
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		textLayout.addView(mImageBg);
		textLayout.addView(mTextView, rlp);

		this.addView(mBtnLeft);
		this.addView(spaceLayoutLeft);
		this.addView(textLayout);
		this.addView(spaceLayoutRight);
		this.addView(mBtnRight);

		mBtnLeft.SetHKTouchListener(new OnHKTouchListener() {
			@Override
			public void OnHKTouchEvent(View view, TOUCH_ACTION action) {
				// TODO Auto-generated method stub
				if (mTouchedListener != null)
					mTouchedListener.OnHKTouchEvent(HKStep.this, action);
				if (action == TOUCH_ACTION.BTN_CLICKED)
					DealLeft();
			}
		});
		mBtnRight.SetHKTouchListener(new OnHKTouchListener() {
			@Override
			public void OnHKTouchEvent(View view, TOUCH_ACTION action) {
				// TODO Auto-generated method stub
				if (mTouchedListener != null)
					mTouchedListener.OnHKTouchEvent(HKStep.this, action);
				if (action == TOUCH_ACTION.BTN_CLICKED)
					DealRight();
			}
		});
		SetCurNum(mCurValue);
	}

	private void InitPic() {
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = mIsScale;
		if (mLPicUp == null && mLPicUpId != 0) {
			mLPicUp = BitmapFactory.decodeResource(getResources(), mLPicUpId,
					bitmapOptions);
			mBtnLeft.SetPicUp(mLPicUp);
		}
		if (mLPicDown == null && mLPicDownId != 0) {
			mLPicDown = BitmapFactory.decodeResource(getResources(),
					mLPicDownId, bitmapOptions);
			mBtnLeft.SetPicDown(mLPicDown);
		}
		if (mLPicDisable == null && mLPicDisableId != 0) {
			mLPicDisable = BitmapFactory.decodeResource(getResources(),
					mLPicDisableId, bitmapOptions);
			mBtnLeft.SetPicDisable(mLPicDisable);
		}
		if (mRPicUp == null && mRPicUpId != 0) {
			mRPicUp = BitmapFactory.decodeResource(getResources(), mRPicUpId,
					bitmapOptions);
			mBtnRight.SetPicUp(mRPicUp);
		}
		if (mRPicDown == null && mRPicDownId != 0) {
			mRPicDown = BitmapFactory.decodeResource(getResources(),
					mRPicDownId, bitmapOptions);
			mBtnRight.SetPicDown(mRPicDown);
		}
		if (mRPicDisable == null && mRPicDisableId != 0) {
			mRPicDisable = BitmapFactory.decodeResource(getResources(),
					mRPicDisableId, bitmapOptions);
			mBtnRight.SetPicDisable(mRPicDisable);
		}
		if (mBg == null && mBgId != 0) {
			mBg = BitmapFactory.decodeResource(getResources(), mBgId,
					bitmapOptions);
			mImageBg.SetImage(mBg);
		}
	}
	
	// 初始化控件
	private void Init(Context context) {
		mContext = context;
		mBtnLeft = new HKButton(context);
		mBtnRight = new HKButton(context);
		mImageBg = new HKImageView(context);
		mTextView = new TextView(context);
	}

	// 右步进
	private void DealRight() {
		mCurValue++;
		SetCurNum(mCurValue);
		if (mChangedListener != null)
			mChangedListener.OnHKChangedEvent(this, mCurValue);
	}

	// 左步进
	private void DealLeft() {
		mCurValue--;
		SetCurNum(mCurValue);
		if (mChangedListener != null)
			mChangedListener.OnHKChangedEvent(this, mCurValue);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		InitPic();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		ReleaseBitmap(); //释放资源 add by lyb 20160201
	}

	// 释放图片资源
	public void ReleaseBitmap() {
		if (mLPicUp != null && mLPicUpId != 0) {
			mLPicUp.recycle();
			mLPicUp = null;
		}
		if (mLPicDown != null && mLPicDownId != 0) {
			mLPicDown.recycle();
			mLPicDown = null;
		}
		if (mLPicDisable != null && mLPicDisableId != 0) {
			mLPicDisable.recycle();
			mLPicDisable = null;
		}
		if (mRPicUp != null && mRPicUpId != 0) {
			mRPicUp.recycle();
			mRPicUp = null;
		}
		if (mRPicDown != null && mRPicDownId != 0) {
			mRPicDown.recycle();
			mRPicDown = null;
		}
		if (mRPicDisable != null && mRPicDisableId != 0) {
			mRPicDisable.recycle();
			mRPicDisable = null;
		}
		if (mBg != null && mBgId != 0) {
			mBg.recycle();
			mBg = null;
		}
	}
}
