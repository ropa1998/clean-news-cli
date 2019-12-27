package cli;


import flusher.FileFlusher;
import flusher.IFlusher;
import flusher.SoutFlusher;
import org.apache.commons.cli.CommandLine;

public class FlusherSolver extends AbstractSolver<IFlusher> {


    public FlusherSolver(CommandLine cmd, String flusherKeyword) {
        super(cmd, flusherKeyword);
    }


    protected IFlusher solve(String arg) {
        switch (arg) {
            case "console":
                return new SoutFlusher();
            case "file":
                return new FileFlusher("clean-news-results.txt");
            default:
                return null;
        }
    }


}
