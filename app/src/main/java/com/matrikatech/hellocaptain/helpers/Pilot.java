package com.matrikatech.hellocaptain.helpers;

/**
 * Created by TANIM on 12-Mar-15.
 */
public class Pilot {

    private String name, rank;
    private int svcNo;

    public Pilot(String rank, String name) {
        this.name = name;
        this.rank = rank;
        this.svcNo = svcNo;
    }

    public Pilot(String name) {
        this.name = name;
    }

    public String getName() {
        if (rank == null)
        {
            return name;
        }
        return rank + " " + name;
    }

    public int getSvcNo() {
        return svcNo;
    }

    public void setSvcNo(int svcNo) {
        this.svcNo = svcNo;
    }
}
