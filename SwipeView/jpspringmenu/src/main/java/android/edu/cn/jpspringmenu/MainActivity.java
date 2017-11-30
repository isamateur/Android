package android.edu.cn.jpspringmenu;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.rebound.SpringConfig;
import com.jpeng.jpspringmenu.MenuListener;
import com.jpeng.jpspringmenu.SpringMenu;

public class MainActivity extends AppCompatActivity implements MenuListener,RadioGroup.OnCheckedChangeListener,SeekBar.OnSeekBarChangeListener {
    SpringMenu mSpringMenu;
    TitleBar mTitleBar;

    SeekBar mTensionbar, mFrictionBar;

    TextView mTvTension, mTvFriction;

    ImageView mIvIgnore;

    RadioGroup mRgFade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRgFade = (RadioGroup) findViewById(R.id.rg_enablefade);
        mFrictionBar = (SeekBar) findViewById(R.id.sb_friction);
        mTensionbar = (SeekBar) findViewById(R.id.sb_tension);
        mTvFriction = (TextView) findViewById(R.id.tv_friction);
        mTvTension = (TextView) findViewById(R.id.tv_tension);
        mIvIgnore = (ImageView) findViewById(R.id.iv_ignore);
        mRgFade.setOnCheckedChangeListener(this);
        mTensionbar.setOnSeekBarChangeListener(this);
        mFrictionBar.setOnSeekBarChangeListener(this);
        mFrictionBar.setMax(100);
        mTensionbar.setMax(100);
        //初始化各个控件


        //TitleBar!!!!!!!!!!!!!!!!!!!!!!
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        // init titlebar
        mTitleBar.setLeftText("Left");
        mTitleBar.setTitle(R.string.app_name);
        mTitleBar.setBackgroundColor(Color.parseColor("#64b4ff"));
        mTitleBar.setDividerColor(Color.GRAY);
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setLeftTextColor(Color.WHITE);
        mTitleBar.setActionTextColor(Color.WHITE);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpringMenu.setDirection(SpringMenu.DIRECTION_LEFT);
                mSpringMenu.openMenu();
            }
        });
        mTitleBar.addAction(new TitleBar.Action() {
            @Override
            public String getText() {
                return "Right";
            }

            @Override
            public int getDrawable() {
                return 0;
            }

            @Override
            public void performAction(View view) {
               // mSpringMenu.setDirection(SpringMenu.DIRECTION_RIGHT);
                //mSpringMenu.openMenu();
            }
        });

        //SpringBar!!!!!!!!!!!!!!!!!!!11

        // R.layout.view_menu 是你自定义的Menu View的资源ID
        // 为菜单做各种各样的设置...
//        mSpringMenu=(SpringMenu)findViewById(R.id.springmenu);
        mSpringMenu = new SpringMenu(this, R.layout.springmenu);
        // 内容页变暗的效果
        mSpringMenu.setFadeEnable(true);
        mSpringMenu.setChildSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(20, 5));
        // 允许菜单开始拖动的距离
        mSpringMenu.setDragOffset(0.4f);
        //如果和其他控件有冲突可以设置忽略
//        mSpringMenu.addIgnoredView(mFrictionBar);
//        mSpringMenu.addIgnoredView(mTensionbar);
        mSpringMenu.setMenuListener(this);
        mSpringMenu.setDirection(SpringMenu.DIRECTION_LEFT);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mSpringMenu.dispatchTouchEvent(ev);
    }

    @Override
    //打开后的回调
    public void onMenuOpen() {
       Toast.makeText(this, "Menu is opened!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    //关闭后的回调
    public void onMenuClose() {
        Toast.makeText(this, "Menu is closed!!!", Toast.LENGTH_SHORT).show();
    }

    public void onProgressUpdate(float value, boolean bouncing) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.radioButton) {
            mSpringMenu.setFadeEnable(true);
        } else {
            mSpringMenu.setFadeEnable(false);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == mTensionbar) {
            mTvTension.setText("Tension:" + progress);
        } else {
            mTvFriction.setText("Fricsion:" + progress);
        }
        mSpringMenu.setMenuSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(mTensionbar.getProgress(), mFrictionBar.getProgress()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
