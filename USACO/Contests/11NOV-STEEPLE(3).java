import java.io.*;
import java.util.*;

public class Main {
	static Reader in;
	static PrintWriter out;
	
	static class Solver {
		List<Integer>[] intersect;
		int[] matching, dist;
		boolean[] vis1, vis2;
		
		Solver() {
			int n=in.nextInt();
			List<Seg> v = new ArrayList<Seg>(), h = new ArrayList<Seg>();
			for(int i=0; i<n; ++i) {
				int x1=in.nextInt(), y1=in.nextInt(), x2=in.nextInt(), y2=in.nextInt();
				if(x1==x2)
					v.add(new Seg(Math.min(y1, y2), Math.max(y1, y2), x1));
				if(y1==y2)
					h.add(new Seg(Math.min(x1, x2), Math.max(x1, x2), y1));
			}
			intersect = new List[v.size()];
			for(int i=0; i<v.size(); ++i) {
				intersect[i] = new ArrayList<Integer>();
				for(int j=0; j<h.size(); ++j)
					if(v.get(i).a<=h.get(j).c&&v.get(i).b>=h.get(j).c&&h.get(j).a<=v.get(i).c&&h.get(j).b>=v.get(i).c)
						intersect[i].add(j);
			}
			matching = new int[h.size()];
			Arrays.fill(matching, -1);
			dist = new int[v.size()];
			vis1 = new boolean[v.size()];
			vis2 = new boolean[v.size()];
			int[] qu = new int[v.size()];
			int matches=0;
			while(true) {
				Arrays.fill(dist, -1);
				int qt=0, qh=0;
				for(int i=0; i<v.size(); ++i) {
					if(!vis1[i]) {
						qu[qt++]=i;
						dist[i]=0;
					}
				}
				while(qh<qt) {
					int u=qu[qh++];
					for(int v1 : intersect[u]) {
						int v2=matching[v1];
						if(v2!=-1&&dist[v2]==-1) {
							dist[v2]=dist[u]+1;
							qu[qt++]=v2;
						}
					}
				}
				int minc=0;
				Arrays.fill(vis2, false);
				for(int i=0; i<v.size(); ++i)
					if(!vis1[i]&&dfs(i))
						++minc;
				if(minc==0)
					break;
				matches+=minc;
			}
			out.println(n-matches);
		}
		
		boolean dfs(int u) {
			vis2[u]=true;
			for(int v1 : intersect[u]) {
				int v2=matching[v1];
				if(v2==-1||!vis2[v2]&&dist[v2]==dist[u]+1&&dfs(v2)) {
					matching[v1]=u;
					return vis1[u]=true;
				}
			}
			return false;
		}
		
		class Seg {
			int a, b, c;
			Seg(int a, int b, int c) {
				this.a=a;
				this.b=b;
				this.c=c;
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		in = new Reader();
		out = new PrintWriter(System.out);
		new Solver();
		out.close();
	}
	
	static class Reader {
		BufferedReader br;
		StringTokenizer st;
		Reader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}
		String next() {
			while(st==null||!st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch(Exception e) {}
			}
			return st.nextToken();
		}
		int nextInt() {
			return Integer.parseInt(next());
		}
	}
}
