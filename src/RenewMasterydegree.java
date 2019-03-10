import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class RenewMasterydegree {
	private static int INF = Integer.MAX_VALUE;
    //dist[i][j]=INF<==>i �� j֮��û�б�
    private int[][] dist;
    //����i �� j�����·�����ȣ���ֵ��i��j�ıߵ�Ȩ��
    private int[][] path;
    private List<Integer> result = new ArrayList<Integer>();
    public RenewMasterydegree(int size) {
        this.path = new int[size][size];
        this.dist = new int[size][size];
    }
    public RenewMasterydegree() {
    }
    public HashMap<String,Double> renew(HashMap<Integer,Double>spgrate) {
    	RenewMasterydegree  graph = new RenewMasterydegree(11);
    	//double average=graph.getAverage(kgrate); //ԭ����ƽ��ֵ
    //	double standarderror=graph.getStandardDevition(kgrate, average);
    	int dist=0;
    	double sigma=1.5;
    	int[][] matrix =
            {  {1,1,1,INF,INF,INF,INF,INF,INF,INF,INF},
               {INF,1,INF,INF,1,INF,INF,INF,INF,INF,INF},
               {INF,INF,1,INF,INF,1,INF,INF,INF,INF,INF},
               {INF,INF,INF,1,1,INF,INF,INF,INF,INF,INF},
               {INF,INF,INF,INF,1,1,INF,INF,INF,INF,INF},
               {INF,INF,INF,INF,INF,1,1,INF,INF,INF,INF},
               {INF,INF,INF,INF,INF,INF,1,1,INF,INF,INF},
               {INF,INF,INF,INF,INF,INF,INF,1,1,INF,INF},
               {INF,INF,INF,INF,INF,INF,INF,INF,1,1,1},
               {INF,INF,INF,INF,INF,INF,INF,INF,INF,1,INF},   //֪ʶͼ��
               {INF,INF,INF,INF,INF,INF,INF,INF,INF,INF,1}};   
    	HashMap<String,Integer> knowledgepoint=new HashMap<String,Integer>();
    	HashMap<Integer,String> knowledgepointtravse=new HashMap<Integer,String>();
    	HashMap<String,Double>kgrate=new HashMap<String,Double>();
    	ArrayList<String> recommendlist_sort=new ArrayList<String>();
    	HashMap<String,Double>recommendlist=new HashMap<String,Double>();
    	knowledgepoint.put("��������", 1);  knowledgepointtravse.put(1,"��������");
    	knowledgepoint.put("���ֺ�������",2); knowledgepointtravse.put(2,"���ֺ�������");
    	knowledgepoint.put("���ݺ�������",3); knowledgepointtravse.put(3,"���ݺ�������");
    	knowledgepoint.put("1NF",4); knowledgepointtravse.put(4,"1NF");
    	knowledgepoint.put("2NF",5); knowledgepointtravse.put(5,"2NF");
    	knowledgepoint.put("3NF",6);knowledgepointtravse.put(6,"3NF");
    	knowledgepoint.put("BCNF",7); knowledgepointtravse.put(7,"BCNF");
    	knowledgepoint.put("��������",8);knowledgepointtravse.put(8,"��������");
    	knowledgepoint.put("��",9); knowledgepointtravse.put(9,"��");
    	knowledgepoint.put("������",10); knowledgepointtravse.put(10,"������");
    	knowledgepoint.put("��������",11);knowledgepointtravse.put(11,"��������");
    	double sum=0;
        for(int i=1;i<=11;i++)
        {
        	kgrate.put(knowledgepointtravse.get(i), spgrate.get(i));
        	sum=sum+spgrate.get(i);
        }
        double average=sum/11;
        for(int i=1;i<=11;i++)
        {
        	if(spgrate.get(i)<average)
        	{
        		recommendlist.put(knowledgepointtravse.get(i),kgrate.get(knowledgepointtravse.get(i)));
        		//recommendlist.add(knowledgepointtravse.get(i));
        	}
        }
        List<Entry<String, Double>> list2 = new ArrayList<Entry<String,Double>>(recommendlist.entrySet());
	    
	     Collections.sort(list2,new Comparator<Map.Entry<String, Double>>() {  
	              //��������  
	     public int compare(Entry<String, Double> o1, Entry<String,  Double> o2) {  
	               return o1.getValue().compareTo(o2.getValue());  
	        }  
	     }); 
        System.out.println("recommend "+recommendlist);
        Iterator iter = recommendlist.entrySet().iterator();
        while (iter.hasNext()) {
        	Map.Entry entry = (Map.Entry) iter.next();
        	recommendlist_sort.add((String) entry.getKey());
        }
        System.out.println("recommend "+recommendlist_sort);
        for(int i=0;i<recommendlist_sort.size();i++)
        {
        	int begin=knowledgepoint.get(recommendlist_sort.get(i))-1;
        	double KGrate=kgrate.get(recommendlist_sort.get(i));
        	for(int j=1;j<=11;j++)
        	{
        		 graph.findCheapestPath(begin, j-1, matrix);
        		 String end=knowledgepointtravse.get(j);
        		 List<Integer> list = graph.result;
        		 dist=graph.dist[begin][j-1];
        		 //System.out.println("KGrate="+KGrate+" "+kgrate.toString()+" kgrate(j)="+kgrate.get(end)+" end="+end);
        		 
        		 KGrate=KGrate+ kgrate.get(end)*( Math.exp( -(dist*dist)/ (2 * sigma * sigma) ) );       //��������/ Math.sqrt(2 * Math.PI * sigma * sigma)
        		 kgrate.put(recommendlist_sort.get(i), KGrate);
        		 //System.out.println("KGrate="+KGrate);
        		 double max=getMax(kgrate);
        	     double min=getMin(kgrate);
        		 KGrate=((double)(KGrate-min))/(float)(max-min);
        		// System.out.println("max="+max+" min="+min+" KGrate2="+KGrate);
        		 //KGrate=KGrate+KGtrate*(1/())
        	}
        	//KGrate=(KGrate-average)/standarderror;
        	
            kgrate.put(recommendlist_sort.get(i), KGrate);
            //System.out.println(kgrate.toString());
        }
        System.out.println("��һ��֮ǰ "+kgrate.toString());
//        double max=getMax(kgrate);
//        double min=getMin(kgrate);
//        System.out.println("min="+min+" max="+max);
//        for(int i=1;i<=11;i++)
//        {
//        	double KGrate=kgrate.get(knowledgepointtravse.get(i));
//        	KGrate=(KGrate-min)/(max-min);
//        	kgrate.put(knowledgepointtravse.get(i), KGrate);
//        }
//        System.out.println("��һ��֮�� "+kgrate.toString());
        return kgrate;
//       
//        System.out.println(begin + " to " + end + ",the cheapest path is:");
//        System.out.println(list.toString());
//        System.out.println(graph.dist[begin][end]);
    }
    public double getMax(Map<String,Double>kgrate){
    	double max= 0;
        Iterator iter = kgrate.entrySet().iterator();
        while (iter.hasNext()) {
        	Map.Entry entry = (Map.Entry) iter.next();
        	Object key = entry.getKey();
        	Object val = entry.getValue();
        	if(Double.parseDouble(val.toString())>max)
        	{
        		max=Double.parseDouble(val.toString());
        		 //System.out.println(" max="+max);
        	}
        }
        return max;
    }
    public double getMin(Map<String,Double>kgrate){
        	double min= INF;
            Iterator iter = kgrate.entrySet().iterator();
            while (iter.hasNext()) {
            	Map.Entry entry = (Map.Entry) iter.next();
            	Object key = entry.getKey();
            	Object val = entry.getValue();
            	if(Double.parseDouble(val.toString())<min)
            	{
            		min=Double.parseDouble(val.toString());
            		 //System.out.println("min="+min);
            	}
            }
            return min;
        }
    public double getStandardDevition(Map<String,Double>kgrate,double average){
    	double sum = 0;
        Iterator iter = kgrate.entrySet().iterator();
        while (iter.hasNext()) {
        	Map.Entry entry = (Map.Entry) iter.next();
        	Object key = entry.getKey();
        	Object val = entry.getValue();
        	sum +=Math.sqrt((Double.parseDouble(val.toString()) -average) *(Double.parseDouble(val.toString())-average));
      }
        return (sum / 10);
    }
    public void findCheapestPath(int begin, int end, int[][] matrix) {
        floyd(matrix);
        result.add(begin);
       // System.out.println("j="+end);
        findPath(begin, end);
        result.add(end);
    }
 
    public void findPath(int i, int j) {
    	//System.out.println("j="+j+" i="+i);
        int k = path[i][j];
        if (k == -1) {
            return;
        }
        findPath(i, k);   //�ݹ�
        result.add(k);
        findPath(k, j);
    }
 
    public void floyd(int[][] matrix) {
        int size = matrix.length;
        //initialize dist and path   
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                path[i][j] = -1;
                dist[i][j] = matrix[i][j];
            }
        }
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (dist[i][k] != INF &&
                            dist[k][j] != INF &&
                            dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        path[i][j] = k;
                    }
                }
            }
        }
 
    }
 
   
}
