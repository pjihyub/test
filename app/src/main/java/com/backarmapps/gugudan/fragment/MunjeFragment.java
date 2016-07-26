package com.backarmapps.gugudan.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.backarmapps.gugudan.R;
import com.backarmapps.gugudan.activities.ApplicationDummy;
import com.backarmapps.gugudan.activities.MainActivity;
import com.backarmapps.gugudan.util.CommonUtil;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by iamabook on 2015. 11. 6..
 */
public class MunjeFragment extends BaseFragment implements View.OnClickListener{

    InterstitialAd mInterstitialAd;
    AdRequest.Builder builder;

    View rootView,mun_je,gugu_pyo;
    Context mContext;

    TextView tv_question1,tv_question2,tv_answer,tv_realanswer;

    ImageView btn_0,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,result;

    Button btn_back, btn_webview;

    private final int ANI_TIMER = 99999;
    private int DEFAULT_COUNT = 1000;
    private int mCurrentTimer = 0;

    private int current_question = 0;

    private ArrayList<int[]> question_set;
    private int[] questionNumArr;
    private int answer;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = getContext();
        rootView = inflater.inflate(R.layout.fragment_munje, container, false);


        initAd();
        initQuestions();
        initView();
        initButton();

        return rootView;

    }

    private void initAd() {
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId(this.getString(R.string.ad_unit_id_interstitial));

        builder = new AdRequest.Builder();
        builder.addTestDevice("7784EAFE79D4264FADDCB213D4BB1E46");
        builder.addTestDevice("63DD795EC89B3B125C1E718C31096011"); // s3

        AdListener mAdListener = new AdListener() {

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
//                btn_webview.setAlpha(1.0f);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                if(!mInterstitialAd.isLoaded()){
                    mInterstitialAd.loadAd(builder.build());
                }

                Intent i = new Intent(mContext, MainActivity.class);
                i.putExtra("powerurl", getResources().getString(R.string.powerurl));
                ApplicationDummy.gotowebview = true;
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(i);



            }
        };

        mInterstitialAd.setAdListener(mAdListener);

        mInterstitialAd.loadAd(builder.build());


    }

    @Override
    public void onStart() {
        super.onStart();

        nextQuestion();

    }


    @Override
    public void onResume() {
        super.onResume();

    }


    private Handler mTimerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what >= ANI_TIMER) {
                nextQuestion();
            }
        }
    };

    private void StopTimer(){
        if(mTimerHandler != null) {
            mTimerHandler.removeMessages(ANI_TIMER);
        }
    }

    private void startNextQuestionTimer(boolean correct){
        StopTimer();
        mCurrentTimer = DEFAULT_COUNT;
        if(correct)
            mTimerHandler.sendEmptyMessageDelayed(ANI_TIMER, mCurrentTimer*1);
        else
            mTimerHandler.sendEmptyMessageDelayed(ANI_TIMER, mCurrentTimer*2);

        setBtnClickable(false);
    }


    private void nextQuestion(){
        if (current_question == question_set.size())
            current_question = 0;

        int[] current_question_set = question_set.get(questionNumArr[current_question]);

        tv_question1.setText(""+current_question_set[0]);
        tv_question2.setText(""+current_question_set[1]);
        tv_answer.setText("");
        tv_realanswer.setVisibility(View.INVISIBLE);
        result.setVisibility(View.INVISIBLE);

        setBtnClickable(true);
        answer = current_question_set[0] * current_question_set[1];


        current_question++;
    }


    private void initQuestions(){

        int[] first = new int[8];
        int[] second = new int[9];

        for(int i=0;i<first.length;i++)
        {
            first[i] = i+2 ; //2~9
        }

        for(int i=0;i<second.length;i++)
        {
            second[i] = i+1 ; //1~9
        }

        question_set = new ArrayList<int[]>();

        for(int i:first)
        {
            for ( int j : second)
            {
                int[] zz = {i, j};
                question_set.add(zz);
            }
        }

        int MAX_IDX = question_set.size();

        questionNumArr = new int[MAX_IDX];
            for (int i = 0;i < MAX_IDX;++i) {
                questionNumArr[i] = i;
            }
            Random oRandom = new Random();
            //shuffle
            int t;
            for (int i = 0;i < MAX_IDX;++i) {

                int newidx = oRandom.nextInt(MAX_IDX);

                if (i != newidx) {
                    t = questionNumArr[newidx];
                    questionNumArr[newidx] = questionNumArr[i];
                    questionNumArr[i] = t;
                }
            }

        // question_set.get(quistionNumAr[0]) = {a , b}  a * b = answer


    }

    private void initButton() {
        btn_back = (Button) rootView.findViewById(R.id.btn_back);
        btn_webview = (Button) rootView.findViewById(R.id.btn_webview);

        btn_back.setOnClickListener(this);
        btn_webview.setOnClickListener(this);

    }



    private void initView() {


        tv_question1 = (TextView)rootView.findViewById(R.id.question1);
        tv_question2 = (TextView)rootView.findViewById(R.id.question2);
        tv_answer = (TextView)rootView.findViewById(R.id.answer);
        tv_realanswer = (TextView)rootView.findViewById(R.id.realanswer);
        result = (ImageView)rootView.findViewById(R.id.result);

        btn_0 = (ImageView) rootView.findViewById(R.id.btn_0);
        btn_1 = (ImageView) rootView.findViewById(R.id.btn_1);
        btn_2 = (ImageView) rootView.findViewById(R.id.btn_2);
        btn_3 = (ImageView) rootView.findViewById(R.id.btn_3);
        btn_4 = (ImageView) rootView.findViewById(R.id.btn_4);
        btn_5 = (ImageView) rootView.findViewById(R.id.btn_5);
        btn_6 = (ImageView) rootView.findViewById(R.id.btn_6);
        btn_7 = (ImageView) rootView.findViewById(R.id.btn_7);
        btn_8 = (ImageView) rootView.findViewById(R.id.btn_8);
        btn_9 = (ImageView) rootView.findViewById(R.id.btn_9);


        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);

        tv_answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count == 0)
                    return;

                if(answer < 10)
                {
                    if(count == 1)
                    {
                        if(Integer.parseInt(s.toString()) == answer)
                        {
                            changeResultImage(true);

                            startNextQuestionTimer(true);
                        } else
                        {
                            changeResultImage(false);

                            tv_realanswer.setText(""+answer);
                            tv_realanswer.setVisibility(View.VISIBLE);
                            startNextQuestionTimer(false);
                        }
                    } else if(count > 1)
                    {
                        changeResultImage(false);

                        tv_realanswer.setText(""+answer);
                        tv_realanswer.setVisibility(View.VISIBLE);
                        startNextQuestionTimer(false);
                    }
                } else
                {
                    if(count == 2)
                    {
                        if(Integer.parseInt(s.toString()) == answer)
                        {
                            changeResultImage(true);

                            startNextQuestionTimer(true);
                        } else
                        {
                            changeResultImage(false);

                            tv_realanswer.setText(""+answer);
                            tv_realanswer.setVisibility(View.VISIBLE);

                            startNextQuestionTimer(false);
                        }
                    } else if(count > 2)
                    {
                        changeResultImage(false);

                        tv_realanswer.setText(""+answer);
                        tv_realanswer.setVisibility(View.VISIBLE);

                        startNextQuestionTimer(false);
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    } //initview end

    private void changeResultImage(boolean correct)
    {
        if(correct)
        {
            result.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.result_correct));

            result.setVisibility(View.VISIBLE);
        }else
        {
            result.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.result_wrong));

            result.setVisibility(View.VISIBLE);

        }
    }


    private void setBtnClickable(boolean clickable)
    {
        btn_0.setClickable(clickable);
        btn_1.setClickable(clickable);
        btn_2.setClickable(clickable);
        btn_3.setClickable(clickable);
        btn_4.setClickable(clickable);
        btn_5.setClickable(clickable);
        btn_6.setClickable(clickable);
        btn_7.setClickable(clickable);
        btn_8.setClickable(clickable);
        btn_9.setClickable(clickable);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_0:
                tv_answer.setText(tv_answer.getText() + "0");
                break;

            case R.id.btn_1:
                tv_answer.setText(tv_answer.getText() + "1");
                break;

            case R.id.btn_2:
                tv_answer.setText(tv_answer.getText() + "2");
                break;

            case R.id.btn_3:
                tv_answer.setText(tv_answer.getText() + "3");
                break;

            case R.id.btn_4:
                tv_answer.setText(tv_answer.getText() + "4");
                break;

            case R.id.btn_5:
                tv_answer.setText(tv_answer.getText() + "5");
                break;

            case R.id.btn_6:
                tv_answer.setText(tv_answer.getText() + "6");
                break;

            case R.id.btn_7:
                tv_answer.setText(tv_answer.getText() + "7");
                break;

            case R.id.btn_8:
                tv_answer.setText(tv_answer.getText() + "8");
                break;

            case R.id.btn_9:
                tv_answer.setText(tv_answer.getText() + "9");
                break;

            case R.id.btn_back:
                CommonUtil.replaceFragment(getContext(), MainActivity.FRAGMENT_MAIN, "BACK_TO_MAIN");

                break;

            case R.id.btn_webview:
//                CommonUtil.replaceFragment(getContext(), MainActivity.FRAGMENT_WEBVIEW, "http://www.naver.com");
                mInterstitialAd.show();
                break;


            default:
                break;
        }


    } // onclick end


}
