package fr.epsi.i4.back.model;

import fr.decisiontree.model.Result;
import fr.epsi.i4.back.model.board.Direction;

/**
 * Created by tkint on 16/02/2018.
 */
public class PossibleChoice {

	private Result result;

	private Direction choice;

	public PossibleChoice(Result result, Direction choice) {
		this.result = result;
		this.choice = choice;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Direction getChoice() {
		return choice;
	}

	public void setChoice(Direction choice) {
		this.choice = choice;
	}
}
