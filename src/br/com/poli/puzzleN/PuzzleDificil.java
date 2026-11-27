package br.com.poli.puzzleN;

public class PuzzleDificil extends Puzzle{

	public PuzzleDificil(Jogador jogador) {
		super(jogador, Dificuldade.DIFICIL);
		CalculaScore temp = new CalculaDificil();
		this.setScore(temp);
	}

}
