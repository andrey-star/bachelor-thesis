package org.softwareheritage.graph.tinkerpop;

import org.softwareheritage.graph.SwhBidirectionalGraph;
import org.webgraph.tinkerpop.GremlinQueryExecutor;
import org.webgraph.tinkerpop.structure.WebGraphGraph;
import org.webgraph.tinkerpop.structure.provider.WebGraphPropertyProvider;

import java.io.IOException;

public class ExampleServer {

    private static final String PATH = "src/main/resources/example/example";

    public static void main(String[] args) throws IOException {
        SwhBidirectionalGraph graph = SwhBidirectionalGraph.loadLabelled(PATH);
        WebGraphPropertyProvider swh = SwhProperties.withEdgeLabels(graph);

        Utils.time(() -> {
            try (var gg = WebGraphGraph.open(graph, swh, PATH)) {
                GremlinQueryExecutor e = new GremlinQueryExecutor(gg);
//            e.print(g -> g.V(18).outE().inV().hasLabel("REV", "REL"));
//            e.print(SWH.aa(19));
//            e.print(g -> g.E().values("filenames"));
//                e.print(g -> g.E().values("dir_length"));
//                e.print(Query.recursiveContentPathsWithPermissions(6));
//                e.print(g -> g.V().elementMap());
                e.print(g -> g.V(7).outE().elementMap());
                e.print(Query.recursiveContentPathsWithPermissions(6));
//                System.out.println(DFS.dfsUp(new Long[]{1L, 3L}, graph));
            }
        });
    }

}
