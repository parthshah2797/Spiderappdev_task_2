package com.student.spider_task_2;


        import java.util.Timer;
        import java.util.TimerTask;

        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.os.Handler;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemSelectedListener;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button play, stop,slideshow;
    Spinner spin;
    final Handler mHandler = new Handler();
    MediaPlayer mp;
    private final String[] listContent = { "Welcome to Hell","Yeah","Fastlane"};
    private final int[] resID = {R.raw.welcometohell,R.raw.yeah,R.raw.fastlane};
    int songid=0;
    int imageid=0;
    ImageView iv;
    Timer timer;
    Timer t1=new Timer();
    int seconds=0,minutes=0;

    private final int[] imageres={R.drawable.friends,R.drawable.gameofthrones,R.drawable.breakingbad,R.drawable.sherlock,R.drawable.houseofcards};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp=new MediaPlayer();
        spin=(Spinner) findViewById(R.id.spinner1);
        play=(Button) findViewById(R.id.button1);
        stop=(Button) findViewById(R.id.button2);
        slideshow=(Button) findViewById(R.id.button3);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listContent);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub

                songid = arg2;
            }

            @Override
            public void onNothingSelected(AdapterView arg0) {
                // TODO Auto-generated method stub

            }

        });

        play.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Toast.makeText(getApplicationContext(), String.valueOf(songid+1), Toast.LENGTH_LONG).show();
                mp.reset();
                mp = MediaPlayer.create(getApplicationContext(), resID[songid]);
                mp.start();

            }
        });
        stop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                mp.stop();
            }
        });

        slideshow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                timer=new Timer();
                imageid=0;
                seconds=0;

                t1.scheduleAtFixedRate(new TimerTask(){

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tv=(TextView)findViewById(R.id.textView);
                                tv.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));
                                seconds++;
                            }
                        });
                    }
                },0,1000);
                final Runnable mUpdateResults = new Runnable() {

                    @Override
                    public void run() {
                        if(imageid>=0)
                        slide();
                        else
                        {
                            timer.cancel();
                            timer.purge();//removes canceled tasks from task queue
                            timer=null;
                        }
                    }
                };
                int delay = 0;

                int period = 3000; // repeat every 3 sec.


                    timer.scheduleAtFixedRate(new TimerTask() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub

                            mHandler.post(mUpdateResults);

                        }

                    }, delay, period);
                }


        });
    }

    protected void slide() {
        // TODO Auto-generated method stub
                slideshow.setEnabled(false);
                iv = (ImageView) findViewById(R.id.imageView1);
                Animation imageAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
                if(imageid<imageres.length) {

                    iv.setImageResource(imageres[imageid]);

                    iv.startAnimation(imageAnimation);
                    imageid++;

                }
                else
                {
                    slideshow.setEnabled(true);
                    imageid=-1;
                    t1.cancel();
                    t1.purge();
                    t1=null;
                    t1=new Timer();
                }

            }
    }





