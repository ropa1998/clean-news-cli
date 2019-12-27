

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

import java.util.Set;

public class Main {


    final private static String trendKeyword = "trends";
    final private static String mediumKeyword = "media";
    final private static String flusherKeyword = "flusher";

    public static void main(String[] args) throws ParseException {

        WebClientFactory webClientFactory = new WebClientFactory();
        WebClient webClient = webClientFactory.getBasicWebClient();

        IMediumScrapperFactory mediumScrapperFactory = new RSSMediumScrapperFactory(webClient);
        ITrendScrapperFactory trendScrapperFactory = new Trends24ScrapperFactory(webClient);

        Set<ITrendScrapper> trendScrappers = getTrendScrappers(trendScrapperFactory, args);
        Set<IMediumScrapper> mediumScrappers = getMediumScrappers(mediumScrapperFactory, args);
        Set<IFlusher> flushers = getFlushers(args);

        CleanNewsEngine cleanNewsEngine = new CleanNewsEngine(mediumScrappers, trendScrappers);
        cleanNewsEngine.run();

        ICleanNewsResult cleanNewsResult = cleanNewsEngine.getResult();

        for (IFlusher flusher : flushers) {
            flusher.flush(cleanNewsResult);
        }
    }

    private static Set<IFlusher> getFlushers(String[] args) throws ParseException {
        Option flusherOption = new Option(flusherKeyword, "How the user wants to flush the results");
        flusherOption.setArgs(Option.UNLIMITED_VALUES);

        Options options = new Options();
        options.addOption(flusherOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        AbstractSolver<IFlusher> flusherArgsSolver = new FlusherSolver(cmd, flusherKeyword);
        return flusherArgsSolver.getResult();
    }

    private static Set<IMediumScrapper> getMediumScrappers(IMediumScrapperFactory mediumScrapperFactory, String[] args) throws ParseException {
        Option mediaOption = new Option(mediumKeyword, "The media the user wants to scrap");
        mediaOption.setArgs(Option.UNLIMITED_VALUES);

        Options options = new Options();
        options.addOption(mediaOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        AbstractSolver<IMediumScrapper> mediumScrapperArgsSolver = new MediumScrapperSolver(mediumScrapperFactory, cmd, mediumKeyword);
        return mediumScrapperArgsSolver.getResult();
    }

    private static Set<ITrendScrapper> getTrendScrappers(ITrendScrapperFactory trendScrapperFactory, String[] args) throws ParseException {
        Option trendOption = new Option(trendKeyword, "The trend region the user wants to scrap");
        trendOption.setArgs(Option.UNLIMITED_VALUES);

        Options options = new Options();
        options.addOption(trendOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        AbstractSolver<ITrendScrapper> trendsScrapperArgsSolver = new TrendsScrapperSolver(trendScrapperFactory, cmd, trendKeyword);
        return trendsScrapperArgsSolver.getResult();
    }

}
