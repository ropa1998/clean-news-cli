package cli;

import factory.trendScrapper.ITrendScrapperFactory;
import implementations.scrappers.trend.ITrendScrapper;
import org.apache.commons.cli.CommandLine;

public class TrendsScrapperSolver extends AbstractSolver<ITrendScrapper> {

    private final ITrendScrapperFactory trendScrapperFactory;


    public TrendsScrapperSolver(ITrendScrapperFactory trendScrapperFactory, CommandLine cmd, String trendKeyword) {
        super(cmd, trendKeyword);
        this.trendScrapperFactory = trendScrapperFactory;
    }


    protected ITrendScrapper solve(String arg) {
        switch (arg) {
            case "argentina":
                return trendScrapperFactory.getArgentinaScrapper();
            default:
                return null;
        }
    }


}
