package darth.bartenderbot.utils.String;

import darth.bartenderbot.utils.FS.FileUtils;
import darth.cocktailapi.CocktailApi;
import net.dv8tion.jda.api.entities.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPhrase {
    private String phrase;
    public String getRandomJoinPhrase(User user) {
        File drink = new FileUtils().GetRandomFile(new File("drinks/"));
        Random random = new Random();
        List<String> JoinPhrases = new ArrayList<>();
        JoinPhrases.add("Here’s yer %drink% %user%! Cheers!");
        JoinPhrases.add("%user%, One %drink% on the house! As always!");
        JoinPhrases.add("*Slides %drink% down the counter* Enjoy your drink, %user%!");
        int phraseindex = random.nextInt(JoinPhrases.size());
        phrase = JoinPhrases.get(phraseindex);
        phrase = phrase.replaceAll("%drink%", drink.getName().replaceAll("_", " "));
        phrase = phrase.replaceAll("%user%", user.getName());
        return phrase;
    }
    public String getRandomServe0 (User user) throws IOException, ParseException {
        Random random = new Random();
        CocktailApi cocktailApi = new CocktailApi();
        JSONArray randomDrinks = cocktailApi.getRandomDrink();
        JSONObject drink = (JSONObject) randomDrinks.get(0);
        List<String> ServePhrases = new ArrayList<>();
        ServePhrases.add("Since your not being picky, have some %drink%, %user%");
        ServePhrases.add("You said you wanted a drink, So try %drink%");
        ServePhrases.add("Since any drink will do, have %drink%");
        int phraseindex = random.nextInt(ServePhrases.size());
        phrase = ServePhrases.get(phraseindex);
        phrase = phrase.replaceAll("%drink%", cocktailApi.getName(drink).toString());
        phrase = phrase.replaceAll("%user%", user.getName());
        return phrase;
    }
}
