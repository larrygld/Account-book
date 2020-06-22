package com.yuukidach.ucount.user;

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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void button_Onclick(View view) {
        // Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
        EditText editText1 = (EditText)findViewById(R.id.username);
        EditText editText2 = (EditText)findViewById(R.id.password);


        if(editText1.getText().toString().equals("") || editText2.getText().toString().equals("")) {
            Toast.makeText(this, "用户名或密码为空!", Toast.LENGTH_SHORT).show();
        }else{
            List<User> users = DataSupport.where("username=? and password=?", editText1.getText().toString(), editText2.getText().toString()).find(User.class);
            if(users.size() == 1){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "用户名或密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
            }
        }

    }




    public void tv_Onclick(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void changePwd(View view) {
        Intent intent = new Intent(LoginActivity.this, ChangePwdActivity.class);
        startActivity(intent);
    }
}
