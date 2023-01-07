package org.softwareheritage.graph.tinkerpop;

import it.unimi.dsi.big.webgraph.BidirectionalImmutableGraph;
import it.unimi.dsi.big.webgraph.LazyLongIterator;
import it.unimi.dsi.big.webgraph.NodeIterator;
import org.softwareheritage.graph.Node;
import org.softwareheritage.graph.SwhBidirectionalGraph;
import org.webgraph.tinkerpop.structure.WebGraphVertex;

import java.util.HashSet;
import java.util.Set;

public class DFS {

    /**
     * Performs a DFS on the graph.
     */
    public static void dfs(BidirectionalImmutableGraph g) {
        NodeIterator nodes = g.nodeIterator();
        Set<Long> roots = new HashSet<>();
        while (nodes.hasNext()) {
            long cur = nodes.nextLong();
            if (g.predecessors(cur).nextLong() == -1) {
                roots.add(cur);
            }
        }
        boolean[] used = new boolean[50_000_000];
        for (Long root : roots) {
            dfs(root, used, g);
        }
    }

    public static long dfsEdges(long v, SwhBidirectionalGraph g) {
        boolean[] used = new boolean[50_000_000];
        return dfsEdges(v, used, g);
    }

    public static long dfsEdgesUp(Long[] v, SwhBidirectionalGraph g) {
        boolean[] used = new boolean[50_000_000];
        long res = 0;
        for (long l : v) {
            res += dfsEdgesUp(l, used, g);
        }
        return res;
    }

    public static long dfsUp(Long[] v, SwhBidirectionalGraph g) {
        boolean[] used = new boolean[50_000_000];
        long res = 0;
        for (long l : v) {
            res += dfsUp(l, used, g);
        }
        return res;
    }

    /**
     * Performs a DFS on the graph, keeping visited vertices in a {@code Set},
     * instead of a boolean array.
     *
     * @see #dfs(BidirectionalImmutableGraph)
     */
    public static void dfsSet(BidirectionalImmutableGraph g) {
        NodeIterator nodes = g.nodeIterator();
        Set<WebGraphVertex> roots = new HashSet<>();
        while (nodes.hasNext()) {
            long cur = nodes.nextLong();
            if (g.predecessors(cur).nextLong() == -1) {
                roots.add(new WebGraphVertex(cur, null));
            }
        }
        Set<WebGraphVertex> used = new HashSet<>();
        for (WebGraphVertex root : roots) {
            dfsSet(root, used, g);
        }
    }

    private static void dfs(long root, boolean[] used, BidirectionalImmutableGraph g) {
        used[(int) root] = true;
        LazyLongIterator successors = g.successors(root);
        long child;
        while ((child = successors.nextLong()) != -1) {
            if (!used[(int) child]) {
                dfs(child, used, g);
            }
        }
    }

    private static void dfsSet(WebGraphVertex root, Set<WebGraphVertex> used, BidirectionalImmutableGraph g) {
        used.add(root);
        LazyLongIterator successors = g.successors((long) root.id());
        long child;
        while ((child = successors.nextLong()) != -1) {
            WebGraphVertex childVertex = new WebGraphVertex(child, null);
            if (!used.contains(childVertex)) {
                dfsSet(childVertex, used, g);
            }
        }
    }

    private static long dfsEdges(long root, boolean[] used, SwhBidirectionalGraph g) {
        used[(int) root] = true;
        LazyLongIterator successors = g.successors(root);
        long child;
        long res = 0;
        while ((child = successors.nextLong()) != -1) {
            res += 1;
            if (!used[(int) child]) {
                res += dfsEdges(child, used, g);
            }
        }
        return res;
    }

    private static long dfsEdgesUp(long root, boolean[] used, SwhBidirectionalGraph g) {
        used[(int) root] = true;
        LazyLongIterator successors = g.predecessors(root);
        long parent;
        long res = 0;
        while ((parent = successors.nextLong()) != -1) {
            res += 1;
            if (!used[(int) parent]) {
                res += dfsEdgesUp(parent, used, g);
            }
        }
        return res;
    }

    private static long dfsUp(long root, boolean[] used, SwhBidirectionalGraph g) {
        used[(int) root] = true;
        LazyLongIterator successors = g.predecessors(root);
        long parent;
        long res = 0;
        while ((parent = successors.nextLong()) != -1) {
            if (!used[(int) parent]) {
                res += dfsUp(parent, used, g);
            }
        }
        return res + 1;
    }

    public static long dfsVertices(long[] a, SwhBidirectionalGraph g) {
        boolean[] used = new boolean[50_000_000];
        long res = 0;
        for (long l : a) {
            res += dfsVertices(l, used, g);
        }
        return res;
    }

    private static long dfsVertices(long root, boolean[] used, SwhBidirectionalGraph g) {
        used[(int) root] = true;
        LazyLongIterator successors = g.successors(root);
        long child;
        long res = 0;
        while ((child = successors.nextLong()) != -1) {
            if (!used[(int) child]) {
                res += dfsVertices(child, used, g);
            }
        }
        return res + 1;
    }
}
