package org.camunda.bpmn.quest.CharacterCreator;

import java.util.ArrayList;
import java.util.List;

public class FightResult {

	private CharacterModel winner;
	private CharacterModel loser;
	
	private int rounds;
	
	public int getRounds() {
		return rounds;
	}

	public void setRounds(int rounds) {
		this.rounds = rounds;
	}

	private List<String> protocol = new ArrayList<String>();

	public CharacterModel getWinner() {
		return winner;
	}

	public void setWinner(CharacterModel winner) {
		this.winner = winner;
	}

	public CharacterModel getLoser() {
		return loser;
	}

	public void setLoser(CharacterModel loser) {
		this.loser = loser;
	}

	public List<String> getProtocol() {
		return protocol;
	}

	public void setProtocol(List<String> protocol) {
		this.protocol = protocol;
	}
}
