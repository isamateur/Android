package android.edu.cn.login;

import android.app.Activity;
import android.content.Intent;
import android.edu.cn.login.model.Users;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import android.edu.cn.login.SoftKeyBoardSatusView.SoftkeyBoardListener;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

//登陆界面Activity设置
public class LogInActivity extends Activity implements OnFocusChangeListener,OnClickListener,SoftkeyBoardListener{

	private final String TAG="LogInActivity";
	ImageView imageView_icon,imageView_arrow;
	EditText editText_account,editText_pasword;
	LinearLayout layout;
	Button button_login,button_register;
	ImageView imageView_account_clear,imageView_password_clear;
	android.edu.cn.login.SoftKeyBoardSatusView satusView;
	ScrollView scrollView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);
		init();
	}

	//初始化所有控件
	private void init()
	{
		imageView_icon=(ImageView)findViewById(R.id.login_icon);
		imageView_arrow=(ImageView)findViewById(R.id.login_arrow);
		imageView_arrow.setOnClickListener(this);
		editText_account=(EditText)findViewById(R.id.login_edit_account);
		editText_pasword=(EditText)findViewById(R.id.login_edit_password);
		layout=(LinearLayout)findViewById(R.id.login_layout);
		layout.setFocusable(true);
		layout.setFocusableInTouchMode(true);
		layout.requestFocus();
		button_login=(Button)findViewById(R.id.login_login_btn);
		button_login.setOnClickListener(this);
		button_register=(Button)findViewById(R.id.login_register_btn);
		button_register.setOnClickListener(this);
		imageView_account_clear=(ImageView)findViewById(R.id.login_account_edit_clear);
		imageView_account_clear.setOnClickListener(this);
		imageView_account_clear.setVisibility(View.GONE);
		imageView_password_clear=(ImageView)findViewById(R.id.login_password_edit_clear);
		imageView_password_clear.setOnClickListener(this);
		imageView_password_clear.setVisibility(View.GONE);
		editText_account.setOnFocusChangeListener(this);
		editText_pasword.setOnFocusChangeListener(this);
		
		satusView=(SoftKeyBoardSatusView)findViewById(R.id.login_soft_status_view);
		satusView.setSoftKeyBoardListener(this);
		
		scrollView=(ScrollView)findViewById(R.id.login_scroller);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_arrow:
			
			break;
		case R.id.login_account_edit_clear:
			editText_account.setText("");
			break;
		case R.id.login_password_edit_clear:
			editText_pasword.setText("");
			break;
		case R.id.login_login_btn:
			String pswd=null;
			String username=editText_account.getText().toString();
			String userpassword=editText_pasword.getText().toString().trim();
			List<Users> usersinfo=DataSupport.where("UsersId = ? ",username).find(Users.class);

			if (usersinfo!=null){
				Intent intent=new Intent(LogInActivity.this,HomePageActivity.class);
				startActivity(intent);
			}
			else{
				Toast.makeText(this,"You need to register first",Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.login_register_btn:
			Users user=new Users();
			user.setUsersId(editText_account.getText().toString());
			user.setUsersPassword(editText_pasword.getText().toString());
			user.save();
			
			break;
		}
	}

	@Override
	public void keyBoardStatus(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyBoardVisable(int move) {
		// TODO Auto-generated method stub
		Log.e(TAG, "open");
		button_login.getScrollY();
		Message message=new Message();
		message.what=WHAT_SCROLL;
		message.obj=move;
		handler.sendMessageDelayed(message, 500);
	}

	@Override
	public void keyBoardInvisable(int move) {
		// TODO Auto-generated method stub
		Log.e(TAG, "close");
		handler.sendEmptyMessageDelayed(WHAT_BTN_VISABEL, 200);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_edit_account:
			if(hasFocus)
			{
				imageView_account_clear.setVisibility(View.VISIBLE);
			}else {
				imageView_account_clear.setVisibility(View.GONE);
			}
			break;
		case R.id.login_edit_password:
			if(hasFocus)
			{
				imageView_password_clear.setVisibility(View.VISIBLE);
			}else {
				imageView_password_clear.setVisibility(View.GONE);
			}
			break;
		}
	}
	
	final int WHAT_SCROLL=0,WHAT_BTN_VISABEL=WHAT_SCROLL+1;
	Handler handler=new Handler(){

		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			switch (msg.what) {
			case WHAT_SCROLL:
				int move=(Integer) msg.obj;
				button_register.setVisibility(View.GONE);
				scrollView.smoothScrollBy(0, move);
				break;
			case WHAT_BTN_VISABEL:
				button_register.setVisibility(View.VISIBLE);
				break;
			}
		}
		
	};
	
}
