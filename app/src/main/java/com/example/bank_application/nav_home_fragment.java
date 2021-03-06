package com.example.bank_application;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class nav_home_fragment extends Fragment {
    private ViewPager2 event_viewPager,financial_viewPager;
    private LinearLayout event_indicator,financial_indicator;
    private DrawerLayout drawerLayout;
    private Switch balance_switch;
    private TextView address,amount,userName,drawer_name;
    private View drawerView, view;
    private Button Transaction_history_button, remittance_button,ATM,setting,inquiry;
    private ImageButton drawer_button,drawer_close_button,curMoney_button,drawer_logoutButton;
    private String JsonString,IP_ADDRESS;
    private Bundle Info;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_nav_home_page,container,false);
        IP_ADDRESS = ((databaseIP)getActivity().getApplication()).getIP_Address();
        Info = getArguments();                              //Bundle ????????????
        ArrayList<String> eventList = Info.getStringArrayList("eventBanner");
        ArrayList<String> financeList = Info.getStringArrayList("financeBanner");
        String[] event_ImageUrl = eventList.toArray(new String[eventList.size()]);  // ????????? ViewPager Url ??????
        String[] financial_ImageUrl = financeList.toArray(new String[financeList.size()]); // ???????????? ViewPager Url ??????
        address = (TextView) view.findViewById(R.id.home_address);      //TextView ??????
        amount = (TextView) view.findViewById(R.id.home_amount);
        userName = (TextView) view.findViewById(R.id.home_userName);
        balance_switch = (Switch) view.findViewById(R.id.home_balance_switch);
        balance_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    getCurMoney getCurMoney = new getCurMoney();
                    getCurMoney.execute("http://" + IP_ADDRESS + "getCurMoney.php",Info.getString("ID"));
                }else{
                    amount.setText("???");
                }
            }
        });
        StringBuilder address_builder = new StringBuilder(Info.getString("Address"));   //???????????? ????????? ??????
        address_builder.insert(3,"-");
        address_builder.insert(8,"-");
        address_builder.insert(13,"-");
        userName.setText(Info.getString("Name"));       //Bundle ????????? ?????? TextView ??????
        address.setText(address_builder.toString());
        amount.setText(String.valueOf(Info.getInt("Money")));
        drawer_button = (ImageButton)view.findViewById(R.id.drawer_button);         //drawer_layout ???????????? ?????? ??????
        drawer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);                               //drawer_layout ???????????? ?????????
            }
        });
        curMoney_button = (ImageButton) view.findViewById(R.id.home_curMoney_button);
        curMoney_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurMoney curMoney = new getCurMoney();
                curMoney.execute("http://" + IP_ADDRESS + "/bank/getCurMoney.php",Info.getString("ID"));
            }
        });
        drawerLayout = (DrawerLayout)view.findViewById(R.id.drawer_layout);        //drawer_layout ??????
        drawer_name = (TextView)view.findViewById(R.id.drawer_name);
        drawer_name.setText(Info.getString("Name"));
        drawer_close_button = (ImageButton) view.findViewById(R.id.drawer_close_button);    //drawer_layout ?????? ?????? ??????
        drawer_logoutButton = (ImageButton) view.findViewById(R.id.drawer_logout_button);
        drawerView = (View) view.findViewById(R.id.drawerView);                     //drawer_layout ?????? ????????? ??? ??????
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);       //??????????????? drawer_layout ????????? ????????? ??????
        DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                drawer_close_button.setOnClickListener(new View.OnClickListener() { //drawer_layout ?????? ??? ???????????? ???????????????
                    @Override
                    public void onClick(View v) {
                        drawerLayout.closeDrawer(drawerView);               //drawer_layout ????????? ??????
                    }
                });
                drawer_logoutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logoutAct();
                    }
                });
            }
            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }
            @Override
            public void onDrawerStateChanged(int newState) {
            }
        };
        drawerLayout.setDrawerListener(listener);                   //drawer_layout ????????? ??????

        financial_viewPager = (ViewPager2)view.findViewById(R.id.home_financial_viewpager);     //viewpager ??????
        financial_indicator = (LinearLayout)view.findViewById(R.id.home_financial_indicator);   //view indicator ??????
        financial_viewPager.setOffscreenPageLimit(1);                       //view pager ?????? ???????????? ??? 1??? ??????
        financial_viewPager.setAdapter(new viewPager_Adapter(view.getContext(),financial_ImageUrl));   //RecyclerViews ????????? ??????
        financial_viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {      //viewpager ????????? ?????? ?????????
                super.onPageSelected(position);
                setCurrentIndicator(position,financial_indicator);      //indicator ?????? ????????? ??????
            }
        });
        setUpIndicator(financial_ImageUrl.length,financial_indicator);      //indicator ?????? ??????

        event_viewPager = (ViewPager2) view.findViewById(R.id.home_event_viewpager);    //?????? ????????? ??????
        event_indicator = (LinearLayout)view.findViewById(R.id.home_event_indicator);
        event_viewPager.setOffscreenPageLimit(1);
        event_viewPager.setAdapter(new viewPager_Adapter(view.getContext(),event_ImageUrl));
        event_viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position,event_indicator);
            }
        });
        setUpIndicator(event_ImageUrl.length,event_indicator);

        Transaction_history_button = (Button) view.findViewById(R.id.Transaction_history_button);
        remittance_button = (Button) view.findViewById(R.id.remittance_button);
        Transaction_history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent history_intent = new Intent(view.getContext(),remittance_history_activity.class);
                history_intent.putExtra("addressName",Info.getString("addressName"));
                history_intent.putExtra("Address_hyphen",address_builder.toString());
                history_intent.putExtra("Money",amount.getText());
                history_intent.putExtra("Address",Info.getString("Address"));
                history_intent.putExtra("ID",Info.getString("ID"));
                startActivity(history_intent);
            }
        });
        remittance_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int limit = Info.getInt("Limit") - Info.getInt("curLimit");
                Intent remittance_intent = new Intent(view.getContext(),remittance_activity.class);
                remittance_intent.putExtra("Address",Info.getString("Address"));
                remittance_intent.putExtra("Address_hyphen",address_builder.toString());
                remittance_intent.putExtra("Limit",limit);
                remittance_intent.putExtra("ID",Info.getString("ID"));
                remittance_intent.putExtra("Name",Info.getString("Name"));
                remittance_intent.putExtra("Money",amount.getText());
                remittance_intent.putExtra("Email",Info.getString("Email"));
                startActivity(remittance_intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getCurMoney curMoney =new getCurMoney();
        curMoney.execute("http://" + IP_ADDRESS+ "/bank/getCurMoney.php",Info.getString("ID"));
    }

    private void setUpIndicator(int count, LinearLayout indicator_layout){      //indicator ????????????
        ImageView[] indicator = new ImageView[count];           //Url ???????????? indicator ??????
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);   //indicator ?????? ??????
        params.setMargins(16,8,16,8);               //indicator margin ??????
        for(int i=0;i<indicator.length;i++){            //indicator ???????????? ?????? ??? ??????
            indicator[i] = new ImageView(view.getContext());        
            indicator[i].setImageDrawable(ContextCompat.getDrawable(view.getContext(),R.drawable.indicator_inactive));
            indicator[i].setLayoutParams(params);
            indicator_layout.addView(indicator[i]);
        }
        setCurrentIndicator(0,indicator_layout);        //indicator ?????? ??? 0????????? ??????
    }
    private void setCurrentIndicator(int position,LinearLayout indicator){      //?????? indicator ???????????? ??????
        int childCount = indicator.getChildCount();         //indicator ?????? ?????? ????????????
        for(int i=0;i<childCount;i++){
            ImageView imageView = (ImageView)indicator.getChildAt(i);
            if(i==position){    //indicator ?????? ???????????? ?????? ?????? ?????? ????????????
                imageView.setImageDrawable(ContextCompat.getDrawable(view.getContext(),R.drawable.indicator_active));
            }else{
                imageView.setImageDrawable(ContextCompat.getDrawable(view.getContext(),R.drawable.indicator_inactive));
            }
        }
    }
    public void logoutAct(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
        dialog.setTitle("???????????? ???????????????????");
        dialog.setPositiveButton("????????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent login = new Intent(view.getContext(),login_activity.class);
                getActivity().finishAffinity();
                startActivity(login);
            }
        });
        dialog.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    protected class getCurMoney extends AsyncTask<String,Void,String>{
        ProgressDialog progressDialog;
        String errMsg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(view.getContext(),"Pleases Wait",null,
                    true, true);
        }

        @Override
        protected String doInBackground(String... strings) {
            String databaseIP = strings[0];
            String ID=strings[1];
            String PostValue = "ID=" + ID;
            try {
                URL url = new URL(databaseIP);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(PostValue.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int httpRequestCode = httpURLConnection.getResponseCode();
                InputStream inputStream;
                if(httpRequestCode == HttpURLConnection.HTTP_OK){
                    inputStream = httpURLConnection.getInputStream();
                }else{
                    inputStream = httpURLConnection.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;
                while((line=bufferedReader.readLine())!=null){
                    sb.append(line);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return sb.toString();
            }catch (Exception e){
                errMsg=e.toString();
                return errMsg;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JsonString = result;
            getMoney();
            progressDialog.dismiss();
        }
        protected void getMoney(){
            String Tag_JSON = "money";
            String Tag_money = "money";
            try {
                JSONObject jsonObject = new JSONObject(JsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(Tag_JSON);
                JSONObject value = jsonArray.getJSONObject(0);
                amount.setText(value.getString(Tag_money));
            }catch (Exception e){
                Log.d("PHP", "????????????"+e);
            }
        }
    }
}
