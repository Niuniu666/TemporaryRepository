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

//触摸区域类
class Area {
	public float left;
	public float right;
	public float top;
	public float bottom;
}

public class HKButton extends View {
	
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
	 * 设置控件有效性
	 * 
	 * @param enable
	 */
	public void SetEnable(boolean enable) {
		if (GetEnable() == enable)
			return;

		if (enable)
			mBtnState = BTNSTATE.UP;
		else
			mBtnState = BTNSTATE.DISABLE;
		invalidate(); // 重画控件
	}

	/**
	 * 获取控件有效性
	 * 
	 * @return boolean
	 */
	public boolean GetEnable() {
		boolean enable = true;
		if (mBtnState == BTNSTATE.DISABLE)
			enable = false;
		return enable;
	}

	/**
	 * 设置控件焦点状态
	 * 
	 * @param focus
	 */
	public void SetFocus(boolean focus) {
		if (GetFocus() == focus)
			return;

		if (focus)
			mBtnState = BTNSTATE.FOCUS;
		else
			mBtnState = BTNSTATE.UP;
		invalidate(); // 重画控件
	}

	/**
	 * 获取控件焦点状态
	 * 
	 * @return boolean
	 */
	public boolean GetFocus() {
		boolean focus = false;
		if (mBtnState == BTNSTATE.FOCUS)
			focus = true;
		return focus;
	}

	/**
	 * 获取控件显示区域
	 * 
	 * @return Area
	 */
	public Area GetShowArea() {
		Area area = new Area();
		if (mPicUp != null) {
			area.left = 0;
			area.right = mPicUp.getWidth();
			area.top = 0;
			area.bottom = mPicUp.getHeight();
		} else if (mIconUp != null) {
			area.left = 0;
			area.right = mIconUp.getWidth();
			area.top = 0;
			area.bottom = mIconUp.getHeight();
		}
		return area;
	}

	/**
	 * 获取控件宽度
	 * 
	 * @return int
	 */
	public int GetWidth() {
		if (mPicUp != null)
			return mPicUp.getWidth();
		else if (mIconUp != null)
			return mIconUp.getWidth();
		return 0;
	}

	/**
	 * 获取控件高度
	 * 
	 * @return int
	 */
	public int GetHeight() {
		if (mPicUp != null)
			return mPicUp.getHeight();
		else if (mIconUp != null)
			return mIconUp.getHeight();
		return 0;
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
	 * 设置控件松开图片
	 * 
	 * @param bmp
	 */
	public void SetPicUp(Bitmap bmp) {
		if (mPicUp != null && mPicUpId != 0) {
			mPicUp.recycle();
			mPicUp = null;
		}
		if (mPicUp == bmp)
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

		mPicUp = bmp;
		mPicUpId = 0;
		if (mPicDown == null) {
			mPicDown = bmp;
			mPicDownId = 0;
		}
		if (mPicFocus == null) {
			mPicFocus = bmp;
			mPicFocusId = 0;
		}
		if (mPicDisable == null) {
			mPicDisable = bmp;
			mPicDisableId = 0;
		}
		if (updateLayout) {
			this.requestLayout(); // 刷布局
		}
		this.invalidate(); // 重画控件
	}

	/**
	 * 设置控件按下图片
	 * 
	 * @param bmp
	 */
	public void SetPicDown(Bitmap bmp) {
		if (mPicDown != null && mPicDownId != 0) {
			mPicDown.recycle();
			mPicDown = null;
		}
		mPicDown = bmp;
		mPicDownId = 0;
	}

	/**
	 * 设置控件焦点图片
	 * 
	 * @param bmp
	 */
	public void SetPicFocus(Bitmap bmp) {
		if (mPicFocus != null && mPicFocusId != 0) {
			mPicFocus.recycle();
			mPicFocus = null;
		}
		mPicFocus = bmp;
		mPicFocusId = 0;
	}

	/**
	 * 设置控件无效图片
	 * 
	 * @param bmp
	 */
	public void SetPicDisable(Bitmap bmp) {
		if (mPicDisable != null && mPicDisableId != 0) {
			mPicDisable.recycle();
			mPicDisable = null;
		}
		mPicDisable = bmp;
		mPicDisableId = 0;
	}

	/**
	 * 设置控件松开图标
	 * 
	 * @param bmp
	 */
	public void SetIconUp(Bitmap bmp) {
		if (mIconUp != null && mIconUpId != 0) {
			mIconUp.recycle();
			mIconUp = null;
		}
		mIconUp = bmp;
		mIconUpId = 0;
		if (mIconDown == null) {
			mIconDown = bmp;
			mIconDownId = 0;
		}
		if (mIconFocus == null) {
			mIconFocus = bmp;
			mIconFocusId = 0;
		}
		if (mIconDisable == null) {
			mIconDisable = bmp;
			mIconDisableId = 0;
		}
		this.invalidate(); // 重画控件
	}

	/**
	 * 设置控件按下图标
	 * 
	 * @param bmp
	 */
	public void SetIconDown(Bitmap bmp) {
		if (mIconDown != null && mIconDownId != 0) {
			mIconDown.recycle();
			mIconDown = null;
		}
		mIconDown = bmp;
		mIconDownId = 0;
	}

	/**
	 * 设置控件焦点图标
	 * 
	 * @param bmp
	 */
	public void SetIconFocus(Bitmap bmp) {
		if (mIconFocus != null && mIconFocusId != 0) {
			mIconFocus.recycle();
			mIconFocus = null;
		}
		mIconFocus = bmp;
		mIconFocusId = 0;
	}

	/**
	 * 设置控件无效图标
	 * 
	 * @param bmp
	 */
	public void SetIconDisable(Bitmap bmp) {
		if (mIconDisable != null && mIconDisableId != 0) {
			mIconDisable.recycle();
			mIconDisable = null;
		}
		mIconDisable = bmp;
		mIconDisableId = 0;
	}

	// 返回状态图片
	public Bitmap GetPicUp() {
		return mPicUp;
	}

	// 返回状态图片
	public Bitmap GetPicDown() {
		return mPicDown;
	}

	// 返回状态图片
	public Bitmap GetPicFocus() {
		return mPicFocus;
	}

	// 返回状态图片
	public Bitmap GetPicDisable() {
		return mPicDisable;
	}

	// 返回状态图片
	public Bitmap GetIconUp() {
		return mIconUp;
	}

	// 返回状态图片
	public Bitmap GetIconDown() {
		return mIconDown;
	}

	// 返回状态图片
	public Bitmap GetIconFocus() {
		return mIconFocus;
	}

	// 返回状态图片
	public Bitmap GetIconDisable() {
		return mIconDisable;
	}
	// ------------------------------外部接口 end------------------------------

	// 按钮状态枚举
	enum BTNSTATE {
		UP, DOWN, FOCUS, DISABLE
	}

	// 属性
	public Bitmap mPicUp = null; // 松开图片
	public Bitmap mPicDown = null; // 按下图片
	public Bitmap mPicFocus = null; // 焦点图片
	public Bitmap mPicDisable = null; // 无效图片
	public Bitmap mIconUp = null; // 松开图标
	public Bitmap mIconDown = null; // 按下图标
	public Bitmap mIconFocus = null; // 焦点图标
	public Bitmap mIconDisable = null; // 无效图标
	public float mIconX = 0; // 图标X
	public float mIconY = 0; // 图标Y
	public boolean mIsScale = false; // 是否根据屏幕密度缩放图片
	public int mLongDownTime = 0; // 长按时间
	public int mClickSpace = 500; // 点击间隔
	// 内部变量
	private Context mContext;
	private BTNSTATE mBtnState = BTNSTATE.UP;
	private boolean mIsBtnDown = false;
	private OnHKTouchListener mTouchedListener = null;
	private int mLayoutWidth = 0;
	private int mLayoutHeight = 0;
	private HKTimer mTimer;
	public int mPicUpId = 0;
	public int mPicDownId = 0;
	public int mPicFocusId = 0;
	public int mPicDisableId = 0;
	public int mIconUpId = 0;
	public int mIconDownId = 0;
	public int mIconFocusId = 0;
	public int mIconDisableId = 0;
	private boolean mFirstTime = true;
	private float mDownX = 0;
	private float mDownY = 0;
	public long mClickTime = 0; // 上次点击时间
	private Util_Global mGlobal;

	public HKButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Init(context);
	}

	public HKButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Init(context);
		// 获取属性
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.HKButton);
		mPicUpId = typedArray.getResourceId(R.styleable.HKButton_btn_pic_up, 0);
		mPicDownId = typedArray.getResourceId(
				R.styleable.HKButton_btn_pic_down, 0);
		mPicFocusId = typedArray.getResourceId(
				R.styleable.HKButton_btn_pic_focus, 0);
		mPicDisableId = typedArray.getResourceId(
				R.styleable.HKButton_btn_pic_disable, 0);
		mIconUpId = typedArray.getResourceId(R.styleable.HKButton_btn_icon_up,
				0);
		mIconDownId = typedArray.getResourceId(
				R.styleable.HKButton_btn_icon_down, 0);
		mIconFocusId = typedArray.getResourceId(
				R.styleable.HKButton_btn_icon_focus, 0);
		mIconDisableId = typedArray.getResourceId(
				R.styleable.HKButton_btn_icon_disable, 0);
		float iconX = typedArray.getDimension(R.styleable.HKButton_btn_icon_x,
				0); // 图标X
		float iconY = typedArray.getDimension(R.styleable.HKButton_btn_icon_y,
				0); // 图标Y
		boolean isScale = typedArray.getBoolean(
				R.styleable.HKButton_btn_pic_scale, false);
		int longDownTime = typedArray.getInt(
				R.styleable.HKButton_btn_longdown_time, 0);
		int clickSpace = typedArray.getInt(
				R.styleable.HKButton_btn_click_space, mClickSpace);
		typedArray.recycle();
		// 将属性值分配到相应变量
		mIsScale = isScale;
		mLongDownTime = longDownTime;
		mClickSpace = clickSpace;
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = mIsScale;
		if (mPicUpId != 0) {
			// 松开图片
			mPicUp = BitmapFactory.decodeResource(getResources(), mPicUpId,
					bitmapOptions);
		}
		if (mIconUpId != 0) {
			// 松开图标
			mIconUp = BitmapFactory.decodeResource(getResources(), mIconUpId,
					bitmapOptions);
		}
		mIconX = iconX; // 图标X
		mIconY = iconY; // 图标Y
		SetLayoutSize(GetWidth(), GetHeight());
	}

	private void InitPicUp() {
		if (mPicUp == null) {
			Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inScaled = mIsScale;
			if (mPicUpId != 0) {
				mPicUp = BitmapFactory.decodeResource(getResources(), mPicUpId,
						bitmapOptions);
			}
		}
	}

	private void InitPicDown() {
		if (mPicDown == null) {
			Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inScaled = mIsScale;
			if (mPicDownId == 0)
				mPicDown = mPicUp;
			else
				mPicDown = BitmapFactory.decodeResource(getResources(),
						mPicDownId, bitmapOptions);
		}
	}

	private void InitPicFocus() {
		if (mPicFocus == null) {
			Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inScaled = mIsScale;
			if (mPicFocusId == 0)
				mPicFocus = mPicUp;
			else
				mPicFocus = BitmapFactory.decodeResource(getResources(),
						mPicFocusId, bitmapOptions);
		}
	}

	private void InitPicDisable() {
		if (mPicDisable == null) {
			Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inScaled = mIsScale;
			if (mPicDisableId == 0)
				mPicDisable = mPicUp;
			else
				mPicDisable = BitmapFactory.decodeResource(getResources(),
						mPicDisableId, bitmapOptions);
		}
	}

	private void InitIconUp() {
		if (mIconUp == null) {
			Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inScaled = mIsScale;
			if (mIconUpId != 0) {
				mIconUp = BitmapFactory.decodeResource(getResources(), mIconUpId,
						bitmapOptions);
			}
		}
	}

	private void InitIconDown() {
		if (mIconDown == null) {
			Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inScaled = mIsScale;
			if (mIconDownId == 0)
				mIconDown = mIconUp;
			else
				mIconDown = BitmapFactory.decodeResource(getResources(),
						mIconDownId, bitmapOptions);
		}
	}

	private void InitIconFocus() {
		if (mIconFocus == null) {
			Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inScaled = mIsScale;
			if (mIconFocusId == 0)
				mIconFocus = mIconUp;
			else
				mIconFocus = BitmapFactory.decodeResource(getResources(),
						mIconFocusId, bitmapOptions);
		}
	}

	private void InitIconDisable() {
		if (mIconDisable == null) {
			Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inScaled = mIsScale;
			if (mIconDisableId == 0)
				mIconDisable = mIconUp;
			else
				mIconDisable = BitmapFactory.decodeResource(getResources(),
						mIconDisableId, bitmapOptions);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画图片
		if (mBtnState == BTNSTATE.UP) {
			InitPicUp();
			InitIconUp();
			if (mPicUp != null && !mPicUp.isRecycled())
				canvas.drawBitmap(mPicUp, 0, 0, null);
			if (mIconUp != null && !mIconUp.isRecycled()) {
				float left = mIconX, top = mIconY;
				if (mPicUp != null) {
					left += (mPicUp.getWidth() - mIconUp.getWidth()) / 2;
					top += (mPicUp.getHeight() - mIconUp.getHeight()) / 2;
				}
				canvas.drawBitmap(mIconUp, left, top, null);
			}
		} else if (mBtnState == BTNSTATE.DOWN) {
			InitPicDown();
			InitIconDown();
			if (mPicDown != null && !mPicDown.isRecycled())
				canvas.drawBitmap(mPicDown, 0, 0, null);
			if (mIconDown != null && !mIconDown.isRecycled()) {
				float left = mIconX, top = mIconY;
				if (mPicDown != null) {
					left += (mPicDown.getWidth() - mIconDown.getWidth()) / 2;
					top += (mPicDown.getHeight() - mIconDown.getHeight()) / 2;
				} else if (mPicUp != null) {
					left += (mPicUp.getWidth() - mIconDown.getWidth()) / 2;
					top += (mPicUp.getHeight() - mIconDown.getHeight()) / 2;
				}
				canvas.drawBitmap(mIconDown, left, top, null);
			}
		} else if (mBtnState == BTNSTATE.FOCUS) {
			InitPicFocus();
			InitIconFocus();
			if (mPicFocus != null && !mPicFocus.isRecycled())
				canvas.drawBitmap(mPicFocus, 0, 0, null);
			if (mIconFocus != null && !mIconFocus.isRecycled()) {
				float left = mIconX, top = mIconY;
				if (mPicFocus != null) {
					left += (mPicFocus.getWidth() - mIconFocus.getWidth()) / 2;
					top += (mPicFocus.getHeight() - mIconFocus.getHeight()) / 2;
				} else if (mPicUp != null) {
					left += (mPicUp.getWidth() - mIconFocus.getWidth()) / 2;
					top += (mPicUp.getHeight() - mIconFocus.getHeight()) / 2;
				}
				canvas.drawBitmap(mIconFocus, left, top, null);
			}
		} else if (mBtnState == BTNSTATE.DISABLE) {
			InitPicDisable();
			InitIconDisable();
			if (mPicDisable != null && !mPicDisable.isRecycled())
				canvas.drawBitmap(mPicDisable, 0, 0, null);
			if (mIconDisable != null && !mIconDisable.isRecycled()) {
				float left = mIconX, top = mIconY;
				if (mPicDisable != null) {
					left += (mPicDisable.getWidth() - mIconDisable.getWidth()) / 2;
					top += (mPicDisable.getHeight() - mIconDisable.getHeight()) / 2;
				} else if (mPicUp != null) {
					left += (mPicUp.getWidth() - mIconDisable.getWidth()) / 2;
					top += (mPicUp.getHeight() - mIconDisable.getHeight()) / 2;
				}
				canvas.drawBitmap(mIconDisable, left, top, null);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
		float y = event.getY();
		boolean touchValid = false; // 是否有效点击
		if (mBtnState == BTNSTATE.DISABLE)
			return touchValid;
		if (mBtnState == BTNSTATE.FOCUS)
			return touchValid;
		// 确定触摸区域
		Area touchArea = GetTouchArea();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 按下
			if (x > touchArea.left && x < touchArea.right && y > touchArea.top
					&& y < touchArea.bottom) {
				mFirstTime = true;
				mDownX = x;
				mDownY = y;

				touchValid = true;
				mIsBtnDown = true;
				mBtnState = BTNSTATE.DOWN; // 设置为按下状态
				if (mTouchedListener != null)
					mTouchedListener.OnHKTouchEvent(HKButton.this,
							TOUCH_ACTION.BTN_DOWN);
				if (mLongDownTime > 0) { // 长按时间大于0，启动长按计时器				
					mTimer.SetTimer(mLongDownTime);
				}
				// 通知父元件，不要夺取本控件的焦点
				this.getParent().requestDisallowInterceptTouchEvent(true);
			}
			break;
		case MotionEvent.ACTION_MOVE: // 移动
			if (mIsBtnDown) {
				if (Math.abs(mDownX - x) < 4 && Math.abs(mDownY - y) < 4
						&& mFirstTime)
					return false;
				mFirstTime = false;

				if (mTouchedListener != null)
					mTouchedListener.OnHKTouchEvent(HKButton.this,
							TOUCH_ACTION.MOUSE_MOVE);
				touchValid = true;
				mBtnState = BTNSTATE.UP; // 设置为松开状态
				if (mLongDownTime > 0)
					mTimer.StopTimer();
				this.getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		case MotionEvent.ACTION_UP: // 弹起
		case MotionEvent.ACTION_CANCEL: // 动作取消
			if (mTouchedListener != null)
				mTouchedListener.OnHKTouchEvent(HKButton.this,
						TOUCH_ACTION.BTN_UP);
			if (mIsBtnDown) {
				touchValid = true;
				mBtnState = BTNSTATE.UP; // 设置为松开状态
				// 判断是否为点击
				if (event.getAction() == MotionEvent.ACTION_UP
						&& x > touchArea.left && x < touchArea.right
						&& y > touchArea.top && y < touchArea.bottom) {
					mGlobal.Beep(mContext); // 按键音
					// 发送点击消息
					if (mTouchedListener != null) {
						long time = System.currentTimeMillis();
						if (Math.abs(time - mClickTime) >= mClickSpace) {
							mClickTime = time;
							mTouchedListener.OnHKTouchEvent(HKButton.this,
									TOUCH_ACTION.BTN_CLICKED);
						}
					}
				}
				if (mLongDownTime > 0)
					mTimer.StopTimer();
			}
			break;
		}
		if (touchValid)
			invalidate(); // 重画控件
		return touchValid;
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mTimer != null)
			mTimer.StopTimer();
		ReleaseBitmap(); // 释放资源 add by lyb 20160201
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		this.setMeasuredDimension(mLayoutWidth, mLayoutHeight);
	}

	// 初始化控件
	private void Init(Context context) {
		mContext = context;
		mGlobal = new Util_Global();
		mTimer = new HKTimer(context);
		mTimer.SetHKTimeUpListener(new OnHKTimeUpListener() {
			@Override
			public void OnHKTimeUp(View view) {
				// TODO Auto-generated method stub
				mIsBtnDown = false; // 阻止ACTION_UP执行
				mBtnState = BTNSTATE.UP; // 设置为松开状态
				invalidate(); // 重画控件

				// 发送长按消息
				if (mTouchedListener != null)
					mTouchedListener.OnHKTouchEvent(HKButton.this,
							TOUCH_ACTION.BTN_LONG_DOWN);
				mTimer.StopTimer();
			}
		});
	}

	// 确定触摸区域
	private Area GetTouchArea() {
		Area area = new Area();
		if (mPicUp != null) {
			area.left = 0;
			area.right = mPicUp.getWidth();
			area.top = 0;
			area.bottom = mPicUp.getHeight();
		} else if (mIconUp != null) {
			area.left = 0;
			area.right = mIconUp.getWidth();
			area.top = 0;
			area.bottom = mIconUp.getHeight();
		}
		return area;
	}

	// 释放图片资源
	public void ReleaseBitmap() {
		if (mPicUp != null && mPicUpId != 0) {
			mPicUp.recycle();
			mPicUp = null;
		}		
		if (mPicDown != null && mPicDownId != 0) {
			mPicDown.recycle();
			mPicDown = null;
		}		
		if (mPicFocus != null && mPicFocusId != 0) {
			mPicFocus.recycle();
			mPicFocus = null;
		}		
		if (mPicDisable != null && mPicDisableId != 0) {
			mPicDisable.recycle();
			mPicDisable = null;
		}		
		if (mIconUp != null && mIconUpId != 0) {
			mIconUp.recycle();
			mIconUp = null;
		}
		if (mIconDown != null && mIconDownId != 0) {
			mIconDown.recycle();
			mIconDown = null;
		}		
		if (mIconFocus != null && mIconFocusId != 0) {
			mIconFocus.recycle();
			mIconFocus = null;
		}		
		if (mIconDisable != null && mIconDisableId != 0) {
			mIconDisable.recycle();
			mIconDisable = null;
		}
	}
}
