package fr.epsi.i4.back.model.board;

public enum Direction {
	LEFT, RIGHT(LEFT), UP, DOWN(UP);

	private Direction opposite;

	Direction() {
		this.opposite = null;
	}

	Direction(Direction opposite) {
		this.opposite = opposite;
	}

	public Direction getOpposite() {
		Direction opposite = this.opposite;
		if (opposite == null) {
			int i = 0;
			while (i < values().length && opposite == null) {
				if (!values()[i].equals(this) && values()[i].getOpposite().equals(this)) {
					opposite = values()[i];
				}
				i++;
			}
		}
		return opposite;
	}

	public static Direction getByIndex(int index) {
		Direction direction = null;
		int i = 0;
		while (i < values().length && direction == null) {
			if (i == index) {
				direction = values()[i];
			}
			i++;
		}
		return direction;
	}
}
