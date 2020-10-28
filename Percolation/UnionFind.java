public class UnionFind {

    private int[] id, size;

    public UnionFind(int n) {
        id = new int[n];
        size = new int[n];

        for (int i = 0; i < n; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    public int find(int node) {
        while (node != id[node]) {
            id[node] = id[id[node]];
            node = id[node];
        }
        return node;
    }

    public void union(int node1, int node2) {
        int root1 = find(node1);
        int root2 = find(node2);

        if (root1 == root2)
            return;

        if (size[root1] > size[root2]) {
            id[root2] = id[root1];
            size[root1] += size[root2];
        }
        else {
            id[root1] = id[root2];
            size[root2] += size[root1];
        }
    }
}
