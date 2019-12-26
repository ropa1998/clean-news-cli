package cli;


import flusher.FileFlusher;
import flusher.IFlusher;
import flusher.SoutFlusher;

import java.util.Set;

public class FlusherSolver extends AbstractArgsSolver {


    private final Set<IFlusher> flushers;

    public FlusherSolver(Set<IFlusher> flusher, String flag, String flusherKeyword) {
        super(flag, flusherKeyword);
        this.flushers = flusher;
    }

    @Override
    protected void solve(String arg) {
        switch (arg) {
            case "console":
                flushers.add(new SoutFlusher());
                break;
            case "file":
                flushers.add(new FileFlusher("clean-news-results.txt"));
                break;
            default:

        }
    }
}
