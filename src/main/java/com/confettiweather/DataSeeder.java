package com.confettiweather;

import com.confettiweather.model.Lyric;
import com.confettiweather.model.Lyric.SectionType;
import com.confettiweather.model.Song;
import com.confettiweather.model.SiteContent;
import com.confettiweather.repository.LyricRepository;
import com.confettiweather.repository.SongRepository;
import com.confettiweather.service.ArtistProfileService;
import com.confettiweather.service.SiteContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final SongRepository songRepository;
    private final LyricRepository lyricRepository;
    private final SiteContentService siteContentService;
    private final ArtistProfileService artistProfileService;

    private static final String ALBUM_SPOTIFY = "https://open.spotify.com/album/0BB8BawGzPa6yNdyf9vGBb";
    private static final String GENRE = "Indie · Industrial Static · Moody Pop · Rock";
    private static final int YEAR = 2026;

    @Override
    public void run(String... args) {
        seedSiteContent();
        seedArtistProfile();
        if (songRepository.count() > 0) {
            log.info("DataSeeder: database already seeded, skipping.");
            return;
        }
        log.info("DataSeeder: seeding Confetti Weather debut album (11 tracks)...");

        Song song1 = seedTrack(1, "Marigold and Gasoline",
                "https://open.spotify.com/track/6MCcLGRTs1Nz6ygPl9m3K8",
                "https://open.spotify.com/embed/track/6MCcLGRTs1Nz6ygPl9m3K8?utm_source=oembed",
                "Kitchen light was gold at six a.m. Your hair still tangled from the wind...",
                true);
        seedLyricsMarigoldAndGasoline(song1);

        Song song2 = seedTrack(2, "Cathedral Eyes",
                "https://open.spotify.com/track/533xLcNtoUtktC7L83Q24G",
                "https://open.spotify.com/embed/track/533xLcNtoUtktC7L83Q24G?utm_source=oembed",
                "Walked into the room like a doorway. Didn't know that light could feel this loud...",
                true);
        seedLyricsCathedralEyes(song2);

        Song song3 = seedTrack(3, "Confetti Weather", null, null,
                "Woke up and the sky forgot to frown. Grabbed my jacket, left the ironing board down...", true);
        seedLyricsConfettiWeather(song3);

        Song song4 = seedTrack(4, "Low Tide Confessions", null, null,
                "The water pulled back past the jetty. Left the whole shoreline undressed...", false);
        seedLyricsLowTideConfessions(song4);

        Song song5 = seedTrack(5, "Slow Bloom, Fast Fade", null, null,
                "Took you so many years to open. Petal by petal, patient and slow...", false);
        seedLyricsSlowBloomFastFade(song5);

        Song song6 = seedTrack(6, "Vexed", null, null,
                "Same six words, different night. Same closed door, same porch light...", false);
        seedLyricsVexed(song6);

        Song song7 = seedTrack(7, "Serpent in the System", null, null,
                "Everything was wired correctly. Every circuit doing what it's told...", false);
        seedLyricsSerpentInTheSystem(song7);

        Song song8 = seedTrack(8, "Neon Apology", null, null,
                "I've got a mouth like a struck match. Quick to burn when I feel cornered...", false);
        seedLyricsNeonApology(song8);

        Song song9 = seedTrack(9, "Concrete Halo", null, null,
                "Streetlight caught you at the crosswalk. Turned the exhaust to something gold...", false);
        seedLyricsConcreteHalo(song9);

        Song song10 = seedTrack(10, "The Weight of Windows", null, null,
                "You keep the blinds at half attention. Enough light in, not enough to see...", false);
        seedLyricsTheWeightOfWindows(song10);

        Song song11 = seedTrack(11, "The Keeping", null, null,
                "They placed me in your arms still warm. Before you'd learned to hold your own breath...", false);
        seedLyricsTheKeeping(song11);

        log.info("DataSeeder: all 11 tracks seeded successfully.");
    }

    private Song seedTrack(int trackNum, String title, String spotifyUrl, String embedUrl,
                           String description, boolean featured) {
        Song song = new Song();
        song.setTitle(title);
        song.setSpotifyUrl(spotifyUrl);
        song.setEmbedUrl(embedUrl);
        song.setImageUrl(null);
        song.setGenre(GENRE);
        song.setReleaseYear(YEAR);
        song.setAiToolsUsed("Suno");
        song.setFeaturedStatus(featured);
        song.setDisplayOrder(trackNum);
        song.setDescription(description);
        return songRepository.save(song);
    }

    private void seedLyricsMarigoldAndGasoline(Song song) {
        int order = 1;
        order = addLyric(song, "Verse 1", SectionType.VERSE, order, "Kitchen light was gold at six a.m.\nYour hair still tangled from the wind\nI didn't know that beautiful\nCould burn me from within");
        order = addLyric(song, "Verse 2", SectionType.VERSE, order, "You kept matches in your pocket\nJust in case the night got cold\nSaid you never meant to torch it\nJust wanted something to hold");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "You were marigold and gasoline\nPrettiest thing I'd ever seen\nStrike a match and watch it go\nI should've known, I should've known\nMarigold and gasoline\nBurned so bright, so in between\nLiving and letting go");
        order = addLyric(song, "Verse 3", SectionType.VERSE, order, "Neighbors said they smelled the smoke\nBefore they heard us start to break\nFunny how the prettiest things\nAre always the first mistake");
        order = addLyric(song, "Bridge", SectionType.BRIDGE, order, "And if I could go back\nI'd still walk into the flame\n'Cause some things worth losing\nAre worth catching all the same");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "You were marigold and gasoline\nPrettiest thing I'd ever seen\nStrike a match and watch it go\nI should've known, I should've known\nMarigold and gasoline\nBurned so bright, so in between\nLiving and letting go");
        addLyric(song, "Outro", SectionType.OUTRO, order, "Ash on the windowsill\nSmoke still hanging in the hall\nMarigold... marigold...\nI loved you through it all");
    }

    private void seedLyricsCathedralEyes(Song song) {
        int order = 1;
        order = addLyric(song, "Verse 1", SectionType.VERSE, order, "Walked into the room like a doorway\nDidn't know that light could feel this loud\nStained glass scattered on your shoulders\nAnd I forgot how to be proud");
        order = addLyric(song, "Verse 2", SectionType.VERSE, order, "You looked at me like I was something\nWorth the weight of all that empty space\nI've never told the truth this quiet\nNever knelt for anyone's face");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "Cathedral eyes, cathedral eyes\nHolding me up to the sky\nI don't know if I'm the kind\nThat's built to stand in that kind of light\nCathedral eyes, cathedral eyes\nShow me every flaw, every lie\nStill I'm staying, still I'm staying\nUnderneath your cathedral eyes");
        order = addLyric(song, "Verse 3", SectionType.VERSE, order, "Every echo sounds like a secret\nEvery silence feels like a prayer\nI keep waiting for the walls to crumble\nBut you just keep on looking there");
        order = addLyric(song, "Bridge", SectionType.BRIDGE, order, "Maybe I'm not worthy of the view\nMaybe grace was never mine to earn\nBut you built me something out of nothing\nAnd I don't know how to not return");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "Cathedral eyes, cathedral eyes\nHolding me up to the sky\nI don't know if I'm the kind\nThat's built to stand in that kind of light\nCathedral eyes, cathedral eyes\nShow me every flaw, every lie\nStill I'm staying, still I'm staying\nUnderneath your cathedral eyes");
        addLyric(song, "Outro", SectionType.OUTRO, order, "Light through the window, falling slow\nCathedral... cathedral...\nI'll stay as long as you let me know\nThat I'm allowed to stay here");
    }

    private void seedLyricsConfettiWeather(Song song) {
        int order = 1;
        order = addLyric(song, "Verse 1", SectionType.VERSE, order, "Woke up and the sky forgot to frown\nGrabbed my jacket, left the ironing board down\nRadio static turned into a song\nSomething about not being gone too long");
        order = addLyric(song, "Verse 2", SectionType.VERSE, order, "You called it luck, I called it timing\nEither way the morning's shining\nThrew the windows open, let the noise in\nBest decisions always start with giving in");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "It's confetti weather, throw it all away\nEvery worry folded up, forecast says we're okay\nConfetti weather, colors in the air\nI don't need a reason, I just need you there\nConfetti weather, everywhere");
        order = addLyric(song, "Verse 3", SectionType.VERSE, order, "Neighbors think we're crazy, dancing on the stoop\nDoesn't matter, we're not asking for the group\nSome days feel like static, some days feel like sound\nToday the whole block's spinning round and round");
        order = addLyric(song, "Bridge", SectionType.BRIDGE, order, "Maybe it won't last past Tuesday\nMaybe rain's already on its way\nBut right now the light's doing something\nSo right now, that's all I'm gonna say");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "It's confetti weather, throw it all away\nEvery worry folded up, forecast says we're okay\nConfetti weather, colors in the air\nI don't need a reason, I just need you there\nConfetti weather, everywhere");
        addLyric(song, "Outro", SectionType.OUTRO, order, "Confetti weather, kicking up the street\nConfetti weather, dancing on our feet\nLet it fall, let it fall, let it fall\nConfetti weather, that's all");
    }

    private void seedLyricsLowTideConfessions(Song song) {
        int order = 1;
        order = addLyric(song, "Verse 1", SectionType.VERSE, order, "The water pulled back past the jetty\nLeft the whole shoreline undressed\nEvery stone we ever threw here\nSitting still, catching its breath");
        order = addLyric(song, "Verse 2", SectionType.VERSE, order, "I've been holding something small and heavy\nLike a shell closed up too tight\nFigured if the noise would just recede\nI could finally get it right");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "These are low tide confessions\nOnly said when the water's gone\nEverything we kept submerged\nSitting out here in the dawn\nLow tide confessions\nNo current left to hide behind\nJust the truth and the empty sand\nAnd however long it takes to climb");
        order = addLyric(song, "Verse 3", SectionType.VERSE, order, "You always said I talk in riddles\nWhen the tide was coming in\nMaybe I needed all that distance\nJust to let you in again");
        order = addLyric(song, "Bridge", SectionType.BRIDGE, order, "So here's the wreck I never mentioned\nHere's the reason for the drift\nNot one storm that pulled us under\nJust the slow, quiet lift");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "These are low tide confessions\nOnly said when the water's gone\nEverything we kept submerged\nSitting out here in the dawn\nLow tide confessions\nNo current left to hide behind\nJust the truth and the empty sand\nAnd however long it takes to climb");
        addLyric(song, "Outro", SectionType.OUTRO, order, "Water's creeping back already\nCovering the lines we drew\nLow tide... low tide...\nSay it now before it's through");
    }

    private void seedLyricsSlowBloomFastFade(Song song) {
        int order = 1;
        order = addLyric(song, "Verse 1", SectionType.VERSE, order, "Took you so many years to open\nPetal by petal, patient and slow\nI watched you turn from someone guarded\nInto the warmest light I know");
        order = addLyric(song, "Verse 2", SectionType.VERSE, order, "Took one night to watch it wilting\nOne conversation, one closed door\nDidn't think a thing built over seasons\nCould disappear in an hour or");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "Slow bloom, fast fade\nThat's just how the whole thing's made\nYears to open, a second to close\nNobody warns you how that goes\nSlow bloom, fast fade\nI'm still standing in the shade\nWhere something used to grow so bright\nGone before I said goodnight");
        order = addLyric(song, "Verse 3", SectionType.VERSE, order, "I kept the water changed and steady\nKept the light just right for you\nDidn't know that kind of tending\nCouldn't stop what you'd walk through");
        order = addLyric(song, "Bridge", SectionType.BRIDGE, order, "Maybe some things aren't meant to last\nJust meant to show you what light could be\nA season's worth of something golden\nBefore it all came down on me");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "Slow bloom, fast fade\nThat's just how the whole thing's made\nYears to open, a second to close\nNobody warns you how that goes\nSlow bloom, fast fade\nI'm still standing in the shade\nWhere something used to grow so bright\nGone before I said goodnight");
        addLyric(song, "Outro", SectionType.OUTRO, order, "Petals on the windowsill\nCurling in like they knew\nSlow bloom... fast fade...\nI'm still in love with the shape of you");
    }

    private void seedLyricsVexed(Song song) {
        int order = 1;
        order = addLyric(song, "Verse 1", SectionType.VERSE, order, "Same six words, different night\nSame closed door, same porch light\nI keep circling the block\nLike the block's gonna talk");
        order = addLyric(song, "Verse 2", SectionType.VERSE, order, "You say I'm reading into static\nI say the static's got a code\nEvery silence feels dramatic\nWhen you're the only one who knows");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "I'm vexed, I'm wound up tight\nChasing something with no shape\nVexed, arguing with the light\nLooking for a way to escape\nA feeling I can't name\nA itch I can't reach\nVexed, and it's always\nThe same six words, the same six words");
        order = addLyric(song, "Verse 3", SectionType.VERSE, order, "Tried to sleep it off on Tuesday\nTried to drown it out on Wednesday night\nThursday came in swinging\nFriday I forgot to fight");
        order = addLyric(song, "Bridge", SectionType.BRIDGE, order, "Maybe I'm the one repeating\nMaybe I'm the loop, not you\nMaybe every door I'm reading\nWas already halfway through");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "I'm vexed, I'm wound up tight\nChasing something with no shape\nVexed, arguing with the light\nLooking for a way to escape\nA feeling I can't name\nA itch I can't reach\nVexed, and it's always\nThe same six words, the same six words");
        addLyric(song, "Outro", SectionType.OUTRO, order, "Six words, same night\nSix words, porch light\nVexed... vexed...\nI'll let it go when I'm ready to");
    }

    private void seedLyricsSerpentInTheSystem(Song song) {
        int order = 1;
        order = addLyric(song, "Verse 1", SectionType.VERSE, order, "Everything was wired correctly\nEvery circuit doing what it's told\nI built this life like a machine\nRan it clean, ran it cold");
        order = addLyric(song, "Verse 2", SectionType.VERSE, order, "Didn't notice when the current shifted\nDidn't feel the first short in the line\nSome kind of rot moves real slow\nTill it's already yours and mine");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "There's a serpent in the system\nCoiled up behind the wall\nWearing my face, speaking my language\nWaiting for the whole thing to fall\nSerpent in the system\nI built the garden, I let it in\nI keep calling it the damage\nWhen I know it's just my skin");
        order = addLyric(song, "Verse 3", SectionType.VERSE, order, "You trusted every green light\nEvery signal set to go\nDidn't know I was rewriting\nThe one thing you needed to know");
        order = addLyric(song, "Bridge", SectionType.BRIDGE, order, "Maybe every system's got one\nSome old hunger coded in the frame\nMaybe I've just been the reason\nEvery safe thing burns the same");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "There's a serpent in the system\nCoiled up behind the wall\nWearing my face, speaking my language\nWaiting for the whole thing to fall\nSerpent in the system\nI built the garden, I let it in\nI keep calling it the damage\nWhen I know it's just my skin");
        addLyric(song, "Outro", SectionType.OUTRO, order, "Sparks behind the firewall\nNobody's found the source\nSerpent in the system... serpent in the system...\nI was always the cause");
    }

    private void seedLyricsNeonApology(Song song) {
        int order = 1;
        order = addLyric(song, "Verse 1", SectionType.VERSE, order, "I've got a mouth like a struck match\nQuick to burn when I feel cornered\nSaid the thing that drew the blood\nJust to prove that I was armored");
        order = addLyric(song, "Verse 2", SectionType.VERSE, order, "There's a list I don't say out loud\nEvery door I let slam shut\nEvery silence used as a weapon\nEvery \"sorry\" I never brought up");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "This is my neon apology\nLit up so you can't miss the sign\nI know the light comes years too late\nBut I need you to see it shine\nNeon apology\nBuzzing bright above the door\nI can't take back what I did\nBut I'm not hiding anymore");
        order = addLyric(song, "Verse 3", SectionType.VERSE, order, "Small cuts add up like a ledger\nJokes that landed like a fist\nEvery time I chose the exit\nOver staying till the end of it");
        order = addLyric(song, "Bridge", SectionType.BRIDGE, order, "I used to think being right\nWas worth more than being kind\nTurns out the ones I wounded\nAre the ones still on my mind");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "This is my neon apology\nLit up so you can't miss the sign\nI know the light comes years too late\nBut I need you to see it shine\nNeon apology\nBuzzing bright above the door\nI can't take back what I did\nBut I'm not hiding anymore");
        addLyric(song, "Outro", SectionType.OUTRO, order, "Some of you won't hear this\nSome of you moved on, and that's fair\nNeon apology... neon apology...\nStill lit, even if you're not there");
    }

    private void seedLyricsConcreteHalo(Song song) {
        int order = 1;
        order = addLyric(song, "Verse 1", SectionType.VERSE, order, "Streetlight caught you at the crosswalk\nTurned the exhaust to something gold\nEverybody rushing past you\nNever noticed what I saw unfold");
        order = addLyric(song, "Verse 2", SectionType.VERSE, order, "Ten years working nights and doubles\nNever asked for what you're owed\nStill you carry it so easy\nLike the weight was never load");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "You wear a concrete halo\nHard-won, never handed down\nNot the kind that comes from heaven\nJust the kind you build up off the ground\nConcrete halo\nCracked but holding all the same\nNobody gave you that kind of shine\nYou poured it, you set it, you became");
        order = addLyric(song, "Verse 3", SectionType.VERSE, order, "I used to think grace looked softer\nSomething distant, something clean\nDidn't know it built in parking lots\nIn the space between machines");
        order = addLyric(song, "Bridge", SectionType.BRIDGE, order, "Maybe sainthood's not a feeling\nMaybe it's just showing up\nYear on year on year of trying\nNever asking for enough");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "You wear a concrete halo\nHard-won, never handed down\nNot the kind that comes from heaven\nJust the kind you build up off the ground\nConcrete halo\nCracked but holding all the same\nNobody gave you that kind of shine\nYou poured it, you set it, you became");
        addLyric(song, "Outro", SectionType.OUTRO, order, "Streetlight's still there at the corner\nStill catching dust like it's not through\nConcrete halo... concrete halo...\nI see it, I see it, I see you");
    }

    private void seedLyricsTheWeightOfWindows(Song song) {
        int order = 1;
        order = addLyric(song, "Verse 1", SectionType.VERSE, order, "You keep the blinds at half attention\nEnough light in, not enough to see\nI've memorized the shape you make in shadow\nStanding there but never facing me");
        order = addLyric(song, "Verse 2", SectionType.VERSE, order, "I've gotten good at reading weather\nThrough the glass instead of through your face\nEvery season passes right in front of me\nAnd I still don't know this place");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "This is the weight of windows\nWatching close but held outside\nEvery pane of glass between us\nJust another way to hide\nThe weight of windows\nGetting heavier each night\nI can see you but I can't reach you\nStanding here in borrowed light");
        order = addLyric(song, "Verse 3", SectionType.VERSE, order, "Maybe you were built for viewing\nNever built to be walked through\nMaybe I've been standing in this doorway\nLonger than I ever knew");
        order = addLyric(song, "Bridge", SectionType.BRIDGE, order, "So tell me if the glass is coming down\nOr if I'm meant to always stand this far\nBecause I'm tired of loving silhouettes\nAnd calling it a kind of art");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "This is the weight of windows\nWatching close but held outside\nEvery pane of glass between us\nJust another way to hide\nThe weight of windows\nGetting heavier each night\nI can see you but I can't reach you\nStanding here in borrowed light");
        addLyric(song, "Outro", SectionType.OUTRO, order, "Light's still moving cross the floorboards\nShapes still shifting on the wall\nThe weight of windows... the weight of windows...\nSome things never open up at all");
    }

    private void seedLyricsTheKeeping(Song song) {
        int order = 1;
        order = addLyric(song, "Verse 1", SectionType.VERSE, order, "They placed me in your arms still warm\nBefore you'd learned to hold your own breath\nYou didn't know a person could weigh\nBoth nothing and everything at once");
        order = addLyric(song, "Verse 2", SectionType.VERSE, order, "Some nights you sat up counting breaths\nA washcloth cooling on my head\nLearned the particular kind of prayer\nThat doesn't need a single word said");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "This is the keeping\nThe thing we do without being told\nHolding on so someone else\nDoesn't have to be alone in the cold\nThe keeping\nPassed from hand to hand to hand\nNobody teaches you the reasons\nYou just learn to understand");
        order = addLyric(song, "Verse 3", SectionType.VERSE, order, "Now I'm the one who counts your breathing\nNow I'm the one who holds your hand\nThe hands that used to reach up toward you\nNow help you the best I can");
        order = addLyric(song, "Bridge", SectionType.BRIDGE, order, "And when your breathing finally slowed\nI held on like you held me, the day I came\nSame hands, same fear, same fierce devotion\nJust the roles reversed, but the love unchanged");
        order = addLyric(song, "Chorus", SectionType.CHORUS, order, "This is the keeping\nThe thing we do without being told\nHolding on so someone else\nDoesn't have to be alone in the cold\nThe keeping\nPassed from hand to hand to hand\nNobody teaches you the reasons\nYou just learn to understand");
        addLyric(song, "Outro", SectionType.OUTRO, order, "Standing at the stone, the ground still soft\nI'm crying, but I'm not broken\nLook how much I got to know\nThe keeping... the keeping...\nSomebody small is holding my hand now\nThat's as close as we get to forever");
    }

    private int addLyric(Song song, String label, SectionType type, int order, String content) {
        Lyric lyric = new Lyric();
        lyric.setSong(song);
        lyric.setSectionLabel(label);
        lyric.setSectionType(type);
        lyric.setContent(content);
        lyric.setDisplayOrder(order);
        lyricRepository.save(lyric);
        return order + 1;
    }

    private void seedArtistProfile() {
        com.confettiweather.model.ArtistProfile profile = artistProfileService.getProfile();
        if (profile.getWebsiteUrl() == null || profile.getWebsiteUrl().isBlank()) {
            profile.setName("Confetti Weather");
            profile.setWebsiteUrl("");
            profile.setTagline("Indie · Industrial Static · Moody Pop · Rock");
            profile.setSpotifyUrl("https://open.spotify.com/album/0BB8BawGzPa6yNdyf9vGBb");
            artistProfileService.updateProfile(profile);
        }
    }

    private void seedSiteContent() {
        List<SiteContent> defaults = List.of(
            new SiteContent("about_text_1",   "Paragraph 1",     "About",      "Confetti Weather is an AI-crafted musical project steeped in the sights, sounds, and soul of the industrial night. We write songs that smell like smoke and marigolds, that taste like gasoline and regret, that sound like striking a match in the dead of night and watching it burn bright."),
            new SiteContent("about_text_2",   "Paragraph 2",     "About",      "Drawing from the deep wells of indie rock, industrial synth, and atmospheric pop, Confetti Weather creates sonic landscapes where every note carries the weight of a burned bridge and the lightness of a spark catching in the wind."),
            new SiteContent("about_tagline",  "About Tagline",   "About",      "Born from Matches & Gasoline"),
            new SiteContent("about_quote",    "About Quote",     "About",      "\"You were marigold and gasoline, burned so bright, so in between living and letting go.\""),
            new SiteContent("about_quote_cit","About Citation",  "About",      "— Marigold and Gasoline")
        );
        for (SiteContent c : defaults) {
            siteContentService.seedIfEmpty(List.of(c));
        }
    }
}
