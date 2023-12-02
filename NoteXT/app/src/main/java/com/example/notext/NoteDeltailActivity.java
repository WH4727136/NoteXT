package com.example.notext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class NoteDeltailActivity extends AppCompatActivity {

    EditText titleEditText, ContentEditText;
    ImageButton saveNoteButton;
    TextView pagetilte;
    String title, content, docId;
    boolean isEditmode = false;
    TextView deletenotebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_deltail);

        titleEditText = findViewById(R.id.notes_editText_title);
        ContentEditText = findViewById(R.id.notes_editText_content);
        saveNoteButton = findViewById(R.id.btnsave);
        pagetilte = findViewById(R.id.title);
        deletenotebtn = findViewById(R.id.btndelete);

        title  = getIntent().getStringExtra("title");
        content  = getIntent().getStringExtra("content");
        docId  = getIntent().getStringExtra("docId");

        if(docId!=null && !docId.isEmpty()) {
            isEditmode = true;
        }

        titleEditText.setText(title);
        ContentEditText.setText(content);

        if(isEditmode) {
            pagetilte.setText("Sửa ghi chú");
            deletenotebtn.setVisibility(View.VISIBLE);
        }

        saveNoteButton.setOnClickListener((v) -> saveNote());

        deletenotebtn.setOnClickListener((v)->deleteNoteFromFB());

    }

    private void deleteNoteFromFB() {
        DocumentReference documentReference;

            documentReference = Utility.getCollectionReferenceForNote().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Utility.showToast(NoteDeltailActivity.this,"Đã xóa thành công");
                    finish();
                } else {
                    Utility.showToast(NoteDeltailActivity.this,"Có lỗi trong quá trình xóa, vui lòng thử lại");
                }
            }
        });
    }

    private void saveNote() {
        String noteTitle = titleEditText.getText().toString();
        String noteContent = ContentEditText.getText().toString();
        if (noteTitle == null || noteTitle.isEmpty()) {
            titleEditText.setError("Tiêu đề không được trống");
            return;
        }

        Note note = new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());
        
        saveNotetoFB(note);
    }

    private void saveNotetoFB(Note note) {
        DocumentReference documentReference;
        if(isEditmode) {
            documentReference = Utility.getCollectionReferenceForNote().document(docId);
        } else {
            documentReference = Utility.getCollectionReferenceForNote().document();
        }

        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Utility.showToast(NoteDeltailActivity.this,"Đã lưu thành công");
                    finish();
                } else {
                    Utility.showToast(NoteDeltailActivity.this,"Có lỗi trong quá trình lưu, vui lòng thử lại");
                }
            }
        });
    }
}