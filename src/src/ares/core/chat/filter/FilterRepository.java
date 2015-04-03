package src.ares.core.chat.filter;

public class FilterRepository
{
	private String[] words = new String[]
	{
	"chink", "chinky", "negro", "honkey", "spick", "National Socialist German Workers", "nazi", "hitler", "slut", "tits", "rape", "queer", "ballsack", "fag", "nazism", "deh fuhrer", "Ku Klux Klan", "noob", "stfu", "gtfo", "lamfo", "anus", "arse", "axewound", "bampot", "bastard", "beaner", "bitch", "blow job", "blowjob", "bollocks", "bollox", "boner", "brotherfucker", "bullshit", "bumblefuck", "butt", "camel toe", "carpetmuncher", "chinc", "chink", "choad", "chode", "clit", "clusterfuck", "cock", "coochie", "coochy", "coon", "cooter", "cracker", "cum", "cumbubble", "cunnie", "cunt", "dago", "damn", "deggo", "dick", "dipshit", "doochbag", "dookie", "douche", "dumpass", "dyke", "fag", "fatass", "fellatio", "feltch", "flamer", "fuck", "fucked", "fudgepacker", "gay", "goddamn", "gooch", "gook", "gringo", "guido", "handjob", "hard on", "heeb", "homo", "honkey", "humping", "jackass", "jagoff", "jap", "jerk off", "jerk", "jizz", "jungle bunny", "junglebunny", "kike", "kooch", "kootch", "kraunt", "kunt", "kyke", "lameass", "lardass", "lesbian", "lesbo", "lezzie", "mcfagget", "mick", "minge", "mothafucka", "motherfucker", "muff", "munging", "negro", "nigaboo", "nigga", "nigger", "niglet", "nut sack", "nutsack", "panooch", "pecker", "peckerhead", "penis", "piss", "polesmoker", "pollock", "porch monkey", "prick", "punanny", "punta", "pussy", "pussies", "puto", "poop", "fuck", "queef", "queer", "renob", "rimjob", "rusky", "schlong", "scrote", "shit", "shiz", "skank", "skeet", "slut", "smeg", "snatch", "spic", "spick", "splooge", "tard", "testicle", "thundercunt", "tit", "twat", "vagina", "wank", "wetback", "whore",
	};;

	private String[] replaces = new String[]
	{
		"****"
	};

	public String[] getReplaces()
	{
		return replaces;
	}

	public String[] getWords()
	{
		return words;
	}
}
