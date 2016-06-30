package com.vip.student.base;

import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.vip.student.R;

/**
 * Created by WuYue on 2015/12/2.
 */
public abstract class ActionBarActivity extends AppCompatActivity{

    /*base view*/
    private FrameLayout mContentView;

    /*action_bar_layout*/
    private View mDefActionbarContent;
    private TextView mbtnBack;
    private TextView mTvTtile;
    private LinearLayout mActionBarRightContent;
    private TextView mTvMenu;
    private FrameLayout mActionBarCustomerContent;
    private View mActionbarUnderline;

    /*用户定义的view*/
    //private View mUserView;

    /*视图构造器*/
    private LayoutInflater mInflater;

    /*
    * 两个属性
    * 1、toolbar是否悬浮在窗口之上
    * 2、toolbar的高度获取
    * */
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            R.attr.actionBarSize
    };

    //开关
    boolean mShowActionbar = true;
    boolean mShowBackIcon= true;
    boolean mOverly=false;

    private int mStatusBarHeight =0;
    private int mActionbarHeight=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInflater = LayoutInflater.from(this);

        TypedArray typedArray = getTheme().obtainStyledAttributes(ATTRS);
        /*获取主题中定义的悬浮标志*/
        mOverly = typedArray.getBoolean(0, false);
        /*获取主题中定义的actionlbar的高度*/
        mActionbarHeight = (int) typedArray.getDimension(1, (int) getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window = getWindow();
			// Translucent status bar
			window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

			// create our manager instance after the content view is set
		    SystemBarTintManager tintManager = new SystemBarTintManager(this);
		    // enable status bar tint
		    tintManager.setStatusBarTintEnabled(true);
		    // enable navigation bar tint
		    tintManager.setNavigationBarTintEnabled(true);
		    // set a custom tint color for all system bars
            if(!mOverly){
                tintManager.setTintColor( getResources().getColor(R.color.status_bar_bg_color));
            }else{
                tintManager.setTintColor( getResources().getColor(R.color.transparent));
            }

		    SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();

		    mStatusBarHeight =config.getStatusBarHeight();
		}
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mTvTtile != null) {
            mTvTtile.setText(title);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        View userView = mInflater.inflate(layoutResID, null);
        pupulateContentView(userView);
    }

    @Override
    public void setContentView(View view) {
        pupulateContentView(view);
    }

    private void pupulateContentView(View userView) {
        /*初始化整个内容*/
        initContentView();
        /*初始化用户定义的布局*/
        initUserView(userView);
        if (mShowActionbar) {
            /*初始化actionbar*/
            initActionBar();
            /*自定义actionbar*/
            onCreateCustomActionbarBar(mActionBarCustomerContent);
            /*准备menu*/
            onPrepareActionbarMenu(mTvMenu);
            /*自定义menu*/
            onCreateCustomActionMenu(mActionBarRightContent);
        }
        super.setContentView(mContentView);
    }


    public void showActionbar(boolean showActionbar) {
        mShowActionbar = showActionbar;
    }

    public void showBackIcon(boolean showBack) {
        mShowBackIcon=showBack;
    }

    /**
     * 自定义Actionbar
     */
    public void onCreateCustomActionbarBar(FrameLayout customerContent) {
        if(customerContent.getChildCount()>0){
            mDefActionbarContent.setVisibility(View.GONE);
            mActionBarCustomerContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 自定义Menu
     */
    public void onCreateCustomActionMenu(LinearLayout menuContent) {
        if(menuContent.getChildCount()>0){
            mTvMenu.setVisibility(View.GONE);
            mActionBarRightContent.setVisibility(View.VISIBLE);
        }
    }

    /*准备Menu */
    public void onPrepareActionbarMenu(TextView menu){}

    /*点击Menu */
    public void onActionBarMenuClick(){}

    private void initContentView() {
        /*直接创建一个帧布局，作为视图容器的父容器*/
        mContentView = new FrameLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
    }

    private void initActionBar() {
        /*通过inflater获取toolbar的布局文件*/
        View actionbarLayout = mInflater.inflate(R.layout.action_bar_layout, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mActionbarHeight);
        /*如果是悬浮状态，则不需要设置间距*/
        params.topMargin = mOverly ? 0 : mStatusBarHeight;
        mContentView.addView(actionbarLayout, params);

        mDefActionbarContent=actionbarLayout.findViewById(R.id.def_content);

        mbtnBack =(TextView)actionbarLayout.findViewById(R.id.go_back);
        mbtnBack.setOnClickListener(mOnActionBarViewClickListener);
        mbtnBack.setText(R.string.go_back);
        mbtnBack.setVisibility(mShowBackIcon ? View.VISIBLE : View.GONE);

        mTvTtile=(TextView)actionbarLayout.findViewById(R.id.title);
        setTitle(getTitle());
        mActionBarRightContent=(LinearLayout)actionbarLayout.findViewById(R.id.right_content);
        mTvMenu=(TextView)actionbarLayout.findViewById(R.id.right_menu);
        mTvMenu.setOnClickListener(mOnActionBarViewClickListener);
        mActionBarCustomerContent=(FrameLayout)actionbarLayout.findViewById(R.id.customer_content);
        mActionbarUnderline=actionbarLayout.findViewById(R.id.underline);
    }

    private void initUserView(View userView) {

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        /*获取主题中定义的toolbar的高度*/
        int toolBarSize = mShowActionbar?(mActionbarHeight+mStatusBarHeight):mStatusBarHeight;

        /*如果是悬浮状态，则不需要设置间距*/
        params.topMargin = mOverly ? 0 : toolBarSize;
        mContentView.addView(userView, params);
    }

    private  View.OnClickListener mOnActionBarViewClickListener =new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.go_back:
                    onBackPressed();
                    break;
                case R.id.right_menu:
                    onActionBarMenuClick();
                    break;
            }
        }
    };
}
