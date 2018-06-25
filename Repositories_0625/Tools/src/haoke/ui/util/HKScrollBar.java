package haoke.ui.util;

import haoke.lib.tools.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class HKScrollBar extends View {
	
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
		if (mMode == 0) { // 拉伸方式
			// 计算彩条宽高
			if (mDirection == 0) { // 水平方向			
				if (mColorUp != null) {
					mColorHeight = mColorUp.getHeight();
					if (mExtent >= mRange) // 列表只有一页的情况
						mColorWidth = 0; // mSlideDistance;
					else {
						mColorWidth = mSlideDistance * mExtent / mRange;
						if (mColorWidth < mMinLen) {
							mColorWidth = mMinLen;
						}
					}
				}
			} else { // 垂直方向			
				if (mColorUp != null) {
					mColorWidth = mColorUp.getWidth();
					if (mExtent >= mRange) // 列表只有一页的情况
						mColorHeight = 0; // mSlideDistance;
					else {
						mColorHeight = mSlideDistance * mExtent / mRange;
						if (mColorHeight < mMinLen) {
							mColorHeight = mMinLen;
						}
					}
				}
			}
		}
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

		if (mDirection == 0) { // 水平方向		
			float distance = (float) offset * (mSlideDistance - mColorWidth)
					/ (mRange - mExtent);
			float x = mStartX + distance;
			float y = mStartY;
			mCurOffset = SetBlockPos(x, y);
			
		} else { // 垂直方向		
			float distance = (float) offset * (mSlideDistance - mColorHeight)
					/ (mRange - mExtent);
			float x = mStartX;
			float y = mStartY + distance;
			mCurOffset = SetBlockPos(x, y);
		}
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

	// 滑动条状态枚举
	enum SCBSTATE {
		MOVE, STOP
	}

	// 属性
	public int mBgUpId = 0;
	public int mColorUpId = 0;
	public int mColorDownId = 0;
	public int mColorHeadId = 0;
	public int mColorTailId = 0;
	public int mRange = 0;
	public int mExtent = 0;
	public int mSlideDistance = 0;
	public int mHotWidth = 0; // 滑动条触摸热点宽度
	public int mHotHeight = 0; // 滑动条触摸热点高度
	public Bitmap mBgUp = null;
	public Bitmap mColorUp = null;
	public Bitmap mColorDown = null;
	public Bitmap mColorHead = null;
	public Bitmap mColorTail = null;
	public boolean mIsScale = false; // 是否根据屏幕密度缩放图片
	public float mBgX = 0;
	public float mBgY = 0;
	public float mColorX = 0;
	public float mColorY = 0;
	public float mStartX = 0;
	public float mStartY = 0;
	public int mDirection = 1;
	public int mMode = 0;
	public boolean mCanTouch = true;
	public boolean mReleaseLocation = true;
	// 内部变量
	private Context mContext;
	private SCBSTATE mScbState = SCBSTATE.STOP;
	private int mLayoutWidth = 0;
	private int mLayoutHeight = 0;
	private int mColorWidth = 0;
	private int mColorHeight = 0;
	private int mColorHeadWidth = 0;
	private int mColorHeadHeight = 0;
	private int mColorTailWidth = 0;
	private int mColorTailHeight = 0;
	private int mHalfBlock = 0; // 滑块的一半长度
	private int mCurOffset = 0;
	private int mMinLen = 10; // 拉伸方式彩条最小长度
	private OnHKTouchListener mTouchedListener = null;
	private OnHKChangedListener mChangedListener = null;
	private boolean mCanUpdate = true;
	private boolean mFirstTime = true;
	private float mDownX = 0;
	private float mDownY = 0;
	private Util_Global mGlobal;

	public HKScrollBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Init(context);
	}

	public HKScrollBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Init(context);
		// 获取属性
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.HKScrollBar);
		float slideDistance = typedArray.getDimension(
				R.styleable.HKScrollBar_scb_slide_distance, 0);
		mBgUpId = typedArray
				.getResourceId(R.styleable.HKScrollBar_scb_bg, 0);
		mColorUpId = typedArray.getResourceId(
				R.styleable.HKScrollBar_scb_color_up, 0);
		mColorDownId = typedArray.getResourceId(
				R.styleable.HKScrollBar_scb_color_down, 0);
		mColorHeadId = typedArray.getResourceId(
				R.styleable.HKScrollBar_scb_color_head, 0);
		mColorTailId = typedArray.getResourceId(
				R.styleable.HKScrollBar_scb_color_tail, 0);
		boolean isScale = typedArray.getBoolean(
				R.styleable.HKScrollBar_scb_pic_scale, false);
		float colorX = typedArray.getDimension(
				R.styleable.HKScrollBar_scb_color_x, 0);
		float colorY = typedArray.getDimension(
				R.styleable.HKScrollBar_scb_color_y, 0);
		float hotWidth = typedArray.getDimension(
				R.styleable.HKScrollBar_scb_hot_width, 0);
		float hotHeight = typedArray.getDimension(
				R.styleable.HKScrollBar_scb_hot_height, 0);
		int direction = typedArray.getInt(
				R.styleable.HKScrollBar_scb_direction, mDirection);
		boolean canTouch = typedArray.getBoolean(
				R.styleable.HKScrollBar_scb_can_touch, true);
		int mode = typedArray.getInt(R.styleable.HKScrollBar_scb_mode, 0);
		typedArray.recycle();
		// 将属性值分配到相应变量
		mIsScale = isScale;
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = mIsScale;
		if (mBgUpId != 0) {
			mBgUp = BitmapFactory.decodeResource(getResources(), mBgUpId,
					bitmapOptions);
		}
		if (mColorUpId != 0) {
			// 松开图片
			mColorUp = BitmapFactory.decodeResource(getResources(), mColorUpId,
					bitmapOptions);
			// 按下图片
			if (mColorDownId == 0)
				mColorDown = mColorUp;
			else
				mColorDown = BitmapFactory.decodeResource(getResources(),
						mColorDownId, bitmapOptions);
		}
		if (mColorHeadId != 0) {
			mColorHead = BitmapFactory.decodeResource(getResources(),
					mColorHeadId, bitmapOptions);
			mColorHeadWidth = mColorHead.getWidth();
			mColorHeadHeight = mColorHead.getHeight();
		}
		if (mColorTailId != 0) {
			mColorTail = BitmapFactory.decodeResource(getResources(),
					mColorTailId, bitmapOptions);
			mColorTailWidth = mColorTail.getWidth();
			mColorTailHeight = mColorTail.getHeight();
		}
		mColorX = colorX;
		mColorY = colorY;
		mStartX = colorX;
		mStartY = colorY;
		mHotWidth = (int) hotWidth;
		mHotHeight = (int) hotHeight;
		mDirection = direction;
		mSlideDistance = (int) slideDistance;
		mCanTouch = canTouch;
		mMode = mode;
		if (mMode == 0) { // 拉伸方式		
			if (mSlideDistance == 0) { // 移动距离没有设置，根据图片计算			
				if (mColorUp != null) {
					if (mDirection == 0) // 水平方向
						mSlideDistance = mColorUp.getWidth();
					else
						// 垂直方向
						mSlideDistance = mColorUp.getHeight();
				}
			}
		} else { // 滑块方式		
			if (mSlideDistance == 0) { // 移动距离没有设置，根据图片计算			
				if (mBgUp != null && mColorUp != null) {
					if (mDirection == 0) // 水平方向
						mSlideDistance = mBgUp.getWidth() - mColorUp.getWidth();
					else
						mSlideDistance = mBgUp.getHeight()
								- mColorUp.getHeight();
				} else if (mHotWidth != 0 && mHotHeight != 0
						&& mColorUp != null) {
					if (mDirection == 0) // 水平方向
						mSlideDistance = mHotWidth - mColorUp.getWidth();
					else // 垂直方向
						mSlideDistance = mHotHeight - mColorUp.getHeight();
				}
			}
		}
		// 计算控件尺寸
		InitProperty();
	}

	private void initPic() {
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
		if (mColorDown == null) {
			if (mColorDownId == 0)
				mColorDown = mColorUp;
			else
				mColorDown = BitmapFactory.decodeResource(getResources(),
						mColorDownId, bitmapOptions);
		}
		if (mColorHead == null && mColorHeadId != 0) {
			mColorHead = BitmapFactory.decodeResource(getResources(),
					mColorHeadId, bitmapOptions);
		}
		if (mColorTail == null && mColorTailId != 0) {
			mColorTail = BitmapFactory.decodeResource(getResources(),
					mColorTailId, bitmapOptions);
		}
	}
	
	// 计算控件尺寸
	private void InitProperty() {
		mColorX = mStartX;
		mColorY = mStartY;
		if (mMode == 0) { // 拉伸方式		
			int colorWidth = 0;
			int colorHeight = 0;
			int bgWidth = 0;
			int bgHeight = 0;
			int sliderLen = mSlideDistance;
			if (mDirection == 0) { // 水平方向			
				if (mColorUp != null) {
					colorWidth = mColorUp.getWidth();
					colorHeight = mColorUp.getHeight();
				}
				if (mBgUp != null) {
					bgWidth = mBgUp.getWidth();
					bgHeight = mBgUp.getHeight();
					mLayoutWidth = bgWidth > sliderLen ? bgWidth : sliderLen;
					mLayoutHeight = bgHeight;
				}
				mLayoutWidth = mHotWidth > mLayoutWidth ? mHotWidth
						: mLayoutWidth;
				mLayoutHeight = mHotHeight > mLayoutHeight ? mHotHeight
						: mLayoutHeight;
				// 计算彩条位置
				if (mColorX == 0) {
					mColorX = (mLayoutWidth - sliderLen) / 2;
				}
				if (mColorY == 0) {
					mColorY = (mLayoutHeight - colorHeight) / 2;
				}
			} else { // 垂直方向			
				if (mColorUp != null) {
					colorWidth = mColorUp.getWidth();
					colorHeight = mColorUp.getHeight();
				}
				if (mBgUp != null) {
					bgWidth = mBgUp.getWidth();
					bgHeight = mBgUp.getHeight();
					mLayoutWidth = bgWidth;
					mLayoutHeight = bgHeight > sliderLen ? bgHeight : sliderLen;
				}
				mLayoutWidth = mHotWidth > mLayoutWidth ? mHotWidth
						: mLayoutWidth;
				mLayoutHeight = mHotHeight > mLayoutHeight ? mHotHeight
						: mLayoutHeight;
				// 计算彩条位置
				if (mColorX == 0) {
					mColorX = (mLayoutWidth - colorWidth) / 2;
				}
				if (mColorY == 0) {
					mColorY = (mLayoutHeight - sliderLen) / 2;
				}
			}
		} else { // 滑块方式		
			if (mDirection == 0) { // 水平方向			
				int colorWidth = 0;
				int colorHeight = 0;
				int bgWidth = 0;
				int bgHeight = 0;
				int sliderLen = mSlideDistance;
				if (mColorUp != null) {
					sliderLen += mColorUp.getWidth();
					mHalfBlock = mColorUp.getWidth() / 2;
					colorWidth = mColorUp.getWidth();
					colorHeight = mColorUp.getHeight();
					// 滑块比背景高，先让背景居中 add by lyb 20151125
					if (colorHeight > mBgUp.getHeight()) {
						mBgY = (colorHeight - mBgUp.getHeight()) / 2;
					}
					if (mStartX < 0) {
						mBgX = -mStartX; // 背景位置X增加
						bgWidth -= mStartX; // 背景宽度增加
						mColorX = 0;
					} else {
						colorWidth += mStartX;
						sliderLen += mStartX;
					}
					if (mStartY < 0) {
						mBgY = -mStartY; // 背景位置Y增加
						bgHeight -= mStartY; // 背景高度增加
						mColorY = 0;
					} else {
						colorHeight += mStartY;
					}
				}
				if (mBgUp != null) {
					bgWidth += mBgUp.getWidth();
					bgHeight += mBgUp.getHeight();
					mLayoutWidth = bgWidth > sliderLen ? bgWidth : sliderLen;
					mLayoutHeight = bgHeight > colorHeight ? bgHeight
							: colorHeight;
					// 判断热点，是否要扩大区域
					if (mHotWidth > mLayoutWidth) {
						int x = (mHotWidth - mLayoutWidth) / 2;
						mColorX += x;
						mBgX += x;
						mLayoutWidth = mHotWidth;
					}
					if (mHotHeight > mLayoutHeight) {
						int y = (mHotHeight - mLayoutHeight) / 2;
						mColorY += y;
						mBgY += y;
						mLayoutHeight = mHotHeight;
					}
					// 若滑块没有设置位置，判断滑块位置
					if (mStartY == 0 && mColorUp != null) {
						mColorY = (mLayoutHeight - mColorUp.getHeight()) / 2;
					}
				} else {
					mLayoutWidth = sliderLen > mHotWidth ? sliderLen
							: mHotWidth;
					if (mColorUp != null)
						mLayoutHeight = mColorUp.getHeight() > mHotHeight ? mColorUp
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
				if (mColorUp != null) {
					sliderLen += mColorUp.getHeight();
					mHalfBlock = mColorUp.getHeight() / 2;
					blockWidth = mColorUp.getWidth();
					blockHeight = mColorUp.getHeight();
					// 滑块比背景宽，先让背景居中 add by lyb 20151125
					if (blockWidth > mBgUp.getWidth()) {
						mBgX = (blockWidth - mBgUp.getWidth()) / 2;
					}
					if (mStartX < 0) {
						mBgX = -mStartX; // 背景位置X增加
						bgWidth -= mStartX; // 背景宽度增加
						mColorX = 0;
					} else {
						blockWidth += mStartX;
					}
					if (mStartY < 0) {
						mBgY = -mStartY; // 背景位置Y增加
						bgHeight -= mStartY; // 背景高度增加
						mColorY = 0;
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
						mColorX += x;
						mBgX += x;
						mLayoutWidth = mHotWidth;
					}
					if (mHotHeight > mLayoutHeight) {
						int y = (mHotHeight - mLayoutHeight) / 2;
						mColorY += y;
						mBgY += y;
						mLayoutHeight = mHotHeight;
					}
					// 若滑块没有设置位置，判断滑块位置
					if (mStartX == 0 && mColorUp != null) {
						mColorX = (mLayoutWidth - mColorUp.getWidth()) / 2;
					}
				} else {
					mLayoutHeight = sliderLen > mHotHeight ? sliderLen
							: mHotHeight;
					if (mColorUp != null)
						mLayoutWidth = mColorUp.getWidth() > mHotWidth ? mColorUp
								.getWidth() : mHotWidth;
					else
						mLayoutWidth = mHotWidth;
				}
			}
		}
		mStartX = mColorX;
		mStartY = mColorY;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画图片
		initPic();
		if (mScbState == SCBSTATE.STOP) { // 静止状态		
			Draw(canvas, mBgUp, mColorUp);
		} else if (mScbState == SCBSTATE.MOVE) { // 滑动状态		
			Draw(canvas, mBgUp, mColorDown);
		}
	}

	// 绘画
	private void Draw(Canvas canvas, Bitmap bmpBg, Bitmap bmpColor) {
		if (bmpBg != null && !bmpBg.isRecycled())
			canvas.drawBitmap(bmpBg, mBgX, mBgY, null);
		if (bmpColor != null && !bmpColor.isRecycled()) {
			if (mMode == 0) { // 拉伸方式			
				int colorX = (int) mColorX;
				int colorY = (int) mColorY;
				Rect clipRect = new Rect(colorX, colorY, mColorWidth + colorX,
						mColorHeight + colorY);
				canvas.save();
				canvas.clipRect(clipRect);
				canvas.drawBitmap(bmpColor, colorX, colorY, null);
				canvas.restore();
				if (mDirection == 0) { // 水平方向				
					if (mColorWidth > 0) {
						if (mColorHead != null)
							canvas.drawBitmap(mColorHead, colorX
									- mColorHeadWidth, colorY, null);
						if (mColorTail != null)
							canvas.drawBitmap(mColorTail, colorX + mColorWidth,
									colorY, null);
					}
				} else { // 垂直方向				
					if (mColorHeight > 0) {
						if (mColorHead != null)
							canvas.drawBitmap(mColorHead, colorX, colorY
									- mColorHeadHeight, null);
						if (mColorTail != null)
							canvas.drawBitmap(mColorTail, colorX, colorY
									+ mColorHeight, null);
					}
				}
			} else { // 滑块方式			
				canvas.drawBitmap(bmpColor, mColorX, mColorY, null);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX() - mColorWidth / 2;
		float y = event.getY() - mColorHeight / 2;
		int curOffset = 0;
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
			mScbState = SCBSTATE.MOVE;

			if (mTouchedListener != null)
				mTouchedListener.OnHKTouchEvent(HKScrollBar.this,
						TOUCH_ACTION.BTN_DOWN);

			curOffset = SetBlockPos(x, y);
			if (mCurOffset != curOffset) { // 值发生变化，发消息通知父级			
				mCurOffset = curOffset;
				if (mChangedListener != null)
					mChangedListener.OnHKChangedEvent(HKScrollBar.this,
							mCurOffset);
			}
			// 通知父元件，不要夺取本控件的焦点
			this.getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_CANCEL: // 动作取消
		case MotionEvent.ACTION_UP: // 弹起
			mCanUpdate = true;
			if (mScbState == SCBSTATE.MOVE) {
				touchValid = true;
				mScbState = SCBSTATE.STOP;

				if (mTouchedListener != null)
					mTouchedListener.OnHKTouchEvent(HKScrollBar.this,
							TOUCH_ACTION.BTN_UP);

				if (event.getAction() == MotionEvent.ACTION_UP) {
					curOffset = SetBlockPos(x, y);
					if (mCurOffset != curOffset) { // 值发生变化，发消息通知父级					
						mCurOffset = curOffset;
						if (mChangedListener != null)
							mChangedListener.OnHKChangedEvent(HKScrollBar.this,
									mCurOffset);
					}
					if (mReleaseLocation) // 松开定位
						curOffset = SetOffset(mCurOffset);
				}
			}
			break;
		case MotionEvent.ACTION_MOVE: // 移动
			if (mScbState == SCBSTATE.MOVE) {
				if (mDownX == x && mDownY == y && mFirstTime)
					return false;
				mFirstTime = false;

				touchValid = true;
				curOffset = SetBlockPos(x, y);
				if (mCurOffset != curOffset) { // 值发生变化，发消息通知父级				
					mCurOffset = curOffset;
					if (mChangedListener != null)
						mChangedListener.OnHKChangedEvent(HKScrollBar.this,
								mCurOffset);
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
		int curOffset = 0;
		if (mDirection == 0) { // 水平方向		
			float minLen = mStartX;
			float maxLen = mStartX + mSlideDistance - mColorWidth;
			if (x < minLen)
				x = minLen;
			else if (x > maxLen)
				x = maxLen;

			mColorX = x;
			curOffset = (int) ((mRange - mExtent) * (x - minLen) / (maxLen - minLen));
		} else { // 垂直方向		
			float minLen = mStartY;
			float maxLen = mStartY + mSlideDistance - mColorHeight;
			if (y < minLen)
				y = minLen;
			else if (y > maxLen)
				y = maxLen;

			mColorY = y;
			curOffset = (int) ((mRange - mExtent) * (y - minLen) / (maxLen - minLen));
		}
		invalidate(); // 重画控件
		return curOffset;
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
		if (mColorUp != null && mColorUpId != 0) {
			mColorUp.recycle();
			mColorUp = null;
		}
		if (mColorDown != null && mColorDownId != 0) {
			mColorDown.recycle();
			mColorDown = null;
		}
		if (mColorHead != null && mColorHeadId != 0) {
			mColorHead.recycle();
			mColorHead = null;
		}
		if (mColorTail != null && mColorTailId != 0) {
			mColorTail.recycle();
			mColorTail = null;
		}
	}
}
