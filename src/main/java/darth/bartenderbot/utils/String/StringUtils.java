package darth.bartenderbot.utils.String;

import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.leaflyapi.LeaflySearch;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public String FormatWeedData(JSONObject strain) {
        LeaflySearch leaflyApi = new LeaflySearch();
 //       Object[] PopularIn = leaflyApi.getPopularIn(strain);
        Map<Object, Object> Cannabinoids = leaflyApi.getCannabinoids(strain);
        int rating;
        try {
            rating = Double.valueOf((Double) leaflyApi.getAverageRating(strain)).intValue();
        } catch (ClassCastException e) {
            rating = 0;
        }
        //String weedmessage = leaflyApi.getDescriptionPlain(strain) + "\n\nPopular in: " + PopularIn[0] + "\n" + "\nCategory: " + leaflyApi.getCategory(strain) + "\n\nChemotype: " + leaflyApi.getChemotype(strain) + "\n\nTop Effects: " + leaflyApi.getTopEffect(strain) + "\n\nRating: " + rating;
        StringBuilder stringBuilder1 = new StringBuilder();

        try {
            stringBuilder1.append(leaflyApi.getDescriptionPlain(strain)+"\n");
        } catch (NullPointerException e) {

        }

        try {
            stringBuilder1.append("\n\nPopular in: " + leaflyApi.getPopularIn(strain) [0]+"\n");
        } catch (NullPointerException e) {

        }

        try {
            stringBuilder1.append("\nCategory: "+ leaflyApi.getCategory(strain));
        } catch (NullPointerException e) {

        }

        try {
            stringBuilder1.append("\n\nChemotype: "+ leaflyApi.getChemotype(strain));
        } catch (NullPointerException e) {

        }

        try {
            stringBuilder1.append("\n\nTop Effects: " + leaflyApi.getTopEffect(strain));
        } catch (NullPointerException e) {

        }

        stringBuilder1.append("\n\nRating: " + rating);

        if (Cannabinoids.containsKey("CBD")) {
            stringBuilder1.append("\n\nCBD: " + Double.valueOf(Cannabinoids.get("CBD").toString()).intValue() + "%");
        }

        if (Cannabinoids.containsKey("THC")) {
            stringBuilder1.append("\nTHC: " + Double.valueOf(Cannabinoids.get("THC").toString()).intValue() + "%");
        }

        try {
            Object[] SimilarStrains = leaflyApi.getSimilarStrains(strain);
            stringBuilder1.append("\n\nSimilar strains: \n");
            for (int i = 0; i < SimilarStrains.length; i++) {
                stringBuilder1.append(SimilarStrains[i] + "\n");
            }
        } catch (NullPointerException e) {

        }
        return stringBuilder1.toString().replaceAll("null", "0");
    }
}
