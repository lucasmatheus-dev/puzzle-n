package br.com.poli.puzzleN;

import java.util.Random;

public class Tabuleiro {

	private Bloco[][] grid;
	//atributo para armazenar os numeros em suas determinadas posicoes certas
	private int[][] posicaoCerta;

	public Tabuleiro(Dificuldade dificuldade) {
		int contador = 1;
		int tamanhoMatriz;
		tamanhoMatriz = (int) (Math.sqrt(dificuldade.getValor() + 1));
		this.grid = new Bloco[tamanhoMatriz][tamanhoMatriz];
		posicaoCerta = new int[grid.length][grid.length];
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid.length; j++) {
				this.grid[i][j] = new Bloco();
				this.grid[i][j].setValor(contador);
				posicaoCerta[i][j]=contador;
				contador++;
				if (i == j && j == this.grid.length - 1) {
					this.grid[i][j].setValor(0);
				posicaoCerta[i][j]=contador;
				}
			}
		}
		setValidos();
	}

	public Tabuleiro(Dificuldade dificuldade, int k) {
		int contador = 1;
		this.grid = new Bloco[k][k];
		posicaoCerta = new int[grid.length][grid.length];
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid.length; j++) {
				this.grid[i][j] = new Bloco();
				this.grid[i][j].setValor(contador);
				posicaoCerta[i][j]=contador;
				contador++;
				if (i == j && j == this.grid.length - 1) {
				this.grid[i][j].setValor(0);
				posicaoCerta[i][j]=0;
				}
			}
		}
		setValidos();
	}
	
	public int getPosicaoCerta(int i, int j) {
		return this.posicaoCerta[i][j];
	}

	public Bloco[][] getGrid() {
		return grid;
	}

	public void setGrid(Bloco[][] grid) {
		this.grid = grid;
	}

	public Bloco getPosicaoGrid(int i, int j) {
		return this.grid[i][j];
	}

	public int getTamanhoGrid() {
		return this.grid.length;
	}

	public int getValorGrid(int i, int j) {
		return this.grid[i][j].getValor();
	}

	// metodo que troca a posicao do 0 com a posicao escolhida, se o movimento for
	// possível
	public boolean executaMovimento(int linha, int coluna) {
		
		try {
			isMovimentoValido(linha, coluna);
		} catch (MovimentoInvalidoException ex) {
			System.out.println(ex.getMessage());
			return false;
		} catch (IndiceInvalidoException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		int x = 0;
		int y = 0;
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid.length; j++) {
				if(this.grid[i][j].getValor()==0) {
					x=i;
					y=j;
				}
			}
		}
		if (linha-1 == x) {
			this.grid[linha - 1][coluna].setValor(this.grid[linha][coluna].getValor());
			this.grid[linha][coluna].setValor(0);
			setValidos();
		} else if (linha+1 == x) {
			this.grid[linha + 1][coluna].setValor(this.grid[linha][coluna].getValor());
			this.grid[linha][coluna].setValor(0);
			setValidos();
		} else if (coluna+1==y) {
			this.grid[linha][coluna + 1].setValor(this.grid[linha][coluna].getValor());
			this.grid[linha][coluna].setValor(0);
			setValidos();
		} else if (coluna-1 == y) {
			this.grid[linha][coluna - 1].setValor(this.grid[linha][coluna].getValor());
			this.grid[linha][coluna].setValor(0);
			setValidos();
		}

		return true;

	}

	// seta true nas casas que podem ser movidas de acordo com a posicao do zero
	public final void setValidos() {
		int j;
		for (int i = 0; i < this.grid.length; i++) {
			for (j = 0; j < this.grid.length; j++) {
				this.grid[i][j].setValido(false);
			}
		}
		for (int i = 0; i < this.grid.length; i++) {

			for (j = 0; j < this.grid.length; j++) {
				if (this.grid[i][j].getValor() == 0) {
					if (i + 1 < this.grid.length) {
						this.grid[i + 1][j].setValido(true);
					}
					if (i - 1 >= 0) {
						this.grid[i - 1][j].setValido(true);
					}
					if (j + 1 < this.grid.length) {
						this.grid[i][j + 1].setValido(true);
					}
					if (j - 1 >= 0) {
						this.grid[i][j - 1].setValido(true);
					}
				}
			}
			if (this.grid[i][j - 1].getValor() == 0)
				break;
		}

	}

	public boolean isTabuleiroOrdenado() {
		// variavel que armazena a diferenca entre linhas
		int contador = 1;
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid.length; j++) {
				if (i == j && i == this.grid.length - 1) {
					if (this.grid[i][j].getValor() != 0) {
						return false;
					}
				} else if (this.grid[i][j].getValor() != i + j + contador) {
					return false;
				}
			}
			contador += (this.grid.length - 1);
		}
		return true;
	}

	public void geraTabuleiro() {
		Random random = new Random();
		int randomico;
		int y[], z[];
		int contador;
		int contador1;

		rotulo: while (true) {
			// faz mil embaralhamentos vezes o tamanho da grid
			for (int u = 0; u < 1000 * this.grid.length; u++) {
				contador = 0;
				contador1 = 0;
				for (int i = 0; i < this.grid.length; i++) {
					for (int j = 0; j < this.grid.length; j++) {
						if (this.grid[i][j].isValido()) {
							contador++;
						}
					}
				}

				y = new int[contador];
				z = new int[contador];

				for (int i = 0; i < this.grid.length; i++) {
					for (int j = 0; j < this.grid.length; j++) {
						if (this.grid[i][j].isValido()) {
							y[contador1] = i;
							z[contador1] = j;
							contador1++;
						}
					}
				}

			

				randomico = random.nextInt(contador);
				executaMovimento(y[randomico], z[randomico]);

					}
				

			

			if (!isTabuleiroOrdenado()) {
				break rotulo;
			}
		}

	}

	

	public boolean isMovimentoValido(int linha, int coluna)
			throws MovimentoInvalidoException, IndiceInvalidoException {
		if (linha < this.grid.length && linha >= 0 && coluna < this.grid.length && coluna >= 0) {
			if (this.grid[linha][coluna].isValido()) {
				return true;
			} else {
				throw new MovimentoInvalidoException("Movimento Inválido.");
			}
		} else {
			throw new IndiceInvalidoException("A casa que você deseja movimentar não existe.\n");
		}

	}
}
