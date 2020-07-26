package com.mitchmele.tradesnacks.helpers;

import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class GradleBuildLauncher {

    public GradleBuildLauncher() throws URISyntaxException { }

    private final BuildLauncher buildLauncher = create();

    private BuildLauncher create() throws URISyntaxException {
        return GradleConnector
                .newConnector()
                .useDistribution(new URI("https://services.gradle.org/distributions/gradle-6.3-bin.zip"))
                .forProjectDirectory(new File("/Users/mitchmele/workspace/personal/tradesnacks"))
                .connect()
                .newBuild()
                .setStandardOutput(System.out);
    }

    public void standUpMongo() {
        buildLauncher.forTasks("standUpMongo").run();
    }

    public void standDownMongo() {
        buildLauncher.forTasks("standDownMongo").run();
    }
}
