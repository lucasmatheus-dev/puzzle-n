package br.com.poli.application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import br.com.poli.puzzleN.Dificuldade;
import br.com.poli.puzzleN.Jogador;
import br.com.poli.puzzleN.Puzzle;
import br.com.poli.puzzleN.PuzzleDificil;
import br.com.poli.puzzleN.PuzzleFacil;
import br.com.poli.puzzleN.PuzzleInsano;
import br.com.poli.puzzleN.PuzzleMedio;
import br.com.poli.puzzleN.TempoExcedidoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class Controlador {

	// cria puzzle statico para persistencia de dados durante a troca de telas
	private static Puzzle puzzle;

	private StringBuilder sb = new StringBuilder();

	private File arquivo = new File("ranking.txt");

	private int linha;

	private int coluna;

	@FXML
	private GridPane gridPane;

	@FXML
	private AnchorPane telaInsano;

	@FXML
	private AnchorPane telaInicial;

	@FXML
	private AnchorPane telaJogadorDificuldade;

	@FXML
	private AnchorPane telaPosGame;

	@FXML
	private AnchorPane telaPuzzle;

	@FXML
	private AnchorPane telaRanking;

	private Button[][] button;

	@FXML
	private Button bIniciar;

	@FXML
	private Button bRanking;

	@FXML
	private Button bSair;

	@FXML
	private Button bDesistir = new Button("DESISTIR");

	@FXML
	private Button bInicioDePartida;

	@FXML
	private Button bVoltar;

	@FXML
	private Button buttonDesistir;

	@FXML
	private Button bContinuar;

	@FXML
	private Button bCima = new Button();

	@FXML
	private Button bBaixo = new Button();

	@FXML
	private Button bEsquerda = new Button();

	@FXML
	private Button bDireita = new Button();

	private Button bNext = new Button("NEXT");

	private Button bAuto = new Button("AUTO");

	private Button aumentarK = new Button("+");

	private Button diminuirK = new Button("-");

	private Label labelInsano = new Label("6");

	private Label labelRanking = new Label();

	@FXML
	private Label labelVouD = new Label();

	@FXML
	private Label labelTempo = new Label();

	@FXML
	private Label labelPontos = new Label();

	@FXML
	private Label labelNome = new Label();

	@FXML
	private MenuButton menuDificuldade;

	@FXML
	private MenuItem menuItemFacil;

	@FXML
	private MenuItem menuItemMedio;

	@FXML
	private MenuItem menuItemDificil;

	@FXML
	private MenuItem menuItemInsano;

	@FXML
	private TextField txtNome;

	public void aBtDesistir() throws IOException {

		labelVouD.setText("Derrota");
		setaPosGame();

	}

	@FXML
	public void aBtInicioDePartida(ActionEvent event) {

		puzzle.getJogador().setNome(preencheString(puzzle.getJogador().getNome(), 10));

		bDesistir.setLayoutX(307);
		bDesistir.setLayoutY(272);
		bDesistir.setFont(Font.font("Arial Black"));
		bDesistir.setCursor(Cursor.HAND);
		bDesistir.setStyle("-fx-background-color: #1d1d1d;");
		bDesistir.setTextFill(Paint.valueOf("WHITE"));
		bDesistir.setOnAction(e -> {
			try {
				aBtDesistir();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		bNext.setLayoutX(14);
		bNext.setLayoutY(272);
		bNext.setFont(Font.font("Arial Black"));
		bNext.setCursor(Cursor.HAND);
		bNext.setStyle("-fx-background-color: #1d1d1d;");
		bNext.setTextFill(Paint.valueOf("WHITE"));
		bNext.setOnAction(e -> aBtNext());
		bAuto.setLayoutX(160);
		bAuto.setLayoutY(272);
		bAuto.setFont(Font.font("Arial Black"));
		bAuto.setCursor(Cursor.HAND);
		bAuto.setStyle("-fx-background-color: #1d1d1d;");
		bAuto.setTextFill(Paint.valueOf("WHITE"));
		bAuto.setOnAction(e -> {
			try {
				aBtAuto();
			} catch (TempoExcedidoException e1) {
				System.out.println(e1.getMessage());
				Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
				dialogoInfo.setTitle("Erro!");
				dialogoInfo.setHeaderText("Tempo de duração excedido!");
				dialogoInfo.setContentText("Por favor, recomece o jogo.");
				dialogoInfo.showAndWait();
				Main.stage.setScene(Main.cenaTelaInicial);
			}
		});

		labelNome.setText("JOGADOR: " + puzzle.getJogador().getNome());

		puzzle.iniciaPartida();

		if (puzzle.getDificuldade().equals(Dificuldade.INSANO)) {

			telaInsano.getChildren().remove(bInicioDePartida);
			telaInsano.getChildren().addAll(bNext, bDesistir, bAuto);
			bCima.setDisable(false);
			bDireita.setDisable(false);
			bEsquerda.setDisable(false);
			bBaixo.setDisable(false);
			puzzle.imprimeGrid();

		}
		else {
			telaPuzzle.getChildren().remove(bInicioDePartida);
			telaPuzzle.getChildren().addAll(bNext, bDesistir, bAuto);
			criaTabuleiro();
		}
	}

	// caso nao esteja em sua posicao devida, coloca o numero na sua casa certa
	public void aBtNext() {
		puzzle.executaMovimentoAuto();
		if (!puzzle.getDificuldade().equals(Dificuldade.INSANO)) {
			imprimeBotoes();
		}
		try {
			fimDeJogo();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void aBtAuto() {
		puzzle.resolveTabuleiro(puzzle.getDificuldade());
		try {
			fimDeJogo();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void aBtIniciar(ActionEvent event) throws IOException {
		Pane telaJogadorDificuldade = FXMLLoader.load(getClass().getResource("TelaJogadorDificuldade.fxml"));

		if (!arquivo.exists()) {
			arquivo.createNewFile();
		}
		Main.stage.setScene(new Scene(telaJogadorDificuldade));
	}

	@FXML
	public void aBtVoltar(ActionEvent event) {
		Main.stage.setScene(Main.cenaTelaInicial);
	}

	@FXML
	public void aBtRanking(ActionEvent event) throws IOException {
		if (!arquivo.exists()) {
			arquivo.createNewFile();
		}
		Pane telaRanking = FXMLLoader.load(getClass().getResource("TelaRanking.fxml"));
		labelRanking.setFont(Font.font("Arial Black", 10));
		labelRanking
				.setStyle("-fx-background-color: #FFFFFF;" + "-fx-border-style: solid inside;" + "-fx-border-width: 1;"
						+ "-fx-border-insets: 2;" + "-fx-border-radius: 2;" + "-fx-border-color: black;");
		labelRanking.setLayoutX(1);
		labelRanking.setLayoutY(0);

		labelRanking.setText(ler());

		telaRanking.getChildren().add(labelRanking);
		Main.stage.setScene(new Scene(telaRanking));

	}

	@FXML
	public void aBtSair(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	public void aBtContinuar(ActionEvent event) throws IOException {
		if (menuDificuldade.getText().equals("DIFICULDADE") || txtNome.getText().equals("")
				|| txtNome.getText().length() > 10 || !percorreString(txtNome.getText().toLowerCase())) {
			Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);

			dialogoInfo.setTitle("Erro!");
			dialogoInfo.setHeaderText("Dificuldade ou nome não informados corretamente.");
			dialogoInfo.setContentText("Por favor, preencha todos os campos corretamente.");
			dialogoInfo.showAndWait();

		}

		else if (menuDificuldade.getText().equals("Fácil")) {
			Pane telaPuzzle = FXMLLoader.load(getClass().getResource("TelaPuzzle.fxml"));

			puzzle = new PuzzleFacil(new Jogador(txtNome.getText()));
			Main.stage.setScene(new Scene(telaPuzzle));

		}

		else if (menuDificuldade.getText().equals("Médio")) {
			Pane telaPuzzle = FXMLLoader.load(getClass().getResource("TelaPuzzle.fxml"));

			puzzle = new PuzzleMedio(new Jogador(txtNome.getText()));
			Main.stage.setScene(new Scene(telaPuzzle));

		}

		else if (menuDificuldade.getText().equals("Difícil")) {
			Pane telaPuzzle = FXMLLoader.load(getClass().getResource("TelaPuzzle.fxml"));

			puzzle = new PuzzleDificil(new Jogador(txtNome.getText()));
			Main.stage.setScene(new Scene(telaPuzzle));

		} else if (menuDificuldade.getText().equals("Insano")) {
			Pane telaInsano = FXMLLoader.load(getClass().getResource("TelaInsano.fxml"));

			puzzle = new PuzzleInsano(new Jogador(txtNome.getText()), Integer.parseInt(labelInsano.getText()));
			Main.stage.setScene(new Scene(telaInsano));
		}

	}

	@FXML
	public void aBBaixo(ActionEvent event) {
		verificaZero();
		fazMovimentoPuzzle(linha - 1, coluna);
	}

	@FXML
	public void aBCima(ActionEvent event) {
		verificaZero();
		fazMovimentoPuzzle(linha + 1, coluna);
	}

	@FXML
	public void aBDireita(ActionEvent event) {
		verificaZero();
		fazMovimentoPuzzle(linha, coluna - 1);
	}

	@FXML
	public void aBEsquerda(ActionEvent event) {
		verificaZero();
		fazMovimentoPuzzle(linha, coluna + 1);
	}

	public void verificaZero() {

		int x = 0, y = 0;
		for (int i = 0; i < puzzle.getGridPuzzle().getTamanhoGrid(); i++) {
			for (int j = 0; j < puzzle.getGridPuzzle().getTamanhoGrid(); j++) {
				if (puzzle.getGridPuzzle().getPosicaoGrid(i, j).getValor() == 0) {
					x = i;
					y = j;
				}
			}
		}

		linha = x;
		coluna = y;
	}

	@FXML
	public void aMiInsano(ActionEvent event) {

		telaJogadorDificuldade.getChildren().removeAll(aumentarK, diminuirK, labelInsano);

		labelInsano.setLayoutX(193);
		labelInsano.setLayoutY(177);
		labelInsano.setFont(Font.font("Arial Black", 12));

		aumentarK.setLayoutX(245);
		aumentarK.setLayoutY(173);
		aumentarK.setFont(Font.font("Arial Black", 12));
		aumentarK.setStyle("-fx-background-color: #000000;");
		aumentarK.setTextFill(Paint.valueOf("WHITE"));
		aumentarK.setOnAction(e -> aumentaLabelInsano(Integer.parseInt(labelInsano.getText())));
		diminuirK.setOnAction(e -> diminuiLabelInsano(Integer.parseInt(labelInsano.getText())));
		diminuirK.setLayoutX(125);
		diminuirK.setLayoutY(173);
		diminuirK.setFont(Font.font("Arial Black", 12));
		diminuirK.setStyle("-fx-background-color: #000000;");
		diminuirK.setTextFill(Paint.valueOf("WHITE"));

		telaJogadorDificuldade.getChildren().addAll(aumentarK, diminuirK, labelInsano);

		menuDificuldade.setText("Insano");
	}

	public void aumentaLabelInsano(int numero) {
		if (numero == 1000) {
			return;
		}
		labelInsano.setText(Integer.toString(++numero));
	}

	public void diminuiLabelInsano(int numero) {
		if (numero == 6) {
			return;
		}
		labelInsano.setText(Integer.toString(--numero));
	}

	@FXML
	public void aMiDificil(ActionEvent event) {
		telaJogadorDificuldade.getChildren().removeAll(aumentarK, diminuirK, labelInsano);

		menuDificuldade.setText("Difícil");
	}

	@FXML
	public void aMiFacil(ActionEvent event) {
		telaJogadorDificuldade.getChildren().removeAll(aumentarK, diminuirK, labelInsano);

		menuDificuldade.setText("Fácil");
	}

	@FXML
	public void aMiMedio(ActionEvent event) {
		telaJogadorDificuldade.getChildren().removeAll(aumentarK, diminuirK, labelInsano);

		menuDificuldade.setText("Médio");
	}

	public boolean percorreString(String string) {
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) < 97 || string.charAt(i) > 122) {
				return false;
			}
		}
		return true;
	}

	public String preencheString(String string, int tamanho) {

		int temp;
		char c;

		// se o tamanho da string for 7 indica que e tempo, entao e completada com 0
		// se nao for 7 e o caso do nome, entao e preenchido com espacos vazios
		if (tamanho == 7) {
			c = '0';
		} else {
			c = ' ';
		}

		if (string.length() <= tamanho) {
			temp = string.length();
			for (int i = temp; i < tamanho; i++) {
				string = string + c;
			}
		}

		return string;
	}

	public void salvar(String texto) throws IOException {

		FileWriter fw = new FileWriter(arquivo.getAbsoluteFile(), false);
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write(texto);
		bw.close();

	}

	public void OrdenaLido() throws IOException {

		String linha;
		String temp = "";
		String[] linhas;
		int[] temp1;
		int[] pontos;
		int contador = 0;
		FileReader ler = new FileReader("ranking.txt");
		FileReader ler2 = new FileReader("ranking.txt");
		BufferedReader leitor = new BufferedReader(ler);
		BufferedReader leitor2 = new BufferedReader(ler2);

		while ((linha = leitor2.readLine()) != null) {
			contador++;
		}

		leitor2.close();
		ler2.close();

		pontos = new int[contador];
		temp1 = new int[contador];
		linhas = new String[contador];

		contador = 0;

		while ((linha = leitor.readLine()) != null) {
			linhas[contador] = linha;
			pontos[contador] = Integer.parseInt(linhas[contador].substring(36, linhas[contador].length()));
			contador++;
		}

		Arrays.sort(pontos);

		contador = 0;

		for (int i = pontos.length - 1; i > -1; i--) {
			temp1[i] = pontos[contador];
			contador++;
		}

		for (int i = 0; i < temp1.length; i++) {
			pontos[i] = temp1[i];
		}

		contador = 0;

		// escreve em uma String temporaria todas as string de linhas, de acordo com a
		// ordem de pontos
		rotulo: for (int i = 0; i < pontos.length; i++) {
			for (int j = 0; j < linhas.length; j++) {
				if (Integer.toString(pontos[i]).equals(linhas[j].substring(36, linhas[j].length()))) {

					temp = temp + linhas[j] + "\n";
					linhas[j] = "                                                                                ";
					if (contador == 19) {
						break rotulo;
					}
					contador++;
				}
			}
		}

		salvar(temp);

		leitor.close();
		ler.close();

	}

	public String ler() throws IOException {

		String linha;
		String temp = "";
		FileReader ler = new FileReader("ranking.txt");
		BufferedReader leitor = new BufferedReader(ler);

		while ((linha = leitor.readLine()) != null) {
			temp = temp + linha + "\n";

		}

		leitor.close();
		ler.close();

		return temp;

	}

	public String transformaTempo() {

		String temp1;

		temp1 = sb.toString();
		temp1 = preencheString(temp1, 7);
		temp1 = temp1.substring(0, 7);

		return temp1;
	}

	public void fimDeJogo() throws IOException {
		if (puzzle.isFimDeJogo()) {
			
			labelVouD.setText("Vitória");

			setaPosGame();

			salvar("Nome: " + puzzle.getJogador().getNome() + " " + transformaTempo() + "min" + " " + "Pontos: "
					+ puzzle.getScore() + "\n" + ler());
			OrdenaLido();
		}
	}

	public void setaPosGame() throws IOException {

		Calendar tempofinal = Calendar.getInstance();
		Pane telaPosGame = null;

		sb.append((float) (tempofinal.getTimeInMillis() - puzzle.getTempo().getTimeInMillis()) / 60000);
		telaPosGame = FXMLLoader.load(getClass().getResource("TelaPosGame.fxml"));

		labelVouD.setFont(Font.font("Arial Black", 54));
		labelVouD.setLayoutX(110);
		labelVouD.setLayoutY(20);

		labelTempo.setFont(Font.font("Arial Black", 20));
		labelTempo.setLayoutX(8);
		labelTempo.setLayoutY(119);
		labelTempo.setText("Tempo decorrido: " + transformaTempo() + " minutos");

		labelPontos.setFont(Font.font("Arial Black", 20));
		labelPontos.setLayoutX(8);
		labelPontos.setLayoutY(150);
		labelPontos.setText("Pontos: " + Integer.toString(puzzle.getScore()));

		telaPosGame.getChildren().addAll(labelVouD, labelTempo, labelPontos);

		Main.stage.setScene(new Scene(telaPosGame));

	}

	public void criaTabuleiro() {

		gridPane.getRowConstraints().clear();
		gridPane.getColumnConstraints().clear();

		button = new Button[puzzle.getGridPuzzle().getTamanhoGrid()][puzzle.getGridPuzzle().getTamanhoGrid()];

		for (int i = 0; i < puzzle.getGridPuzzle().getTamanhoGrid(); i++) {
			for (int j = 0; j < puzzle.getGridPuzzle().getTamanhoGrid(); j++) {
				if (puzzle.getGridPuzzle().getPosicaoGrid(i, j).getValor() == 0) {
					button[i][j] = new Button();
					button[i][j].setVisible(false);
					button[i][j].setFont(Font.font("Arial Black", 12));
					button[i][j].setStyle("-fx-background-color: #1d1d1d;" + "-fx-border-style: solid inside;"
							+ "-fx-border-width: 1;" + "-fx-border-insets: 2;" + "-fx-border-radius: 2;"
							+ "-fx-border-color: white;");
					button[i][j].setTextFill(Paint.valueOf("WHITE"));
					button[i][j].setCursor(Cursor.HAND);

				} else {

					button[i][j] = new Button(Integer.toString(puzzle.getGridPuzzle().getPosicaoGrid(i, j).getValor()));
					button[i][j].setFont(Font.font("Arial Black", 12));
					button[i][j].setStyle("-fx-background-color: #1d1d1d;" + "-fx-border-style: solid inside;"
							+ "-fx-border-width: 1;" + "-fx-border-insets: 2;" + "-fx-border-radius: 2;"
							+ "-fx-border-color: white;");
					button[i][j].setTextFill(Paint.valueOf("WHITE"));
					button[i][j].setCursor(Cursor.HAND);

				}

				button[i][j].setMaxSize(60, 60);

				GridPane.setConstraints(button[i][j], j, i);

				gridPane.getChildren().addAll(button[i][j]);

			}
			gridPane.getRowConstraints().add(new RowConstraints(45));
			gridPane.getColumnConstraints().add(new ColumnConstraints(45));

		}
		button[0][0].setOnAction(e -> fazMovimentoPuzzle(0, 0));
		button[0][1].setOnAction(e -> fazMovimentoPuzzle(0, 1));
		button[0][2].setOnAction(e -> fazMovimentoPuzzle(0, 2));
		button[1][0].setOnAction(e -> fazMovimentoPuzzle(1, 0));
		button[1][1].setOnAction(e -> fazMovimentoPuzzle(1, 1));
		button[1][2].setOnAction(e -> fazMovimentoPuzzle(1, 2));
		button[2][0].setOnAction(e -> fazMovimentoPuzzle(2, 0));
		button[2][1].setOnAction(e -> fazMovimentoPuzzle(2, 1));
		button[2][2].setOnAction(e -> fazMovimentoPuzzle(2, 2));
		if (puzzle.getGridPuzzle().getTamanhoGrid() == 4 || puzzle.getGridPuzzle().getTamanhoGrid() == 5) {
			button[0][3].setOnAction(e -> fazMovimentoPuzzle(0, 3));
			button[1][3].setOnAction(e -> fazMovimentoPuzzle(1, 3));
			button[2][3].setOnAction(e -> fazMovimentoPuzzle(2, 3));
			button[3][0].setOnAction(e -> fazMovimentoPuzzle(3, 0));
			button[3][1].setOnAction(e -> fazMovimentoPuzzle(3, 1));
			button[3][2].setOnAction(e -> fazMovimentoPuzzle(3, 2));
			button[3][3].setOnAction(e -> fazMovimentoPuzzle(3, 3));
		}
		if (puzzle.getGridPuzzle().getTamanhoGrid() == 5) {
			button[0][4].setOnAction(e -> fazMovimentoPuzzle(0, 4));
			button[1][4].setOnAction(e -> fazMovimentoPuzzle(1, 4));
			button[2][4].setOnAction(e -> fazMovimentoPuzzle(2, 4));
			button[3][4].setOnAction(e -> fazMovimentoPuzzle(3, 4));
			button[4][0].setOnAction(e -> fazMovimentoPuzzle(4, 0));
			button[4][1].setOnAction(e -> fazMovimentoPuzzle(4, 1));
			button[4][2].setOnAction(e -> fazMovimentoPuzzle(4, 2));
			button[4][3].setOnAction(e -> fazMovimentoPuzzle(4, 3));
			button[4][4].setOnAction(e -> fazMovimentoPuzzle(4, 4));
		}

	}

	public void imprimeBotoes() {
		for (int k = 0; k < button.length; k++) {
			for (int l = 0; l < button.length; l++) {
				if (puzzle.getGridPuzzle().getPosicaoGrid(k, l).getValor() == 0) {
					button[k][l].setVisible(false);

				} else {

					if (button[k][l].getText() != Integer
							.toString(puzzle.getGridPuzzle().getPosicaoGrid(k, l).getValor())) {

						button[k][l].setText(Integer.toString(puzzle.getGridPuzzle().getPosicaoGrid(k, l).getValor()));
						button[k][l].setVisible(true);

					}
				}
			}
		}
	}

	public void fazMovimentoPuzzle(int linha, int coluna) {

		if (!puzzle.getDificuldade().equals(Dificuldade.INSANO)) {
			puzzle.fazMovimento(linha, coluna);
			imprimeBotoes();

		} else {
			puzzle.fazMovimento(linha, coluna);
		}
		try {
			fimDeJogo();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}