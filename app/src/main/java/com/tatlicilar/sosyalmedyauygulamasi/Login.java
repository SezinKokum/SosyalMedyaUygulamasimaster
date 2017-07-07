package com.tatlicilar.sosyalmedyauygulamasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
public class Login extends AppCompatActivity {

    EditText name,pass; //Login ekrandaki kullanıcı adı ve şifre değişkenleri
    ArrayList<Kisi> uyeler; // Sistemdeki üyelerin bilgilerinin tutulduğu array
    String loginIsim, loginPass; //name ve pass değişkenlerine yazılan değerler
    Intent intent; // HomePage e giden intent
    CheckBox cbox; // Kullanıcı oturumunu hatırla tıkı

    private SharedPreferences loginPreferences; //kullanıcı oturumu hatırlamak için shared preferences kullandım
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name= (EditText) findViewById(R.id.loginName); //R.id deki giriş ismini tanıttım
        pass= (EditText) findViewById(R.id.password);  //R.id deki şifreyi tanıttım
        cbox = (CheckBox)findViewById(R.id.checkBox);  //R.id deki checkboxu tanıttım
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false); //ilk durumda kullanıcı oturumunu hatırla dememiş varsaydım
        if (saveLogin == true) {
           name.setText(loginPreferences.getString("name", ""));
           pass.setText(loginPreferences.getString("pass", ""));
           cbox.setChecked(true);
       }

        kisileriOlustur(); //kisileri oluşturan metod

    }

    public void onClick (View v)
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(name.getWindowToken(), 0);

        switch (v.getId()){
            case R.id.girisBtn: // giriş yap butonuna basıldığında
                loginIsim = name.getText().toString();
                loginPass = pass.getText().toString();
                int yetkili=0;
                for(int i=0;i<uyeler.size();i++) {
                    //uyelenin kullanıcı adı ve şifresi edit text alanından girilen bilgilerle uyuşuyorsa
                    if (uyeler.get(i).getIsim().equals(loginIsim) && uyeler.get(i).getParola().equals(loginPass)) {
                        yetkili = 1;
                        //kullanıcı oturumumu hatırla dediyse bilgileri alıyorum değilse siliyorum
                        if(cbox.isChecked()){
                            loginPrefsEditor.putBoolean("saveLogin", true);
                            loginPrefsEditor.putString("name", loginIsim);
                            loginPrefsEditor.putString("pass", loginPass);
                            loginPrefsEditor.commit();
                        }
                       else{
                           loginPrefsEditor.clear();
                           loginPrefsEditor.commit();
                        }
                        intent = new Intent(this, HomePage.class); // artık girişe hazırım homepage classına gidiyorum
                        intent.putExtra("kisi", uyeler.get(i)); // homepage e giderken ilgili üyenin bilgilerini de gönderiyorum
                        startActivity(intent); //yeni ekran açılacak
                    }
                    }
                if(yetkili==0) //eğer bu kişinin bilgileri üye bilgisiyle uyuşmadıysa ekrana bu bilgiyi basıyorum
                    Toast.makeText(this, "Hatalı giriş yaptınız", Toast.LENGTH_SHORT).show();
                break;
            case R.id.uyeBtn: //üye ol butonu
                intent = new Intent(this, Register.class);
                startActivity(intent);
                break;


        }


    }

    public void kisileriOlustur(){

        Kisi k1 = new Kisi("Rümeysa","1234","rumeysa@gmail.com","Kadın","07.01.1998",R.drawable.misty,"http://www.google.com");
        Kisi k2 = new Kisi("Cici","5678","cici@gmail.com","Kadın","08.01.1998",R.drawable.sailormoon,"http://www.facebook.com");
        Kisi k3 = new Kisi("Bici","9101","bici@gmail.com","Erkek","09.01.1998",R.drawable.mask,"http://www.twitter.com");
        Kisi k4 = new Kisi("Neval","1112","neval@gmail.com","Kadın","10.01.1998",R.drawable.angel,"https://github.com/Surinovi");

        k1.arkadasEkle(k3);
        k1.arkadasEkle(k4);
        k2.arkadasEkle(k1);
        k2.arkadasEkle(k4);
        k3.arkadasEkle(k1);
        k3.arkadasEkle(k2);
        k4.arkadasEkle(k1);
        k4.arkadasEkle(k2);

        uyeler=new ArrayList<Kisi>();

        uyeler.add(k1);
        uyeler.add(k2);
        uyeler.add(k3);
        uyeler.add(k4);
    }
    public void doSomethingElse() {
        startActivity(new Intent(Login.this, HomePage.class));
        Login.this.finish();
    }
    public void CopyDB(InputStream inputStream, OutputStream outputStream)
            throws IOException {
        // Copy 1K bytes at a time
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }
}
