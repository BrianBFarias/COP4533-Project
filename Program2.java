import java.util.Scanner;

class Program2{
    public record Result(int numPlatforms, int totalHeight, int[] numPaintings) {}
   
    /**
    * Solution to program 2
    * @param n number of paintings
    * @param w width of the platform
    * @param heights array of heights of the paintings
    * @param widths array of widths of the paintings
    * @return Result object containing the number of platforms, total height of the paintings and the number of paintings on each platform
    */
    private static Result program2(int n, int w, int[] heights, int[] widths) {
        int[] dp = new int[n + 1];
        int[] platforms = new int[n + 1];
        int[] lastPlatform = new int[n];

        for (int i = 1; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
            int currentWidth = 0;
            int currentMaxHeight = 0;

            for (int j = i; j > 0; j--) {
                currentWidth += widths[j - 1];
                
                if (currentWidth > w) break;
                
                currentMaxHeight = Math.max(currentMaxHeight, heights[j - 1]);
                
                if (dp[i] > dp[j - 1] + currentMaxHeight) {
                    dp[i] = dp[j - 1] + currentMaxHeight;
                    platforms[i] = platforms[j - 1] + 1;
                    lastPlatform[i - 1] = j - 1;
                }
            }
        }

        int numPlatforms = platforms[n];
        int[] numPaintingsOnPlatform = new int[numPlatforms];
        int currentPainting = n;
        for (int i = numPlatforms - 1; i >= 0; i--) {
            numPaintingsOnPlatform[i] = currentPainting - lastPlatform[currentPainting - 1];
            currentPainting = lastPlatform[currentPainting - 1];
        }

        return new Result(numPlatforms, dp[n], numPaintingsOnPlatform);
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int W = sc.nextInt();
        int[] heights = new int[n];
        int[] widths = new int[n];
        for(int i=0; i<n; i++){
            heights[i] = sc.nextInt();
        }
        for(int i=0; i<n; i++){
            widths[i] = sc.nextInt();
        }
        sc.close();
        Result result = program2(n, W, heights, widths);
        System.out.println(result.numPlatforms);
        System.out.println(result.totalHeight);
        for(int i=0; i<result.numPaintings.length; i++){
            System.out.println(result.numPaintings[i]);
        }
    }
}