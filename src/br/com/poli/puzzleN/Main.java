package br.com.poli.puzzleN;
/*
 * Jean Felipe Coelho Ferreira 
 * Lucas Matheus da Silva Oliveira
 * 
 * Turma KW - Professor BRUNO 
 * 
 * Eclipse
 */

public class Main {
	public static void main(String[] args) {

		Puzzle teste = new PuzzleFacil(new Jogador("oioi"));
		Puzzle teste0 = new PuzzleFacil(new Jogador("naej e sacul"));
		Puzzle teste1 = new PuzzleMedio(new Jogador("luscas e jeanus"));
		Puzzle teste2 = new PuzzleDificil(new Jogador("matheus e felipe"));
		Puzzle teste3; 
		Puzzle teste4 = new PuzzleInsano(new Jogador("sofro"),15);

		
		System.out.println("------------------");
		System.out.println("TESTE PUZZLE FACIL");
		System.out.println("JOGADOR: " +teste.getJogador().getNome());
		System.out.println();
		// testa grid facil 
		teste.iniciaPartida();
		teste.getGridPuzzle().getPosicaoGrid(0, 0).setValor(1);
		teste.getGridPuzzle().getPosicaoGrid(0, 1).setValor(2);
		teste.getGridPuzzle().getPosicaoGrid(0, 2).setValor(3);
		teste.getGridPuzzle().getPosicaoGrid(1, 0).setValor(4);
		teste.getGridPuzzle().getPosicaoGrid(1, 1).setValor(0);
		teste.getGridPuzzle().getPosicaoGrid(1, 2).setValor(5);
		teste.getGridPuzzle().getPosicaoGrid(2, 0).setValor(7);
		teste.getGridPuzzle().getPosicaoGrid(2, 1).setValor(8);
		teste.getGridPuzzle().getPosicaoGrid(2, 2).setValor(6);
		teste.getGridPuzzle().setValidos();

		System.out.println(" ");
		teste.imprimeGrid();
		System.out.println();
		teste.imprimeGridValido();
		System.out.println();
		
		// faz o movimento invalido para mostrar o erro
		teste.fazMovimento(1, 1);
		System.out.println();
		System.out.println("Venceu? " + teste.isVenceu());
		System.out.println();
		System.out.println("quantidade de movimentos: "+ teste.getQuantidadeMovimento());
		System.out.println();
		System.out.println("------------------");
		System.out.println();
		
		teste.fazMovimento(1, 2 );
		teste.imprimeGrid();
		System.out.println();
		teste.imprimeGridValido();
		System.out.println("Venceu? " + teste.isVenceu());
		System.out.println();
		System.out.println("quantidade de movimentos: "+ teste.getQuantidadeMovimento());
		System.out.println();
		System.out.println("-----------------------");
		
		System.out.println(" ");
		teste.imprimeGrid();
		System.out.println();
		teste.imprimeGridValido();
		System.out.println();
		
		// tentar fazer um movimento fora do limite da matriz para mostrar um erro
		teste.fazMovimento(4, 3);
		System.out.println();
		System.out.println("Venceu? " + teste.isVenceu());
		System.out.println();
		System.out.println("quantidade de movimentos: "+ teste.getQuantidadeMovimento());
		System.out.println();
		System.out.println("------------------");
		
		//faz movimento para vencer o jogo
		teste.fazMovimento(2, 2);
		System.out.println(" ");
		teste.imprimeGrid();
		System.out.println();
		teste.imprimeGridValido();
		System.out.println();
		System.out.println();
		System.out.println("Venceu? " + teste.isVenceu());
		System.out.println();
		System.out.println("quantidade de movimentos: "+ teste.getQuantidadeMovimento());
		System.out.println();
		System.out.println("Score: "+ teste.getScore());
		System.out.println();
		System.out.println("------------------");
		
		//faz movimento depois de ter vencido para lançar o erro
		teste.fazMovimento(1, 2);
		System.out.println(" ");
		teste.imprimeGrid();			
		System.out.println();		
		teste.imprimeGridValido();
		System.out.println();
		System.out.println();
		System.out.println("Venceu? " + teste.isVenceu());
		System.out.println();
		System.out.println("quantidade de movimentos: "+ teste.getQuantidadeMovimento());
		System.out.println();
		System.out.println("Score: "+ teste.getScore());
		System.out.println();
		System.out.println("------------------");

	}
}