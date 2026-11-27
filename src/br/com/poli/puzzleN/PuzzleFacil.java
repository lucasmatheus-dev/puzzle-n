package br.com.poli.puzzleN;

public class PuzzleFacil extends Puzzle{

	public PuzzleFacil(Jogador jogador) {
		super(jogador, Dificuldade.FACIL);
		CalculaScore temp = new CalculaFacil();
		this.setScore(temp);
		
	}

}
