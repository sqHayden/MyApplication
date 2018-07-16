package demo.list.com.myshop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.idx.aidldemo.bean.user.User;
import com.idx.aidldemo.bean.user.UserDao;
import utils.DaoManger;

public class LoginActivity extends AppCompatActivity {

    //总布局
    private RelativeLayout mainLayout;
    //登录
    private LinearLayout loginLayout,registerLayout;
    private EditText account,password;
    private Button regist,login;
    //注册
    private EditText registerAccount,registerPassword,registerName;
    private RadioButton radioMan,radioWoman;
    private Button backToLogin,finish;
    //错误提示
    private EditText accountError,passwordError,nameError;
    //键盘控制器
    private InputMethodManager immanager;
    //性比存储
    private String registGender;
    //数据库管理对象
    private DaoManger mDaoManager;
    //数据库操作对象
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //数据库初始化
        initDataBase();
        //获取控制器
        immanager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //控件加载
        initView();
    }

    /**
     * 获取数据库管理对象
     */
    private void initDataBase() {
        mDaoManager = DaoManger.getInstance();
        mUserDao = mDaoManager.getDaoSession().getUserDao();
    }

    /**
     * 控件加载
     */
    private void initView() {
        mainLayout = findViewById(R.id.main_layout);
        //================登录相关===================
        //登录界面
        loginLayout = findViewById(R.id.login_lay);
        //账号
        account = findViewById(R.id.account);
        //密码
        password = findViewById(R.id.password);
        //注册
        regist = findViewById(R.id.register);
        //注册逻辑
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //转换到注册界面
                changeToRegister();
            }
        });
        //登录
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(account.getText().toString().trim())&&!TextUtils.isEmpty(password.getText().toString().trim())){
                    //校验是否存在
                    User user = mUserDao.queryBuilder().where(UserDao.Properties.Account.eq(account.getText().toString().trim())).unique();
                    if(user!=null){
                        //判断密码是否一致
                        if(user.getPassword().equals(password.getText().toString().trim())){
                            //修改数据库当前用户状态
                            user.setState(true);
                            mUserDao.update(user);
                            //结束
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"用户名或密码错误，请重试！",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        //当前用户不存在
                        Toast.makeText(getApplicationContext(),"当前用户不存在，请注册后操作",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"用户名或密码为空,请填写后操作",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //==================注册相关=================
        //注册界面
        registerLayout = findViewById(R.id.register_lay);
        //账号
        registerAccount = findViewById(R.id.register_account);
        //设置焦点获取监听
        registerAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Toast.makeText(getApplicationContext(),"失去了焦点",Toast.LENGTH_SHORT).show();
                    //校验
                    //判断账号是否为空
                    if(!TextUtils.isEmpty(registerAccount.getText().toString().trim())) {
                        //判断格式是否正确
                        if (!isMobileNumber(registerAccount.getText().toString().trim())) {
                            accountError.setText("※手机格式错误");
                            accountError.setVisibility(View.VISIBLE);
                        } else {
                            accountError.setVisibility(View.INVISIBLE);
                        }
                    }else{
                        accountError.setText("※账号不能为空");
                        accountError.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        //密码
        registerPassword = findViewById(R.id.register_password);
        //设置焦点获取监听
        registerPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Toast.makeText(getApplicationContext(),"失去了焦点",Toast.LENGTH_SHORT).show();
                    //校验
                    //判断密码是否为空
                    if(!TextUtils.isEmpty(registerPassword.getText().toString().trim())) {
                        //判断长度是否正确
                        if (registerPassword.getText().toString().trim().length()<5||registerPassword.getText().toString().trim().length()>26) {
                            passwordError.setText("※密码长度错误");
                            passwordError.setVisibility(View.VISIBLE);
                        } else {
                            passwordError.setVisibility(View.INVISIBLE);
                        }
                    }else{
                        passwordError.setText("※密码不能为空");
                        passwordError.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        //姓名
        registerName = findViewById(R.id.register_name);
        //设置焦点获取监听
        registerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Toast.makeText(getApplicationContext(),"失去了焦点",Toast.LENGTH_SHORT).show();
                    if(!TextUtils.isEmpty(registerName.getText().toString().trim())){
                        if(!isLegalName(registerName.getText().toString().trim())){
                            nameError.setText("※姓名不符合规范");
                            nameError.setVisibility(View.VISIBLE);
                        }else{
                            nameError.setVisibility(View.INVISIBLE);
                        }
                    }else{
                        nameError.setText("※姓名不能为空");
                        nameError.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        //性别
        radioMan = findViewById(R.id.radio_man);
        radioMan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    radioMan.setChecked(true);
                    registGender = "男";
                }
            }
        });
        radioWoman = findViewById(R.id.radio_woman);
        radioWoman.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    radioWoman.setChecked(true);
                    registGender = "女";
                }
            }
        });
        //返回
        backToLogin = findViewById(R.id.to_login);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去主界面
                changeToLogin();
                //设置为隐藏
                accountError.setVisibility(View.GONE);
                passwordError.setVisibility(View.GONE);
                nameError.setVisibility(View.GONE);
            }
        });
        //完成
        finish = findViewById(R.id.register_finish);
        finish.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    //先做格式校验
                    if(accountError.getVisibility()==View.INVISIBLE&&passwordError.getVisibility()==View.INVISIBLE&&nameError.getVisibility()==View.INVISIBLE&&!registGender.equals("")){
                        //去数据库中进行校验
                        User user = mUserDao.queryBuilder().where(UserDao.Properties.Name.eq(registerAccount.getText())).unique();
                        if(user==null){
                            //未注册(向数据库中添加)
                            User registUser = new User(null,registerAccount.getText().toString().trim(),registerPassword.getText().toString().trim(),registerName.getText().toString().trim(),registGender,false);
                            mUserDao.insert(registUser);
                            //关闭键盘
                            closeEdit(v);
                            Toast.makeText(getApplicationContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                            //开启登录
                            changeToLogin();
                        }else {
                            //已注册
                            AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext())
                                    .setTitle("用户已注册")
                                    .setMessage("手机号"+registerAccount.getText().toString()+"已注册，是否返回登录界面？")
                                    .setIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //返回到主界面
                                            changeToLogin();
                                        }
                                    })
                                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //不做事情
                                        }
                                    }).create();
                            alertDialog.show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"注册错误，请修改后重试！",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //====================提示相关================
        //账号错误
        accountError = findViewById(R.id.account_error);
        //密码错误
        passwordError = findViewById(R.id.word_error);
        //姓名错误
        nameError = findViewById(R.id.name_error);
    }

    /**
     * 去注册界面
     */
    private void changeToRegister() {
        //隐藏登录
        if(loginLayout.getVisibility()==View.VISIBLE){
            loginLayout.setVisibility(View.GONE);
        }
        //显示注册
        if(registerLayout.getVisibility()==View.GONE){
            registerLayout.setVisibility(View.VISIBLE);
        }
        //更换背景
        mainLayout.setBackgroundColor(getResources().getColor(R.color.proper));
        //清空注册数据
        clearRegisterData();
    }

    /**
     * 清空注册信息
     */
    private void clearRegisterData() {
        registerAccount.setText("");
        registerPassword.setText("");
        registerName.setText("");
        radioMan.setChecked(false);
        radioWoman.setChecked(false);
        registGender = "";
    }

    /**
     * 去登录界面
     */
    private void changeToLogin() {
        //显示登录
        if(loginLayout.getVisibility()==View.GONE){
            loginLayout.setVisibility(View.VISIBLE);
        }
        //隐藏注册
        if(registerLayout.getVisibility()==View.VISIBLE){
            registerLayout.setVisibility(View.GONE);
        }
        //更换背景
        mainLayout.setBackground(getResources().getDrawable(R.mipmap.mainbg));
    }

    /**
     * 失去焦点并关闭键盘
     **/
    private void closeEdit(View view) {
        //失去焦点
        view.setFocusable(false);
        //关闭键盘
        immanager.hideSoftInputFromWindow(view.getWindowToken(), 0);//隐藏键盘
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNumber(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）/^0?1[3|4|5|7|8][0-9]\d{8}$/
    总结起来就是第一位必定为1，第二位必定为3或5或8或7（电信运营商），其他位置的可以为0-9
    */
        String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 验证输入的名字是否为“中文”或者是否包含“·”
     */
    public static boolean isLegalName(String name){
        if (name.contains("·") || name.contains("•")){
            if (name.matches("^[\\u4e00-\\u9fa5]+[·•][\\u4e00-\\u9fa5]+$")){
                return true;
            }else {
                return false;
            }
        }else {
            if (name.matches("^[\\u4e00-\\u9fa5]+$")){
                return true;
            }else {
                return false;
            }
        }
    }
}
