package com.hansion.commonlib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;

import com.hansion.commonlib.BaseApplication;
import com.hansion.commonlib.R;
import com.hansion.commonlib.utils.UiUtils;


/**
 * Description：带清除按钮的EditText
 * Author: Hansion
 * Time: 2016/10/30 14:10
 */
public class ClearableEditText extends android.support.v7.widget.AppCompatEditText implements TextWatcher, View.OnFocusChangeListener, View.OnKeyListener {

    //删除按钮
    private Drawable mClearDrawable;
    //是否使用无焦点左边图标居中的模式
    private boolean hasNoFocusIconState = false;
    //图标是否默认在左边
    private boolean isLeft = false;
    //是否点击软键盘
    private boolean pressEnterKey = false;
    private OnEditTextListener listener;
    private int textLength = 0;


    //---------------------------构造方法------------------------------------
    public ClearableEditText(Context context) {
        this(context, null);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //-------------------------初始化------------------------------------
    private void init() {
        //取得清除按钮图片（个人测试就算设置了drawableRight也会为null）
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.icon_close);
        }
        //对drawable的边界进行处理
//        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        mClearDrawable.setBounds(0, 0, UiUtils.px2Dp2Px(BaseApplication.Companion.getAppContext().getApplicationContext(),34),
                UiUtils.px2Dp2Px(BaseApplication.Companion.getAppContext().getApplicationContext(),34));
        //初始化默认清除图标不可见
        setClearIconVisible(false);

        //设置输入框焦点变化情况的监听
        setOnFocusChangeListener(this);
        //设置输入框KeyEnter键按下的监听
        setOnKeyListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    //-------------------------清除按钮的显隐------------------------------------
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    //-------------------------左边图标相关------------------------------------
    @Override
    protected void onDraw(Canvas canvas) {
        if (!hasNoFocusIconState) {    //默认样式，图标在左边
            super.onDraw(canvas);
        } else { //图标在中间
            Drawable[] drawables = getCompoundDrawables();
            Drawable drawableLeft = drawables[0];
            translate(drawableLeft, canvas);
            super.onDraw(canvas);
        }
    }

    public void translate(Drawable drawable, Canvas canvas) {
        if (drawable != null) {
            float textWidth = getPaint().measureText(getHint().toString());
            int drawablePadding = getCompoundDrawablePadding();
            int drawableWidth = drawable.getIntrinsicWidth();
            float bodyWidth = textWidth + drawableWidth + drawablePadding;
            if (drawable == getCompoundDrawables()[0]) {
                //平移画布
                canvas.translate((getWidth() - bodyWidth - getPaddingLeft() - getPaddingRight()) / 2, 0);
            }
        }
    }

    //-------------------------其他重写方法------------------------------------

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // 恢复EditText默认的样式
        if (TextUtils.isEmpty(getText().toString())) {

            if (hasNoFocusIconState) {
                isLeft = hasFocus;
            }

            if (listener == null)
                listener = deFaultListener;

            if (hasFocus) {
                listener.onHasFocusAction(v);
            } else {
                listener.onLostFocusAction(v);
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        pressEnterKey = (keyCode == KeyEvent.KEYCODE_ENTER);
        if (pressEnterKey) {
            /*隐藏软键盘*/
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            }

            if (listener == null)
                listener = deFaultListener;

            if (event.getAction() == KeyEvent.ACTION_UP) {
                listener.onEnterKeyAction(v);
            }
        }
        return false;
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑。
     * <p>
     * getWidth():得到控件的宽度
     * event.getX():抬起时的坐标(改坐标是相对于控件本身而言的)
     * getTotalPaddingRight():clean的图标左边缘至控件右边缘的距离
     * getPaddingRight():clean的图标右边缘至控件右边缘的距离
     * <p>
     * 于是:
     * getWidth() - getTotalPaddingRight()表示:
     * 控件左边到clean的图标左边缘的区域
     * getWidth() - getPaddingRight()表示:
     * 控件左边到clean的图标右边缘的区域
     * 所以这两者之间的区域刚好是clean的图标的区域
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mClearDrawable != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        textLength = s.toString().trim().length();
        setClearIconVisible(s.length() > 0);
        isLeft = true;
        if (onEditTextInputListener != null) {
            if (s.toString().length() > 0) {
                onEditTextInputListener.onInput();
            } else {
                onEditTextInputListener.onClear();
            }
        }

    }

    public int getTextlength() {
        return textLength;
    }


    //----------------------------抖动------------------------------------------------
    public void shake(int counts) {
        setAnimation(shakeAnimation(counts));
    }

    private Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 20, 0, 0);
        //设置一个循环加速器，使用传入的次数就会出现摆动的效果。
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }


    //-----------------------------监听-------------------------------------------------

    public void setOnEditTextListener(OnEditTextListener listener) {
        this.listener = listener;
    }

    public interface OnEditTextListener {
        void onEnterKeyAction(View view);

        void onHasFocusAction(View view);

        void onLostFocusAction(View view);
    }

    private OnEditTextListener deFaultListener = new OnEditTextListener() {
        @Override
        public void onEnterKeyAction(View view) {

        }

        @Override
        public void onHasFocusAction(View view) {

        }

        @Override
        public void onLostFocusAction(View view) {

        }
    };


    private OnEditTextInputListener onEditTextInputListener;

    public void setOnEditTextInputListener(OnEditTextInputListener onEditTextInputListener) {
        this.onEditTextInputListener = onEditTextInputListener;
    }

    public interface OnEditTextInputListener {
        void onInput();

        void onClear();
    }

}
