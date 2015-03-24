package com.matrikatech.hellocaptain.helpers;

/**
 * Created by TANIM on 23-Mar-15.
 *
 * Returns a SQL string containing only where clauses
 */
public class WhereSQLBuilder {
    private String firstPilot, secondPilot, acName, fromDt, toDt, msn;
    private int night, rotor, multi;

    public void setFirstPilot(String firstPilot) {
        this.firstPilot = firstPilot;
    }

    public void setSecondPilot(String secondPilot) {
        this.secondPilot = secondPilot;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public void setFromDt(String fromDt) {
        this.fromDt = fromDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public void setNight(boolean night) {
        this.night = night ? 1 : 0;
    }

    public void setRotor(boolean rotor) {
        this.rotor = rotor ? 1:0;
    }

    public void setMulti(boolean multi) {
        this.multi = multi ? 1:0;
    }

    @Override
    public String toString() {
        String fp = new StringUtils().getNullSafeString(firstPilot);
        String sp = new StringUtils().getNullSafeString(secondPilot);
        String acn = new StringUtils().getNullSafeString(acName);
        String ms = new StringUtils().getNullSafeString(msn);
        String fd = new StringUtils().getNullSafeString(fromDt);
        String td = new StringUtils().getNullSafeString(toDt);

        String queryStr = " WHERE ";
        queryStr += (fp.isEmpty()) ? "" : " pilot_1= '" + fp + "' AND ";
        queryStr += (sp.isEmpty()) ? "" : " pilot_2= '" + sp + "' AND ";
        queryStr += (acn.isEmpty()) ? "" : " ac_name='" + acn + "' AND ";
        queryStr += (ms.isEmpty()) ? "" : " mission LIKE '%" + ms + "%'  AND ";
        queryStr += (multi == 0 ? "" : " multi_eng=1 AND ");
        queryStr += (rotor == 0 ? "" : " rotor=1 AND ");
        queryStr += (night == 0 ? "" : " night=1 AND ");
        if ((!fd.isEmpty()) && (!td.isEmpty())) {
            queryStr += " (dt BETWEEN '" + fd + "' AND '" + td + "') AND ";
        } else { queryStr += ""; }
        queryStr += " id != 0 ";
        queryStr += " ORDER BY dt";

        return queryStr;
    }
}