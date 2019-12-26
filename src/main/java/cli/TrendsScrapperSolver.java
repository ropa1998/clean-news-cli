package cli;

import factory.trendScrapper.ITrendScrapperFactory;
import implementations.scrappers.trend.ITrendScrapper;

import java.util.Set;

public class TrendsScrapperSolver extends AbstractArgsSolver {

    private final ITrendScrapperFactory trendScrapperFactory;
    private final Set<ITrendScrapper> trendScrappers;

    public TrendsScrapperSolver(Set<ITrendScrapper> trendScrappers, ITrendScrapperFactory trendScrapperFactory, String flag, String keyword) {
        super(flag, keyword);
        this.trendScrappers = trendScrappers;
        this.trendScrapperFactory = trendScrapperFactory;
    }


    protected void solve(String arg) {
        switch (arg) {
            case "argentina":
                trendScrappers.add(trendScrapperFactory.getArgentinaScrapper());
            default:

        }
    }
}
