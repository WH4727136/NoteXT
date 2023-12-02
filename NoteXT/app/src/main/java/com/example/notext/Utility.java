package com.example.notext;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class Utility {
    static CollectionReference getCollectionReferenceForNote() {
        return FirebaseFirestore.getInstance().collection("notes");
    }

    public static void showToast(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    static String timeStampToString (Timestamp timestamp) {
        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
    }
}
