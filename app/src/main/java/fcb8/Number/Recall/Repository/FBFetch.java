package fcb8.Number.Recall.Repository;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class FBFetch {
    private final DocumentReference docRef;

    public FBFetch() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String FSCollection = "fcb8NumberRecall";
        String FSDocument = "fcb8NumberRecall2";
        docRef = db.collection(FSCollection).document(FSDocument);
    }

    public void addSnapshotListener(EventListener<DocumentSnapshot> eventListener) {
        docRef.addSnapshotListener(eventListener);
    }
    private String getStringField (DocumentSnapshot value, String field) {
        Object fieldValue = value.get(field);
        return (fieldValue != null) ? fieldValue.toString() : "";
    }
    public String getFSStatus(DocumentSnapshot value) {
        return getStringField(value, "q");
    }
    public String getUrl(DocumentSnapshot value) {
        return getStringField(value, "w");
    }
    public String getBaseLink(DocumentSnapshot value) {
        return getStringField(value, "e");
    }
    public String getURLStatus(DocumentSnapshot value) {
        return getStringField(value, "a");
    }



    public String getURLEquals1(DocumentSnapshot value) {
        return getStringField(value,"URLEquals1");
    }
    public String getURLEquals2(DocumentSnapshot value) {
        return getStringField(value,"URLEquals2");
    }
    public String getURLContains1(DocumentSnapshot value) {
        return getStringField(value,"URLContains1");
    }
    public String getURLContains2(DocumentSnapshot value) {
        return getStringField(value,"URLContains2");
    }
    public String getURLContains3(DocumentSnapshot value) {
        return getStringField(value,"URLContains3");
    }
    public String getURLContains4(DocumentSnapshot value) {
        return getStringField(value,"URLContains4");
    }
    public String getURLContains5(DocumentSnapshot value) {
        return getStringField(value,"URLContains5");
    }

}
