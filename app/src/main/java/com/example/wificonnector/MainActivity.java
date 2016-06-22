
package com.example.wificonnector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File path = Environment.getExternalStorageDirectory();
        String txtPath = path.toString() + File.separator + "wifi_config.txt";
        File wifiFile = new File(txtPath);
        if(! wifiFile.exists()){
            Log.e("WIFI_TEST", "NO FILE");
            try {
               boolean success = wifiFile.createNewFile();
               writeFileSdcardFile(wifiFile.toString(), "SmartHome, biaoyangwo");
            } catch (IOException e) {
                Log.e("WIFI_TEST", "NO FILE errrrrror");
            }
        } else {
            Log.e("WIFI_TEST", "has FILE");
            try {
                String wifiInfo = readFileSdcardFile(wifiFile.toString());
            String[] wifiInfoArrah = wifiInfo.split(",");
            if(wifiInfoArrah.length > 1){
                WifiAutoConnectManager manager = new WifiAutoConnectManager((WifiManager)getSystemService(WIFI_SERVICE));
                manager.connect(wifiInfoArrah[0], wifiInfoArrah[1], WifiAutoConnectManager.getCipherType(this, wifiInfoArrah[0]));
            }
        } catch (IOException e) {
            Log.e("WIFI_TEST", "FILE READ errrrrror");
        }
        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidMan  ifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
  //写数据到SD中的文件
    public void writeFileSdcardFile(String fileName,String write_str) throws IOException{ 
     try{ 

           FileOutputStream fout = new FileOutputStream(fileName); 
           byte [] bytes = write_str.getBytes(); 

           fout.write(bytes); 
           fout.close(); 
         }

          catch(Exception e){ 
            e.printStackTrace(); 
           } 
       } 

      
    //读SD中的文件
    public String readFileSdcardFile(String fileName) throws IOException{ 
      String res=""; 
      try{ 
             FileInputStream fin = new FileInputStream(fileName); 

             int length = fin.available(); 

             byte [] buffer = new byte[length]; 
             fin.read(buffer);     

             res = new String(buffer, "UTF-8"); 

             fin.close();     
            } 

            catch(Exception e){ 
             e.printStackTrace(); 
            } 
            return res; 
    } 
}
