package cli;


import factory.mediumScrapper.IMediumScrapperFactory;
import implementations.scrappers.medium.IMediumScrapper;
import org.apache.commons.cli.CommandLine;

public class MediumScrapperSolver extends AbstractSolver<IMediumScrapper> {

    private final IMediumScrapperFactory mediumScrapperFactory;

    public MediumScrapperSolver(IMediumScrapperFactory mediumScrapperFactory, CommandLine cmd, String mediumKeyword) {
        super(cmd, mediumKeyword);
        this.mediumScrapperFactory = mediumScrapperFactory;
    }


    protected IMediumScrapper solve(String arg) {
        switch (arg) {
            case "lanacion":
                return mediumScrapperFactory.getLaNacionScrapper();
            case "pagina12":
                return mediumScrapperFactory.getPagina12Scrapper();
            default:
                return null;
        }
    }

}
