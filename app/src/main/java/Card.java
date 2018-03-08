package dependency.greendao.test.tinder.directional;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipeDirection;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeTouch;
import com.mindorks.placeholderview.annotations.swipe.SwipeView;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

@Layout(R.layout.card_view)
public class Card {

    @View(R.id.tweetView)
    TextView tweetView;

    @View(R.id.profileImageView)
    ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    TextView locationNameTxt;

    @SwipeView
    android.view.View mSwipeView;

    private Profile mProfile;
    private Context mContext;
    private Point mCardViewHolderSize;
    private Callback mCallback;

    public Card(Context context, Profile profile, Point cardViewHolderSize, Callback callback) {
        mContext = context;
        mProfile = profile;
        mCardViewHolderSize = cardViewHolderSize;
        mCallback = callback;
    }

    @Resolve
    public void onResolved() {
        Glide.with(mContext).load(mProfile.getImage()).into(profileImageView);
        tweetView.setText(mProfile.getTweet());
        nameAgeTxt.setText(mProfile.getName() + ", " + mProfile.getUsername());
        locationNameTxt.setText(mProfile.getLocation());
        mSwipeView.setAlpha(1);
    }

    @Click(R.id.profileImageView)
    public void onClick() {
        //Something
    }

    @SwipeOutDirectional
    public void onSwipeOutDirectional(SwipeDirection direction) {
        if (direction.getDirection() == SwipeDirection.TOP.getDirection()) {
            mCallback.onSwipeUp();
        }
    }

    @SwipeCancelState
    public void onSwipeCancelState() {
        mSwipeView.setAlpha(1);
    }


    @SwipeTouch
    public void onSwipeTouch(float xStart, float yStart, float xCurrent, float yCurrent) {

        float cardHolderDiagonalLength =
                (float) Math.sqrt(Math.pow(mCardViewHolderSize.x, 2) + (Math.pow(mCardViewHolderSize.y, 2)));
        float distance = (float) Math.sqrt(Math.pow(xCurrent - xStart, 2) + (Math.pow(yCurrent - yStart, 2)));

        float alpha = 1 - distance / cardHolderDiagonalLength;

        ((FrameLayout)mSwipeView).setAlpha(alpha);
    }

    //@SwipeOut
    private void onSwipedOut(){
        // Negative
    }

    //@SwipeIn
    private void onSwipeIn(){
        // Positive
    }

    interface Callback {
        void onSwipeUp();
    }
}