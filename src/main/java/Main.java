

import cli.AbstractSolver;
import cli.FlusherSolver;
import cli.MediumScrapperSolver;
import cli.TrendsScrapperSolver;
import com.gargoylesoftware.htmlunit.WebClient;
import factory.mediumScrapper.IMediumScrapperFactory;
import factory.mediumScrapper.RSSMediumScrapperFactory;
import factory.trendScrapper.ITrendScrapperFactory;
import factory.trendScrapper.Trends24ScrapperFactory;
import flusher.IFlusher;
import implementations.CleanNewsEngine;
import implementations.core.cleanNewsResult.ICleanNewsResult;
import implementations.factory.webclient.WebClientFactory;
import implementations.scrappers.medium.IMediumScrapper;
import implementations.scrappers.trend.ITrendScrapper;
import org.apache.commons.cli.*;

import java.util.HashSet;
import java.util.Set;

public class Main {

    final private static String trendKeyword = "trends";
    final private static String mediumKeyword = "media";
    final private static String flusherKeyword = "flushers";

    public static void main(String[] args) {
        Options options = generateProgramOptions();
        generateHelp(options);

        WebClient webClient = getWebClient();

        IMediumScrapperFactory mediumScrapperFactory = new RSSMediumScrapperFactory(webClient);
        ITrendScrapperFactory trendScrapperFactory = new Trends24ScrapperFactory(webClient);

        Set<ITrendScrapper> trendScrappers = new HashSet<>();
        Set<IMediumScrapper> mediumScrappers = new HashSet<>();
        Set<IFlusher> flushers = new HashSet<>();

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine line = parser.parse(options, args);

            trendScrappers = getTrendScrappers(trendScrapperFactory, line);
            mediumScrappers = getMediumScrappers(mediumScrapperFactory, line);
            flushers = getFlushers(line);
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }


        CleanNewsEngine cleanNewsEngine = new CleanNewsEngine(mediumScrappers, trendScrappers);
        cleanNewsEngine.run();

        ICleanNewsResult cleanNewsResult = cleanNewsEngine.getResult();

        for (IFlusher flusher : flushers) {
            flusher.flush(cleanNewsResult);
        }
    }

    private static WebClient getWebClient() {
        WebClientFactory webClientFactory = new WebClientFactory();
        return webClientFactory.getBasicWebClient();
    }


    private static Options generateProgramOptions() {
        Option trendsOption = Option.builder(trendKeyword).hasArgs().argName("trend titles").desc("Specify the trend region you want to scrap").build();
        Option flusherOption = Option.builder(flusherKeyword).hasArgs().argName("trend titles").desc("Specify in which ways you want your information to be flushed.").build();
        Option mediaOption = Option.builder(mediumKeyword).hasArgs().argName("media titles").desc("Specify the media you want scrapped. The supported media are: ").build();

        Options options = new Options();

        options.addOption(trendsOption);
        options.addOption(mediaOption);
        options.addOption(flusherOption);

        return options;
    }

    private static void generateHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("clean-news-cli", options);
    }

    private static Set<IFlusher> getFlushers(CommandLine cmd) {
        AbstractSolver<IFlusher> flusherArgsSolver = new FlusherSolver(cmd, flusherKeyword);
        return flusherArgsSolver.getResult();
    }

    private static Set<IMediumScrapper> getMediumScrappers(IMediumScrapperFactory mediumScrapperFactory, CommandLine cmd) {
        AbstractSolver<IMediumScrapper> mediumScrapperArgsSolver = new MediumScrapperSolver(mediumScrapperFactory, cmd, mediumKeyword);
        return mediumScrapperArgsSolver.getResult();
    }

    private static Set<ITrendScrapper> getTrendScrappers(ITrendScrapperFactory trendScrapperFactory, CommandLine cmd) {
        AbstractSolver<ITrendScrapper> trendsScrapperArgsSolver = new TrendsScrapperSolver(trendScrapperFactory, cmd, trendKeyword);
        return trendsScrapperArgsSolver.getResult();
    }

}
