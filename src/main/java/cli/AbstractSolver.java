package cli;

import org.apache.commons.cli.CommandLine;

import java.util.*;

public abstract class AbstractSolver<T> {

    protected final Set<T> elements;
    protected CommandLine cmd;
    protected String keyword;

    public AbstractSolver(CommandLine cmd, String keyword) {
        this.elements = new HashSet<>();
        this.keyword = keyword;
        this.cmd = cmd;
    }


    public Set<T> getResult() {

        try {
            String args[] = cmd.getOptionValues(keyword);
            List<String> argsList = new ArrayList<>(Arrays.asList(args));
            for (String arg : argsList) {
                elements.add(solve(arg));
            }
        } catch (Exception e) {

        }

        return elements;
    }

    protected abstract T solve(String arg);
}
