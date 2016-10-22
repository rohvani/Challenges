// https://leetcode.com/problems/perfect-squares/

public class Solution
{
    public int numSquares(int n)
    {
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        
        // This is a cache that kees track of how many squares
        // it takes to calculate a particular i (where i is our
        // index within the cache)
        int[] cache = new int[n + 1];
        
        // Add perfect squares <= n to cache and queue
        for(int i = 1; i * i <= n; i++)
        {
            int square = i * i;
            
            // Our input was a perfect square, so we are done
            if(square == n) return 1;
            
            cache[square]++;
            queue.offer(square);
        }
        
        // Perform BFS
        while(!queue.isEmpty())
        {
            int square_sum = queue.poll();
            int remaining = n - square_sum;
            
            // Find additional squares to add to our current square_sum so we can queue them
            for(int i = 1; i * i <= remaining; i++)
            {
                int additional_square = i * i;
                
                // We found our next best square_sum!
                if(additional_square == remaining) return cache[square_sum] + 1;
                
                // This square_sum is too big
                if(additional_square > remaining) break;
                
                int new_square_sum = additional_square + square_sum;
                
                // If we've found a new sum of squares, we should add how many squares it took 
                // in our cache and add it to the queue
                if(cache[new_square_sum] == 0)
                {
                    cache[additional_square + square_sum] = cache[square_sum] + 1;
                    queue.offer(additional_square + square_sum);
                }
            }
        }
        
        return cache[n];
    }
}