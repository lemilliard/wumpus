package fr.epsi.i4.back.model.board;

public enum Direction {
	UP, DOWN(UP), LEFT, RIGHT(LEFT);

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
}
