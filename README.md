# Puzzle-N

Jogo de quebra-cabeça deslizante (N-Puzzle) feito em **Java** com interface gráfica em **JavaFX**.

O projeto foi desenvolvido em 2019, em dupla, para a disciplina de **Linguagem de Programação Orientada a Objetos (LPOO)**, com o objetivo de praticar e consolidar os conceitos de orientação a objetos em Java.

## Autores

- Lucas Matheus da Silva Oliveira
- Jean Felipe Coelho Ferreira

## Sobre o jogo

O tabuleiro tem peças numeradas embaralhadas e um espaço vazio. O objetivo é mover as peças vizinhas ao espaço vazio até que todas fiquem em ordem crescente, da esquerda para a direita e de cima para baixo, com o espaço vazio na última posição:

```
1  2  3
4  5  6
7  8  _
```

### Dificuldades

| Dificuldade | Tabuleiro |
|-------------|-----------|
| Fácil       | 3 x 3     |
| Médio       | 4 x 4     |
| Difícil     | 5 x 5     |
| Insano      | K x K, com K escolhido pelo jogador (a partir de 6; use valores até 999) |

### Como jogar

1. Na tela inicial, clique em **INICIAR**.
2. Digite um nome com até 10 letras, **sem acentos, espaços ou números**.
3. Escolha a dificuldade, clique em **CONTINUAR** e depois em **INICIAR PARTIDA**.
4. Nos modos Fácil, Médio e Difícil, clique em uma peça vizinha ao espaço vazio para movê-la.
5. No modo Insano, o tabuleiro é exibido **no console**, e as peças são movidas pelos botões de seta da janela. Por isso, execute o jogo por um terminal ou pela IDE.

Durante a partida também há os botões:

- **NEXT**: leva automaticamente a próxima peça em direção à posição correta.
- **AUTO**: tenta resolver o tabuleiro automaticamente (limite de 10 segundos).
- **DESISTIR**: encerra a partida como derrota.

A pontuação depende da dificuldade, do tamanho do tabuleiro e da quantidade de movimentos: quanto menos movimentos, mais pontos. As vitórias são salvas no arquivo `ranking.txt`, e o **RANKING** mostra as 20 maiores pontuações.

## Conceitos de POO aplicados

| Conceito | Onde aparece |
|----------|--------------|
| Encapsulamento | Atributos privados com getters e setters, como em `Bloco`, `Puzzle` e `ParOrdenado` |
| Herança | `PuzzleFacil`, `PuzzleMedio`, `PuzzleDificil` e `PuzzleInsano` estendem `Puzzle` |
| Polimorfismo | `PuzzleInsano` sobrescreve `iniciaPartida()`, e cada dificuldade calcula a pontuação do seu jeito |
| Interfaces | `CalculaScore`, implementada por uma classe de cálculo para cada dificuldade |
| Composição | `Puzzle` possui um `Jogador` e um `Tabuleiro`, que é formado por vários `Bloco` |
| Enumerações | `Dificuldade` |
| Exceções personalizadas | `MovimentoInvalidoException`, `IndiceInvalidoException` e `TempoExcedidoException` |

## Estrutura do projeto

```
puzzle-n/
├── src/
│   ├── br/com/poli/
│   │   ├── application/   # Interface gráfica: classe principal, controlador, telas (.fxml) e CSS
│   │   └── puzzleN/       # Regras do jogo e testes via console
│   └── images/            # Imagens usadas na interface
├── .settings/             # Configurações do Eclipse
├── .classpath             # Configurações do Eclipse
├── .project               # Configurações do Eclipse
├── build.fxbuild          # Configuração de build do e(fx)clipse
└── README.md
```

## Como executar

### Pré-requisitos

O projeto foi configurado para **Java 8** e usa **JavaFX**. A opção mais simples é instalar um JDK 8 que já inclua o JavaFX, por exemplo:

- [Liberica JDK 8](https://bell-sw.com/pages/downloads/) (versão **Full**)
- [Azul Zulu 8](https://www.azul.com/downloads/) (pacote **JDK FX**)
- Oracle JDK 8

Confira se o Java está disponível no terminal:

```bash
java -version
javac -version
```

Primeiro, clone o repositório:

```bash
git clone https://github.com/lucasmatheus-dev/puzzle-n.git
cd puzzle-n
```

Execute todos os comandos a seguir **na raiz do projeto**. O arquivo `ranking.txt` é criado na pasta em que o jogo é executado.

### Windows (PowerShell)

```powershell
# 1. Compilar
New-Item -ItemType Directory -Force bin | Out-Null
$fontes = (Get-ChildItem -Recurse src -Filter *.java).FullName
javac -encoding UTF-8 -d bin $fontes

# 2. Copiar telas e imagens para a pasta de saída
Copy-Item src/br/com/poli/application/*.fxml, src/br/com/poli/application/*.css bin/br/com/poli/application/
Copy-Item -Recurse -Force src/images bin/

# 3. Executar
java -cp bin br.com.poli.application.Main
```

### Linux e macOS

```bash
# 1. Compilar
mkdir -p bin
javac -encoding UTF-8 -d bin src/br/com/poli/*/*.java

# 2. Copiar telas e imagens para a pasta de saída
cp src/br/com/poli/application/*.fxml src/br/com/poli/application/*.css bin/br/com/poli/application/
cp -r src/images bin/

# 3. Executar
java -cp bin br.com.poli.application.Main
```

### Java 11 ou superior

A partir do Java 11, o JavaFX não vem mais no JDK padrão. Se você não usar um JDK que já o inclua, baixe o [JavaFX SDK](https://gluonhq.com/products/javafx/) compatível com a sua versão do Java. Depois, acrescente as opções abaixo aos comandos `javac` e `java`, trocando o caminho pela pasta `lib` do SDK:

```
--module-path "caminho/para/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml
```

Por exemplo, na execução:

```bash
java --module-path "caminho/para/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml -cp bin br.com.poli.application.Main
```

O projeto foi criado para o Java 8, então versões mais novas podem exibir avisos sobre a versão do JavaFX usada nos arquivos `.fxml`.

### Pelo Eclipse

1. Use um JDK 8 com JavaFX e instale o plugin **e(fx)clipse**.
2. Vá em **File > Import > General > Existing Projects into Workspace** e selecione a pasta do repositório. O projeto aparece como `jfcf.lmso.puzzleN`.
3. Abra `src/br/com/poli/application/Main.java` e escolha **Run As > Java Application**.

### Testes via console

A classe `br.com.poli.puzzleN.Main` testa as regras do jogo no console, sem abrir a interface gráfica. Depois de compilar o projeto, execute:

```bash
java -cp bin br.com.poli.puzzleN.Main
```
