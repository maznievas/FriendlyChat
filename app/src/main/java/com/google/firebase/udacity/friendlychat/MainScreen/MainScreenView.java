package com.google.firebase.udacity.friendlychat.MainScreen;

import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.firebase.udacity.friendlychat.entity.FriendlyMessage;

/**
 * Created by sts on 21.12.17.
 */

public interface MainScreenView extends MvpView {

    void updateMessages(FriendlyMessage message);
    @StateStrategyType(SkipStrategy.class)
    void showErrorMessage(String message);
    @StateStrategyType(SkipStrategy.class)
    void showLoadingState() ;
    @StateStrategyType(SkipStrategy.class)
    void hideLoadingState();

}
