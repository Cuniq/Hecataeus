package edu.ntua.dblab.hecataeus.graph.visual;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import clusters.HAggloEngine;
import clusters.EngineConstructs.Cluster;
import clusters.EngineConstructs.ClusterSet;
import edu.ntua.dblab.hecataeus.HecataeusViewer;
import edu.ntua.dblab.hecataeus.graph.evolution.NodeType;

public class VisualClustersOnACircleLayout extends VisualCircleLayout {
	
	protected double endC;
	protected VisualGraph graph;
	private List<VisualNode> queries;
	private List<VisualNode> relations;
	private List<VisualNode> views;
	private ClusterSet cs;
	
	
	protected List<String> files;
	private List<VisualNode> RQV;
	
	protected VisualCircleLayout vcl;
	
	public VisualClustersOnACircleLayout(VisualGraph g, double endC) {
		super(g);
		this.graph = g;
		this.endC = endC;
		vcl = new VisualCircleLayout(this.graph);
		
		queries = new ArrayList<VisualNode>(vcl.getQueries());
		relations = new ArrayList<VisualNode>(vcl.getRelations());
		views = new ArrayList<VisualNode>(vcl.getViews());
		RQV = new ArrayList<VisualNode>(vcl.RQV);
		files = new ArrayList<String>(vcl.files);
	}
	

	private void circles(List<VisualNode> nodes, double cx, double cy){
		int b = 0;
		for(VisualNode v : nodes){
			if(v.getType() == NodeType.NODE_TYPE_RELATION){
				
				double smallRad = 1.3*getSmallRad(relationsInCluster(nodes));
				Point2D coord = transform(v);
				double angleA = (2 * Math.PI ) / relationsInCluster(nodes).size();
				coord.setLocation(Math.cos(angleA*b)*smallRad+(cx),Math.sin(angleA*b)*smallRad+(cy));
				v.setLocation(coord);
				HecataeusViewer.getActiveViewer().getRenderContext().setVertexFillPaintTransformer(new VisualClusteredNodeColor(v, HecataeusViewer.getActiveViewer().getPickedVertexState()));
				HecataeusViewer.getActiveViewer().repaint();
			}else{
				double smallRad = getSmallRad(nodes);
				Point2D coord = transform(v);
				double angleA = 0.0;
				if(relationsInCluster(nodes).size() > 1){
					angleA = (2 * Math.PI ) / (nodes.size()-relationsInCluster(nodes).size());
				}else{
					angleA = (2 * Math.PI ) / nodes.size();
				}
				coord.setLocation(Math.cos(angleA*b)*smallRad+(cx),Math.sin(angleA*b)*smallRad+(cy));
				v.setLocation(coord);
				HecataeusViewer.getActiveViewer().getRenderContext().setVertexFillPaintTransformer(new VisualClusteredNodeColor(v, HecataeusViewer.getActiveViewer().getPickedVertexState()));
				HecataeusViewer.getActiveViewer().repaint();
			}
			b++;
		}
		
	}
	
	
	
	private void clustersOnaCircle(double endC){
		
		ArrayList<Cluster> Clusters;
		double [][] distances = cs.getClusterDistances();
		
//		SortedArrayList<Cluster> Clusters = new SortedArrayList<Cluster>();
//		SortedArrayList sl = new SortedArrayList();
//		ArrayList<Cluster> Clusters = new ArrayList<Cluster>(sl.insertSorted(cs.getClusters(), distances));
		
		//for(int i = 0; i < cs.getClusters().size(); i++){
		//	Clusters.insertSorted(cs.getClusters(), distances);
		//}
//		for(int i = 0; i < cs.getClusters().size(); i++){
//			System.out.println("CLUSTERS " + Clusters.get(i).getName(Clusters.get(i)));
//		}

		ArrayList<ArrayList<VisualNode>> xa = new ArrayList<ArrayList<VisualNode>>();
		
		
		if(endC != 1){
			SortedArrayList sl = new SortedArrayList();
			Clusters = new ArrayList<Cluster>(sl.insertSorted(cs.getClusters(), distances));
//			VisualGraph clusterGraph = new VisualGraph();
//			
//			for(Cluster cl : Clusters){
//				for(VisualNode node : cl.getNode()){
//					clusterGraph.addVertex(node);
//					for(VisualEdge edge : node.getInEdges()){
//						clusterGraph.addEdge(edge);
//					}
//					for(VisualEdge edge : node.getOutEdges()){
//						clusterGraph.addEdge(edge);
//					}
//				}
//				
//			}
//			VisualBicomponentClusterer bClusterer = new VisualBicomponentClusterer();
//			Set<Set<VisualNode>> clusterSet = bClusterer.transform(this.graph);
			
//			System.out.println("eva");
			
			
//			System.out.println(clusterSet.toString());
		//	Clusters.clear();
			
//			for (Iterator<Set<VisualNode>> cIt = clusterSet.iterator(); cIt.hasNext();) {
//			
//				Set<VisualNode> sv = cIt.next();
//				ArrayList<VisualNode> temp = new ArrayList<VisualNode>();
//				
//				for (Iterator<VisualNode> vIt = sv.iterator(); vIt.hasNext();) {
//					VisualNode eva = vIt.next();
//					
//					temp.add(eva);
//				}
//				
//				xa.add(temp);
//				
//			}
			
//			System.out.println(xa.toString());
			
//			for (Iterator<Set<VisualNode>> cIt = clusterSet.iterator(); cIt.hasNext();) {
//				
//				Set<VisualNode> vertices = cIt.next();
//				for(VisualNode vIt : vertices){
//					
//					findBiconnectedComponents((UndirectedGraph<VisualNode, VisualEdge>) clusterGraph ,vIt ,clusterSet);
//				}
//					
//				
//				//findBiconnectedComponents(UndirectedGraph<V,E> g, V v, Set<Set<V>> bicomponents)
//			}
			
		}
		else{
			Clusters = new ArrayList<Cluster>(cs.getClusters());
		}
		
		ArrayList<ArrayList<VisualNode>> vertices = new ArrayList<ArrayList<VisualNode>>();       //lista me ta clusters 
		ArrayList<ArrayList<VisualNode>> V = new ArrayList<ArrayList<VisualNode>>();   // tin xrisimopoio gia na anakatevw tin vertices gia na min einai olla ta megala cluster mazi
		for(Cluster cl : Clusters){
			vertices.add(cl.getNode());
			Collections.shuffle(vertices);
		}
		V.addAll(vertices);
		double myRad = 0.0;
		double RAD = 0;
		//taksinomei tin lista --> prwta ta relations meta ta upoloipa k briskei aktina
		for(ArrayList<VisualNode> lista : vertices){
			List<VisualNode> nodes = new ArrayList<VisualNode>();
	//		System.out.println(lista);
			Collections.sort(lista, new CustomComparator());
			nodes.addAll(lista);
			RAD += getSmallRad(nodes);
		}
		myRad = RAD/Math.PI;
		double diametros = 0;
		int a = 0;double angle = 0.0, sum = 0.0;
			
		// only if clustering algo won't produce more than one clusters
//		if(Clusters.size() < 2){
//			Dimension d = getSize();
//			double height = d.getHeight();
//			double width = d.getWidth();
//			
//			//for(ArrayList<VisualNode> lista : V){
//				int k = 0;
//				for(VisualNode v : V.get(0)){
//					Point2D coord = transform(v);				
//					double angle1 = (2 * Math.PI) / V.get(0).size();
//					coord.setLocation(Math.cos(angle1*k) * myRad + width / 2, Math.sin(angle1*k) * myRad + height / 2);k++;
//					v.setLocation(coord);
//				}
//				
//			//}
//		}
//		else{
			for(ArrayList<VisualNode> lista : V){
				List<VisualNode> nodes = new ArrayList<VisualNode>();
				Collections.sort(lista, new CustomComparator());
				nodes.addAll(lista);
				diametros = 2*getSmallRad(nodes);
				double temp =   (2*myRad*myRad - getSmallRad(nodes)*getSmallRad(nodes)*0.94)/(2*myRad*myRad );
				if(Math.abs(temp)>1){
					temp = 0.9;
				}
				angle = (Math.acos( temp))*2;   // 0.94 is used simulate strait lines to curves
				double cx = Math.cos(sum+angle/2) * myRad*1.8;// 1.8 is used for white space borders
				double cy =	Math.sin(sum+angle/2) * myRad*1.8;
				Point2D coord1 = transform(nodes.get(0));
				coord1.setLocation(cx, cy);
				System.out.println("Node name    " + lista.get(0).getName()  + "   cx:    " +cx + " cy: " +cy+ " my angle: " +angle );
				sum+=angle;
				circles(nodes, cx, cy);
				lista.get(0).setLocation(coord1);
				a++;
			}
		//}
		System.out.println("LOGIKA 2*Math.Pi: "+Math.PI*2+" and it is: " + sum);
	}


	@Override
	public void initialize() {
		HAggloEngine engine = new HAggloEngine(this.graph);
		VisualCreateAdjMatrix cAdjM = new VisualCreateAdjMatrix(RQV);		
		engine.executeParser(relations, queries, views, cAdjM.createAdjMatrix());
		engine.buildFirstSolution();
		cs = engine.execute(endC);
		clustersOnaCircle(endC);
	}

	@Override
	public void reset() {
		initialize();
		
	}
	
}
