package com.example.examen3_pmo189.Contructores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Contrc {

    public class contrc {
        public static Bitmap base64ToBitmap(String base64String) {
            byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
    }
}
