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

        int totalHeight = 0;
        int currentWidth = 0;
        int currentMaxHeight = 0;
        int localMin = 0;

        int[] numPaintingsOnPlatformFirstPhase = new int[n];
        int[] numPaintingsOnPlatformSecondPhase = new int[n]; 
        int firstPhasePlatformCount = 0;
        int secondPhasePlatformCount = 0;

        int paintingsOnCurrentPlatform = 0;
        boolean localMinProcessed = false;

        int i = 0;

        // First phase: process the paintings before the local minimum
        while (heights[i] > heights[i + 1]) {

            // Check if current painting fits on platform
            if (currentWidth + widths[i] <= w) {
                currentWidth += widths[i];
                currentMaxHeight = Math.max(currentMaxHeight, heights[i]);
                paintingsOnCurrentPlatform++;

            } 
            // create new platform
            else {
                totalHeight += currentMaxHeight;
                numPaintingsOnPlatformFirstPhase[firstPhasePlatformCount] = paintingsOnCurrentPlatform;
                firstPhasePlatformCount++;

                // Start a new platform with the current painting
                currentWidth = widths[i];
                currentMaxHeight = heights[i];
                paintingsOnCurrentPlatform = 1;
            }
            i++;
        }

        localMin = i;

        // Check if local min painting fits on platform
        if (currentWidth + widths[i] <= w) {
            currentWidth += widths[i];
            currentMaxHeight = Math.max(currentMaxHeight, heights[i]);
            paintingsOnCurrentPlatform++;
            localMinProcessed = true;
        }

        // Move to next platform for starting from back
        if (paintingsOnCurrentPlatform > 0) {
            totalHeight += currentMaxHeight;
            numPaintingsOnPlatformFirstPhase[firstPhasePlatformCount] = paintingsOnCurrentPlatform;
            firstPhasePlatformCount++;
        }

        currentWidth = 0;
        currentMaxHeight = 0;
        paintingsOnCurrentPlatform = 0;
        i = n - 1;

        // Second phase: process the paintings after the local minimum
        while (i >= localMin) {
            // Check if current painting fits on platform
            if (currentWidth + widths[i] <= w) {
                if (localMinProcessed && localMin == i) break;

                currentWidth += widths[i];
                currentMaxHeight = Math.max(currentMaxHeight, heights[i]);
                paintingsOnCurrentPlatform++;

            } 

            // Create new platform
            else {
                totalHeight += currentMaxHeight;
                numPaintingsOnPlatformSecondPhase[secondPhasePlatformCount] = paintingsOnCurrentPlatform;
                secondPhasePlatformCount++;

                // Start a new platform with the current painting
                currentWidth = widths[i];
                currentMaxHeight = heights[i];
                paintingsOnCurrentPlatform = 1;
            }
            i--;
        }

        // Move to next platform for starting from back
        if (paintingsOnCurrentPlatform > 0) {
            totalHeight += currentMaxHeight;
            numPaintingsOnPlatformSecondPhase[secondPhasePlatformCount] = paintingsOnCurrentPlatform;
            secondPhasePlatformCount++;
        }

        int totalPlatforms = firstPhasePlatformCount + secondPhasePlatformCount;
        int[] finalPaintingsOnPlatforms = new int[totalPlatforms];

        System.arraycopy(numPaintingsOnPlatformFirstPhase, 0, finalPaintingsOnPlatforms, 0, firstPhasePlatformCount);

        // flip final platforms order (reversed)
        for (int j = 0; j < secondPhasePlatformCount; j++) {
            finalPaintingsOnPlatforms[firstPhasePlatformCount + j] = numPaintingsOnPlatformSecondPhase[secondPhasePlatformCount - j - 1];
        }

        return new Result(totalPlatforms, totalHeight, finalPaintingsOnPlatforms);
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