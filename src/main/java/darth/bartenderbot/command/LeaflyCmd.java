package darth.bartenderbot.command;

import darth.leaflyapi.LeaflySearch;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Map;

public class LeaflyCmd {
    public void queryGrowInfo(String message) throws IOException, ParseException {
        LeaflySearch leaflySearch = new LeaflySearch();
        JSONObject strain = leaflySearch.fechLeafly(message);
        Map<String, Object> growInfo = leaflySearch.getGrowInfo(strain);
        StringBuilder sb = new StringBuilder();
        for (Object o : growInfo.keySet()) {
            if (growInfo.get(o) != null)
            sb.append(o+": "+growInfo.get(o)+"\n");
        }
        System.out.println(sb.toString());
    }
}