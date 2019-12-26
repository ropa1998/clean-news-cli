

import cli.FlusherSolver;
import cli.IArgsSolver;
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

import java.util.HashSet;
import java.util.Set;

public class Main {

    private static final String flag = "-";
    final private static String trendKeyword = "trends";
    final private static String mediumKeyword = "media";
    final private static String flusherKeyword = "flusher";

    public static void main(String[] args) {
        Set<ITrendScrapper> trendScrappers = new HashSet<>();
        Set<IMediumScrapper> mediumScrappers = new HashSet<>();
        Set<IFlusher> flushers = new HashSet<>();

        WebClientFactory webClientFactory = new WebClientFactory();
        WebClient webClient = webClientFactory.getBasicWebClient();

        IMediumScrapperFactory mediumScrapperFactory = new RSSMediumScrapperFactory(webClient);
        ITrendScrapperFactory trendScrapperFactory = new Trends24ScrapperFactory(webClient);

        IArgsSolver trendsScrapperArgsSolver = new TrendsScrapperSolver(trendScrappers, trendScrapperFactory, flag, trendKeyword);
        IArgsSolver mediumScrapperArgsSolver = new MediumScrapperSolver(mediumScrappers, mediumScrapperFactory, flag, mediumKeyword);
        IArgsSolver flusherArgsSolver = new FlusherSolver(flushers, flag, flusherKeyword);

        trendsScrapperArgsSolver.solve(args);
        mediumScrapperArgsSolver.solve(args);
        flusherArgsSolver.solve(args);

        CleanNewsEngine cleanNewsEngine = new CleanNewsEngine(mediumScrappers, trendScrappers);
        cleanNewsEngine.run();

        ICleanNewsResult cleanNewsResult = cleanNewsEngine.getResult();

        for (IFlusher flusher : flushers) {
            flusher.flush(cleanNewsResult);
        }
    }

}
