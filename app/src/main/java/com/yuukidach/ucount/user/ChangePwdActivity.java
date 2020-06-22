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

public class ChangePwdActivity extends AppCompatActivity {

    private EditText oldpwd, newpwd, newpwd02, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
    }

    public void goToChange(View view) {

        oldpwd = findViewById(R.id.change_old);
        newpwd = findViewById(R.id.pwd_new);
        newpwd02 = findViewById(R.id.pwd_new02);
        username = findViewById(R.id.username01);

        if (username.getText().toString().equals("") || newpwd.getText().toString().equals("") || newpwd02.getText().toString().equals("") || oldpwd.getText().toString().equals("")) {
            Toast.makeText(ChangePwdActivity.this, "输入内容为空，请重新输入！", Toast.LENGTH_SHORT).show();
        } else if(!newpwd.getText().toString().equals(newpwd02.getText().toString())) {
            Toast.makeText(ChangePwdActivity.this, "两次输入密码不一致，请重新输入！", Toast.LENGTH_SHORT).show();
        }else{
            List<User> users = DataSupport.where("username=? and password=?", username.getText().toString(), oldpwd.getText().toString()).find(User.class);
            if(users.size() == 0){
                Toast.makeText(ChangePwdActivity.this, "用户名或密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
            }else{
                ContentValues values=new ContentValues();
                values.put("password", newpwd.getText().toString());
                DataSupport.updateAll(User.class, values, "username = ?", username.getText().toString());
                Intent intent = new Intent(ChangePwdActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(this, "密码修改成功，请登录！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void goToDel(View view) {

        oldpwd = findViewById(R.id.change_old);
        username = findViewById(R.id.username01);

        if (username.getText().toString().equals("") || oldpwd.getText().toString().equals("")) {
            Toast.makeText(ChangePwdActivity.this, "请输入用户名或密码！", Toast.LENGTH_SHORT).show();
        }else{

            List<User> users = DataSupport.where("username=? and password=?", username.getText().toString(), oldpwd.getText().toString()).find(User.class);
            if(users.size() == 0){
                Toast.makeText(ChangePwdActivity.this, "用户名或密码错误，无法注销，请重新输入！", Toast.LENGTH_SHORT).show();
            }else{
                DataSupport.deleteAll(User.class,"username=? and password=?", username.getText().toString(), oldpwd.getText().toString());
                Intent intent = new Intent(ChangePwdActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(this, "注销账号成功！", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
