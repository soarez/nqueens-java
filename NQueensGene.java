public class NQueensGene {
  public final int[] GenCode;

  public NQueensGene(int[] genCode) {
    this.GenCode = genCode;
  }

  @Override
  public String toString() {
    return java.util.Arrays.toString(this.GenCode);
  }
}
