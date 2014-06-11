package sample;

import org.sonar.wsclient.Host;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.connectors.HttpClient4Connector;
import org.sonar.wsclient.services.*;
import java.util.List;

public class Sample {

  public static void main(String args[]) {
    // Use Eclipse SONAR: https://dev.eclipse.org/sonar/api/metrics?resource=org.eclipse.mylyn.tasks:org.eclipse.mylyn.tasks-parent
    String url = "https://dev.eclipse.org/sonar";
    String login = "admin";
    String password = "admin";
    Sonar sonar = new Sonar(new HttpClient4Connector(new Host(url, login, password)));

    String projectKey = "org.eclipse.mylyn.tasks:org.eclipse.mylyn.tasks-parent";
    // String manualMetricKey = "burned_budget";
    String metricKey = "open_issues";

    Resource mylyn = sonar.find(ResourceQuery.createForMetrics(projectKey, "open_issues", "coverage", "lines", "violations"));
    // mylyn.getMeasure("open_issues");

    //getVariation2 for "7 days"
    List<Measure> allMeasures = mylyn.getMeasures();
    for (Measure measure : allMeasures) {
        System.out.println(measure.getMetricKey()+": "+measure.getValue());
    }

    /* sonar.create(ManualMeasureCreateQuery.create(projectKey, manualMetricKey).setValue(50.0));

    for (ManualMeasure manualMeasure : sonar.findAll(ManualMeasureQuery.create(projectKey))) {
      System.out.println("Manual measure on project: " + manualMeasure);
    } */
  }

}
