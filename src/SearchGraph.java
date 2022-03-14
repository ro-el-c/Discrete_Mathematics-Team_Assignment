import java.util.Arrays;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class SearchGraph{
    public static void Dijkstra(int [][] graphMatrix, int n){
        boolean[] visitedNode = new boolean[n];
        int [] distance = new int[n];
        String [] route = new String[n];

        distance = graphMatrix[0];
        visitedNode[0] = true;

        Arrays.fill(route, "1");

        for (int i=0; i<n-1; i++){
            int minDistance = Integer.MAX_VALUE;
            int minIndex = 0;

            for(int j=0; j<n; j++){
                if(!visitedNode[j] && distance[j] != Integer.MAX_VALUE){
                    if(distance[j] < minDistance) {
                        minDistance = distance[j];
                        minIndex = j;
                    }
                }
            }

            route[minIndex] += " - " + (minIndex+1);

            visitedNode[minIndex] = true;

            for(int j=0; j<n; j++){
                if(!visitedNode[j] && graphMatrix[minIndex][j] != Integer.MAX_VALUE){
                    if(distance[j] > (distance[minIndex] + graphMatrix[minIndex][j])){
                        distance[j] = distance[minIndex] + graphMatrix[minIndex][j];
                        route[j] = route[minIndex];
                    }
                }
            }
        }


        for (int i=0; i<n; i++){
            if(distance[i] == Integer.MAX_VALUE){
                route[i] = "경로가 존재하지 않음";
            }
        }

        System.out.println("----------------------------------------");
        System.out.println("시작점 : 1");
        for(int i=1; i<n; i++){
            System.out.printf("정점 [%d] : %s, 길이 : %d\n", i+1, route[i], distance[i]);
        }
        System.out.println("=======================================");
        System.out.println();
    }


    public static void main(String[] args) throws IOException{
        int n=0, count=1;
        String str;

        File file = new File("./input2.txt");

        if(file.exists()) {
            BufferedReader bufRead = new BufferedReader(new FileReader(file));

            while ((str = bufRead.readLine()) != null) {
                System.out.printf("그래프 [%d]\n", count);
                n = Integer.parseInt(str);

                int[][] graphMatrix = new int[n][n];

                for (int k = 0; k < n; k++) {
                    String[] splitStr = bufRead.readLine().split(" ");
                    int num = Integer.parseInt(splitStr[0]);

                    for(int l=0; l<(splitStr.length/2); l++){
                        int j = Integer.parseInt(splitStr[l*2+1]);
                        int weight = Integer.parseInt(splitStr[(l+1)*2]);

                        graphMatrix[num-1][j-1] = weight;
                    }

                    for(int l=0; l<n; l++){
                        if(graphMatrix[num-1][l] == 0){
                            if(l == num-1){
                                continue;
                            }
                            graphMatrix[num-1][l] = Integer.MAX_VALUE;
                        }
                    }
                }

                /* 다익스트라 함수 호출 -> 최단 경로 및 거리 출력 */
                Dijkstra(graphMatrix, n);

                count++;
            }
            bufRead.close();
        } else{
            System.out.println("No file found");
        }
    }
}
