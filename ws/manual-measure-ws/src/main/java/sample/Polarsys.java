package sample;

import org.sonar.wsclient.Host;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.connectors.HttpClient4Connector;
import org.sonar.wsclient.services.*;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Polarsys {

  public static void main(String args[]) {
    // Use Eclipse SONAR: https://dev.eclipse.org/sonar/api/metrics?resource=org.eclipse.mylyn.tasks:org.eclipse.mylyn.tasks-parent
    // https://dev.eclipse.org/sonar/dashboard/index/org.eclipse.cdt:cdt-parent?did=1
    String url = "https://dev.eclipse.org/sonar";
    String login = "admin";
    String password = "admin";
    Sonar sonar = new Sonar(new HttpClient4Connector(new Host(url, login, password)));

    String projectKey = "org.eclipse.cdt:cdt-parent";

    // ResourceQuery query = ResourceQuery.createForMetrics(projectKey, "complexity","open_issues", "coverage", "lines", "violations");
    ResourceQuery query = ResourceQuery.createForMetrics(projectKey,
    		"line_coverage","tests","test_success_density","ncloc",
    		"functions","complexity","comment_lines_density",
    		"uncovered_lines","duplicated_lines","weighted_violations");
    // query.setIncludeTrends(true);
    Resource mylyn = sonar.find(query);

    JSONObject obj = new JSONObject();

    //getVariation2 for "7 days"
    List<Measure> allMeasures = mylyn.getMeasures();
    for (Measure measure : allMeasures) {
        System.out.println(measure.getMetricKey()+": "+measure.getValue());
    	obj.put(measure.getMetricKey(), measure.getValue());
    }

    try {
		FileWriter file = new FileWriter("eclipse_sonar.json");
		file.write(obj.toJSONString());
		file.flush();
		file.close();
 
	} catch (IOException e) {
		e.printStackTrace();
	}
 
	System.out.print(obj);
  }

}
