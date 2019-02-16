package com.elhazent.picodiploma.myreadwritefile;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Helper {

    private static final String TAG = Helper.class.getName();
    static void writToFile(Item item, Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(item.getFilename(), Context.MODE_PRIVATE));
            outputStreamWriter.write(item.getData());
            outputStreamWriter.close();
        } catch (IOException e){
            Log.e(TAG,"File Write Failed " , e);
        }
    }

    static Item readFromFile(Context context, String filename){
        Item item = new Item();

        try {
            InputStream inputStream = context.openFileInput(filename);
            if (inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null){
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                item.setData(stringBuilder.toString());
                item.setFilename(filename);
            }

        } catch (FileNotFoundException e){
            Log.e(TAG,"File Not Found ", e);
        } catch (IOException e){
            Log.e(TAG, "Cannot Read File ", e);
        }
        return item;
    }
}
