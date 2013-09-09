import java.util.*;

public class NQueens extends Solver<NQueensGene> {

  private int boardSize;

  public NQueens(int size, int maxgen) {
    super(new Config(50, maxgen, 0.2));
    this.boardSize = size;
  }

  protected NQueensGene random() {
    int[] genCode = new int[this.boardSize];

    for(int i = 0; i < genCode.length; ++i)
      genCode[i] = i;

    for(int i = 0; i < genCode.length; ++i) {
      int j = (int) Math.floor(Math.random() * genCode.length);

      int k = genCode[j];
      genCode[j] = genCode[i];
      genCode[i] = k;
    }

    return new NQueensGene(genCode);
  }

  /// Reproduct
  // Using Cycle Crossover
  // http://en.wikipedia.org/wiki/Crossover_(genetic_algorithm)

  protected NQueensGene reproduct(NQueensGene a, NQueensGene b) {

    int[] genCodeA = a.GenCode;
    int[] genCodeB = b.GenCode;
    int[] genCodeNew = new int[genCodeA.length];

    boolean[] filled = new boolean[this.boardSize];

    int next = (int) (Math.random() * this.boardSize);

    while(! filled[next]) {
      genCodeNew[next] = genCodeA[next];
      filled[next] = true;

      int i;
      for (i = 0; i < genCodeA.length; ++i)
        if (genCodeA[i] == genCodeB[next])
          break;

      next = i;
    }

    for(int i = 0; i < this.boardSize; ++i)
      if(! filled[i])
        genCodeNew[i] = genCodeB[i];

    return new NQueensGene(genCodeNew);
  }

  /// Evaluate

  protected Double evaluate(NQueensGene g) {
    int[] board = g.GenCode;
    double threats = 0;

    for(int i = 0; i < board.length; ++i) {
      for(int j = 0; j < board.length; ++j) {
        if (j == i)
          continue;

        // count horizontal threats
        if (board[i] == board[j])
          ++threats;

        // count diagonnal threats
        int a = Math.min(j, i);
        int b = Math.max(j, i);
        if((b-a) == Math.abs(board[b] - board[a]))
          ++threats;
      }
    }

    Double fitness = threats == 0 ? 1.0 : 1.0 / threats;

    return fitness;
  }


  public static void main(String[] args) {

    if (args.length < 2) {
      System.out.println("Usage: executable <boardSize> <maxGens>");
      return;
    }

    int size = Integer.parseInt(args[0]);
    int maxgen = Integer.parseInt(args[1]);
    System.out.format("Running with size %s in %s generations.\n", size, maxgen);

    Solver<NQueensGene> solver = new NQueens(size, maxgen);

    solver.run();
    Specimen<NQueensGene> best = solver.best();
    System.out.format("Best solution found: %s\n", best);

    // System.out.println("All solutions:");
    // for (Object specimen : solver.getPopulation())
    //   System.out.println(specimen);

    NQueensGene gene = best.Gene;
    int[] genCode = gene.GenCode;

    for(int i = 0; i < size; ++i)
      for(int j = 0; j < size; ++j)
        System.out.print("|" + (genCode[i] == j ? 'Q' : ' ') + (j == size - 1 ? "|\n" : ""));
  }
}
