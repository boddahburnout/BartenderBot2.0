package darth.bartenderbot.command.user.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import darth.bartenderbot.command.category.BotCategories;
import darth.bartenderbot.handler.CommandHandler;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.bartenderbot.utils.String.RandomPhrase;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.json.simple.parser.ParseException;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Serve extends Command {

    public Serve() {
        this.name = "serve";
        this.help = "Serve a drink to yourself ot your friends";
        this.arguments = "optional <drink> <@user>";
        this.category = new BotCategories().UserCat();
    }

    /**
     * The main body method of a {@link Command Command}.
     * <br>This is the "response" for a successful
     * {@link Command#run(CommandEvent) #run(CommandEvent)}.
     *
     * @param event The {@link CommandEvent CommandEvent} that
     *              triggered this Command
     */
    @Override
    protected void execute(CommandEvent event) {
        Guild guild = event.getGuild();
        Message message = event.getMessage();
        Map<String, String> commandData;
        try {
            commandData = new CommandHandler().getCommandData(guild, message);
            if (commandData.get("args").split(" ") [0].isEmpty()) { // No arguments no problem, everyone gets a drink!
                try {
                    String[] drink = new RandomPhrase().getRandomServe(event.getAuthor());
                    Color color = Color.BLUE;
                    try {
                        color = new EmbedWrapper().GetGuildEmbedColor(guild);
                    } catch (InvalidConfigurationException | IOException e) {
                        e.printStackTrace();
                    }
                    event.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(event.getGuild().getJDA().getSelfUser().getName() + " served a drink to " + event.getAuthor().getName(), "", "", color, drink[0], null, null, null, drink[1])).queue();
                } catch (IllegalArgumentException | ParseException e) {
                    e.printStackTrace();
                    message.getChannel().sendMessage("Please add some drinks first!").queue();
                }
                return;
            }
            try {
                List<User> mention = message.getMentionedUsers();
                if (!mention.isEmpty()) { //handle a serve with a mention
                    List<User> user = message.getMentionedUsers();
                    String args = event.getArgs();
                    for (User u : user) {
                        args = args.replaceAll("<@!" + u.getId() + ">", "").trim();
                        args = args.replaceAll("<@" + u.getId() + ">", "").trim();
                    }
                    Color color = Color.BLUE;
                    try {
                        color = new EmbedWrapper().GetGuildEmbedColor(guild);
                    } catch (InvalidConfigurationException | IOException e) {
                        e.printStackTrace();
                    }
                    if (args.length() != 0) { //Serve a drink provide by the user
                        String[] drink = new RandomPhrase().getServe(event.getAuthor(), args);
                        message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(event.getAuthor().getName() + " served a drink to " + user.get(0).getName(), "", "", color, drink[0], null, null, null, drink[1])).queue();
                        return;
                    } else { //Serve a random drink
                        String[] drink = new RandomPhrase().getRandomServe(user.get(0));
                        message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(event.getAuthor().getName() + " served a drink to " + user.get(0).getName(), "", "", color, drink[0], null, null, null, drink[1])).queue();
                        return;
                    }
                } else { //handle a serve with no mention and specified drink
                    String[] drink = new RandomPhrase().getServe(event.getAuthor(), event.getArgs());
                    Color color = Color.BLUE;
                    try {
                        color = new EmbedWrapper().GetGuildEmbedColor(guild);
                    } catch (InvalidConfigurationException | IOException e) {
                        e.printStackTrace();
                    }
                    message.getChannel().sendMessage(new EmbedWrapper().EmbedMessage(event.getGuild().getJDA().getSelfUser().getName() + " served a drink to " + event.getAuthor().getName(), "", "", color, drink[0], null, null, null, drink[1])).queue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }
}