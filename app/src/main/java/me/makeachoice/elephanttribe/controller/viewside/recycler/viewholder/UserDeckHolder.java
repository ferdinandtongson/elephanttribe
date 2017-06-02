package me.makeachoice.elephanttribe.controller.viewside.recycler.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;

import me.makeachoice.elephanttribe.R;
import me.makeachoice.elephanttribe.controller.viewside.navigation.boommenu.BuilderManager;
import me.makeachoice.elephanttribe.model.item.deck.DeckItem;

/**
 * UserDeckHolder is the viewHolder for user deck card layout
 */

public class UserDeckHolder extends RecyclerView.ViewHolder{

/**************************************************************************************************/
/*
 * Class Variables:
 */
/**************************************************************************************************/

    private CardView mCardView;

    private TextView mTxtName;
    private TextView mTxtCount;
    private ImageView mImgBoom;
    private BoomMenuButton mBmbMenu;

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public UserDeckHolder(View recyclerView){
        super(recyclerView);

        mCardView = (CardView)itemView.findViewById(R.id.choiceCardView);
        mTxtName = (TextView)itemView.findViewById(R.id.cardDeck_txtName);
        mTxtCount = (TextView)itemView.findViewById(R.id.cardDeck_txtCount);

        mImgBoom = (ImageView)itemView.findViewById(R.id.cardDeck_imgBoom);
        mBmbMenu = (BoomMenuButton)itemView.findViewById(R.id.choiceBoomMenuButton);
    }

    public void bindItemOnClickListener(DeckItem item, int position, View.OnClickListener listener){
        mCardView.setTag(R.string.tag_position, position);
        mCardView.setTag(R.string.tag_item, item);
        mCardView.setTag(R.string.tag_boom, mBmbMenu);
        mCardView.setOnClickListener(listener);

        mImgBoom.setTag(R.string.tag_position, position);
        mImgBoom.setTag(R.string.tag_item, item);
        mImgBoom.setTag(R.string.tag_boom, mBmbMenu);
        mImgBoom.setOnClickListener(listener);
    }

    public void bindTextView(DeckItem item){
        mTxtName.setText(item.deck);
        mTxtCount.setText("50");
    }

    public void bindBoomMenuButton(ArrayList<String> titles, ArrayList<String> subtitles,
                                   ArrayList<Integer> images, OnBMClickListener listener){
        BuilderManager bmManager = BuilderManager.getInstance();

        mBmbMenu.clearBuilders();
        for (int i = 0; i < mBmbMenu.getPiecePlaceEnum().pieceNumber(); i++){
            HamButton.Builder builder = bmManager.getElephantHamButtonBuilder(titles.get(i),
                    subtitles.get(i), images.get(i))
                    .listener(listener);

            mBmbMenu.addBuilder(builder);
        }

    }

/**************************************************************************************************/

}
