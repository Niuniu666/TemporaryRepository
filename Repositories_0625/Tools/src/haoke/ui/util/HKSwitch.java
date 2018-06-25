package haoke.ui.util;

import haoke.lib.tools.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.BitmapFactory.Options;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class HKSwitch extends View {
	
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
	public void SetHKSwitchListener(OnHKSwitchListener listener) {
		mChangedListener = listener;
	}

	/**
	 * 设置当前值
	 * 
	 * @param num
	 * @return int 当前值
	 */
	public void SetCurValue(boolean value) {
		int num = value ? 1 : 0;
		if (num < mMinNum)
			num = mMinNum;
		else if (num > mMaxNum)
			num = mMaxNum;

		if (mMode == 0) { // 模式0		
			mCurNum = num;
			mSwtState = num == 1 ? SWTSTATE.ON : SWTSTATE.OFF;
			invalidate(); // 重画控件
		} else { // 模式1		
			float distance = (float) (num - mMinNum) * mSlideDistance
					/ (mMaxNum - mMinNum);
			if (mDirection == 0) { // 水平方向			
				float x = mStartX + mHalfBlock + distance;
				float y = mStartY;
				mCurNum = SetBlockPos(x, y);
			} else { // 垂直方向			
				float x = mStartX;
				float y = mStartY + mHalfBlock + (mSlideDistance - distance);
				mCurNum = SetBlockPos(x, y);
			}
		}
	}

	/**
	 * 获取当前值
	 * 
	 * @return int
	 */
	public boolean GetCurValue() {
		return mCurNum == 1 ? true : false;
	}

	/**
	 * 设置可滑动的距离
	 * 
	 * @param len
	 */
	public void SetSlideDistance(int len) {
		mSlideDistance = len;
	}

	/**
	 * 获取可滑动的距离
	 * 
	 * @return int
	 */
	public int GetSlideDistance() {
		return mSlideDistance;
	}

	/**
	 * 设置控件有效性
	 * 
	 * @param enable
	 */
	public void SetEnable(boolean enable) {
		if (enable)
			mSkbState = SKBSTATE.STOP;
		else
			mSkbState = SKBSTATE.DISABLE;
		invalidate(); // 重画控件
	}

	/**
	 * 获取控件有效性
	 * 
	 * @return boolean
	 */
	public boolean GetEnable() {
		boolean enable = true;
		if (mSkbState == SKBSTATE.DISABLE)
			enable = false;
		return enable;
	}

	/**
	 * 获取控件在XML中的宽度和高度
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

	// 滑动条状态枚举
	enum SKBSTATE {
		MOVE, STOP, DISABLE
	}

	// 开关状态枚举
	enum SWTSTATE {
		ON, OFF, DISABLE
	}

	// 属性
	public int mMaxNum = 1;
	public int mMinNum = 0;
	public int mSlideDistance = 0;
	public int mHotWidth = 0; // 滑动条触摸热点宽度
	public int mHotHeight = 0; // 滑动条触摸热点高度
	public int mBgUpId = 0;
	public int mBgDownId = 0;
	public int mBgDisableId = 0;
	public int mColorUpId = 0;
	public int mColorDownId = 0;
	public int mColorDisableId = 0;
	public int mBlockUpId = 0;
	public int mBlockDownId = 0;
	public int mBlockDisableId = 0;
	public Bitmap mBgUp = null;
	public Bitmap mBgDown = null;
	public Bitmap mBgDisable = null;
	public Bitmap mColorUp = null;
	public Bitmap mColorDown = null;
	public Bitmap mColorDisable = null;
	public Bitmap mBlockUp = null;
	public Bitmap mBlockDown = null;
	public Bitmap mBlockDisable = null;
	public boolean mIsScale = false; // 是否根据屏幕密度缩放图片
	public float mBgX = 0;
	public float mBgY = 0;
	public float mColorX = 0;
	public float mColorY = 0;
	public float mStartX = 0;
	public float mStartY = 0;
	public int mDirection = 0;
	// 内部变量
	private Context mContext;
	private SKBSTATE mSkbState = SKBSTATE.STOP; // 模式1使用
	private SWTSTATE mSwtState = SWTSTATE.OFF; // 模式0使用
	private int mLayoutWidth = 0;
	private int mLayoutHeight = 0;
	private int mColorWidth = 0;
	private int mColorHeight = 0;
	public float mBlockX = 0;
	public float mBlockY = 0;
	private float mLastX = 0;
	private float mLastY = 0;
	private int mHalfBlock = 0; // 滑块的一半长度
	private int mCurNum = 0;
	private int mMode = 0; // 开关模式：0表示不带滑块，1表示带滑块
	private OnHKTouchListener mTouchedListener = null;
	private OnHKSwitchListener mChangedListener = null;
	private boolean mFirstTime = true;
	private float mDownX = 0;
	private float mDownY = 0;
	private Util_Global mGlobal;

	public HKSwitch(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Init(context);
	}

	public HKSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Init(context);
		// 获取属性
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.HKSwitch);
		float slideDistance = typedArray.getDimension(
				R.styleable.HKSwitch_swt_slide_distance, 0);
		mBgUpId = typedArray
				.getResourceId(R.styleable.HKSwitch_swt_bg_up, 0);
		mBgDownId = typedArray.getResourceId(
				R.styleable.HKSwitch_swt_bg_down, 0);
		mBgDisableId = typedArray.getResourceId(
				R.styleable.HKSwitch_swt_bg_disable, 0);
		mColorUpId = typedArray.getResourceId(
				R.styleable.HKSwitch_swt_color_up, 0);
		mColorDownId = typedArray.getResourceId(
				R.styleable.HKSwitch_swt_color_down, 0);
		mColorDisableId = typedArray.getResourceId(
				R.styleable.HKSwitch_swt_color_disable, 0);
		mBlockUpId = typedArray.getResourceId(
				R.styleable.HKSwitch_swt_block_up, 0);
		mBlockDownId = typedArray.getResourceId(
				R.styleable.HKSwitch_swt_block_down, 0);
		mBlockDisableId = typedArray.getResourceId(
				R.styleable.HKSwitch_swt_block_disable, 0);
		boolean isScale = typedArray.getBoolean(
				R.styleable.HKSwitch_swt_pic_scale, false);
		float colorX = typedArray.getDimension(
				R.styleable.HKSwitch_swt_color_x, 0);
		float colorY = typedArray.getDimension(
				R.styleable.HKSwitch_swt_color_y, 0);
		float blockX = typedArray.getDimension(
				R.styleable.HKSwitch_swt_block_x, 0);
		float blockY = typedArray.getDimension(
				R.styleable.HKSwitch_swt_block_y, 0);
		float hotWidth = typedArray.getDimension(
				R.styleable.HKSwitch_swt_hot_width, 0);
		float hotHeight = typedArray.getDimension(
				R.styleable.HKSwitch_swt_hot_height, 0);
		int direction = typedArray
				.getInt(R.styleable.HKSwitch_swt_direction, 0);
		typedArray.recycle();
		// 将属性值分配到相应变量
		mIsScale = isScale;
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = mIsScale;
		mSlideDistance = (int) slideDistance;
		if (mBgUpId != 0) {
			// 松开图片
			mBgUp = BitmapFactory.decodeResource(getResources(), mBgUpId,
					bitmapOptions);
		}
		if (mColorUpId != 0) {
			// 松开图片
			mColorUp = BitmapFactory.decodeResource(getResources(), mColorUpId,
					bitmapOptions);
		}
		if (mBlockUpId != 0) {
			// 松开图片
			mBlockUp = BitmapFactory.decodeResource(getResources(), mBlockUpId,
					bitmapOptions);
			// 有滑块，属于模式1
			mMode = 1;
			if (mSlideDistance == 0) { // 移动距离没有设置，根据图片计算			
				if (mBgUp != null && mBlockUp != null) {
					if (mDirection == 0) // 水平方向
						mSlideDistance = mBgUp.getWidth() - mBlockUp.getWidth();
					else
						mSlideDistance = mBgUp.getHeight()
								- mBlockUp.getHeight();
				} else if (mColorUp != null && mBlockUp == null) {
					if (mDirection == 0) // 水平方向
						mSlideDistance = mColorUp.getWidth();
					else
						mSlideDistance = mColorUp.getHeight();
				}
			}
		} else {
			// 无滑块，属于模式0
			mMode = 0;
		}
		mColorX = colorX;
		mColorY = colorY;
		mStartX = blockX;
		mStartY = blockY;
		mBlockX = blockX;
		mBlockY = blockY;
		mHotWidth = (int) hotWidth;
		mHotHeight = (int) hotHeight;
		mDirection = direction;
		// 计算控件尺寸（由背景和滑块来决定，不由彩条）
		if (mDirection == 0) { // 水平方向		
			int blockWidth = 0;
			int blockHeight = 0;
			int bgWidth = 0;
			int bgHeight = 0;
			int sliderLen = mSlideDistance;
			if (mBlockUp != null) {
				sliderLen += mBlockUp.getWidth();
				mHalfBlock = mBlockUp.getWidth() / 2;
				blockWidth = mBlockUp.getWidth();
				blockHeight = mBlockUp.getHeight();
				if (mStartX < 0) {
					mBgX = -mStartX; // 背景位置X增加
					bgWidth -= mStartX; // 背景宽度增加
					mBlockX = 0;
				} else {
					blockWidth += mStartX;
					sliderLen += mStartX;
				}
				if (mStartY < 0) {
					mBgY = -mStartY; // 背景位置Y增加
					bgHeight -= mStartY; // 背景高度增加
					mBlockY = 0;
				} else {
					blockHeight += mStartY;
				}
			}
			if (mBgUp != null) {
				bgWidth += mBgUp.getWidth();
				bgHeight += mBgUp.getHeight();
				mLayoutWidth = bgWidth > sliderLen ? bgWidth : sliderLen;
				mLayoutHeight = bgHeight > blockHeight ? bgHeight : blockHeight;
				// 判断热点，是否要扩大区域
				if (mHotWidth > mLayoutWidth) {
					int x = (mHotWidth - mLayoutWidth) / 2;
					mBlockX += x;
					mBgX += x;
					mLayoutWidth = mHotWidth;
				}
				if (mHotHeight > mLayoutHeight) {
					int y = (mHotHeight - mLayoutHeight) / 2;
					mBlockY += y;
					mBgY += y;
					mLayoutHeight = mHotHeight;
				}
				// 若彩条没有设置位置，判断彩条位置
				if (mColorX == 0 && mColorY == 0 && mColorUp != null) {
					mColorX = (mBgUp.getWidth() - mColorUp.getWidth()) / 2
							+ mBgX;
					mColorY = (mBgUp.getHeight() - mColorUp.getHeight()) / 2
							+ mBgY;
					mColorHeight = mColorUp.getHeight();
				}
				// 若滑块没有设置位置，判断滑块位置
				if (mStartY == 0 && mBlockUp != null) {
					mBlockY = (mLayoutHeight - mBlockUp.getHeight()) / 2;
				}
			} else {
				mLayoutWidth = sliderLen > mHotWidth ? sliderLen : mHotWidth;
				if (mBlockUp != null)
					mLayoutHeight = mBlockUp.getHeight() > mHotHeight ? mBlockUp
							.getHeight() : mHotHeight;
				else
					mLayoutHeight = mHotHeight;
			}
		} else { // 垂直方向		
			int blockWidth = 0;
			int blockHeight = 0;
			int bgWidth = 0;
			int bgHeight = 0;
			int sliderLen = mSlideDistance;
			if (mBlockUp != null) {
				sliderLen += mBlockUp.getHeight();
				mHalfBlock = mBlockUp.getHeight() / 2;
				blockWidth = mBlockUp.getWidth();
				blockHeight = mBlockUp.getHeight();
				if (mStartX < 0) {
					mBgX = -mStartX; // 背景位置X增加
					bgWidth -= mStartX; // 背景宽度增加
					mBlockX = 0;
				} else {
					blockWidth += mStartX;
				}
				if (mStartY < 0) {
					mBgY = -mStartY; // 背景位置Y增加
					bgHeight -= mStartY; // 背景高度增加
					mBlockY = 0;
				} else {
					blockHeight += mStartY;
					sliderLen += mStartY;
				}
			}
			if (mBgUp != null) {
				bgWidth += mBgUp.getWidth();
				bgHeight += mBgUp.getHeight();
				mLayoutWidth = bgWidth > blockWidth ? bgWidth : blockWidth;
				mLayoutHeight = bgHeight > sliderLen ? bgHeight : sliderLen;
				// 判断热点，是否要扩大区域
				if (mHotWidth > mLayoutWidth) {
					int x = (mHotWidth - mLayoutWidth) / 2;
					mBlockX += x;
					mBgX += x;
					mLayoutWidth = mHotWidth;
				}
				if (mHotHeight > mLayoutHeight) {
					int y = (mHotHeight - mLayoutHeight) / 2;
					mBlockY += y;
					mBgY += y;
					mLayoutHeight = mHotHeight;
				}
				// 若彩条没有设置位置，判断彩条位置
				if (mColorX == 0 && mColorY == 0 && mColorUp != null) {
					mColorX = (mBgUp.getWidth() - mColorUp.getWidth()) / 2
							+ mBgX;
					mColorY = (mBgUp.getHeight() - mColorUp.getHeight()) / 2
							+ mBgY;
					mColorWidth = mColorUp.getWidth();
				}
				// 若滑块没有设置位置，判断滑块位置
				if (mStartX == 0 && mBlockUp != null) {
					mBlockX = (mLayoutWidth - mBlockUp.getWidth()) / 2;
				}
			} else {
				mLayoutHeight = sliderLen > mHotHeight ? sliderLen : mHotHeight;
				if (mBlockUp != null)
					mLayoutWidth = mBlockUp.getWidth() > mHotWidth ? mBlockUp
							.getWidth() : mHotWidth;
				else
					mLayoutWidth = mHotWidth;
			}
		}
		mStartX = mBlockX;
		mStartY = mBlockY;
		boolean bValue = mCurNum == 1 ? true : false;
		SetCurValue(bValue);
	}

	private void initPicUp() {
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = mIsScale;
		if (mBgUp == null && mBgUpId != 0) {
			mBgUp = BitmapFactory.decodeResource(getResources(), mBgUpId,
					bitmapOptions);
		}
		if (mColorUp == null && mColorUpId != 0) {
			mColorUp = BitmapFactory.decodeResource(getResources(), mColorUpId,
					bitmapOptions);
		}
		if (mBlockUp == null && mBlockUpId != 0) {
			mBlockUp = BitmapFactory.decodeResource(getResources(), mBlockUpId,
					bitmapOptions);
		}
	}

	private void initPicDown() {
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = mIsScale;
		if (mBgDown == null) {
			if (mBgDownId == 0)
				mBgDown = mBgUp;
			else
				mBgDown = BitmapFactory.decodeResource(getResources(),
						mBgDownId, bitmapOptions);
		}
		if (mColorDown == null) {
			if (mColorDownId == 0)
				mColorDown = mColorUp;
			else
				mColorDown = BitmapFactory.decodeResource(getResources(),
						mColorDownId, bitmapOptions);
		}
		if (mBlockDown == null) {
			if (mBlockDownId == 0)
				mBlockDown = mBlockUp;
			else
				mBlockDown = BitmapFactory.decodeResource(getResources(),
						mBlockDownId, bitmapOptions);
		}
	}
	
	private void initPicDisable() {
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = mIsScale;
		if (mBgDisable == null) {
			if (mBgDisableId == 0)
				mBgDisable = mBgUp;
			else
				mBgDisable = BitmapFactory.decodeResource(getResources(),
						mBgDisableId, bitmapOptions);
		}
		if (mColorDisable == null) {
			if (mColorDisableId == 0)
				mColorDisable = mColorUp;
			else
				mColorDisable = BitmapFactory.decodeResource(getResources(),
						mColorDisableId, bitmapOptions);
		}
		if (mBlockDisable == null) {
			if (mBlockDisableId == 0)
				mBlockDisable = mBlockUp;
			else
				mBlockDisable = BitmapFactory.decodeResource(getResources(),
						mBlockDisableId, bitmapOptions);
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mMode == 0) { // 模式0		
			if (mSwtState == SWTSTATE.OFF) { // 关	
				initPicUp();		
				Draw(canvas, mBgUp, null, null);
			} else if (mSwtState == SWTSTATE.ON) { // 开				
				initPicDown();	
				Draw(canvas, mBgDown, null, null);
			} else if (mSwtState == SWTSTATE.DISABLE) { // 无效状态	
				initPicDisable();		
				Draw(canvas, mBgDisable, null, null);
			}
		} else { // 模式1		
			// 画图片
			if (mSkbState == SKBSTATE.STOP) { // 静止状态		
				initPicUp();	
				Draw(canvas, mBgUp, mColorUp, mBlockUp);
			} else if (mSkbState == SKBSTATE.MOVE) { // 滑动状态			
				initPicDown();		
				Draw(canvas, mBgDown, mColorDown, mBlockDown);
			} else if (mSkbState == SKBSTATE.DISABLE) { // 无效状态	
				initPicDisable();		
				Draw(canvas, mBgDisable, mColorDisable, mBlockDisable);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		int curNum = 0;
		boolean touchValid = false; // 是否有效点击
		if (mSkbState == SKBSTATE.DISABLE)
			return touchValid;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 按下
			mGlobal.Beep(mContext); // 按键音

			mFirstTime = true;
			mDownX = x;
			mDownY = y;

			touchValid = true;
			if (mMode == 0) { // 模式0			
				if (mTouchedListener != null)
					mTouchedListener.OnHKTouchEvent(HKSwitch.this,
							TOUCH_ACTION.BTN_DOWN);
				mSwtState = mSwtState == SWTSTATE.ON ? SWTSTATE.OFF
						: SWTSTATE.ON;
				mCurNum = 1 - mCurNum;
				if (mChangedListener != null) {
					boolean bValue = mCurNum == 1 ? true : false;
					mChangedListener.OnHKSwitchEvent(HKSwitch.this, bValue);
				}
				invalidate(); // 重画控件
			} else { // 模式1			
				mSkbState = SKBSTATE.MOVE;
				SetBlockPos(x, y);
				if (mTouchedListener != null)
					mTouchedListener.OnHKTouchEvent(HKSwitch.this,
							TOUCH_ACTION.BTN_DOWN);
			}
			// 通知父元件，不要夺取本控件的焦点
			this.getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_UP: // 弹起
		case MotionEvent.ACTION_CANCEL: // 动作取消
			if (mMode == 0) { // 模式0			
				if (mTouchedListener != null)
					mTouchedListener.OnHKTouchEvent(HKSwitch.this,
							TOUCH_ACTION.BTN_UP);
			} else { // 模式1			
				if (mSkbState == SKBSTATE.MOVE) {
					touchValid = true;
					mSkbState = SKBSTATE.STOP;

					if (mTouchedListener != null)
						mTouchedListener.OnHKTouchEvent(HKSwitch.this,
								TOUCH_ACTION.BTN_UP);

					if (event.getAction() == MotionEvent.ACTION_UP) {
						curNum = SetBlockPos(x, y);
						if (mCurNum != curNum) { // 值发生变化，发消息通知父级						
							mCurNum = curNum;
							if (mChangedListener != null) {
								boolean bValue = mCurNum == 1 ? true : false;
								mChangedListener.OnHKSwitchEvent(HKSwitch.this,
										bValue);
							}
						}
						boolean bValue = curNum == 1 ? true : false;
						SetCurValue(bValue);
					}
				}
			}
			break;
		case MotionEvent.ACTION_MOVE: // 移动
			if (mMode == 1) { // 模式1			
				if (mSkbState == SKBSTATE.MOVE) {
					if (mDownX == x && mDownY == y && mFirstTime)
						return false;
					mFirstTime = false;

					touchValid = true;
					SetBlockPos(x, y);
				}
			}
			break;
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

	// 初始化控件
	private void Init(Context context) {
		mContext = context;
		mGlobal = new Util_Global();
	}

	// 设置滑块位置
	private int SetBlockPos(float x, float y) {
		int curNum = 0;
		if (mDirection == 0) { // 水平方向		
			float startX;
			if (mBlockUp != null) { // 带滑块			
				startX = mStartX;
			} else { // 不带滑块			
				startX = mColorX;
			}
			float minLen = startX;
			float maxLen = startX + mSlideDistance;
			float blockX = x - mHalfBlock;
			if (blockX >= minLen && blockX <= maxLen) {
				mBlockX = blockX;
				mColorWidth = (int) (x - mColorX);
			} else if (blockX < minLen) {
				mBlockX = minLen;
				mColorWidth = (int) (mHalfBlock - mColorX + minLen);
			} else if (blockX > maxLen) {
				mBlockX = maxLen;
				mColorWidth = (int) (maxLen + mHalfBlock - mColorX);
			}
			if (mColorWidth < 0)
				mColorWidth = 0;
			curNum = (int) ((mBlockX - minLen) * (mMaxNum - mMinNum)
					/ mSlideDistance + 0.5)
					+ mMinNum;
		} else { // 垂直方向		
			float startY;
			if (mBlockUp != null) { // 带滑块			
				startY = mStartY;
			} else { // 不带滑块			
				startY = mColorY;
			}
			float minLen = startY;
			float maxLen = startY + mSlideDistance;
			float blockY = y - mHalfBlock;
			if (blockY >= minLen && blockY <= maxLen) {
				mBlockY = blockY;
				mColorHeight = (int) (y - mColorY);
			} else if (blockY < minLen) {
				mBlockY = minLen;
				mColorHeight = (int) (mHalfBlock - mColorY + minLen);
			} else if (blockY > maxLen) {
				mBlockY = maxLen;
				mColorHeight = (int) (maxLen + mHalfBlock - mColorY);
			}
			if (mColorHeight < 0)
				mColorHeight = 0;
			mColorHeight = mColorUp.getHeight() - mColorHeight;
			curNum = (int) ((mBlockY - minLen) * (mMaxNum - mMinNum)
					/ mSlideDistance + 0.5)
					+ mMinNum;
			curNum = mMaxNum - curNum;
		}
		invalidate(); // 重画控件
		return curNum;
	}

	// 绘画
	private void Draw(Canvas canvas, Bitmap bmpBg, Bitmap bmpColor,
			Bitmap bmpBlock) {
		if (mMode == 0) { // 模式0		
			if (bmpBg != null && !bmpBg.isRecycled())
				canvas.drawBitmap(bmpBg, mBgX, mBgY, null);
		} else {
			if (bmpBg != null && !bmpBg.isRecycled())
				canvas.drawBitmap(bmpBg, mBgX, mBgY, null);
			if (bmpColor != null && !bmpColor.isRecycled()) {
				if (mDirection == 0) { // 水平方向				
					Paint paint = new Paint();
					int colorX = (int) mColorX;
					int colorY = (int) mColorY;
					Rect clipRect = new Rect(colorX, colorY, mColorWidth
							+ colorX, mColorHeight + colorY);
					canvas.save();
					canvas.clipRect(clipRect);
					canvas.drawBitmap(bmpColor, colorX, colorY, null);
					canvas.restore();
				} else { // 垂直方向				
					Paint paint = new Paint();
					int colorX = (int) mColorX;
					int colorY = (int) mColorY
							+ (mColorUp.getHeight() - mColorHeight);
					Rect clipRect = new Rect(colorX, colorY, mColorWidth
							+ colorX, mColorHeight + colorY);
					canvas.save();
					canvas.clipRect(clipRect);
					canvas.drawBitmap(bmpColor, colorX, mColorY, null);
					canvas.restore();
				}
			}
			if (bmpBlock != null && !bmpBlock.isRecycled())
				canvas.drawBitmap(bmpBlock, mBlockX, mBlockY, null);
		}
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
		if (mBgUp != null && mBgUpId != 0) {
			mBgUp.recycle();
			mBgUp = null;
		}
		if (mBgDown != null && mBgDownId != 0) {
			mBgDown.recycle();
			mBgDown = null;
		}
		if (mBgDisable != null && mBgDisableId != 0) {
			mBgDisable.recycle();
			mBgDisable = null;
		}
		if (mColorUp != null && mColorUpId != 0) {
			mColorUp.recycle();
			mColorUp = null;
		}
		if (mColorDown != null && mColorDownId != 0) {
			mColorDown.recycle();
			mColorDown = null;
		}
		if (mColorDisable != null && mColorDisableId != 0) {
			mColorDisable.recycle();
			mColorDisable = null;
		}
		if (mBlockUp != null && mBlockUpId != 0) {
			mBlockUp.recycle();
			mBlockUp = null;
		}
		if (mBlockDown != null && mBlockDownId != 0) {
			mBlockDown.recycle();
			mBlockDown = null;
		}
		if (mBlockDisable != null && mBlockDisableId != 0) {
			mBlockDisable.recycle();
			mBlockDisable = null;
		}
	}
}
