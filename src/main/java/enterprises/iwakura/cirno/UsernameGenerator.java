package enterprises.iwakura.cirno;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.experimental.UtilityClass;

/**
 * Username generate that unique username from prefix, suffix, and 4 number discriminator
 */
@UtilityClass
public class UsernameGenerator {

    public static final Random RANDOM = new Random();
    public static final List<String> PREFIX = new ArrayList<>();
    public static final List<String> SUFFIX = new ArrayList<>();

    static {
        PREFIX.add("Reimu");
        PREFIX.add("Marisa");
        PREFIX.add("Sakuya");
        PREFIX.add("Remilia");
        PREFIX.add("Flandre");
        PREFIX.add("Patchouli");
        PREFIX.add("Koakuma");
        PREFIX.add("Meiling");
        PREFIX.add("Alice");
        PREFIX.add("Shanghai");
        PREFIX.add("Youmu");
        PREFIX.add("Yuyuko");
        PREFIX.add("Yukari");
        PREFIX.add("Ran");
        PREFIX.add("Chen");
        PREFIX.add("Suika");
        PREFIX.add("Tenshi");
        PREFIX.add("Iku");
        PREFIX.add("Komachi");
        PREFIX.add("Eiki");
        PREFIX.add("Reisen");
        PREFIX.add("Tewi");
        PREFIX.add("Eirin");
        PREFIX.add("Kaguya");
        PREFIX.add("Mokou");
        PREFIX.add("Keine");
        PREFIX.add("Cirno");
        PREFIX.add("Daiyousei");
        PREFIX.add("Rumia");
        PREFIX.add("Wriggle");
        PREFIX.add("Mystia");
        PREFIX.add("Nitori");
        PREFIX.add("Momiji");
        PREFIX.add("Aya");
        PREFIX.add("Sanae");
        PREFIX.add("Kanako");
        PREFIX.add("Suwako");
        PREFIX.add("Kogasa");
        PREFIX.add("Parsee");
        PREFIX.add("Yamame");
        PREFIX.add("Yuugi");
        PREFIX.add("Satori");
        PREFIX.add("Rin");
        PREFIX.add("Utsuho");
        PREFIX.add("Koishi");
        PREFIX.add("Nazrin");
        PREFIX.add("Shou");
        PREFIX.add("Ichirin");
        PREFIX.add("Murasa");
        PREFIX.add("Byakuren");
        PREFIX.add("Nue");
        PREFIX.add("Hatate");
        PREFIX.add("Seija");
        PREFIX.add("Shinmyoumaru");
        PREFIX.add("Seiran");
        PREFIX.add("Ringo");
        PREFIX.add("Doremy");
        PREFIX.add("Sagume");
        PREFIX.add("Clownpiece");
        PREFIX.add("Hecatia");
        PREFIX.add("Junko");
        PREFIX.add("Okina");
        PREFIX.add("Nemuno");
        PREFIX.add("Aunn");
        PREFIX.add("Narumi");
        PREFIX.add("Satono");
        PREFIX.add("Mai");
        PREFIX.add("Reimu");
        PREFIX.add("Keiki");
        PREFIX.add("Mayumi");
        PREFIX.add("Kutaka");
        PREFIX.add("Yachie");
        PREFIX.add("Saki");
        PREFIX.add("Eika");
        PREFIX.add("Urumi");
        PREFIX.add("Zanmu");
        PREFIX.add("Misumaru");
        PREFIX.add("Enoko");
        PREFIX.add("Chiyari");
        PREFIX.add("Isonade");
        PREFIX.add("Hisami");
        PREFIX.add("Larva");

        SUFFIX.add("Gensokyo");
        SUFFIX.add("Hakurei");
        SUFFIX.add("Scarlet");
        SUFFIX.add("Voile");
        SUFFIX.add("Hakugyokurou");
        SUFFIX.add("Mayohiga");
        SUFFIX.add("Moriya");
        SUFFIX.add("Youkai");
        SUFFIX.add("Misty");
        SUFFIX.add("Eientei");
        SUFFIX.add("Bamboo");
        SUFFIX.add("Netherworld");
        SUFFIX.add("Bhava");
        SUFFIX.add("Higan");
        SUFFIX.add("Sanzu");
        SUFFIX.add("Palace");
        SUFFIX.add("Chireiden");
        SUFFIX.add("Underground");
        SUFFIX.add("Myouren");
        SUFFIX.add("Byakuren");
        SUFFIX.add("Hokkai");
        SUFFIX.add("Lunar");
        SUFFIX.add("Lunarian");
        SUFFIX.add("Crimson");
        SUFFIX.add("Akutagawa");
        SUFFIX.add("Makai");
        SUFFIX.add("Shrine");
        SUFFIX.add("Forest");
        SUFFIX.add("Lake");
        SUFFIX.add("Mountain");
        SUFFIX.add("Garden");
        SUFFIX.add("Riverside");
        SUFFIX.add("Sunflower");
        SUFFIX.add("Cemetery");
        SUFFIX.add("Village");
        SUFFIX.add("Border");
        SUFFIX.add("Gap");
        SUFFIX.add("Boundary");
        SUFFIX.add("Phantasm");
        SUFFIX.add("Dreaming");
        SUFFIX.add("Spark");
        SUFFIX.add("Danmaku");
        SUFFIX.add("Spellcard");
        SUFFIX.add("Eternal");
        SUFFIX.add("Celestial");
        SUFFIX.add("Divine");
        SUFFIX.add("Frozen");
        SUFFIX.add("Youkai");
        SUFFIX.add("Spirit");
        SUFFIX.add("Phantom");
        SUFFIX.add("Oni");
        SUFFIX.add("Tengu");
        SUFFIX.add("Kappa");
        SUFFIX.add("Fairy");
        SUFFIX.add("Witch");
        SUFFIX.add("Miko");
        SUFFIX.add("Shrine");
        SUFFIX.add("Twilight");
        SUFFIX.add("Scarlet");
        SUFFIX.add("Frosty");
        SUFFIX.add("Vengeful");
        SUFFIX.add("Immortal");
        SUFFIX.add("Lunar");
        SUFFIX.add("Blazing");
        SUFFIX.add("Nuclear");
        SUFFIX.add("Poltergeist");
        SUFFIX.add("Satori");
        SUFFIX.add("Komeiji");
        SUFFIX.add("Izayoi");
        SUFFIX.add("Hinanawi");
        SUFFIX.add("Yakumo");
        SUFFIX.add("Fujiwara");
        SUFFIX.add("Houraisan");
        SUFFIX.add("Yagokoro");
    }

    /**
     * Generates a random username based on prefixes and suffixes, incl. discriminator. Should be unique. Maybe.
     *
     * @return Username
     */
    public static String generate() {
        String prefix = PREFIX.get(RANDOM.nextInt(PREFIX.size()));
        String suffix = SUFFIX.get(RANDOM.nextInt(SUFFIX.size()));
        int number = RANDOM.nextInt(9999);
        return prefix + suffix + "#" + number;
    }
}
