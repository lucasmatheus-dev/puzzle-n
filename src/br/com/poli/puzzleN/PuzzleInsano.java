package br.com.poli.puzzleN;

import java.util.Calendar;

public class PuzzleInsano extends Puzzle {
	private int tamanho;

	public PuzzleInsano(Jogador jogador,int k)  {
		super(jogador, Dificuldade.INSANO);
		CalculaScore temp = new CalculaInsano();
		this.setScore(temp);
		try {
			if(isKvalido(k)) {
			this.tamanho = k;
			}
		} catch (IndiceInvalidoException ex) {
			this.tamanho = 6;
			System.out.println(ex.getMessage());
		}
	}
	
	public int getTamanho() {
		return tamanho;
	}


	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	
		public boolean isKvalido(int k) throws IndiceInvalidoException{
			if (k <= 5 || k>=1000) {
				throw new IndiceInvalidoException("InvalidIndex InsanePuzzleSize Redirected to 6");
			}else {
				return  true;
			}
		}
	
	@Override
	public void iniciaPartida() {
		this.setQuantidadeMovimento(0);
		this.setTempo(Calendar.getInstance());
		this.setVenceu(false);
		this.setGridPuzzle(new Tabuleiro(this.getDificuldade(),this.tamanho));
		this.getGridPuzzle().geraTabuleiro();
	}
	
}
