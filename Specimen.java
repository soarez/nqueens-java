public class Specimen<Gene> implements Comparable<Specimen> {
  public final Gene Gene;
  public final Double Fitness;

  public Specimen(Gene g, Double f) {
    this.Gene = g;
    this.Fitness = f;
  }

  @Override
  public String toString() {
    return String.format("%s (%.2g)", this.Gene.toString(), this.Fitness);
  }

  @Override
  public int compareTo(Specimen other) {
    return this.Fitness.compareTo(other.Fitness);
  }
}
