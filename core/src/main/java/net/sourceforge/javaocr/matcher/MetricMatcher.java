package net.sourceforge.javaocr.matcher;

import net.sourceforge.javaocr.cluster.Metric;

import java.util.*;

/**
 * classifies supplied features
 *
 * @author Konstantin Pribluda
 */
public class MetricMatcher {

    // metrics to characters
    Map<Metric, Character> metrics;
    // yellow border distance
    Map<Metric, Double> yellow;
    // red border distance
    Map<Metric, Double> red;


    public MetricMatcher() {
        metrics = new HashMap();
        yellow = new HashMap();
        red = new HashMap();
    }

    /**
     * register metric for character with statistical parameters
     *
     * @param metric
     * @param character
     * @param yellow
     * @param red
     */
    public void addMetric(Metric metric, Character character, double yellow, double red) {
        metrics.put(metric, character);
        this.yellow.put(metric, yellow);
        this.red.put(metric, red);
    }

    public List<Match> classify(double[] features) {

        List<Match> matches = new ArrayList<Match>();

        for (Metric metric : metrics.keySet()) {
            double weight = metric.distance(features);
            Match match = new Match(metrics.get(metric), weight, yellow.get(metric), red.get(metric));
            matches.add(match);
        }

        Collections.sort(matches);

        return matches;
    }


    public Map<Metric, Character> getMetrics() {
        return metrics;
    }

    public Map<Metric, Double> getRed() {
        return red;
    }

    public Map<Metric, Double> getYellow() {
        return yellow;
    }


    public List<MetricContainer> containers() {
        List<MetricContainer> values = new ArrayList();
        for (Metric metric : getMetrics().keySet()) {
            MetricContainer clusterContainer = new MetricContainer();
            clusterContainer.setMetric(metric);
            clusterContainer.setRed(getRed().get(metric));
            clusterContainer.setYellow(getYellow().get(metric));
            clusterContainer.setCharacter(getMetrics().get(metric));
            values.add(clusterContainer);
        }
        return values;
    }

    public void setContainers(Collection<? extends MetricContainer> containers) {
        for (MetricContainer container : containers) {
            addMetric(container.getMetric(),container.getCharacter(),container.getYellow(),container.getRed());
        }
    }
}
