package g6.ppacg6.classes;

import estg.ipp.pt.tp02_conferencesystem.enumerations.ConferenceState;
import estg.ipp.pt.tp02_conferencesystem.exceptions.ConferenceException;
import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Statistics;
import g6.ppacg6.implementations.StatisticsImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonGenerator {

    public static String generateNumberofSessionsbyRoom(Statistics[] statistics) throws ConferenceException {
        // Since we dont have access to the Conference object, when we recevied a null array,
        // its because the conference has finished, and we can now throw an exception
        if (statistics == null) {
            throw new ConferenceException("The conference has finished, no statistics available");
        }

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

    public static String generateNumberofParticipantsbySession(Statistics[] statistics) {
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

        JSONObject rooms = new JSONObject();

        rooms.put("label", "Participants by Sessions");
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

    public static String generateOutlabeledPie(String[] labels, String[] data) {
        String cleanLabels = "", cleanData = "";

        for (int i = 0; i < labels.length; i++) {
            cleanLabels += "'" + labels[i] + "'";
            if (i < labels.length - 1) {
                cleanLabels += ",";
            }
        }

        for (int i = 0; i < data.length; i++) {
            cleanData += "'" + data[i] + "'";
            if (i < data.length - 1) {
                cleanData += ",";
            }
        }

        return String.format("{\n" +
                "  \"type\": \"outlabeledPie\",\n" +
                "  \"data\": {\n" +
                "    \"labels\": [%s],\n" +
                "    \"datasets\": [{\n" +
                "        \"backgroundColor\": [\"#FF3784\", \"#36A2EB\", \"#4BC0C0\", \"#F77825\", \"#9966FF\"],\n" +
                "        \"data\": [%s]\n" +
                "    }]\n" +
                "  },\n" +
                "  \"options\": {\n" +
                "    \"plugins\": {\n" +
                "      \"legend\": false,\n" +
                "      \"outlabels\": {\n" +
                "        \"color\": \"white\",\n" +
                "        \"stretch\": 35,\n" +
                "        \"font\": {\n" +
                "          \"resizable\": true,\n" +
                "          \"minSize\": 12,\n" +
                "          \"maxSize\": 18\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}", cleanLabels, cleanData);
    }

    public static String generateScheduleBar(String[] labels, String[] data) {
        String cleanLabels = "", cleanData = "";

        for (int i = 0; i < labels.length; i++) {
            cleanLabels += "'" + labels[i] + "'";
            if (i < labels.length - 1) {
                cleanLabels += ",";
            }
        }

        for (int i = 0; i < data.length; i++) {
            cleanData += "'" + data[i] + "'";
            if (i < data.length - 1) {
                cleanData += ",";
            }
        }

        return String.format("", cleanLabels, cleanData);
    }
}