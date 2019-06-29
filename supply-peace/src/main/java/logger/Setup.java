package logger;

import javax.annotation.PostConstruct;

/**
 * Created by prayagupd
 * on 11/24/16.
 */

public class Setup {

    static {
        initIt("from static");
    }

    private static void initIt(String from) {
        System.out.println("setting up " + from);
        System.setProperty("something", "something");
    }

    @PostConstruct
    public void init() {
        initIt("Post Construct");
    }
}
