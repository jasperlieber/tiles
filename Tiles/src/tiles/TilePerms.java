package tiles;

import java.util.BitSet;
import java.util.TreeSet;

public class TilePerms extends TreeSet<Tile>
{
    private static final long serialVersionUID = 5338022458432599538L;
    public static final boolean REPS_NO = false;
    public static final boolean REPS_YES = true;
    
    private final int numEdges, numColors;
    private final boolean bRepsOk;
    private Tile tmp;
    
    public TilePerms(int numEdges, int numColors, boolean bReps)
    {
        this.numEdges = numEdges;
        this.numColors = numColors;
        this.bRepsOk = bReps;
        tmp = new Tile(numEdges, numColors);
        for (int jj = 0; jj < (bRepsOk ? numEdges : (numColors - numEdges + 1)); jj++)
        {
            tmp.edgeColors[0].colorNum = jj;
            recurse(1);
        }
    }

    private void recurse(int depth)
    {
        for (int jj = 0; jj < numColors; jj++)
        {
            tmp.edgeColors[depth].colorNum = jj;
            if (depth < numEdges - 1)
            {
                recurse(depth+1);
            }
            else 
            {
                add(new Tile(tmp));
            }
        }
    }

    public boolean add(Tile tmp)
    {
//        System.out.println("trying " + tmp);
//        System.out.println(this);
        if (!bRepsOk)
        {
            boolean foundRep = false;
            BitSet found = new BitSet(numColors);
            found.set(tmp.edgeColors[0].colorNum);
            for (int jj = 1; jj < numEdges; jj++)
            {
                foundRep = found.get(tmp.edgeColors[jj].colorNum);
                if (foundRep) return false;
                found.set(tmp.edgeColors[jj].colorNum);
            }
        }
        
        for (Tile tile : this)
        {
//            System.out.println("comparing to " + tile);
            for (int ii = 1; ii < numEdges; ii++)
            {
                boolean shiftSame = true;
                for (int jj = 0; shiftSame && jj < numEdges; jj++)
                {
//                    System.out.println("tmp["+jj+"] ?== tile[ " 
//                            + ((ii + jj) % numEdges) + "]");
                    shiftSame = tmp.edgeColors[jj].colorNum 
                            == tile.edgeColors[(ii + jj) % numEdges].colorNum;
                }
                if (shiftSame) return false;
            }
        }
//        System.out.println("adding " + tmp);
        return super.add(tmp);
    }
    
    @Override
    public String toString()
    {
        StringBuffer out = new StringBuffer("Perms of " + numColors 
                + " colors for " + numEdges + " edges: \n");
        int cnt = 1;
        for (Tile tile : this)
        {
            out.append("#" + cnt++ + ": " + tile + "\n");
        }
        return out.toString();
    }

    public static TilePerms getPerms(int numEdges, int numColors, boolean bReps)
    {
        return new TilePerms(numEdges, numColors, bReps);
    }

    public static void main(String[] args)
    {
        System.out.println(TilePerms.getPerms(3,4,REPS_NO));
    }

}
