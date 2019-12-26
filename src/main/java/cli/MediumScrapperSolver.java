package cli;


import factory.mediumScrapper.IMediumScrapperFactory;
import implementations.scrappers.medium.IMediumScrapper;

import java.util.Set;

public class MediumScrapperSolver extends AbstractArgsSolver {
    private final IMediumScrapperFactory mediumScrapperFactory;
    private final Set<IMediumScrapper> mediumScrappers;

    public MediumScrapperSolver(Set<IMediumScrapper> mediumScrappers, IMediumScrapperFactory mediumScrapperFactory, String flag, String keyword) {
        super(flag, keyword);
        this.mediumScrappers = mediumScrappers;
        this.mediumScrapperFactory = mediumScrapperFactory;
    }


    protected void solve(String arg) {
        switch (arg) {
            case "lanacion":
                mediumScrappers.add(mediumScrapperFactory.getLaNacionScrapper());
                break;
            case "pagina12":
                mediumScrappers.add(mediumScrapperFactory.getPagina12Scrapper());
                break;
            default:

        }
    }
}
