package com.example.thtuan3_nguyenleminh.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class Img implements Serializable {
    public Bitmap bitmap;

    public Img(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        out.writeInt(byteArray.length);
        out.write(byteArray);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        int bufferLength = in.readInt();
        byte[] byteArray = new byte[bufferLength];
        int pos = 0;
        do {
            int read = in.read(byteArray, pos, bufferLength - pos);
            if (read != -1) {
                pos += read;
            } else {
                break;
            }
        } while (pos < bufferLength);
        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, bufferLength);
    }
}
