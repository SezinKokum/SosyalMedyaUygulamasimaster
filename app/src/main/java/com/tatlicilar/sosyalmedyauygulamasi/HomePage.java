package com.tatlicilar.sosyalmedyauygulamasi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HomePage extends AppCompatActivity {

    Intent intent,intent2, intent3, intent4, intent5;//açılacak yeni ekranlar için intentler
    ImageView img; // profil resmi
    TextView name,tarih;//üye ismi ve doğum tarihi bilgisi
    ListView listView;// üyenin arkadaşları listviewde tutuluyor
    Kisi k;
    MyAdapter adapter;
    int sira;

    private Button buttonImage; //profil fotoğrafını galeriden seçmek için kullandım
    private static int RESULT_LOAD_IMAGE = 1;
    private MediaRecorder recorder; // ses kaydetme için mediarecorder ve mediaplayer kullandım
    private MediaPlayer player;
    private Button recordButton; // sesi başlatmak için kullandım
    private Button recordStop; // sesi durdurmak için kullandım
    private Button aramaBtn;
    private Button date; // doğum tarihini değiştirmek için buton
    private int year; // datepickerdaki yıl
    private int month; //datepickerdaki ay
    private int day; //datepickerdaki gün
    private final String  filepath = Environment.getExternalStorageDirectory().getPath() + "/Users/sezinkokum/Desktop/";
    static final int DATE_DIALOG_ID = 999;
    private String picker_tarih; //date pickerdan seçilen gün-ay-yıl ın oluşturduğu string
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        img = (ImageView) findViewById(R.id.profilImg); // profil resmi
        name = (TextView) findViewById(R.id.nameTvHome); //üye ismi için
        tarih= (TextView) findViewById(R.id.tarihTvHome); //doğum tarihi
        listView = (ListView) findViewById(R.id.arkadasList);//arkadaşlar
        buttonImage = (Button) this.findViewById(R.id.button_from_phone);//galeriden seçilen fotoğraf
        recordButton = (Button) findViewById(R.id.record_button);//şarkıyı başlatma
        recordStop = (Button) findViewById(R.id.record_stop);//şarkıyı durdurma
        aramaBtn = (Button)findViewById(R.id.aramaBtn);//arama butonu
        date = (Button)findViewById(R.id.datepick);//datepicker ı başlatmak için basılan buton
        final MediaPlayer ses=MediaPlayer.create(this, R.raw.ses);

        intent = getIntent();
        k = (Kisi) intent.getSerializableExtra("kisi");
        img.setImageResource(k.getResim()); //homepage açıldığında önceki classtan gelen bilgileri bu ekranda atıyorum
        name.setText(k.getIsim()); //homepage açıldığında önceki classtan gelen bilgileri bu ekranda atıyorum
        tarih.setText(k.getTarih()); //homepage açıldığında önceki classtan gelen bilgileri bu ekranda atıyorum

        adapter=new MyAdapter(this, k.getArkadaslarim());
        listView.setAdapter(adapter);

        //listviewda herhangi bir satıra tıklandıysa
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder alert = new AlertDialog.Builder(HomePage.this);
                alert.setTitle("Arkadaş Sil");
                alert.setMessage(k.getArkadaslarim().get(position).getIsim()+"'i silmek istediğinden emin misin?");

                sira = position;
                alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        k.arkadasSil(sira);
                        adapter.notifyDataSetChanged();
                    }
                });

                alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();

                return false;
            }
        });
        //profil için galeriden fotoğraf seçme butonuna basıldıysa handle ediyorum
        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        //arama butonuna basıldıysa handle ediyorum
        aramaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                intent3 = new Intent(HomePage.this, Arama.class);
                startActivityForResult(intent3, RESULT_LOAD_IMAGE);
            }
        });

        // şarkıyı başlat butonuna basıldıysa handle ediyorum
        recordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ses.start();
            }
        });

        //şarkıyı durdur butonuna basıldıysa handle ediyorum
        recordStop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                ses.pause();
            }

        });

        //doğum tarhi değiştirme butonuna basıldıysa handle ediyorum
        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDialog(DATE_DIALOG_ID);
            }
        });

}

//datepicker objesi yaratıyorum
  protected Dialog onCreateDialog(int id){
      if(id == DATE_DIALOG_ID)
        return new DatePickerDialog(this,dpickerListener, year,month,day);
      return null;
  }

  //datepickera basıldıysa handle ediyorum
    //ay bilgisini aldığımda mevcut aydan hep 1 ay az gösterdi o yüzden +1 diyerek kullandım
  private DatePickerDialog.OnDateSetListener dpickerListener
          = new DatePickerDialog.OnDateSetListener(){
    @Override
      public void onDateSet(DatePicker view, int year, int month, int day){
        if ((month+1) < 10){ //eğer ay tek haneli ise başına 0 ekledim
            picker_tarih = day + ".0" + (month+1) + "." + year;
        }
        else{ //ay çift haneli ise olduğu gibi aldım
        picker_tarih = day + "." + (month+1) + "." + year;
        }
         tarih.setText(picker_tarih) ;
    }
  };

  //action bar için yarattığım menu.xml i tanıtıyorum
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //action bardaki butonlardan birine basıldıysa handle ediyorum
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnArama:     // arama butonunun gideceği sayfaya action bar aracılığıyla gidiyorum
                //Toast.makeText(this, "arama seçildi", Toast.LENGTH_SHORT)
                //  .show();
                intent4 = new Intent(HomePage.this, Arama.class); //arama classına geçiyor
                startActivity(intent4);
                return true;

            // profil butonunun gideceği sayfaya action bar aracılığıyla gidiyorum
            case R.id.btnProfilDuzenle:
                intent5 = new Intent(HomePage.this, ProfilDuzenle.class); //profil düzenle classına gidiyor
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //github profil gösteriyor
    public void profilGoster(View v)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Github Profili");

        WebView wv = new WebView(this);
        wv.loadUrl(k.getUrl());
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
        });

        alert.setView(wv);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    //homepage de geri tuşuna basıldıysa çıkmak istiyor musun diye soruyor
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(HomePage.this);
        alert.setTitle("Çıkış");
        alert.setMessage("Çıkmak istediğinden emin misin?");

        alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent2 = new Intent(HomePage.this, Login.class);
                startActivity(intent2);
            }
        });
        alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    //galeriden seçilen fotoğrafı yüklüyorum
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView image2 = (ImageView) findViewById(R.id.profilImg);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            image2.setImageURI(selectedImage); //profil fotoğrafına set ediyorum
        }
    }

//    private void startRecording() {
//        recorder = new MediaRecorder();
//        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        recorder.setOutputFile(filepath);
//        try {
//            recorder.prepare();
//            recorder.start();
//        }
//        catch (IllegalStateException e) {
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void stopRecording() {
//        if (recorder != null) {
//            recorder.stop();
//            recorder.reset();
//            recorder.release();
//            recorder = null;
//        }
//    }
//
//    private void startPlaying() {
//        player = new MediaPlayer();
//        player.setVolume(1.0f, 1.0f);
//        try {
//            player.setDataSource(filepath);
//            player.prepare();
//            player.start();
//            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//
//                @Override
//                public void onCompletion(MediaPlayer arg0) {
//                    player.stop();
//                    player.release();
//                    player = null;
//                }
//            });
//        }
//        catch (Exception e) {
//        }
//    }

}
