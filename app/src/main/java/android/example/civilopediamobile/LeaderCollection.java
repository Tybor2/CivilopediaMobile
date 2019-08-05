package android.example.civilopediamobile;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class LeaderCollection {

    private static LeaderCollection leaderCollection;

    private Context context;
    private List<Leader> leaderList;

    public static LeaderCollection get(Context context) {
        if(leaderCollection == null)
            leaderCollection = new LeaderCollection(context);
        return leaderCollection;
    }

    private LeaderCollection(Context context){
        this.context = context.getApplicationContext();
        leaderList = new ArrayList<>();
    }

    public void addLeaders(List<Leader> leadersToAdd){
        //leaderList = leadersToAdd;
        for(Leader leader: leadersToAdd){
            leaderList.add(leader);
        }
    }

    public void addLeader(Leader leader){
        leaderList.add(leader);
    }

    public List<Leader> getLeaders(){
        return leaderList;
    }

    public Leader getLeader(String name, String civ){
        for(Leader leader: leaderList){
            if(leader.getName().equals(name) && leader.getCivilization().equals(civ))
                return leader;
        }
        return null;
    }

    public void setLeaders(List<Leader> leaders){
        leaderList = leaders;
    }
}
