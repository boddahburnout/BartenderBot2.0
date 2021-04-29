package darth.bartenderbot.handler;

import darth.bartenderbot.config.ConfigManager;
import darth.bartenderbot.permission.PermCheck;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.util.TreeMap;

public class CommandHandler {
    public boolean iscommand(Guild guild, Message message) throws InvalidConfigurationException, IOException {
        if (message.getAuthor() == null || (message.getAuthor().isBot())) {
            return false;
        }
        YamlFile botConfig = new ConfigManager().accessConfig();
        return message.getContentRaw().startsWith(botConfig.getString(guild.getId()+".Prefix"));
    }
    public Map<String, String> getCommandData(Guild guild, Message message) throws InvalidConfigurationException, IOException {
        YamlFile botConfig = new ConfigManager().accessConfig();
        String msg = message.getContentDisplay();
        Map<String, String> commandData = new HashMap<>();
        String name = msg.replaceFirst((String) "\\"+botConfig.getString(guild.getId()+".Prefix"), "").split(" ") [0];
        String args = msg.replaceFirst((String) "\\"+botConfig.getString(guild.getId()+".Prefix"), "").replaceAll(name, "");
        commandData.put("name", name);
        commandData.put("args", args.replaceFirst(" ", ""));
        return commandData;
    }
    public Map<String, String> getDescription(Guild guild, Message message) throws InvalidConfigurationException, IOException {
        Map<String, String> cmdDesc = new TreeMap<>();
        cmdDesc.put("0", "guilds: See guilds that i'm currently active in");
        cmdDesc.put("1", "viewcard: View a full size image of a magic card. args <Card Name>");
        cmdDesc.put("2", "mtg: Get a overview of a magic card. args <Card Name>");
        cmdDesc.put("3", "strain: Get an overview of a strains info. args <Strain name>");
        cmdDesc.put("4", "invite: Get an invite link to invite me to your servers!");
        cmdDesc.put("5", "join: Ask the bot to join the vc with you");
        cmdDesc.put("6", "leave: Ask the bot to leave the voice channel it's currently connected to");
        cmdDesc.put("7", "skip: Skip the song currently playing");
        cmdDesc.put("8", "playing: See what song is playing now");
        cmdDesc.put("9", "drinks: See what drinks are available for order");
        cmdDesc.put("10", "echo: Monkey see, Monkey do, Make this bot talk for you");
        cmdDesc.put("11", "serve: Order yourself, or another user a drink. Optional args: <drink> <recipient>");
        cmdDesc.put("12", "queue: See the songs in the queue");
        if (new PermCheck().CheckGuildRole(guild, message.getMember())) {
            cmdDesc.put("13", "setbr: Set the bartender roles for the server, this gives these roles access to bot commands");
            cmdDesc.put("14", "adddrink: Add a drink to to the serve system");
            cmdDesc.put("15", "performance: View ram and disk usage");
            cmdDesc.put("16", "setwr: Set the staff role to mention for welcoming users");
            cmdDesc.put("17", "setwelcome: Set the channel to welcome new users in");
            cmdDesc.put("18", "setprefix: Set the bots prefix for your guild");
            cmdDesc.put("19", "bjtable: Open up a stationary BlackJack table in a channel *warning* not fully stable/finished");
            cmdDesc.put("20", "ban: Remove a users ability to interact with the bot in your guild");
            cmdDesc.put("21", "unban: Remove user from banlist");
            cmdDesc.put("22", "addcat: Add a drink category");
            cmdDesc.put("23", "removecat: Remove a category");
            cmdDesc.put("24", "testjoin: Send a fake new join to test your welcome message setup");
            cmdDesc.put("25", "setrgb: Set the color of embed messages for your guild args <r> <g> <b>");
            cmdDesc.put("26", "addemote: Add an emote image to an existing cat");
        }
        return cmdDesc;
    }
}
