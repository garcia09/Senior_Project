package com.example.enchantedpetsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;
import com.amazonaws.regions.Regions;
import com.example.enchantedpetsapp.databinding.ActivityMainBinding;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

//public class MQTT_MANAGER extends AppCompatActivity {
public class MQTT_MANAGER extends AppCompatActivity {

    ActivityMainBinding binding;

    private static final String CUSTOMER_SPECIFIC_IOT_ENDPOINT = "ap9bgs3gweked-ats.iot.us-west-1.amazonaws.com";

    private static final String COGNITO_POOL_ID = "us-west-1:4c05e995-41b5-45c8-8758-21edc190a1ce";

    private static final Regions MY_REGION = Regions.US_WEST_1;

    AWSIotMqttManager mqttManager;
    String clientId;
    CognitoCachingCredentialsProvider credentialsProvider;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public void connection(){

        System.out.println("-------------------------INSIDE CONNECTION()---------------------------------------");

        clientId = UUID.randomUUID().toString();

        credentialsProvider = new CognitoCachingCredentialsProvider(
                this.getApplicationContext(), // context
                COGNITO_POOL_ID, // Identity Pool ID
                MY_REGION // Region
        );

        mqttManager = new AWSIotMqttManager(clientId, CUSTOMER_SPECIFIC_IOT_ENDPOINT);

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();
        mqttManager.connect(credentialsProvider, new AWSIotMqttClientStatusCallback() {
            @Override
            public void onStatusChanged(AWSIotMqttClientStatus status, Throwable throwable) {

            }
        });

        System.out.println("-------------------------------Connected----------------------------");


        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------------------------------Publishing topic----------------------------");
        mqttManager.publishString("toggle","picow/led", AWSIotMqttQos.QOS1);
    }





}

