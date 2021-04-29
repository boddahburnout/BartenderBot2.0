package darth.bartenderbot.command;

import darth.leaflyapi.LeaflySearch;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Map;

public class LeaflyCmd {
    public void queryGrowInfo(TextChannel channel, String message) throws IOException, ParseException {
        LeaflySearch leaflySearch = new LeaflySearch();
        JSONObject strain = leaflySearch.fechLeafly(message);
        Map<String, Object> growInfo = leaflySearch.getGrowInfo(strain);
        for (Object o : growInfo.entrySet()) {
            System.out.println(o);
        }

    }
}