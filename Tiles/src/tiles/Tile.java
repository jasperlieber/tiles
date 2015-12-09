package tiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.BitSet;

class Tile implements Comparable<Tile>
{

    private static final int MAX_HASH_SMALL = 1000000;
    private static final int MAX_HASH_BIG   = 10000000;
    private static final int MAX_NUM_COLORS = 10;

    public final int numEdges;
    public final int numColors;
    public final TileColors[] edgeColors;

    private Point2D.Double[] corners;
    private int category = -1;
    private double theta0;

    public Tile(int numEdges, int numColors) 
    {
        this.numEdges = numEdges;
        this.numColors = numColors;
        edgeColors    =  TileColors.createArray(numEdges);
        corners = new Point2D.Double[numEdges];
        for (int jj = 0; jj < numEdges; jj++)
            corners[jj] = new Point2D.Double();
        switch(numEdges)
        {
            case 3:
                theta0 = -Math.PI * .5;
                break;
            case 4:
                theta0 = -Math.PI * .25;
                break;
            default:
                theta0 = 0;
        }
    }
    
    public Tile(TileColors[] edgeColors, int numColors) 
    {
        this(edgeColors.length, numColors);
        for (int jj = 0; jj < numEdges; jj++)
            this.edgeColors[jj].colorNum = edgeColors[jj].colorNum;
    }
    
    public Tile(Tile tmp)
    {
        this(tmp.edgeColors, tmp.numColors);
    }

    public String toString()
    {
        StringBuffer out = new StringBuffer("[");
        for (int jj = 0; jj < numEdges; jj++)
        {
            out.append(String.format(" %d ",edgeColors[jj].colorNum));
        }
        out.append(String.format("] c%d", getCategory()));
        return out.toString();

    }

    /**
     * The "category" of a tile is the max color number of it's edges.
     * @return
     */
    public int getCategory()
    {
        if (category < 0)
        {
            for (int jj = 0; jj < numEdges; jj++)
            {
                if (edgeColors[jj].colorNum > category)
                    category = edgeColors[jj].colorNum;
            }
        }
        return category;
    }

    public void draw(int leftX, int topY, double tileSize, Graphics2D g2d) 
            throws Exception
    {
        g2d.setStroke(new BasicStroke(3,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND));
        tileSize /= 2;
        double cx = leftX + tileSize + 10;
        double cy = topY  + tileSize*2*.702-30;
        double radius = 1.85*tileSize/Math.sqrt(3);
        for (int jj = 0; jj < numEdges; jj++)
        {
            double theta = Math.PI * 2 / numEdges * jj + theta0;
           
            corners[jj].x = Math.cos(theta) * radius + cx;
            corners[jj].y = Math.sin(theta) * radius + cy;
        }
        
        BitSet setCols = new BitSet(numColors);
        for (int jj = 0; jj < numEdges; jj++)
        {
            Path2D.Double tri = new Path2D.Double();
            tri.moveTo(corners[jj].x, corners[jj].y);
            tri.lineTo(corners[(jj+1) % numEdges].x, corners[(jj+1) % numEdges].y);
            tri.lineTo(cx,cy);
            tri.closePath();
            g2d.setColor(edgeColors[jj].getColor());
            g2d.fill(tri);
            g2d.setColor(Color.black);
            g2d.draw(tri);
            
            setCols.set(edgeColors[jj].colorNum);
        }
        
        final float dash1[] = {6f};
        final BasicStroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 6f, dash1, 0f);
        final BasicStroke solid = new BasicStroke(2);
        
        for (int jj = 0; jj < numColors; jj++)
        {
            g2d.setStroke(setCols.get(jj) ? solid : dashed);
            Ellipse2D circle2D = new Ellipse2D.Double();
            circle2D.setFrameFromCenter(
                cx,cy,cx+tileSize*.06*(jj+2),cy+tileSize*.06*(jj+2));
            g2d.draw(circle2D);
        }
        
//        g2d.setStroke(new BasicStroke(1));
//        Ellipse2D circle2D = new Ellipse2D.Double();
//        circle2D.setFrameFromCenter(
//            cx,cy,cx+tileSize,cy+tileSize);
//        g2d.draw(circle2D);
        
    }

    
//    static ArrayList<Tile> getPerms(int numEdges, int numColors)
//    {
//        ArrayList<Tile> tiles = new ArrayList<Tile>();
//        
//        return tiles;
//    }
    
    @Override
    public boolean equals(Object obj)
    {
        return obj.getClass() == Tile.class
                && ((Tile)obj).hashCode() == hashCode();
    }
    
    @Override
    public int hashCode()
    {
        int base = 0;
        for (TileColors color:edgeColors)
        {
//            System.out.println(color.colorNum+1);
            base = base * MAX_NUM_COLORS + color.colorNum;
        }
        if (Math.abs(base) > MAX_HASH_SMALL )
        {
            System.err.println("hashCode() exploded - base = " + base);
            System.exit(-1);
        }
        if (getCategory() > MAX_NUM_COLORS )
        {
            System.err.println("hashCode() exploded - tile category = " 
                    + getCategory());
            System.exit(-1);
        }
        int hash = base + MAX_HASH_BIG * numEdges;
//        System.out.println("hash(" + toString() + ") = " + hash);
        return hash;
    }


    @Override
    public int compareTo(Tile tile)
    {
        return Integer.compare(hashCode(), tile.hashCode());
    }
    
   
}

