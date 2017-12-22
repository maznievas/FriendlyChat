package com.google.firebase.udacity.friendlychat.MainScreen;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.udacity.friendlychat.entity.FriendlyMessage;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sts on 21.12.17.
 */

@InjectViewState
public class MainScreenPresenter extends MvpPresenter<MainScreenView> {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private CompositeDisposable compositeDisposable;
    private ChildEventListener childEventListener;

    public MainScreenPresenter() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("message");
        compositeDisposable = new CompositeDisposable();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FriendlyMessage message = dataSnapshot.getValue(FriendlyMessage.class);
                getViewState().updateMessages(message);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }

    public void sendMessage(final FriendlyMessage message) {
        compositeDisposable.add(Flowable.just(new Object())
                .subscribeOn(Schedulers.io())
                .map(o -> {
                    databaseReference.push().setValue(message);
                    return o;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                  //  getViewState().updateMessages(message);
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().showErrorMessage("Try again.");
                }));
    }

    public void clearMemory()
    {
        compositeDisposable.clear();
    }
}
