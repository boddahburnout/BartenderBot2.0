package darth.bartenderbot.Games;

public class Emotes {
    public String getEmote(Object emote) {
        String em = emote.toString();
        em = em.replace(em.charAt(0), em.toUpperCase().charAt(0));
        if (em.contains("K")) {
            if (em.contains("r")) {
                return "<:Kr:761805671988985906>";
            }
            if (em.contains("b")) {
                return "<:Kb:761805671645184012>";
            }
        }
        if (em.contains("Q")) {
            if (em.contains("r")) {
                return "<:Qr:761805671934722048>";
            }
            if (em.contains("b")) {
                return "<:Qb:761805672013889556>";
            }
        }
        if (em.contains("J")) {
            if (em.contains("r")) {
                return "<:Jr:761805671909687336>";
            }
            if (em.contains("b")) {
                return "<:Jb:761805671897235466>";
            }
        }
        if (em.contains("A")) {
            if (em.contains("r")) {
                return "<:Ar:761805671653572609>";
            }
            if (em.contains("b")) {
                return "<:ab:761805671707836477>";
            }
        }
        if (em.contains("10")) {
            if (em.contains("r")) {
                return "<:10r:761805671846117396>";
            }
            if (em.contains("b")) {
                return "<:10b:761805671762624532>";
            }
        }
        if (em.contains("9")) {
            if (em.contains("r")) {
                return "<:9r:761805671418036265>";
            }
            if (em.contains("b")) {
                return "<:9b:761805671687258162>";
            }
        }
        if (em.contains("8")) {
            if (em.contains("r")) {
                return "<:8r:761805671661436938>";
            }
            if (em.contains("b")) {
                return "<:8b:761805671682801664>";
            }
        }
        if (em.contains("7")) {
            if (em.contains("r")) {
                return "<:7r:761805671669956640>";
            }
            if (em.contains("b")) {
                return "<:7b:761805671745716244>";
            }
        }
        if (em.contains("6")) {
            if (em.contains("r")) {
                return "<:6r:761805671691190292>";
            }
            if (em.contains("b")) {
                return "<:6b:761805671603372102>";
            }
        }
        if (em.contains("5")) {
            if (em.contains("r")) {
                return "<:5r:761805671623557151>";
            }
            if (em.contains("b")) {
                return "<:5b:761805671644528650>";
            }
        }
        if (em.contains("4")) {
            if (em.contains("r")) {
                return "<:4r:761805671300595713>";
            }
            if (em.contains("b")) {
                return "<:4b:761805671683326012>";
            }
        }
        if (em.contains("3")) {
            if (em.contains("r")) {
                return "<:3r:761805671669956618>";
            }
            if (em.contains("b")) {
                return "<:3b:761805671498252348>";
            }
        }
        if (em.contains("2")) {
            if (em.contains("r")) {
                return "<:2r:761805670034833448>";
            }
            if (em.contains("b")) {
                return "<:2b:761805669706891265>";
            }
        }
        if (em.contains("?")) {
            return "<:hidden:761959703407755316>";
        }
        return emote.toString();
    }
}
