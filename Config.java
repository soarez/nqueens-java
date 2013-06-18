public class Config {
  public int populationSize;
  public int generations;
  public double decimation;

  public Config(int populationSize, int generations, double decimation) {
    this.populationSize = populationSize;
    this.generations = generations;
    this.decimation = decimation;
  }
}
