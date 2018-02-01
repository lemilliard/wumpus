package fr.epsi.i4.nao.front;

import java.io.File;

public enum FrontContent {
	AGENT("agent.png"), //
	GOLD("gold.png"), //
	PIT("pit.png"), //
	WUMPUS("wumpus.png"), //
	BREEZE("breeze.png"), //
	STENCH("mouche.jpg");

	private static final String filePath = "./img/";

	private String fileName;

	FrontContent(String fileName) {
		this.fileName = fileName;
	}

	public static FrontContent getFrontContentByName(String name) {
		for (FrontContent frontContent : values()) {
			if (frontContent.toString().equals(name)) {
				return frontContent;
			}
		}
		return null;
	}

	public String getFileName() {
		return fileName;
	}

	public File getFile() {
		File file = new File(filePath + fileName);
		if (file.exists()) {
			return file;
		}
		return null;
	}
}