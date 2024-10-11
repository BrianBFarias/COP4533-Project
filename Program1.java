import java.util.Scanner;

class Program1{
    public record Result(int numPlatforms, int totalHeight, int[] numPaintings) {}
   
    /**
    * Solution to program 1
    * @param n number of paintings
    * @param w width of the platform
    * @param heights array of heights of the paintings
    * @param widths array of widths of the paintings
    * @return Result object containing the number of platforms, total height of the paintings and the number of paintings on each platform
    */
    private static Result program1(int n, int w, int[] heights, int[] widths) {
        int numPaintings = n;
        int dpWidth = w;
        int[] paintingHeights = heights;
        int[] paintingWidths = widths;
        int cost = 0;
        int numPlatforms = 0;


        int[] paintingsPerPlatform = new int[n]; // Track the number of paintings on each platform
        int currentPlatformPaintings = 0; // Counter for the current platform's paintings
        int currentPlatformWidthUsed = 0; // The current platform width in use
        int currentPlatformMaxHeight = 0; // The maximum height for the current platform


        for (int i = 0; i < numPaintings; i++) {
            // If it's the first painting on this platform, initialize the platform
            if (currentPlatformWidthUsed == 0) {
                if (numPlatforms > 0) {
                    // Add the previous platform's height to the total cost before moving to a new platform
                    cost += currentPlatformMaxHeight;
                    paintingsPerPlatform[numPlatforms - 1] = currentPlatformPaintings;
                }
                numPlatforms++; // Increment the platform count
                currentPlatformMaxHeight = paintingHeights[i]; // Set the max height to the first painting
                currentPlatformPaintings = 0; // Reset painting count for the new platform
            }


            // If the current painting can fit on the current platform
            if ((currentPlatformWidthUsed + paintingWidths[i]) <= dpWidth) {
                currentPlatformWidthUsed += paintingWidths[i]; // Add the painting width to the platform
                currentPlatformMaxHeight = Math.max(currentPlatformMaxHeight, paintingHeights[i]); // Update max height
                currentPlatformPaintings++; // Increment the number of paintings on this platform
            } else {
                // Platform is full, finalize it and move to the next platform
                cost += currentPlatformMaxHeight; // Add the platform's height to the total cost
                paintingsPerPlatform[numPlatforms - 1] = currentPlatformPaintings; // Record number of paintings on this platform


                // Start a new platform for the current painting
                currentPlatformWidthUsed = paintingWidths[i]; // Set the new platform width to the current painting's width
                currentPlatformMaxHeight = paintingHeights[i]; // Set the new max height
                currentPlatformPaintings = 1; // Reset the painting count for the new platform
                numPlatforms++; // Increment the platform count
            }
        }


        // Add the last platform's height and record the paintings
        if (currentPlatformWidthUsed > 0) {
            cost += currentPlatformMaxHeight; // Add the last platform's height
            paintingsPerPlatform[numPlatforms - 1] = currentPlatformPaintings; // Record number of paintings on the last platform
        }


        // Create a new array with only the relevant platforms
        int[] finalPaintingsPerPlatform = new int[numPlatforms];
        System.arraycopy(paintingsPerPlatform, 0, finalPaintingsPerPlatform, 0, numPlatforms);


        return new Result(numPlatforms, cost, finalPaintingsPerPlatform);
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
        Result result = program1(n, W, heights, widths);
        System.out.println(result.numPlatforms);
        System.out.println(result.totalHeight);
        for(int i=0; i<result.numPaintings.length; i++){
            System.out.println(result.numPaintings[i]);
        }
    }
}