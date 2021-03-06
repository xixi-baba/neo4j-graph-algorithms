/**
 * Copyright (c) 2017 "Neo4j, Inc." <http://neo4j.com>
 *
 * This file is part of Neo4j Graph Algorithms <http://github.com/neo4j-contrib/neo4j-graph-algorithms>.
 *
 * Neo4j Graph Algorithms is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.graphalgo.impl.pagerank;

import org.neo4j.graphalgo.api.Graph;
import org.neo4j.graphalgo.impl.degree.WeightedDegreeCentrality;
import org.neo4j.graphdb.Direction;

import java.util.concurrent.ExecutorService;

public class WeightedDegreeComputer implements DegreeComputer {

    private Graph graph;
    private boolean cacheWeights;

    public WeightedDegreeComputer(Graph graph, boolean cacheWeights) {
        this.graph = graph;
        this.cacheWeights = cacheWeights;
    }

    @Override
    public DegreeCache degree(ExecutorService executor, int concurrency) {
        WeightedDegreeCentrality degreeCentrality = new WeightedDegreeCentrality(graph, executor, concurrency, Direction.OUTGOING);
        degreeCentrality.compute(cacheWeights);
        return new DegreeCache(degreeCentrality.degrees(), degreeCentrality.weights(), -1D);
    }
}
