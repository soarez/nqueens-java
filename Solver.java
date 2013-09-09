import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public abstract class Solver<Gene> {

  protected abstract Gene random();
  protected abstract Gene reproduct(Gene a, Gene b);
  protected abstract Double evaluate(Gene g);

  protected final Config config;

  protected Solver(Config config) {
    this.config = config;
  }

  private int generations;
  private ArrayList<Specimen<Gene>> population;

  public Iterable<Specimen<Gene>> getPopulation() {
    Collections.sort(this.population);
    return this.population;
  }

  public Specimen<Gene> best() {
    Collections.sort(this.population);
    return population.get(0);
  }

  public void run() {
    this.createInitialPopulation();
    this.generations = 0;

    while (this.generations < this.config.generations) {
      this.decimate();
      this.breed();
      this.generations++;
    }
  }

  private void createInitialPopulation(){
    this.population = new ArrayList<Specimen<Gene>>(this.config.populationSize);

    for(int i = 0; i < this.config.populationSize; ++i)
      this.add(this.random());
  }

  private void decimate() {
    Collections.sort(this.population);
    int kill = (int) Math.round(this.config.decimation * this.population.size());
    int populationSize = this.population.size();
    this.population = this.population.subList(populationSize - kill, populationSize);
  }

  private void breed() {
    while (this.population.size() < this.config.populationSize)
      this.add(this.reproduct(this.select(), this.select()));
  }

  private void add(Gene gene) {
    Double fitness = this.evaluate(gene);
    Specimen<Gene> specimen = new Specimen<Gene>(gene, fitness);
    this.population.add(specimen);
  }

  private Gene select() {
    int index = (int) Math.floor(this.population.size() * Math.random());
    return this.population.get(index).Gene;
  }
}
