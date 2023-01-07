package org.webgraph.tinkerpop.structure;

import it.unimi.dsi.fastutil.longs.LongLongImmutablePair;
import it.unimi.dsi.fastutil.longs.LongLongPair;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.util.ElementHelper;
import org.apache.tinkerpop.gremlin.structure.util.StringFactory;
import org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WebGraphEdge extends WebGraphElement implements Edge {

    private final Map<String, Property> properties = new HashMap<>();

    public WebGraphEdge(long fromId, long toId, WebGraphGraph graph) {
        this(new LongLongImmutablePair(fromId, toId), graph);
    }

    public WebGraphEdge(LongLongPair id, WebGraphGraph graph) {
        super(id, graph.getPropertyProvider().edgeLabel(id.firstLong(), id.secondLong()), graph);
    }

    @Override
    public Iterator<Vertex> vertices(Direction direction) {
        LongLongPair edge = (LongLongPair) id();
        switch (direction) {
            case OUT:
                Vertex f = graph.vertexCache.computeIfAbsent(edge.firstLong(),
                        idd -> new WebGraphVertex(idd, graph));
                return IteratorUtils.of(f);
            case IN:
                Vertex s = graph.vertexCache.computeIfAbsent(edge.secondLong(),
                        idd -> new WebGraphVertex(idd, graph));
                return IteratorUtils.of(s);
            default:
                Vertex f2 = graph.vertexCache.computeIfAbsent(edge.firstLong(),
                        idd -> new WebGraphVertex(idd, graph));
                Vertex s2 = graph.vertexCache.computeIfAbsent(edge.secondLong(),
                        idd -> new WebGraphVertex(idd, graph));
                return IteratorUtils.of(f2, s2);
        }
    }


    @Override
    public <V> Iterator<Property<V>> properties(String... propertyKeys) {
        LongLongPair id = (LongLongPair) id();
        String[] keys = propertyKeys.length == 0 ? graph.getPropertyProvider()
                                                        .edgeProperties(id.firstLong(),
                                                                id.secondLong()) : propertyKeys; // if no props are provided, return all props
        return new Iterator<>() {
            int nextIndex = -1;
            Property<V> nextProp = nextProp();

            @Override
            public boolean hasNext() {
                return nextIndex < keys.length;
            }

            @Override
            public Property<V> next() {
                Property<V> res = nextProp;
                nextProp = nextProp();
                return res;
            }

            private Property<V> nextProp() {
                nextIndex++;
                while (nextIndex < keys.length) {
                    String key = keys[nextIndex];
                    Property<V> p = properties.computeIfAbsent(key, k -> {
                        Object val = graph.getPropertyProvider().edgeProperty(key, id.firstLong(), id.secondLong());
                        if (val == null) {
                            return Property.empty();
                        }
                        return new WebGraphProperty<>(WebGraphEdge.this, key, (V) val);
                    });
                    if (p.isPresent()) {
                        return p;
                    }
                    nextIndex++;
                }
                return null;
            }
        };
    }

    @Override
    public <V> Property<V> property(String key, V value) {
        return null;
    }

    @Override
    public void remove() {
        throw Edge.Exceptions.edgeRemovalNotSupported();
    }

    @Override
    public String toString() {
        return StringFactory.edgeString(this);
    }

    @Override
    public int hashCode() {
        return ElementHelper.hashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return ElementHelper.areEqual(this, obj);
    }
}
