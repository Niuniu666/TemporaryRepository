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

public class HKImageView extends View {
	
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
	 * 设置控件显示图片
	 * 
	 * @param Bitmap
	 *            图片
	 */
	public void SetImage(Bitmap bmp) {
		if (mBmpBg == bmp)
			return;

		boolean updateLayout = true;
		if (bmp != null) {
			if (mLayoutWidth == bmp.getWidth()
					&& mLayoutHeight == bmp.getHeight())
				updateLayout = false;
			SetLayoutSize(bmp.getWidth(), bmp.getHeight());
		} else {
			SetLayoutSize(0, 0);
		}
		mBmpBg = bmp;

		if (updateLayout) {
			this.requestLayout(); // 刷布局
		}
		this.invalidate();
	}

	/**
	 * 设置控件是否接收触摸
	 */
	public void SetTouchEnable(boolean enable) {
		mCanTouch = enable;
	}

	/**
	 * 获取控件显示图片
	 * 
	 * @return Bitmap 图片
	 */
	public Bitmap GetImage() {
		return mBmpBg;
	}

	/**
	 * 设置控件宽度
	 * 
	 * @return int
	 */
	public void SetWidth(int width) {
		mLayoutWidth = width;
	}

	/**
	 * 设置控件高度
	 * 
	 * @return int
	 */
	public void SetHeight(int height) {
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
	 * 获取触摸坐标x
	 * 
	 * @return float
	 */
	public float GetTouchX() {
		return mTouchX;
	}

	/**
	 * 获取触摸坐标y
	 * 
	 * @return float
	 */
	public float GetTouchY() {
		return mTouchY;
	}
	// ------------------------------外部接口 end------------------------------

	// 属性
	public int mBmpBgId = 0;
	public Bitmap mBmpBg = null; // 背景
	public boolean mIsScale = false; // 是否根据屏幕密度缩放图片
	public boolean mCanTouch = false; // 是否支持触摸
	// 内部变量
	private Context mContext;
	private boolean mIsBtnDown = false;
	private int mLayoutWidth = 0;
	private int mLayoutHeight = 0;
	private OnHKTouchListener mTouchedListener = null;
	private boolean mFirstTime = true;
	private float mDownX = 0;
	private float mDownY = 0;
	private float mTouchX = 0;
	private float mTouchY = 0;
	private Util_Global mGlobal;

	public HKImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Init(context);
	}

	public HKImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Init(context);
		// 获取属性
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.HKImageView);
		mBmpBgId = typedArray.getResourceId(
				R.styleable.HKImageView_image_bg, 0);
		boolean isScale = typedArray.getBoolean(
				R.styleable.HKImageView_image_pic_scale, false);
		boolean canTouch = typedArray.getBoolean(
				R.styleable.HKImageView_image_can_touch, false);
		float width = typedArray.getDimension(
				R.styleable.HKImageView_image_hot_width, 0);
		float height = typedArray.getDimension(
				R.styleable.HKImageView_image_hot_height, 0);
		typedArray.recycle();
		// 将属性值分配到相应变量
		mIsScale = isScale;
		mCanTouch = canTouch;
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = mIsScale;
		if (mBmpBgId != 0) {
			mBmpBg = BitmapFactory.decodeResource(getResources(), mBmpBgId,
					bitmapOptions);
			SetLayoutSize(mBmpBg.getWidth(), mBmpBg.getHeight());
		}
		if (width != 0 && height != 0) {
			SetLayoutSize((int) width, (int) height);
		}
	}

	private void InitBg() {
		if (mBmpBg == null) {
			Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inScaled = mIsScale;
			if (mBmpBgId != 0) {
				mBmpBg = BitmapFactory.decodeResource(getResources(), mBmpBgId,
						bitmapOptions);
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画图片
		InitBg();
		if (mBmpBg != null && !mBmpBg.isRecycled()) {
			canvas.drawBitmap(mBmpBg, 0, 0, null);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		this.setMeasuredDimension(mLayoutWidth, mLayoutHeight);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		ReleaseBitmap(); //释放资源 add by lyb 20160201
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
		float y = event.getY();
		boolean touchValid = false; // 是否有效点击
		if (mCanTouch == false)
			return touchValid;

		mTouchX = x;
		mTouchY = y;
		// 确定触摸区域
		Area touchArea = GetTouchArea();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 按下
			if (x > touchArea.left && x < touchArea.right && y > touchArea.top
					&& y < touchArea.bottom) {
				mGlobal.Beep(mContext); // 按键音

				mFirstTime = true;
				mDownX = x;
				mDownY = y;

				touchValid = true;
				mIsBtnDown = true;
				if (mTouchedListener != null)
					mTouchedListener.OnHKTouchEvent(HKImageView.this,
							TOUCH_ACTION.BTN_DOWN);
				// 通知父元件，不要夺取本控件的焦点
				this.getParent().requestDisallowInterceptTouchEvent(true);
			}
			break;
		case MotionEvent.ACTION_MOVE: // 移动
			if (mIsBtnDown) {
				if (mDownX == x && mDownY == y && mFirstTime)
					return false;
				mFirstTime = false;

				if (mTouchedListener != null)
					mTouchedListener.OnHKTouchEvent(HKImageView.this,
							TOUCH_ACTION.MOUSE_MOVE);
				touchValid = true;
				this.getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		case MotionEvent.ACTION_UP: // 弹起
		case MotionEvent.ACTION_CANCEL: // 动作取消
			if (mTouchedListener != null)
				mTouchedListener.OnHKTouchEvent(HKImageView.this,
						TOUCH_ACTION.BTN_UP);
			if (mIsBtnDown) {
				touchValid = true;
				// 判断是否为点击
				if (event.getAction() == MotionEvent.ACTION_UP
						&& x > touchArea.left && x < touchArea.right
						&& y > touchArea.top && y < touchArea.bottom) {
					// 发送点击消息
					if (mTouchedListener != null)
						mTouchedListener.OnHKTouchEvent(HKImageView.this,
								TOUCH_ACTION.BTN_CLICKED);
				}
			}
			break;
		}
		if (touchValid)
			invalidate(); // 重画控件
		return touchValid;
	}

	// 初始化控件
	private void Init(Context context) {
		mContext = context;
		mGlobal = new Util_Global();
	}

	// 确定触摸区域
	private Area GetTouchArea() {
		Area area = new Area();
		area.left = 0;
		area.right = mLayoutWidth;
		area.top = 0;
		area.bottom = mLayoutHeight;
		return area;
	}

	// 释放图片资源
	public void ReleaseBitmap() {
		if (mBmpBg != null && mBmpBgId != 0) {
			mBmpBg.recycle();
			mBmpBg = null;
		}
	}
}
