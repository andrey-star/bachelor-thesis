package org.softwareheritage.graph.tinkerpop;

import it.unimi.dsi.big.webgraph.BidirectionalImmutableGraph;
import it.unimi.dsi.big.webgraph.ImmutableGraph;
import it.unimi.dsi.big.webgraph.labelling.ArcLabelledImmutableGraph;

import static org.apache.tinkerpop.gremlin.process.traversal.P.*;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;
import org.webgraph.tinkerpop.GremlinQueryExecutor;
import org.webgraph.tinkerpop.structure.WebGraphGraph;
import org.webgraph.tinkerpop.structure.property.edge.ArcLabelEdgeProperty;
import org.webgraph.tinkerpop.structure.property.edge.ArcLabelEdgeSubProperty;
import org.webgraph.tinkerpop.structure.property.vertex.VertexProperty;
import org.webgraph.tinkerpop.structure.property.vertex.file.FileVertexProperty;
import org.webgraph.tinkerpop.structure.provider.StandardWebGraphPropertyProvider;
import org.webgraph.tinkerpop.structure.provider.WebGraphPropertyProvider;

import java.io.IOException;
import java.nio.file.Path;

public class MyExample {

    public static WebGraphPropertyProvider getProvider(MyGraph myGraph) throws IOException {
var p = new StandardWebGraphPropertyProvider();
p.setVertexLabeller(myGraph::getNodeType);
p.addVertexProperty(new FileVertexProperty<>("timestamp", Long.class, Path.of("timestamp.bin")));
p.addVertexProperty(new VertexProperty<>("name", myGraph::getName));

var arcLabel = new ArcLabelEdgeProperty<MyArcLabel>(myGraph.getLabelledGraph());
p.addEdgeProperty(arcLabel);
p.addEdgeProperty(new ArcLabelEdgeSubProperty<>("duration", arcLabel, MyArcLabel::getDuration));

try (var gremlinGraph = WebGraphGraph.open(myGraph, p, myGraph.getPath())) {
    GremlinQueryExecutor executor = new GremlinQueryExecutor(gremlinGraph);
    executor.print(g -> g.V().has("timestamp", gt(1000)));
    executor.print("g.V().hasLabel('REV')");
}

        return p;
    }

    static class MyGraph extends BidirectionalImmutableGraph {
        public MyGraph(ImmutableGraph graph, ImmutableGraph transpose) {
            super(graph, transpose);
        }

        public String getNodeType(Long id) {
            return null;
        }

        public String getPath() {
            return null;
        }

        public ArcLabelledImmutableGraph getLabelledGraph() {
            return null;
        }

        public String getName(long l) {
            return null;
        }
    }


    static class MyArcLabel {

        public <T, E> T getFilename(E e) {
            return null;
        }

        public static <T, E> T getDuration(E e) {
            return null;
        }
    }
}
