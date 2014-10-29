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
    String url = "https://dev.eclipse.org/sonar";
    String login = "admin";
    String password = "admin";
    Sonar sonar = new Sonar(new HttpClient4Connector(new Host(url, login, password)));

    String projectKey = "org.eclipse.cdt:cdt-parent";
    // projectKey = "org.eclipse.emf.compare.features:org.eclipse.emf.compare.diagram.papyrus";
    // projectKey = "org.eclipse.emf.compare:org.eclipse.emf.compare.diagram.papyrus.tests";
    // projectKey = "org.eclipse.emf.compare:org.eclipse.emf.compare.diagram.ide.ui.papyrus";
    // projectKey = "org.eclipse.ease:ease";
    // projectKey = "org.eclipse.ease:ease.modules:EASE.modules";
	System.out.println(projectKey);

    // dit metrics is not available in SONAR CDT project
    ResourceQuery query = ResourceQuery.createForMetrics(projectKey,
    		"line_coverage","tests","test_success_density","ncloc",
    		"functions","complexity","comment_lines_density",
    		"uncovered_lines","duplicated_lines","weighted_violations","public_api","dit");
    // query.setIncludeTrends(true);
    Resource metrics = sonar.find(query);

    JSONObject obj = new JSONObject();

    //getVariation2 for "7 days"
    List<Measure> allMeasures = metrics.getMeasures();
    for (Measure measure : allMeasures) {
        System.out.println(measure.getMetricKey()+": "+measure.getValue());
    	obj.put(measure.getMetricKey(), measure.getValue());
    }

    try {
		FileWriter file = new FileWriter(projectKey + ".json");
		file.write(obj.toJSONString());
		file.flush();
		file.close();
 
	} catch (IOException e) {
		e.printStackTrace();
	}
 
	System.out.print(obj);
  }

}
