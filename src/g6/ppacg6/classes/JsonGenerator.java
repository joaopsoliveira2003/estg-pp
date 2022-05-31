package g6.ppacg6.classes;

import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Statistics;
import g6.ppacg6.implementations.StatisticsImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonGenerator {

    public static String generateNumberofSessionsbyRoom(Statistics[] statistics) {
        JSONObject json = new JSONObject();
        json.put("type", "bar");

        JSONObject data = new JSONObject();
        JSONArray labels = new JSONArray();
        int i = 1;
        for (Statistics stat : statistics) {
            labels.add(stat.getDescription());
        }
        data.put("labels", labels);
        json.put("data", data);

        JSONArray datasets = new JSONArray();

        /*

        JSONObject sessions = new JSONObject();

        sessions.put("label", "Sessions");

        JSONArray dataSessions = new JSONArray();
        for (Statistics stat : statistics) {
            dataSessions.add((int) stat.getValue());
        }
        sessions.put("data", dataSessions);
        datasets.add(sessions);

        */

        JSONObject rooms = new JSONObject();

        rooms.put("label", "Sessions by Rooms");
        JSONArray dataRooms = new JSONArray();
        for (Statistics stat : statistics) {
            dataRooms.add(stat.getValue());
        }
        rooms.put("data", dataRooms);
        datasets.add(rooms);

        data.put("datasets", datasets);

        System.out.println(json.toJSONString());

        return json.toJSONString();
    }

}
