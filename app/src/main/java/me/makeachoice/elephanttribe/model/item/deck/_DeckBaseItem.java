package me.makeachoice.elephanttribe.model.item.deck;

/**
 * _DeckBaseItem
 */

public class _DeckBaseItem {

    //deck info
    public String deck;
    public String description;
    public int cardCount;

    //creator info
    public String userId;
    //todo - need to add creatorId
    public String creator;
    public String profilePic;

    //deck status
    public int cost;            //currency $, in pennies
    public boolean isFree;      //free or premium
    public boolean isPublic;    //public or private
    public double rating;
    public int active;
    public int download;

    //deck filter
    public String subject;
    public String school;
    public String classId;
    public String teacher;
}
