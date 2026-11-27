package br.com.poli.puzzleN;

public class PuzzleMedio extends Puzzle {

	public PuzzleMedio(Jogador jogador) {
		super(jogador, Dificuldade.NORMAL);
		CalculaScore temp = new CalculaMedio();
		this.setScore(temp);
	}

}
