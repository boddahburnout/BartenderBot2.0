package darth.bartenderbot.Games;

import darth.bartenderbot.config.ConfigManager;
import darth.bartenderbot.utils.Deck.DeckUtils;
import darth.bartenderbot.utils.Discord.EmbedWrapper;
import darth.bartenderbot.utils.String.StringUtils;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlackJack {
    List deck = new DeckUtils().getDeck();
    public void CreateTable(TextChannel channel) {
        Activity.Emoji play = new Activity.Emoji(":play_pause:");
        String table = "React with "+ play.getName() +" to start";
        channel.deleteMessageById(channel.getLatestMessageId()).queue();
        channel.sendMessage(new EmbedWrapper().EmbedMessage("BlackJack Table", "No one playing", null, Color.BLUE, table, null, null, null, null)).queue(message1 -> {
            message1.addReaction("U+23efU+fe0f").queue();
        });
    }
    public void reactHandler(Guild guild, MessageReactionAddEvent event) {
        deck = new DeckUtils().shuffle(deck);
        try {
            String botid = guild.getSelfMember().getId();
            YamlFile botConfig = new ConfigManager().accessConfig();
            if (botid.equals(event.getUserId())) return;
            if (deck == null) {
                deck = new DeckUtils().getDeck();
                deck = new DeckUtils().shuffle(deck);
                System.out.println("Deck Shuffled");
            }
            if (deck.size() < 25) {
                deck = new DeckUtils().getDeck();
                deck = new DeckUtils().shuffle(deck);
                System.out.println("Deck Shuffled");
            }
            MessageReaction emote = event.getReaction();
            if (emote.getReactionEmote().toString().equalsIgnoreCase("RE:U+23efU+fe0f")) {
                event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
                    if (message.getEmbeds().get(0) == null) return;
                    java.util.List dealerhand = new ArrayList();
                    java.util.List playerhand = new ArrayList();
                    dealerhand.add(new DeckUtils().draw(deck));
                    playerhand.add(new DeckUtils().draw(deck));
                    playerhand.add(new DeckUtils().draw(deck));
                    Integer playersapi = new DeckUtils().blackjackvalue(playerhand);

                    StringBuilder handbuilder = new StringBuilder();
                    for (Object card : playerhand) {
                        handbuilder.append(new Emotes().getEmote(card)+" ");
                    }

                    StringBuilder dhandbuilder = new StringBuilder();
                    for (Object card : dealerhand) {
                        dhandbuilder.append(new Emotes().getEmote(card)+" ");
                    }

                    if (playersapi == 21) {
                        event.getChannel().retrieveMessageById(event.getMessageId()).queue(message1 -> {
                            if (message.getEmbeds().get(0) == null) return;
                            String embmsg = "\nDealer ("+new DeckUtils().blackjackvalue(dealerhand)+"): "+dhandbuilder.toString()+"\n\n"+event.getUser().getName()+" ("+new DeckUtils().blackjackvalue(playerhand)+"): "+handbuilder.toString()+"\n\nBlackJack! You win!\nReact with :play_pause: to start";
                            MessageEmbed bjtable = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, embmsg, null, null, null, null);
                            message1.editMessage(bjtable).queue();
                            message1.clearReactions().queue();
                            message1.addReaction("U+23efU+fe0f").queue();
                        });
                    }

                    String bjmessage = "Hit: :white_check_mark: \nStand: :negative_squared_cross_mark: \nDouble Down: :moneybag: \n\nDealer ("+new DeckUtils().blackjackvalue(dealerhand)+"): " + new Emotes().getEmote(dealerhand.get(0)) + " " + new Emotes().getEmote("?") + "\n\n" + event.getUser().getName() + " ("+new DeckUtils().blackjackvalue(playerhand)+"): " + new Emotes().getEmote(playerhand.get(0)) + " " + new Emotes().getEmote(playerhand.get(1));
                    if (message.getEmbeds().get(0).getTitle().trim().equalsIgnoreCase("BlackJack Table")) {
                        MessageEmbed bjtable = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, bjmessage, null, null, null, null);
                        message.editMessage(bjtable).queue();
                        message.clearReactions().queue();
                        message.addReaction("U+2705").queue();
                        message.addReaction("U+274E").queue();
                        message.addReaction("U+1F4B0").queue();
                    }
                });
            }
            if (emote.getReactionEmote().toString().equalsIgnoreCase("RE:U+2705")) {
                //hit
                event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
                    if (message.getEmbeds().get(0) == null) return;
                    StringBuilder stringBuilder = new StringBuilder();
                    Object hit = new DeckUtils().draw(deck);
                    String[] desc = message.getEmbeds().get(0).getDescription().split("\n");
                    List playerhand = new StringUtils().GetCardsFromEmote(desc[6].replaceAll("\\): ", ""));
                    playerhand.add(hit);
                    for  (int i = 0; i < desc.length; i++) {
                        if (i <= 5) {
                            stringBuilder.append(desc[i]+"\n");
                        } else {
                            stringBuilder.append(new StringUtils().UpdateScore(desc[i], new DeckUtils().blackjackvalue(playerhand)));
                            stringBuilder.append(" "+new Emotes().getEmote(hit));
                        }
                    }
                    MessageEmbed bjtable = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, stringBuilder.toString(), null, null, null, null);
                    message.editMessage(bjtable).queue();
                    StringBuilder playerbuilder = new StringBuilder();
                    if (new DeckUtils().blackjackvalue(playerhand) > 21) {
                        for (Object card : playerhand) {
                            playerbuilder.append(new Emotes().getEmote(card)+" ");
                        }
                        String losemsg = "\n\n"+event.getUser().getName()+" ("+new DeckUtils().blackjackvalue(playerhand)+"): "+playerbuilder.toString()+"\nYou busted! React with :play_pause: to play again";
                        MessageEmbed lose = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, losemsg, null, null, null, null);
                        message.editMessage(lose).queue();
                        message.clearReactions().queue();
                        message.addReaction("U+23efU+fe0f").queue();
                    }
                    if (new DeckUtils().blackjackvalue(playerhand) == 21) {
                        for (Object card : playerhand) {
                            playerbuilder.append(new Emotes().getEmote(card)+" ");
                        }
                        String embmsg = "\n\n"+event.getUser().getName()+" ("+new DeckUtils().blackjackvalue(playerhand)+"): "+playerbuilder.toString()+"\n\nBlackJack! You win!\nReact with :play_pause: to start";
                        MessageEmbed winmsg = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, embmsg, null, null, null, null);
                        message.editMessage(winmsg).queue();
                        message.clearReactions().queue();
                        message.addReaction("U+23efU+fe0f").queue();
                    }
                    message.removeReaction("U+2705", event.getUser()).queue();
                });
            }
            if (emote.getReactionEmote().toString().equalsIgnoreCase("RE:U+274E")) {
                //stand
                event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
                    if (message.getEmbeds().get(0) == null) return;
                    String desc = message.getEmbeds().get(0).getDescription();
                    MessageEmbed bjtable = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, desc, null, null, null, null);
                    String[] contents = bjtable.getDescription().split("\n");
                    StringBuilder stringBuilder = new StringBuilder();
                    java.util.List playerhand = new StringUtils().GetCardsFromEmote(contents[6].replaceAll("\\):", ""));
                    java.util.List dealerhand = new StringUtils().GetCardsFromEmote(contents[4].replaceAll("\\): ", ""));
                    message.removeReaction("U+274E", event.getUser()).queue();
                    for (int i = 0; i < contents.length; i++) {
                        if (i == 4) {
                            //dealers hand
                            Object ddraw = new DeckUtils().draw(deck);
                            dealerhand.add(new StringUtils().GetCardsFromEmote(contents[4].replaceAll("\\): ", " ")).get(0));
                            dealerhand.add(ddraw);
                            dealerhand.remove("hidden");
                            stringBuilder.append(contents[i].replaceAll( "<:hidden:7619509703407755316>", new Emotes().getEmote(ddraw)) + " ");
                            int dealersapi = new DeckUtils().blackjackvalue(dealerhand);
                            int sapi = new DeckUtils().blackjackvalue(playerhand);
                            if (dealersapi < 17) {
                                Boolean hitting = true;
                                while (hitting) {
                                    Object hit = new DeckUtils().draw(deck);
                                    dealerhand.add(hit);
                                    stringBuilder.append(" " + new Emotes().getEmote(hit));
                                    dealersapi = new DeckUtils().blackjackvalue(dealerhand);
                                    if (dealersapi >= 17) {
                                        stringBuilder.append("\n");
                                        hitting = false;
                                    }
                                }
                            } else {
                                stringBuilder.append("\n");
                            }

                            StringBuilder dealerbuilder = new StringBuilder();
                            StringBuilder playerbuilder = new StringBuilder();

                            for (Object card : dealerhand) {
                                dealerbuilder.append(new Emotes().getEmote(card)+" ");
                            }

                            for (Object card : playerhand) {
                                playerbuilder.append(new Emotes().getEmote(card)+" ");
                            }

                            if (dealersapi > 21) {
                                String embmsg = "\nDealer ("+new DeckUtils().blackjackvalue(dealerhand)+"): "+  dealerbuilder.toString()+"\n\n"+event.getUser().getName()+" ("+new DeckUtils().blackjackvalue(playerhand)+"): "+playerbuilder.toString()+"\n\nDealer bust, You win!\n\nReact with :play_pause: to start";
                                MessageEmbed winmsg = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, embmsg, null, null, null, null);
                                message.editMessage(winmsg).queue();
                                message.clearReactions().queue();
                                message.addReaction("U+23efU+fe0f").queue();
                                return;
                            } else {
                                if (dealersapi < sapi) {
                                    String embmsg = "\nDealer ("+new DeckUtils().blackjackvalue(dealerhand)+"): "+ dealerbuilder.toString()+"\n\n"+event.getUser().getName()+" ("+new DeckUtils().blackjackvalue(playerhand)+"): "+playerbuilder.toString()+"\n\nYou win!\nReact with :play_pause: to start";
                                    MessageEmbed winmsg = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, embmsg, null, null, null, null);
                                    message.editMessage(winmsg).queue();
                                    message.clearReactions().queue();
                                    message.addReaction("U+23efU+fe0f").queue();
                                    return;
                                }
                                if (dealersapi == sapi) {
                                    System.out.println(dealersapi);
                                    String embmsg = "\nDealer ("+new DeckUtils().blackjackvalue(dealerhand)+"): "+ dealerbuilder.toString()+"\n\n"+event.getUser().getName()+" ("+new DeckUtils().blackjackvalue(playerhand)+"): "+playerbuilder.toString()+"\n\nDraw! Bets returned!\nReact with :play_pause: to start";
                                    MessageEmbed winmsg = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, embmsg, null, null, null, null);
                                    message.editMessage(winmsg).queue();
                                    message.clearReactions().queue();
                                    message.addReaction("U+23efU+fe0f").queue();
                                    return;
                                }
                                if (dealersapi > sapi) {
                                    String embmsg = "\nDealer ("+new DeckUtils().blackjackvalue(dealerhand)+"): "+ dealerbuilder.toString()+"\n\n"+event.getUser().getName()+" ("+new DeckUtils().blackjackvalue(playerhand)+"): "+playerbuilder.toString()+"\n\nDealer has higher hand, you lose!\nReact with :play_pause: to start";
                                    MessageEmbed winmsg = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, embmsg, null, null, null, null);
                                    message.editMessage(winmsg).queue();
                                    message.clearReactions().queue();
                                    message.addReaction("U+23efU+fe0f").queue();
                                    return;
                                }
                            }
                        } else {
                            stringBuilder.append(contents[i] + "\n");
                        }
                    }
                });
            }
            if (emote.getReactionEmote().toString().equalsIgnoreCase("RE:U+1F4B0")) {
                event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
                    message.removeReaction("U+1F4B0", event.getUser()).queue();
                    String desc = message.getEmbeds().get(0).getDescription();
                    String[] ddownlines = desc.split("\n");
                    java.util.List playerhand = new StringUtils().GetCardsFromEmote(ddownlines[6].replaceAll("\\):", ""));
                    List dealerhand = new StringUtils().GetCardsFromEmote(ddownlines[4].replaceAll("\\): ", ""));
                    Object draw = new DeckUtils().draw(deck);
                    playerhand.add(draw);
                    dealerhand.remove("hidden");
                    int playersapi = new DeckUtils().blackjackvalue(playerhand);
                    int dealersapi = new DeckUtils().blackjackvalue(dealerhand);

                    StringBuilder dealerbuilder = new StringBuilder();
                    StringBuilder playerbuilder = new StringBuilder();

                    for (Object card : dealerhand) {
                        dealerbuilder.append(new Emotes().getEmote(card)+" ");
                    }

                    for (Object card : playerhand) {
                        playerbuilder.append(new Emotes().getEmote(card)+" ");
                    }

                    if (playersapi > 21) {
                        for (Object card : playerhand) {
                            playerbuilder.append(new Emotes().getEmote(card)+" ");
                        }
                        String losemsg = "\n\n"+event.getUser().getName()+" ("+new DeckUtils().blackjackvalue(playerhand)+"): "+playerbuilder.toString()+"\nYou busted! React with :play_pause: to play again";
                        MessageEmbed lose = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, losemsg, null, null, null, null);
                        message.editMessage(lose).queue();
                        message.clearReactions().queue();
                        message.addReaction("U+23efU+fe0f").queue();
                        return;
                    }
                    if (playersapi == 21) {
                        for (Object card : playerhand) {
                            playerbuilder.append(new Emotes().getEmote(card)+" ");
                        }
                        String embmsg = "\n\n"+event.getUser().getName()+" ("+new DeckUtils().blackjackvalue(playerhand)+"): "+playerbuilder.toString()+"\n\nBlackJack! You win!\nReact with :play_pause: to start";
                        MessageEmbed winmsg = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, embmsg, null, null, null, null);
                        message.editMessage(winmsg).queue();
                        message.clearReactions().queue();
                        message.addReaction("U+23efU+fe0f").queue();
                        return;
                    }

                    StringBuilder stringBuilder = new StringBuilder();

                    for (int i = 0; i < ddownlines.length; i++) {
                        if (i == 4) {

                            Object ddraw = new DeckUtils().draw(deck);

                            dealerhand.add(ddraw);

                            stringBuilder.append(ddownlines[i].replaceAll("<:hidden:761959703407755316>", new Emotes().getEmote(ddraw)) + " ");

                            System.out.print(stringBuilder);

                            if (dealersapi < 17) {
                                Boolean hitting = true;
                                while (hitting) {
                                    Object hit = new DeckUtils().draw(deck);
                                    dealerhand.add(hit);
                                    stringBuilder.append(" " + new Emotes().getEmote(hit));
                                    dealersapi = new DeckUtils().blackjackvalue(dealerhand);
                                    if (dealersapi >= 17) {
                                        stringBuilder.append("\n");
                                        hitting = false;
                                    }
                                }
                            } else {
                                stringBuilder.append("\n");
                            }

                            if (dealersapi > 21) {
                                String embmsg = "\nDealer (" + new DeckUtils().blackjackvalue(dealerhand) + "): " + dealerbuilder.toString() + "\n\n" + event.getUser().getName() + " (" + new DeckUtils().blackjackvalue(playerhand) + "): " + playerbuilder.toString() + "\n\nDealer bust, You win!\n\nReact with :play_pause: to start";
                                MessageEmbed winmsg = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, embmsg, null, null, null, null);
                                message.editMessage(winmsg).queue();
                                message.clearReactions().queue();
                                message.addReaction("U+23efU+fe0f").queue();
                                return;
                            } else {
                                if (dealersapi < playersapi) {
                                    String embmsg = "\nDealer (" + new DeckUtils().blackjackvalue(dealerhand) + "): " + dealerbuilder.toString() + "\n\n" + event.getUser().getName() + " (" + new DeckUtils().blackjackvalue(playerhand) + "): " + playerbuilder.toString() + "\n\nYou win!\nReact with :play_pause: to start";
                                    MessageEmbed winmsg = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, embmsg, null, null, null, null);
                                    message.editMessage(winmsg).queue();
                                    message.clearReactions().queue();
                                    message.addReaction("U+23efU+fe0f").queue();
                                    return;
                                }
                                if (dealersapi == playersapi) {
                                    System.out.println(dealersapi);
                                    String embmsg = "\nDealer (" + new DeckUtils().blackjackvalue(dealerhand) + "): " + dealerbuilder.toString() + "\n\n" + event.getUser().getName() + " (" + new DeckUtils().blackjackvalue(playerhand) + "): " + playerbuilder.toString() + "\n\nDraw! Bets returned!\nReact with :play_pause: to start";
                                    MessageEmbed winmsg = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, embmsg, null, null, null, null);
                                    message.editMessage(winmsg).queue();
                                    message.clearReactions().queue();
                                    message.addReaction("U+23efU+fe0f").queue();
                                    return;
                                }
                                if (dealersapi > playersapi) {
                                    String embmsg = "\nDealer (" + new DeckUtils().blackjackvalue(dealerhand) + "): " + dealerbuilder.toString() + "\n\n" + event.getUser().getName() + " (" + new DeckUtils().blackjackvalue(playerhand) + "): " + playerbuilder.toString() + "\n\nDealer has higher hand, you lose!\nReact with :play_pause: to start";
                                    MessageEmbed winmsg = new EmbedWrapper().EmbedMessage("BlackJack Table", event.getMember().getEffectiveName() + "'s table", null, Color.BLUE, embmsg, null, null, null, null);
                                    message.editMessage(winmsg).queue();
                                    message.clearReactions().queue();
                                    message.addReaction("U+23efU+fe0f").queue();
                                    return;
                                }
                            }
                        }
                    }
                });
            }
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }
}
