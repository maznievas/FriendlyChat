package com.google.firebase.udacity.friendlychat.MainScreen;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.udacity.friendlychat.MessagesAdapter;
import com.google.firebase.udacity.friendlychat.R;
import com.google.firebase.udacity.friendlychat.entity.FriendlyMessage;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;


public class MainScreenFragment extends MvpAppCompatFragment implements MainScreenView {

    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    private static final String TAG = "mLog";

    @BindView(R.id.my_recycler_view)
    public RecyclerView messages;

    @BindView(R.id.sendButton)
    public Button sendButton;

    @InjectPresenter
    MainScreenPresenter presenter;

    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;

    private RecyclerView.LayoutManager mLayoutManager;
    private String mUsername;
    private Unbinder unbinder;
    private MessagesAdapter adapterRecyclerView;
    private ProgressDialog dialog;



    public MainScreenFragment() {
        // Required empty public constructor
    }

    public static MainScreenFragment newInstance() {
        return new MainScreenFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);
        unbinder = ButterKnife.bind(this, view);
        // Initialize references to views
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mPhotoPickerButton = (ImageButton) view.findViewById(R.id.photoPickerButton);
        mMessageEditText = (EditText) view.findViewById(R.id.messageEditText);

        init();

        return view;
    }

    public void init() {

        mUsername = ANONYMOUS;
        mLayoutManager = new LinearLayoutManager(getContext());
        messages.setLayoutManager(mLayoutManager);

        adapterRecyclerView = new MessagesAdapter();
        messages.setAdapter(adapterRecyclerView);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
            }
        });

        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        // Send button sends a message and clears the EditText
        RxView.clicks(sendButton)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        FriendlyMessage message = new FriendlyMessage(mMessageEditText.getText().toString(), mUsername, null);
                        presenter.sendMessage(message);

                        // Clear input box
                        mMessageEditText.setText("");
                    }
                });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
        presenter.clearMemory();
    }

    @Override
    public void updateMessages(FriendlyMessage message) {
        adapterRecyclerView.addMessage(message);
    }

    @Override
    public void showErrorMessage(String message) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    @Override
    public void showLoadingState() {
       // dialog.show();
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public void hideLoadingState() {
       // dialog.dismiss();
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
