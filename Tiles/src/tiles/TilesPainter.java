package tiles;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TilesPainter extends EscapableFrame
{
    private static final long serialVersionUID = 1310909846189463898L;

    public TilesPainter() throws Exception
    {
        initUI();
    }

    private void initUI() throws Exception
    {
        setTitle("Tiles");
        
        add(new DrawTilePerms());
//        add(TileShape.drawShape(TileShape.Tri4,
//                TilePerms.getPerms(3, 4, TilePerms.REPS_NO)));

        setSize(1440, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                TilesPainter sk;
                try
                {
                    sk = new TilesPainter();
                    sk.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

}