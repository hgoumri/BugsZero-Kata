package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {

	
	//[Hamza GOUMRI] :  Ajouter private dans tous les attributs de la classe , et pour les tableaux
     ArrayList players = new ArrayList();
	
		// [Hamza GOUMRI] : Ajouter une variable static mieux que les déclater directement 
     public static final int initialisation = 6;
     public static final String ROCKQUESTION= "Rock Question ";
     public static final String POPQUESTION= "Pop Question ";
     public static final String SCIENCEQUESTION= "Science Question ";
     public static final String SPORTQUESTION= "Sports Question ";
		
	// [Hamza GOUMRI] : Remplacer le 6 par la static final déclaré au dévu
    int[] places = new int[initialisation];
    int[] purses  = new int[initialisation];
    boolean[] inPenaltyBox  = new boolean[initialisation];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
	// [Hamza GOUMRI] : [Hamza GOUMRI] : Ajouter les getters et les setters des attributs de la classe
	
	// [Hamza GOUMRI] : Remplacer le pop;Sport,Scence Question par les static final déclarés au début
    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast(POPQUESTION + i);
			scienceQuestions.addLast((SCIENCEQUESTION + i));
			sportsQuestions.addLast((SPORTQUESTION + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }
	
// [Hamza GOUMRI] : Remplacer le Rock Question par la static final déclaré au début
	public String createRockQuestion(int index){
		return ROCKQUESTION + index;
	}

	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
		
		
	    players.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				movePlayerAndAskQuestion(roll);
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {

			movePlayerAndAskQuestion(roll);
		}
		
	}

	private void movePlayerAndAskQuestion(int roll) {
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

		System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	//[Hamza GOUMRI] : utiliser les Switch case mieux que les if pour une qualité de code
	private void askQuestion() {
		if (currentCategory() == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());		
	}
	
	//[Hamza GOUMRI] : utiliser les Switch case mieux que les if pour une qualité de code
	private String currentCategory() {
		if (places[currentPlayer] == 0) return "Pop";
		if (places[currentPlayer] == 4) return "Pop";
		if (places[currentPlayer] == 8) return "Pop";
		if (places[currentPlayer] == 1) return "Science";
		if (places[currentPlayer] == 5) return "Science";
		if (places[currentPlayer] == 9) return "Science";
		if (places[currentPlayer] == 2) return "Sports";
		if (places[currentPlayer] == 6) return "Sports";
		if (places[currentPlayer] == 10) return "Sports";
		return "Rock";
	}
// [Hamza GOUMRI] : Cette méthode a changer a cause mauvaise manip 
	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				purses[currentPlayer]++;
				
				System.out.println(players.get(currentPlayer)
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");

		              boolean winner = didPlayerWin();
				 if (currentPlayer == players.size())
                                    currentPlayer = 0;
                             
				
	return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return (purses[currentPlayer] =! initialisation);
	}
}
