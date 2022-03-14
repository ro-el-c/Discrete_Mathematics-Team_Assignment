import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


public class SearchGraph_Comment{
    /* 시작점 : 1 */
    public static void Dijkstra(int [][] graphMatrix, int n){   // graphMatrix : 다익스트라 알고리즘 적용 그래프 인접 행렬, n : 그래프의 노드 수
        boolean[] visitedNode = new boolean[n];                 // 노드 방문 여부 배열 -> true : 방문 완료, false : 아직 방문 X
        int [] distance = new int[n];                           // 시작점으로부터 각 노드까지 거리
        String [] route = new String[n];

        /* 거리 초기화 */
        distance = graphMatrix[0];        // 거리 초기화
        visitedNode[0] = true;      // 시작점은 방문한 것으로

        Arrays.fill(route, "1");        // 모든 정점의 경로 시작점 1로 초기화

        //System.out.println(Arrays.toString(route));
        //System.out.println(Arrays.toString(distance));
        //System.out.println();

        for (int i=0; i<n-1; i++){
            int minDistance = Integer.MAX_VALUE;
            int minIndex = 0;

            /* D 배열에서 시작점으로 부터 최소 거리를 가지는 정점 추출 */
            for(int j=0; j<n; j++){
                if(!visitedNode[j] && distance[j] != Integer.MAX_VALUE){
                    if(distance[j] < minDistance) {
                        minDistance = distance[j];
                        minIndex = j;
                    }
                }
            }

            System.out.printf("거리가 최소인 정점 : %d\n", minIndex+1);
            /* 확정된 정점에 대하여 마지막 경로 지정 */
            route[minIndex] += " - " + (minIndex+1);

            System.out.println("정점 fix 후 경로 : "+Arrays.toString(route));

            /* 해당 정점 방문 표기 */
            visitedNode[minIndex] = true;

            //System.out.println("방문 여부 : " + Arrays.toString(visitedNode));

            /* D 배열의 각 원소(최단 거리) 유지 및 갱신 */
            for(int j=0; j<n; j++){
                if(!visitedNode[j] && graphMatrix[minIndex][j] != Integer.MAX_VALUE){
                    System.out.println(j+1+" , "+visitedNode[j]+" , "+distance[j]+" , "+distance[minIndex]+" , "+ graphMatrix[minIndex][j]);
                    if(distance[j] > (distance[minIndex] + graphMatrix[minIndex][j])){
                        distance[j] = distance[minIndex] + graphMatrix[minIndex][j];
                        route[j] = route[minIndex];
                    }
                }
            }

            System.out.println("거리 갱신 후 경로 : "+Arrays.toString(route));

            System.out.println("D배열(시작점으로부터 최단 거리) 값 : " + Arrays.toString(distance));
            System.out.println();
        }

        System.out.println("----------------------------------------");

        /* (결과) 최단 경로 및 거리 출력 */
        System.out.println("시작점 : 1");
        for(int i=1; i<n; i++){
            System.out.printf("정점 [%d] : %s, 길이 : %d\n", i+1, route[i], distance[i]);
        }

        System.out.println("=======================================");
        System.out.println();
    }


    public static void main(String[] args) throws IOException{
        int n=0, count=1;   // nodeNum : 각 그래프의 총 노드 수, count : 몇 번째 그래프인지
        String str;

        /* 파일 읽기 */
        File file = new File("./input2.txt");
        if(file.exists()) {
            BufferedReader bufRead = new BufferedReader(new FileReader(file));
            while ((str = bufRead.readLine()) != null) { // 더 이상 읽을 내용 없으면 종료
                System.out.printf("그래프 [%d]\n", count);

                n = Integer.parseInt(str);               // 정점의 개수
                //System.out.println("정점 개수 : " + n);

                int[][] graphMatrix = new int[n][n];    // 그래프 -> 인접 행렬 / 노드 n의 인덱스 : n-1

                for (int k = 0; k < n; k++) {           // 정점 개수만큼의 줄 읽어오기
                    String[] splitStr = bufRead.readLine().split(" ");
                    int i = Integer.parseInt(splitStr[0]);      // node
                    //System.out.printf("node : %d\n", i);

                    for(int l=0; l<(splitStr.length/2); l++){
                        int j = Integer.parseInt(splitStr[l*2+1]);
                        int weight = Integer.parseInt(splitStr[(l+1)*2]);

                        /* 가중치 값 저장 */
                        graphMatrix[i-1][j-1] = weight; // 인접 행렬의 index는 0부터 시작. -> 각 노드명(숫자) -1
                        //System.out.printf("graphMatrix[%d][%d] : %d\n", i, j, weight);
                    }

                    for(int l=0; l<n; l++){
                        if(graphMatrix[i-1][l] == 0){
                            if(l == i-1){
                                continue;
                            }
                            graphMatrix[i-1][l] = Integer.MAX_VALUE;        // 인접하지 않은 점 사이의 거리를 무한대(integer 범위 내 가장 큰 값)으로 설정
                        }
                    }
                    //System.out.println();
                }

                //System.out.println(Arrays.deepToString(graphMatrix));

                /* 다익스트라 알고리즘 구현 함수 호출 */
                Dijkstra(graphMatrix, n);
                count++;
            }
            bufRead.close();
        }
    }
}
