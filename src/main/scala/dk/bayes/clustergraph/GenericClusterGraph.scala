package dk.bayes.clustergraph

import dk.bayes.factor.Factor
import ClusterGraph._
import scala.collection._
import scala.annotation.tailrec
import scala.util.Random
import dk.bayes.factor.Factor._
import dk.bayes.factor.SingleFactor

/**
 * Default implementation of a ClusterGraph.
 *
 * @author Daniel Korzekwa
 */
case class GenericClusterGraph extends ClusterGraph {

  private var clusters: List[Cluster] = List()

  def getClusters(): Seq[Cluster] = clusters

  def addCluster(clusterId: Int, factor: Factor) = {
    val cluster = Cluster(clusterId, factor)
    clusters = cluster :: clusters
  }

  def addEdge(clusterId1: Int, clusterId2: Int) {
    val cluster1 = clusters.find(c => c.id == clusterId1).get
    val cluster2 = clusters.find(c => c.id == clusterId2).get
    val sepset = calcSepset(cluster1, cluster2)

    val edge12 = Edge(clusterId2, sepset)
    val edge21 = Edge(clusterId1, sepset)

    edge12.setIncomingEdge(edge21)
    edge21.setIncomingEdge(edge12)

    cluster1.addEdge(edge12)
    cluster2.addEdge(edge21)
  }

  def addEdges(firstEdge: Tuple2[Int, Int], nextEdges: Tuple2[Int, Int]*) {
    addEdge(firstEdge._1, firstEdge._2)
    nextEdges.foreach(e => addEdge(e._1, e._2))
  }

  private def calcSepset(cluster1: Cluster, cluster2: Cluster): SingleFactor = {
    val intersectVariables = cluster1.getFactor().getVariables().intersect(cluster2.getFactor().getVariables())
    require(intersectVariables.size == 1, "Sepset must contain single variable only")
    val intersectVariable = intersectVariables.head

    val uniformValues = Array.fill(intersectVariable.dim)(1d)
    SingleFactor(intersectVariable, uniformValues)
  }

}