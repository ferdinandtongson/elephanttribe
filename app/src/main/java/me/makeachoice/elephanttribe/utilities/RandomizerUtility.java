package me.makeachoice.elephanttribe.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.makeachoice.elephanttribe.model.item.flashcard.FlashcardItem;

/**
 * RandomizerUtility provides randomizer methods used to shuffle flashcard deck
 */

public class RandomizerUtility {

    private static int SAFETY_MAX = 50;

/**************************************************************************************************/
/*
 * Randomize List Order:
 *      HashMap<Integer,Integer> randomizeOrder(...) - create random order list
 *      HashMap<Integer,Integer> finishRandomOrder(...) - complete randomizing list
 */
/**************************************************************************************************/
    /*
     * HashMap<Integer,Integer> randomizeOrder(...) - create random order list
     */
    public static HashMap<Integer,Integer> randomizeOrder(int size){
        //create hashMap buffer for random list
        HashMap<Integer,Integer> randomOrder = new HashMap<>();

        //zero safety counter
        int safety = 0;

        //zero counter
        int counter = 0;

        //initialize randomized status flag
        boolean randomized = false;

        //loop until counter is equal to size or safety counter exceeds max
        while(counter < size && safety < SAFETY_MAX){
            //get randomizer object
            Random random = new Random();

            //get random number (0 to size-1)
            int index = random.nextInt(size);

            //check if index has already been saved
            if(!randomOrder.containsValue(index)){
                //not saved, add index to randomized list
                randomOrder.put(counter, index);

                //increment counter
                counter++;

                //zero safety counter
                safety = 0;
            }
            else{
                //already saved, increment safety counter
                safety++;
            }

            //check if counter is equal to list size
            if(counter == size){
                //yes, complete list randomized
                randomized = true;
            }
        }

        //check if complete list randomized
        if(!randomized){
            //not completely randomized, complete randomization and return list
            return finishRandomOrder(randomOrder, size);
        }

        //list randomized, return list
        return randomOrder;
    }

    /*
     * HashMap<Integer,Integer> finishRandomOrder(...) - complete randomizing list
     */
    private static HashMap<Integer,Integer> finishRandomOrder(HashMap<Integer,Integer> randomOrder, int size){
        //get hashMap size
        int mapSize = randomOrder.size();

        //loop through order list size
        for(int i = 0; i < size; i++){

            //check if index is in random order hashMap
            if(!randomOrder.containsValue(i)){
                //not in map, add index to map
                randomOrder.put(mapSize,i);

                //increment map size
                mapSize++;
            }
        }

        //return random ordered hashMap
        return randomOrder;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Randomly Select Distractors:
 */
/**************************************************************************************************/

    public static ArrayList<FlashcardItem> selectDistractors(FlashcardItem flashcardItem, int numOfDistractors,
                                                             ArrayList<FlashcardItem> cardList){
        //initialize distractor list
        ArrayList<FlashcardItem> distractorList = new ArrayList<>();

        //copy card list to distractor list
        distractorList.addAll(cardList);

        //initialize selected distractor list
        ArrayList<FlashcardItem> selectedList = new ArrayList<>();

        //add flashcard to distractor list
        selectedList.add(flashcardItem);

        //get size of selected distractor list
        int selectCount = selectedList.size();

        int safety = 0;

        //initialize selection completed status flag
        boolean selectComplete = false;

        //loop until counter is equal to # of Distractors or safety counter exceeds max
        while(selectCount < numOfDistractors && safety < SAFETY_MAX){
            //get size of list
            int size = cardList.size();

            //get randomizer object
            Random random = new Random();

            //get random number (0 to size-1)
            int index = random.nextInt(size);

            //get randomly selected distractor
            FlashcardItem newDistractor = distractorList.get(index);

            //check if distractor is valid
            if(validDistractor(newDistractor, selectedList)){
                //is valid, add distractor to selected list
                selectedList.add(newDistractor);

                //increment counter
                selectCount = selectedList.size();

                //zero safety counter
                safety = 0;
            }
            else{
                //already saved, increment safety counter
                safety++;
            }

            //check if counter is equal to number of request distractors
            if(selectCount == numOfDistractors){
                //yes, complete list
                selectComplete = true;
            }
        }

        //check if distractor selection is complete
        if(!selectComplete){
            return finishDistractorSelection(distractorList, selectedList, numOfDistractors);
        }

        return selectedList;
    }


    /*
     *
     */
    private static ArrayList<FlashcardItem> finishDistractorSelection(ArrayList<FlashcardItem> distractors,
                                                                      ArrayList<FlashcardItem> selected,
                                                                      int numOfDistractors){
        //get # of selected distractors
        int selectCount = selected.size();

        //get # of distractors
        int disCount = distractors.size();

        //loop through order list size
        for(int i = 0; i < disCount; i++){

            FlashcardItem item = distractors.get(i);

            if(validDistractor(item, selected)){
                selected.add(item);

                if(selected.size() == numOfDistractors){
                    return selected;
                }
            }
        }

        return selected;
    }


    private static boolean validDistractor(FlashcardItem item, ArrayList<FlashcardItem> distractors){
        //get size of selected distractors
        int count = distractors.size();

        //loop through selected distractors
        for(int i = 0; i < count; i++){
            //get distractor
            FlashcardItem disItem = distractors.get(i);

            //check if flashcard or answer are equal
            if(disItem.card.equalsIgnoreCase(item.card) ||
                    disItem.answer.equalsIgnoreCase(item.answer)){

                //equal, return false
                return false;
            }
        }

        //valid distractor, return true
        return true;
    }

/**************************************************************************************************/


/**************************************************************************************************/
/*
 * Constructor:
 */
/**************************************************************************************************/

    public static HashMap<Integer,String> generateDistractors(ArrayList<String> choices){
        HashMap<Integer,String> distractors = new HashMap<>();

        int range = choices.size();
        int choiceCounter = 0;
        boolean randomized = false;
        int safety = 0;

        while(!randomized && safety < SAFETY_MAX){
            Random random = new Random();
            int index = random.nextInt(range);

            if(!distractors.containsKey(index)){
                distractors.put(index, choices.get(choiceCounter));
                choiceCounter++;
                safety = 0;
            }
            else{
                safety++;
            }

            if(choiceCounter == range){
                randomized = true;
            }
        }

        if(!randomized){
            return finishRandomizer(distractors, choices, choiceCounter);
        }

        return distractors;
    }

    private static HashMap<Integer,String> finishRandomizer(HashMap<Integer,String> distractor,
                                                            ArrayList<String> choices, int choiceCounter){
        int count = choices.size();

        for(int i = 0; i < count; i++){
            if(!distractor.containsKey(i)){
                distractor.put(i, choices.get(choiceCounter));
                choiceCounter++;
            }
        }

        return distractor;
    }

/**************************************************************************************************/


}
