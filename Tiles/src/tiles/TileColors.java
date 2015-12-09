package tiles;

import java.awt.Color;

public class TileColors
{
    int colorNum=-2;

    public TileColors(int c)
    {
        colorNum = c;
    }
    
//    public TileColor()
//    {
//        // TODO Auto-generated constructor stub
//    }

    public final Color getColor() throws Exception
    {
         final Color[] colors = {
                Color.black,
                Color.red,
                Color.green,
                //Color.getHSBColor(.5f, .99f, .99f), //Color.blue,
                Color.cyan,
                Color.yellow,
                Color.magenta,
                Color.blue,
                Color.orange,
                Color.pink,
                };
        
        if (colorNum < -1 || colorNum > colors.length-2)
            throw new Exception("undefined color");
        
        return colors[colorNum+1];
    }
    
    static TileColors[] createArray(int numEdges)
    {
        TileColors[] temp = new TileColors[numEdges];
        for (int jj = 0; jj < numEdges; jj++)
            temp[jj] = new TileColors(-2);
        return temp;
    }

}
