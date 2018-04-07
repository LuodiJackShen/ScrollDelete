package jack.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import jack.view.scrolldel.R;

/**
 * LuodiJackShen
 */
public class ScrollDeleteLayout extends ViewGroup {
    private static final String TAG = "ScrollDeleteLayout";

    private int mRightBorder;//右边界
    private int mLeftBorder;//左边界
    private int mExtendBorder;//扩展边界
    private int mLastX;
    private int mLastY;

    private OnExtendListener mListener;//展开监听器

    public ScrollDeleteLayout(Context context) {
        this(context, null);
    }

    public ScrollDeleteLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollDeleteLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        setWillNotDraw(false);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScrollDeleteLayout);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            int totalWidth = 0;
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                int width = childView.getMeasuredWidth();
                int height = childView.getMeasuredHeight();
                childView.layout(totalWidth, 0, totalWidth + width, height);
                totalWidth += childView.getMeasuredWidth();
            }

            if (getChildCount() > 0) {//初始化边界值。
                mLeftBorder = getChildAt(0).getLeft();
                mRightBorder = getChildAt(getChildCount() - 1).getRight();
                mExtendBorder = (mRightBorder - getWidth()) / 2;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        requestDisallowInterceptTouchEvent(true);//请求处理滑动手势
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    //边界处理
                    if (getScrollX() - Math.abs(deltaX) <= mLeftBorder && deltaX > 0) {
                        scrollTo(mLeftBorder, 0);
                        break;
                    }
                    //边界处理
                    if (getScrollX() + getWidth() + Math.abs(deltaX) >= mRightBorder && deltaX < 0) {
                        scrollTo(mRightBorder - getWidth(), 0);
                        break;
                    }
                    scrollBy(-deltaX, 0);
                    mLastX = x;
                    mLastY = y;
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                //调整布局
                if (getScrollX() < mExtendBorder) {
                    shrink();
                } else {
                    notifyOnExtendListener();
                    scrollTo(mRightBorder - getWidth(), 0);
                }
            default:
                break;
        }
        return false;
    }

    /***
     * 收缩布局。
     */
    public void shrink() {
        scrollBy(-getScrollX(), 0);
    }

    /***
     * 通知布局已经展开。
     */
    private void notifyOnExtendListener() {
        if (mListener != null) {
            mListener.onExtend(this);
        }
    }

    /***
     * 设置展开监听器。
     * @param listener
     */
    public void setOnExtendListener(OnExtendListener listener) {
        mListener = listener;
    }

    /***
     * ViewHolder实现此接口，监听展开操作。
     */
    public interface OnExtendListener {
        void onExtend(ScrollDeleteLayout layout);
    }

}
