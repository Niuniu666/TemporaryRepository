package haoke.ui.util;

import haoke.lib.tools.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.BitmapFactory.Options;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class HKCircleBar extends View {
	
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
	 * 设置数值范围
	 * 
	 * @param num
	 */
	public void SetRangeAndExtent(int range, int extent) {
		if (mCanUpdate == false)
			return;
		if (mRange == range && mExtent == extent)
			return;

		mRange = range;
		mExtent = extent;
	}

	/**
	 * 设置当前值
	 * 
	 * @param num
	 * @return int 当前值
	 */
	public int SetOffset(int offset) {
		if (mCanUpdate == false)
			return 0;

		if (offset < 0)
			offset = 0;
		else if (offset > mRange)
			offset = mRange;

		mCurAngle = (float) offset * (mMaxAngle - mMinAngle)
				/ (mRange - mExtent) + mMinAngle;
		mCurOffset = offset;

		invalidate(); // 重画控件
		return mCurOffset;
	}

	/**
	 * 获取当前值
	 * 
	 * @return int
	 */
	public int GetCurOffset() {
		return mCurOffset;
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
	// ------------------------------外部接口 end------------------------------

	// 属性
	public int mRange = 0;
	public int mExtent = 0;
	public Bitmap mBmpBg = null; // 背景
	public Bitmap mBmpBlock = null; // 滑块
	public int mBgX = 0;
	public int mBgY = 0;
	public int mBlockX = 0;
	public int mBlockY = 0;
	public float mMinAngle = 0;
	public float mMaxAngle = 0;
	public boolean mIsScale = false; // 是否根据屏幕密度缩放图片
	public boolean mCanTouch = true;
	// 内部变量
	private Context mContext = null;
	private int mLayoutWidth = 0;
	private int mLayoutHeight = 0;
	private int mBmpBgId = 0;
	private int mBmpBlockId = 0;
	private float mCurAngle = 0;
	private float mPointX = 0; // 圆心X
	private float mPointY = 0; // 圆心Y
	private int mCurOffset = 0;
	private boolean mCanUpdate = true;
	private boolean mFirstTime = true;
	private float mDownX = 0;
	private float mDownY = 0;
	private OnHKTouchListener mTouchedListener = null;
	private OnHKChangedListener mChangedListener = null;
	private Util_Global mGlobal;

	public HKCircleBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Init(context);
	}

	public HKCircleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Init(context);
		// 获取属性
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.HKCircleBar);
		float minAngle = typedArray.getFloat(
				R.styleable.HKCircleBar_ccb_min_angle, 0);
		float maxAngle = typedArray.getFloat(
				R.styleable.HKCircleBar_ccb_max_angle, 0);
		mBmpBgId = typedArray.getResourceId(R.styleable.HKCircleBar_ccb_bg, 0);
		mBmpBlockId = typedArray.getResourceId(
				R.styleable.HKCircleBar_ccb_block, 0);
		boolean isScale = typedArray.getBoolean(
				R.styleable.HKCircleBar_ccb_pic_scale, false);
		boolean canTouch = typedArray.getBoolean(
				R.styleable.HKCircleBar_ccb_can_touch, true);
		typedArray.recycle();
		// 将属性值分配到相应变量
		mMinAngle = minAngle;
		mMaxAngle = maxAngle;
		mCanTouch = canTouch;
		mIsScale = isScale;
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = mIsScale;
		if (mBmpBgId != 0)
			mBmpBg = BitmapFactory.decodeResource(getResources(), mBmpBgId,
					bitmapOptions);
		if (mBmpBlockId != 0)
			mBmpBlock = BitmapFactory.decodeResource(getResources(), mBmpBlockId,
					bitmapOptions);

		if (mBmpBg != null && mBmpBlock != null) {
			mLayoutWidth = mBmpBg.getWidth() > mBmpBlock.getWidth() ? mBmpBg
					.getWidth() : mBmpBlock.getWidth();
			mLayoutHeight = mBmpBg.getHeight() > mBmpBlock.getHeight() ? mBmpBg
					.getHeight() : mBmpBlock.getHeight();
			mBgX = mLayoutWidth - mBmpBg.getWidth();
			mBgY = mLayoutHeight - mBmpBg.getHeight();
			mBlockY = (mLayoutHeight - mBmpBlock.getHeight()) / 2;
		} else if (mBmpBg != null) {
			mLayoutWidth = mBmpBg.getWidth();
			mLayoutHeight = mBmpBg.getHeight();
		} else if (mBmpBlock != null) {
			mLayoutWidth = mBmpBlock.getWidth();
			mLayoutHeight = mBmpBlock.getHeight();
		}
		mPointX = 0;
		mPointY = mLayoutHeight / 2;
	}

	private void initPic() {
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = mIsScale;
		if (mBmpBg == null && mBmpBgId != 0)
			mBmpBg = BitmapFactory.decodeResource(getResources(), mBmpBgId,
					bitmapOptions);
		if (mBmpBlock == null && mBmpBlockId != 0)
			mBmpBlock = BitmapFactory.decodeResource(getResources(), mBmpBlockId,
					bitmapOptions);
	}
	
	// 初始化控件
	private void Init(Context context) {
		mContext = context;
		mGlobal = new Util_Global();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		initPic();
		if (mBmpBg != null && !mBmpBg.isRecycled())
			canvas.drawBitmap(mBmpBg, mBgX, mBgY, null);
		if (mBmpBlock != null && !mBmpBlock.isRecycled()) {
			if (mExtent < mRange) { // 列表大于一页			
				canvas.save();
				canvas.translate(mBlockX, mBlockY);
				canvas.rotate(mCurAngle, 0, mBmpBlock.getHeight() / 2);
				canvas.drawBitmap(mBmpBlock, 0, 0, null);
				canvas.restore();
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX() - mPointX;
		float y = event.getY() - mPointY;
		boolean touchValid = false; // 是否有效点击
		if (mCanTouch == false)
			return touchValid;
		if (mExtent >= mRange) // 列表只有一页的情况
			return touchValid;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 按下
			mGlobal.Beep(mContext); // 按键音

			mFirstTime = true;
			mDownX = x;
			mDownY = y;

			mCanUpdate = false;
			touchValid = true;
			if (mTouchedListener != null)
				mTouchedListener.OnHKTouchEvent(HKCircleBar.this,
						TOUCH_ACTION.BTN_DOWN);
			break;

		case MotionEvent.ACTION_MOVE: // 移动
			if (mDownX == x && mDownY == y && mFirstTime)
				return false;
			mFirstTime = false;

			touchValid = true;
			if (mTouchedListener != null)
				mTouchedListener.OnHKTouchEvent(HKCircleBar.this,
						TOUCH_ACTION.MOUSE_MOVE);
			break;

		case MotionEvent.ACTION_CANCEL: // 动作取消
		case MotionEvent.ACTION_UP: // 弹起
			mCanUpdate = true;
			touchValid = true;
			if (mTouchedListener != null)
				mTouchedListener.OnHKTouchEvent(HKCircleBar.this,
						TOUCH_ACTION.BTN_UP);
			break;
		}

		int curOffset = SetBlockAngle(x, y);
		if (mCurOffset != curOffset) { // 值发生变化，发消息通知父级		
			mCurOffset = curOffset;
			if (mChangedListener != null)
				mChangedListener.OnHKChangedEvent(HKCircleBar.this, mCurOffset);
		}
		return touchValid;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		this.setMeasuredDimension(mLayoutWidth, mLayoutHeight);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		ReleaseBitmap(); // 释放资源 add by lyb 20160201
	}

	private int SetBlockAngle(float x, float y) {
		float angle = (float) Math.atan2(y, x);
		mCurAngle = (float) Math.toDegrees(angle);
		if (mCurAngle < mMinAngle)
			mCurAngle = mMinAngle;
		else if (mCurAngle > mMaxAngle)
			mCurAngle = mMaxAngle;

		int curOffset = (int) ((mRange - mExtent) * (mCurAngle - mMinAngle) / (mMaxAngle - mMinAngle));

		invalidate(); // 重画控件
		return curOffset;
	}
	
	// 释放图片资源
	public void ReleaseBitmap() {
		if (mBmpBg != null && mBmpBgId != 0) {
			mBmpBg.recycle();
			mBmpBg = null;
		}
		if (mBmpBlock != null && mBmpBlockId != 0) {
			mBmpBlock.recycle();
			mBmpBlock = null;
		}
	}
}
