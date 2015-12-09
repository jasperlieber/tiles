package tiles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Iterator;

import javax.swing.JPanel;

class DrawTilePerms extends JPanel
{
    private static final long serialVersionUID = -8026182402662297589L;
    private TilePerms tiles;
    
    DrawTilePerms() throws Exception
    {
//        tiles = TilePerms.getPerms(3, 3, TilePerms.REPS_YES);
//        tiles = TilePerms.getPerms(3, 5, TilePerms.REPS_NO);
//        tiles = TilePerms.getPerms(4, 6, TilePerms.REPS_NO);
        tiles = TilePerms.getPerms(4, 5, TilePerms.REPS_NO);
    }

    private void doDrawing(Graphics g) throws Exception
    {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,
               RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        Dimension size = getSize();
//        Insets insets = getInsets();

        int maxW = size.width;// - insets.left - insets.right;
        int maxH = size.height;// - insets.top - insets.bottom;
        
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, maxW-1, maxH-1);
        
        // tileCnt 
        int rows = (int) Math.sqrt((double)tiles.size());
        int cols = (int) Math.ceil(tiles.size() / (double)rows);
        
        System.out.println(tiles.size() + " tiles in " + cols + " cols and " + rows + " rows");

        double tileSize = Math.min((double)maxW / cols, (double)maxH / rows);
        int leftX = maxW / cols;
        int topY  = maxH / rows;

        Iterator<Tile> it = tiles.iterator();
        
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                if (!it.hasNext()) return;
                Tile tile = it.next();
                tile.draw(leftX*col,topY*row,tileSize,g2d);
            }
        }
        
    }

    @Override
    public void paintComponent(Graphics g)
    {

        super.paintComponent(g);
        try
        {
            doDrawing(g);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
