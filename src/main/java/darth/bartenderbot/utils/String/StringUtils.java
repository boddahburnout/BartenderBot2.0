package darth.bartenderbot.utils.String;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {
    public String ListToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String string : list) {
            sb.append(string+"\n");
        }
        return sb.toString();
    }
    public List GetCardsFromEmote(String string) {
        int i = 0;
        List cards = new ArrayList();
        List pos = new ArrayList();
        for (char c : string.toCharArray()) {
            i++;
            if (String.valueOf(c).equalsIgnoreCase(":")) {
                pos.add(i);
            }
        }
        for (int m = 0; m < pos.size(); m++) {
            cards.add(string.substring(Integer.parseInt(pos.get(m).toString()), Integer.parseInt(pos.get(m+1).toString())-1));
            m = m+1;
        }
        return cards;
    }
    public String UpdateScore(String string, int score) {
        int i = 0;
        List pos = new ArrayList();
        for (char c : string.toCharArray()) {
            i++;
            if (String.valueOf(c).equalsIgnoreCase("(")) {
                pos.add(i);
            }
            if (String.valueOf(c).equalsIgnoreCase(")")) {
                pos.add(i);
            }
        }
        string = string.replaceAll(string.substring(Integer.parseInt(pos.get(0).toString()), Integer.parseInt(pos.get(1).toString())-1), String.valueOf(score));
        return string;
    }
}
