package com.yuukidach.ucount.user;

import android.app.Person;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yuukidach.ucount.MainActivity;
import com.yuukidach.ucount.R;

import org.litepal.crud.DataSupport;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText uname, upwd, upwd02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    public void regist_Onclick(View view) {

        uname = findViewById(R.id.regist_uname);
        upwd = findViewById(R.id.regist_pwd);
        upwd02 = findViewById(R.id.regist_pwd02);


        if(uname.getText().toString().equals("") || upwd.getText().toString().equals("") || upwd02.getText().toString().equals("") ) {
            Toast.makeText(this, "输入内容为空，请重新输入！", Toast.LENGTH_SHORT).show();
        }else if(!upwd.getText().toString().equals(upwd02.getText().toString())){
            Toast.makeText(this, "两次输入密码不一致，请重新输入!!", Toast.LENGTH_SHORT).show();
        }else{
            List<User> users = DataSupport.where("username=?", uname.getText().toString()).find(User.class);
            if(users.size() != 0){
                Toast.makeText(this, "用户名已存在，请重新注册！", Toast.LENGTH_SHORT).show();
            }else{
                User user = new User();
                user.setUsername(uname.getText().toString());
                user.setPassword(upwd.getText().toString());
                Boolean status = user.save();
                if(status){
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "用户创建成功，请登录！", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
