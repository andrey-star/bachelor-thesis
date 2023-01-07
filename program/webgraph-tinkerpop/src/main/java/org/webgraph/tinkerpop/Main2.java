package org.webgraph.tinkerpop;

import it.unimi.dsi.big.webgraph.BVGraph;
import it.unimi.dsi.big.webgraph.BidirectionalImmutableGraph;
import it.unimi.dsi.big.webgraph.ImmutableGraph;

import java.io.IOException;

public class Main2 {

    public static void main(String[] args) throws IOException {
        ImmutableGraph immutableGraph = ImmutableGraph.loadOffline("src/main/resources/imdb/imdb-2021");
        BVGraph.store(immutableGraph, "src/main/resources/imdb-2021");
        ImmutableGraph f = ImmutableGraph.loadOffline("src/main/resources/imdb/imdb-2021-transposed");
        BVGraph.store(f, "src/main/resources/imdb-2021-transposed");
    }
}
