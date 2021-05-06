package darth.bartenderbot.utils.String;

import darth.bartenderbot.config.ConfigManager;
import darth.cocktailapi.CocktailApi;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPhrase {
    private String[] phrase;
    public String[] getRandomJoinPhrase(Guild guild, User user) throws IOException, ParseException, InvalidConfigurationException {
        phrase = new String[2];
        YamlFile botConfig = new ConfigManager().accessConfig();
        CocktailApi cocktailApi = new CocktailApi();
        Random random = new Random();
        JSONArray randomDrinks = cocktailApi.getRandomDrink();
        JSONObject drink = (JSONObject) randomDrinks.get(0);
        List<String> JoinPhrases = new ArrayList<>();
        JoinPhrases.add("Welcome %user% to %guild% have some %drink% on the house! Make our new friend comfortable!");
        int phraseindex = random.nextInt(JoinPhrases.size());
        phrase [0] = JoinPhrases.get(phraseindex);
        phrase [0] = phrase [0].replaceAll("%drink%", cocktailApi.getName(drink).toString());
        phrase [0] = phrase [0].replaceAll("%user%", user.getName());
        phrase [0] = phrase [0].replaceAll("%guild%", guild.getName());
        phrase [1] = cocktailApi.getThumb(drink).toString();
        return phrase;
    }
    public String[] getRandomServe (User user) throws IOException, ParseException {
        Random random = new Random();
        phrase = new String[2];
        CocktailApi cocktailApi = new CocktailApi();
        JSONArray randomDrinks = cocktailApi.getRandomDrink();
        JSONObject drink = (JSONObject) randomDrinks.get(0);
        List<String> ServePhrases = new ArrayList<>();
        ServePhrases.add("Since your not being picky, have some %drink%, %user%");
        ServePhrases.add("You said you wanted a drink, So try %drink%");
        ServePhrases.add("Since any drink will do, have %drink%");
        int phraseindex = random.nextInt(ServePhrases.size());
        phrase [0] = ServePhrases.get(phraseindex);
        phrase [0] = phrase [0].replaceAll("%drink%", cocktailApi.getName(drink).toString());
        phrase [0] = phrase [0] .replaceAll("%user%", user.getName());
        phrase [1] = cocktailApi.getThumb(drink).toString();
        return phrase;
    }
    public String[] getServe(User user, String userdrink) throws IOException, ParseException {
        CocktailApi cocktailApi = new CocktailApi();
        phrase = new String[2];
        JSONArray Drinks = cocktailApi.getDrinkByName(userdrink);
        JSONObject drink = (JSONObject) Drinks.get(0);
        Random random = new Random();
        List<String> JoinPhrases = new ArrayList<>();
        JoinPhrases.add("Heres yer %drink% %user%! Cheers!");
        JoinPhrases.add("%user%, One %drink% on the house! As always!");
        JoinPhrases.add("*Slides %drink% down the counter* Enjoy your drink, %user%!");
        int phraseindex = random.nextInt(JoinPhrases.size());
        phrase [0] = JoinPhrases.get(phraseindex);
        phrase [0] = phrase [0].replaceAll("%drink%", cocktailApi.getName(drink).toString());
        phrase [0] = phrase [0].replaceAll("%user%", user.getName());
        phrase [1] = cocktailApi.getThumb(drink).toString();
        return phrase;
    }
}
