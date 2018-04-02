package jack.view.scrolldel;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * LuodiJackShen
 */
public class ScrollDeleteLayout extends ViewGroup {
    private static final String TAG = "ScrollDeleteLayout";

    private int mRightBorder;
    private int mLeftBorder;
    private int mScrollBorder;
    private int mLastX;
    private int mLastY;

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

            if (getChildCount() > 0) {
                mLeftBorder = getChildAt(0).getLeft();
                mRightBorder = getChildAt(getChildCount() - 1).getRight();
                mScrollBorder = (mRightBorder - getWidth()) / 2;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        requestDisallowInterceptTouchEvent(true);
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
                    if (getScrollX() - Math.abs(deltaX) <= mLeftBorder && deltaX > 0) {
                        scrollTo(mLeftBorder, 0);
                        break;
                    }
                    if (getScrollX() + getWidth() + Math.abs(deltaX) >= mRightBorder && deltaX < 0) {
                        scrollTo(mRightBorder - getWidth(), 0);
                        break;
                    }
                    scrollBy(-deltaX, 0);
                    mLastX = x;
                    mLastY = y;
                    return true;
                } else {
                    requestDisallowInterceptTouchEvent(false);
                }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (getScrollX() < mScrollBorder) {
                    scrollBy(-getScrollX(), 0);
                } else {
                    scrollTo(mRightBorder - getWidth(), 0);
                }
            default:
                break;
        }
        return false;
    }
}
